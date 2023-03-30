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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Table(indexes = {
		@Index(columnList = "content"),
		@Index(columnList = "createdAt"),
		@Index(columnList = "createdBy")
})
@Entity
public class ArticleComment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Setter @ManyToOne(optional = false) private Article article;
	@Setter @Column(nullable = false, length = 500) private String tilte;
	
	private String content;
	private String hashtag;
	
	@CreatedDate @Column(nullable = false) private LocalDateTime createdAt;
	@CreatedBy @Column(nullable = false, length= 100) private String createdBy;
	@LastModifiedDate @Column(nullable = false) private LocalDateTime modifiedAt;
	@LastModifiedBy @Column(nullable = false, length= 100) private String modifiedBy;
	
	protected ArticleComment() {}
	
	private ArticleComment(Article article, String content) {
		this.article = article;
		this.content = content;
	}
	
	public static ArticleComment of(Article article, String content) {
		return new ArticleComment(article, content);
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ArticleComment that)) return false;	//패턴어쩌고
		return id != null && id.equals(that.id);	//id가 null일때(insert 전) 조건 추가-> 새로만든 엔티티는 무조건 다른 값으로 취급
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
