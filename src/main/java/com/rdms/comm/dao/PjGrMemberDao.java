package com.rdms.comm.dao;

import java.util.List;

import com.rdms.base.dao.BaseDao;
import com.rdms.comm.domain.PjGrMember;

public interface PjGrMemberDao extends BaseDao<PjGrMember> {
	
	public List<PjGrMember> queryBySpecPjGroup(String pjGrId) throws Exception;
	
}
