package com.rdms.comm.dao;

import java.util.List;

import com.rdms.base.PageBean;
import com.rdms.base.dao.BaseDao;
import com.rdms.comm.domain.Project;

public interface ProjectDao extends BaseDao<Project> {
	
	public List<Project> queryJoinPj(String[] pjGrIds) throws Exception;

	public PageBean<Project> queryJoinPjByPage(String[] pjGrIds, int offset, int limit, String orderBy, boolean asc) throws Exception;
	
	public List<Project> queryPjByDept(String deptName) throws Exception;
	
	// 统计每个部门负责的项目个数
	public List<Object[]> countEachDeptPj() throws Exception;
}
