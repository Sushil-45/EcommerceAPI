package com.ecommerce.exceptions;

public class ExistingProductFound extends RuntimeException {

	private int statusCode;
	private String status;
	private String message;
	private String errorMessage;

	public ExistingProductFound() {
		super();
	}
	public ExistingProductFound(int statusCode, String status, String message, String errorMessage) {
		super();
		this.statusCode = statusCode;
		this.status = status;
		this.message = message;
		this.errorMessage = errorMessage;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	

	
}
