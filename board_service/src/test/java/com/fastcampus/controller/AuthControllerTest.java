package com.fastcampus.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.then;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fastcampus.config.TestSecurityConfig;
import com.fastcampus.service.ArticleService;
import com.fastcampus.service.PaginationService;

@DisplayName("View 컨트롤러 - 인증")
@Import(TestSecurityConfig.class)
@WebMvcTest
class AuthControllerTest {
	@Autowired
	private final MockMvc mvc;
	
	@MockBean private ArticleService articleService;
	@MockBean private PaginationService paginationService;
	
	public AuthControllerTest(@Autowired MockMvc mvc) {
		this.mvc = mvc;
	}
	
	@DisplayName("[view][GET] 게시글 리스트 (게시판) 페이지 - 정상 호출")
	@Test
	public void givenNothing_whenTryingToLogin_thenReturnsLoginView() throws Exception {
		//Given
		
		//When & Then
		mvc.perform(get("/login"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
		then(articleService).shouldHaveNoInteractions();
		then(paginationService).shouldHaveNoInteractions();
	}
}
