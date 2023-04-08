package com.fastcampus.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fastcampus.domain.type.SearchType;
import com.fastcampus.dto.ArticleDto;
import com.fastcampus.dto.ArticleWithCommentsDto;
import com.fastcampus.repository.ArticleRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {
	private final ArticleRepository articleRepository;
	
	@Transactional(readOnly = true)
	public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable){
		return Page.empty();
	}
	
	@Transactional(readOnly = true)
	public ArticleWithCommentsDto getArticle(Long articleId) {
		return null;
	}

	public void saveArticle(ArticleDto dto) {
		
	}

	public void updateArticle(ArticleDto dto) {
		
	}

	public void deleteArticle(long articleId) {
		
	}
}
