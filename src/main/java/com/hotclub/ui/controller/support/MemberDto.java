package com.hotclub.ui.controller.support;

import java.util.Date;

import com.hotclub.domain.common.Image;

import lombok.Data;

public class MemberDto {

	@Data
	public static class Create {
		private String username;
		private String password;
		private String name;
		private String description;
		private String phoneNumber;
		private Image profileImage;
		private Date birthday;
	}

	@Data
	public static class Response {
		private Long id;
		private String username;
		private String name;
		private String description;
		private String phoneNumber;
		private Date birthday;
		private Image profileImage;
		private Date joinDate;
	}

	@Data
	public static class Update {
		private String password;
		private String name;
		private String description;
		private String phoneNumber;
		private Date birthday;
		private Image profileImage;
	}
}
