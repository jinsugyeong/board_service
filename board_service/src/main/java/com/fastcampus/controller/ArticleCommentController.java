package com.fastcampus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fastcampus.dto.request.ArticleCommentRequest;
import com.fastcampus.service.ArticleCommentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/comments")
@Controller
public class ArticleCommentController {
	
	private final ArticleCommentService articleCommentService;
	
	
	//댓글 생성
	@PostMapping("/new")
	public String postNewArticleComment(ArticleCommentRequest articleCommentReqest) {
		
		return "redirect:/articels";
		
	}
	
	//댓글 삭제
	@PostMapping("/{commentId}/deletre")
	public String deleteArticleComment(@PathVariable Long commentId) {
		
		return "redirect:/articles";
	}
}
