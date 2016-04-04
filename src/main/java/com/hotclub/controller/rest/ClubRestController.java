package com.hotclub.controller.rest;

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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hotclub.controller.support.ClubDto;
import com.hotclub.domain.club.Club;
import com.hotclub.domain.common.Image;
import com.hotclub.repository.ClubRepository;
import com.hotclub.service.ClubService;
import com.hotclub.service.ImageService;

@RestController
@SuppressWarnings("rawtypes")
public class ClubRestController {

	@Autowired
	private ClubService clubService;

	@Autowired
	private ImageService imageService;

	@Autowired
	private ClubRepository clubRepository;

	@Autowired
	private ModelMapper modelMapper;

	// 조건문에 따라 HttpStatus 를 변경해서 리턴하기 위해 ResponseEntity 로 반환한다.
	@RequestMapping(value = "/clubs", method = POST)
	public ResponseEntity create(@RequestPart("club") @Valid ClubDto.Create create,
			@RequestParam(value = "mainImage", required = false) MultipartFile mainImage, BindingResult result) {

		if (result.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		if (mainImage != null) {
			Image image = null;
			try {
				image = imageService.uploadImage(mainImage);
			} catch (IOException e) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			create.setMainImage(image);
		}

		Club newClub = clubService.create(create);// create(create);
		return new ResponseEntity<>(modelMapper.map(newClub, ClubDto.Response.class), HttpStatus.CREATED);
	}

	// http://docs.spring.io/spring-data/data-commons/docs/1.6.1.RELEASE/reference/html/repositories.html
	// 의 Table 1.1. 참조
	// clubs?page=0&size=20&sort=username,asc$sort=name,asc
	@RequestMapping(value = "/clubs", method = GET)
	@ResponseStatus(HttpStatus.OK)
	public PageImpl<ClubDto.Response> getClubs(Pageable pageable) {
		Page<Club> page = clubRepository.findAll(pageable);

		// 종종 스트림에 있는 값들을 특정 방식으로 변환하고 싶을때가 있다. 이 경우 map 메서드를 사용하고
		// 변환을 수행하는 함수를 파라미터로 전달한다.
		List<ClubDto.Response> content = page.getContent().stream()
				.map(account -> modelMapper.map(account, ClubDto.Response.class)).collect(Collectors.toList());
		return new PageImpl<>(content, pageable, page.getTotalElements());
	}

	@RequestMapping(value = "/clubs/{id}", method = GET)
	@ResponseStatus(HttpStatus.OK)
	public ClubDto.Response getClub(@PathVariable Long id) {
		Club member = clubService.getClub(id);
		return modelMapper.map(member, ClubDto.Response.class);
	}

	// file upload 관련..참조(아래)
	// http://stackoverflow.com/questions/21329426/spring-mvc-multipart-request-with-json
	@RequestMapping(value = "/clubs/{id}", method = POST) // method = PUT)
	@PreAuthorize("hasPermission(#club, admin)")
	public ResponseEntity update(@PathVariable Long id, @RequestPart("club") @Valid ClubDto.Update updateDto,
			@RequestParam(value = "mainImage", required = false) MultipartFile mainImage, BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		if (mainImage != null) {
			Image image = null;
			try {
				image = imageService.uploadImage(mainImage);
			} catch (IOException e) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			updateDto.setMainImage(image);
		}

		Club updatedClub = clubService.update(id, updateDto);
		return new ResponseEntity<>(modelMapper.map(updatedClub, ClubDto.Response.class), HttpStatus.OK);
	}

	@RequestMapping(value = "/clubs/{id}", method = DELETE)
	public ResponseEntity leave(@PathVariable Long id) {
		clubService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
