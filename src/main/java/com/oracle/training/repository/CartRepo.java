package com.oracle.training.repository;

import org.springframework.data.repository.CrudRepository;

import com.oracle.training.model.Cart;


public interface CartRepo extends CrudRepository<Cart,Integer>{
}