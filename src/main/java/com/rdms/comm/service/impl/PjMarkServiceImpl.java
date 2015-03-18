package com.rdms.comm.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rdms.base.service.impl.BaseServiceImpl;
import com.rdms.comm.dao.PjMarkDao;
import com.rdms.comm.domain.PjMark;
import com.rdms.comm.service.PjMarkService;

@Service("pjMarkService")
public class PjMarkServiceImpl extends BaseServiceImpl<PjMark> implements PjMarkService {
	
	private PjMarkDao pjMarkDao;

	public PjMarkDao getPjMarkDao() {
		return pjMarkDao;
	}

	@Resource(name="pjMarkDao")
	public void setPjMarkDao(PjMarkDao pjMarkDao) {
		super.setBaseDAO(pjMarkDao);
		this.pjMarkDao = pjMarkDao;
	}

}
