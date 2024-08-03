package com.ecommerce.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ecommerce.entity.User;
import com.ecommerce.enums.AppConstants;
import com.ecommerce.payload.UserDto;
import com.ecommerce.responsePayload.GenericResponseMessageBean;


public interface UserService {
	
	UserDto createUserOrUpdateUser(UserDto user, AppConstants create);
	
//	UserDto updateUser(UserDto user,Long userId);
	
	UserDto getUserById(Long userId);
	
//	List<UserDto> getAllUsers();
	List<User> getAllUsers();

	GenericResponseMessageBean deleteUser(Long userId);

	Page<User> getAllUsers(int page, int size, String filterBy, String filterByValue, String sortBy,
			String sortByValue);
	
}
