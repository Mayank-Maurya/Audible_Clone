package com.audiocart.dto;

public class CartDTO {
	private Integer cartId;
	private Integer customerId;
	private Integer audioBookId;
	private String transactionId;
	private String type;
	
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public Integer getCartId() {
		return cartId;
	}
	public void setCartId(Integer cartId) {
		this.cartId = cartId;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Integer getAudioBookId() {
		return audioBookId;
	}
	public void setAudioBookId(Integer audioBookId) {
		this.audioBookId = audioBookId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
