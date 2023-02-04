package com.oracle.training.repository;

import org.springframework.data.repository.CrudRepository;

import com.oracle.training.model.CartItem;


public interface CartItemsRepo extends CrudRepository<CartItem,Integer>{
}