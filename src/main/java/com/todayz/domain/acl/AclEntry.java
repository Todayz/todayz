package com.todayz.domain.acl;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "ACL_ENTRY")
@Getter
@Setter
@ToString
public class AclEntry implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "ACE_ORDER", nullable = false, unique = false)
	private Integer aceOrder;

	@Column(name = "MASK", nullable = false, unique = false)
	private Integer mask;

	@Column(name = "GRANTING", nullable = false, unique = false)
	private Boolean granting;

	@Column(name = "AUDIT_SUCCESS", nullable = false, unique = false)
	private Boolean auditSuccess;

	@Column(name = "AUDIT_FAILURE", nullable = false, unique = false)
	private Boolean auditFailure;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ACL_OBJECT_IDENTITY", referencedColumnName = "ID", nullable = false, unique = false, insertable = true, updatable = true)
	private AclObjectIdentity aclObjectIdentity;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "SID", referencedColumnName = "ID", nullable = false, unique = false, insertable = true, updatable = true)
	private AclSid sid;
}