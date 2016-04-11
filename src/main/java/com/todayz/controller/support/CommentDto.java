package com.todayz.controller.support;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

public class CommentDto {

	@Data
	public static class Response {
		private Long id;
		private String title;
		private ItemDto.Response parent;
		private MemberDto.Response writer;
		private String content;
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		private Date createdDate;
	}
}
