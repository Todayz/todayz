package com.todayz.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todayz.controller.support.MemberDto;
import com.todayz.domain.club.Club;
import com.todayz.domain.club.Meeting;
import com.todayz.domain.member.Member;
import com.todayz.domain.member.MemberRole;
import com.todayz.exception.MemberDuplicatedException;
import com.todayz.exception.MemberNotFoundException;
import com.todayz.repository.ClubRepository;
import com.todayz.repository.MeetingRepository;
import com.todayz.repository.MemberRepository;
import com.todayz.repository.MemberRoleRepository;
import com.todayz.service.MemberService;
import com.todayz.service.TodayzAclService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private MemberRoleRepository memberRoleRepository;

	@Autowired
	private MeetingRepository meetingRepository;

	@Autowired
	private ClubRepository clubRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private TodayzAclService<Club> todayzAclService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	protected String getUsername() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth.getPrincipal() instanceof UserDetails) {
			return ((UserDetails) auth.getPrincipal()).getUsername();
		} else {
			return auth.getPrincipal().toString();
		}
	}

	/**
	 * 회원 가입
	 */
	@Override
	public Member join(MemberDto.Create dto) {
		Member member = modelMapper.map(dto, Member.class);

		String authName = dto.getAuthName();
		if (memberRepository.findByAuthName(authName) != null) {
			MemberDuplicatedException e = new MemberDuplicatedException(authName);
			log.error("member duplicated exception. {}", authName, e);
			throw e;
		}

		member.setPassword(passwordEncoder.encode(member.getPassword()));
		Date now = new Date();
		member.setJoinDate(now);

		member = memberRepository.save(member);
		MemberRole role = new MemberRole();
		role.setParent(member);
		// default
		role.setRole("ROLE_USER");
		memberRoleRepository.save(role);

		log.info("member add success. {}", authName);
		return member;
	}

	@Override
	public Member update(Long id, MemberDto.Update dto) {
		// TODO Auto-generated method stub
		Member member = getMember(id);
		if (StringUtils.isNotBlank(dto.getPassword())) {
			member.setPassword(passwordEncoder.encode(dto.getPassword()));
		}

		member.setName(dto.getName());
		member.setDescription(dto.getDescription());
		member.setPhoneNumber(dto.getPhoneNumber());
		member.setBirthday(dto.getBirthday());
		member.setProfileImage(dto.getProfileImage());

		log.info("member update success. {}", member.getAuthName());

		return member;
	}

	public Member getMember(Long id) {
		Member member = memberRepository.findOne(id);
		if (member == null) {
			MemberNotFoundException e = new MemberNotFoundException(id);
			log.error("member not found exception. {}", id, e);
			throw e;
		}

		return member;
	}

	@Override
	public Member getMemberByAuthName(String authName) {
		Member member = memberRepository.findByAuthName(authName);
		if (member == null) {
			MemberNotFoundException e = new MemberNotFoundException(authName);
			log.error("member not found exception. {}", authName, e);
			throw e;
		}
		return member;
	}

	@Override
	public void leave(Long id) {
		Member member = getMember(id);
		memberRoleRepository.removeByParent(member);
		memberRepository.delete(member);

		log.info("member leave success. {}", member.getAuthName());
	}

	/**
	 * 중복 회원 검증
	 *//*
		 * private void validateDuplicateMember(Member member) { Member
		 * findMember = memberRepository.findByUsername(member.getUsername());
		 * if (findMember != null) { throw new IllegalStateException(
		 * "이미 존재하는 회원입니다."); } }
		 */

	@Override
	public void joinClub(Long clubId, Member member) {
		Club club = clubRepository.findOne(clubId);

		List<Member> members = club.getJoiningMembers();
		members.add(member);
		// member.getJoinClubs().add(club);

		// System.out.println(club.getJoiningMembers().get(0));
		// System.out.println(member.getJoinClubs().get(0));

		// TODO Permission Read Write 추가.
		todayzAclService.addPermission(club, new PrincipalSid(getUsername()), BasePermission.READ);
		todayzAclService.addPermission(club, new PrincipalSid(getUsername()), BasePermission.WRITE);

		log.info("member join club success. {}", member.getAuthName());
	}

	@Override
	public void leaveClub(Long clubId, Member member) {
		// TODO Auto-generated method stub
		Club club = clubRepository.findOne(clubId);

		todayzAclService.deletePermission(club, new PrincipalSid(getUsername()), BasePermission.READ);
		todayzAclService.deletePermission(club, new PrincipalSid(getUsername()), BasePermission.WRITE);

		List<Member> members = club.getJoiningMembers();
		members.remove(member);

		log.info("member leave club success. {}", member.getAuthName());
	}

	@Override
	public void attachMeeting(Long meetingId, Member member) {
		// TODO Auto-generated method stub
		Meeting meeting = meetingRepository.findOne(meetingId);

		List<Member> members = meeting.getAttachMembers();
		members.add(member);

		log.info("member attach meeting success. {}", member.getAuthName());
	}

	@Override
	public void detachMeeting(Long meetingId, Member member) {
		// TODO Auto-generated method stub
		Meeting meeting = meetingRepository.findOne(meetingId);

		List<Member> members = meeting.getAttachMembers();
		members.remove(member);

		log.info("member detach meeting success. {}", member.getAuthName());
	}
}
