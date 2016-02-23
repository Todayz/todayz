package com.hotclub.service;

import com.hotclub.domain.club.Meeting;

public interface MeetingService {

	public Meeting create(Meeting meeting);

	public void update(Meeting meeting);

	public void delete(Long id);
}
