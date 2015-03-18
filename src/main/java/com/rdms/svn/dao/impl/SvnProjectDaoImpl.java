package com.rdms.svn.dao.impl;

import org.springframework.stereotype.Repository;

import com.rdms.base.dao.impl.BaseDaoImpl;
import com.rdms.svn.dao.SvnProjectDao;
import com.rdms.svn.domain.SvnProject;

@Repository("svnProjectDao")
public class SvnProjectDaoImpl extends BaseDaoImpl<SvnProject> implements SvnProjectDao {

}
