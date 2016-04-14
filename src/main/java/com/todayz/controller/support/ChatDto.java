package com.todayz.controller.support;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.todayz.domain.club.Club;

import lombok.Data;

public class ChatDto {

	@Data
	public static class Create {
		private String content;
	}

	@Data
	public static class Response {
		private Long id;
		private MemberDto.Response writer;
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		private Date createdDate;
		private String content;
	}
}
