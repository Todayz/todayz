package com.todayz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todayz.domain.club.Club;
import com.todayz.domain.club.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {
	  List<Menu> findByParentClub(Club club);
}
