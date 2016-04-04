package com.hotclub.domain.club;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.hotclub.domain.AclDomain;
import com.hotclub.domain.common.Image;
import com.hotclub.domain.member.Member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString(callSuper = true, exclude = { "joiningMembers", "mainImage", "owner" })
public class Club implements AclDomain {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CLUB_ID")
	private Long id;

	private String title;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "IMAGE_ID")
	private Image mainImage;

	@Lob
	private String notice;

	// @OneToMany(mappedBy = "parent")
	// private List<Meeting> meetings;

	// @OneToMany(mappedBy = "parentClub")
	// private List<Menu> menus;

	/*
	 * 이거 관련하여 테이블 하나 필요.
	 **/
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "CLUB_AND_MEMBER", joinColumns = @JoinColumn(name = "CLUB_ID") , inverseJoinColumns = @JoinColumn(name = "MEMBER_ID") )
	private List<Member> joiningMembers = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_ID")
	private Member owner;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;
}