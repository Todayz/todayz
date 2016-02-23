package com.hotclub.service;

import java.util.List;

import com.hotclub.domain.member.Member;

// 멤버가 할수 있는 일들에 대한 정의
public interface MemberService {

	/**
	 * 회원 가입
	 */
	public Long join(Member member);

	/**
	 * 수정
	 * 
	 * @param member
	 */
	public void update(Member member);

	/**
	 * 탈퇴
	 * 
	 * @param id
	 */
	public void leave(Long id);

	/**
	 * 전체 회원 조회
	 */
	public List<Member> findMembers();

	/**
	 * 클럽가입
	 * 
	 * @param member
	 * @return
	 */
	public Member joinClub(Long clubId, Member member);

	/**
	 * 클럽탈퇴
	 * 
	 * @param member
	 */
	public void leaveClub(Long clubId, Member member);

	/**
	 * 모임참
	 * 
	 * @param member
	 * @return
	 */
	public Member attachMeeting(Long meetingId, Member member);

	/**
	 * 모임취소
	 * 
	 * @param member
	 */
	public void detachMeeting(Long meetingId, Member member);

	public Member findByAuthId(String authId);
}
