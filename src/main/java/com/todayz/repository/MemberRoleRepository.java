package com.todayz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todayz.domain.member.Member;
import com.todayz.domain.member.MemberRole;

public interface MemberRoleRepository extends JpaRepository<MemberRole, Long> {
	List<MemberRole> findByParent(Member parent);

	List<MemberRole> removeByParent(Member parent);
}
