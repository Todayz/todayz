package com.hotclub.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Desktop {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "DESKTOP_ID")
	private long id;

	// private Member currentMember;
	// @OneToMany(mappedBy = "parent")
	// private List<Club> clubs;
}
