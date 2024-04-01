package com.example.cms.exception;

public class UnAuthorizedException extends RuntimeException {

	public UnAuthorizedException(String message) {
		super(message);
	}
}
