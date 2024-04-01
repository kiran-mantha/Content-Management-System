package com.example.cms.utility;

import org.springframework.stereotype.Component;

@Component
public class ErrorStructure<T> {

	private int errorCode;
	private String errorMessage;
	private T rootCause;
	public int getErrorCode() {
		return errorCode;
	}
	public ErrorStructure<T> setErrorCode(int errorCode) {
		this.errorCode = errorCode;
		return this;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public ErrorStructure<T> setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
		return this;
	}
	public Object getRootCause() {
		return rootCause;
	}
	public ErrorStructure<T> setRootCause(T rootCause) {
		this.rootCause = rootCause;
		return this;
	}
}
