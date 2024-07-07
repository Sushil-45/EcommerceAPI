package com.ecommerce.exceptions;

import org.springframework.http.HttpStatus;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ResponseStatusSaveException extends RuntimeException {

	private HttpStatus status;
	private String reason;
	private Throwable cause;

	public ResponseStatusSaveException(HttpStatus status, String reason, Throwable cause) {
		super(reason, cause);
		this.status = status;
		this.reason = reason;
		this.cause = cause;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public String getReason() {
		return reason;
	}

	public Throwable getCause() {
		return cause;
	}
}
