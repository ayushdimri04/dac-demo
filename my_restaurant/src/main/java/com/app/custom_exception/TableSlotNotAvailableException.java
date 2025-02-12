package com.app.custom_exception;

public class TableSlotNotAvailableException extends RuntimeException{

	public TableSlotNotAvailableException(String message) {
		super(message);
	}

}
