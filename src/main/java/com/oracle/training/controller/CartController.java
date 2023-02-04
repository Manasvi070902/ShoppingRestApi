package com.oracle.training.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.oracle.training.model.Cart;
import com.oracle.training.model.CartItem;
import com.oracle.training.model.User;
import com.oracle.training.repository.CartRepo;
import com.oracle.training.repository.UserRepo;


@RestController
public class CartController {

	@Autowired
	CartRepo cartRepo;
	UserRepo userRepo;
	
	@PostMapping("/addToCart")
	public ResponseEntity<?> addToCart(@RequestBody ObjectNode cartObj) {
		 try {
			 String email = cartObj.get("email").asText();
			 ObjectMapper mapper = new ObjectMapper();
			 ObjectReader reader = mapper.readerFor(new TypeReference<List<CartItem>>() {
			 });
			 JsonNode cartItems = cartObj.get("cartItems");
			 List<CartItem> cartItemList = reader.readValue(cartItems);
			 
			 
			 User user = userRepo.findByEmail(email);
			 Cart cart = new Cart();
			 cart.setUser(user);
			 cart.setCartItems(cartItemList);
			 System.out.print(cart.getUser());
			 cartRepo.save(cart);
			 
		      return new ResponseEntity<>(cart, HttpStatus.CREATED);
		    } catch (Exception e) {
		      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		    }

	}
}
