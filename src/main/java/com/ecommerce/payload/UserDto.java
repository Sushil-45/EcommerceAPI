package com.ecommerce.payload;

import java.util.Date;
import java.util.List;

public class UserDto {

	private Long id;
	private String userid;
	private String firstName;
	private String lastName;
	private String email;

	private String password;
	
	private List<String> roles;
	
	private String createdBy;
	private Date createdDates;
	private Date updatedate;
	private String phone;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> allRoles) {
		this.roles = allRoles;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDates() {
		return createdDates;
	}
	public void setCreatedDates(Date createdDates) {
		this.createdDates = createdDates;
	}
	public Date getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Override
	public String toString() {
		return "UserDto [id=" + id + ", userid=" + userid + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", password=" + password + ", roles=" + roles + ", createdBy=" + createdBy
				+ ", createdDates=" + createdDates + ", updatedate=" + updatedate + ", phone=" + phone + "]";
	}
	
	

	
	
}
