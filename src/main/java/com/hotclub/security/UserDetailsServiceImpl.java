package com.hotclub.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hotclub.domain.member.Member;
import com.hotclub.repository.MemberRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private MemberRepository repository;

	@Override
	public UserDetails loadUserByUsername(String authName) throws UsernameNotFoundException {
		Member member = repository.findByAuthName(authName);
		if (member == null) {
			throw new UsernameNotFoundException(authName);
		}

		return new UserDetailsImpl(member);
	}
}
