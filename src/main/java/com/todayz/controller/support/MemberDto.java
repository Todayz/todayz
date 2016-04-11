package com.todayz.controller.support;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.todayz.domain.common.Image;

import lombok.Data;

/**
 * 원하는 데이터만 주고받고 하기위해 Dto 생성. Rest 방식으로 개발할때 필요. 기존방식은 dto를 사용하지 않더라도 자기가 원하는
 * 내용만 반환할 수 있음.
 */
// TODO validation 처리
public class MemberDto {

	@Data
	public static class Create {
		private String authName;
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
		private String authName;
		private String name;
		private String description;
		private String phoneNumber;

		// http://stackoverflow.com/questions/29027475/date-format-in-the-json-output-using-spring-boot
		// json으로 데이터 내보낼 시 포멧이 원하는 대로 나오지 않아 처리한 부분
		@JsonFormat(pattern = "yyyy-MM-dd")
		private Date birthday;
		private Image profileImage;

		@JsonFormat(pattern = "yyyy-MM-dd")
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
