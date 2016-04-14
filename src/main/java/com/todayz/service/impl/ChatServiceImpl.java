package com.todayz.service.impl;

import java.util.Date;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todayz.controller.support.ChatDto;
import com.todayz.domain.chat.Chat;
import com.todayz.domain.club.Club;
import com.todayz.domain.member.Member;
import com.todayz.repository.ChatRepository;
import com.todayz.service.ChatService;
import com.todayz.service.ClubService;
import com.todayz.service.MemberService;

@Service
@Transactional
public class ChatServiceImpl implements ChatService {

	@Autowired
	private ChatRepository chatRepository;

	@Autowired
	private ClubService clubService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Chat create(ChatDto.Create newMessage, String authName, Long clubId) {
		Chat chat = modelMapper.map(newMessage, Chat.class);

		Club parent = clubService.getClub(clubId);
		chat.setParent(parent);
		
		Member writer = memberService.getMemberByAuthName(authName);

		Date now = new Date();
		chat.setCreatedDate(now);

		chat.setWriter(writer);
		chat = chatRepository.save(chat);

		// log.info("chat create success. {}", chat);
		return chat;
	}

	@Override
	public Chat getChat(Long id) {
		Chat chat = chatRepository.findOne(id);
		// if (chat == null) {
		// throw new ClubNotFoundException(id);
		// }
		return chat;
	}
}
