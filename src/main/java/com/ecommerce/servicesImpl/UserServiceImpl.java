package com.ecommerce.servicesImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.entity.Roles;
import com.ecommerce.entity.User;
import com.ecommerce.enums.AppConstants;
import com.ecommerce.exceptions.DataNotFoundException;
import com.ecommerce.exceptions.ExistingProductFound;
import com.ecommerce.payload.UserDto;
import com.ecommerce.repositories.UserRepo;
import com.ecommerce.responsePayload.GenericResponseMessageBean;
import com.ecommerce.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	PasswordEncoder passwordEncoder;

	@SuppressWarnings("unused")
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDto createUserOrUpdateUser(UserDto userDto, AppConstants createOrUpdate) {

		Optional<User> optionalUser = this.userRepo.findByUsername(userDto.getUserid());

		if (optionalUser.isPresent() && createOrUpdate.toString().equalsIgnoreCase("update")) {
			User user = new User();
			User users = optionalUser.get();
			users.setEmail(userDto.getEmail());
			users.setPassword(userDto.getPassword());
			users.setFirstName(userDto.getFirstName());
			users.setLastName(userDto.getLastName());
			List<Roles> rolesSet = userDto.getRoles().stream().map(Roles::new).collect(Collectors.toList());
			users.setRoles(rolesSet);
			users.setEncpassword(passwordEncoder.encode(userDto.getPassword()));
			users.setCreatedby(userDto.getCreatedBy());
			users.setUpdatedate(new Date());
			user = this.userRepo.save(users);
			return this.userToUserDto(user);
		} else if (optionalUser.isEmpty() && createOrUpdate.toString().equalsIgnoreCase("create")) {
			User user = this.dtoToUser(userDto);

			User savedUser = this.userRepo.save(user);

			return this.userToUserDto(savedUser);
		} else {
			throw new ExistingProductFound(200, "Success", "User already Exist", "");
		}

	}

//	@Override
//	public UserDto updateUser(UserDto userDto, Long userId) {
//
//		User user = this.userRepo.findById(userId)
//				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
//
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setFirstName(userDto.getFirstName());
//		user.setEncpassword(passwordEncoder.encode(userDto.getPassword()));
//
//		User updateUser = this.userRepo.save(user);
//
//		UserDto userDto1 = this.userToUserDto(updateUser);
//
//		return userDto1;
//	}

	@Override
	public UserDto getUserById(Long userId) {

		User user = this.userRepo.findById(userId).orElseThrow(() -> new DataNotFoundException(String.valueOf(HttpStatus.OK.value()), "Error",
				"Product Not found with Productid : " + userId, String.valueOf(userId)));

		return this.userToUserDto(user);
	}

	@Override
	public List<User> getAllUsers() {
		List<User> users = this.userRepo.findAll();
		return users;
	}

	@Override
	public GenericResponseMessageBean deleteUser(Long userId) {
		
		try {
			Optional<User> userDeletion = this.userRepo.findById(userId);
			if (userDeletion.isPresent()) {
				User user = userDeletion.get();
				this.userRepo.delete(user);
				return new GenericResponseMessageBean(String.valueOf(HttpStatus.OK.value()), "Success",
						"User deleted successfully", null);
			} else {
				return new GenericResponseMessageBean(String.valueOf(HttpStatus.OK.value()), "Error",
						"User not found with userId : " +userId, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new GenericResponseMessageBean(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), "Error",
					"Failed to delete user Something happened!! : " + e.getMessage(), null);
		}


	}

	private User dtoToUser(UserDto userDto) {
		// User user = this.modelMapper.map(userDto,User.class);
		User user = new User();

		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
//		user.setRoles(Arrays.asList(new Roles(userDto.getRoles())));

		List<Roles> rolesSet = userDto.getRoles().stream().map(Roles::new).collect(Collectors.toList());
		user.setRoles(rolesSet);
		user.setUserid(userDto.getUserid());
		user.setEncpassword(passwordEncoder.encode(userDto.getPassword()));
		user.setCreatedby(userDto.getCreatedBy());
		user.setCreatedDates(new Date());
		return user;

	}

	private UserDto userToUserDto(User user) {
		// UserDto userDto = this.modelMapper.map(user,UserDto.class);
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setEmail(user.getEmail());
		userDto.setPassword(passwordEncoder.encode(user.getPassword()));
		userDto.setFirstName(user.getFirstName());
		userDto.setLastName(user.getLastName());
		userDto.setCreatedBy(user.getCreatedby());
		userDto.setUserid(user.getUserid());
		userDto.setCreatedDates(user.getCreatedDates());
		userDto.setUpdatedate(user.getUpdatedate());
//		String role = user.getRoles().get(0).toString();
		List<Roles> roles = user.getRoles();
		List<String> allRoles = roles.stream().map(Roles::getName).collect(Collectors.toList());
		userDto.setRoles(allRoles);

		return userDto;

	}

	@Override
	public Page<User> getAllUsers(int page, int size, String filterBy, String filterByValue, String sortBy,
			String sortByValue) {
		Pageable pageable;
		if (sortBy != null && sortByValue != null) {
			Sort sort = sortByValue.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
					: Sort.by(sortBy).descending();
			pageable = PageRequest.of(page, size, sort);
		} else {
			pageable = PageRequest.of(page, size);
		}

		Specification<User> spec = Specification.where(null);
		if (filterBy != null) {
			if (filterByValue != null && !filterByValue.trim().isEmpty() && !filterByValue.isEmpty()) {
				spec = spec.and((root, query, cb) -> cb.equal(root.get(filterBy), filterByValue));
			} else {
				spec = spec.and((root, query, cb) -> cb.isNotNull(root.get(filterBy)));
			}
		}

		return userRepo.findAll(spec, pageable);
	}

}
