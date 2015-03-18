package com.rdms.comm.domain;

import java.io.Serializable;
import java.util.Date;

public class PjStage implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private Project project;
	private String name;
	private Date createTime;
	private Date startTime;
	private Date endTime;
	private Integer preCompleteDay;
	private Integer realCompleteDay;
	private String status;
	private Double progress;
	
	public PjStage() {}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Integer getPreCompleteDay() {
		return preCompleteDay;
	}
	public void setPreCompleteDay(Integer preCompleteDay) {
		this.preCompleteDay = preCompleteDay;
	}
	public Integer getRealCompleteDay() {
		return realCompleteDay;
	}
	public void setRealCompleteDay(Integer realCompleteDay) {
		this.realCompleteDay = realCompleteDay;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Double getProgress() {
		return progress;
	}
	public void setProgress(Double progress) {
		this.progress = progress;
	}
	
}
