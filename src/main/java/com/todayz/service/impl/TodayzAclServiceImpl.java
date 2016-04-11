package com.todayz.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.stereotype.Service;

import com.todayz.domain.AclDomain;
import com.todayz.service.TodayzAclService;

@Service
@Transactional
public class TodayzAclServiceImpl<T extends AclDomain> implements TodayzAclService<T> {

	@Autowired
	private MutableAclService mutableAclService;

	@Override
	public void addPermission(T object, Sid recipient, Permission permission) {
		MutableAcl acl;
		ObjectIdentity oid = new ObjectIdentityImpl(object.getClass(), object.getId());

		try {
			acl = (MutableAcl) mutableAclService.readAclById(oid);
		} catch (NotFoundException nfe) {
			acl = mutableAclService.createAcl(oid);
		}

		acl.insertAce(acl.getEntries().size(), permission, recipient, true);
		mutableAclService.updateAcl(acl);

		// logger.debug("Added permission " + permission + " for Sid " +
		// recipient
		// + " contact " + contact);
	}

	@Override
	public void deletePermission(T object, Sid recipient, Permission permission) {
		ObjectIdentity oid = new ObjectIdentityImpl(object.getClass(), object.getId());
		MutableAcl acl = (MutableAcl) mutableAclService.readAclById(oid);

		// Remove all permissions associated with this particular recipient
		// (string
		// equality to KISS)
		List<AccessControlEntry> entries = acl.getEntries();

		for (int i = 0; i < entries.size(); i++) {
			if (entries.get(i).getSid().equals(recipient) && entries.get(i).getPermission().equals(permission)) {
				acl.deleteAce(i);
			}
		}

		mutableAclService.updateAcl(acl);
		/*
		 * if (logger.isDebugEnabled()) { logger.debug("Deleted contact " +
		 * contact + " ACL permissions for recipient " + recipient); }
		 */
	}

	@Override
	public void deleteAcl(T object) {
		ObjectIdentity oid = new ObjectIdentityImpl(object.getClass(), object.getId());
		mutableAclService.deleteAcl(oid, false);
	}
}
