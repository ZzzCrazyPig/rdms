package com.rdms.svn.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rdms.base.service.impl.BaseServiceImpl;
import com.rdms.svn.dao.SvnPjGrAuthDao;
import com.rdms.svn.domain.SvnPjGrAuth;
import com.rdms.svn.service.SvnPjGrAuthService;

@Service("svnPjGrAuthService")
public class SvnPjGrAuthServiceImpl extends BaseServiceImpl<SvnPjGrAuth> implements SvnPjGrAuthService {

	private SvnPjGrAuthDao svnPjGrAuthDao;

	public SvnPjGrAuthDao getSvnPjGrAuthDao() {
		return svnPjGrAuthDao;
	}

	@Resource(name="svnPjGrAuthDao")
	public void setSvnPjGrAuthDao(SvnPjGrAuthDao svnPjGrAuthDao) {
		this.svnPjGrAuthDao = svnPjGrAuthDao;
	}
}
