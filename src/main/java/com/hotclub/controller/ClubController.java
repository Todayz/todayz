package com.hotclub.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hotclub.controller.support.ClubDto;
import com.hotclub.domain.club.Club;
import com.hotclub.service.ClubService;
import com.hotclub.service.MemberService;

@Controller
@RequestMapping("/pages/club")
public class ClubController {

	@Autowired
	private ClubService clubService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private ModelMapper modelMapper;

	@RequestMapping({ "/form" })
	public String clubForm() {
		return "club/form";
	}

	@RequestMapping({ "/main/{id}" })
	public String clubMain(@PathVariable Long id, Model model) {
		if (id == null) {
			throw new NullPointerException();
		}

		Club club = clubService.getClub(id);

		ClubDto.Response response = modelMapper.map(club, ClubDto.Response.class);
		model.addAttribute("club", response);

		return "club/main";
	}
}
