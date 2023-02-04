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

import com.oracle.training.model.User;
import com.oracle.training.repository.UserRepo;

@RestController
public class UserController {
	@Autowired
	UserRepo repo;
	
	@GetMapping("/users/all")
	public ResponseEntity<?>getUsers() {
		try {

		      List<User> users = (List<User>) repo.findAll();

		      // if no user found
		      if (users.isEmpty()) {
		        return new ResponseEntity<>("No Users available",HttpStatus.NO_CONTENT);
		      }else {
		      return new ResponseEntity<>(users, HttpStatus.OK);
		      }
		    } 
		catch (Exception e) {
		      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		    }	
	}
	@GetMapping("/users/{id}")
	public ResponseEntity<?> findUser(@PathVariable Integer id) {
		Optional<User> userData = repo.findById(id);
		if (userData.isPresent()) {
		      return new ResponseEntity<>(userData, HttpStatus.OK);
		    } else {
		      return new ResponseEntity<>("User Details Not Found", HttpStatus.NOT_FOUND);
		    }

	}
	@PostMapping("/users/add")
	public ResponseEntity<?> addUser(@RequestBody User user) {
		 try {
			 System.out.print(user.getName());
			 User userData =  repo.save(user);
		      return new ResponseEntity<>(userData, HttpStatus.CREATED);
		    } catch (Exception e) {
		      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		    }

	}
	@DeleteMapping("/users/{id}")
	  public ResponseEntity<?> deleteTutorial(@PathVariable int id) {
	    try {
	      repo.deleteById(id);
	      return new ResponseEntity<>("User deleted succesfully",HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	
}