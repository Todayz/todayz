package com.todayz.controller.chat;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.todayz.controller.support.ChatDto;
import com.todayz.domain.chat.Chat;
import com.todayz.domain.club.Club;
import com.todayz.repository.ChatRepository;
import com.todayz.service.ChatService;
import com.todayz.service.ClubService;

@Controller
public class ChatController {

	@Autowired
	private ChatService chatService;

	@Autowired
	private ChatRepository chatRepository;

	@Autowired
	private ClubService clubService;

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

	// http://docs.spring.io/spring-data/data-commons/docs/1.6.1.RELEASE/reference/html/repositories.html
	// 의 Table 1.1. 참조
	// chats?page=0&size=20&sort=username,asc$sort=name,asc
	@ResponseBody
	@RequestMapping(value = "/chats", method = GET)
	@ResponseStatus(HttpStatus.OK)
	public PageImpl<ChatDto.Response> getChats(Pageable pageable, @DestinationVariable("clubId") String clubId) {

		Long id = Long.parseLong(clubId);

		Club parent = clubService.getClub(id);
		Page<Chat> page = chatRepository.findByParent(parent, pageable);

		// 종종 스트림에 있는 값들을 특정 방식으로 변환하고 싶을때가 있다. 이 경우 map 메서드를 사용하고
		// 변환을 수행하는 함수를 파라미터로 전달한다.
		List<ChatDto.Response> content = page.getContent().stream()
				.map(chat -> modelMapper.map(chat, ChatDto.Response.class)).collect(Collectors.toList());
		return new PageImpl<>(content, pageable, page.getTotalElements());
	}
}