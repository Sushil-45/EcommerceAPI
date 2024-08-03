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
import com.ecommerce.responsePayload.GenericResponseMessageBean;
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
	public ResponseEntity<GenericResponseMessageBean> createUser(@RequestBody UserDto userDto){
		try {
			GenericResponseMessageBean successResponse = new GenericResponseMessageBean();
			UserDto createUser = this.userService.createUserOrUpdateUser(userDto,AppConstants.CREATE);
			if (createUser != null || createUser.getId() > 0) {
				successResponse.setResponseCode(String.valueOf(HttpStatus.OK.value()));
				successResponse.setResult("Success");
				successResponse.setResponseMessage("User save successfully user Id  : " + createUser.getId());
				successResponse.setData("");
			} else {
				successResponse.setResponseCode(String.valueOf(HttpStatus.OK.value()));
				successResponse.setResult("Error");
				successResponse.setResponseMessage("Something happened failed to save Data");
				successResponse.setData("");
			}
			return new ResponseEntity<>(successResponse, HttpStatus.OK);
		}catch(ExistingProductFound e) {
			GenericResponseMessageBean errorResponse = new GenericResponseMessageBean();
			errorResponse.setResponseCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
			errorResponse.setResult("Error");
			errorResponse.setResponseMessage("Failed to save user as : " + e.getMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			GenericResponseMessageBean errorResponse = new GenericResponseMessageBean();
			errorResponse.setResponseCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
			errorResponse.setResult("Error");
			errorResponse.setResponseMessage("Failed to save user: " + e.getMessage());
			errorResponse.setData("");
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PutMapping("/updateUser")
	public  ResponseEntity<GenericResponseMessageBean> updateUser(@RequestBody UserDto userDto){
		try {
			GenericResponseMessageBean successResponse = new GenericResponseMessageBean();
			UserDto createUser = this.userService.createUserOrUpdateUser(userDto,AppConstants.UPDATE);
			if (createUser != null || createUser.getId() > 0) {
				successResponse.setResponseCode(String.valueOf(HttpStatus.OK.value()));
				successResponse.setResult("Success");
				successResponse.setResponseMessage("User updated successfully user Id  : " + createUser.getId());
				successResponse.setData("");
			} else {
				successResponse.setResponseCode(String.valueOf(HttpStatus.OK.value()));
				successResponse.setResult("Error");
				successResponse.setResponseMessage("Something happened failed to update Data");
				successResponse.setData("");
			}
			return new ResponseEntity<>(successResponse, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			GenericResponseMessageBean errorResponse = new GenericResponseMessageBean();
			errorResponse.setResponseCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
			errorResponse.setResult("Error");
			errorResponse.setResponseMessage("Failed to save user: " + e.getMessage());
			errorResponse.setData("");
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@DeleteMapping("/deleteUser/{userId}")
	public ResponseEntity<GenericResponseMessageBean> deleteUser( @PathVariable Long userId){
		
		try {
			GenericResponseMessageBean products = this.userService.deleteUser(userId);

			return new ResponseEntity<>(products, HttpStatus.OK);
		} catch (Exception e) {
			GenericResponseMessageBean errorResponse = new GenericResponseMessageBean(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
					"Error", "Failed to delete user", e.getMessage());

			return new ResponseEntity<>(errorResponse, HttpStatus.OK);
		}
		
	}
	
	@GetMapping("/")
	public ResponseEntity<GenericResponseMessageBean> getAllUsers(){
		try {
			List<User> users = this.userService.getAllUsers();

			GenericResponseMessageBean successResponse = new GenericResponseMessageBean();
			if (users != null) {
				successResponse.setResponseCode(String.valueOf(HttpStatus.OK.value()));
				successResponse.setResult("Success");
				successResponse.setResponseMessage("User Data Fetch Successfully");
				successResponse.setData(users);
			} else {
				successResponse.setResponseCode(String.valueOf(HttpStatus.OK.value()));
				successResponse.setResult("Error");
				successResponse.setResponseMessage("Data is Empty !!!");
				successResponse.setData(new ArrayList<>());
			}
			return new ResponseEntity<>(successResponse, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			GenericResponseMessageBean errorResponse = new GenericResponseMessageBean();
			errorResponse.setResponseCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
			errorResponse.setResult("Error");
			errorResponse.setResponseMessage("Failed to fetch users: " + e.getMessage());
			errorResponse.setData("");
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	
	@GetMapping("/findById/{userId}")
	public ResponseEntity<GenericResponseMessageBean> getUserByUserId(@PathVariable Long userId){
		try {
			
			UserDto users = this.userService.getUserById(userId);
			
			GenericResponseMessageBean successResponse = new GenericResponseMessageBean();
			if (users != null) {
				successResponse.setResponseCode(String.valueOf(HttpStatus.OK.value()));
				successResponse.setResult("Success");
				successResponse.setResponseMessage("User Data Fetch Successfully");
				successResponse.setData(users);
			} else {
				successResponse.setResponseCode(String.valueOf(HttpStatus.OK.value()));
				successResponse.setResult("Error");
				successResponse.setResponseMessage("Data is Empty !!!");
				successResponse.setData(new ArrayList<>());
			}
			return new ResponseEntity<>(successResponse, HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			GenericResponseMessageBean errorResponse = new GenericResponseMessageBean();
			errorResponse.setResponseCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
			errorResponse.setResult("Error");
			errorResponse.setResponseMessage("Failed to fetch users: " + e.getMessage());
			errorResponse.setData("");
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PostMapping("/getAllUsers")
	public ResponseEntity<GenericResponseMessageBean> getAllUsers(@RequestBody ProductFilter productFilter) {
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

			Page<User> users = userService.getAllUsers(page, size, filterBy, filterByValue, sortBy,
					sortByValue);
			GenericResponseMessageBean successResponse = new GenericResponseMessageBean();
			if (users != null) {
				successResponse.setResponseCode(String.valueOf(HttpStatus.OK.value()));
				successResponse.setResult("Success");
				successResponse.setResponseMessage("User Data Fetch Successfully");
				successResponse.setData(users);
			} else {
				successResponse.setResponseCode(String.valueOf(HttpStatus.OK.value()));
				successResponse.setResult("Error");
				successResponse.setResponseMessage("Data is Empty !!!");
				successResponse.setData(new ArrayList<>());
			}
			return new ResponseEntity<>(successResponse, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			GenericResponseMessageBean errorResponse = new GenericResponseMessageBean();
			errorResponse.setResponseCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
			errorResponse.setResult("Error");
			errorResponse.setResponseMessage("Failed to fetch users: " + e.getMessage());
			errorResponse.setData("");
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}


	}
}
