package com.rdms.auth.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rdms.auth.dao.ActionDao;
import com.rdms.auth.domain.Action;
import com.rdms.auth.service.ActionService;
import com.rdms.base.service.impl.BaseServiceImpl;

@Service("actionService")
public class ActionServiceImpl extends BaseServiceImpl<Action> implements ActionService {
	
	private ActionDao actionDao;
	
	@Resource(name="actionDao")
	public void setActionDao(ActionDao actionDao) {
		super.setBaseDAO(actionDao);
		this.actionDao = actionDao;
	}

}
