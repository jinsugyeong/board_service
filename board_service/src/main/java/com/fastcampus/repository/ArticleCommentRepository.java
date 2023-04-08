package com.fastcampus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.fastcampus.domain.ArticleComment;
import com.fastcampus.domain.QArticleComment;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;

@RepositoryRestResource
public interface ArticleCommentRepository extends 
		JpaRepository<ArticleComment, Long>
		,QuerydslPredicateExecutor<ArticleComment>	//기본검색기능
		,QuerydslBinderCustomizer<QArticleComment>
{
	
	List<ArticleComment> findByArticle_Id(Long articleId);
	
	@Override
	default void customize(QuerydslBindings bindings, QArticleComment root) {
		bindings.excludeUnlistedProperties(true);
		bindings.including(root.content, root.createdAt, root.createdBy);
		bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
		bindings.bind(root.createdAt).first(DateTimeExpression::eq);
		bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
	}
}
