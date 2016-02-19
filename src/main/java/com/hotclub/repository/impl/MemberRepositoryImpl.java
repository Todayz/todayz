package com.hotclub.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

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
		Member member = em.find(Member.class, id);
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
		return em.createQuery("select m from Member m where m.authId = :authId", Member.class)
				.setParameter("authId", authId).getSingleResult();
	}

	@Override
	public List<Member> findByClubId(Long clubId) {
		return null;
	}

	@Override
	public List<Member> findByMeetingId(Long meetingId) {
		return null;
	}

}
