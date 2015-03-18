package com.rdms.bug.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rdms.base.service.impl.BaseServiceImpl;
import com.rdms.bug.dao.BugTraceDao;
import com.rdms.bug.domain.BugTrace;
import com.rdms.bug.service.BugTraceService;

@Service("bugTraceService")
public class BugTraceServiceImpl extends BaseServiceImpl<BugTrace> implements BugTraceService {

	private BugTraceDao bugTraceDao;

	public BugTraceDao getBugTraceDao() {
		return bugTraceDao;
	}

	@Resource(name="bugTraceDao")
	public void setBugTraceDao(BugTraceDao bugTraceDao) {
		super.setBaseDAO(bugTraceDao);
		this.bugTraceDao = bugTraceDao;
	}
}
