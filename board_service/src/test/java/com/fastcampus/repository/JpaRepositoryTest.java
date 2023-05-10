package com.fastcampus.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.fastcampus.domain.Article;
import com.fastcampus.domain.Hashtag;
import com.fastcampus.domain.UserAccount;

@DisplayName("JPA 연결 테스트")
@Import(JpaRepositoryTest.TestJpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {
	
	private final ArticleRepository articleRepository;
	private final ArticleCommentRepository articleCommentRepository;
	private final UserAccountRepository userAccountRepository;
	private final HashtagRepository hashtagRepository;

	public JpaRepositoryTest(
			@Autowired ArticleRepository articleRepository,
			@Autowired ArticleCommentRepository articleCommentRepository,
			@Autowired UserAccountRepository userAccountRepository,
			@Autowired HashtagRepository hashtagRepository
	) {
		this.articleRepository = articleRepository;
		this.articleCommentRepository = articleCommentRepository;
		this.userAccountRepository = userAccountRepository;
		this.hashtagRepository = hashtagRepository;
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
			.hasSize(123);	// classpath:resources/data.sql 참조
		
	}
	
	@DisplayName("insert 테스트")
	@Test
	void givenTestData_whenInserting_thenWorksFine() {
		//Given
		//기존의 갯수를 insert 한 다음에 숫자 1개가 늘었다
		long previousCount = articleRepository.count();
		UserAccount userAccount = userAccountRepository.save(UserAccount.of("newUno", "pw", null, null, null));
		Article article = Article.of(userAccount, "new article", "new content");
		article.addHashtags(Set.of(Hashtag.of("spring")));
		
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
		Hashtag updatedHashtag = Hashtag.of("springboot");
        article.clearHashtags();
        article.addHashtags(Set.of(updatedHashtag));
		
		//When
		Article savedArticle = articleRepository.saveAndFlush(article);	//롤백을 생각한 flush
		
		//Then
		 assertThat(savedArticle.getHashtags())
			         .hasSize(1)
			         .extracting("hashtagName", String.class)
			                 .containsExactly(updatedHashtag.getHashtagName());
		
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
	
	
	@DisplayName("[Querydsl] 전체 hashtag 리스트에서 이름만 조회하기")
	@Test
	void givenNothing_whenQueryingHashtags_thenReturnsHashtagNames() {
		//Given
		
		//When
		List<String> hashtagNames = hashtagRepository.findAllHashtagNames();
		
		//Then
		assertThat(hashtagNames).hasSize(19);
	}
	
	
	@DisplayName("[Querydsl] hashtag로 페이징된 게시글 검색하기")
	@Test
	void givenHashtagNamesAndPageable_whenQueryingArticles_thenReturnsArticlePage() {
		//Given
		List<String> hashtagNames = List.of("blue", "crimson", "fuscia");
		Pageable pageable = PageRequest.of(0, 5, Sort.by(
				Sort.Order.desc("hashtags.hashtagName"),
				Sort.Order.asc("title")
		));
		
		//When
		Page<Article> articlePage = articleRepository.findByHashtagNames(hashtagNames, pageable);
		
		//Then
		assertThat(articlePage.getContent()).hasSize(pageable.getPageSize());
		assertThat(articlePage.getContent().get(0).getTitle()).isEqualTo("Fusce posuere felis sed lacus.");
		assertThat(articlePage.getContent().get(0).getHashtags())
					.extracting("hashtagName", String.class).containsExactly("fuscia");
		assertThat(articlePage.getTotalElements()).isEqualTo(17);
		assertThat(articlePage.getTotalPages()).isEqualTo(4);
	}
	
	//테스트할때만 등록
	@EnableJpaAuditing
	@TestConfiguration
	public static class TestJpaConfig {	//security와 무관하게
		@Bean
		public AuditorAware<String> auditorAware(){
			return () -> Optional.of("uno");
		}
	}
	
}