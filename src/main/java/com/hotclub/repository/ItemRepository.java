package com.hotclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotclub.domain.item.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
