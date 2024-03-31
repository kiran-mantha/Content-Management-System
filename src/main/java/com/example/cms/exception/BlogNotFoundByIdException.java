package com.example.cms.exception;

public class BlogNotFoundByIdException extends RuntimeException {

	public BlogNotFoundByIdException(String message){
		super(message);
	}
}
