package com.fastcampus.domain;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@Table(indexes = {
		@Index(columnList = "title"),
		@Index(columnList = "hashtag"),
		@Index(columnList = "createdAt"),
		@Index(columnList = "createdBy")
})
@Entity
public class Article extends AuditingFields{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Setter @ManyToOne(optional = false) @JoinColumn(name = "userId") private UserAccount userAccount;
	
	@Setter @Column(nullable = false) private String title;	//자동으로 설정되지 않는 부분만 setter
	@Setter @Column(nullable = false, length= 10000) private String content;
	
	@Setter private String hashtag;
	
	//JPA에 의해 사용되는 데이터
	@ToString.Exclude	//순환참조 끊기
    @OrderBy("createdAt DESC")//시간 순 정렬
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)//양방향 바인딩(게시글 삭제 시 댓글도 삭제됨)
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();
	
	protected Article() {}
	
	//생성자
	private Article(UserAccount userAccount, String title, String content, String hashtag) {
        this.userAccount = userAccount;
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static Article of(UserAccount userAccount, String title, String content, String hashtag) {
        return new Article(userAccount, title, content, hashtag);
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