package com.rdms.comm.service;

import java.util.List;

import com.rdms.base.PageBean;
import com.rdms.base.service.BaseService;
import com.rdms.comm.domain.Project;

public interface ProjectService extends BaseService<Project> {
	
	public List<Project> queryJoinPj(String[] pjGrIds) throws Exception;
	
	public PageBean<Project> queryJoinPjByPage(String[] pjGrIds, int offset, int limit, String orderBy, boolean asc) throws Exception;
	
	public List<Project> queryPjByDept(String deptName) throws Exception;
	
	// 统计每个部门负责的项目个数
	public List<Object[]> countEachDeptPj() throws Exception;

}
