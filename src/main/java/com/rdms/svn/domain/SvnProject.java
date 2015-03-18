package com.rdms.svn.domain;

import java.io.Serializable;

import com.rdms.comm.domain.Project;

public class SvnProject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private Project project;
	private String path;
	private String url;
	private String detail;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
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

}
