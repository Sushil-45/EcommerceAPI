package com.ecommerce.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ecommerce.entity.Product;
import com.ecommerce.enums.AppConstants;
import com.ecommerce.payload.ProductDto;
import com.ecommerce.requestPayload.ProductRequest;
import com.ecommerce.responsePayload.GenericResponseMessageBean;
import com.ecommerce.responsePayload.ProductSaveResponse;

public interface ProductServices {

	public Page<Product> getAllProducts(int page, int size, String fetchFilterBy, String fetchFilterByValue,
			String sortBy, String sortByValue);

	public List<Product> getAll();

	public ProductRequest saveNewOrProduct(ProductRequest productRequest, AppConstants create);

	public GenericResponseMessageBean fetchById(long id);

	public List<Product> getProductsByFilter(String fetchFilterBy, String fetchFilterByValue);

	public GenericResponseMessageBean deleteProduct(long id);


}
