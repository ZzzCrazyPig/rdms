package com.rdms.comm.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rdms.base.service.impl.BaseServiceImpl;
import com.rdms.comm.dao.DepartmentDao;
import com.rdms.comm.domain.Department;
import com.rdms.comm.service.DepartmentService;

@Service("departmentService")
public class DepartmentServiceImpl extends BaseServiceImpl<Department> implements DepartmentService {

	private DepartmentDao departmentDao;
	
	@Resource(name="departmentDao")
	public void setDepartmentDao(DepartmentDao departmentDao) {
		super.setBaseDAO(departmentDao);
		this.departmentDao = departmentDao;
	}
	
	public boolean isAvailable(String name, String code) throws Exception {
		return this.departmentDao.isAvailable(name, code);
	}

	public DepartmentDao getDepartmentDao() {
		return departmentDao;
	}

	public Department findByName(String name) throws Exception {
		return this.departmentDao.findByName(name);
	}


}
