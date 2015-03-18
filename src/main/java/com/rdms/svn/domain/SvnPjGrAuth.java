package com.rdms.svn.domain;

import java.io.Serializable;

public class SvnPjGrAuth implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private SvnProject svnProject;
	private SvnPjGroup svnPjGroup;
	private String resource;
	private String auth;
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
	public SvnPjGroup getSvnPjGroup() {
		return svnPjGroup;
	}
	public void setSvnPjGroup(SvnPjGroup svnPjGroup) {
		this.svnPjGroup = svnPjGroup;
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
