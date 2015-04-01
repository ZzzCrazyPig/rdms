package com.rdms.svn.action.model;

import java.util.Map;

import org.apache.struts2.json.annotations.JSON;

import com.rdms.base.action.model.BaseModel;
//import com.rdms.svn.domain.SvnProject;

public class SvnPjUserModel extends BaseModel {
	
	private String id;
//	private SvnProject svnProject;
	private String svnPjId;
	private String account;
	private String pwd;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSvnPjId() {
		return svnPjId;
	}

	public void setSvnPjId(String svnPjId) {
		this.svnPjId = svnPjId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Override
	public BaseModel toModel(Object e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@JSON(serialize = false)
	public Map<String, Class<?>> getComplexObjClassMapOfModel() {
		// TODO Auto-generated method stub
		return null;
	}
}
