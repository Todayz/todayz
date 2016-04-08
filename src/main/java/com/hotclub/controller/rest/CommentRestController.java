package com.hotclub.controller.rest;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hotclub.controller.support.CommentDto;
import com.hotclub.domain.common.Comment;
import com.hotclub.domain.item.Item;
import com.hotclub.repository.CommentRepository;
import com.hotclub.service.CommentService;
import com.hotclub.service.ItemService;

@RestController
@SuppressWarnings("rawtypes")
public class CommentRestController {

	@Autowired
	private ItemService<Item> itemService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private ModelMapper modelMapper;

	// 조건문에 따라 HttpStatus 를 변경해서 리턴하기 위해 ResponseEntity 로 반환한다.
	@RequestMapping(value = "/comments", method = POST)
	public ResponseEntity create(@RequestBody @Valid Comment newComment, @RequestParam(value = "itemId") Long itemId,
			BindingResult result) {

		if (result.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Item parent = itemService.getItem(itemId);
		newComment.setParent(parent);
		commentService.create(newComment);

		return new ResponseEntity<>(modelMapper.map(newComment, CommentDto.Response.class), HttpStatus.CREATED);
	}

	// http://docs.spring.io/spring-data/data-commons/docs/1.6.1.RELEASE/reference/html/repositories.html
	// 의 Table 1.1. 참조
	// articles?page=0&size=20&sort=username,asc$sort=name,asc
	@RequestMapping(value = "/comments", method = GET)
	@ResponseStatus(HttpStatus.OK)
	public List<Comment> getComments(Sort sort, @RequestParam(value = "itemId") Long itemId) {
		Item parent = itemService.getItem(itemId);
		List<Comment> comments = commentRepository.findByParent(parent);

		// 종종 스트림에 있는 값들을 특정 방식으로 변환하고 싶을때가 있다. 이 경우 map 메서드를 사용하고
		// 변환을 수행하는 함수를 파라미터로 전달한다.

		return comments;
	}

	@RequestMapping(value = "/comments/{id}", method = DELETE)
	public ResponseEntity remove(@PathVariable Long id) {
		commentService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}