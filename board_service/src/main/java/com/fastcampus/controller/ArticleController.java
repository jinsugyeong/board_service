package com.fastcampus.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fastcampus.domain.constant.FormStatus;
import com.fastcampus.domain.constant.SearchType;
import com.fastcampus.dto.UserAccountDto;
import com.fastcampus.dto.request.ArticleRequest;
import com.fastcampus.dto.response.ArticleResponse;
import com.fastcampus.dto.response.ArticleWithCommentResponse;
import com.fastcampus.service.ArticleService;
import com.fastcampus.service.PaginationService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/articles")
@Controller
public class ArticleController {
	
	private final ArticleService articleService;
	private final PaginationService paginationService;
	
	//게시글 리스트 조회
	@GetMapping
	public String articles(
			@RequestParam(required = false) SearchType searchType,
			@RequestParam(required = false) String searchValue,
			@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
			ModelMap map
	) {
		Page<ArticleResponse> articles = articleService.searchArticles(searchType, searchValue, pageable).map(ArticleResponse::from);
		List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), articles.getTotalPages());
		
		map.addAttribute("articles", articles);
		map.addAttribute("paginationBarNumbers", barNumbers);
		map.addAttribute("searchTypes", SearchType.values());
		
		return "articles/index";
	}
	
	//게시글 단건(상세) 조회
	@GetMapping("/{articleId}")
	public String article(@PathVariable Long articleId, ModelMap map) {
		ArticleWithCommentResponse article = ArticleWithCommentResponse.from(articleService.getArticleWithcommets(articleId));
		
		map.addAttribute("article", article);
		map.addAttribute("articleComments", article.articleCommentResponse());
		map.addAttribute("totalCount", articleService.getArticleCount());
		
		return "articles/detail";
	}
	
	
	//해시태그 검색
	@GetMapping("/search-hashtag")
	public String searchArticleHashtag(
			@RequestParam(required = false) String searchValue,
			@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
			ModelMap map
	) {
		
		Page<ArticleResponse> articles = articleService.searchArticlesViaHashtag(searchValue, pageable).map(ArticleResponse::from);
		List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), articles.getTotalPages());
		List<String> hashtags = articleService.getHashtags();
		
		map.addAttribute("articles", articles);
		map.addAttribute("hashtags", hashtags);
		map.addAttribute("paginationBarNumbers", barNumbers);
		map.addAttribute("searchType", SearchType.HASHTAG);
		
		return "articles/search-hashtag";
	}
	
	
	//게시글 작성 페이지
	@GetMapping("/form")
    public String articleForm(ModelMap map) {
        map.addAttribute("formStatus", FormStatus.CREATE);

        return "articles/form";
    }
	
	
	//게시글 작성
	@PostMapping("/form")
	public String postNewArticle(ArticleRequest articleRequest) {
		
		articleService.saveArticle(articleRequest.toDto(UserAccountDto.of(
				"sugyeong", "password", "jsg@email.com", "gang", "memo", null, null, null, null	//인증 정보를 넣어줘야함
		)));
		
		return "redirect:/articles";
	}
	
	
	//게시글 수정 페이지
	@GetMapping("/{articleId}/form")
    public String updateArticleForm(@PathVariable Long articleId, ModelMap map) {
        ArticleResponse article = ArticleResponse.from(articleService.getArticle(articleId));

        map.addAttribute("article", article);
        map.addAttribute("formStatus", FormStatus.UPDATE);

        return "articles/form";
    }

	
	
	//게시글 수정
	@PostMapping("/{articleId}/form")
	public String updateArticle(@PathVariable Long articleId, ArticleRequest articleRequest) {
		articleService.updateArticle(articleId, articleRequest.toDto(UserAccountDto.of(
				"sugyeong", "password", "jsg@email.com", "gang", "memo", null, null, null, null	//인증 정보를 넣어줘야함
		)));
		
		return "redirect:/articles/" + articleId;
	}
	
	
	//게시글 삭제
	@PostMapping("{articleId}/delete")
	public String deleteArticle(@PathVariable Long articleId) {
		articleService.deleteArticle(articleId);	//인증 정보 넣어줘야 함		
		
		return "redirect:/articles";
	}
	
}
