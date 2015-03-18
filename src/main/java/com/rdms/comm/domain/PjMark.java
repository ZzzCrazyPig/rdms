package com.rdms.comm.domain;

import java.io.Serializable;
import java.util.Date;

import com.rdms.comm.action.model.PjMarkModel;

public class PjMark implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private Date createTime;
	private Project project;
	private String content;
	private String attachment;
	
	public static PjMarkModel toModel(PjMark entity) {
		PjMark pjMark = entity;
		PjMarkModel model = new PjMarkModel();
		model.setId(pjMark.getId());
		model.setContent(pjMark.getContent());
		model.setAttachment(pjMark.getAttachment());
		model.setPid(pjMark.getProject().getId());
		model.setCreateTime(pjMark.getCreateTime());
		return model;
	}
	
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
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
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
