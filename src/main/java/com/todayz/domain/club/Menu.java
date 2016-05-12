package com.todayz.domain.club;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "TODAYZ_MENU")
@Getter
@Setter
@ToString
public class Menu {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "MENU_ID")
	private Long id;

	// private MenuType type;
	@NotBlank
	@Size(max = 50)
	@Column(length = 100, nullable = false)
	private String title;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLUB_ID")
	@JsonIgnore
	private Club parentClub;

	private boolean defaultMenu;

	public Menu() {
		super();
	}

	public Menu(String title, Club parentClub, boolean defaultMenu) {
		super();
		this.title = title;
		this.parentClub = parentClub;
		this.defaultMenu = defaultMenu;
	}

	// @OneToMany(mappedBy = "parent")
	// private List<Item> items;
}
