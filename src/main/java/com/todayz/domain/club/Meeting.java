package com.todayz.domain.club;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.todayz.domain.AclDomain;
import com.todayz.domain.member.Member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "TODAYZ_MEETING")
@Getter
@Setter
@ToString
public class Meeting implements AclDomain {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "MEETING_ID")
	private Long id;

	@Column(length = 100, nullable = false)
	private String title;

	@Temporal(TemporalType.TIMESTAMP)
	private Date meetingDate;

	@Column(length = 50, nullable = false)
	private String place;

	@Column(length = 100, nullable = false)
	private String attendCosts;

	@Column(nullable = false)
	private Long quota;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLUB_ID")
	private Club parent;

	@ManyToMany
	@JoinTable(name = "TODAYZ_MEETING_AND_MEMBER", joinColumns = @JoinColumn(name = "MEETING_ID") , inverseJoinColumns = @JoinColumn(name = "MEMBER_ID") )
	private List<Member> attachMembers = new ArrayList<>();
}
