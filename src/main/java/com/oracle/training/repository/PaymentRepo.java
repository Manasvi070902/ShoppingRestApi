package com.oracle.training.repository;

import org.springframework.data.repository.CrudRepository;

import com.oracle.training.model.Payment;

public interface PaymentRepo extends CrudRepository<Payment,Integer>{
	
	Payment findById(int id);

}
