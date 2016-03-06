package com.hotclub.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotclub.HotclubApplication;
import com.hotclub.controller.support.MemberDto;
import com.hotclub.domain.common.Image;
import com.hotclub.domain.member.Member;
import com.hotclub.service.MemberService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = HotclubApplication.class)
@WebAppConfiguration
@Transactional
public class MemberControllerTests {

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MemberService service;

	private MockMvc mockMvc;

	private MemberDto.Create memberCreateDto() throws ParseException {
		return memberCreateDto("jmlim");
	}

	private MemberDto.Create memberCreateDto(String username) throws ParseException {
		MemberDto.Create createDto = new MemberDto.Create();
		createDto.setUsername(username);
		createDto.setName("임정묵");
		createDto.setPassword("passjmlim");
		createDto.setPhoneNumber("010-8791-1883");
		createDto.setDescription("안녕하세요~ 반갑습니다 ^^");

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date birthday = format.parse("1986-08-30");
		createDto.setBirthday(birthday);

		Image image = new Image();
		image.setImageName("내사진");
		image.setType("png");
		createDto.setProfileImage(image);
		return createDto;
	}

	private void manyMembersCreate(Integer size) throws ParseException {
		String username = "jmlim";
		if (size == null)
			size = 10;
		MemberDto.Create createDto;
		for (int i = 0; i < size; i++) {
			createDto = memberCreateDto(username + i);
			service.join(createDto);
		}
	}

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void joinMember() throws Exception {
		MemberDto.Create createDto = memberCreateDto();

		ResultActions result = mockMvc.perform(post("/members").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(createDto)));

		result.andDo(print());
		result.andExpect(status().isCreated());
		result.andExpect(jsonPath("$.username", is("jmlim")));

		// https://github.com/jayway/JsonPath
		// 멤버가 중복될 경우 GlobalControllerExceptionHandler 에서 BadRequest 던짐
		// member duplicate test
		result = mockMvc.perform(post("/members").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(createDto)));

		result.andDo(print());
		result.andExpect(status().isBadRequest());
		result.andExpect(jsonPath("$.code", is("duplicated.username.exception")));
	}

	@Test
	public void update() throws Exception {
		MemberDto.Create createDto = memberCreateDto();
		Member member = service.join(createDto);

		MemberDto.Update updateDto = new MemberDto.Update();

		updateDto.setName("okboy");
		updateDto.setPassword("okjmlim333");

		ResultActions result = mockMvc.perform(put("/members/" + member.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updateDto)));

		result.andDo(print());
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.name", is("okboy")));
	}

	@Test
	public void leave() throws Exception {
		MemberDto.Create createDto = memberCreateDto();
		Member member = service.join(createDto);

		ResultActions result = mockMvc.perform(delete("/members/1231231"));
		result.andDo(print());
		result.andExpect(status().isBadRequest());

		result = mockMvc.perform(delete("/members/" + member.getId()));

		result.andDo(print());
		result.andExpect(status().isNoContent());
	}

	@Test
	public void getMembers() throws Exception {
		MemberDto.Create createDto = memberCreateDto();
		service.join(createDto);

		ResultActions result = mockMvc.perform(get("/members"));

		/*
		 * Body =
		 * {"content":[{"id":5,"username":"jmlim","name":"임정묵","description":
		 * "안녕하세요~ 반갑습니다 ^^"
		 * ,"phoneNumber":"010-8791-1883","birthday":525711600000,"profileImage"
		 * :{ "id":5,"parent":null,"imageName":"내사진","type":"png"},"joinDate":
		 * 1457265179444}],"last":true,"totalElements":1,"totalPages":1,"sort":
		 * null, "first":true,"numberOfElements":1,"size":20,"number":0}
		 */
		result.andDo(print());
		result.andExpect(status().isOk());
	}

	@Test
	public void getMembersPageable() throws Exception {
		manyMembersCreate(100);
		ResultActions result = mockMvc.perform(get("/members?page=3&size=10"));// &sort=username,asc&sort=name,asc"));

		result.andDo(print());
		result.andExpect(status().isOk());

		result.andExpect(jsonPath("$.totalElements", is(100)));
		result.andExpect(jsonPath("$.totalPages", is(10)));
		result.andExpect(jsonPath("$.size", is(10)));
		result.andExpect(jsonPath("$.numberOfElements", is(10)));
		result.andExpect(jsonPath("$.number", is(3)));
	}

	@Test
	public void getMember() throws Exception {
		MemberDto.Create createDto = memberCreateDto();
		Member member = service.join(createDto);

		ResultActions result = mockMvc.perform(get("/members/" + member.getId()));

		// MemberDto.Response return
		result.andDo(print());
		result.andExpect(status().isOk());
	}
}
