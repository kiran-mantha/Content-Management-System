package com.example.cms.exception;

public class BlogAlreadyExistsByTitleException extends RuntimeException {

	public BlogAlreadyExistsByTitleException(String message) {
		super(message);
	}
}
