package com.oracle.training.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;
//import com.oracle.training.model.Cart;
import com.oracle.training.model.Orders;
import com.oracle.training.model.Payment;
import com.oracle.training.repository.CartRepo;
import com.oracle.training.repository.OrderRepo;
import com.oracle.training.repository.PaymentRepo;

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
			 Orders order=orderepo.findById(id);
			 order.setOrderStatus("Paid");
			 orderepo.save(order);
//			 System.out.print(id);
			 Payment pay=new Payment();
			 pay.setOrder(order);
			 pay.setStatus("Paid");
			 payrepo.save(pay);
			 //delete from cart after payment
//			 Cart cart=cartRepo.findById(id);
//			 cartRepo.delete(cart);
			 return new ResponseEntity<>(pay,HttpStatus.OK);
		    } catch (Exception e) {
		      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		    }

	}
	@GetMapping("/pay/{id}")
	public ResponseEntity<?> getPaymentDetails(@PathVariable Integer id) {
		try {
		Optional<Payment> paymentData = payrepo.findById(id);
		if (paymentData.isPresent()) {
		      return new ResponseEntity<>(paymentData, HttpStatus.OK);
		    } else {
		      return new ResponseEntity<>("No Payment Found", HttpStatus.NOT_FOUND);
		    }
		}
		catch (Exception e) {
		      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		    }

	}
	
	
}
