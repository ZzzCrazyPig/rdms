package com.rdms.sys.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rdms.base.service.impl.BaseServiceImpl;
import com.rdms.sys.dao.PageDao;
import com.rdms.sys.domain.Page;
import com.rdms.sys.service.PageService;

@Service("pageService")
public class PageServiceImpl extends BaseServiceImpl<Page> implements PageService {

	private PageDao pageDao;
	
	@Resource(name="pageDao")
	public void setPageDao(PageDao pageDao) {
		super.setBaseDAO(pageDao);
		this.pageDao = pageDao;
	}

	public PageDao getPageDao() {
		return pageDao;
	}

	public boolean isAvailable(String code) throws Exception {
		return this.pageDao.isAvailable(code);
	}

	public Page findByCode(String code) throws Exception {
		return this.pageDao.findByCode(code);
	}
	
}
