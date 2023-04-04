package com.fastcampus.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fastcampus.domain.Article;

@EnableWebMvc
@RequestMapping("/articles")
@Controller
public class ArticleController {
	
	@GetMapping
	public String articles(ModelMap map) {
		map.addAttribute("articles", List.of());
		
		return "articles/index";
	}
	
	@GetMapping("/{articleId}")
	public String article(@PathVariable Long articleId, ModelMap map) {
		map.addAttribute("article", "");
		map.addAttribute("articleComments", List.of());
		
		return "articles/detail";
	}
}
