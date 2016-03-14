package com.hotclub.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
import org.springframework.security.web.FilterChainProxy;
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
public class MemberControllerIntegrationTests {

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MemberService service;

	@Autowired
	private FilterChainProxy springSecurityFilterChain;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).addFilter(springSecurityFilterChain).build();
	}

	private MemberDto.Create memberCreateDto() throws ParseException {
		return memberCreateDto("jmlim");
	}

	private MemberDto.Create memberCreateDto(String authName) throws ParseException {
		MemberDto.Create createDto = new MemberDto.Create();
		createDto.setAuthName(authName);
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
		String authName = "jmlim";
		if (size == null)
			size = 10;
		MemberDto.Create createDto;
		for (int i = 0; i < size; i++) {
			createDto = memberCreateDto(authName + i);
			service.join(createDto);
		}
	}

	@Test
	public void joinMember() throws Exception {
		MemberDto.Create createDto = memberCreateDto();

		ResultActions result = mockMvc.perform(post("/members").with(user("test"))
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createDto)));

		result.andDo(print());
		result.andExpect(status().isCreated());
		result.andExpect(jsonPath("$.authName", is("jmlim")));

		// https://github.com/jayway/JsonPath
		// 멤버가 중복될 경우 GlobalControllerExceptionHandler 에서 BadRequest 던짐
		// member duplicate test
		result = mockMvc.perform(post("/members").with(user("test")).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(createDto)));

		result.andDo(print());
		result.andExpect(status().isBadRequest());
		result.andExpect(jsonPath("$.code", is("duplicated.authName.exception")));
	}

	@Test
	public void update() throws Exception {
		MemberDto.Create createDto = memberCreateDto();
		Member member = service.join(createDto);

		MemberDto.Update updateDto = new MemberDto.Update();

		updateDto.setName("okboy");
		updateDto.setPassword("okjmlim333");

		ResultActions result = mockMvc.perform(
				put("/members/" + member.getId()).with(httpBasic(createDto.getAuthName(), createDto.getPassword()))
						.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updateDto)));

		result.andDo(print());
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.name", is("okboy")));
	}

	@Test
	public void leave() throws Exception {
		MemberDto.Create createDto = memberCreateDto();
		Member member = service.join(createDto);

		// ResultActions result = mockMvc.perform(delete("/members/1231231"));
		ResultActions result = mockMvc
				.perform(delete("/members/1231").with(httpBasic(createDto.getAuthName(), createDto.getPassword())));

		//
		// ResultActions result = mockMvc.perform(delete("/members/12311")
		// .with(user(createDto.getAuthName()).password("okkok").roles("USER",
		// "ADMIN")));
		result.andDo(print());
		result.andExpect(status().isBadRequest());

		result = mockMvc.perform(
				delete("/members/" + member.getId()).with(httpBasic(createDto.getAuthName(), createDto.getPassword())));

		result.andDo(print());
		result.andExpect(status().isNoContent());
	}

	@Test
	public void getMembers() throws Exception {
		MemberDto.Create createDto = memberCreateDto();
		service.join(createDto);

		// ResultActions result = mockMvc.perform(get("/members"));
		ResultActions result = mockMvc
				.perform(get("/members").with(httpBasic(createDto.getAuthName(), createDto.getPassword())));

		/*
		 * Body =
		 * {"content":[{"id":5,"authName":"jmlim","name":"임정묵","description":
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
		ResultActions result = mockMvc.perform(get("/members?page=3&size=10")// &sort=authName,asc&sort=name,asc"));
				.with(httpBasic("jmlim1", "passjmlim")));

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

		ResultActions result = mockMvc.perform(
				get("/members/" + member.getId()).with(httpBasic(createDto.getAuthName(), createDto.getPassword())));

		// MemberDto.Response return
		result.andDo(print());
		result.andExpect(status().isOk());
	}
}
