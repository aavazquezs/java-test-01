package com.avangenio.warehouse;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.avangenio.warehouse.controller.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SecurityTest {
	
	@Autowired
	private MockMvc api;
	
	@Test
	void anyoneCanViewPublicEndpoint() throws Exception {
		api.perform(get("/api/api-docs")).andExpect(status().isOk());
		
	}
	
	@Test
	void notLoggedIn_shouldNotSeeSecuredEndpoint() throws Exception {
		api.perform(get("/api/sections"))
			.andExpect(status().isUnauthorized());
	}
	
	@Test
	@WithMockUser
	void loggedIn_shouldSeeSecuredEndpoint() throws Exception {
		api.perform(get("/api/sections")).andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser
	void simpleUser_shouldNotSeeAdminEndpoint() throws Exception {
		api.perform(delete("/api/section"))
			.andExpect(status().isForbidden());
	}
}
