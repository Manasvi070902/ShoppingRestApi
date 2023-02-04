package com.oracle.training.controller;

import java.util.List;
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
import com.oracle.training.model.Cart;
import com.oracle.training.model.CartItem;
import com.oracle.training.model.Item;
import com.oracle.training.model.Orders;
import com.oracle.training.model.User;
import com.oracle.training.repository.CartRepo;
import com.oracle.training.repository.ItemRepo;
import com.oracle.training.repository.OrderRepo;
import com.oracle.training.repository.UserRepo;

@RestController
public class OrdersController {
	@Autowired
	OrderRepo orderepo;
	@Autowired
	CartRepo cartRepo;
	@Autowired
	UserRepo userRepo;
	@Autowired
	ItemRepo itemRepo;
	@Autowired
	OrderRepo orderRepo;
	
	float amount = 0;
	
	@GetMapping("/orders/{id}")
	public ResponseEntity<?> getOrderDetails(@PathVariable Integer id) {
		try {
		Optional<Orders> orderData = orderepo.findById(id);
		if (orderData.isPresent()) {
		      return new ResponseEntity<>(orderData, HttpStatus.OK);
		    } else {
		      return new ResponseEntity<>("No Order Found", HttpStatus.NOT_FOUND);
		    }
		}catch(Exception e) {
			return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@PostMapping("/placeOrder")
	public ResponseEntity<?> placeOrder(@RequestBody ObjectNode obj) {
		 try {
			 String email = obj.get("email").asText();
			User user = userRepo.findByEmail(email);
			Cart cartData = cartRepo.findByUser(user);
			
			if (cartData != null) {
				
				Orders order = new Orders();
				List<CartItem> cartItemList = cartData.getCartItems();
				
				cartItemList.forEach(cartItem -> {
					Item item = itemRepo.findById(cartItem.getItemID());
					amount = item.getPrice() * cartItem.getQuantity();
					
				});
				order.setCart(cartData);
				order.setOrderStatus("Pending");
				order.setAmount(amount);
				
				orderRepo.save(order);
				return new ResponseEntity<>(order, HttpStatus.OK);
				    
			} else {
				  return new ResponseEntity<>("Cart Not Found", HttpStatus.NOT_FOUND);
				    
			}
		    } catch (Exception e) {
		      return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		    }

	}
	
}
