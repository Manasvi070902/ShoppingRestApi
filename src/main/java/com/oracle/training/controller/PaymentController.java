package com.oracle.training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.oracle.training.model.Cart;
import com.oracle.training.model.Order;
import com.oracle.training.model.Payment;
import com.oracle.training.model.User;
import com.oracle.training.repository.CartItemsRepo;
import com.oracle.training.repository.CartRepo;
import com.oracle.training.repository.OrderRepo;
import com.oracle.training.repository.PaymentRepo;
import com.oracle.training.repository.UserRepo;
@RestController
public class PaymentController {
	
	@Autowired
	PaymentRepo payrepo;
	@Autowired
	OrderRepo orderepo;
	@Autowired
	CartRepo cartRepo;
	@PostMapping("/pay")
	public ResponseEntity<?> pay(@RequestBody ObjectNode obj) {
		 try {
			 int id=obj.get("id").asInt();
			 //Order table set paid
			 Order order=orderepo.findById(id);
			 order.setStatusCart("Paid");
			 orderepo.save(order);
//			 System.out.print(id);
			 Payment pay=new Payment();
			 pay.setOrderid(id);
			 pay.setStatus("Paid");
			 payrepo.save(pay);
			 //delete from cart after payment
			 Cart cart=cartRepo.findById(id);
			 cartRepo.delete(cart);
			 return new ResponseEntity<>(pay,HttpStatus.OK);
		    } catch (Exception e) {
		      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		    }

	}
	
	
	
}
