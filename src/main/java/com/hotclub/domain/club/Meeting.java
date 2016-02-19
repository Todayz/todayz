package com.hotclub.domain.club;

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

import com.hotclub.domain.member.Member;

@Entity
public class Meeting {

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
	@JoinTable(name = "MEETING_AND_MEMBER",
		joinColumns = @JoinColumn(name = "MEETING_ID") , 
		inverseJoinColumns = @JoinColumn(name = "MEMBER_ID") )
	private List<Member> attachMembers;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getMeetingDate() {
		return meetingDate;
	}

	public void setMeetingDate(Date meetingDate) {
		this.meetingDate = meetingDate;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getAttendCosts() {
		return attendCosts;
	}

	public void setAttendCosts(String attendCosts) {
		this.attendCosts = attendCosts;
	}

	public Long getQuota() {
		return quota;
	}

	public void setQuota(Long quota) {
		this.quota = quota;
	}

	public Club getParent() {
		return parent;
	}

	public void setParent(Club parent) {
		this.parent = parent;
	}

	public List<Member> getAttachMembers() {
		return attachMembers;
	}

	public void setAttachMembers(List<Member> attachMembers) {
		this.attachMembers = attachMembers;
	}
}
