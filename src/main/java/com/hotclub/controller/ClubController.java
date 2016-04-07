package com.hotclub.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hotclub.context.web.TodayzSession;
import com.hotclub.domain.club.Club;
import com.hotclub.domain.club.Menu;
import com.hotclub.domain.member.Member;
import com.hotclub.repository.MenuRepository;
import com.hotclub.security.UserDetailsImpl;
import com.hotclub.service.ClubService;
import com.hotclub.service.MemberService;

@Controller
@Transactional
@RequestMapping("/pages/club")
public class ClubController {

	@Autowired
	private ClubService clubService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private MenuRepository menuRepository;
	// @Autowired
	// private MemberService memberService;

	// @Autowired
	// private ModelMapper modelMapper;

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
		model.addAttribute("club", club);

		// System.out.println(club.getJoiningMembers());
		List<Menu> menuList = menuRepository.findByParentClub(club);
		model.addAttribute("menuList", menuList);

		return "club/main";
	}

	@RequestMapping({ "/main/{id}/joinClub" })
	public String joinClub(@PathVariable Long id, HttpSession session, Model model) {
		if (id == null) {
			throw new NullPointerException();
		}

		TodayzSession todayzSession = TodayzSession.createSession(session);

		if (todayzSession.hasAttribute(TodayzSession.USER_KEY)) {
			UserDetailsImpl user = todayzSession.getUser();
			Member member = memberService.getMember(user.getId());
			memberService.joinClub(id, member);
		}
		/*
		 * Club club = clubService.getClub(id); model.addAttribute("club",
		 * club);
		 * 
		 * List<Menu> menuList = menuRepository.findByParentClub(club);
		 * model.addAttribute("menuList", menuList);
		 */

		return "redirect:/pages/club/main/" + id;
	}

	@RequestMapping({ "/main/{id}/menu/form" })
	public String clubMenuForm(@PathVariable Long id, Model model) {
		if (id == null) {
			throw new NullPointerException();
		}

		Club club = clubService.getClub(id);
		model.addAttribute("club", club);

		List<Menu> menuList = menuRepository.findByParentClub(club);
		model.addAttribute("menuList", menuList);

		return "club/menu/form";
	}

	@RequestMapping({ "/main/{clubId}/menu/{menuId}/item/list" })
	public String itemList(@PathVariable Long clubId, @PathVariable Long menuId, Model model) {
		if (clubId == null) {
			throw new NullPointerException();
		}

		if (menuId == null) {
			throw new NullPointerException();
		}

		Club club = clubService.getClub(clubId);
		model.addAttribute("club", club);

		List<Menu> menuList = menuRepository.findByParentClub(club);
		model.addAttribute("menuList", menuList);
		model.addAttribute("menuId", menuId);

		return "club/item/list";
	}

	@RequestMapping({ "/main/{clubId}/menu/{menuId}/item/form" })
	public String clubArticleForm(@PathVariable Long clubId, @PathVariable Long menuId, Model model) {
		if (clubId == null) {
			throw new NullPointerException();
		}

		Club club = clubService.getClub(clubId);
		model.addAttribute("club", club);

		List<Menu> menuList = menuRepository.findByParentClub(club);
		model.addAttribute("menuList", menuList);
		model.addAttribute("menuId", menuId);

		return "club/item/form";
	}

}
