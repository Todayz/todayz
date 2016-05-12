package com.todayz.controller.support;

import java.util.Date;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.todayz.domain.club.Club;

import lombok.Data;

public class MeetingDto {

	@Data
	public static class Create {
		@NotBlank
		@Size(max = 50)
		private String title;
		@NotNull
		private Date meetingDate;
		@NotBlank
		private String place;
		@NotBlank
		private String attendCosts;
		@DecimalMax(value = "180")
		@Digits(integer = 3, fraction = 0)
		private Long quota;
		private Club parent;
	}

	@Data
	public static class Response {
		private Long id;
		private String title;
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		private Date meetingDate;
		private String place;
		private String attendCosts;
		private Long quota;
		// private Club parent;
	}

	@Data
	public static class Update {
		@NotBlank
		@Size(max = 50)
		private String title;
		@NotNull
		private Date meetingDate;
		@NotBlank
		private String place;
		@NotBlank
		private String attendCosts;

		@DecimalMax(value = "180")
		@Digits(integer = 3, fraction = 0)
		private Long quota;
	}
}
