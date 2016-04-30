package com.todayz.controller.rest;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.todayz.controller.support.MemberDto;
import com.todayz.domain.member.Member;
import com.todayz.repository.MemberRepository;
import com.todayz.service.MemberService;

@RestController
@SuppressWarnings("rawtypes")
public class MemberRestController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private ModelMapper modelMapper;

	// 조건문에 따라 HttpStatus 를 변경해서 리턴하기 위해 ResponseEntity 로 반환한다.
	// file upload 관련..참조(아래)
	// http://stackoverflow.com/questions/21329426/spring-mvc-multipart-request-with-json
	@RequestMapping(value = "/members", method = POST)
	public ResponseEntity joinMember(@RequestPart("member") @Valid MemberDto.Create create,
			@RequestParam(value = "profileImage", required = false) MultipartFile profileImage, BindingResult result) {

		if (result.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Member newMember = null;
		try {
			newMember = memberService.join(create, profileImage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(modelMapper.map(newMember, MemberDto.Response.class), HttpStatus.CREATED);
	}

	// http://docs.spring.io/spring-data/data-commons/docs/1.6.1.RELEASE/reference/html/repositories.html
	// 의 Table 1.1. 참조
	// members?page=0&size=20&sort=username,asc$sort=name,asc
	@RequestMapping(value = "/members", method = GET)
	@ResponseStatus(HttpStatus.OK)
	public PageImpl<MemberDto.Response> getMembers(Pageable pageable) {
		Page<Member> page = memberRepository.findAll(pageable);

		// 종종 스트림에 있는 값들을 특정 방식으로 변환하고 싶을때가 있다. 이 경우 map 메서드를 사용하고
		// 변환을 수행하는 함수를 파라미터로 전달한다.
		List<MemberDto.Response> content = page.getContent().stream()
				.map(account -> modelMapper.map(account, MemberDto.Response.class)).collect(Collectors.toList());
		return new PageImpl<>(content, pageable, page.getTotalElements());
	}

	@RequestMapping(value = "/members/{id}", method = GET)
	@ResponseStatus(HttpStatus.OK)
	public MemberDto.Response getMember(@PathVariable Long id) {
		Member member = memberService.getMember(id);
		return modelMapper.map(member, MemberDto.Response.class);
	}

	// put 에서 post 로 변경 이유..
	// http://stackoverflow.com/questions/30594436/multipart-with-spring-boot-rest-service
	@RequestMapping(value = "/members/{id}", method = POST)
	public ResponseEntity update(@PathVariable Long id, @RequestPart("member") @Valid MemberDto.Update updateDto,
			@RequestParam(value = "profileImage", required = false) MultipartFile profileImage, BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Member updatedMember = null;
		try {
			updatedMember = memberService.update(id, updateDto, profileImage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(modelMapper.map(updatedMember, MemberDto.Response.class), HttpStatus.OK);
	}

	@RequestMapping(value = "/members/{id}", method = DELETE)
	public ResponseEntity leave(@PathVariable Long id) {
		memberService.leave(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
