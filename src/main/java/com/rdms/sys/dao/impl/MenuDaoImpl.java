package com.rdms.sys.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rdms.base.PageBean;
import com.rdms.base.dao.impl.BaseDaoImpl;
import com.rdms.sys.dao.MenuDao;
import com.rdms.sys.domain.Menu;

@Repository("menuDao")
public class MenuDaoImpl extends BaseDaoImpl<Menu> implements MenuDao {
	
	public boolean isAvailable(String code) throws Exception {
		final String hql = "FROM Menu AS m WHERE m.code = ?";
		return this.find(hql, code).size() == 0;
	}
	
	public List<Menu> findMenuWithSpecPage(String pageId) throws Exception {
		final String hql = "FROM Menu AS m WHERE m.page.id = ?";
		return this.find(hql, pageId);
	}

	public PageBean<Menu> queryTreeByPage(int offset, int limit,String orderBy, String asc) throws Exception {
		final String hql = "FROM Menu AS m WHERE m.parent is NULL";
		return this.getPageBean(offset, limit, hql, new Object[]{}, orderBy, asc);
	}

	public List<Menu> queryTree(String orderBy, String asc) throws Exception {
		final String hql = "FROM Menu AS m WHERE m.parent is NULL ORDER BY " + orderBy + " " + asc.toUpperCase();
		return this.find(hql, new Object[]{});
	}

}
