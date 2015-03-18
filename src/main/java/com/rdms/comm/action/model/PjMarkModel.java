package com.rdms.comm.action.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.rdms.base.action.model.BaseModel;

public class PjMarkModel extends BaseModel {
	
	private String id;
	private Date createTime;
//	private Project project;
	private String pid;
	private String content;
	private String attachment;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
}
