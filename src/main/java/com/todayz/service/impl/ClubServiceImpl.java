package com.todayz.service.impl;

import java.io.IOException;
import java.util.Date;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.todayz.controller.support.ClubDto;
import com.todayz.domain.club.Club;
import com.todayz.domain.club.Menu;
import com.todayz.domain.common.Image;
import com.todayz.domain.member.Member;
import com.todayz.exception.ClubNotFoundException;
import com.todayz.repository.ClubRepository;
import com.todayz.service.ClubService;
import com.todayz.service.ImageService;
import com.todayz.service.MemberService;
import com.todayz.service.MenuService;
import com.todayz.service.TodayzAclService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ClubServiceImpl implements ClubService {

	@Autowired
	private ClubRepository clubRepository;

	@Autowired
	private MenuService menuService;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private MemberService memberService;

	@Autowired
	private ImageService imageService;

	@Autowired
	private TodayzAclService<Club> todayzAclService;

	protected String getUsername() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth.getPrincipal() instanceof UserDetails) {
			return ((UserDetails) auth.getPrincipal()).getUsername();
		} else {
			return auth.getPrincipal().toString();
		}
	}

	@Override
	public Club create(ClubDto.Create create, MultipartFile mainImage) throws IOException {
		if (mainImage != null) {
			Image image = null;
			image = imageService.uploadImage(mainImage);
			create.setMainImage(image);
		}

		Club club = modelMapper.map(create, Club.class);
		String authName = getUsername();
		Member owner = memberService.getMemberByAuthName(authName);

		Date now = new Date();
		club.setCreatedDate(now);
		club.setUpdatedDate(now);

		club.setOwner(owner);
		club = clubRepository.save(club);

		todayzAclService.addPermission(club, new PrincipalSid(authName), BasePermission.ADMINISTRATION);
		memberService.joinClub(club.getId(), owner);

		// 기본 게시판
		menuService.create(new Menu("공지사항", club, true));
		menuService.create(new Menu("자유게시판", club, false));
		menuService.create(new Menu("가입인사", club, false));
		menuService.create(new Menu("관심사 공유", club, false));

		log.info("club add success. {}", club.getTitle());

		return club;
	}

	@Override
	public Club update(Long id, ClubDto.Update update, MultipartFile mainImage) throws IOException {
		if (mainImage != null) {
			Image image = null;
			image = imageService.uploadImage(mainImage);
			update.setMainImage(image);
		}

		// TODO Auto-generated method stub
		Club club = getClub(id);
		club.setTitle(update.getTitle());
		club.setMainImage(update.getMainImage());
		club.setNotice(update.getNotice());

		club.setUpdatedDate(new Date());

		log.info("club update success. {}", club.getTitle());
		return club;
	}

	@Override
	public void delete(Long id) {
		Club club = getClub(id);
		clubRepository.delete(club);
		todayzAclService.deleteAcl(club);
		log.info("club delete success. {}", club.getTitle());
	}

	@Override
	public Club getClub(Long id) {
		Club club = clubRepository.findOne(id);
		if (club == null) {
			throw new ClubNotFoundException(id);
		}
		return club;
	}
}
