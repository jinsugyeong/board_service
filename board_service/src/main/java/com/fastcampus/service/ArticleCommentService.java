package com.fastcampus.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fastcampus.dto.ArticleCommentDto;
import com.fastcampus.repository.ArticleCommentRepository;
import com.fastcampus.repository.ArticleRepository;
import com.fastcampus.domain.ArticleComment;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleCommentService {
	
	private final ArticleRepository articleRepository;
	private final ArticleCommentRepository articleCommentRepository;
	
	//댓글 리스트 조회
	@Transactional(readOnly = true)
	public List<ArticleCommentDto> searchArticleComments(Long articleId) {
		return articleCommentRepository.findByArticle_Id(articleId)
				.stream()
				.map(ArticleCommentDto::from)
				.toList();
	}
	
	
	//댓글 저장
	public void saveArticleComment(ArticleCommentDto dto) {
		try {
			articleCommentRepository.save(dto.toEntity(articleRepository.getReferenceById(dto.articleId())));
			
		} catch(EntityNotFoundException e) {
			log.warn("댓글 저장 실패. 댓글의 게시글을 찾을 수 없습니다 - dto: {}", dto);
		}
	}
	
	
	//댓글 수정
	public void updateArticleComment(ArticleCommentDto dto) {
		try {
			ArticleComment articleComment = articleCommentRepository.getReferenceById(dto.id());
			if(dto.content() != null) articleComment.setContent(dto.content()); 
			
		} catch(EntityNotFoundException e) {
			log.warn("댓글 업데이트 실패. 댓글을 찾을 수 없습니다. - dto: {}", dto);
		}
    }
	
	//댓글 삭제
    public void deleteArticleComment(Long articleCommentId) {
    	
    	articleCommentRepository.deleteById(articleCommentId);
    }
}
