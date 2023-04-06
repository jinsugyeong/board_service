package com.fastcampus.controller;

import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.fastcampus.config.SecurityConfig;

@Import(SecurityConfig.class)
@WebMvcTest(MainController.class)
class MainControllerTest {

	private final MockMvc mvc;
	
	public MainControllerTest(@Autowired MockMvc mvc) {
		this.mvc = mvc;
	}
	
	@Test
	void givenNothing_whenRequestingRootPage_thenRedirectsToArticlesPage() throws Exception {
		//Given
		
		//When & Then
		mvc.perform(get("/"))
			.andExpect(status().is3xxRedirection());
	}

}
