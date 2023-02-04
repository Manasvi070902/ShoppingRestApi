package com.oracle.training.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Payment {
//	 @JoinColumn(name = "orderID", referencedColumnName = "orderID")
//	 private Order order;
	@Id
	private int orderid;
	 private String status;
	
	public int getOrderid() {
		return orderid;
	}
	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Payment(Integer orderid, String status) {
	    this.orderid = orderid;
	    this.status = status;
	}
	public Payment()
	{
		
	}

}
