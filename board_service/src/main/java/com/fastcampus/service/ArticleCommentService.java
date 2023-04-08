package com.fastcampus.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fastcampus.dto.ArticleCommentDto;
import com.fastcampus.repository.ArticleCommentRepository;
import com.fastcampus.repository.ArticleRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleCommentService {
	
	private final ArticleRepository articleRepository;
	private final ArticleCommentRepository articleCommentRepository;
	
	@Transactional(readOnly = true)
	public List<ArticleCommentDto> searchArticleComments(Long articleId) {
		return List.of();
	}

	public void saveArticleComment(ArticleCommentDto of) {
		
	}
	
	public void updateArticleComment(ArticleCommentDto dto) {
    }

    public void deleteArticleComment(Long articleCommentId) {
    }
}
