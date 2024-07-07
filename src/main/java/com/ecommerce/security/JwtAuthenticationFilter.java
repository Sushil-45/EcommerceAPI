package com.ecommerce.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ecommerce.servicesImpl.CustomUsesrDetailService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private CustomUsesrDetailService userDetailService;

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		logger.info("Request URL: {}", request.getRequestURL());
		// get Token from headers

		if ("/api/v1/auth/login".equals(request.getRequestURI())
				|| "/api/users/saveNew".equals(request.getRequestURI())) {
			filterChain.doFilter(request, response);
			return;
		}

		String requestToken = request.getHeader("Authorization");

		System.out.println("JWT token : " + requestToken);
		// Bearer "Token"

		String token = "";
		String username = "";

		if (request != null && requestToken.startsWith("Bearer")) {

			token = requestToken.substring(7);

			try {

				username = this.jwtTokenHelper.extractUsername(token);
			} catch (IllegalArgumentException e) {
				handleTokenUnavailableException(response, "Unable to get Token: " + e.getMessage());
				return;
			} catch (ExpiredJwtException e) {
				handleExpiredTokenException(response, "Expired token: " + e.getMessage());
				return;
			} catch (MalformedJwtException e) {
				handleInvalidTokenException(response, "Invalid token: " + e.getMessage());
				return;
			}

		} else {
			System.out.println("Token not begin with Bearer");
		}

		// Validation after fetching token

		if (username != null && !username.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = this.userDetailService.loadUserByUsername(username);

			if (this.jwtTokenHelper.validateToken(token, userDetails.getUsername())) {

				// valid token now authentication

				UsernamePasswordAuthenticationToken usernamePasswordAuthentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthentication);

			} else {
				System.out.println("invalid jwt token");
			}

		} else {
			System.out.println("user is null");
		}

		filterChain.doFilter(request, response);

	}

	private void handleTokenUnavailableException(HttpServletResponse response, String errorMessage) throws IOException {
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		response.setContentType("application/json");
		response.getWriter().write("{\"path\":\"/error\" ,  \"status\":\"" + HttpStatus.UNAUTHORIZED.value()
				+ "\" ,   \"error\": \"" + errorMessage + "\" , \"message\" :\"" + errorMessage + "\"}");

	}

	private void handleExpiredTokenException(HttpServletResponse response, String errorMessage) throws IOException {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType("application/json");
		response.getWriter().write("{\"path\":\"/error\" ,  \"status\":\"" + HttpStatus.UNAUTHORIZED.value()
				+ "\" ,   \"error\": \"" + errorMessage + "\" , \"message\" :\"" + errorMessage + "\"}");
	}

	private void handleInvalidTokenException(HttpServletResponse response, String errorMessage) throws IOException {
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		response.setContentType("application/json");
		response.getWriter().write("{\"path\":\"/error\" ,  \"status\":\"" + HttpStatus.UNAUTHORIZED.value()
				+ "\" ,   \"error\": \"" + errorMessage + "\" , \"message\" :\"" + errorMessage + "\"}");

	}

}
