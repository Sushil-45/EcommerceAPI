package com.ecommerce.servicesImpl;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
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
import com.ecommerce.entity.User;
import com.ecommerce.exceptions.ResourceNotFoundException;
import com.ecommerce.exceptions.UserNotFoundException;
import com.ecommerce.repositories.ProductRepository;
import com.ecommerce.requestPayload.ProductRequest;
import com.ecommerce.responsePayload.ProductSaveResponse;
import com.ecommerce.services.ProductServices;

@Service
public class ProductServicesImpl implements ProductServices {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public Page<Product> getAllProducts(int page, int size, String filterBy, String filterByValue, String sortBy,
			String sortByValue) {
//		try {
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
			        spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get(filterBy)), "%" + filterByValue.toLowerCase() + "%"));
			    } else {
			        spec = spec.and((root, query, cb) -> cb.isNotNull(root.get(filterBy)));
			    }
			}

			return productRepository.findAll(spec, pageable);
//		} catch (IllegalArgumentException | NoSuchElementException e) {
//			throw e;
//		} catch (Exception e) {
//			throw new RuntimeException("Failed to fetch products", e);
//		}
	}

	@Override
	public List<Product> getAll() {
		try {
			return productRepository.findAll();
		} catch (Exception e) {
			throw new RuntimeException("Failed to fetch products", e);
		}
	}

	@Override
	public ProductRequest saveNewOrProduct(ProductRequest productRequest) {

		if (productRequest.getId() != null) {
			Optional<Product> optionalProduct = this.productRepository.findById(productRequest.getId());
			Product saveNewProduct = new Product();
			if (optionalProduct.isPresent()) {
				Product products = optionalProduct.get();
				products.setProductDesc(productRequest.getProductDesc());
				products.setProductName(productRequest.getProductName());
				products.setProductPrice(productRequest.getProductPrice());
				products.setQuantity(productRequest.getQuantity());
				products.setTitle(productRequest.getTitle());
				products.setUpdatedate(new Date());
				products.setUpdatedUserId(productRequest.getUserId());

				saveNewProduct = this.productRepository.save(products);
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
	public Product fetchById(long id) {

//		Product product = this.productRepository.findById(id)
//				.orElseThrow(() -> new ResourceNotFoundException("Product ", " Id ", id));

		Product product = this.productRepository.findById(id).orElseThrow(() -> new UserNotFoundException(200, "Error",
				"Product Not found with Productid : " + id, String.valueOf(id)));

		return product;
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
	public ProductSaveResponse deleteProduct(long id) {
		try {
			Optional<Product> productOptional = productRepository.findById(id);
			if (productOptional.isPresent()) {
				Product product = productOptional.get();
				productRepository.delete(product);
				return new ProductSaveResponse(HttpStatus.OK.value(), "Success", "Product deleted successfully", null);
			} else {
				return new ProductSaveResponse(HttpStatus.NOT_FOUND.value(), "Error", "Product not found", null);
			}
		} catch (Exception e) {
			return new ProductSaveResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error",
					"Failed to delete product", e.getMessage());
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
