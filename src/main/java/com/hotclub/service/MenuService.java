package com.hotclub.service;

import com.hotclub.domain.club.Menu;

public interface MenuService {
	public Menu create(Menu create);

	public Menu update(Long id, Menu update);

	public void delete(Long id);

	public Menu getMenu(Long id);
}
