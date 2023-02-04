package com.oracle.training.repository;


import org.springframework.data.repository.CrudRepository;
import com.oracle.training.model.Orders;

public interface OrderRepo extends CrudRepository<Orders,Integer> {
	Orders findById(int id);

}
