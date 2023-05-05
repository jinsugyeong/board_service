package com.fastcampus.repository.querydsl;

import java.util.List;

import org.springframework.data.domain.Pageable;

public interface ArticleRepositoryCustom {
	List<String> findAllDistinctHashtags(String string, Pageable pageable);
}