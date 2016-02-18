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

	public void save(Member member) {
		em.persist(member);
	}

	public Member findOne(long id) {
		return em.find(Member.class, id);
	}

	public List<Member> findAll() {
		return em.createQuery("select m from Member m", Member.class).getResultList();
	}

	public List<Member> findByAuthId(String authId) {
		return em.createQuery("select m from Member m where m.authId = :authId", Member.class)
				.setParameter("authId", authId).getResultList();
	}
}
