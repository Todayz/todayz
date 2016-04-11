package com.todayz.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.todayz.domain.member.Member;

public class UserDetailsImpl extends User {

	private Long id;

	public UserDetailsImpl(Member member) {
		super(member.getAuthName(), member.getPassword(), authorities(member));
		this.id = member.getId();
	}

	private static Collection<? extends GrantedAuthority> authorities(Member member) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		// TODO 회원을 관리할 수 있는 슈퍼관리자 권한. ROLE_ADMIN,
		// TODO 클럽을 만들어서 관리할 수 있는 권한 ROLE_CLUB_MANAGER
		// 이런 권한들에 대한 Entity를 만들어 Member에 적용할 필요가 있음. 
		// 현재는 모든 계정
		authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		return authorities;
	}

	public Long getId() {
		return id;
	}
}
