package com.project.secrettalk.oauth;

import java.util.Map;

import com.project.secrettalk.config.Role;
import com.project.secrettalk.model.User;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes
            , String nameAttributeKey
            , String name
            , String email) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        System.out.println("OAuthAttributes name : "+name);
        this.email = email;
        System.out.println("OAuthAttributes email : "+email);
    }

    public static OAuthAttributes of(
            String registrationId
            , String userNameAttributeName
            , Map<String, Object> attributes) {

        return ofGoogle(userNameAttributeName, attributes);
    }

    public static OAuthAttributes ofGoogle(
            String userNameAttributeName
            , Map<String, Object> attributes) {

        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .role(Role.USER.getKey())
                .pwd(email)
                .build();
    }
}