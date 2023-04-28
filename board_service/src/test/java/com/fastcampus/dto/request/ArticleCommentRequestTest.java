package com.fastcampus.dto.request;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.fastcampus.config.SecurityConfig;
import com.fastcampus.controller.ArticleCommentController;
import com.fastcampus.util.FormDataEncoder;

@DisplayName("View 컨트롤러 - 댓글")
@Import({SecurityConfig.class, FormDataEncoder.class})
@WebMvcTest(ArticleCommentController.class)
class ArticleCommentRequestTest {
	
	private final MockMvc mvc;
	private final FormDataEncoder formDataEncoder;
	
	 
	
	public ArticleCommentRequestTest(
			@Autowired MockMvc mvc
			,@Autowired FormDataEncoder formDataEncoder
	) {
		this.mvc = mvc;
		this.formDataEncoder = formDataEncoder;
	}



	@Test
	void test() {
		fail("Not yet implemented");
	}

}
