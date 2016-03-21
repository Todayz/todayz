package com.hotclub.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.hotclub.domain.member.Member;

public class UserDetailsImpl extends User {

	private Long id;

	public UserDetailsImpl(Member member) {
		super(member.getAuthName(), member.getPassword(), authorities(member));
		this.id = member.getId();
	}

	private static Collection<? extends GrantedAuthority> authorities(Member member) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		return authorities;
	}

	public Long getId() {
		return id;
	}
}
