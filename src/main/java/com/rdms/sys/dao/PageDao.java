package com.rdms.sys.dao;

import com.rdms.base.dao.BaseDao;
import com.rdms.sys.domain.Page;

public interface PageDao extends BaseDao<Page> {
	
	/**
	 * 检测页面代码是否可用
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public boolean isAvailable(String code) throws Exception;
	
	/**
	 * 根据页面代码查找页面对象
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public Page findByCode(String code) throws Exception;

}
