package com.rdms.comm.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rdms.base.PageBean;
import com.rdms.base.service.impl.BaseServiceImpl;
import com.rdms.comm.action.model.EmployeeModel;
import com.rdms.comm.dao.EmployeeDao;
import com.rdms.comm.domain.Employee;
import com.rdms.comm.service.EmployeeService;

@Service("employeeService")
public class EmployeeServiceImpl extends BaseServiceImpl<Employee> implements EmployeeService {
	
	private EmployeeDao employeeDao;
	
	@Resource(name="employeeDao")
	public void setEmployeeDao(EmployeeDao employeeDao) {
		super.setBaseDAO(employeeDao);
		this.employeeDao = employeeDao;
	}

	public PageBean<Employee> queryByPage(int offset, int limit,
			EmployeeModel model, String orderBy, String asc) throws Exception{
		// if you have other else to do , write this
		return super.queryByPage(offset, limit, model, orderBy, asc);
	}

	public EmployeeDao getEmployeeDao() {
		return employeeDao;
	}

	public Employee validate(String account, String pwd) throws Exception {
		return this.employeeDao.validate(account, pwd);
	}

}
