package com.fastcampus.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.fastcampus.config.JpaConfig;
import com.fastcampus.domain.Article;
import com.fastcampus.domain.UserAccount;

//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) > application 설정에서 테스트db 전역설정 안했을 시 사용해야하는 어노테이션
//@ActiveProfiles("testdb")

@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {
	
	private final ArticleRepository articleRepository;
	private final ArticleCommentRepository articleCommentRepository;
	private final UserAccountRepository userAccountRepository;

	public JpaRepositoryTest(
			@Autowired ArticleRepository articleRepository,
			@Autowired ArticleCommentRepository articleCommentRepository,
			@Autowired UserAccountRepository userAccountRepository
	) {
		this.articleRepository = articleRepository;
		this.articleCommentRepository = articleCommentRepository;
		this.userAccountRepository = userAccountRepository;
	}
	
	@DisplayName("select테스트")
	@Test
	void givenTestData_whenSelecting_thenWorksFine() {
		//Given
		
		//When
		List<Article> articles = articleRepository.findAll();
		
		//Then
		assertThat(articles)
			.isNotNull()
			.hasSize(123);
		
	}
	
	@DisplayName("insert 테스트")
	@Test
	void givenTestData_whenInserting_thenWorksFine() {
		//Given
		//기존의 갯수를 insert 한 다음에 숫자 1개가 늘었다
		long previousCount = articleRepository.count();
		UserAccount userAccount = userAccountRepository.save(UserAccount.of("sg", "pw", null, null, null));
		Article article = Article.of(userAccount, "new article", "new content", "#spring");
		
		//When
		articleRepository.save(article);
		
		//Then
		assertThat(articleRepository.count()).isEqualTo(previousCount + 1);
		
	}
	
	@DisplayName("update 테스트")
	@Test
	void givenTestData_whenUpdating_thenWorksFine() {
		//Given
		//기존에 데이터가 있고, 그것을 수정했을 때 쿼리 관찰 필요
		Article article = articleRepository.findById(1L).orElseThrow();
		String updateHashtag = "#springboot";
		article.setHashtag(updateHashtag);
		
		//When
		Article savedArticle = articleRepository.saveAndFlush(article);	//롤백을 생각한 flush
		
		//Then
		assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag", updateHashtag);
		
	}
	
	@DisplayName("delete 테스트")
	@Test
	void givenTestData_whenDeleting_thenWorksFine() {
		//Given
		//값 하나 꺼내서 지우기(게시글 삭제하면 댓글도 삭제되는지)
		Article article = articleRepository.findById(1L).orElseThrow();
		long previousArticleCount = articleRepository.count();
		long previousArticleCommentCount = articleCommentRepository.count();
		int deletedCommentsSize = article.getArticleComments().size();
		
		//When
		articleRepository.delete(article);
		
		//Then
		assertThat(articleRepository.count()).isEqualTo(previousArticleCount - 1);
		assertThat(articleCommentRepository.count()).isEqualTo(previousArticleCommentCount - deletedCommentsSize);
		
	}
	
}