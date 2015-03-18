package com.rdms.comm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rdms.base.PageBean;
import com.rdms.base.service.impl.BaseServiceImpl;
import com.rdms.comm.dao.ProjectDao;
import com.rdms.comm.domain.Project;
import com.rdms.comm.service.ProjectService;

@Service("projectService")
public class ProjectServiceImpl extends BaseServiceImpl<Project> implements ProjectService {

	private ProjectDao projectDao;

	public ProjectDao getProjectDao() {
		return projectDao;
	}

	@Resource(name="projectDao")
	public void setProjectDao(ProjectDao projectDao) {
		super.setBaseDAO(projectDao);
		this.projectDao = projectDao;
	}

	@Override
	public List<Project> queryJoinPj(String[] pjGrIds) throws Exception {
		return this.projectDao.queryJoinPj(pjGrIds);
	}

	@Override
	public PageBean<Project> queryJoinPjByPage(String[] pjGrIds, int offset,
			int limit, String orderBy, boolean asc) throws Exception {
		return this.projectDao.queryJoinPjByPage(pjGrIds, offset, limit, orderBy, asc);
	}

	@Override
	public List<Object[]> countEachDeptPj() throws Exception {
		return this.projectDao.countEachDeptPj();
	}

	@Override
	public List<Project> queryPjByDept(String deptName) throws Exception {
		return this.projectDao.queryPjByDept(deptName);
	}
}
