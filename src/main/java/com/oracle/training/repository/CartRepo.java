package com.oracle.training.repository;

import org.springframework.data.repository.CrudRepository;

import com.oracle.training.model.Cart;
import com.oracle.training.model.Payment;


public interface CartRepo extends CrudRepository<Cart,Integer>
{
	Cart findById(int id);
}
