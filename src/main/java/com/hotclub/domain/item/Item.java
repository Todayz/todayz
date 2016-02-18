package com.hotclub.domain.item;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.hotclub.domain.club.Menu;
import com.hotclub.domain.common.Comment;
import com.hotclub.domain.common.Image;
import com.hotclub.domain.member.Member;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "ITEM_TYPE")
public abstract class Item {

	@Id
	@GeneratedValue
	@Column(name = "ITEM_ID")
	private long id;

	private String title;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MENU_ID")
	private Menu parent;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_ID")
	private Member writer;

	// 공통으로 사용할 객체
	// @OneToMany(mappedBy = "parent")
	// private List<Comment> comments;

	// 공통으로 사용할 객체
	// @OneToMany(mappedBy = "parent")
	// private List<Image> attachImages;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;
}
