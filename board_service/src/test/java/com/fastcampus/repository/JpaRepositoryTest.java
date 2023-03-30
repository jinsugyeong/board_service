package com.fastcampus.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.fastcampus.config.JpaConfig;
import com.fastcampus.domain.Article;

@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {
	
	private final ArticleRepository articleRepository;
	private final ArticleCommentRepository articleCommentRepository;
	
	@Autowired
	public JpaRepositoryTest(ArticleRepository articleRepository, 
			ArticleCommentRepository articleCommentRepository
	) {
		this.articleRepository = articleRepository;
		this.articleCommentRepository = articleCommentRepository;
	}
	
	@DisplayName("select테스트")
	@Test
	void givenTestData_whenSelecting_thenWorksFine() {
		//Given
		
		
		//When
		List<Article> articles = articleRepository.findAll();
		
		//Then
		assertThat(articles)
			.isNotNull()
			.hasSize(123);
		
	}
	
	
}
