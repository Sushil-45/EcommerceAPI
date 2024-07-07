
package com.ecommerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TokenUnavailableException extends RuntimeException {

	public TokenUnavailableException(String message) {
		super(message);
	}
}
