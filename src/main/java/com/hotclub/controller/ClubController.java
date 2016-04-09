package com.hotclub.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hotclub.context.web.TodayzSession;
import com.hotclub.domain.club.Club;
import com.hotclub.domain.club.Menu;
import com.hotclub.domain.common.Comment;
import com.hotclub.domain.item.Article;
import com.hotclub.domain.item.Item;
import com.hotclub.domain.member.Member;
import com.hotclub.repository.CommentRepository;
import com.hotclub.repository.MenuRepository;
import com.hotclub.security.UserDetailsImpl;
import com.hotclub.service.ClubService;
import com.hotclub.service.ItemService;
import com.hotclub.service.MemberService;

//리팩토링 필요.
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

	@Autowired
	private ItemService<Item> articleService;

	// @Autowired
	// private MemberService memberService;
	@Autowired
	private CommentRepository commentRepository;

	//@Autowired
	//private ModelMapper modelMapper;

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
	public String clubArticleForm(@PathVariable Long clubId, @PathVariable Long menuId,
			@RequestParam(value = "itemId", required = false) Long itemId, Model model) {
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

		if (itemId != null) {
			Article article = (Article) articleService.getItem(itemId);
			model.addAttribute("article", article);
		}

		return "club/item/form";
	}

	@RequestMapping({ "/main/{clubId}/menu/{menuId}/item/{itemId}" })
	public String itemContent(@PathVariable Long clubId, @PathVariable Long menuId, @PathVariable Long itemId,
			Model model) {
		if (clubId == null) {
			throw new NullPointerException();
		}

		if (menuId == null) {
			throw new NullPointerException();
		}

		if (itemId == null) {
			throw new NullPointerException();
		}

		Club club = clubService.getClub(clubId);
		model.addAttribute("club", club);

		List<Menu> menuList = menuRepository.findByParentClub(club);
		Article article = (Article) articleService.getItem(itemId);

		List<Comment> commentList = commentRepository.findByParent(article);
		model.addAttribute("menuList", menuList);
		model.addAttribute("menuId", menuId);
		model.addAttribute("article", article);
		model.addAttribute("commentList", commentList);

		return "club/item/content";
	}
}