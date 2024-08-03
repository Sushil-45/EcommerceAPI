package com.ecommerce.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.ecommerce.responsePayload.GenericResponseMessageBean;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	 private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		logger.error("Unauthorized error: {}", authException.getMessage());

	    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

//	    final Map<String, Object> body = new HashMap<>();
//	    body.put("responseCode", HttpServletResponse.SC_UNAUTHORIZED);
//	    body.put("responseMessage", "Unauthorized");
//	    body.put("result", authException.getMessage());
//	    body.put("data", request.getServletPath());
//	    body.put("id", "");
//
//	    final ObjectMapper mapper = new ObjectMapper();
//	    mapper.writeValue(response.getOutputStream(), body);
	    
	    
		GenericResponseMessageBean responseBody = new GenericResponseMessageBean(
				String.valueOf(HttpServletResponse.SC_UNAUTHORIZED), authException.getMessage(), "Unauthorized",
				request.getServletPath());
		responseBody.setId(0L);

		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getOutputStream(), responseBody);

	}

}
