package com.todayz.service.impl;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.todayz.controller.support.MeetingDto;
import com.todayz.domain.club.Meeting;
import com.todayz.domain.member.Member;
import com.todayz.repository.MeetingRepository;
import com.todayz.service.MeetingService;
import com.todayz.service.MemberService;
import com.todayz.service.TodayzAclService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class MeetingServiceImpl implements MeetingService {

	@Autowired
	private MeetingRepository meetingRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private MemberService memberService;

	// @Autowired
	// private ClubService clubService;

	@Autowired
	private TodayzAclService<Meeting> todayzAclService;

	protected String getUsername() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth.getPrincipal() instanceof UserDetails) {
			return ((UserDetails) auth.getPrincipal()).getUsername();
		} else {
			return auth.getPrincipal().toString();
		}
	}

	@Override
	public Meeting create(MeetingDto.Create create) {
		Meeting meeting = modelMapper.map(create, Meeting.class);
		String authName = getUsername();
		Member member = memberService.getMemberByAuthName(authName);

		// Date now = new Date();
		// meeting.setCreatedDate(now);
		// meeting.setUpdatedDate(now);

		// meeting.setOwner(owner);
		meeting = meetingRepository.save(meeting);

		// todayzAclService.addPermission(meeting, new PrincipalSid(authName),
		// BasePermission.ADMINISTRATION);
		memberService.attachMeeting(meeting.getId(), member);

		log.info("meeting add success. {}", meeting.getTitle());

		return meeting;
	}

	@Override
	public Meeting update(Long id, MeetingDto.Update update) {

		// TODO Auto-generated method stub
		Meeting meeting = getMeeting(id);

		meeting.setTitle(update.getTitle());
		meeting.setMeetingDate(update.getMeetingDate());
		meeting.setPlace(update.getPlace());
		meeting.setAttendCosts(update.getAttendCosts());
		meeting.setQuota(update.getQuota());

		log.info("meeting update success. {}", meeting.getTitle());
		return meeting;
	}

	@Override
	public void delete(Long id) {
		Meeting meeting = getMeeting(id);
		meetingRepository.delete(meeting);
		todayzAclService.deleteAcl(meeting);
		log.info("meeting delete success. {}", meeting.getTitle());
	}

	@Override
	public Meeting getMeeting(Long id) {
		Meeting meeting = meetingRepository.findOne(id);
		if (meeting == null) {
			// throw new MeetingNotFoundException(id);
		}
		return meeting;
	}
}
