package com.hotclub.domain.member;

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
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.hotclub.domain.club.Club;
import com.hotclub.domain.club.Meeting;
import com.hotclub.domain.common.Image;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "MEMBER_ID")
	private Long id;

	@Column(unique = true)
	private String authName;

	private String password;

	private String name;

	private String description;

	private String phoneNumber;

	private Date birthday;

	// image 를 굳이 LAZY 로 불러올 필요가 있는지 확인 필요.
	// http://stackoverflow.com/questions/26957554/jsonmappingexception-could-not-initialize-proxy-no-session
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "IMAGE_ID")
	private Image profileImage;
	/*
	 * // 객체로 바뀔 가능성 존재 private List<String> places;
	 * 
	 * // 객체로 바뀔 가능성 존재 private List<String> hobbies;
	 */
	@ManyToMany(mappedBy = "joiningMembers")
	private List<Club> joinClubs = new ArrayList<>();

	@ManyToMany(mappedBy = "attachMembers")
	private List<Meeting> attachMeetings = new ArrayList<>();

	// @OneToMany(mappedBy = "writer")
	// private List<Item> items;

	@Temporal(TemporalType.TIMESTAMP)
	private Date joinDate;
}
