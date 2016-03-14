package com.hotclub.service.impl;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hotclub.controller.support.MemberDto;
import com.hotclub.domain.club.Club;
import com.hotclub.domain.club.Meeting;
import com.hotclub.domain.member.Member;
import com.hotclub.exception.MemberDuplicatedException;
import com.hotclub.exception.MemberNotFoundException;
import com.hotclub.repository.ClubRepository;
import com.hotclub.repository.MeetingRepository;
import com.hotclub.repository.MemberRepository;
import com.hotclub.service.MemberService;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private MeetingRepository meetingRepository;

	@Autowired
	private ClubRepository clubRepository;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * 회원 가입
	 */
	@Override
	public Member join(MemberDto.Create dto) {
		Member member = modelMapper.map(dto, Member.class);

		String authName = dto.getAuthName();
		if (memberRepository.findByAuthName(authName) != null) {
			// log.error("user duplicated exception. {}", username);
			throw new MemberDuplicatedException(authName);
		}

		// account.setPassword(passwordEncoder.encode(account.getPassword()));

		Date now = new Date();
		member.setJoinDate(now);
		return memberRepository.save(member);
	}

	@Override
	public Member update(Long id, MemberDto.Update dto) {
		// TODO Auto-generated method stub
		Member member = getMember(id);
		member.setPassword(dto.getPassword());
		member.setName(dto.getName());
		member.setDescription(dto.getDescription());
		member.setPhoneNumber(dto.getPhoneNumber());
		member.setBirthday(dto.getBirthday());
		member.setProfileImage(dto.getProfileImage());

		return memberRepository.save(member);
	}

	public Member getMember(Long id) {
		Member member = memberRepository.findOne(id);
		if (member == null) {
			throw new MemberNotFoundException(id);
		}

		return member;
	}

	@Override
	public void leave(Long id) {
		memberRepository.delete(getMember(id));
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
		// TODO Auto-generated method stub
		Club club = clubRepository.findOne(clubId);

		List<Member> members = club.getJoiningMembers();
		members.add(member);
	}

	@Override
	public void leaveClub(Long clubId, Member member) {
		// TODO Auto-generated method stub
		Club club = clubRepository.findOne(clubId);

		List<Member> members = club.getJoiningMembers();
		members.remove(member);
	}

	@Override
	public void attachMeeting(Long meetingId, Member member) {
		// TODO Auto-generated method stub
		Meeting meeting = meetingRepository.findOne(meetingId);

		List<Member> members = meeting.getAttachMembers();
		members.add(member);
	}

	@Override
	public void detachMeeting(Long meetingId, Member member) {
		// TODO Auto-generated method stub
		Meeting meeting = meetingRepository.findOne(meetingId);

		List<Member> members = meeting.getAttachMembers();
		members.remove(member);
	}
}
