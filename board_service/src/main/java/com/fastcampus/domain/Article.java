package com.fastcampus.domain;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@Table(indexes = {
		@Index(columnList = "title"),
		@Index(columnList = "createdAt"),
		@Index(columnList = "createdBy")
})
@Entity
public class Article extends AuditingFields{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Setter 
	@JoinColumn(name = "userId") 
	@ManyToOne(optional = false) 
	private UserAccount userAccount;	//유저 정보(ID)
	
	@Setter @Column(nullable = false) private String title;	//자동으로 설정되지 않는 부분만 setter
	@Setter @Column(nullable = false, length= 10000) private String content;
	
	@ToString.Exclude
	@JoinTable(
			name = "article_hashtag"
			,joinColumns = @JoinColumn(name = "articleId")
			,inverseJoinColumns = @JoinColumn(name = "hashtagId")
	)
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<Hashtag> hashtags = new LinkedHashSet<>();
	
	//JPA에 의해 사용되는 데이터
	@ToString.Exclude	//순환참조 끊기
    @OrderBy("createdAt DESC")//시간 순 정렬
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)//양방향 바인딩(게시글 삭제 시 댓글도 삭제됨)
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();
	
	protected Article() {}
	
	//생성자
	private Article(UserAccount userAccount, String title, String content) {
        this.userAccount = userAccount;
        this.title = title;
        this.content = content;
    }

    public static Article of(UserAccount userAccount, String title, String content) {
        return new Article(userAccount, title, content);
    }
    
    public void addHashtag(Hashtag hashtag) {
    	this.getHashtags().add(hashtag);
    }
    
    public void addHashtags(Collection<Hashtag> hashtags) {
    	this.getHashtags().addAll(hashtags);
    }
    
    public void clearHashtags() {
    	this.getHashtags().clear();
    }
	
	//리스트 비교
	@Override
	public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article that)) return false;
        return this.getId() != null && this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
	
}