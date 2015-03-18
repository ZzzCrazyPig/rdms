package com.rdms.comm.dao;

import com.rdms.base.dao.BaseDao;
import com.rdms.comm.domain.Position;

public interface PositionDao extends BaseDao<Position> {
	
	public boolean isAvailable(String name) throws Exception;

}
