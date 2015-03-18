package com.rdms.comm.service;

import java.util.List;

import com.rdms.base.service.BaseService;
import com.rdms.comm.domain.PjGrMember;

public interface PjGrMemberService extends BaseService<PjGrMember> {
	
	public List<PjGrMember> queryBySpecPjGroup(String pjGrId) throws Exception;

}
