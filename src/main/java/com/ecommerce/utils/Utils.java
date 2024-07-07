package com.ecommerce.utils;

import org.springframework.stereotype.Component;

@Component
public class Utils {

	public int fetchPages(int page) {
		switch (page) {
		case 0:
			return 0;
		default:
			return page;
		}
	}

	public int fetchSize(int size) {
		switch (size) {
		case 0:
			return 10;
		default:
			return size;
		}
	}

	public String fetchFilterBy(String filterBy) {

		if (filterBy != null && !filterBy.isEmpty() && !filterBy.trim().isEmpty()) {

			return filterBy;
		}

		else {
			return "title";
		}

	}

	public String fetchSortBy(String sortBy) {
		
		if(sortBy!=null && !sortBy.trim().isEmpty() && !sortBy.isEmpty()) {
			return sortBy;
		}
		else {
			return "id";
		}
		
	}

	public String fetchSortByAscDsc(String sortByValue) {
		
		if(sortByValue!=null && !sortByValue.trim().isEmpty() && !sortByValue.isEmpty()) {
			return sortByValue;
		}
		else {
			return "asc";
		}
	}

}
