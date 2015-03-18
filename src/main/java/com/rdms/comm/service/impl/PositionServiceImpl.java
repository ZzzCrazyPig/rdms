package com.rdms.comm.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rdms.base.service.impl.BaseServiceImpl;
import com.rdms.comm.dao.PositionDao;
import com.rdms.comm.domain.Position;
import com.rdms.comm.service.PositionService;

@Service("positionService")
public class PositionServiceImpl extends BaseServiceImpl<Position> implements PositionService { 

	private PositionDao positionDao;
	
	@Resource(name="positionDao")
	public void setPositionDao(PositionDao positionDao) {
		super.setBaseDAO(positionDao);
		this.positionDao = positionDao;
	}

	public PositionDao getPositionDao() {
		return positionDao;
	}

	public boolean isAvailable(String name) throws Exception {
		return this.positionDao.isAvailable(name);
	}
	
}
