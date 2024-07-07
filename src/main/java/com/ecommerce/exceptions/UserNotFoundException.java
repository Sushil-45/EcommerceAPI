package com.ecommerce.exceptions;

import java.util.List;

public class UserNotFoundException extends RuntimeException {

	private int statusCode;
	private String status;
	private String errorMessage;
	private String userId;
	public UserNotFoundException(int statusCode, String status, String errorMessage, String userId) {
		super();
		this.statusCode = statusCode;
		this.status = status;
		this.errorMessage = errorMessage;
		this.userId = userId;
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
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "UserNotFoundException [statusCode=" + statusCode + ", status=" + status + ", errorMessage="
				+ errorMessage + ", userId=" + userId + "]";
	}

	
	
	
}
