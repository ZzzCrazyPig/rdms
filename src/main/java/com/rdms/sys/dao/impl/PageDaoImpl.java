package com.rdms.sys.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rdms.base.dao.impl.BaseDaoImpl;
import com.rdms.sys.dao.PageDao;
import com.rdms.sys.domain.Page;

@Repository("pageDao")
public class PageDaoImpl extends BaseDaoImpl<Page> implements PageDao {

	/**
	 * 检测页面代码是否可用
	 */
	public boolean isAvailable(String code) throws Exception {
		final String hql = "FROM Page AS p WHERE p.code = ?";
		return this.find(hql, code).size() == 0;
	}
	
	/**
	 * 根据页面代码查找页面对象
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public Page findByCode(String code) throws Exception {
		final String hql = "FROM Page AS p WHERE p.code = ?";
		List<Page> result = this.find(hql, code);
		return result.size() > 0 ? result.get(0) : null;
	}

}
