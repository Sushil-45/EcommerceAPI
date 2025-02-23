package com.ecommerce.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.ecommerce.responsePayload.GenericResponseMessageBean;
import com.ecommerce.services.ProductServices;
import com.ecommerce.utils.Utils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/product")
public class ProductController {

	@Autowired
	private Utils utils;

	@Autowired
	private ProductServices productService;

	@PostMapping("/getAllProducts")
	public ResponseEntity<GenericResponseMessageBean> getAllProducts(@RequestBody ProductFilter productFilter) {
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
			GenericResponseMessageBean response = new  GenericResponseMessageBean();
			if (products != null && products.getContent().size() > 0) {
				response.setResponseCode(String.valueOf(HttpStatus.OK.value()));
				response.setResult("Success");
				response.setResponseMessage("Data fetched Succesfully");
				response.setData(products.getContent());
			} else {
				response.setResponseCode(String.valueOf(HttpStatus.OK.value()));
				response.setResult("Success");
				response.setResponseMessage("Data is empty either filterBy or filterByValue is wrong ");
				response.setData(new ArrayList<>());
			}
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			GenericResponseMessageBean errorResponse = new GenericResponseMessageBean();
			errorResponse.setResponseCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
			errorResponse.setResult("Error");
			errorResponse.setResponseMessage("Failed to fetch products: " + e.getMessage());
			errorResponse.setData(new ArrayList<>());
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/")
	public ResponseEntity<GenericResponseMessageBean> getAll() {

		try {

			List<Product> products = productService.getAll();

			GenericResponseMessageBean response = new GenericResponseMessageBean();
			if (products != null) {
				response.setResponseCode(String.valueOf(HttpStatus.OK.value()));
				response.setResult("Success");
				response.setResponseMessage("Data fetched Succesfully");
				response.setData(products);
			} else {
				response.setResponseCode(String.valueOf(HttpStatus.OK.value()));
				response.setResult("Success");
				response.setResponseMessage("Data is empty ");
				response.setData(new ArrayList<>());
			}
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			GenericResponseMessageBean errorResponse = new GenericResponseMessageBean();
			errorResponse.setResponseCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
			errorResponse.setResult("Error");
			errorResponse.setResponseMessage("Failed to fetch products: " + e.getMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/saveNewProducts")
	public ResponseEntity<GenericResponseMessageBean> saveNewProduct(@RequestBody ProductRequest productRequest) {

		try {
			GenericResponseMessageBean successResponse = new GenericResponseMessageBean();
			ProductRequest products = productService.saveNewOrProduct(productRequest, AppConstants.CREATE);

			if (products != null || products.getId() > 0) {
				successResponse.setResponseCode(String.valueOf(HttpStatus.OK.value()));
				successResponse.setResult("Success");
				successResponse.setResponseMessage("Product save successfully Product Id : " + products.getId());
				successResponse.setData("");
			} else {
				successResponse.setResponseCode(String.valueOf(HttpStatus.OK.value()));
				successResponse.setResult("Success");
				successResponse.setResponseMessage("Something went wrong");
				successResponse.setData("");
			}
			return new ResponseEntity<>(successResponse, HttpStatus.OK);
		} catch (ExistingProductFound e) {
			GenericResponseMessageBean errorResponse = new GenericResponseMessageBean();
			errorResponse.setResponseCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
			errorResponse.setResult("Error");
			errorResponse.setResponseMessage("Failed to save product : " + e.getMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			GenericResponseMessageBean errorResponse = new GenericResponseMessageBean();
			errorResponse.setResponseCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
			errorResponse.setResult("Error");
			errorResponse.setResponseMessage("Failed to save product: " + e.getMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/updateNewProducts")
	public ResponseEntity<GenericResponseMessageBean> updateNewProducts(@RequestBody ProductRequest productRequest) {

		try {
			GenericResponseMessageBean successResponse = new GenericResponseMessageBean();
			ProductRequest products = productService.saveNewOrProduct(productRequest, AppConstants.UPDATE);

			if (products != null || products.getId() > 0) {
				successResponse.setResponseCode(String.valueOf(HttpStatus.OK.value()));
				successResponse.setResult("Success");
				successResponse.setResponseMessage("Product updated successfully Product Id : " + products.getId());
				successResponse.setData("");
			} else {
				successResponse.setResponseCode(String.valueOf(HttpStatus.OK.value()));
				successResponse.setResult("Success");
				successResponse.setResponseMessage("Something went wrong");
				successResponse.setData("");
			}
			return new ResponseEntity<>(successResponse, HttpStatus.OK);
		} catch (Exception e) {
			GenericResponseMessageBean errorResponse = new GenericResponseMessageBean();
			errorResponse.setResponseCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
			errorResponse.setResult("Error");
			errorResponse.setResponseMessage("Failed to update product: " + e.getMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping("/deleteProductByid/{id}")
	public ResponseEntity<GenericResponseMessageBean> deleteProductById(@PathVariable("id") long id) {
		try {
			GenericResponseMessageBean products = this.productService.deleteProduct(id);

			return new ResponseEntity<>(products, HttpStatus.OK);
		} catch (Exception e) {
			GenericResponseMessageBean errorResponse = new GenericResponseMessageBean(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
					"Error", "Failed to delete product", e.getMessage());

			return new ResponseEntity<>(errorResponse, HttpStatus.OK);
		}

	}

	@GetMapping("/fetchProductById/{id}")
	public GenericResponseMessageBean fetchById(@PathVariable("id") long id) {
		return this.productService.fetchById(id);
	}

}
