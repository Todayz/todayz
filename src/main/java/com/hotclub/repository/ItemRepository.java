package com.hotclub.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hotclub.domain.club.Menu;
import com.hotclub.domain.item.Item;

public interface ItemRepository<T extends Item> extends JpaRepository<T, Long> {
	Page<T> findByParent(Menu menu, Pageable pageable);
}
