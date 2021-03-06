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

import com.todayz.controller.support.ItemDto;
import com.todayz.domain.club.Menu;
import com.todayz.domain.item.Article;
import com.todayz.repository.ItemRepository;
import com.todayz.service.ArticleService;
import com.todayz.service.ItemService;
import com.todayz.service.MenuService;

@RestController
@SuppressWarnings("rawtypes")
public class ArticleRestController {

	@Autowired
	private ItemService<Article> itemService;

	@Autowired
	private ArticleService articleService;

	@Autowired
	private MenuService menuService;

	@Autowired
	private ItemRepository<Article> articleRepository;

	@Autowired
	private ModelMapper modelMapper;

	// 조건문에 따라 HttpStatus 를 변경해서 리턴하기 위해 ResponseEntity 로 반환한다.
	@RequestMapping(value = "/articles", method = POST)
	public ResponseEntity create(@RequestPart("article") @Valid ItemDto.Create create,
			@RequestParam(value = "menuId") Long menuId,
			@RequestParam(value = "articleImage", required = false) MultipartFile articleImage, BindingResult result) {

		if (result.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Article newArticle = null;
		try {
			newArticle = articleService.createArticle(create, articleImage, menuId);
		} catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(modelMapper.map(newArticle, ItemDto.Response.class), HttpStatus.CREATED);
	}

	// http://docs.spring.io/spring-data/data-commons/docs/1.6.1.RELEASE/reference/html/repositories.html
	// 의 Table 1.1. 참조
	// articles?page=0&size=20&sort=username,asc$sort=name,asc
	@RequestMapping(value = "/articles", method = GET)
	@ResponseStatus(HttpStatus.OK)
	public PageImpl<ItemDto.Response> getArticles(Pageable pageable, @RequestParam(value = "menuId") Long menuId) {
		Menu parent = menuService.getMenu(menuId);
		Page<Article> page = articleRepository.findByParent(parent, pageable);

		// 종종 스트림에 있는 값들을 특정 방식으로 변환하고 싶을때가 있다. 이 경우 map 메서드를 사용하고
		// 변환을 수행하는 함수를 파라미터로 전달한다.
		List<ItemDto.Response> content = page.getContent().stream()
				.map(item -> modelMapper.map(item, ItemDto.Response.class)).collect(Collectors.toList());
		return new PageImpl<>(content, pageable, page.getTotalElements());
	}

	@RequestMapping(value = "/articles/{id}", method = GET)
	@ResponseStatus(HttpStatus.OK)
	public ItemDto.Response getArticle(@PathVariable Long id) {
		Article member = itemService.getItem(id);
		return modelMapper.map(member, ItemDto.Response.class);
	}

	// file upload 관련..참조(아래)
	// http://stackoverflow.com/questions/21329426/spring-mvc-multipart-request-with-json
	@RequestMapping(value = "/articles/{id}", method = POST) // method = PUT)
	// @PreAuthorize("hasPermission(#article, admin)")
	public ResponseEntity update(@PathVariable Long id, @RequestPart("article") @Valid ItemDto.Update updateDto,
			@RequestParam(value = "articleImage", required = false) MultipartFile articleImage, BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Article updatedArticle;
		try {
			updatedArticle = articleService.updateArticle(updateDto, articleImage, id);
		} catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(modelMapper.map(updatedArticle, ItemDto.Response.class), HttpStatus.OK);
	}

	@RequestMapping(value = "/articles/{id}", method = DELETE)
	public ResponseEntity leave(@PathVariable Long id) {
		itemService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}