package com.rdms.bug.action.model;

import java.util.Date;
import java.util.Map;

import org.apache.struts2.json.annotations.JSON;

import com.rdms.base.action.model.BaseModel;
import com.rdms.bug.domain.BugTrace;
import com.rdms.comm.action.model.EmployeeModel;

public class BugTraceModel extends BaseModel {
	
	private String id;
	private String title;
	private String bid;
	private String type;
	private Date createTime;
	private String detail;
	private String attachment;
	
	private String fromWhoId;
	private String toWhoId;
	
	private EmployeeModel fromWho;
	private EmployeeModel toWho;

	public BugTraceModel() {}
	
	@Override
	@JSON(serialize = false)
	public Map<String, Class<?>> getComplexObjClassMapOfModel() {
		return null;
	}
	
	@Override
	public BaseModel toModel(Object e) {
		if(e == null) return new BugTraceModel();
		BugTrace entity = (BugTrace) e;
		return toModel(entity);
	}
	
	public static BugTraceModel toModel(BugTrace entity) {
		BugTraceModel model = new BugTraceModel();
		model.setId(entity.getId());
		model.setTitle(entity.getTitle());
		model.setBid(entity.getBug().getId());
		model.setType(entity.getType());
		model.setCreateTime(entity.getCreateTime());
		model.setDetail(entity.getDetail());
		model.setAttachment(entity.getAttachment());
		model.setFromWhoId(entity.getFromWho().getId());
		model.setToWhoId(entity.getToWho().getId());
		model.setFromWho(EmployeeModel.toModel(entity.getFromWho()));
		model.setToWho(EmployeeModel.toModel(entity.getToWho()));
		return model;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getFromWhoId() {
		return fromWhoId;
	}

	public void setFromWhoId(String fromWhoId) {
		this.fromWhoId = fromWhoId;
	}

	public String getToWhoId() {
		return toWhoId;
	}

	public void setToWhoId(String toWhoId) {
		this.toWhoId = toWhoId;
	}

	public EmployeeModel getFromWho() {
		return fromWho;
	}

	public void setFromWho(EmployeeModel fromWho) {
		this.fromWho = fromWho;
	}

	public EmployeeModel getToWho() {
		return toWho;
	}

	public void setToWho(EmployeeModel toWho) {
		this.toWho = toWho;
	}

}
