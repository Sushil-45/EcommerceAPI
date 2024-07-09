package com.ecommerce.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ecommerce.entity.Product;
import com.ecommerce.enums.AppConstants;
import com.ecommerce.exceptions.ExistingProductFound;
import com.ecommerce.requestPayload.ProductFilter;
import com.ecommerce.requestPayload.ProductRequest;
import com.ecommerce.responsePayload.ProductResponse;
import com.ecommerce.responsePayload.ProductSaveResponse;
import com.ecommerce.services.ProductServices;
import com.ecommerce.utils.Utils;

@RestController
@RequestMapping("/api/product")
public class ProductControllers {

	@Autowired
	private Utils utils;

	@Autowired
	private ProductServices productService;

	@PostMapping("/getAllProducts")
	public ResponseEntity<ProductResponse> getAllProducts(@RequestBody ProductFilter productFilter) {
		try {
			int page = utils.fetchPages(productFilter.getPage());

			int size = utils.fetchSize(productFilter.getSize());

			String sortBy = utils.fetchSortBy(productFilter.getSortBy());
			String sortByValue = utils.fetchSortByAscDsc(productFilter.getSortByValue());

			String filterBy = utils.fetchFilterBy(productFilter.getFilterBy());
			String filterByValue = "";
			if (filterBy != null && !filterBy.isEmpty()) {
				filterByValue = utils.fetchFilterBy(productFilter.getFilterByValue());
			}

			Page<Product> products = productService.getAllProducts(page, size, filterBy, filterByValue, sortBy,
					sortByValue);
			ProductResponse response = new ProductResponse();
			if (products != null && products.getContent().size() > 0) {
				response.setStatusCode(HttpStatus.OK.value());
				response.setStatus("Success");
				response.setProducts(products.getContent());
			} else {
				response.setStatusCode(HttpStatus.OK.value());
				response.setStatus("Success");
				response.setErrorMessage("Data is empty either filterBy or filterByValue is wrong ");
				response.setProducts(new ArrayList<>());
			}
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			ProductResponse errorResponse = new ProductResponse();
			errorResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			errorResponse.setStatus("Error");
			errorResponse.setErrorMessage("Failed to fetch products: " + e.getMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/")
	public ResponseEntity<ProductResponse> getAll() {

		try {

			List<Product> products = productService.getAll();

			ProductResponse response = new ProductResponse();
			if (products != null) {
				response.setStatusCode(HttpStatus.OK.value());
				response.setStatus("Success");
				response.setProducts(products);
			} else {
				response.setStatusCode(HttpStatus.OK.value());
				response.setStatus("Success");
				response.setErrorMessage("Data is empty");
				response.setProducts(new ArrayList<>());
			}
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			ProductResponse errorResponse = new ProductResponse();
			errorResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			errorResponse.setStatus("Error");
			errorResponse.setErrorMessage("Failed to fetch products: " + e.getMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/saveNewProducts")
	public ResponseEntity<ProductSaveResponse> saveNewProduct(@RequestBody ProductRequest productRequest) {

		try {
			ProductSaveResponse successResponse = new ProductSaveResponse();
			ProductRequest products = productService.saveNewOrProduct(productRequest, AppConstants.CREATE);

			if (products != null || products.getId() > 0) {
				successResponse.setStatus("Success");
				successResponse.setStatusCode(HttpStatus.OK.value());
				successResponse.setMessage("Product save successfully Product Id : " + products.getId());
				successResponse.setErrorMessage("");
			} else {
				successResponse.setStatus("Error");
				successResponse.setStatusCode(HttpStatus.OK.value());
				successResponse.setMessage("Something went wrong");
				successResponse.setErrorMessage("Something went wrong");
			}
			return new ResponseEntity<>(successResponse, HttpStatus.OK);
		} catch (ExistingProductFound e) {
			ProductSaveResponse errorResponse = new ProductSaveResponse();
			errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
			errorResponse.setStatus("Error");
			errorResponse.setErrorMessage("Failed to save user: " + e.getMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			ProductSaveResponse errorResponse = new ProductSaveResponse();
			errorResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			errorResponse.setStatus("Error");
			errorResponse.setErrorMessage("Failed to save products: " + e.getMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/updateNewProducts")
	public ResponseEntity<ProductSaveResponse> updateNewProducts(@RequestBody ProductRequest productRequest) {

		try {
			ProductSaveResponse successResponse = new ProductSaveResponse();
			ProductRequest products = productService.saveNewOrProduct(productRequest, AppConstants.UPDATE);

			if (products != null || products.getId() > 0) {
				successResponse.setStatus("Success");
				successResponse.setStatusCode(HttpStatus.OK.value());
				successResponse.setMessage("Product updated successfully Product Id : " + products.getId());
				successResponse.setErrorMessage("");
			} else {
				successResponse.setStatus("Error");
				successResponse.setStatusCode(HttpStatus.OK.value());
				successResponse.setMessage("Something went wrong");
				successResponse.setErrorMessage("Something went wrong");
			}
			return new ResponseEntity<>(successResponse, HttpStatus.OK);
		} catch (Exception e) {
			ProductSaveResponse errorResponse = new ProductSaveResponse();
			errorResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			errorResponse.setStatus("Error");
			errorResponse.setErrorMessage("Failed to update product : " + e.getMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping("/deleteProductByid/{id}")
	public ResponseEntity<ProductSaveResponse> deleteProductById(@PathVariable("id") long id) {
		try {
			ProductSaveResponse products = this.productService.deleteProduct(id);

			return new ResponseEntity<>(products, HttpStatus.OK);
		} catch (Exception e) {
			ProductSaveResponse errorResponse = new ProductSaveResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					"Error", "Failed to delete product", e.getMessage());

			return new ResponseEntity<>(errorResponse, HttpStatus.OK);
		}

	}

	@GetMapping("/fetchProductById/{id}")
	public Product fetchById(@PathVariable("id") long id) {
		return this.productService.fetchById(id);
	}

}
