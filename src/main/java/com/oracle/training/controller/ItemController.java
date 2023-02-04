package com.oracle.training.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.training.model.Item;
import com.oracle.training.repository.ItemRepo;


@RestController
public class ItemController {
	@Autowired
	ItemRepo itemRepo;
	
	@GetMapping("/item/all")
	public ResponseEntity<?>getItems() {
		try {

		      List<Item> items = (List<Item>) itemRepo.findAll();

		      // if no item is found
		      if (items.isEmpty()) {
		        return new ResponseEntity<>("No items available",HttpStatus.NO_CONTENT);
		      }else {
		      return new ResponseEntity<>(items, HttpStatus.OK);
		      }
		    } 
		catch (Exception e) {
		      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		    }	
	}
	@GetMapping("/item/{id}")
	public ResponseEntity<?> searchItem(@PathVariable Integer id) {
		Optional<Item> itemData = itemRepo.findById(id);
		if (itemData.isPresent()) {
		      return new ResponseEntity<>(itemData, HttpStatus.OK);
		    } else {
		      return new ResponseEntity<>("Item Details Not Found", HttpStatus.NOT_FOUND);
		    }

	}
	@PostMapping("/item/add")
	public ResponseEntity<?> addItem(@RequestBody Item item) {
		 try {
			 Item itemData =  itemRepo.save(item);
		      return new ResponseEntity<>(itemData, HttpStatus.CREATED);
		    } catch (Exception e) {
		      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		    }

	}
	@DeleteMapping("/item/{id}")
	  public ResponseEntity<?> deleteItem(@PathVariable int id) {
	    try {
	    	itemRepo.deleteById(id);
	      return new ResponseEntity<>("Item deleted succesfully",HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	
}