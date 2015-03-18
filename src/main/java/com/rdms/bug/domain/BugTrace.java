package com.rdms.bug.domain;

import java.io.Serializable;
import java.util.Date;

import com.rdms.comm.domain.Employee;

public class BugTrace implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String title;
	private BugInfo bug;
	private String type;
	private Date createTime;
	private String detail;
	private String attachment;
	
	private Employee fromWho;
	private Employee toWho;
	
	public BugTrace() {}
	
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
	public BugInfo getBug() {
		return bug;
	}
	public void setBug(BugInfo bug) {
		this.bug = bug;
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

	public Employee getFromWho() {
		return fromWho;
	}

	public void setFromWho(Employee fromWho) {
		this.fromWho = fromWho;
	}

	public Employee getToWho() {
		return toWho;
	}

	public void setToWho(Employee toWho) {
		this.toWho = toWho;
	}

}
