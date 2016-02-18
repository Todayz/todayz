package com.hotclub.domain.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.hotclub.domain.item.Item;

@Entity
public class Image {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "IMAGE_ID")
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ITEM_ID")
	private Item parent;

	private String imageUrl;

}
