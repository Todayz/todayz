package com.hotclub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/pages")
public class SignController {

	@RequestMapping(value = "/signin")
	public String signin() {
		return "sign/signin";
	}

	@RequestMapping(value = "/signup")
	public String signup() {
		return "sign/signup";
	}
}
