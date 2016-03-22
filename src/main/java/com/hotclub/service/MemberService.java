package com.hotclub.service;

import com.hotclub.controller.support.MemberDto;
import com.hotclub.domain.member.Member;

// 멤버가 할수 있는 일들에 대한 정의
public interface MemberService {

	/**
	 * 회원 가입
	 */
	public Member join(MemberDto.Create dto);

	/**
	 * 수정
	 * 
	 * @param member
	 */
	public Member update(Long id, MemberDto.Update dto);

	/**
	 * @param id
	 * @return
	 */
	public Member getMember(Long id);

	/**
	 * 탈퇴
	 * 
	 * @param id
	 */
	public void leave(Long id);

	/**
	 * 클럽가입
	 * 
	 * @param member
	 * @return
	 */
	public void joinClub(Long clubId, Member member);

	/**
	 * 클럽탈퇴
	 * 
	 * @param member
	 */
	public void leaveClub(Long clubId, Member member);

	/**
	 * 모임참가
	 * 
	 * @param member
	 * @return
	 */
	public void attachMeeting(Long meetingId, Member member);

	/**
	 * 모임취소
	 * 
	 * @param member
	 */
	public void detachMeeting(Long meetingId, Member member);
}
