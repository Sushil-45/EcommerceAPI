package com.ecommerce.servicesImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ecommerce.entity.Product;
import com.ecommerce.enums.AppConstants;
import com.ecommerce.exceptions.DataNotFoundException;
import com.ecommerce.exceptions.ExistingProductFound;
import com.ecommerce.repositories.ProductRepository;
import com.ecommerce.requestPayload.ProductRequest;
import com.ecommerce.responsePayload.GenericResponseMessageBean;
import com.ecommerce.services.ProductServices;

@Service
public class ProductServicesImpl implements ProductServices {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public Page<Product> getAllProducts(int page, int size, String filterBy, String filterByValue, String sortBy,
			String sortByValue) {
		Pageable pageable;
		if (sortBy != null && sortByValue != null) {
			Sort sort = sortByValue.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
					: Sort.by(sortBy).descending();
			pageable = PageRequest.of(page, size, sort);
		} else {
			pageable = PageRequest.of(page, size);
		}

		Specification<Product> spec = Specification.where(null);
		if (filterBy != null) {
			if (filterByValue != null && !filterByValue.trim().isEmpty() && !filterByValue.isEmpty()) {
				spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get(filterBy)),
						"%" + filterByValue.toLowerCase() + "%"));
			} else {
				spec = spec.and((root, query, cb) -> cb.isNotNull(root.get(filterBy)));
			}
		}

		return productRepository.findAll(spec, pageable);
	}

	@Override
	public List<Product> getAll() {
		return productRepository.findAll();
	}

	@Override
	public ProductRequest saveNewOrProduct(ProductRequest productRequest, AppConstants create) {

		if (productRequest.getId() != null) {
			Optional<Product> optionalProduct = this.productRepository.findById(productRequest.getId());
			Product saveNewProduct = new Product();
			if (optionalProduct.isPresent() && create.toString().equalsIgnoreCase("Update")) {
				Product products = optionalProduct.get();
				products.setProductDesc(productRequest.getProductDesc());
				products.setProductName(productRequest.getProductName());
				products.setProductPrice(productRequest.getProductPrice());
				products.setQuantity(productRequest.getQuantity());
				products.setTitle(productRequest.getTitle());
				products.setUpdatedate(new Date());
				products.setUpdatedUserId(productRequest.getUserId());

				saveNewProduct = this.productRepository.save(products);
			} else {
				throw new ExistingProductFound(200, "Success", "Product already Exist", "");
			}
			return this.productToDto(saveNewProduct);

		} else {
			Product product = this.dtoToProduct(productRequest);

			Product saveNewProduct = this.productRepository.save(product);

			return this.productToDto(saveNewProduct);
		}
	}

//	@Override
//	public ProductRequest updateProduct(ProductRequest productRequest) {
//
//		Product product = this.productRepository.findById(productRequest.getId())
//				.orElseThrow(() -> new ResourceNotFoundException("Product ", " Id ", productRequest.getId()));
//		;
//
//		if (product != null) {
//
//			product.setProductDesc(productRequest.getProductDesc());
//			product.setProductName(productRequest.getProductName());
//			product.setProductPrice(productRequest.getProductPrice());
//			product.setQuantity(productRequest.getQuantity());
//			product.setTitle(productRequest.getTitle());
//			product.setUpdatedate(new Date());
//			product.setUpdatedUserId(productRequest.getUserId());
//
//			Product saveNewProduct = this.productRepository.save(product);
//			return this.productToDto(saveNewProduct);
//
//		} else {
//
//			Product products = this.dtoToProduct(productRequest);
//
//			Product saveNewProduct = this.productRepository.save(products);
//
//			return this.productToDto(saveNewProduct);
//
//		}
//
//	}

	@Override
	public GenericResponseMessageBean fetchById(long id) {
		try {
			GenericResponseMessageBean response = new GenericResponseMessageBean();

			Product product = this.productRepository.findById(id)
					.orElseThrow(() -> new DataNotFoundException(String.valueOf(HttpStatus.OK.value()), "Error",
							"Product Not found with Productid : " + id, String.valueOf(id)));

			if (product != null) {
				response.setResponseCode(String.valueOf(HttpStatus.OK.value()));
				response.setResponseMessage("Succesfully fetched data for product : " + id);
				response.setResult("Success");
				response.setData(product);
			} else {
				response.setResponseCode(String.valueOf(HttpStatus.OK.value()));
				response.setResponseMessage("Data Not Found with Product ID : " + id);
				response.setResult("Error");
				response.setData(product);
			}

			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponseMessageBean(String.valueOf(HttpStatus.OK.value()), "Error",
					"Failed to find product something happened!!! : " + e.getMessage(), null);
		}
	}

	@Override
	public List<Product> getProductsByFilter(String fetchFilterBy, String filterByValue) {
		switch (fetchFilterBy) {
		case "title":
			return productRepository.findByTitleContainingIgnoreCase(filterByValue);

		case "productDesc":
			return productRepository.findByProductDescContainingIgnoreCase(filterByValue);

		case "productPrice":
			return productRepository.findByProductPriceLessThan(Double.parseDouble(filterByValue));

		case "quantity":
			return productRepository.findByQuantityGreaterThan(Integer.parseInt(filterByValue));

		case "productName":
			return productRepository.findByProductNameContainingIgnoreCase(filterByValue);
		default:
			throw new IllegalArgumentException("Invalid filterBy value: " + fetchFilterBy);
		}
	}

	@Override
	public GenericResponseMessageBean deleteProduct(long id) {
		try {
			Optional<Product> productOptional = productRepository.findById(id);
			if (productOptional.isPresent()) {
				Product product = productOptional.get();
				productRepository.delete(product);
				return new GenericResponseMessageBean(String.valueOf(HttpStatus.OK.value()), "Success",
						"Product deleted successfully", null);
			} else {
				return new GenericResponseMessageBean(String.valueOf(HttpStatus.OK.value()), "Error",
						"Product not found", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponseMessageBean(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), "Error",
					"Failed to delete product Something happened!! : " + e.getMessage(), null);
		}
	}

	private ProductRequest productToDto(Product productRequest) {
		ProductRequest product = new ProductRequest();
		product.setId(productRequest.getId());
		product.setProductDesc(productRequest.getProductDesc());
		product.setProductName(productRequest.getProductName());
		product.setProductPrice(productRequest.getProductPrice());
		product.setQuantity(productRequest.getQuantity());
		product.setTitle(productRequest.getTitle());
		product.setUserId(productRequest.getUserid());
		return product;
	}

	private Product dtoToProduct(ProductRequest productRequest) {

		Product product = new Product();

		product.setProductDesc(productRequest.getProductDesc());
		product.setProductName(productRequest.getProductName());
		product.setProductPrice(productRequest.getProductPrice());
		product.setQuantity(productRequest.getQuantity());
		product.setTitle(productRequest.getTitle());
		product.setUserid(productRequest.getUserId());
		product.setCreatedby(new Date());

		return product;
	}
}
