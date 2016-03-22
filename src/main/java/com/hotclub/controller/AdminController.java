package com.hotclub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pages/admin")
public class AdminController {

	@RequestMapping({ "/member/list" })
	public String adminMember() {
		return "admin/member/list";
	}
}
