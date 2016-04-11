package com.todayz.domain.item;

import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.todayz.domain.AclDomain;
import com.todayz.domain.club.Club;
import com.todayz.domain.club.Menu;
import com.todayz.domain.member.Member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "ITEM_TYPE")
@Getter
@Setter
@ToString
public abstract class Item implements AclDomain {

	@Id
	@GeneratedValue
	@Column(name = "ITEM_ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MENU_ID")
	private Menu parent;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_ID")
	private Member writer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLUB_ID")
	private Club parentClub;

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
