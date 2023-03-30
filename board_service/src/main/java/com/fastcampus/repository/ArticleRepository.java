package com.fastcampus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fastcampus.domain.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}
