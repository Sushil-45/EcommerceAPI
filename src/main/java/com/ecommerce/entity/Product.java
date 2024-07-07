package com.ecommerce.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class Product {

	@Id

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String title;
	private String productDesc;
	private double productPrice;
	private long quantity;
	private String productName;
	private Date updatedate;
	private Date createdby;
	private String userid;
	private String updatedUserId;
	
	

	public String getUpdatedUserId() {
		return updatedUserId;
	}

	public void setUpdatedUserId(String updatedUserId) {
		this.updatedUserId = updatedUserId;
	}

	public Product() {

	}

	public Product(long id, String title, String productDesc, double productPrice, long quantity,
			String productName) {
		super();
		this.id = id;
		this.title = title;
		this.productDesc = productDesc;
		this.productPrice = productPrice;
		this.quantity = quantity;
		this.productName = productName;
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

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public Date getCreatedby() {
		return createdby;
	}

	public void setCreatedby(Date createdby) {
		this.createdby = createdby;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", title=" + title + ", productDesc=" + productDesc + ", productPrice="
				+ productPrice + ", quantity=" + quantity + ", productName=" + productName + "]";
	}

}
