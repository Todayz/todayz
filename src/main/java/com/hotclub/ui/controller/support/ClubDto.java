package com.hotclub.ui.controller.support;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hotclub.domain.common.Image;
import com.hotclub.domain.member.Member;

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
		private List<Member> joiningMembers = new ArrayList<>();
		private Date createdDate;
		private Date updatedDate;
	}

	@Data
	public static class Update {
		private String title;
		private Image mainImage;
		private String notice;
	}
}