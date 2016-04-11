package com.todayz.service;

import com.todayz.domain.common.Comment;

public interface CommentService {
	public Comment create(Comment comment);

	public void delete(Long id);

	public Comment getComment(Long id);
}
