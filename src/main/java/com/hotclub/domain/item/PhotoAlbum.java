package com.hotclub.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("P")
public class PhotoAlbum extends Item {

}
