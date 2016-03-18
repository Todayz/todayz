package com.hotclub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	@RequestMapping({ "/pages/index" })
	public String index() {
		return "index";
	}

	@RequestMapping({ "/", "/pages/home" })
	public String home() {
		return "home";
	}
}
