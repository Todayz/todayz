package com.hotclub.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
@DiscriminatorValue("A")
public class Article extends Item {

	@Lob
	private String content;
}
