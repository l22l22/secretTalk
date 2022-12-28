package com.project.secrettalk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.secrettalk.repo.UserRepository;

@Controller
@RequestMapping(value = "/oauth")
public class oauthController {
	
	
	@Value("${spring.security.oauth2.client.registration.google.client-id}")
	private String googlekey;
	
	
	@Autowired
	private UserRepository userrepo;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping("/OAuth2google")	//구글 인증
	public String google() {
		System.out.println("googlekey" + googlekey);
		return "redirect:http://localhost:8080/oauth2/authorization/google?redirect_uri=http://localhost:3000/oauth/redirect";
	}
	
	@GetMapping("/redirect")		// 최종 보여주고싶은 페이지
	public String googleauthredirect() {
		
		return "admin";
	}
}
