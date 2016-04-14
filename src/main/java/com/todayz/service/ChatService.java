package com.todayz.service;

import com.todayz.controller.support.ChatDto;
import com.todayz.domain.chat.Chat;

public interface ChatService {
	public Chat create(ChatDto.Create newMessage, String authName, Long clubId);

	public Chat getChat(Long id);
}
