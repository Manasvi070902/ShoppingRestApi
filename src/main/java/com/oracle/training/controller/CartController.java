package com.oracle.training.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.oracle.training.model.Item;
import com.oracle.training.model.User;
import com.oracle.training.repository.CartRepo;
import com.oracle.training.repository.ItemRepo;
import com.oracle.training.repository.UserRepo;


@RestController
public class CartController {

	@Autowired
	CartRepo cartRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	ItemRepo itemRepo;
	
	boolean isItemPresent = true;
	
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
				 //check if sufficient items are present
				 cartItemList.forEach(cartItem -> {
						Item item = itemRepo.findById(cartItem.getItemID());
						if(item.getQty() < cartItem.getQuantity() ) {
							isItemPresent = false;
						}
				});
				//if present, create a new cart and decrease quantity of items
				 if(isItemPresent) {
					 //update item quantity
					 cartItemList.forEach(cartItem -> {
							Item item = itemRepo.findById(cartItem.getItemID());
							int newQuantity = item.getQty() - cartItem.getQuantity();
							
							item.setQty(newQuantity);
							itemRepo.save(item);
					});
					 cart = new Cart();
					 cart.setUser(user);
					 cart.setCartItems(cartItemList);
					 cartRepo.save(cart);
				     return new ResponseEntity<>(cart, HttpStatus.CREATED);
				 }else {
					 return new ResponseEntity<>("Item quantity not available", HttpStatus.NOT_FOUND);
				 }
			 }else {
				 //cart exists
				 return new ResponseEntity<>("Cart already exists", HttpStatus.FOUND);
			 } 
			  
		    } catch (Exception e) {
		    	System.out.print(e);
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
			 
			 //check if items are present
			 newcartItems.forEach(cartItem -> {
				 Item item = itemRepo.findById(cartItem.getItemID());
				 if(item.getQty() < cartItem.getQuantity() ) {
						isItemPresent = false;
					}
			});
			 
			 if(isItemPresent) {
			 User user = userRepo.findByEmail(email);
			 
			 Cart oldCart = cartRepo.findByUser(user);
			if(oldCart == null) {
				return new ResponseEntity<>("No cart found", HttpStatus.NOT_FOUND);
			}
		     List<CartItem> cartItemList = oldCart.getCartItems();
		     
		   //update item quantity
		     newcartItems.forEach(cartItem -> {
					Item item = itemRepo.findById(cartItem.getItemID());
					int newQuantity = item.getQty() - cartItem.getQuantity();
					item.setQty(newQuantity);
					itemRepo.save(item);
			});
		     //merge and set new updated list
		     List<CartItem> finalCartItemList= Stream.concat(newcartItems.stream(), cartItemList.stream())
		     .collect(Collectors.groupingBy(item -> item.getItemID(),
                     Collectors.summingInt(CartItem::getQuantity)))
		     .entrySet()
		     .stream()
		     .map(entry -> new CartItem(entry.getKey(),entry.getValue())).collect(Collectors.toList()); 
		     oldCart.setCartItems(finalCartItemList);
			 cartRepo.save(oldCart);
			 
		      return new ResponseEntity<>(oldCart, HttpStatus.CREATED);
			 }else {
				 return new ResponseEntity<>("Item quantity not available", HttpStatus.NOT_FOUND);
			 }
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
