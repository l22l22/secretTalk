package com.project.secrettalk.model;

public class SessionUser {
	
	
	private String name;
	private String email;
	
	public SessionUser(User user) {
		this.name = user.getName();
		this.email = user.getEmail();
	}
}
