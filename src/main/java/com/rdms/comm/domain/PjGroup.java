package com.rdms.comm.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class PjGroup implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private String detail;
	private Employee createUser;
	private Date createTime;
	
	private Set<PjGrMember> pjGrMems = new HashSet<PjGrMember>();
	
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
	public Set<PjGrMember> getPjGrMems() {
		return pjGrMems;
	}
	public void setPjGrMems(Set<PjGrMember> pjGrMems) {
		this.pjGrMems = pjGrMems;
	}

}
