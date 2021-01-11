package com.example.demo.exception;

public class SpringRedditException extends RuntimeException {

	private static final long serialVersionUID = 8437107612321116665L;

	public SpringRedditException(String exMessage) {
		super(exMessage);
	}

}
