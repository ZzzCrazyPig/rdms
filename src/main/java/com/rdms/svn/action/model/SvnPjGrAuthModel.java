package com.rdms.svn.action.model;

import com.rdms.base.action.model.BaseModel;

public class SvnPjGrAuthModel extends BaseModel {
	
	private String id;
	private String svnPjId;
	private String svnPjGrId;
	private String resource;
	private String auth;

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

	public String getSvnPjGrId() {
		return svnPjGrId;
	}

	public void setSvnPjGrId(String svnPjGrId) {
		this.svnPjGrId = svnPjGrId;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

}
