package com.fastcampus.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fastcampus.domain.type.SearchType;
import com.fastcampus.dto.response.ArticleCommentResponse;
import com.fastcampus.dto.response.ArticleResponse;
import com.fastcampus.dto.response.ArticleWithCommentResponse;
import com.fastcampus.service.ArticleService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/articles")
@Controller
public class ArticleController {
	
	private final ArticleService articleService;
	
	//게시글 리스트 조회
	@GetMapping
	public String articles(
			@RequestParam(required = false) SearchType searchType,
			@RequestParam(required = false) String searchValue,
			@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
			ModelMap map
	) {
		map.addAttribute("articles", articleService.searchArticles(searchType, searchValue, pageable).map(ArticleResponse::from));
		
		return "articles/index";
	}
	
	//게시글 단건(상세) 조회
	@GetMapping("/{articleId}")
	public String article(@PathVariable Long articleId, ModelMap map) {
		ArticleWithCommentResponse article = ArticleWithCommentResponse.from(articleService.getArticle(articleId));
		
		map.addAttribute("article", article);
		map.addAttribute("articleComments", article.articleCommentResponse());
		
		return "articles/detail";
	}
}
