package com.rdms.comm.dao;

import java.util.List;
import java.util.Map;

import com.rdms.base.PageBean;
import com.rdms.base.dao.BaseDao;
import com.rdms.comm.domain.PjGroup;

public interface PjGroupDao extends BaseDao<PjGroup> {
	
	public PageBean<PjGroup> queryFetchPjGrMemsByPage(int offset, int limit, Map<String, Object> whereArgs, String orderBy, String asc) throws Exception;

	public List<PjGroup> queryAllJoinPjGroup(String eid) throws Exception;
	
	// 统计每个部门组织的项目组个数
	public List<Object[]> countEachDeptPjGroup() throws Exception;
}
