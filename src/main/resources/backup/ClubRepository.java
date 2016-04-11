package com.todayz.repository;

import java.util.List;

import com.todayz.domain.club.Club;
import com.todayz.domain.member.Member;

public interface ClubRepository {
	public void save(Club club);

	public void delete(Long id);

	public Club findOne(Long id);

	//추후에 paging에 대한 처리 필요.
	public List<Club> findAll();

	public Member joinClub(Club club, Member member);

	public void leaveClub(Club club, Member member);
}
