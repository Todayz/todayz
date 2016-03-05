package com.hotclub.ui.controller;

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

import com.hotclub.domain.member.Member;
import com.hotclub.repository.MemberRepository;
import com.hotclub.service.MemberService;
import com.hotclub.ui.controller.support.MemberDto;

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

	/*// TODO stream() vs parallelStream()
	// TODO HATEOAS
	// TODO 뷰
	// TODO boot를 프로덕션에 배포할때 튜닝포인트(?) 궁금해요~ ^^ (강대권)
	// NSPA 1. Thymeleaf
	// SPA 2. 앵귤러 3. 리액트
	@RequestMapping(value = "/members", method = GET)
	@ResponseStatus(HttpStatus.OK)
	public PageImpl<MemberDto.Response> getMembers(Pageable pageable) {
		Page<Member> page = memberRepository.findAll(pageable);
		List<MemberDto.Response> content = page.getContent().parallelStream()
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
	}*/

	/*
	 * // TODO 예외 처리 네번째 방법 (콜백 비스무리한거...)
	 * 
	 * @ExceptionHandler(UserDuplicatedException.class)
	 * 
	 * @ResponseStatus(HttpStatus.BAD_REQUEST) public ErrorResponse
	 * handleUserDuplicatedException(UserDuplicatedException e) { ErrorResponse
	 * errorResponse = new ErrorResponse(); errorResponse.setMessage("[" +
	 * e.getUsername() + "] 중복된 username 입니다.");
	 * errorResponse.setCode("duplicated.username.exception"); return
	 * errorResponse; }
	 * 
	 * @ExceptionHandler(MemberNotFoundException.class)
	 * 
	 * @ResponseStatus(HttpStatus.BAD_REQUEST) public ErrorResponse
	 * handleMemberNotFoundException(MemberNotFoundException e) { ErrorResponse
	 * errorResponse = new ErrorResponse(); errorResponse.setMessage("[" +
	 * e.getId() + "]에 해당하는 계정이 없습니다.");
	 * errorResponse.setCode("account.not.found.exception"); return
	 * errorResponse; }
	 */
}
