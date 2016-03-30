package com.hotclub.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotclub.domain.club.Menu;
import com.hotclub.exception.MenuNotFoundException;
import com.hotclub.repository.MenuRepository;
import com.hotclub.service.MenuService;

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

	protected Menu getMenu(Long id) throws MenuNotFoundException {
		Menu club = menuRepository.findOne(id);
		if (club == null) {
			throw new MenuNotFoundException(id);
		}
		return club;
	}
}
