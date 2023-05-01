package com.fastcampus.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fastcampus.dto.UserAccountDto;
import com.fastcampus.dto.request.ArticleCommentRequest;
import com.fastcampus.dto.security.BoardPrincipal;
import com.fastcampus.service.ArticleCommentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/comments")
@Controller
public class ArticleCommentController {
	
	private final ArticleCommentService articleCommentService;
	
	
	//댓글 생성
	@PostMapping ("/new")
    public String postNewArticleComment(
    		ArticleCommentRequest articleCommentRequest
    		, @AuthenticationPrincipal BoardPrincipal boardPrincipal
    ) {
        articleCommentService.saveArticleComment(articleCommentRequest.toDto(boardPrincipal.toDto()));

        return "redirect:/articles/" + articleCommentRequest.articleId();
    }
	
	//댓글 삭제
	@PostMapping ("/{commentId}/delete")
    public String deleteArticleComment(
    		@PathVariable Long commentId
    		, @AuthenticationPrincipal BoardPrincipal boardPrincipal
    		, Long articleId
    ) {
        articleCommentService.deleteArticleComment(commentId, boardPrincipal.getUsername());

        return "redirect:/articles/" + articleId;
    }

}
