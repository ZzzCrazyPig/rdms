package com.rdms.comm.dao;

import java.util.List;

import com.rdms.auth.domain.Action;
import com.rdms.base.dao.BaseDao;
import com.rdms.comm.domain.Employee;

public interface EmployeeDao extends BaseDao<Employee> {
	
	public Employee validate(String account, String pwd) throws Exception;
	
	public List<Object[]> countNumsByDept(Object[] empIds) throws Exception;
	
	// 查询用户所被禁止的行为
	public List<Action> queryBan(String uid) throws Exception;

}
