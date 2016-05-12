package com.todayz.controller.support;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

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

		@NotBlank(message = "인증할 아이디를 입력해주세요.")
		@Size(min = 5, max = 20, message = "아이디는 5 ~ 20자 사이의 숫자만 입력 가능합니다.")
		@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "아이디는 숫자 또는 영문만 표기 가능합니다.")
		private String authName;

		@NotBlank
		@Size(min = 5, max = 20)
		private String password;

		@NotBlank
		@Size(min = 1, max = 20)
		private String name;
		private String description;

		@NotBlank
		@Size(max = 13)
		@Pattern(regexp = "^01(?:0|1[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", message = "올바른 핸드폰 번호를 입력해주세요")
		private String phoneNumber;
		private Image profileImage;

		@NotNull
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
		@NotBlank
		@Size(min = 5, max = 20)
		private String password;

		@NotBlank
		@Size(min = 1, max = 20)
		private String name;
		private String description;

		@NotBlank
		@Size(max = 13)
		@Pattern(regexp = "^01(?:0|1[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", message = "올바른 핸드폰 번호를 입력해주세요")
		private String phoneNumber;
		private Image profileImage;

		@NotNull
		private Date birthday;
	}
}
