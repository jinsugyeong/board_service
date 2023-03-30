package com.fastcampus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fastcampus.domain.ArticleComment;

public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {

}
