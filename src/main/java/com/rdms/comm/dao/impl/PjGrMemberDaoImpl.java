package com.rdms.comm.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rdms.base.dao.impl.BaseDaoImpl;
import com.rdms.comm.dao.PjGrMemberDao;
import com.rdms.comm.domain.PjGrMember;

@Repository("pjGrMemberDao")
public class PjGrMemberDaoImpl extends BaseDaoImpl<PjGrMember> implements PjGrMemberDao {

	@Override
	public List<PjGrMember> queryBySpecPjGroup(String pjGrId) throws Exception {
		final String hql = "FROM PjGrMember AS pgm WHERE pgm.pjGroup.id = ?";
		return this.find(hql, pjGrId);
	}
	
}
