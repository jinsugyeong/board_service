package com.fastcampus.domain;

import java.time.LocalDateTime;

public class Articlecomment {
	private Long id;
	private Article article;
	private String tilte;
	private String content;
	private String hashtag;
	
	private LocalDateTime createdAt;
	private String createdBy;
	private LocalDateTime modifiedAt;
	private String modifiedBy;
}
