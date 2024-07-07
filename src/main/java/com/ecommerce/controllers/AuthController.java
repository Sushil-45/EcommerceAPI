package com.ecommerce.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.exceptions.ApiException;
import com.ecommerce.payload.JwtAuthRequest;
import com.ecommerce.security.JwtAuthResponse;
import com.ecommerce.security.JwtTokenHelper;
import com.ecommerce.servicesImpl.CustomUsesrDetailService;
import com.ecommerce.servicesImpl.MyUserDetails;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private CustomUsesrDetailService customUserDetailService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) {

		JwtAuthResponse response = new JwtAuthResponse();
		MyUserDetails ud = new MyUserDetails();
		try {

			this.authenticate(request.getUsername(), request.getPassword());

			UserDetails userDetails = this.customUserDetailService.loadUserByUsername(request.getUsername());

			ud = (MyUserDetails) userDetails;

			String token = this.jwtTokenHelper.generateToken(ud.getUsername());

			response.setToken(token);
			response.setUserId(ud.getUsername());
			response.setErrorMessage("");
			response.setStatus("Success");
			response.setStatusCode(200)
;			List<String> authorityNames = ud.getAuthorities().stream().map(GrantedAuthority::getAuthority)
					.collect(Collectors.toList());
			response.setRoles(authorityNames);

			
		} catch (Exception e) {
			response.setToken("");
			response.setUserId(ud.getUsername());
			response.setErrorMessage(e.getMessage());
			response.setStatus("Error");
			response.setStatusCode(200);

		}
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
	}

	private void authenticate(String username, String password) {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);
		try {
			this.authenticationManager.authenticate(authenticationToken);
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		} catch (BadCredentialsException e) {
			throw new ApiException("Invalid Credentials");
		}
	}

}
