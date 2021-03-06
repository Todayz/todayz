package com.todayz.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.todayz.exception.ClubNotFoundException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

	@ExceptionHandler(ClubNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleClubNotFoundException(ClubNotFoundException e) {
		return "error/error404";
	}
}