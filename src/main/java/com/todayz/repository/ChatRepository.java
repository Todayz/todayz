package com.todayz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todayz.domain.chat.Chat;
import com.todayz.domain.club.Club;

public interface ChatRepository extends JpaRepository<Chat, Long> {
	List<Chat> findByParent(Club club);
}
