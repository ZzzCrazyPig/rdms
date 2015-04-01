package com.rdms.auth.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.rdms.comm.domain.Employee;

public class Role implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private String detail;
	
	private Set<Action> bans = new HashSet<Action>();
	private Set<Employee> emps = new HashSet<Employee>();
	
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
	public Set<Action> getBans() {
		return bans;
	}
	public void setBans(Set<Action> bans) {
		this.bans = bans;
	}
	public Set<Employee> getEmps() {
		return emps;
	}
	public void setEmps(Set<Employee> emps) {
		this.emps = emps;
	}
}
