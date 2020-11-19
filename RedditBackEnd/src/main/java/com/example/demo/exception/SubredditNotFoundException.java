package com.example.demo.exception;

public class SubredditNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SubredditNotFoundException(String excMessage) {
		super(excMessage);
	}

}
