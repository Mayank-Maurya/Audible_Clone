package com.customer.exception;

public class UserException extends Exception{
	
	public UserException(String message){
		super(message);
	}
	
	public String getMessage() {
		return super.getMessage();
	}

}
