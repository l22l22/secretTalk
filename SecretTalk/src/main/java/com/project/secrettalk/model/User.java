package com.project.secrettalk.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.datetime.DateTimeFormatAnnotationFormatterFactory;

import com.project.secrettalk.config.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Data
@NoArgsConstructor
@Table(name = "secrettalk_users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int sid;
	
	private String email;
	private String name;
	private String age;
	private String pwd;
	private String last_receive_date;
	private String role;
	private LocalDateTime createDate;
	
	@Builder
	public User(String email,String name, String age, String role, String pwd) {
		this.name=name;
		this.email=email;
		this.age=age;
		this.pwd=pwd;
		this.role=role;
		this.createDate = LocalDateTime.now();
	}

	public User update(String name,  String email) {
        this.name = name;
        this.email = email;
        this.createDate = LocalDateTime.now();
        this.age = "100";

        return this;
    }
	
}
