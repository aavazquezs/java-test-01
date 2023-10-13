package com.avangenio.warehouse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.avangenio.warehouse.model.ContainerType;
import com.avangenio.warehouse.model.Product;
import com.avangenio.warehouse.model.ProductType;
import com.avangenio.warehouse.model.Section;
import com.avangenio.warehouse.repository.ProductRepository;
import com.avangenio.warehouse.repository.SectionRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class SectionRepositoryTest {
	
	@Autowired 
	private SectionRepository repository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Test
	public void saveSectionTest() {
		Section section = Section.builder()
				.size(100)
				.productType(ProductType.ElectricalAppliance)
				.build();
		Section savedSection = repository.save(section);
		assertNotNull(savedSection);
		assertNotNull(savedSection.getId());
		log.info("Saved Section id=" + savedSection.getId().toString());
	}
	
	@Test
	public void updateSectionTest() {
		Section section = Section.builder()
				.size(100)
				.productType(ProductType.ElectricalAppliance)
				.build();
		Section savedSection = repository.save(section);
		
		savedSection.setSize(50);
		savedSection.setProductType(ProductType.MeatProduct);
		
		Section updatedSection = repository.save(savedSection);
		
		assertEquals(50, updatedSection.getSize());
		assertEquals(ProductType.MeatProduct, updatedSection.getProductType());
	}
	
	@Test
	public void addingProductTest() {
		Section section = Section.builder()
				.size(100)
				.productType(ProductType.ElectricalAppliance)
				.build();
		Section savedSection = repository.save(section);
		
		Product product1 = Product.builder()
				.color("BLUE")
				.containerType(ContainerType.Cardboard)
				.count(0)
				.isFragile(true)
				.lote("L-01")
				.price(50.0)
				.size(50)
				.section(savedSection)
				.build();
		
		product1 = productRepository.save(product1);
		
		Product product2 = Product.builder()
				.color("RED")
				.containerType(ContainerType.Nylon)
				.count(10)
				.isFragile(false)
				.lote("L-02")
				.price(55.0)
				.size(10)
				.section(savedSection)
				.build();
		
		product2 = productRepository.save(product2);
		
		List<Product> products = new LinkedList<>();
		products.add(product1);
		products.add(product2);
		
		savedSection.setProducts(products);
		
		Section sectionWithProducts = repository.save(savedSection);
		
		assertEquals(2, sectionWithProducts.getProducts().size());
		
		assertNotNull(sectionWithProducts.getProducts().get(0));
		assertNotNull(sectionWithProducts.getProducts().get(1));
		
		assertNotNull(sectionWithProducts.getProducts().get(0).getId());
		assertNotNull(sectionWithProducts.getProducts().get(1).getId());
		
	}
	
	
	@Test
	public void findallSectionsTest() {
		
		this.productRepository.deleteAll();
		this.repository.deleteAll();
		
		List<Section> sections = repository.findAll();
		assertEquals(0, sections.size());
		Section section = Section.builder()
				.size(100)
				.productType(ProductType.ElectricalAppliance)
				.build();
		
		Section savedSection = repository.save(section);
		
		sections = repository.findAll();
		
		assertEquals(1, sections.size());
		
		Section section2 = Section.builder()
				.size(50)
				.productType(ProductType.MeatProduct)
				.build();
		Section savedSection2 = repository.save(section2);
		
		sections = repository.findAll();
		
		assertEquals(2, sections.size());
	}
	
	@Test
	public void deleteSectionTest() {
		
		this.productRepository.deleteAll();
		this.repository.deleteAll();
		
		Section section = Section.builder()
				.size(100)
				.productType(ProductType.ElectricalAppliance)
				.build();
		
		Section savedSection = repository.save(section);
		
		List<Section> sections = repository.findAll();
		
		assertEquals(1, sections.size());
		
		repository.delete(savedSection);
		
		sections = repository.findAll();
		
		assertEquals(0, sections.size());
		
	}
	
}
