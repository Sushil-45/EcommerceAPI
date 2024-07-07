package com.ecommerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.ecommerce.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> , JpaSpecificationExecutor<Product> {

	List<Product> findByTitleContainingIgnoreCase(String filterByValue);

	List<Product> findByProductDescContainingIgnoreCase(String filterByValue);

	List<Product> findByProductPriceLessThan(double double1);

	List<Product> findByQuantityGreaterThan(int int1);

	List<Product> findByProductNameContainingIgnoreCase(String filterByValue);

}
