package com.project.secrettalk.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
	USER("ROLE_USER","손님"),
	MANAGER("ROLE_MANAGER","매니저"),
	ADMIN("ROLE_ADMIN","관리자");
	
	private final String key;
	private final String title;
}
