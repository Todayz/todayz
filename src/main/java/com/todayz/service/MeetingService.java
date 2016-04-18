package com.todayz.service;

import com.todayz.controller.support.MeetingDto;
import com.todayz.domain.club.Meeting;

public interface MeetingService {

	public Meeting create(MeetingDto.Create create);

	public Meeting update(Long id, MeetingDto.Update update);

	public void delete(Long id);

	public Meeting getMeeting(Long id);
}
