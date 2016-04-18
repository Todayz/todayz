package com.todayz.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.todayz.domain.chat.Chat;
import com.todayz.domain.club.Club;

public interface ChatRepository extends JpaRepository<Chat, Long> {
	Page<Chat> findByParent(Club club, Pageable pageable);
}
