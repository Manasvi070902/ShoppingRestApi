package com.oracle.training.repository;

import org.springframework.data.repository.CrudRepository;

import com.oracle.training.model.Item;


public interface ItemRepo extends CrudRepository<Item,Integer>{
	Item findById(int id);
}