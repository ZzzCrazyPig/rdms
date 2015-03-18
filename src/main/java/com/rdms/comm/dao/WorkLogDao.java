package com.rdms.comm.dao;

import java.util.Date;
import java.util.List;

import com.rdms.base.dao.BaseDao;
import com.rdms.comm.domain.WorkLog;

public interface WorkLogDao extends BaseDao<WorkLog> {
	
	public List<WorkLog> queryOneWeek(String eid, Date startDate, Date endDate) throws Exception;
	
	/**
	 * 统计每个部门每个人的考勤情况
	 * @param begin 开始日期
	 * @param end 结束日期
	 * @return List<Object[]>
	 * @throws Exception
	 */
	public List<Object[]> countEachDeptEachEmpWorkLog(Date begin, Date end) throws Exception;
	
	/**
	 * 统计特定部门每个人的考勤情况
	 * @param begin
	 * @param end
	 * @param dept
	 * @return
	 * @throws Exception
	 */
	public List<Object[]> countSpecDeptEachEmpWorkLog(Date begin, Date end, String dept) throws Exception;
}
