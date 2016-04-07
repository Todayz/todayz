package com.hotclub.service.impl;

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

import com.hotclub.controller.support.ClubDto;
import com.hotclub.domain.club.Club;
import com.hotclub.domain.member.Member;
import com.hotclub.exception.ClubNotFoundException;
import com.hotclub.repository.ClubRepository;
import com.hotclub.service.ClubService;
import com.hotclub.service.MemberService;
import com.hotclub.service.TodayzAclService;

@Service
@Transactional
public class ClubServiceImpl implements ClubService {

	@Autowired
	private ClubRepository clubRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private MemberService memberService;

	// @Autowired
	// private MutableAclService mutableAclService;

	@Autowired
	private TodayzAclService<Club> todayzAclService;

	/**
	 * @param club
	 * @param recipient
	 * @param permission
	 */
	/*
	 * protected void addPermission(Club club, Sid recipient, Permission
	 * permission) { MutableAcl acl; ObjectIdentity oid = new
	 * ObjectIdentityImpl(Club.class, club.getId());
	 * 
	 * try { acl = (MutableAcl) mutableAclService.readAclById(oid); } catch
	 * (NotFoundException nfe) { acl = mutableAclService.createAcl(oid); }
	 * 
	 * acl.insertAce(acl.getEntries().size(), permission, recipient, true);
	 * mutableAclService.updateAcl(acl);
	 * 
	 * // logger.debug("Added permission " + permission + " for Sid " + //
	 * recipient // + " contact " + contact); }
	 */

	protected String getUsername() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth.getPrincipal() instanceof UserDetails) {
			return ((UserDetails) auth.getPrincipal()).getUsername();
		} else {
			return auth.getPrincipal().toString();
		}
	}

	@Override
	public Club create(ClubDto.Create dto) {
		Club club = modelMapper.map(dto, Club.class);
		String authName = getUsername();
		Member owner = memberService.getMemberByAuthName(authName);

		Date now = new Date();
		club.setCreatedDate(now);
		club.setUpdatedDate(now);

		club.setOwner(owner);
		club = clubRepository.save(club);

		todayzAclService.addPermission(club, new PrincipalSid(authName), BasePermission.ADMINISTRATION);
		memberService.joinClub(club.getId(), owner);

		return club;
	}

	@Override
	public Club update(Long id, ClubDto.Update dto) {
		// TODO Auto-generated method stub
		Club club = getClub(id);
		club.setTitle(dto.getTitle());
		club.setMainImage(dto.getMainImage());
		club.setNotice(dto.getNotice());

		club.setUpdatedDate(new Date());
		return club;
	}

	@Override
	public void delete(Long id) {
		Club club = getClub(id);
		clubRepository.delete(club);
		todayzAclService.deleteAcl(club);
		/*
		 * if (logger.isDebugEnabled()) { logger.debug("Deleted contact " +
		 * contact + " including ACL permissions"); }
		 */
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
