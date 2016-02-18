package com.hotclub.service;

import java.util.List;

import com.hotclub.domain.member.Member;

public interface MemberService {

	/**
	 * 회원 가입
	 */
	public Long join(Member member);

	/**
	 * 전체 회원 조회
	 */
	public List<Member> findMembers();

	public Member findOne(long id);
}
