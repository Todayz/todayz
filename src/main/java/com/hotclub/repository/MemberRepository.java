package com.hotclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotclub.domain.member.Member;


public interface MemberRepository extends JpaRepository<Member, Long> {
	public Member findByAuthName(String authName);
	
	//public List<Member> findByClub(Club club);
}
