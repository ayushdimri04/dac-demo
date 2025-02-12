package com.app.custom_exception;

public class RestaurantNotFoundException extends RuntimeException{
	public RestaurantNotFoundException(String message) {
		super(message);
	}
}
