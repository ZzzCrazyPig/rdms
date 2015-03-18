package com.rdms.comm.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rdms.base.service.impl.BaseServiceImpl;
import com.rdms.comm.dao.WorkLogDao;
import com.rdms.comm.domain.WorkLog;
import com.rdms.comm.service.WorkLogService;

@Service("workLogService")
public class WorkLogServiceImpl extends BaseServiceImpl<WorkLog> implements WorkLogService {

	private WorkLogDao workLogDao;

	public WorkLogDao getWorkLogDao() {
		return workLogDao;
	}

	@Resource(name="workLogDao")
	public void setWorkLogDao(WorkLogDao workLogDao) {
		super.setBaseDAO(workLogDao);
		this.workLogDao = workLogDao;
	}

	public List<WorkLog> queryOneWeek(String eid, Date startDate, Date endDate)
			throws Exception {
		return this.workLogDao.queryOneWeek(eid, startDate, endDate);
	}

	@Override
	public List<Object[]> countEachDeptEachEmpWorkLog(Date startDate,
			Date endDate) throws Exception {
		return this.workLogDao.countEachDeptEachEmpWorkLog(startDate, endDate);
	}

	@Override
	public List<Object[]> countSpecDeptEachEmpWorkLog(Date begin, Date end,
			String dept) throws Exception {
		return this.workLogDao.countSpecDeptEachEmpWorkLog(begin, end, dept);
	}
}
