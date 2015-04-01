package com.rdms.auth.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rdms.auth.dao.RoleDao;
import com.rdms.auth.domain.Role;
import com.rdms.auth.service.RoleService;
import com.rdms.base.service.impl.BaseServiceImpl;

@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
	
	private RoleDao roleDao;
	
	@Resource(name="roleDao")
	public void setRoleDao(RoleDao roleDao) {
		super.setBaseDAO(roleDao);
		this.roleDao = roleDao;
	}

	public RoleDao getRoleDao() {
		return roleDao;
	}
	

}
