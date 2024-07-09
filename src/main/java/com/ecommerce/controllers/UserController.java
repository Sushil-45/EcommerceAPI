package com.ecommerce.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.entity.User;
import com.ecommerce.enums.AppConstants;
import com.ecommerce.exceptions.ExistingProductFound;
import com.ecommerce.payload.UserDto;
import com.ecommerce.requestPayload.ProductFilter;
import com.ecommerce.responsePayload.ProductSaveResponse;
import com.ecommerce.responsePayload.UserResponse;
import com.ecommerce.services.UserService;
import com.ecommerce.utils.Utils;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private Utils utils;
	
	@PostMapping("/saveNew")
	public ResponseEntity<ProductSaveResponse> createUser(@RequestBody UserDto userDto){
		
//		UserDto createUser = this.userService.createUserOrUpdateUser(userDto);
//		return new ResponseEntity<>(createUser,HttpStatus.OK);
		try {
			ProductSaveResponse successResponse = new ProductSaveResponse();
			UserDto createUser = this.userService.createUserOrUpdateUser(userDto,AppConstants.CREATE);
			if (createUser != null || createUser.getId() > 0) {
				successResponse.setStatus("Success");
				successResponse.setStatusCode(HttpStatus.OK.value());
				successResponse.setMessage("User save successfully Product Id : " + createUser.getId());
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
			errorResponse.setErrorMessage("Failed to save user: " + e.getMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PutMapping("/updateUser")
	public  ResponseEntity<ProductSaveResponse> updateUser(@RequestBody UserDto userDto){
//		UserDto createUser = this.userService.createUserOrUpdateUser(userDto);
//		return new ResponseEntity<>(createUser,HttpStatus.OK);
		try {
			ProductSaveResponse successResponse = new ProductSaveResponse();
			UserDto createUser = this.userService.createUserOrUpdateUser(userDto,AppConstants.UPDATE);
			if (createUser != null || createUser.getId() > 0) {
				successResponse.setStatus("Success");
				successResponse.setStatusCode(HttpStatus.OK.value());
				successResponse.setMessage("User save successfully user Id : " + createUser.getId());
				successResponse.setErrorMessage("");
			} else {
				successResponse.setStatus("Error");
				successResponse.setStatusCode(HttpStatus.OK.value());
				successResponse.setMessage("Something went wrong");
				successResponse.setErrorMessage("Something went wrong");
			}
			return new ResponseEntity<>(successResponse, HttpStatus.OK);
		} catch(ExistingProductFound e) {
			ProductSaveResponse errorResponse = new ProductSaveResponse();
			errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
			errorResponse.setStatus("Error");
			errorResponse.setErrorMessage("Failed to save user: " + e.getMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			ProductSaveResponse errorResponse = new ProductSaveResponse();
			errorResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			errorResponse.setStatus("Error");
			errorResponse.setErrorMessage("Failed to save user: " + e.getMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@SuppressWarnings("unchecked")
	@DeleteMapping("/deleteUser/{userId}")
	public ResponseEntity<?> deleteUser( @PathVariable Long userId){
		this.userService.deleteUser(userId);
		return new ResponseEntity(Map.of("user","Usesr deleted successfully"),HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<UserResponse> getAllUsers(){
//		return ResponseEntity.ok(this.userService.getAllUsers());
		try {

			List<User> users = this.userService.getAllUsers();

			UserResponse response = new UserResponse();
			if (users != null) {
				response.setStatusCode(HttpStatus.OK.value());
				response.setStatus("Success");
				response.setUsers(users);
			} else {
				response.setStatusCode(HttpStatus.OK.value());
				response.setStatus("Success");
				response.setErrorMessage("Data is empty");
				response.setUsers(new ArrayList<>());
			}
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			UserResponse errorResponse = new UserResponse();
			errorResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			errorResponse.setStatus("Error");
			errorResponse.setErrorMessage("Failed to fetch products: " + e.getMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	
	@GetMapping("/findById/{userId}")
	public ResponseEntity<UserDto> getUserByUserId(@PathVariable Long userId){
		return ResponseEntity.ok(this.userService.getUserById(userId));
	}
	
	@PostMapping("/getAllUsers")
	public ResponseEntity<UserResponse> getAllUsers(@RequestBody ProductFilter productFilter) {
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

			Page<User> products = userService.getAllUsers(page, size, filterBy, filterByValue, sortBy,
					sortByValue);
			UserResponse response = new UserResponse();
			if (products != null && products.getContent().size()>0) {
				response.setStatusCode(HttpStatus.OK.value());
				response.setStatus("Success");
				response.setUsers(products.getContent());
			} else {
				response.setStatusCode(HttpStatus.OK.value());
				response.setStatus("Success");
				response.setErrorMessage("Data is empty");
				response.setUsers(new ArrayList<>());
			}
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			UserResponse errorResponse = new UserResponse();
			errorResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			errorResponse.setStatus("Error");
			errorResponse.setErrorMessage("Failed to fetch user: " + e.getMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
