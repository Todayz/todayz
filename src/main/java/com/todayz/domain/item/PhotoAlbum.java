package com.todayz.domain.item;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.todayz.domain.common.Image;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@DiscriminatorValue("P")
@Getter
@Setter
@ToString
public class PhotoAlbum extends Item {

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "IMAGE_ID")
	private Image photo;
}
