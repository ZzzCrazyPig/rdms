package com.rdms.sys.service;

import com.rdms.base.service.BaseService;
import com.rdms.sys.domain.Page;

public interface PageService extends BaseService<Page> {

	public boolean isAvailable(String code) throws Exception;
	
	public Page findByCode(String code) throws Exception;
}
