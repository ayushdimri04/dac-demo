package com.app.custom_exception;

public class OrderCancellationException extends RuntimeException{

	public OrderCancellationException(String message) {
		super(message);
	}

}
