package com.oracle.training.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Item {
@Id
private int itemID;
private String itemName;
private float price;
private int qty;
public int getItemID() {
	return itemID;
}
public void setItemID(int itemID) {
	this.itemID = itemID;
}
public String getItemName() {
	return itemName;
}
public void setItemName(String itemName) {
	this.itemName = itemName;
}
public float getPrice() {
	return price;
}
public void setPrice(float price) {
	this.price = price;
}
public int getQty() {
	return qty;
}
public void setQty(int qty) {
	this.qty = qty;
}
}
