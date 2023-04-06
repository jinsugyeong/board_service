package com.fastcampus.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fastcampus.domain.type.SearchType;
import com.fastcampus.dto.ArticleDto;
import com.fastcampus.repository.ArticleRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {
	private final ArticleRepository articleRepository;
	
	@Transactional(readOnly = true)
	public Page<ArticleDto> searchArticles(SearchType title, String search_keyword){
		return Page.empty();
	}
	
	@Transactional(readOnly = true)
	public ArticleDto searchArticle(long l) {
		return null;
	}
}
