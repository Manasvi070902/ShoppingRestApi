package com.oracle.training.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	@Autowired
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
			 
			 Cart cart = cartRepo.findByUser(user);
			 if(cart == null) {
				 //create a new cart
				 cart = new Cart();
				 cart.setUser(user);
				 cart.setCartItems(cartItemList);
				 
			 }else {
				 //cart exists
				 return new ResponseEntity<>("Cart already exists", HttpStatus.FOUND);
			 } 
			  cartRepo.save(cart);
		      return new ResponseEntity<>(cart, HttpStatus.CREATED);
		    } catch (Exception e) {
		      return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		    }

	}
	@PutMapping("/updateCart")
	public ResponseEntity<?> updateCart(@RequestBody ObjectNode cartObj) {
		 try {
			 String email = cartObj.get("email").asText();
			 ObjectMapper mapper = new ObjectMapper();
			 ObjectReader reader = mapper.readerFor(new TypeReference<List<CartItem>>() {
			 });
			 JsonNode cartItems = cartObj.get("cartItems");
			 List<CartItem> newcartItems = reader.readValue(cartItems);
			 
			 User user = userRepo.findByEmail(email);
			 
			 Cart oldCart = cartRepo.findByUser(user);
			
		     List<CartItem> cartItemList = oldCart.getCartItems();
		     //merge and set new updated list
		     cartItemList.addAll(newcartItems);
		     oldCart.setCartItems(cartItemList);
			 System.out.print(oldCart.getCartItems());
			 cartRepo.save(oldCart);
			 
		      return new ResponseEntity<>(oldCart, HttpStatus.CREATED);
		    } catch (Exception e) {
		    	System.out.print(e);
		      return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		    }

	}
	@GetMapping("/getCart")
	public ResponseEntity<?> findUser(@RequestBody ObjectNode obj) {
		try {
		String email = obj.get("email").asText();
		User user = userRepo.findByEmail(email);
		Cart cartData = cartRepo.findByUser(user);
		if (cartData != null) {
		      return new ResponseEntity<>(cartData, HttpStatus.OK);
		    } else {
		      return new ResponseEntity<>("Cart Not Found", HttpStatus.NOT_FOUND);
		    }
		}catch (Exception e) {
	    	System.out.print(e);
		      return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
