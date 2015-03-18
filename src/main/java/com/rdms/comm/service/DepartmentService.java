package com.rdms.comm.service;

import com.rdms.base.service.BaseService;
import com.rdms.comm.domain.Department;

public interface DepartmentService extends BaseService<Department> {

	public boolean isAvailable(String name, String code) throws Exception;
	
	public Department findByName(String name) throws Exception;
}
