package com.todayz.domain.item;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import com.todayz.domain.common.Image;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@DiscriminatorValue("A")
@Getter
@Setter
@ToString
public class Article extends Item {

	@Lob
	private String content;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "IMAGE_ID")
	private Image articleImage;
}
