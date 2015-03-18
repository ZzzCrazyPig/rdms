package com.rdms.comm.service;

import com.rdms.base.service.BaseService;
import com.rdms.comm.domain.Position;

public interface PositionService extends BaseService<Position> {
	
	public boolean isAvailable(String name) throws Exception;

}
