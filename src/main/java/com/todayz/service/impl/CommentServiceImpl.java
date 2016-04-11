package com.todayz.service.impl;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.todayz.domain.common.Comment;
import com.todayz.domain.member.Member;
import com.todayz.repository.CommentRepository;
import com.todayz.service.CommentService;
import com.todayz.service.MemberService;
import com.todayz.service.TodayzAclService;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private MemberService memberService;

	@Autowired
	private TodayzAclService<Comment> todayzAclService;

	protected String getUsername() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth.getPrincipal() instanceof UserDetails) {
			return ((UserDetails) auth.getPrincipal()).getUsername();
		} else {
			return auth.getPrincipal().toString();
		}
	}

	@Override
	public Comment create(Comment comment) {
		String authName = getUsername();
		Member writer = memberService.getMemberByAuthName(authName);

		Date now = new Date();
		comment.setCreatedDate(now);

		comment.setWriter(writer);
		comment = commentRepository.save(comment);

		todayzAclService.addPermission(comment, new PrincipalSid(authName), BasePermission.DELETE);
		return comment;
	}

	@Override
	public void delete(Long id) {
		Comment comment = getComment(id);
		commentRepository.delete(comment);
		todayzAclService.deleteAcl(comment);
	}

	@Override
	public Comment getComment(Long id) {
		Comment comment = commentRepository.findOne(id);
		// if (comment == null) {
		// throw new ClubNotFoundException(id);
		// }
		return comment;
	}
}
