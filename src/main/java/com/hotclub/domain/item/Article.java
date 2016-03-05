package com.hotclub.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Lob;

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
}
