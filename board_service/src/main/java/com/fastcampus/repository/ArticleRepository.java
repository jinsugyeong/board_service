package com.fastcampus.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.fastcampus.domain.Article;
import com.fastcampus.domain.QArticle;
import com.fastcampus.repository.querydsl.ArticleRepositoryCustom;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;

@RepositoryRestResource
public interface ArticleRepository extends 
		JpaRepository<Article,Long>
		,ArticleRepositoryCustom
		,QuerydslPredicateExecutor<Article>	//기본검색기능
		,QuerydslBinderCustomizer<QArticle>
{
	Page<Article> findByTitleContaining(String title, Pageable pageable);
	Page<Article> findByContentContaining(String content, Pageable pageable);
	Page<Article> findByUserAccount_UserIdContaining(String userId, Pageable pageable);
	Page<Article> findByUserAccount_NicknameContaining(String title, Pageable pageable);
	
	void deleteByIdAndUserAccount_UserId(Long articleId, String userId);
	
	@Override
	default void customize(QuerydslBindings bindings, QArticle root) {
	//default 메서드로 인터페이스를 구현
		
		bindings.excludeUnlistedProperties(true);	//선택적인 필드만 검색하고싶음
		bindings.including(root.title, root.content, root.hashtag, root.createdAt, root.createdBy);	//제목,	본문, 해시태그, 생성일시, 글쓴이
		
		//(path, value) -> path.eq(value)  > SimpeExpression::eq >
		//bindings.bind(root.title).first(StringExpression::likeIgnoreCase);	//like '${value}'	와일드카드x 부분검사불가
		bindings.bind(root.title).first(StringExpression::containsIgnoreCase);	//like '%${value}%'
		bindings.bind(root.content).first(StringExpression::containsIgnoreCase);	//like '%${value}%'
		//bindings.bind(root.hashtags.any().hashtagName).first(StringExpression::containsIgnoreCase);
		bindings.bind(root.createdAt).first(DateTimeExpression::eq);	//like '%${value}%'
		bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);	//like '%${value}%'
	}
}
