package com.avangenio.warehouse;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.avangenio.warehouse.model.ProductType;
import com.avangenio.warehouse.model.Section;
import com.avangenio.warehouse.repository.SectionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = JavaTest01Application.class)
@AutoConfigureMockMvc
//@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class SectionRestControllerTest {
	
	@Autowired
    private MockMvc mvc;

    @Autowired
    private SectionRepository repository;
    
    private Section createTestSection() {
    	Section section = Section.builder()
				.size(100)
				.productType(ProductType.ElectricalAppliance)
				.build();
		Section savedSection = repository.save(section); 
		return savedSection;
    }
    
    @Test
    public void givenSections_whenGetSections_thenStatus200()
      throws Exception {

        createTestSection();

        mvc.perform(get("/api/sections")
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content()
          .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$..sections[0].size", hasItem(100)));
    }
    
    @Test
    public void testCreateSection() throws JsonProcessingException, Exception {
    	
    	Section section = Section.builder()
				.size(100)
				.productType(ProductType.ElectricalAppliance)
				.build();
    	
    	mvc.perform(post("/api/sections")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(section)))
                .andExpect(status().isCreated());
    	
    	assertThat(repository.count()).isEqualTo(3);
    	
    }
    
    @Test
    public void testUpdateSection() throws JsonProcessingException, Exception {
    	
    	createTestSection();
    	
    	List<Section> sections = repository.findAll();
    	
    	assertFalse(sections.isEmpty()); 
    	
    	Section section = sections.get(0);
    	
    	Map<String, Object> updates = new HashMap<>();
        updates.put("size", 1);
        updates.put("productType", "Clothing");
    	
    	mvc.perform(patch("/api/sections/{id}", section.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updates)))
                .andExpect(status().isNoContent());
    	
    }
}
