package com.hotclub.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.stereotype.Service;

import com.hotclub.domain.club.Club;
import com.hotclub.service.TodayzAclService;

@Service
@Transactional
public class TodayzAclServiceImpl<T extends Object> implements TodayzAclService<T> {

	@Autowired
	private MutableAclService mutableAclService;

	@Override
	public void addPermission(T t, Sid recipient, Permission permission) {
		/*MutableAcl acl;
		ObjectIdentity oid = new ObjectIdentityImpl(t.getClass(), t.getId());

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
*/	}
}
