package com.todayz.controller.rest;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.todayz.context.web.TodayzSession;
import com.todayz.controller.support.MeetingDto;
import com.todayz.controller.support.MemberDto;
import com.todayz.domain.club.Club;
import com.todayz.domain.club.Meeting;
import com.todayz.domain.member.Member;
import com.todayz.repository.MeetingRepository;
import com.todayz.security.UserDetailsImpl;
import com.todayz.service.ClubService;
import com.todayz.service.MeetingService;
import com.todayz.service.MemberService;

@RestController
@SuppressWarnings("rawtypes")
public class MeetingRestController {

	@Autowired
	private MeetingService meetingService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private ClubService clubService;

	@Autowired
	private MeetingRepository meetingRepository;

	@Autowired
	private ModelMapper modelMapper;

	// 조건문에 따라 HttpStatus 를 변경해서 리턴하기 위해 ResponseEntity 로 반환한다.
	@RequestMapping(value = "/meetings", method = POST)
	public ResponseEntity create(@RequestBody @Valid MeetingDto.Create create,
			@RequestParam(value = "clubId") Long clubId, BindingResult result) {

		if (result.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Club parent = clubService.getClub(clubId);
		create.setParent(parent);
		Meeting newMeeting = meetingService.create(create);
		return new ResponseEntity<>(modelMapper.map(newMeeting, MeetingDto.Response.class), HttpStatus.CREATED);
	}

	// http://docs.spring.io/spring-data/data-commons/docs/1.6.1.RELEASE/reference/html/repositories.html
	// 의 Table 1.1. 참조
	// meetings?page=0&size=20&sort=username,asc$sort=name,asc
	@RequestMapping(value = "/meetings", method = GET)
	@ResponseStatus(HttpStatus.OK)
	public PageImpl<MeetingDto.Response> getMeetings(Pageable pageable) {
		Page<Meeting> page = meetingRepository.findAll(pageable);

		// 종종 스트림에 있는 값들을 특정 방식으로 변환하고 싶을때가 있다. 이 경우 map 메서드를 사용하고
		// 변환을 수행하는 함수를 파라미터로 전달한다.
		List<MeetingDto.Response> content = page.getContent().stream()
				.map(meeting -> modelMapper.map(meeting, MeetingDto.Response.class)).collect(Collectors.toList());
		return new PageImpl<>(content, pageable, page.getTotalElements());
	}

	@RequestMapping(value = "/meetings/{id}", method = GET)
	@ResponseStatus(HttpStatus.OK)
	public MeetingDto.Response getMeeting(@PathVariable Long id) {
		Meeting meeting = meetingService.getMeeting(id);
		return modelMapper.map(meeting, MeetingDto.Response.class);
	}

	// file upload 관련..참조(아래)
	// http://stackoverflow.com/questions/21329426/spring-mvc-multipart-request-with-json
	@RequestMapping(value = "/meetings/{id}", method = POST) // method = PUT)
	// @PreAuthorize("hasPermission(#meeting, admin)")
	public ResponseEntity update(@PathVariable Long id, @RequestBody @Valid MeetingDto.Update update,
			BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Meeting updatedMeeting = meetingService.update(id, update);
		return new ResponseEntity<>(modelMapper.map(updatedMeeting, MeetingDto.Response.class), HttpStatus.OK);
	}

	@RequestMapping(value = "/meetings/{id}", method = DELETE)
	public ResponseEntity leave(@PathVariable Long id) {
		meetingService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/meetings/{id}/attach", method = GET)
	public ResponseEntity attachMeeting(@PathVariable Long id, HttpSession session) {
		if (id == null) {
			throw new NullPointerException();
		}

		TodayzSession todayzSession = TodayzSession.createSession(session);

		if (todayzSession.hasAttribute(TodayzSession.USER_KEY)) {
			UserDetailsImpl user = todayzSession.getUser();
			Member member = memberService.getMember(user.getId());
			memberService.attachMeeting(id, member);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/meetings/{id}/detach", method = GET)
	public ResponseEntity detachMeeting(@PathVariable Long id, HttpSession session) {
		if (id == null) {
			throw new NullPointerException();
		}

		TodayzSession todayzSession = TodayzSession.createSession(session);

		if (todayzSession.hasAttribute(TodayzSession.USER_KEY)) {
			UserDetailsImpl user = todayzSession.getUser();
			Member member = memberService.getMember(user.getId());
			memberService.detachMeeting(id, member);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/meetings/{id}/attachMembers", method = GET)
	//@ResponseStatus(HttpStatus.OK)
	public ResponseEntity getAttachMembers(@PathVariable Long id) {
		if (id == null) {
			throw new NullPointerException();
		}
		Meeting meeting = meetingService.getMeeting(id);
		List<MemberDto.Response> content = meeting.getAttachMembers().stream()
				.map(member -> modelMapper.map(member, MemberDto.Response.class)).collect(Collectors.toList());
		return new ResponseEntity<>(content, HttpStatus.OK);
	}
}