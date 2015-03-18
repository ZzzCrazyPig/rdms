package com.rdms.comm.service;

import java.util.List;

import com.rdms.base.PageBean;
import com.rdms.base.action.model.BaseModel;
import com.rdms.base.service.BaseService;
import com.rdms.comm.domain.PjGroup;

public interface PjGroupService extends BaseService<PjGroup> {
	
	public PageBean<PjGroup> queryFetchPjGrMemsByPage(int offset, int limit,
			BaseModel model, String orderBy, String asc) throws Exception;
	
	public List<PjGroup> queryAllJoinPjGroup(String eid) throws Exception;
	
	// 统计每个部门负责的项目组个数
	public List<Object[]> countEachDeptPjGroup() throws Exception;

}
