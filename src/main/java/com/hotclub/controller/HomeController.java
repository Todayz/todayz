package com.hotclub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/pages")
public class HomeController {
	@RequestMapping({ "/", "/index" })
	public String index(Model model) {
		return "index";
	}
}
