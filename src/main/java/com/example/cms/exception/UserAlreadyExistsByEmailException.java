package com.example.cms.exception;

public class UserAlreadyExistsByEmailException extends RuntimeException {

	public UserAlreadyExistsByEmailException(String message) {
		super(message);
	}
}
