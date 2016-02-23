package com.hotclub.repository;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.hotclub.HotclubApplication;
import com.hotclub.domain.common.Image;
import com.hotclub.domain.member.Member;
import com.hotclub.service.MemberService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(HotclubApplication.class)
@Transactional
public class MemberServiceIntegrationTests {

	@Autowired
	private MemberService service;

	@Autowired
	private MemberRepository memberRepository;

	@Test
	public void join() throws ParseException {
		Member member = new Member();
		member.setAuthId("jmlim");
		member.setName("임정묵");
		member.setPassword("passjmlim");
		member.setPhoneNumber("010-8791-1883");
		member.setDescription("안녕하세요~ 반갑습니다 ^^");

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date birthday = format.parse("1986-08-30");
		member.setBirthday(birthday);

		Calendar cal = Calendar.getInstance();
		Date joinDate = cal.getTime();

		member.setJoinDate(joinDate);

		Image image = new Image();
		image.setImageUrl("/upload/1");
		member.setProfileImage(image);

		Long saveId = service.join(member);

		assertEquals(member, memberRepository.findOne(saveId));
	}

	@Test
	public void update() throws ParseException {
		Member newMember = new Member();
		newMember.setAuthId("jmlim");
		newMember.setName("임정묵");
		newMember.setPassword("passjmlim");
		newMember.setPhoneNumber("010-8791-1883");
		newMember.setDescription("안녕하세요~ 반갑습니다 ^^");

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date birthday = format.parse("1986-08-30");
		newMember.setBirthday(birthday);

		Calendar cal = Calendar.getInstance();
		Date joinDate = cal.getTime();

		newMember.setJoinDate(joinDate);

		Image image = new Image();
		image.setImageUrl("/upload/1");
		newMember.setProfileImage(image);

		Long saveId = service.join(newMember);
		Member member = memberRepository.findOne(saveId);
		member.setName("임구라");

		assertEquals(member.getName(), newMember.getName());
	}

	@Test
	public void leave() throws ParseException {
		Member member = new Member();
		member.setAuthId("jmlim");
		member.setName("임정묵");
		member.setPassword("passjmlim");
		member.setPhoneNumber("010-8791-1883");
		member.setDescription("안녕하세요~ 반갑습니다 ^^");

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date birthday = format.parse("1986-08-30");
		member.setBirthday(birthday);

		Calendar cal = Calendar.getInstance();
		Date joinDate = cal.getTime();

		member.setJoinDate(joinDate);

		Image image = new Image();
		image.setImageUrl("/upload/1");
		member.setProfileImage(image);

		Long saveId = service.join(member);

		assertEquals(member, memberRepository.findOne(saveId));

		service.leave(saveId);

		assertEquals(null, memberRepository.findOne(saveId));
	}
}
