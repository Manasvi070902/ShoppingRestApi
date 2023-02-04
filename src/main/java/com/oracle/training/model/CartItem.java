package com.oracle.training.model;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class CartItem {
	@Id
	private int itemID;
	private int quantity;
	public int getItemID() {
		return itemID;
	}
	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	 public CartItem(int itemID, int quantity) {
		    this.itemID = itemID;
		    this.quantity = quantity;
		    }
    public CartItem(){
			
		   }
}
