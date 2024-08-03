package com.ecommerce.exceptions;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.responsePayload.GenericResponseMessageBean;

import jakarta.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class GlobalExceptionHandlere {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<GenericResponseMessageBean> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
		String message = ex.getMessage();
		GenericResponseMessageBean apiResponse = new GenericResponseMessageBean(String.valueOf(HttpStatus.NOT_FOUND), "Error",message,null);		
		return new ResponseEntity<GenericResponseMessageBean>(apiResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<GenericResponseMessageBean> handleMethodArgsNotValid(HttpRequestMethodNotSupportedException ex) {
		String message = ex.getMessage();
		GenericResponseMessageBean apiResponse = new GenericResponseMessageBean(String.valueOf(HttpStatus.NOT_FOUND), "Error",message,null);		
		return new ResponseEntity<GenericResponseMessageBean>(apiResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ApiException.class)
	public ResponseEntity<GenericResponseMessageBean> handleApiException(ApiException ex) {
		String message = ex.getMessage();
		GenericResponseMessageBean apiResponse = new GenericResponseMessageBean(String.valueOf(HttpStatus.NOT_FOUND), "Error",message,null);		
		return new ResponseEntity<GenericResponseMessageBean>(apiResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<GenericResponseMessageBean> handleResponseStatusException(ResponseStatusException e) {
		GenericResponseMessageBean errorResponse = new GenericResponseMessageBean(String.valueOf(e.getStatusCode().value()), "Error ", " Error due to : " +e.getReason() +e.getMessage(),
				null);
		return ResponseEntity.status(e.getStatusCode()).body(errorResponse);
	}

	@ExceptionHandler(ResponseStatusSaveException.class)
	public ResponseEntity<GenericResponseMessageBean> handleResponseStatusException(ResponseStatusSaveException e) {
		GenericResponseMessageBean errorResponse = new GenericResponseMessageBean("400", e.getStatus().toString(), e.getMessage(),
				null);
		return ResponseEntity.status(400).body(errorResponse);
	}

	@ExceptionHandler(TokenUnavailableException.class)
	public ResponseEntity<GenericResponseMessageBean> handleTokenUnavailableException(TokenUnavailableException e) {
		GenericResponseMessageBean errorResponse = new GenericResponseMessageBean(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Error", e.getMessage(),
				null);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

	@ExceptionHandler(ExpiredTokenException.class)
	public ResponseEntity<GenericResponseMessageBean> handleExpiredTokenException(ExpiredTokenException e) {
		GenericResponseMessageBean errorResponse = new GenericResponseMessageBean(String.valueOf(HttpStatus.UNAUTHORIZED.value()), "Error", e.getMessage(),
				null);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
	}

	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity<GenericResponseMessageBean> handleInvalidTokenException(InvalidTokenException e) {
		GenericResponseMessageBean errorResponse = new GenericResponseMessageBean(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Error", e.getMessage(),
				null);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}
	

	@ExceptionHandler(DataNotFoundException.class)
	public void DataNotFoundException(DataNotFoundException ex,HttpServletResponse response) throws IOException {	
		response.setStatus(HttpStatus.NOT_FOUND.value());
		response.setContentType("application/json");
		response.getWriter().write("{\"responseCode\":\""+ HttpStatus.NOT_FOUND.value()+"\" , \"result\":\""+ex.getResult() +"\" , \"responseMessage\":\""+ex.getResponseMessage()+"\" ,"
				+ " \"data\": \"" + ex.getData() + "\",\"id\":\""+""+"\"}");
	}

	
}
