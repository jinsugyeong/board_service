package com.fastcampus.dto.request;

import java.util.Set;

import com.fastcampus.dto.ArticleDto;
import com.fastcampus.dto.HashtagDto;
import com.fastcampus.dto.UserAccountDto;

public record ArticleRequest(
		 String title
		,String content
) {
	public static ArticleRequest of(String title, String content) {
		return new ArticleRequest(title, content);
	}
	
	
	public ArticleDto toDto(UserAccountDto userAccountDto) {
		return toDto(userAccountDto, null);
    }

    public ArticleDto toDto(UserAccountDto userAccountDto, Set<HashtagDto> hashtagDtos) {
		return ArticleDto.of(
				userAccountDto
				, title
				, content
				, hashtagDtos
		);
	}
}
