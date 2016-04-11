package com.todayz.service;

import com.todayz.domain.club.Meeting;

public interface MeetingService {

	public Meeting create(Meeting meeting);

	public void update(Meeting meeting);

	public void delete(Long id);
}
