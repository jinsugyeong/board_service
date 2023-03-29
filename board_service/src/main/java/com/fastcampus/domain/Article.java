package com.fastcampus.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Table(indexes = {
		@Index(columnList = "title"),
		@Index(columnList = "hashtag"),
		@Index(columnList = "createdAt"),
		@Index(columnList = "createdBy")
})
@Entity
public class Article {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Setter @Column(nullable = false) private String title;	//자동으로 설정되지 않는 부분만 setter
	@Setter @Column(nullable = false, length= 10000) private String content;
	
	@Setter private String hashtag;
	
	@CreatedDate @Column(nullable = false) private LocalDateTime createdAt;
	@CreatedBy @Column(nullable = false, length= 100) private String createdBy;
	@LastModifiedDate @Column(nullable = false) private LocalDateTime modifiedAt;
	@LastModifiedBy @Column(nullable = false, length= 100) private String modifiedBy;
	
	//생성자
	protected Article(String title, String content, String hashtag) {
		this.title = title;
		this.content = content;
		this.hashtag = hashtag;
	}
	
	public static Article of(String title, String content, String hashtag) {
		return new Article(title, content, hashtag);
	}
	
	//리스트 비교
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Article article)) return false;	//패턴어쩌고
		return id != null && id.equals(article.id);	//id가 null일때(insert 전) 조건 추가-> 새로만든 엔티티는 무조건 다른 값으로 취급
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
}