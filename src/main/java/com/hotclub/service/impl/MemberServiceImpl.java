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
		validateDuplicateMember(member); // 중복 회원 검증
		memberRepository.save(member);
		return member.getId();
	}

	private void validateDuplicateMember(Member member) {
		List<Member> findMembers = memberRepository.findByAuthId(member.getAuthId());
		if (!findMembers.isEmpty()) {
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
	public Member findOne(long id) {
		return memberRepository.findOne(id);
	}
}
