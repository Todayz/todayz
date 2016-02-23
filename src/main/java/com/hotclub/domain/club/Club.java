package com.hotclub.domain.club;

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
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.hotclub.domain.common.Image;
import com.hotclub.domain.member.Member;

@Entity
public class Club {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CLUB_ID")
	private Long id;

	private String title;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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
	@ManyToMany
	@JoinTable(name = "CLUB_AND_MEMBER", joinColumns = @JoinColumn(name = "CLUB_ID") , inverseJoinColumns = @JoinColumn(name = "MEMBER_ID") )
	private List<Member> joiningMembers;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the mainImage
	 */
	public Image getMainImage() {
		return mainImage;
	}

	/**
	 * @param mainImage
	 *            the mainImage to set
	 */
	public void setMainImage(Image mainImage) {
		this.mainImage = mainImage;
	}

	/**
	 * @return the notice
	 */
	public String getNotice() {
		return notice;
	}

	/**
	 * @param notice
	 *            the notice to set
	 */
	public void setNotice(String notice) {
		this.notice = notice;
	}

	/**
	 * @return the joiningMembers
	 */
	public List<Member> getJoiningMembers() {
		return joiningMembers;
	}

	/**
	 * @param joiningMembers
	 *            the joiningMembers to set
	 */
	public void setJoiningMembers(List<Member> joiningMembers) {
		this.joiningMembers = joiningMembers;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate
	 *            the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the updatedDate
	 */
	public Date getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param updatedDate
	 *            the updatedDate to set
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
}