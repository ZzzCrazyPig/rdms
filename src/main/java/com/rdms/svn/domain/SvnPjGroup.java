package com.rdms.svn.domain;

import java.io.Serializable;

public class SvnPjGroup implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private SvnProject svnProject;
	private String name;
	private String detail;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public SvnProject getSvnProject() {
		return svnProject;
	}
	public void setSvnProject(SvnProject svnProject) {
		this.svnProject = svnProject;
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
