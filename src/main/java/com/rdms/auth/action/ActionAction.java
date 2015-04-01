package com.rdms.auth.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.rdms.auth.action.model.ActionModel;
import com.rdms.auth.domain.Action;
import com.rdms.auth.service.ActionService;
import com.rdms.base.action.GeneralAction;

@Controller("actionAction")
@Scope("prototype")
public class ActionAction extends GeneralAction<Action, ActionService, ActionModel> {

	private static final long serialVersionUID = 1L;
	
	private ActionService actionService;
	
	@Resource(name="actionService")
	public void setActionService(ActionService actionService) {
		super.setBaseService(actionService);
		this.actionService = actionService;
	}

	public ActionService getActionService() {
		return actionService;
	}
	
	// 映射对应的
	public String mapRole() {
		return SUCCESS;
	}


	@Override
	protected Action toEntity(ActionModel model, Action entity) {
		ActionModel actionModel = (ActionModel) model;
		Action actionEntity = null;
		if(entity == null) {
			actionEntity = new Action();
		} else {
			actionEntity = (Action) entity;
		}
		actionEntity.setId(actionModel.getId());
		actionEntity.setName(actionModel.getName());
		actionEntity.setUrl(actionModel.getUrl());
		actionEntity.setDetail(actionModel.getDetail());
		return actionEntity;
	}

}
