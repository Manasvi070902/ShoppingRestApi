package com.oracle.training.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;

import com.oracle.training.model.Item;


public interface ItemRepo extends CrudRepository<Item,Integer>{
	@Nullable
	Item findById(int id);
}