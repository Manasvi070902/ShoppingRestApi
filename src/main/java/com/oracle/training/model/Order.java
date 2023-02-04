package com.oracle.training.model;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

public class Order {
		@Id
		private int orderID;
		@OneToOne(cascade = CascadeType.MERGE)
	 @JoinColumn(name = "cartID", referencedColumnName = "card_id")
		private Cart cart;
		private String statusCart;
		public int getOrderID() {
			return orderID;
		}
		public void setOrderID(int orderID) {
			this.orderID = orderID;
		}
		public Cart getCart() {
			return cart;
		}
		public void setCart(Cart cart) {
			this.cart = cart;
		}
		public String getStatusCart() {
			return statusCart;
		}
		public void setStatusCart(String statusCart) {
			this.statusCart = statusCart;
		}
		
		
}
