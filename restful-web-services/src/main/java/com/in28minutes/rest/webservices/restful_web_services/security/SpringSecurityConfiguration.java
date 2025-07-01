package com.in28minutes.rest.webservices.restful_web_services.security;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfiguration {

	// 스프링 시큐리티 필터체인 오버라이드
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
//		1. 모든요청이 인증되야함 (모든요청에 자격증명이 첨부되야함)
		http.authorizeHttpRequests(
				auth -> auth.anyRequest().authenticated()
				);
//		2.만약 요청이 인증되지 않았다면 기본값으로 웹페이지가 뜸.
		http.httpBasic(withDefaults());
//		3. CSRF -> POST, PUT  .. 등 이렇게 필터체인해서, 요청에 영향을줌
		http.csrf().disable();
		
		return http.build();
	}
}
