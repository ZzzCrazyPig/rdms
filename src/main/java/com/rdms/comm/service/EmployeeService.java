package com.rdms.comm.service;

import java.util.List;

import com.rdms.auth.domain.Action;
import com.rdms.base.PageBean;
import com.rdms.base.service.BaseService;
import com.rdms.comm.action.model.EmployeeModel;
import com.rdms.comm.domain.Employee;

public interface EmployeeService extends BaseService<Employee> {

	public PageBean<Employee> queryByPage(int offset, int limit, EmployeeModel model, String orderBy, String asc) throws Exception;
	
	public Employee validate(String account, String pwd) throws Exception;
	
	public List<Object[]> countNumsByDept(Object[] empIds) throws Exception;
	
	public List<Action> queryBan(String uid) throws Exception;
	
}
