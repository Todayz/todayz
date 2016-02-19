package com.hotclub.repository;

import java.util.List;

import com.hotclub.domain.club.Meeting;

public interface MeetingRepository {
	public void save(Meeting meeting);

	public Meeting findOne(Long id);

	public List<Meeting> findAll();

	public List<Meeting> findByClubId(Long clubId);
}
