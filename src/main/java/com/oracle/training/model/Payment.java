package com.oracle.training.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Payment {
	   @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       private int id;
		
		@OneToOne(cascade = CascadeType.MERGE)
	    @JoinColumn(name = "order_id", referencedColumnName = "id")
	    private Orders order;
		
		private String status;

		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public Orders getOrder() {
			return order;
		}
		public void setOrder(Orders order) {
			this.order = order;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		
	   public Payment(Orders order, String status) {
	    this.order = order;
	    this.status = status;
	    }
	   public Payment(){
		
	   }

}