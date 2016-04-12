package com.todayz.domain.acl;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "ACL_OBJECT_IDENTITY", uniqueConstraints = @UniqueConstraint(name = "uk_acl_object_identity", columnNames = {
		"OBJECT_ID_CLASS", "OBJECT_ID_IDENTITY" }))
@Getter
@Setter
@ToString
// https://github.com/MediaIQ/spring-data-jpa-acl/tree/master/src/main/java/com/mediaiqdigital/spring/acl/jpa
public class AclObjectIdentity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "OBJECT_ID_IDENTITY", nullable = false, unique = false)
	private Long objectIdIdentity;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "OBJECT_ID_CLASS", referencedColumnName = "ID", nullable = false, unique = false, insertable = true, updatable = true)
	private AclClass objectIdClass;

	@Column(name = "ENTRIES_INHERITING", nullable = false, unique = false)
	private Boolean entriesInheriting;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_OBJECT", referencedColumnName = "ID", nullable = true, unique = false, insertable = true, updatable = true)
	private AclObjectIdentity parentObject;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OWNER_SID", referencedColumnName = "ID", nullable = true, unique = false, insertable = true, updatable = true)
	private AclSid ownerSid;

	@OneToMany(targetEntity = com.todayz.domain.acl.AclEntry.class, fetch = FetchType.LAZY, mappedBy = "aclObjectIdentity", cascade = CascadeType.REMOVE)
	private Set<AclEntry> aclEntries = new HashSet<AclEntry>();
}