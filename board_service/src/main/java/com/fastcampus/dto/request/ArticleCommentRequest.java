package com.fastcampus.dto.request;

import com.fastcampus.dto.ArticleCommentDto;
import com.fastcampus.dto.UserAccountDto;

public record ArticleCommentRequest(Long articleId, String content) {
	
	public static ArticleCommentRequest of(Long articleId, String content) {
		return new ArticleCommentRequest(articleId, content);
	}
	
	public ArticleCommentDto toDto(UserAccountDto userAccountDto) {
		return ArticleCommentDto.of(
			articleId,
			userAccountDto,
			content
		);
	}
}
