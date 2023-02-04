package com.oracle.training.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.oracle.training.model.Item;
import com.oracle.training.model.Order;
import com.oracle.training.model.Payment;

public interface OrderRepo extends CrudRepository<Order,Integer> {
	Order findById(int id);

}
