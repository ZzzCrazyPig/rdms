package com.rdms.comm.action.model;

import java.util.Date;
import java.util.Map;

import org.apache.struts2.json.annotations.JSON;

import com.rdms.base.action.model.BaseModel;
import com.rdms.comm.domain.PjMark;

public class PjMarkModel extends BaseModel {
	
	private String id;
	private Date createTime;
//	private Project project;
	private String pid;
	private String content;
	private String attachment;
	
	@Override
	@JSON(serialize = false)
	public Map<String, Class<?>> getComplexObjClassMapOfModel() {
		return null;
	}
	
	@Override
	public BaseModel toModel(Object e) {
		if(e == null) return new PjMarkModel();
		PjMark entity = (PjMark) e;
		return toModel(entity);
	}
	
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
