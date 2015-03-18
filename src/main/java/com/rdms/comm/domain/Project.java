package com.rdms.comm.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.rdms.svn.domain.SvnProject;

public class Project implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private String detail;
	private Employee createUser;
	private Date createTime;
	private Date startTime;
	private Date endTime;
	private Integer preCompleteDay;
	private Integer realCompleteDay;
	private PjGroup pjGroup;
	private Double progress;
	private String status;
	
	private Set<SvnProject> svnPjs = new HashSet<SvnProject>(0);
	
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
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Employee getCreateUser() {
		return createUser;
	}
	public void setCreateUser(Employee createUser) {
		this.createUser = createUser;
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
	public PjGroup getPjGroup() {
		return pjGroup;
	}
	public void setPjGroup(PjGroup pjGroup) {
		this.pjGroup = pjGroup;
	}
	public Double getProgress() {
		return progress;
	}
	public void setProgress(Double progress) {
		this.progress = progress;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Set<SvnProject> getSvnPjs() {
		return svnPjs;
	}
	public void setSvnPjs(Set<SvnProject> svnPjs) {
		this.svnPjs = svnPjs;
	}
	
}
