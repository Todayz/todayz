package com.hotclub.domain.common;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.hotclub.domain.item.Item;
import com.hotclub.domain.member.Member;

@Entity
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "COMMENT_ID")
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ITEM_ID")
	private Item parent;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_ID")
	private Member writer;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	@Lob
	private String content;
}
