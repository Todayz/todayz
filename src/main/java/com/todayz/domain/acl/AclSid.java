package com.todayz.domain.acl;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "acl_sid")
@Getter
@Setter	
@ToString
public class AclSid implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "principal", nullable = false, unique = false)
	private Boolean principal;

	@Column(name = "sid", length = 100, nullable = false, unique = false)
	private String sid;

	public AclSid() {
	}

	public AclSid(Long id, Boolean principal, String sid) {
		setId(id);
		setPrincipal(principal);
		setSid(sid);
	}
}