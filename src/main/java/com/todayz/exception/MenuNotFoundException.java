package com.todayz.exception;

public class MenuNotFoundException extends RuntimeException{
	Long id;

	public MenuNotFoundException(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}
