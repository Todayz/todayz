package com.hotclub.service;

import com.hotclub.domain.item.Item;

public interface ItemService<T extends Item> {
	public T create(T dto);

	public T update(Long id, T dto);

	public void delete(Long id);

	public T getItem(Long id);
}
