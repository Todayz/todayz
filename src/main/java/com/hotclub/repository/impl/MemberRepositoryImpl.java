package com.hotclub.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.hotclub.domain.club.Club;
import com.hotclub.domain.club.Meeting;
import com.hotclub.domain.member.Member;
import com.hotclub.repository.MemberRepository;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void save(Member member) {
		if (member.getId() == null) {
			em.persist(member);
		} else {
			em.merge(member);
		}
	}

	@Override
	public void delete(Long id) {
		Member member = findOne(id);
		em.remove(member);
	}

	@Override
	public Member findOne(Long id) {
		return em.find(Member.class, id);
	}

	@Override
	public List<Member> findAll() {
		return em.createQuery("select m from Member m", Member.class).getResultList();
	}

	@Override
	public Member findByAuthId(String authId) {
		Member member = null;
		// http://stackoverflow.com/questions/25616374/javax-persistence-noresultexception-no-entity-found-for-query-jpql-query
		try {
			member = em.createQuery("select m from Member m where m.authId = :authId", Member.class)
					.setParameter("authId", authId).getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}
		return member;
	}

	@Override
	public List<Member> findByClubId(Long clubId) {
		// 양방향 매핑이므로 이론상 성립하나 확인 필요.
		Club club = em.find(Club.class, clubId);
		return club.getJoiningMembers();
	}

	@Override
	public List<Member> findByMeetingId(Long meetingId) {
		Meeting meeting = em.find(Meeting.class, meetingId);
		return meeting.getAttachMembers();
	}
}
