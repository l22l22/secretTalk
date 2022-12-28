package com.project.secrettalk.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.secrettalk.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	Optional<User> findByEmail(String email);
	
}
