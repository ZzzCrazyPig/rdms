package com.rdms.comm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rdms.base.service.impl.BaseServiceImpl;
import com.rdms.comm.dao.PjGrMemberDao;
import com.rdms.comm.domain.PjGrMember;
import com.rdms.comm.service.PjGrMemberService;

@Service("pjGrMemberService")
public class PjGrMemberServiceImpl extends BaseServiceImpl<PjGrMember> implements PjGrMemberService {

	private PjGrMemberDao pjGrMemberDao;
	
	@Resource(name="pjGrMemberDao")
	public void setPjGrMemberDao(PjGrMemberDao pjGrMemberDao) {
		super.setBaseDAO(pjGrMemberDao);
		this.pjGrMemberDao = pjGrMemberDao;
	}

	public PjGrMemberDao getPjGrMemberDao() {
		return pjGrMemberDao;
	}

	@Override
	public List<PjGrMember> queryBySpecPjGroup(String pjGrId) throws Exception {
		return this.pjGrMemberDao.queryBySpecPjGroup(pjGrId);
	}
	
}
