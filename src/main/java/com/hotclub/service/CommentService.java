package com.hotclub.service;

import com.hotclub.domain.common.Comment;

public interface CommentService {
	public Comment create(Comment comment);

	public void delete(Long id);

	public Comment getComment(Long id);
}
