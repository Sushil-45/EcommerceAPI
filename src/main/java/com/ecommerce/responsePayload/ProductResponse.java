package com.ecommerce.responsePayload;

import java.util.List;

import com.ecommerce.entity.Product;
import com.ecommerce.payload.ProductDto;

public class ProductResponse {

	private int statusCode;
	private String status;
	private String errorMessage;
	private List<Product> products;
	private Product product;

	public ProductResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductResponse(int statusCode, String status, String errorMessage, List<Product> productResponses) {
		super();
		this.statusCode = statusCode;
		this.status = status;
		this.errorMessage = errorMessage;
		this.products = productResponses;
	}
	
	

	public ProductResponse(int statusCode, String status, String errorMessage, List<Product> products,
			Product product) {
		super();
		this.statusCode = statusCode;
		this.status = status;
		this.errorMessage = errorMessage;
		this.products = products;
		this.product = product;
	}
	
	

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
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

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "ProductResponse [statusCode=" + statusCode + ", status=" + status + ", errorMessage=" + errorMessage
				+ ", products=" + products + ", product=" + product + "]";
	}

	

}
