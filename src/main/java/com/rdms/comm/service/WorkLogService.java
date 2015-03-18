package com.rdms.comm.service;

import java.util.Date;
import java.util.List;

import com.rdms.base.service.BaseService;
import com.rdms.comm.domain.WorkLog;

public interface WorkLogService extends BaseService<WorkLog> {
	
	public List<WorkLog> queryOneWeek(String eid, Date startDate, Date endDate) throws Exception;
	
	public List<Object[]> countEachDeptEachEmpWorkLog(Date startDate, Date endDate) throws Exception;

	public List<Object[]> countSpecDeptEachEmpWorkLog(Date begin, Date end, String dept) throws Exception;
}
