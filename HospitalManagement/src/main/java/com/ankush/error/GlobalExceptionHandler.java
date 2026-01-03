package com.ankush.error;

import java.nio.file.AccessDeniedException;

import javax.naming.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.JwtException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ApiError> handelUsernameNotFoundException(UsernameNotFoundException ex){
		ApiError apiError = new ApiError("Username not found with username :"+ex.getMessage(),HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(apiError,apiError.getStatusCode());
	}
	
	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ApiError> handleAuthenticationException(AuthenticationException ex){
		ApiError apiError = new ApiError("Authentication feiled : "+ex.getMessage(),HttpStatus.UNAUTHORIZED);
		return new ResponseEntity<>(apiError,apiError.getStatusCode());
	}
	
	@ExceptionHandler(JwtException.class)
	public ResponseEntity<ApiError> handleJwtException(JwtException ex){
		ApiError apiError = new ApiError("invalid Jwt token : "+ex.getMessage(),HttpStatus.UNAUTHORIZED);
		return new ResponseEntity<>(apiError,HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ApiError> handleAccessDeniedException(AccessDeniedException ex){
		ApiError apiError = new ApiError("AccessDeniedException feiled : "+ex.getMessage(),HttpStatus.UNAUTHORIZED);
		return new ResponseEntity<>(apiError,apiError.getStatusCode());
	}
	
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex) {
        ApiError apiError = new ApiError("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
