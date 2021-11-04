package com.example.demo.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.model.ErrorResponse;
import com.example.demo.model.ExceptionClass;

@ControllerAdvice
public class ExceptionController {
	
	@ExceptionHandler(ExceptionClass.class)
	public  ResponseEntity<Object> handleBadRequestException(ExceptionClass errorResponse) {
	ErrorResponse response = new ErrorResponse();
	response.setCode(HttpStatus.BAD_REQUEST.name());
	response.setMessage(errorResponse.getMessage());
	response.setTimestamp(LocalDateTime.now());
	return new ResponseEntity<Object>(response,HttpStatus.BAD_REQUEST);
	}
}







