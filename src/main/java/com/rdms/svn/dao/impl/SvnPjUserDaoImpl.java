package com.rdms.svn.dao.impl;

import org.springframework.stereotype.Repository;

import com.rdms.base.dao.impl.BaseDaoImpl;
import com.rdms.svn.dao.SvnPjUserDao;
import com.rdms.svn.domain.SvnPjUser;

@Repository("svnPjUserDao")
public class SvnPjUserDaoImpl extends BaseDaoImpl<SvnPjUser> implements SvnPjUserDao {

}
