package com.rdms.comm.dao;

import com.rdms.base.dao.BaseDao;
import com.rdms.comm.domain.Department;

public interface DepartmentDao extends BaseDao<Department> {

	/**
	 * 检测部门名字或者部门代码是否被使用过
	 * @param name
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public boolean isAvailable(String name, String code) throws Exception;
	
	/**
	 * 根据名字查找部门
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public Department findByName(String name) throws Exception;
	
}
