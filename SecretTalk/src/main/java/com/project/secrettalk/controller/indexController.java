package com.project.secrettalk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.secrettalk.config.Role;
import com.project.secrettalk.model.User;
import com.project.secrettalk.repo.UserRepository;

@Controller
public class indexController {
	
	@Value("${spring.security.oauth2.client.registration.google.client-id}")
	private String googlekey;
	
	
	@Autowired
	private UserRepository userrepo;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping("/index")
	public String index(Model model) {
		System.out.println("hi");
		return "index";
	}
	
	@GetMapping("/user")
	public String user() {
		return "user";
	}
	
	@GetMapping("/admin")
	public String admin() {
		return "admin";
	}
	
	@GetMapping("/manager")
	public String manager() {
		return "manager";
	}
	
	@GetMapping("/loginForm")
	public String loginForm() {
		return "loginForm";
	}
	@GetMapping("/joinForm")	//회원가입 페이지
	public String joinForm() {
		return "joinForm";
	}
	
	@PostMapping("/join") //회원가입 프로세스 
	public String join(User user) {
		String rawPassword = user.getPwd();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		
		User entity = User.builder()
				.email(user.getEmail())
				.name(user.getName())
				.age(user.getAge())
				.pwd(encPassword)
				.role(Role.USER.getKey())
				.build();
		
		//Date 기록안됨
//		user.setRole(Role.USER.getKey());
//		String rawPassword = user.getPwd();
//		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
//		user.setPwd(encPassword);
//		System.out.println(user);
//		userrepo.save(user);
		System.out.println(entity);
		userrepo.save(entity);
		return "redirect:/loginForm";
	}

	@GetMapping("/login/oauth2/code/google") // 구글 인증 후 소셜 authorization server에 엑세스 토큰 요청
	public String googleauthredirect(@RequestParam(value="code") String code, @RequestParam(value="scope") String scope, @RequestParam(value="authuser") String authuser) {
		System.out.println(code+" : "+scope +" : "+ authuser);
		
		
		return "";
	}
}
