package com.project.secrettalk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.project.secrettalk.service.CustomOAuth2UserService;

import lombok.RequiredArgsConstructor;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity	//스프링 시큐리티 필터가 스프링 필터 체인에 등록
public class SecurityConfig {

	@Autowired
	private final CustomOAuth2UserService customOauth2UserService;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers("/user/**").authenticated()
			.antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
			.anyRequest().permitAll()
		.and()
			.formLogin()
			.loginPage("/loginForm")
		.and()
			.oauth2Login()
			.userInfoEndpoint()
			.userService(customOauth2UserService);
		return http.build();
	}
	
	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
	
}
