package com.todayz.service;

import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.todayz.TodayzApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(TodayzApplication.class)
@Transactional
public class MemberServiceIntegrationTests {

	/*@Autowired
	private MemberService memberService;

	@Autowired
	private ClubService clubService;

	@Autowired
	private MemberRepository memberRepository;

	private MemberDto.Create memberCreateDto() throws ParseException {
		MemberDto.Create createDto = new MemberDto.Create();
		createDto.setAuthName("jmlim");
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

	public Club clubCreate() {
		ClubDto.Create createDto = new ClubDto.Create();
		// ClubDto.create createDto =.....
		// club.
		Image image = new Image();
		image.setImageName("메인사");
		image.setType("png");

		createDto.setMainImage(image);
		createDto.setNotice("튼튼 2030 초보등산,,,내용.........ㄷㄷㄷㄷㄷ");
		createDto.setTitle("튼튼 2030 초보등산");

		return clubService.create(createDto, null);
	}

	@Test
	public void join() throws ParseException {
		MemberDto.Create createDto = memberCreateDto();
		Member member = memberService.join(createDto);
		assertEquals(member, memberRepository.findOne(member.getId()));
	}

	@Test
	public void update() throws ParseException {
		MemberDto.Create newMember = memberCreateDto();

		Member member = memberService.join(newMember);
		member.setName("임구라");

		Member updatedMember = memberRepository.findOne(member.getId());
		assertEquals(member.getName(), updatedMember.getName());
	}

	@Test
	public void leave() throws ParseException {
		MemberDto.Create createDto = memberCreateDto();
		Member member = memberService.join(createDto);

		Long id = member.getId();
		memberService.leave(id);

		assertEquals(null, memberRepository.findOne(id));
	}

	// arguments : Long clubId, Member member
	@Test
	public void joinClub() throws ParseException {
		Club club = clubCreate();

		MemberDto.Create createDto = memberCreateDto();
		Member member = memberService.join(createDto);

		memberService.joinClub(club.getId(), member);

		assertEquals(member, club.getJoiningMembers().get(0));
	}

	// arguments : Long clubId, Member member
	@Test
	public void leaveClub() throws ParseException {
		Club club = clubCreate();

		MemberDto.Create createDto = memberCreateDto();
		Member member = memberService.join(createDto);

		memberService.joinClub(club.getId(), member);

		assertEquals(member, club.getJoiningMembers().get(0));

		memberService.leaveClub(club.getId(), member);

		assertEquals(true, club.getJoiningMembers().isEmpty());
	}*/
}
