package com.oracle.training.repository;

import org.springframework.data.repository.CrudRepository;

import com.oracle.training.model.User;

public interface UserRepo extends CrudRepository<User,Integer>{
	
	User findByEmail(String email);
}
