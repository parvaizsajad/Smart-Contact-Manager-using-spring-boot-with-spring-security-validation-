package com.smart.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table( name = "Orders")
public class MyOrders {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long myorderId;
	private String orderId;
	private String amount;
	private String reciept;
	private String status;
	@ManyToOne
	private User user;
	private String paymentId;
	public Long getMyorderId() {
		return myorderId;
	}
	public void setMyorderId(Long myorderId) {
		this.myorderId = myorderId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getReciept() {
		return reciept;
	}
	public void setReciept(String reciept) {
		this.reciept = reciept;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	@Override
	public String toString() {
		return "MyOrders [myorderId=" + myorderId + ", orderId=" + orderId + ", amount=" + amount + ", reciept="
				+ reciept + ", status=" + status + ", user=" + user + ", paymentId=" + paymentId + "]";
	}
	
}
