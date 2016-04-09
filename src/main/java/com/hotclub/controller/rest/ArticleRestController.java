package com.hotclub.controller.rest;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
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

import com.hotclub.controller.support.ItemDto;
import com.hotclub.domain.club.Menu;
import com.hotclub.domain.common.Image;
import com.hotclub.domain.item.Article;
import com.hotclub.repository.ItemRepository;
import com.hotclub.service.ImageService;
import com.hotclub.service.ItemService;
import com.hotclub.service.MenuService;

@RestController
@SuppressWarnings("rawtypes")
public class ArticleRestController {

	@Autowired
	private ItemService<Article> articleService;

	@Autowired
	private ImageService imageService;

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

		if (articleImage != null) {
			Image image = null;
			try {
				image = imageService.uploadImage(articleImage);
			} catch (IOException e) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			create.setArticleImage(image);
		}
		Article newArticle = modelMapper.map(create, Article.class);
		Menu parent = menuService.getMenu(menuId);
		newArticle.setParent(parent);
		newArticle = articleService.create(newArticle);// create(create);
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
		Article member = articleService.getItem(id);
		return modelMapper.map(member, ItemDto.Response.class);
	}

	// file upload 관련..참조(아래)
	// http://stackoverflow.com/questions/21329426/spring-mvc-multipart-request-with-json
	@RequestMapping(value = "/articles/{id}", method = POST) // method = PUT)
	// @PreAuthorize("hasPermission(#article, admin)")
	@Transactional
	public ResponseEntity update(@PathVariable Long id, @RequestPart("article") @Valid ItemDto.Update updateDto,
			@RequestParam(value = "articleImage", required = false) MultipartFile articleImage, BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		if (articleImage != null) {
			Image image = null;
			try {
				image = imageService.uploadImage(articleImage);
			} catch (IOException e) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			updateDto.setArticleImage(image);
		}

		Article updatedArticle = modelMapper.map(updateDto, Article.class);
		Article article = articleService.getItem(id);

		// update 메소드에서 할 수 있는 방법이 없는지 고민 필요.
		article.setTitle(updatedArticle.getTitle());
		article.setContent(updatedArticle.getContent());
		if (updatedArticle.getArticleImage() != null) {
			article.setArticleImage(updatedArticle.getArticleImage());
		}
		article.setUpdatedDate(new Date());

		// updatedArticle = articleService.update(id, updatedArticle);
		return new ResponseEntity<>(modelMapper.map(updatedArticle, ItemDto.Response.class), HttpStatus.OK);
	}

	@RequestMapping(value = "/articles/{id}", method = DELETE)
	public ResponseEntity leave(@PathVariable Long id) {
		articleService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}