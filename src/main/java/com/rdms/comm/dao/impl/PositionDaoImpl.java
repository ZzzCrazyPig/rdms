package com.rdms.comm.dao.impl;

import org.springframework.stereotype.Repository;

import com.rdms.base.dao.impl.BaseDaoImpl;
import com.rdms.comm.dao.PositionDao;
import com.rdms.comm.domain.Position;

@Repository("positionDao")
public class PositionDaoImpl extends BaseDaoImpl<Position> implements PositionDao {

	public boolean isAvailable(String name) throws Exception {
		final String hql = "FROM Position AS p WHERE p.name = ?";
		return this.find(hql, name).size() == 0;
	}

}
