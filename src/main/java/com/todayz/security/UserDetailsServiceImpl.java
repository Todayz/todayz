package com.todayz.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.todayz.domain.member.Member;
import com.todayz.domain.member.MemberRole;
import com.todayz.repository.MemberRepository;
import com.todayz.repository.MemberRoleRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private MemberRoleRepository roleRepository;

	@Override
	public UserDetails loadUserByUsername(String authName) throws UsernameNotFoundException {
		Member member = memberRepository.findByAuthName(authName);
		List<MemberRole> roles = roleRepository.findByParent(member);
		if (member == null) {
			throw new UsernameNotFoundException(authName);
		}

		return new UserDetailsImpl(member, roles);
	}
}
