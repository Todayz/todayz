package com.hotclub.repository;

import java.util.List;

import com.hotclub.domain.member.Member;

public interface MemberRepository {

	public void save(Member member);

	public void delete(Long id);

	public Member findOne(Long id);

	//추후에 paging에 대한 처리 필요.
	public List<Member> findAll();

	public Member findByAuthId(String authId);

	public List<Member> findByClubId(Long clubId);

	public List<Member> findByMeetingId(Long meetingId);
}
