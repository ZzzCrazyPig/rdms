package com.rdms.comm.dao.impl;

import org.springframework.stereotype.Repository;

import com.rdms.base.dao.impl.BaseDaoImpl;
import com.rdms.comm.dao.DepartmentDao;
import com.rdms.comm.domain.Department;

@Repository("departmentDao")
public class DepartmentDaoImpl extends BaseDaoImpl<Department> implements DepartmentDao {

	/**
	 * 检测部门名字或者部门代码是否被使用过
	 * @param name
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public boolean isAvailable(String name, String code) throws Exception {
		final String hql = "FROM Department AS d WHERE d.name = ? OR d.deptCode = ?";
		return this.find(hql, name, code).size() == 0;
	}

	
	/**
	 * 根据名字查找部门
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public Department findByName(String name) throws Exception {
		final String hql = "FROM Department AS d WHERE d.name = ?";
		return this.find(hql, name).get(0);
	}

}
