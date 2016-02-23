package com.hotclub.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hotclub.domain.club.Club;
import com.hotclub.domain.club.Meeting;
import com.hotclub.domain.member.Member;
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

	/**
	 * 회원 가입
	 */
	@Override
	public Long join(Member member) {
		validateDuplicateMember(member);
		memberRepository.save(member);
		return member.getId();
	}

	@Override
	public void update(Member member) {
		// TODO Auto-generated method stub
		memberRepository.save(member);
	}

	@Override
	public void leave(Long id) {
		// TODO Auto-generated method stub
		memberRepository.delete(id);
	}

	/**
	 * 중복 회원 검증
	 */
	private void validateDuplicateMember(Member member) {
		Member findMember = memberRepository.findByAuthId(member.getAuthId());
		if (findMember != null) {
			throw new IllegalStateException("이미 존재하는 회원입니다.");
		}
	}

	/**
	 * 전체 회원 조회
	 */
	@Override
	public List<Member> findMembers() {
		return memberRepository.findAll();
	}

	@Override
	public Member findByAuthId(String authId) {
		return memberRepository.findByAuthId(authId);
	}

	@Override
	public Member joinClub(Long clubId, Member member) {
		// TODO Auto-generated method stub
		Club club = clubRepository.findOne(clubId);
		return clubRepository.joinClub(club, member);

	}

	@Override
	public void leaveClub(Long clubId, Member member) {
		// TODO Auto-generated method stub
		Club club = clubRepository.findOne(clubId);
		clubRepository.leaveClub(club, member);
	}

	@Override
	public Member attachMeeting(Long meetingId, Member member) {
		// TODO Auto-generated method stub
		Meeting meeting = meetingRepository.findOne(meetingId);
		return meetingRepository.attachMeeting(meeting, member);
	}

	@Override
	public void detachMeeting(Long meetingId, Member member) {
		// TODO Auto-generated method stub
		Meeting meeting = meetingRepository.findOne(meetingId);
		meetingRepository.detachMeeting(meeting, member);
	}
}
