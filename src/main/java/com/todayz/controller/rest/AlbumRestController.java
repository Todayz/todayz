package com.todayz.controller.rest;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.todayz.controller.support.ItemDto;
import com.todayz.domain.club.Club;
import com.todayz.domain.item.PhotoAlbum;
import com.todayz.repository.ItemRepository;
import com.todayz.service.AlbumService;
import com.todayz.service.ClubService;
import com.todayz.service.ItemService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@SuppressWarnings("rawtypes")
public class AlbumRestController {

	@Autowired
	private ItemService<PhotoAlbum> itemService;

	@Autowired
	private AlbumService albumService;

	@Autowired
	private ClubService clubService;

	@Autowired
	private ItemRepository<PhotoAlbum> albumRepository;

	@Autowired
	private ModelMapper modelMapper;

	// 조건문에 따라 HttpStatus 를 변경해서 리턴하기 위해 ResponseEntity 로 반환한다.
	@RequestMapping(value = "/albums", method = POST)
	public ResponseEntity create(@RequestParam(value = "clubId") Long clubId,
			@RequestParam(value = "photo[]") MultipartFile[] photo) {
		List<PhotoAlbum> albums;
		try {
			albums = albumService.createAlbum(clubId, photo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.info("image upload problem.", e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(modelMapper.map(albums, ItemDto.Response.class), HttpStatus.CREATED);
	}

	// http://docs.spring.io/spring-data/data-commons/docs/1.6.1.RELEASE/reference/html/repositories.html
	// 의 Table 1.1. 참조
	// albums?page=0&size=20&sort=username,asc$sort=name,asc
	@RequestMapping(value = "/albums", method = GET)
	@ResponseStatus(HttpStatus.OK)
	public PageImpl<ItemDto.Response> getPhotoAlbums(Pageable pageable, @RequestParam(value = "clubId") Long clubId) {
		Club parent = clubService.getClub(clubId);
		Page<PhotoAlbum> page = albumRepository.findByParentClub(parent, pageable);

		// 종종 스트림에 있는 값들을 특정 방식으로 변환하고 싶을때가 있다. 이 경우 map 메서드를 사용하고
		// 변환을 수행하는 함수를 파라미터로 전달한다.
		List<ItemDto.Response> content = page.getContent().stream()
				.map(item -> modelMapper.map(item, ItemDto.Response.class)).collect(Collectors.toList());
		return new PageImpl<>(content, pageable, page.getTotalElements());
	}

	@RequestMapping(value = "/albums/{id}", method = GET)
	@ResponseStatus(HttpStatus.OK)
	public ItemDto.Response getPhotoAlbum(@PathVariable Long id) {
		PhotoAlbum member = itemService.getItem(id);
		return modelMapper.map(member, ItemDto.Response.class);
	}

	// file upload 관련..참조(아래)
	// http://stackoverflow.com/questions/21329426/spring-mvc-multipart-request-with-json
	@RequestMapping(value = "/albums/{id}", method = POST) // method = PUT)
	// @PreAuthorize("hasPermission(#album, admin)")
	public ResponseEntity update(@PathVariable Long id,
			@RequestParam(value = "photo", required = false) MultipartFile photo) {

		PhotoAlbum updatedPhotoAlbum = null;
		try {
			updatedPhotoAlbum = albumService.updateAlbum(id, photo);
		} catch (IOException e) {
			log.info("image upload problem.", e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(modelMapper.map(updatedPhotoAlbum, ItemDto.Response.class), HttpStatus.OK);
	}

	@RequestMapping(value = "/albums/{id}", method = DELETE)
	public ResponseEntity leave(@PathVariable Long id) {
		itemService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}