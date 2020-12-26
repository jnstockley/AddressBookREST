package com.github.jnstockley.addressbookrest;

import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
public class AddressException extends Exception {
	
	private HttpStatus http;
	private String message;
	
	public HttpStatus getHttp() {
		return this.http;
	}
	
	public String getMessage() {
		return this.message;
	}

	public AddressException(HttpStatus http, String message) {
		this.http = http;
		this.message = message;
	}
	
}
