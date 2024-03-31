package com.example.cms.exception;

public class UserNotFoundByIdException extends RuntimeException {

	public UserNotFoundByIdException(String message) {
		super(message);
	}
}
