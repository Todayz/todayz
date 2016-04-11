package com.todayz.exception;

public class ClubNotFoundException extends RuntimeException {

	Long id;

	public ClubNotFoundException(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}