package com.hotclub.controller;

import javax.servlet.http.HttpSession;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hotclub.context.web.TodayzSession;
import com.hotclub.controller.support.MemberDto;
import com.hotclub.domain.member.Member;
import com.hotclub.security.UserDetailsImpl;
import com.hotclub.service.MemberService;

@Controller
@RequestMapping(value = "/pages")
public class SignController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private ModelMapper modelMapper;

	@RequestMapping(value = "/signin")
	public String signin() {
		return "sign/signin";
	}

	@RequestMapping(value = "/signup")
	public String signup(HttpSession session, Model model) {
		TodayzSession todayzSession = TodayzSession.createSession(session);

		if (todayzSession.hasAttribute(TodayzSession.USER_KEY)) {
			UserDetailsImpl user = todayzSession.getUser();
			Member member = memberService.getMember(user.getId());

			MemberDto.Response response = modelMapper.map(member, MemberDto.Response.class);
			model.addAttribute("member", response);
		}

		return "sign/signup";
	}
}
