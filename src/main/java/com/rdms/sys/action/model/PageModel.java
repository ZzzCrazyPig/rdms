package com.rdms.sys.action.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.rdms.base.action.model.BaseModel;
import com.rdms.sys.domain.Page;

@Component("pageModel")
@Scope("prototype")
public class PageModel extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String code;
	private String name;
	private String url;
	private String createUser;
	private Date createTime;
	
	public static Map<String, Class<?>> getClassMap() {
		return null;
	}
	
	public static PageModel toModel(Page entity) {
		Page pageEntity = entity;
		PageModel model = new PageModel();
		model.setId(pageEntity.getId());
		model.setCode(pageEntity.getCode());
		model.setName(pageEntity.getName());
		model.setUrl(pageEntity.getUrl());
		model.setCreateUser(pageEntity.getCreateUser());
		model.setCreateTime(pageEntity.getCreateTime());
		return model;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
