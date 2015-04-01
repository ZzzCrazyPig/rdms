package com.rdms.auth.dao.impl;

import org.springframework.stereotype.Repository;

import com.rdms.auth.dao.RoleDao;
import com.rdms.auth.domain.Role;
import com.rdms.base.dao.impl.BaseDaoImpl;

@Repository("roleDao")
public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao {

}
