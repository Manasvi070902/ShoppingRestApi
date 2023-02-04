package com.oracle.training.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;

import com.oracle.training.model.Cart;
import com.oracle.training.model.User;

public interface CartRepo extends CrudRepository<Cart,Integer>{
	@Nullable
	Cart findByUser(User user);
	Cart findById(int id);
}
