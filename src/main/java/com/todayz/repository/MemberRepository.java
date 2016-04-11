package com.todayz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todayz.domain.member.Member;


public interface MemberRepository extends JpaRepository<Member, Long> {
	public Member findByAuthName(String authName);
	
	//public List<Member> findByClub(Club club);
}
