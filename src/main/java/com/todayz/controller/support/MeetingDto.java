package com.todayz.controller.support;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.todayz.domain.club.Club;

import lombok.Data;

public class MeetingDto {

	@Data
	public static class Create {
		private String title;
		private Date meetingDate;
		private String place;
		private String attendCosts;
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
		//private Club parent;
	}

	@Data
	public static class Update {
		private String title;
		private Date meetingDate;
		private String place;
		private String attendCosts;
		private Long quota;
	}
}
