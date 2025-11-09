package com.solarcom.sites_management.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.solarcom.sites_management.domain.User;

public interface UserRepository extends MongoRepository<User, String> {
	  Optional<User> findByLogin(String login);
	  
}

