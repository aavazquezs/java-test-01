package com.avangenio.warehouse;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.avangenio.warehouse.model.ContainerType;
import com.avangenio.warehouse.model.Product;
import com.avangenio.warehouse.model.ProductType;
import com.avangenio.warehouse.model.Section;
import com.avangenio.warehouse.repository.ProductRepository;
import com.avangenio.warehouse.repository.SectionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.RequestDispatcher;

@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
public class ApiDocumentation {
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private SectionRepository sectionRepository;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;
	
	@BeforeEach
	public void setUp(RestDocumentationContextProvider restDocumentation) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
				.apply(documentationConfiguration(restDocumentation)).build();
	}
	
	@Test
	void errorExample() throws Exception {
		this.mockMvc
				.perform(get("/error")
						.requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 400)
						.requestAttr(RequestDispatcher.ERROR_REQUEST_URI,
								"/api/sections")
						.requestAttr(RequestDispatcher.ERROR_MESSAGE,
								"The tag 'http://localhost:8080/api/sections/123' does not exist"))
				.andDo(print()).andExpect(status().isBadRequest())
				.andExpect(jsonPath("error", is("Bad Request")))
				.andExpect(jsonPath("timestamp", is(notNullValue())))
				.andExpect(jsonPath("status", is(400)))
				.andExpect(jsonPath("path", is(notNullValue())))
				.andDo(document("error-example",
						responseFields(
								fieldWithPath("error").description("The HTTP error that occurred, e.g. `Bad Request`"),
								fieldWithPath("path").description("The path to which the request was made"),
								fieldWithPath("status").description("The HTTP status code, e.g. `400`"),
								fieldWithPath("timestamp").description("The time, in milliseconds, at which the error occurred"))));
	}
	
	@Test
	void indexExample() throws Exception {
		this.mockMvc.perform(get("/api/"))
			.andExpect(status().isOk())
			.andDo(document("index-example",
					responseFields(
							subsectionWithPath("_links").description("<<resources_index_access_links,Links>> to other resources"))));

	}
	
	@Test
	void sectionListExample() throws Exception {
		this.productRepository.deleteAll();
		this.sectionRepository.deleteAll();

		createSection(1, ProductType.Clothing);
		createSection(2, ProductType.ElectricalAppliance);
		createSection(3, ProductType.GroomingProduct);
		
		this.mockMvc.perform(get("/api/sections"))
			.andExpect(status().isOk())
			.andDo(document("section-list-example",
					responseFields(
							subsectionWithPath("_embedded.sections").description("An array of <<resources_section, Section resources>>"),
							subsectionWithPath("_links").description("<<resources_tags_list_links, Links>> to other resources"),
							subsectionWithPath("page").description("Pagination infos")))
							
					);
	}
	
	@Test
	void productsCreateExample() throws Exception {
		Map<String, Object> section = new HashMap<>();
		section.put("size", 1);
		section.put("productType", "Clothing");

		String sectionLocation = this.mockMvc
				.perform(
						post("/api/sections").contentType(MediaTypes.HAL_JSON).content(
								this.objectMapper.writeValueAsString(section)))
				.andExpect(status().isCreated()).andReturn().getResponse()
				.getHeader("Location");

		Map<String, Object> product = new HashMap<String, Object>();
		product.put("color", "blue");
		product.put("containerType", "Cardboard");
		product.put("count", 100);
		product.put("isFragile", true);
		product.put("lote", "L-01");
		product.put("price", 10.0);
		product.put("size", 10);
		product.put("section", sectionLocation);
		
		this.mockMvc.perform(
				post("/api/products").contentType(MediaTypes.HAL_JSON).content(
						this.objectMapper.writeValueAsString(product))).andExpect(
				status().isCreated())
				.andDo(document("product-create-example",
						requestFields(
									fieldWithPath("color").description("The color of the product"),
									fieldWithPath("containerType").description("The containerType of the product"),
									fieldWithPath("count").description("The count of the product"),
									fieldWithPath("isFragile").description("Indicates whether the product is fragile or not"),
									fieldWithPath("lote").description("The lote of the product"),
									fieldWithPath("price").description("The price of product"),
									fieldWithPath("size").description("The size of product"),
									fieldWithPath("section").description("The section where product belong")
						)));
	}
	
	@Test
	void productGetExample() throws Exception {
		
		Map<String, Object> section = new HashMap<>();
		section.put("size", 1);
		section.put("productType", "Clothing");

		String sectionLocation = this.mockMvc
				.perform(
						post("/api/sections").contentType(MediaTypes.HAL_JSON).content(
								this.objectMapper.writeValueAsString(section)))
				.andExpect(status().isCreated()).andReturn().getResponse()
				.getHeader("Location");
		
		Map<String, Object> product = new HashMap<String, Object>();
		product.put("color", "blue");
		product.put("containerType", "Cardboard");
		product.put("count", 100);
		product.put("isFragile", true);
		product.put("lote", "L-01");
		product.put("price", 10.0);
		product.put("size", 10);
		product.put("section", sectionLocation);
		
		String productLocation = this.mockMvc
				.perform(
						post("/api/products").contentType(MediaTypes.HAL_JSON).content(
								this.objectMapper.writeValueAsString(product)))
				.andExpect(status().isCreated()).andReturn().getResponse()
				.getHeader("Location");

		this.mockMvc.perform(get(productLocation))
			.andExpect(status().isOk())
			.andExpect(jsonPath("color", is(product.get("color"))))
			.andExpect(jsonPath("containerType", is(product.get("containerType"))))
			.andExpect(jsonPath("_links.self.href", is(productLocation)))
			.andExpect(jsonPath("_links.product", is(notNullValue())))
			.andDo(print())
			.andDo(document("product-get-example",
					responseFields(
							fieldWithPath("color").description("The color of the product"),
							fieldWithPath("containerType").description("The containerType of the product"),
							fieldWithPath("count").description("The count of the product"),
							fieldWithPath("isFragile").description("Indicates whether the product is fragile or not"),
							fieldWithPath("lote").description("The lote of the product"),
							fieldWithPath("price").description("The price of product"),
							fieldWithPath("size").description("The size of product"),
							subsectionWithPath("_links").description("<<resources_product_links,Links>> to other resources"))));
	}
	
	@Test
	void productListExample() throws Exception {
		this.productRepository.deleteAll();
		this.sectionRepository.deleteAll();

		Section section = createSection(100, ProductType.Clothing);
		
		createProduct("red", ContainerType.Cardboard, 100, true, "L-01", 30.5, section, 100);
		
		createProduct("blue", ContainerType.Nylon, 200, false, "L-02", 40.0, section, 200);
		
		createProduct("green", ContainerType.Glass, 300, true, "L-02", 50.0, section, 300);
		
		this.mockMvc.perform(get("/api/products"))
			.andExpect(status().isOk())
			.andDo(document("products-list-example",
					responseFields(
							subsectionWithPath("_embedded.products").description("An array of <<resources_products,Product resources>>"),
							subsectionWithPath("_links").description("<<resources_products_list_links, Links>> to other resources"),
							subsectionWithPath("page").description("Pagination infos"))));
	}
	
	private Section createSection(Integer size, ProductType productType) {
		Section section = Section.builder()
				.size(size)
				.productType(productType)
				.products(Collections.emptyList())
				.build();
		
		return this.sectionRepository.save(section);
	}

	private Product createProduct(String color, ContainerType containerType, Integer count, Boolean isFragile, String lote,
		Double price, Section section, Integer size) {
		Product product = Product.builder()
				.color(color)
				.containerType(containerType)
				.count(count)
				.isFragile(isFragile)
				.lote(lote)
				.price(price)
				.section(section)
				.size(size)
				.build();
		return this.productRepository.save(product);
	}
	
	@Test
	void sectionCreateExample() throws Exception {
		Map<String, Object> section = new HashMap<>();
		section.put("size", 1);
		section.put("productType", "Clothing");

		this.mockMvc.perform(
				post("/api/sections").contentType(MediaTypes.HAL_JSON).content(
						this.objectMapper.writeValueAsString(section)))
				.andExpect(status().isCreated())
				.andDo(document("sections-create-example",
						requestFields(
								fieldWithPath("size").description("The size of the tag"),
								fieldWithPath("productType").description("The product type for the section"))));
	}
	
	@Test
	void sectionGetExample() throws Exception {
		Map<String, Object> section = new HashMap<>();
		section.put("size", 1);
		section.put("productType", "Clothing");

		String sectionLocation = this.mockMvc
				.perform(
						post("/api/sections").contentType(MediaTypes.HAL_JSON).content(
								this.objectMapper.writeValueAsString(section)))
				.andExpect(status().isCreated()).andReturn().getResponse()
				.getHeader("Location");

		this.mockMvc.perform(get(sectionLocation))
			.andExpect(status().isOk())
			.andExpect(jsonPath("size", is(section.get("size"))))
			.andExpect(jsonPath("productType", is(section.get("productType"))))
			.andDo(document("section-get-example",
					responseFields(
							fieldWithPath("size").description("The size of the section"),
							fieldWithPath("productType").description("The product type of the section"),
							subsectionWithPath("_links").description("<<resources_section_links,Links>> to other resources"))));
	}
	
	@Test
	void sectionUpdateExample() throws Exception {
		Map<String, Object> section = new HashMap<>();
		section.put("size", 1);
		section.put("productType", "Clothing");

		String sectionLocation = this.mockMvc
				.perform(
						post("/api/sections").contentType(MediaTypes.HAL_JSON).content(
								this.objectMapper.writeValueAsString(section)))
				.andExpect(status().isCreated()).andReturn().getResponse()
				.getHeader("Location");

		Map<String, Object> sectionUpdate = new HashMap<String, Object>();
		sectionUpdate.put("size", 2);
		sectionUpdate.put("productType", "MeatProduct");

		this.mockMvc.perform(
				patch(sectionLocation).contentType(MediaTypes.HAL_JSON).content(
						this.objectMapper.writeValueAsString(sectionUpdate)))
				.andExpect(status().isNoContent())
				.andDo(document("section-update-example",
						requestFields(
								fieldWithPath("size").description("The size of the section"),
								fieldWithPath("productType").description("The product type of the section"))));
	}
	
	@Test
	void productUpdateExample() throws Exception {
		Map<String, Object> section = new HashMap<>();
		section.put("size", 1);
		section.put("productType", "Clothing");

		String sectionLocation = this.mockMvc
				.perform(
						post("/api/sections").contentType(MediaTypes.HAL_JSON).content(
								this.objectMapper.writeValueAsString(section)))
				.andExpect(status().isCreated()).andReturn().getResponse()
				.getHeader("Location");

		Map<String, Object> product = new HashMap<String, Object>();
		product.put("color", "blue");
		product.put("containerType", "Cardboard");
		product.put("count", 100);
		product.put("isFragile", true);
		product.put("lote", "L-01");
		product.put("price", 10.0);
		product.put("size", 10);
		product.put("section", sectionLocation);
		
		String productLocation = this.mockMvc
				.perform(
						post("/api/products").contentType(MediaTypes.HAL_JSON).content(
									this.objectMapper.writeValueAsString(product)))
				.andExpect(status().isCreated()).andReturn().getResponse()
				.getHeader("Location");
		
		
		Map<String, Object> productUpdate = new HashMap<String, Object>();
		productUpdate.put("color", "red");
		productUpdate.put("count", 200);

		this.mockMvc.perform(
				patch(productLocation).contentType(MediaTypes.HAL_JSON).content(
						this.objectMapper.writeValueAsString(productUpdate)))
				.andExpect(status().isNoContent())
				.andDo(document("product-update-example",
						requestFields(
								fieldWithPath("color").description("The color of the product"),
								fieldWithPath("count").description("The count of the product"))));
	}
	
	@Test
	void productDeleteExample() throws Exception {
		Map<String, Object> section = new HashMap<>();
		section.put("size", 1);
		section.put("productType", "Clothing");

		String sectionLocation = this.mockMvc
				.perform(
						post("/api/sections").contentType(MediaTypes.HAL_JSON).content(
								this.objectMapper.writeValueAsString(section)))
				.andExpect(status().isCreated()).andReturn().getResponse()
				.getHeader("Location");

		Map<String, Object> product = new HashMap<String, Object>();
		product.put("color", "blue");
		product.put("containerType", "Cardboard");
		product.put("count", 100);
		product.put("isFragile", true);
		product.put("lote", "L-01");
		product.put("price", 10.0);
		product.put("size", 10);
		product.put("section", sectionLocation);
		
		String productLocation = this.mockMvc
				.perform(
						post("/api/products").contentType(MediaTypes.HAL_JSON).content(
									this.objectMapper.writeValueAsString(product)))
				.andExpect(status().isCreated()).andReturn().getResponse()
				.getHeader("Location");
		
		
		this.mockMvc.perform(
			    delete(productLocation))
			    .andExpect(status().isNoContent())
			    .andDo(document("product-delete-example"));

	}
	
	@Test
	void sectionDeleteExample() throws Exception {
		
		this.productRepository.deleteAll();
		
		Map<String, Object> section = new HashMap<>();
		section.put("size", 1);
		section.put("productType", "Clothing");

		String sectionLocation = this.mockMvc
				.perform(
						post("/api/sections").contentType(MediaTypes.HAL_JSON).content(
								this.objectMapper.writeValueAsString(section)))
				.andExpect(status().isCreated()).andReturn().getResponse()
				.getHeader("Location");

		//normal method is not allowed
		
		this.mockMvc.perform(
			    delete(sectionLocation))
			    .andExpect(status().isMethodNotAllowed());
			    //.andDo(document("product-delete-example"));

		//you must use other endpoint
		sectionLocation = sectionLocation.replaceFirst("sections", "section");
		
		this.mockMvc.perform(
			    delete(sectionLocation))
			    .andExpect(status().isOk())
			    .andDo(document("section-delete-example"));
	}
}
