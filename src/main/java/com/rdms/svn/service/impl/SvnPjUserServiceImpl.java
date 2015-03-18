package com.rdms.svn.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rdms.base.service.impl.BaseServiceImpl;
import com.rdms.svn.dao.SvnPjUserDao;
import com.rdms.svn.domain.SvnPjUser;
import com.rdms.svn.service.SvnPjUserService;

@Service("svnPjUserService")
public class SvnPjUserServiceImpl extends BaseServiceImpl<SvnPjUser> implements SvnPjUserService {
	
	private SvnPjUserDao svnPjUserDao;

	public SvnPjUserDao getSvnPjUserDao() {
		return svnPjUserDao;
	}

	@Resource(name="svnPjUserDao")
	public void setSvnPjUserDao(SvnPjUserDao svnPjUserDao) {
		this.svnPjUserDao = svnPjUserDao;
	}
}
