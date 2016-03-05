package com.hotclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotclub.domain.member.Member;


public interface MemberRepository extends JpaRepository<Member, Long> {
	public Member findByUsername(String username);
}
