package com.todayz.repository;

import java.util.List;

import com.todayz.domain.club.Meeting;
import com.todayz.domain.member.Member;

public interface MeetingRepository {
	public void save(Meeting meeting);

	public void delete(Long id);

	public Meeting findOne(Long id);

	// 추후에 paging에 대한 처리 필요.
	public List<Meeting> findAll();

	public List<Meeting> findByClubId(Long clubId);

	public Member attachMeeting(Meeting meeting, Member member);

	public void detachMeeting(Meeting meeting, Member member);
}