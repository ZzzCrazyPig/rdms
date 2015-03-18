package com.rdms.comm.action.model;

import java.io.Serializable;

public class ChangePwdModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String eid;
	private String oldPwd;
	private String newPwd;
	private String confirmPwd;
	
	public ChangePwdModel() {}
	
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public String getOldPwd() {
		return oldPwd;
	}
	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}
	public String getNewPwd() {
		return newPwd;
	}
	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}
	public String getConfirmPwd() {
		return confirmPwd;
	}
	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}
	
}
