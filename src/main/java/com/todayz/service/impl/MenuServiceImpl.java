package com.todayz.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todayz.domain.club.Menu;
import com.todayz.repository.MenuRepository;
import com.todayz.service.MenuService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuRepository menuRepository;

	@Override
	public Menu create(Menu create) {
		Menu createdMenu = menuRepository.save(create);
		log.info("menu create success {}, {}", create.getId(), create.getTitle());
		return createdMenu;
	}

	@Override
	public Menu update(Long id, Menu update) {
		// TODO Auto-generated method stub

		Menu menu = getMenu(id);
		menu.setTitle(update.getTitle());

		log.info("menu update success {}, {}", update.getId(), update.getTitle());

		return menu;
	}

	@Override
	public void delete(Long id) {
		Menu delete = getMenu(id);
		menuRepository.delete(delete);
		log.info("menu delete success {}, {}", delete.getId(), delete.getTitle());
	}

	public Menu getMenu(Long id) {
		Menu menu = menuRepository.findOne(id);
		// if (club == null) {
		// throw new MenuNotFoundException(id);
		// }
		return menu;
	}
}
