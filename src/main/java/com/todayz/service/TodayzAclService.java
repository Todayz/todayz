package com.todayz.service;

import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;

public interface TodayzAclService<T> {
	public void addPermission(T object, Sid recipient, Permission permission);

	public void deletePermission(T object, Sid recipient, Permission permission);

	public void deleteAcl(T object);
}
