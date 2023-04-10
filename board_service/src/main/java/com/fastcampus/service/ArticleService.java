package com.fastcampus.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fastcampus.domain.Article;
import com.fastcampus.domain.type.SearchType;
import com.fastcampus.dto.ArticleDto;
import com.fastcampus.dto.ArticleWithCommentsDto;
import com.fastcampus.repository.ArticleRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {
	private final ArticleRepository articleRepository;
	
	//게시글 리스트 조회
	@Transactional(readOnly = true)
	public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable){
		//검색어 없이 게시글을 검색하면
		if(searchKeyword == null || searchKeyword.isBlank()) {
			return articleRepository.findAll(pageable).map(ArticleDto::from);
		}
		
		//검색어 타입, 검색어 지정하여 게시글 검색
		return switch (searchType) {
			case TITLE -> articleRepository.findByTitleContaining(searchKeyword, pageable).map(ArticleDto::from);
			case CONTENT -> articleRepository.findByContentContaining(searchKeyword, pageable).map(ArticleDto::from);
			case ID -> articleRepository.findByUserAccount_UserIdContaining(searchKeyword, pageable).map(ArticleDto::from);
			case NICKNAME -> articleRepository.findByUserAccount_NicknameContaining(searchKeyword, pageable).map(ArticleDto::from);
			case HASHTAG -> articleRepository.findByHashtag("#"+searchKeyword, pageable).map(ArticleDto::from);
		};
		
	}
	
	//게시글 단건 조회
	@Transactional(readOnly = true)
	public ArticleWithCommentsDto getArticle(Long articleId) {
		return articleRepository.findById(articleId)
				.map(ArticleWithCommentsDto::from)
				.orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
	}

	//게시글 저장
	public void saveArticle(ArticleDto dto) {
		articleRepository.save(dto.toEntity());
	}
	
	//게시글 수정
	public void updateArticle(ArticleDto dto) {
		try{Article article = articleRepository.getReferenceById(dto.id());
		
			if(dto.title() != null) article.setTitle(dto.title());
			if(dto.content() != null) article.setContent(dto.content());
			article.setHashtag(dto.hashtag());
		
		} catch(EntityNotFoundException e) {
			log.warn("게시글 업데이트 실패. 게시글을 찾을 수 없습니다. - dto: {}", dto);
		}
	}
	
	//게시글 삭제
	public void deleteArticle(long articleId) {
		articleRepository.deleteById(articleId);
	}
}
