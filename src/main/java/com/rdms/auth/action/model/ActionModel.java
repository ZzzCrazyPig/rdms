package com.rdms.auth.action.model;

import java.util.Map;

import org.apache.struts2.json.annotations.JSON;

import com.rdms.auth.domain.Action;
import com.rdms.base.action.model.BaseModel;

public class ActionModel extends BaseModel {
	
	private String id;
	private String name;
	private String url;
	private String detail;
	
	public ActionModel() {}
	
	@Override
	@JSON(serialize = false)
	public Map<String, Class<?>> getComplexObjClassMapOfModel() {
		return getClassMap();
	}
	
	public static Map<String, Class<?>> getClassMap() {
		return null;
	}
	
	@Override
	public BaseModel toModel(Object e) {
		if(e == null) return new ActionModel();
		Action entity = (Action) e;
		return toModel(entity);
	}
	
	public static ActionModel toModel(Action entity) {
		ActionModel model = new ActionModel();
		model.setId(entity.getId());
		model.setName(entity.getName());
		model.setUrl(entity.getUrl());
		model.setDetail(entity.getDetail());
		return model;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}

}
