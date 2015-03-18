package com.rdms.comm.action.model;

import java.util.Date;

import com.rdms.base.action.model.BaseModel;

public class WorkLogModel extends BaseModel {
	
	private String id;
	private String eid;
	private Long createTimeUTC;
	private Date createTime;
	private int workTimes;
	private String content;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public Date getCreateTime() {
		if(this.createTimeUTC != null) {
			this.createTime = new Date(this.createTimeUTC);
		}
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

	public Long getCreateTimeUTC() {
		return createTimeUTC;
	}

	public void setCreateTimeUTC(Long createTimeUTC) {
		this.createTimeUTC = createTimeUTC;
	}
}
