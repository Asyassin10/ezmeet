package com.example.EzMeet.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(
	        MethodArgumentNotValidException ex) {
	    
	    System.out.println("=== VALIDATION EXCEPTION CAUGHT ===");
	    System.out.println("Exception: " + ex.getMessage());
	    
	    Map<String, String> errors = new HashMap<>();
	    
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	        System.out.println("Field: " + fieldName + " | Error: " + errorMessage);
	    });
	    
	    System.out.println("Returning errors: " + errors);
	    System.out.println("===================================");
	    
	    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

    @ExceptionHandler(org.springframework.security.core.AuthenticationException.class)
    public ResponseEntity<?> handleAuthException(Exception ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse("UNAUTHORIZED", ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("BAD_REQUEST", ex.getMessage()));
    }

    // Generic exception handler should be LAST (lowest priority)
    @ExceptionHandler(Exception.class)
    @Order(Ordered.LOWEST_PRECEDENCE)
    public ResponseEntity<?> handleGeneral(Exception ex) {
        System.out.println("Generic exception caught: " + ex.getClass().getSimpleName() + " - " + ex.getMessage()); // Debug log
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("INTERNAL_SERVER_ERROR", ex.getMessage()));
    }

    record ErrorResponse(String error, String message) {}
}