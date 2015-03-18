package com.rdms.comm.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rdms.base.dao.impl.BaseDaoImpl;
import com.rdms.comm.dao.EmployeeDao;
import com.rdms.comm.domain.Employee;

@Repository("employeeDao")
public class EmployeeDaoImpl extends BaseDaoImpl<Employee> implements EmployeeDao {

	public Employee validate(String account, String pwd) throws Exception {
		final String hql = "FROM Employee AS e WHERE e.account = ? AND e.pwd = ?";
		List<Employee> result = this.find(hql, account, pwd);
		return result.size() > 0 ? result.get(0) : null;
	}
	
}
