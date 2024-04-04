package com.example.cms.exception;

public class ScheduledTimeExpiredException extends RuntimeException {

	public ScheduledTimeExpiredException(String message) {
		super(message);
	}
}
