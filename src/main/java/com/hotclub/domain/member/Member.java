package com.hotclub.domain.member;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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

@Entity
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String authId;

	private String password;

	private String name;

	private String description;

	private String phoneNumber;

	private Date birthday;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the authId
	 */
	public String getAuthId() {
		return authId;
	}

	/**
	 * @param authId
	 *            the authId to set
	 */
	public void setAuthId(String authId) {
		this.authId = authId;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber
	 *            the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the birthday
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * @param birthday
	 *            the birthday to set
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * @return the profileImage
	 */
	public Image getProfileImage() {
		return profileImage;
	}

	/**
	 * @param profileImage
	 *            the profileImage to set
	 */
	public void setProfileImage(Image profileImage) {
		this.profileImage = profileImage;
	}

	/**
	 * @return the joinClubs
	 */
	public List<Club> getJoinClubs() {
		return joinClubs;
	}

	/**
	 * @param joinClubs
	 *            the joinClubs to set
	 */
	public void setJoinClubs(List<Club> joinClubs) {
		this.joinClubs = joinClubs;
	}

	/**
	 * @return the attachMeetings
	 */
	public List<Meeting> getAttachMeetings() {
		return attachMeetings;
	}

	/**
	 * @param attachMeetings
	 *            the attachMeetings to set
	 */
	public void setAttachMeetings(List<Meeting> attachMeetings) {
		this.attachMeetings = attachMeetings;
	}

	/**
	 * @return the joinDate
	 */
	public Date getJoinDate() {
		return joinDate;
	}

	/**
	 * @param joinDate
	 *            the joinDate to set
	 */
	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}
}
