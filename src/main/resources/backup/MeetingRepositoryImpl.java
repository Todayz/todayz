package com.hotclub.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.hotclub.domain.club.Club;
import com.hotclub.domain.club.Meeting;
import com.hotclub.domain.member.Member;
import com.hotclub.repository.MeetingRepository;

@Repository
public class MeetingRepositoryImpl implements MeetingRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void save(Meeting meeting) {
		if (meeting.getId() == null) {
			em.persist(meeting);
		} else {
			em.merge(meeting);
		}
	}

	@Override
	public void delete(Long id) {
		Meeting meeting = findOne(id);
		em.remove(meeting);
	}

	@Override
	public Meeting findOne(Long id) {
		// TODO Auto-generated method stub
		return em.find(Meeting.class, id);
	}

	@Override
	public List<Meeting> findAll() {
		// TODO Auto-generated method stub
		return em.createQuery("select m from Meeting m", Meeting.class).getResultList();
	}

	@Override
	public List<Meeting> findByClubId(Long clubId) {
		// TODO Auto-generated method stub
		// 문제없을 시 양방향 매핑으로 추후에 변경 예정.
		Club club = em.find(Club.class, clubId);
		return em.createQuery("select m from Meeting m where m.parent = :parent", Meeting.class)
				.setParameter("parent", club).getResultList();
	}

	@Override
	public Member attachMeeting(Meeting meeting, Member member) {
		// TODO Auto-generated method stub
		// 리턴의 필요성??
		List<Member> members = meeting.getAttachMembers();
		members.add(member);
		return member;
	}

	@Override
	public void detachMeeting(Meeting meeting, Member member) {
		// TODO Auto-generated method stub
		List<Member> members = meeting.getAttachMembers();
		members.remove(member);
	}
}
