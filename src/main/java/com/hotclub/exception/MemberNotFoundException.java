package com.hotclub.exception;

public class MemberNotFoundException extends RuntimeException {

	Long id;

	public MemberNotFoundException(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}
