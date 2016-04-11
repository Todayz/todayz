package com.todayz.exception;

public class MemberNotFoundException extends RuntimeException {

	private Long id;

	private String authName;

	public MemberNotFoundException(Long id) {
		this.id = id;
	}

	public MemberNotFoundException(String authName) {
		this.authName = authName;
	}

	public Long getId() {
		return id;
	}

	public String getAuthName() {
		return authName;
	}
}
