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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.todayz.domain.AclDomain;
import com.todayz.domain.member.Member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Meeting implements AclDomain {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "MEETING_ID")
	private Long id;

	private String title;

	@Temporal(TemporalType.TIMESTAMP)
	private Date meetingDate;

	private String place;

	private String attendCosts;

	private Long quota;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLUB_ID")
	private Club parent;

	@ManyToMany
	@JoinTable(name = "MEETING_AND_MEMBER", joinColumns = @JoinColumn(name = "MEETING_ID") , inverseJoinColumns = @JoinColumn(name = "MEMBER_ID") )
	private List<Member> attachMembers = new ArrayList<>();
}
