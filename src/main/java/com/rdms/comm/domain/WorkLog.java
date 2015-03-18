package com.rdms.comm.domain;

import java.io.Serializable;
import java.util.Date;

import com.rdms.comm.action.model.WorkLogModel;

public class WorkLog implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private Employee emp;
	private Date createTime;
	private int workTimes;
	private String content;
	
	public WorkLog() {}
	
	public static WorkLogModel toModel(WorkLog entity) {
		WorkLog workLog = entity;
		WorkLogModel model = new WorkLogModel();
		model.setId(workLog.getId());
		model.setEid(workLog.getEmp().getId());
		model.setCreateTime(workLog.getCreateTime());
		model.setCreateTimeUTC(workLog.getCreateTime().getTime());
		model.setWorkTimes(workLog.getWorkTimes());
		model.setContent(workLog.getContent());
		return model;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Employee getEmp() {
		return emp;
	}
	public void setEmp(Employee emp) {
		this.emp = emp;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getWorkTimes() {
		return workTimes;
	}
	public void setWorkTimes(int workTimes) {
		this.workTimes = workTimes;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

}
