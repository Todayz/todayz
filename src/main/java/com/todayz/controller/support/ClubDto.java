package com.todayz.controller.support;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.todayz.domain.common.Image;

import lombok.Data;

public class ClubDto {

	@Data
	public static class Create {
		private String title;
		private Image mainImage;
		private String notice;
	}

	@Data
	public static class Response {
		private Long id;
		private String title;
		private Image mainImage;
		private String notice;
		@JsonFormat(pattern = "yyyy-MM-dd")
		private Date createdDate;
		@JsonFormat(pattern = "yyyy-MM-dd")
		private Date updatedDate;
	}

	@Data
	public static class Update {
		private String title;
		private Image mainImage;
		private String notice;
	}
}