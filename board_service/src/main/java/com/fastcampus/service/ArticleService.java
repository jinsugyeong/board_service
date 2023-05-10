package com.fastcampus.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fastcampus.domain.Article;
import com.fastcampus.domain.UserAccount;
import com.fastcampus.domain.constant.SearchType;
import com.fastcampus.dto.ArticleDto;
import com.fastcampus.dto.ArticleWithCommentsDto;
import com.fastcampus.repository.ArticleRepository;
import com.fastcampus.repository.UserAccountRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {
	private final ArticleRepository articleRepository;
    private final UserAccountRepository userAccountRepository;
	
	//게시글 리스트 조회
	@Transactional(readOnly = true)
	public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable) {
		//검색어 없이 게시글을 검색하면
		if (searchKeyword == null || searchKeyword.isBlank()) {
            return articleRepository.findAll(pageable).map(ArticleDto::from);
        }
		
		//검색어 타입, 검색어 지정하여 게시글 검색
		return switch (searchType) {
        case TITLE -> articleRepository.findByTitleContaining(searchKeyword, pageable).map(ArticleDto::from);
        case CONTENT -> articleRepository.findByContentContaining(searchKeyword, pageable).map(ArticleDto::from);
        case ID -> articleRepository.findByUserAccount_UserIdContaining(searchKeyword, pageable).map(ArticleDto::from);
        case NICKNAME -> articleRepository.findByUserAccount_NicknameContaining(searchKeyword, pageable).map(ArticleDto::from);
        case HASHTAG -> articleRepository.findByHashtagNames(
                Arrays.stream(searchKeyword.split(" ")).toList(),
                pageable
            )
                .map(ArticleDto::from);
	    };
	}
	
	//게시글 단건 조회(댓글 O)
	@Transactional(readOnly = true)
	public ArticleWithCommentsDto getArticleWithComments(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleWithCommentsDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
    }
	
	//게시글 단건 조회(댓글 X)
	@Transactional(readOnly = true)
	public ArticleDto getArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
    }

	//게시글 저장
	public void saveArticle(ArticleDto dto) {
        UserAccount userAccount = userAccountRepository.getReferenceById(dto.userAccountDto().userId());
        articleRepository.save(dto.toEntity(userAccount));
    }
	
	//게시글 수정
	public void updateArticle(Long articleId, ArticleDto dto) {
        try {
            Article article = articleRepository.getReferenceById(articleId);
            UserAccount userAccount = userAccountRepository.getReferenceById(dto.userAccountDto().userId());

            if (article.getUserAccount().equals(userAccount)) {
                if (dto.title() != null) { article.setTitle(dto.title()); }
                if (dto.content() != null) { article.setContent(dto.content()); }
            }
        } catch (EntityNotFoundException e) {
            log.warn("게시글 업데이트 실패. 게시글을 수정하는데 필요한 정보를 찾을 수 없습니다 - {}", e.getLocalizedMessage());
        }
    }
	
	//게시글 삭제
	public void deleteArticle(long articleId, String userId) {
        articleRepository.deleteByIdAndUserAccount_UserId(articleId, userId);
    }
	
	// 게시글 갯수 카운트
	public long getArticleCount() {
        return articleRepository.count();
    }

	
	//해시태그 사용하여 게시글 검색
	@Transactional(readOnly = true)
	public Page<ArticleDto> searchArticlesViaHashtag(String hashtag, Pageable pageable) {
		if (hashtag == null || hashtag.isBlank()) {
			//해시태그가 없다면 빈페이지
			return Page.empty(pageable);
		}
		
		return articleRepository.findByHashtagNames(null, pageable).map(ArticleDto::from);
	}
	
	//해시태그 리스트 조회
	public List<String> getHashtags() {
        return articleRepository.findAllDistinctHashtags();
    }
}