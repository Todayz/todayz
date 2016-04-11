package com.todayz.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todayz.domain.club.Menu;
import com.todayz.exception.MenuNotFoundException;
import com.todayz.repository.MenuRepository;
import com.todayz.service.MenuService;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuRepository menuRepository;

	@Override
	public Menu create(Menu create) {
		return menuRepository.save(create);
	}

	@Override
	public Menu update(Long id, Menu update) {
		// TODO Auto-generated method stub

		Menu menu = getMenu(id);
		menu.setTitle(update.getTitle());
		return menu;
	}

	@Override
	public void delete(Long id) {
		menuRepository.delete(getMenu(id));
	}

	public Menu getMenu(Long id) {
		Menu menu = menuRepository.findOne(id);
		//if (club == null) {
		//	throw new MenuNotFoundException(id);
		//}
		return menu;
	}
}
