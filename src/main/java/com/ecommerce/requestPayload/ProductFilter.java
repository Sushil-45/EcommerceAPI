package com.ecommerce.requestPayload;

public class ProductFilter {
	
	private Integer page;
	private Integer size;
	private String filterBy;
	private String filterByValue;
	private String sortBy;
	private String sortByValue;
	public ProductFilter(Integer page, Integer size, String filterBy, String filterByValue, String sortBy, String sortByValue) {
		super();
		this.page = page;
		this.size = size;
		this.filterBy = filterBy;
		this.filterByValue = filterByValue;
		this.sortBy = sortBy;
		this.sortByValue = sortByValue;
	}
	public ProductFilter() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getFilterBy() {
		return filterBy;
	}
	public void setFilterBy(String filterBy) {
		this.filterBy = filterBy;
	}
	public String getFilterByValue() {
		return filterByValue;
	}
	public void setFilterByValue(String filterByValue) {
		this.filterByValue = filterByValue;
	}
	public String getSortBy() {
		return sortBy;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	public String getSortByValue() {
		return sortByValue;
	}
	public void setSortByValue(String sortByValue) {
		this.sortByValue = sortByValue;
	}
	@Override
	public String toString() {
		return "ProductFilter [page=" + page + ", size=" + size + ", filterBy=" + filterBy + ", filterByValue="
				+ filterByValue + ", sortBy=" + sortBy + ", sortByValue=" + sortByValue + "]";
	}
	
	
	

}
