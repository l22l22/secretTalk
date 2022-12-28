package com.project.secrettalk.service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.project.secrettalk.model.SessionUser;
import com.project.secrettalk.model.User;
import com.project.secrettalk.oauth.OAuthAttributes;
import com.project.secrettalk.repo.UserRepository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

//@RequiredArgsConstructor 안됨
@AllArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest,OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oauth2User = delegate.loadUser(userRequest);
        
        //oauth 주체
        String registrationId = userRequest.getClientRegistration().getRegistrationId(); 
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
     //  System.out.printf("registrationId /"+registrationId+"@@ userNameAttributeName / "+registrationId+userNameAttributeName);
           
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oauth2User.getAttributes());
        Map<String, Object> kk = oauth2User.getAttributes();
        for (Map.Entry<String, Object> pair : kk.entrySet()) {
        	System.out.println(String.format("Key is: %s, Value is : %s", pair.getKey(), pair.getValue()));
        }    
        
        User user = saveOrUpdate(attributes);
        Map<String, Object> s = attributes.getAttributes();
        httpSession.setAttribute("user", new SessionUser(user));
        System.out.println("httpSession.getAttribute(\"user\") "+httpSession.getAttribute("user"));

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRole().toString())), attributes.getAttributes(), attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes){
    	User user = null;
        user = userRepository.findByEmail(attributes.getEmail()).map(entity -> entity.update(attributes.getName(),attributes.getEmail())).orElse(attributes.toEntity());
        System.out.println(user.getAge()+user.getLast_receive_date()+user.getCreateDate());
        return userRepository.save(user);
    }
}