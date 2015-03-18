package com.rdms.sys.service;

import java.util.List;

import com.rdms.base.PageBean;
import com.rdms.base.service.BaseService;
import com.rdms.sys.domain.Menu;

public interface MenuService extends BaseService<Menu> {
	
	public boolean isAvailable(String code) throws Exception;
	
	public List<Menu> findMenuWithSpecPage(String pageId) throws Exception;
	
	public PageBean<Menu> queryTreeByPage(int offset, int limit, String orderBy, String asc) throws Exception;

	public List<Menu> queryTree(String orderBy, String asc) throws Exception;
}
