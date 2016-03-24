package com.hotclub.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.hotclub.commons.ErrorResponse;
import com.hotclub.exception.MemberDuplicatedException;
import com.hotclub.exception.MemberNotFoundException;

@ControllerAdvice
@ResponseBody
public class GlobalRestControllerExceptionHandler {

	@ExceptionHandler(MemberDuplicatedException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleMemberDuplicatedException(MemberDuplicatedException e) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage("[" + e.getUsername() + "] 중복된 username 입니다.");
		errorResponse.setCode("duplicated.authName.exception");
		return errorResponse;
	}

	@ExceptionHandler(MemberNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleMemberNotFoundException(MemberNotFoundException e) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage("[" + e.getId() + "]에 해당하는 계정이 없습니다.");
		errorResponse.setCode("member.not.found.exception");
		return errorResponse;
	}
}