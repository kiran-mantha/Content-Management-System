package com.example.cms.utility;

import org.springframework.stereotype.Component;

@Component
public class ResponseStructure {

	private int statuscode;
	private String message;
	private Object data;
	public int getStatuscode() {
		return statuscode;
	}
	public ResponseStructure setStatuscode(int statuscode) {
		this.statuscode = statuscode;
		return this;
	}
	public String getMessage() {
		return message;
	}
	public ResponseStructure setMessage(String message) {
		this.message = message;
		return this;
	}
	public Object getData() {
		return data;
	}
	public ResponseStructure setData(Object data) {
		this.data = data;
		return this;
	}
	
}
