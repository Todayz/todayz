package com.hotclub.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hotclub.controller.support.MemberDto;
import com.hotclub.domain.member.Member;
import com.hotclub.repository.MemberRepository;
import com.hotclub.service.MemberService;

@RestController
@SuppressWarnings("rawtypes")
public class MemberController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private ModelMapper modelMapper;

	@RequestMapping(value = "/members", method = POST)
	public ResponseEntity joinMember(@RequestBody @Valid MemberDto.Create create, BindingResult result) {
		if (result.hasErrors()) {
			// ErrorResponse errorResponse = new ErrorResponse();
			// errorResponse.setMessage("잘못된 요청입니다.");
			// errorResponse.setCode("bad.request");
			// TODO BindingResult 안에 들어있는 에러 정보 사용하기.
			// return new ResponseEntity<>(errorResponse,
			// HttpStatus.BAD_REQUEST);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Member newMember = memberService.join(create);// create(create);
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

	// 전체 업데이트: PUT
	// - (password:"pass", fullName:null)

	// 부분 업데이트: PATCH
	// - (username:"whiteship")
	// - (password:"pass")
	// - (username:"whiteship", password:"pass")
	@RequestMapping(value = "/members/{id}", method = PUT)
	public ResponseEntity update(@PathVariable Long id, @RequestBody @Valid MemberDto.Update updateDto,
			BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Member updatedMember = memberService.update(id, updateDto);
		return new ResponseEntity<>(modelMapper.map(updatedMember, MemberDto.Response.class), HttpStatus.OK);
	}

	@RequestMapping(value = "/members/{id}", method = DELETE)
	public ResponseEntity leave(@PathVariable Long id) {
		memberService.leave(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
