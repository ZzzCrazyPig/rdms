package com.rdms.svn.action.model;

import com.rdms.base.action.model.BaseModel;
//import com.rdms.comm.domain.Project;
import com.rdms.svn.domain.SvnProject;

public class SvnProjectModel extends BaseModel {
	
	private String id;
//	private Project project;
	private String path;
	private String url;
	private String detail;
	
	private String pid;
	private String pjName;
	
	public SvnProjectModel() {}
	
	public static SvnProjectModel toModel(SvnProject svnPj) {
		SvnProjectModel model = new SvnProjectModel();
		model.setId(svnPj.getId());
		model.setPath(svnPj.getPath());
		model.setUrl(svnPj.getUrl());
		model.setDetail(svnPj.getDetail());
		model.setPid(svnPj.getProject().getId());
		model.setPjName(svnPj.getProject().getName());
		return model;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
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

	public String getPjName() {
		return pjName;
	}

	public void setPjName(String pjName) {
		this.pjName = pjName;
	}
	
}
