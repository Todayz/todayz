package com.todayz.controller.support;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.todayz.domain.common.Image;

import lombok.Data;

public class ItemDto {

	@Data
	public static class Create {
		private String title;
		private Image articleImage;
		private Image photo;
		private String content;
	}

	@Data
	public static class Response {
		private Long id;
		private String title;
		// private Menu parent;
		private MemberDto.Response writer;
		private Image articleImage;
		private Image photo;
		private String content;
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		private Date createdDate;
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		private Date updatedDate;
	}

	@Data
	public static class Update {
		private String title;
		private Image articleImage;
		private Image photo;
		private String content;
	}
}