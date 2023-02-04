package com.oracle.training.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;

import com.oracle.training.model.User;

public interface UserRepo extends CrudRepository<User,Integer>{
	@Nullable
	User findByEmail(String email);
}
