package com.fastcampus.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import com.fastcampus.domain.type.SearchType;
import com.fastcampus.dto.ArticleDto;
import com.fastcampus.repository.ArticleRepository;

@DisplayName("비즈니스 로직 - 게시글")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {
	
	@InjectMocks private ArticleService sut;
	@Mock private ArticleRepository articleRepository;
	
	@DisplayName("게시글을 검색하면, 게시글 리스트를 반환한다.")
	@Test
	void givenSearchParameters_whenSearchArticles_thenReturnsArticleList() {
		//Given
		
		//When
		Page<ArticleDto> articles = sut.searchArticles(SearchType.TITLE, "search keyword");	//제목, 본문, ID, 닉네임, 해시태그
		
		//Then
		assertThat(articles).isNotNull();
	}
	
	@DisplayName("게시글을 조회하면, 게시글을 반환한다.")
	@Test
	void givenId_whenSearchArticle_thenReturnsArticle() {
		//Given
		
		//When
		ArticleDto article = sut.searchArticle(1L);
		
		//Then
		assertThat(article).isNotNull();
	}
}
