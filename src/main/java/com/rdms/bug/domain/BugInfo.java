package com.rdms.bug.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.rdms.comm.domain.Employee;
import com.rdms.comm.domain.Project;

public class BugInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private Project project;
	private String title;
	private String detail;
	private Employee qa;
	private Employee dev;
	private String level;
	private String status;
	private Date createTime;
	private Integer preResolveDay;
	private Integer realResolveDay;
	private Date resolveTime;
	private String attachment;
	
	private Set<BugTrace> bugTraces = new HashSet<BugTrace>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Employee getQa() {
		return qa;
	}
	public void setQa(Employee qa) {
		this.qa = qa;
	}
	public Employee getDev() {
		return dev;
	}
	public void setDev(Employee dev) {
		this.dev = dev;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getPreResolveDay() {
		return preResolveDay;
	}
	public void setPreResolveDay(Integer preResolveDay) {
		this.preResolveDay = preResolveDay;
	}
	public Integer getRealResolveDay() {
		return realResolveDay;
	}
	public void setRealResolveDay(Integer realResolveDay) {
		this.realResolveDay = realResolveDay;
	}
	public Date getResolveTime() {
		return resolveTime;
	}
	public void setResolveTime(Date resolveTime) {
		this.resolveTime = resolveTime;
	}
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	public Set<BugTrace> getBugTraces() {
		return bugTraces;
	}
	public void setBugTraces(Set<BugTrace> bugTraces) {
		this.bugTraces = bugTraces;
	}

}
