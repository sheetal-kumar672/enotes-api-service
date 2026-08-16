package org.springboot.integration;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springboot.dto.CategoryDto;
import org.springboot.dto.LoginRequest;
import org.springboot.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.criteria.Root;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;


@SpringBootTest
@ActiveProfiles("dev")
@AutoConfigureMockMvc
public class CategoryControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private JsonMapper objectMapper;
	
	private CategoryDto categoryDto = null;
	private Category category = null;
	
	private final String token = null;
	
	@BeforeEach
	public void initalize()
	{
		 categoryDto = CategoryDto.builder()
				 .id(null)
				.name("Java Notes")
				.description("java notes")
				.isActive(true).build();
		 
		 category = Category.builder()
				 .id(null)
				.name("Java Notes")
				.description("java notes")
				.isDeleted(false)
				.isActive(true).build();
	}	
	
	@Test
	public void testSaveCategory() throws JsonProcessingException, Exception
	{
		String token = generateToken("sheetalshakshi143@gmail.com", "1234567");
		
		mockMvc.perform(post("/api/category/save")
				 .contentType(MediaType.APPLICATION_JSON)
				 .content(objectMapper.writeValueAsString(categoryDto))
				 .header("Authorization", token)
				
				).andExpect(status().isCreated())
		         .andExpect(jsonPath("$.message").value("saved success"))
                 .andExpect(jsonPath("$.status").value("success"));
		         
	}
	
	@Test
	public String generateToken(String email,String password) throws JsonProcessingException, UnsupportedEncodingException, Exception
	{
		
		
		LoginRequest login = new LoginRequest();
		login.setEmail(email);
		login.setPassword(password);
		
		String response = mockMvc.perform(post("/api/auth/login")
				 .contentType(MediaType.APPLICATION_JSON)
				 .content(objectMapper.writeValueAsString(login))
				 .header("Authorization", token)
				 
				 ).andExpect(status().isOk())
		          .andReturn()
		          .getResponse()
		          .getContentAsString();
		
		JsonNode root = objectMapper.readTree(response);
		String token = root.path("data").path("token").asText();
		return "Bearer "+token;
	}
}
