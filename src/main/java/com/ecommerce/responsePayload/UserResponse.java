package com.ecommerce.responsePayload;

import java.util.List;

import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;

public class UserResponse {
	
	private int statusCode;
	private String status;
	private String errorMessage;
	private List<User> users;
	private User user;
	
	public UserResponse(int statusCode, String status, String errorMessage, List<User> users, User user) {
		super();
		this.statusCode = statusCode;
		this.status = status;
		this.errorMessage = errorMessage;
		this.users = users;
		this.user = user;
	}
	public UserResponse() {
		super();
		// TODO Auto-generated constructor stub
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
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	

}
