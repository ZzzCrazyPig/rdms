package com.rdms.bug.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rdms.base.service.impl.BaseServiceImpl;
import com.rdms.bug.dao.BugInfoDao;
import com.rdms.bug.domain.BugInfo;
import com.rdms.bug.service.BugInfoService;

@Service("bugInfoService")
public class BugInfoServiceImpl extends BaseServiceImpl<BugInfo> implements BugInfoService {

	private BugInfoDao bugInfoDao;

	public BugInfoDao getBugInfoDao() {
		return bugInfoDao;
	}

	@Resource(name="bugInfoDao")
	public void setBugInfoDao(BugInfoDao bugInfoDao) {
		super.setBaseDAO(bugInfoDao);
		this.bugInfoDao = bugInfoDao;
	}
	
}
