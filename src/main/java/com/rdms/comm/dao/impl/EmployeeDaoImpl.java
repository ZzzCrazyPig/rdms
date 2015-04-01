package com.rdms.comm.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.rdms.auth.domain.Action;
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> countNumsByDept(Object[] empIds) throws Exception {
		final String hql = "SELECT e.dept, COUNT(*) FROM Employee AS e WHERE e.id IN (:ids) GROUP BY e.dept";
		Query query = this.getSession().createQuery(hql);
		query.setParameterList("ids", empIds);
		List<Object[]> result = query.list();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Action> queryBan(String uid) throws Exception {
		final String hql = "SELECT b FROM Employee AS e LEFT JOIN e.roles AS r LEFT JOIN r.bans AS b WHERE e.id = ?";
		Query query = this.getSession().createQuery(hql);
		List<Action> bans = query.list();
		return bans;
	}
	
}
