package com.rdms.svn.domain;

import java.io.Serializable;

public class SvnPjUser implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private SvnProject svnProject;
	private String account;
	private String pwd;
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
}
