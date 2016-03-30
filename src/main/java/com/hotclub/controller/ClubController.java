package com.hotclub.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hotclub.controller.support.ClubDto;
import com.hotclub.domain.club.Club;
import com.hotclub.domain.club.Menu;
import com.hotclub.repository.MenuRepository;
import com.hotclub.service.ClubService;

@Controller
@RequestMapping("/pages/club")
public class ClubController {

	@Autowired
	private ClubService clubService;

	@Autowired
	private MenuRepository menuRepository;
	// @Autowired
	// private MemberService memberService;

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

		List<Menu> menuList = menuRepository.findByParentClub(club);
		model.addAttribute("menuList", menuList);

		return "club/main";
	}

	@RequestMapping({ "/main/{id}/menu/form" })
	public String clubMenuForm(@PathVariable Long id, Model model) {
		if (id == null) {
			throw new NullPointerException();
		}

		Club club = clubService.getClub(id);

		ClubDto.Response response = modelMapper.map(club, ClubDto.Response.class);
		model.addAttribute("club", response);

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

		ClubDto.Response response = modelMapper.map(club, ClubDto.Response.class);
		model.addAttribute("club", response);

		List<Menu> menuList = menuRepository.findByParentClub(club);
		model.addAttribute("menuList", menuList);

		return "club/item/list";
	}
}
