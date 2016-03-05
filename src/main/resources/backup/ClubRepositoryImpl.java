package com.hotclub.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.hotclub.domain.club.Club;
import com.hotclub.domain.member.Member;
import com.hotclub.repository.ClubRepository;

@Repository
public class ClubRepositoryImpl implements ClubRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void save(Club club) {
		if (club.getId() == null) {
			em.persist(club);
		} else {
			em.merge(club);
		}
	}

	@Override
	public void delete(Long id) {
		Club club = findOne(id);
		em.remove(club);
	}

	@Override
	public Club findOne(Long id) {
		// TODO Auto-generated method stub
		return em.find(Club.class, id);
	}

	@Override
	public List<Club> findAll() {
		// TODO Auto-generated method stub
		return em.createQuery("select c from Club c", Club.class).getResultList();
	}

	@Override
	public Member joinClub(Club club, Member member) {
		// TODO Auto-generated method stub
		// 리턴의 필요성??
		List<Member> members = club.getJoiningMembers();
		members.add(member);
		return member;
	}

	@Override
	public void leaveClub(Club club, Member member) {
		// TODO Auto-generated method stub
		List<Member> members = club.getJoiningMembers();
		members.remove(member);
	}
}
