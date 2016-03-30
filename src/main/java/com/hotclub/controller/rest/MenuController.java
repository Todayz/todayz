package com.hotclub.controller.rest;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotclub.domain.club.Club;
import com.hotclub.domain.club.Menu;
import com.hotclub.service.ClubService;
import com.hotclub.service.MenuService;

/**
 * @author jmlim
 */
@RestController
@SuppressWarnings("rawtypes")
public class MenuController {

	@Autowired
	private MenuService menuService;

	@Autowired
	private ClubService clubService;

	// 조건문에 따라 HttpStatus 를 변경해서 리턴하기 위해 ResponseEntity 로 반환한다.
	@RequestMapping(value = "/club/{clubId}/menus", method = POST)
	public ResponseEntity create(@PathVariable Long clubId, @RequestBody @Valid Menu create, BindingResult result) {

		if (result.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Club club = clubService.getClub(clubId);
		create.setParentClub(club);

		Menu newMenu = menuService.create(create);// create(create);
		return new ResponseEntity<>(newMenu, HttpStatus.CREATED);
	}

	// file upload 관련..참조(아래)
	// http://stackoverflow.com/questions/21329426/spring-mvc-multipart-request-with-json
	@RequestMapping(value = "/club/{clubId}/menus/{id}", method = PUT) // method
	public ResponseEntity update(@PathVariable Long id, @RequestBody @Valid Menu update, BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Menu updatedMenu = menuService.update(id, update);
		return new ResponseEntity<>(updatedMenu, HttpStatus.OK);
	}

	@RequestMapping(value = "/club/{clubId}/menus/{id}", method = DELETE)
	public ResponseEntity leave(@PathVariable Long id) {
		menuService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
