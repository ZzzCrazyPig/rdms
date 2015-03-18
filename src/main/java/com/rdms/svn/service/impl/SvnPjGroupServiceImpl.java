package com.rdms.svn.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rdms.base.service.impl.BaseServiceImpl;
import com.rdms.svn.dao.SvnPjGroupDao;
import com.rdms.svn.domain.SvnPjGroup;
import com.rdms.svn.service.SvnPjGroupService;

@Service("svnPjGroupService")
public class SvnPjGroupServiceImpl extends BaseServiceImpl<SvnPjGroup> implements SvnPjGroupService {

	private SvnPjGroupDao svnPjGroupDao;

	public SvnPjGroupDao getSvnPjGroupDao() {
		return svnPjGroupDao;
	}

	@Resource(name="svnPjGroupDao")
	public void setSvnPjGroupDao(SvnPjGroupDao svnPjGroupDao) {
		this.svnPjGroupDao = svnPjGroupDao;
	}
	
}
