package com.fastcampus.domain;

import java.time.LocalDateTime;

public class Article {
	private Long id;
	private String tilte;
	private String content;
	private String hashtag;
	
	private LocalDateTime createdAt;
	private String createdBy;
	private LocalDateTime modifiedAt;
	private String modifiedBy;
}