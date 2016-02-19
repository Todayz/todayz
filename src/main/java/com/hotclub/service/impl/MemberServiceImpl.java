package com.hotclub.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hotclub.domain.member.Member;
import com.hotclub.repository.MemberRepository;
import com.hotclub.service.MemberService;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberRepository memberRepository;

	/**
	 * 회원 가입
	 */
	@Override
	public Long join(Member member) {
		validateDuplicateMember(member);
		memberRepository.save(member);
		return member.getId();
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
	public Member findOne(Long id) {
		return memberRepository.findOne(id);
	}
}
