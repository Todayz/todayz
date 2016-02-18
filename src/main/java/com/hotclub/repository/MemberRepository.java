package com.hotclub.repository;

import java.util.List;

import com.hotclub.domain.member.Member;

public interface MemberRepository {

	public void save(Member member);

	public Member findOne(long id);

	public List<Member> findAll();

	public List<Member> findByAuthId(String authId);
}
