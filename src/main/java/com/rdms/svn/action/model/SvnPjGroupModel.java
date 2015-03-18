package com.rdms.svn.action.model;

import com.rdms.base.action.model.BaseModel;
//import com.rdms.svn.domain.SvnProject;

public class SvnPjGroupModel extends BaseModel {
	
	private String id;
//	private SvnProject svnProject;
	private String svnPjId;
	private String name;
	private String detail;
	
	public static final String ENTITY_ID = "id";
//	public static final String ENTITY_SVN_PROJECT = "svnProject.id";
	public static final String ENTITY_NAME = "name";
	public static final String ENTITY_DETAIL = "detail";

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
