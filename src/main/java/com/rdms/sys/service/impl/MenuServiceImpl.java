package com.rdms.sys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rdms.base.PageBean;
import com.rdms.base.service.impl.BaseServiceImpl;
import com.rdms.sys.dao.MenuDao;
import com.rdms.sys.domain.Menu;
import com.rdms.sys.service.MenuService;

@Service("menuService")
public class MenuServiceImpl extends BaseServiceImpl<Menu> implements MenuService {

	private MenuDao menuDao;
	
	@Resource(name="menuDao")
	public void setMenuDao(MenuDao menuDao) {
		super.setBaseDAO(menuDao);
		this.menuDao = menuDao;
	}

	public MenuDao getMenuDao() {
		return menuDao;
	}

	public boolean isAvailable(String code) throws Exception {
		return this.menuDao.isAvailable(code);
	}
	
	public List<Menu> findMenuWithSpecPage(String pageId) throws Exception {
		return this.menuDao.findMenuWithSpecPage(pageId);
	}

	public PageBean<Menu> queryTreeByPage(int offset, int limit,
			String orderBy, String asc) throws Exception {
		return this.menuDao.queryTreeByPage(offset, limit, orderBy, asc);
	}

	public List<Menu> queryTree(String orderBy, String asc) throws Exception {
		return this.menuDao.queryTree(orderBy, asc);
	}
	
}
