package com.app.custom_exception;

public class PasswordDidNotMatchException extends RuntimeException{

	public PasswordDidNotMatchException(String message) {
		super(message);
	}

}
