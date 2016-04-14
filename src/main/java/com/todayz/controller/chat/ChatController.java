package com.todayz.controller.chat;

import java.security.Principal;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.todayz.controller.support.ChatDto;
import com.todayz.domain.chat.Chat;
import com.todayz.domain.club.Club;
import com.todayz.service.ChatService;
import com.todayz.service.ClubService;

@Controller
public class ChatController {

	@Autowired
	private ChatService chatService;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Autowired
	private ModelMapper modelMapper;

	@MessageMapping("/chatTyping.{clubId}")
	// @SendTo("/topic/chatMessages")
	public void chatMessage(ChatDto.Create newMessage, Principal principal,
			@DestinationVariable("clubId") String clubId) {

		Long id = Long.parseLong(clubId);
		String authName = principal.getName();

		Chat chat = chatService.create(newMessage, authName, id);

		ChatDto.Response responseMessage = modelMapper.map(chat, ChatDto.Response.class);
		// return chat;
		simpMessagingTemplate.convertAndSend("/topic/chatMessages." + id, responseMessage);
	}
}