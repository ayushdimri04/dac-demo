package com.app.custom_exception;

public class UniqueSlotException extends RuntimeException{

	public UniqueSlotException(String message) {
		super(message);
	}

}
