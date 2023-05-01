package com.fastcampus.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fastcampus.dto.security.BoardPrincipal;

@EnableJpaAuditing
@Configuration
public class JpaConfig {
	
	@Bean
	public AuditorAware<String> auditorAware(){
		return () -> Optional.ofNullable(SecurityContextHolder.getContext())	//security정보를 모두 들고 있는 클래스에서 context를 들고옴
				.map(SecurityContext::getAuthentication)	//authentication 정보를 가져옴
				.filter(Authentication::isAuthenticated)	//인증 되었는지 확인(로그인 되었는지)
				.map(Authentication::getPrincipal)		//로그인 정보 꺼내옴
				.map(BoardPrincipal.class::cast)		//내가 구현한 principal(userdetail로만듦) 정보
				.map(BoardPrincipal::getUsername);		//user정보 가져옴
	}
}
