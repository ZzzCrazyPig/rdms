package com.rdms.svn.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rdms.base.service.impl.BaseServiceImpl;
import com.rdms.svn.dao.SvnProjectDao;
import com.rdms.svn.domain.SvnProject;
import com.rdms.svn.service.SvnProjectService;

@Service("svnProjectService")
public class SvnProjectServiceImpl extends BaseServiceImpl<SvnProject> implements SvnProjectService {

	private SvnProjectDao svnProjectDao;

	public SvnProjectDao getSvnProjectDao() {
		return svnProjectDao;
	}

	@Resource(name="svnProjectDao")
	public void setSvnProjectDao(SvnProjectDao svnProjectDao) {
		super.setBaseDAO(svnProjectDao);
		this.svnProjectDao = svnProjectDao;
	}
}
