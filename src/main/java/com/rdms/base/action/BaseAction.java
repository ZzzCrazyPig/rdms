package com.rdms.base.action;

import javax.annotation.Resource;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.rdms.base.action.model.AppModel;
import com.rdms.base.vo.AppVO;

public abstract class BaseAction extends ActionSupport implements ModelDriven<AppModel> {
	
	@Resource(name="appModel")
	private AppModel appModel;
	@Resource(name="appVO")
	private AppVO appVO;

	private static final long serialVersionUID = 1L;
	
	public abstract String insert();
	
	public abstract String update();
	
	public abstract String delete();
	
	public abstract String multiDelete();
	
	public abstract String query();
	
	public abstract String queryByPage();
	
	public abstract Object toEntity(Object model, Object entity) throws Exception;
	
//	public abstract Object toModel(Object entity) throws Exception;

	public AppModel getAppModel() {
		return appModel;
	}

	public void setAppModel(AppModel appModel) {
		this.appModel = appModel;
	}

	public AppVO getAppVO() {
		return appVO;
	}

	public void setAppVO(AppVO appVO) {
		this.appVO = appVO;
	}

	public AppModel getModel() {
		return this.appModel;
	}
	
	
}
