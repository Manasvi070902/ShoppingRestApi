package com.oracle.training.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
	
public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
@Id
private int id;
private String name;
private String email;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}



}
