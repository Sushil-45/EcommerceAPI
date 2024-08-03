package com.ecommerce.servicesImpl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecommerce.entity.User;
import com.ecommerce.exceptions.ResourceNotFoundException;
import com.ecommerce.repositories.UserRepo;

@Service
public class CustomUsesrDetailService implements UserDetailsService{

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = this.userRepo.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException(200,"Error","User Not found with userid : " +username,"", username
									,new ArrayList<>()));

		return new MyUserDetails(user);
	}
	
}
