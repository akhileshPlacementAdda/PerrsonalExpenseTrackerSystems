package com.dollop.expensetracker.handler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dollop.expensetracker.exception.AmountException;
import com.dollop.expensetracker.exception.DuplicateResourceException;
import com.dollop.expensetracker.exception.InvalidResourceException;
import com.dollop.expensetracker.exception.ResourceNotFoundException;
import com.dollop.expensetracker.exception.UnauthorzationException;
import com.dollop.expensetracker.response.ApiResponse;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class CustomHandler {
	@ExceptionHandler(UnauthorzationException.class)
	public ResponseEntity<Map<String, String>> handleUnauthorized(UnauthorzationException ex) {
		Map<String, String> error = new HashMap<>();
		error.put("message", ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED); // or any status you want
	}

	
	@ExceptionHandler(DuplicateResourceException.class)
	public ResponseEntity<ApiResponse> handleDuplicateResource(DuplicateResourceException ex) {
	    ApiResponse response = ApiResponse.builder()
	        .message(ex.getMessage())
	        .response(null)
	        .build();

	    return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	}
	  
	  
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<String> checkResource(ResourceNotFoundException ex) {
	    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidResourceException.class)
	public ResponseEntity<String> checkInvalid(InvalidResourceException ex) {
	    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(IllegalArgumentException.class) // Corrected class name
	public ResponseEntity<String> checkArgument(IllegalArgumentException ex) {
	    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(AmountException.class)
	public ResponseEntity<ApiResponse> handleAmountException(AmountException ex) {
	    ApiResponse response = ApiResponse.builder()
	        .message(ex.getMessage())
	        .response(null)
	        .build();

	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
	  

	  
//	  @ExceptionHandler(BadRequestException.class) public ResponseEntity<String>
//	  requiredField(BadRequestException ex){ return new
//	  ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_REQUEST); }
//	  private ResponseEntity<Map<String, Object>> buildErrorResponse(HttpStatus status, String message) {
//		 Map<String, Object> error = new HashMap<>();
//		 error.put("timestamp", LocalDateTime.now());
//		 error.put("status", status.value());
//		 error.put("error", status.getReasonPhrase());
//		 error.put("message", message);
//		 return new ResponseEntity<>(error, status);
//	 }
}
