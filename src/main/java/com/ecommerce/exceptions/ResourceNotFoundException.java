package com.ecommerce.exceptions;

import java.util.List;

public class ResourceNotFoundException extends RuntimeException {

	private int statusCode;
	private String status;
	private String errorMessage;
	private String token;
	private String userId;
	private List<String> roles;

	public ResourceNotFoundException(int statusCode, String status, String errorMessage, String token, String userId,
			List<String> roles) {
		super();
		this.statusCode = statusCode;
		this.status = status;
		this.errorMessage = errorMessage;
		this.token = token;
		this.userId = userId;
		this.roles = roles;
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

}
