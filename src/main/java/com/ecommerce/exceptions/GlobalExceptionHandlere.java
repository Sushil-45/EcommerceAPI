package com.ecommerce.exceptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.payload.ApiResponse;
import com.ecommerce.responsePayload.ProductResponse;
import com.ecommerce.responsePayload.ProductSaveResponse;

import jakarta.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class GlobalExceptionHandlere {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, false);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<String> handleMethodArgsNotValid(HttpRequestMethodNotSupportedException ex) {
		Map<String, String> resp = new HashMap<String, String>();

		String error = ex.getMessage();

		return new ResponseEntity<String>(error, HttpStatus.METHOD_NOT_ALLOWED);

	}

	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiResponse> handleApiException(ApiException ex) {
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, true);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<ProductResponse> handleResponseStatusException(ResponseStatusException e) {
		ProductResponse errorResponse = new ProductResponse(e.getStatusCode().value(), e.getReason(), e.getMessage(),
				null);
		return ResponseEntity.status(e.getStatusCode()).body(errorResponse);
	}

	@ExceptionHandler(ResponseStatusSaveException.class)
	public ResponseEntity<ProductSaveResponse> handleResponseStatusException(ResponseStatusSaveException e) {
		ProductSaveResponse errorResponse = new ProductSaveResponse(400, e.getStatus().toString(), e.getMessage(),
				e.getMessage());
		return ResponseEntity.status(400).body(errorResponse);
	}

	@ExceptionHandler(TokenUnavailableException.class)
	public ResponseEntity<String> handleTokenUnavailableException(TokenUnavailableException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}

	@ExceptionHandler(ExpiredTokenException.class)
	public ResponseEntity<String> handleExpiredTokenException(ExpiredTokenException e) {
		String errorMessage = e.getMessage();
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
	}

	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity<String> handleInvalidTokenException(InvalidTokenException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}
	

	@ExceptionHandler(UserNotFoundException.class)
	public void UserNotFoundException(UserNotFoundException ex,HttpServletResponse response) throws IOException {
		
		response.setStatus(HttpStatus.NOT_FOUND.value());
		response.setContentType("application/json");
		response.getWriter().write("{\"statusCode\":\""+ HttpStatus.NOT_FOUND.value()+"\" , \"status\":\""+ex.getStatus() +"\" , \"errorMessage\":\""+ex.getErrorMessage()+"\" , \"userId\": \"" + ex.getUserId() + "\"}");
	}

	
}
