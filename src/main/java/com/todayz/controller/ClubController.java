package com.todayz.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.todayz.context.web.TodayzSession;
import com.todayz.domain.club.Club;
import com.todayz.domain.club.Menu;
import com.todayz.domain.common.Comment;
import com.todayz.domain.item.Article;
import com.todayz.domain.item.Item;
import com.todayz.domain.item.PhotoAlbum;
import com.todayz.domain.member.Member;
import com.todayz.repository.CommentRepository;
import com.todayz.repository.MenuRepository;
import com.todayz.security.UserDetailsImpl;
import com.todayz.service.ClubService;
import com.todayz.service.ItemService;
import com.todayz.service.MemberService;

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
	private ItemService<Item> itemService;

	// @Autowired
	// private MemberService memberService;
	@Autowired
	private CommentRepository commentRepository;

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

	@RequestMapping({ "/main/{clubId}/menu/{menuId}/{itemType}/list" })
	public String itemList(@PathVariable Long clubId, @PathVariable Long menuId, @PathVariable String itemType,
			Model model) {
		if (clubId == null) {
			throw new NullPointerException();
		}

		Club club = clubService.getClub(clubId);
		model.addAttribute("club", club);

		List<Menu> menuList = menuRepository.findByParentClub(club);
		model.addAttribute("menuList", menuList);
		model.addAttribute("menuId", menuId);

		return "club/" + itemType + "/list";
	}

	@RequestMapping({ "/main/{clubId}/menu/{menuId}/{itemType}/form" })
	public String clubItemForm(@PathVariable Long clubId, @PathVariable Long menuId, @PathVariable String itemType,
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
			Item item = itemService.getItem(itemId);
			if (itemType.equals("article")) {
				model.addAttribute("article", (Article) item);
			} else {
				model.addAttribute("album", (PhotoAlbum) item);
			}
		}

		return "club/" + itemType + "/form";
	}

	@RequestMapping({ "/main/{clubId}/menu/{menuId}/{itemType}/{itemId}" })
	public String itemContent(@PathVariable Long clubId, @PathVariable Long menuId, @PathVariable Long itemId,
			@PathVariable String itemType, Model model) {
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
		Item item = itemService.getItem(itemId);

		List<Comment> commentList = commentRepository.findByParent(item);
		model.addAttribute("menuList", menuList);
		model.addAttribute("menuId", menuId);
		if (itemType.equals("article")) {
			model.addAttribute("article", (Article) item);
		} else {
			model.addAttribute("album", (PhotoAlbum) item);
		}
		model.addAttribute("commentList", commentList);

		return "club/" + itemType + "/content";
	}
}