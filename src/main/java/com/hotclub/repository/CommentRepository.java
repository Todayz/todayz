package com.hotclub.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hotclub.domain.common.Comment;
import com.hotclub.domain.item.Item;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findByParent(Item item);
}
