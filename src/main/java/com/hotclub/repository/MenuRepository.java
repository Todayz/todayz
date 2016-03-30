package com.hotclub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotclub.domain.club.Club;
import com.hotclub.domain.club.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {
	  List<Menu> findByParentClub(Club club);
}
