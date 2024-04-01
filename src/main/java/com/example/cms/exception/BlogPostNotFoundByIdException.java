package com.example.cms.exception;

public class BlogPostNotFoundByIdException extends RuntimeException {

	public BlogPostNotFoundByIdException(String message) {
		super(message);
	}
}
