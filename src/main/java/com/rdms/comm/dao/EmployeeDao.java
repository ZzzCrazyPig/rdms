package com.rdms.comm.dao;

import com.rdms.base.dao.BaseDao;
import com.rdms.comm.domain.Employee;

public interface EmployeeDao extends BaseDao<Employee> {
	
	public Employee validate(String account, String pwd) throws Exception;

}
