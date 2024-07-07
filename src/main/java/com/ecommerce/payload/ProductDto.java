package com.ecommerce.payload;

import com.ecommerce.entity.Product;

public class ProductDto {

	private long id;

	private String title;
	private String productDesc;
	private String productPrice;
	private String quantity;
	
	private long categoryId;
	
	public ProductDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductDto(long id, String title, String productDesc, String productPrice, String quantity,
			long categoryId) {
		super();
		this.id = id;
		this.title = title;
		this.productDesc = productDesc;
		this.productPrice = productPrice;
		this.quantity = quantity;
		this.categoryId = categoryId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	@Override
	public String toString() {
		return "ProductDto [id=" + id + ", title=" + title + ", productDesc=" + productDesc + ", productPrice="
				+ productPrice + ", quantity=" + quantity + ", categoryId=" + categoryId + "]";
	}
	
	

}
