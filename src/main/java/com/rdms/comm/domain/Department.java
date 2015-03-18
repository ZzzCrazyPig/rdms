package com.rdms.comm.domain;

import java.io.Serializable;

import com.rdms.comm.action.model.DepartmentModel;

public class Department implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private String deptCode;
	private int memTotal;
	
	public Department() {}
	
	public static DepartmentModel toModel(Department entity) {
		Department dept = entity;
		DepartmentModel model = new DepartmentModel();
		model.setId(dept.getId());
		model.setName(dept.getName());
		model.setDeptCode(dept.getDeptCode());
		model.setMemTotal(dept.getMemTotal());
		return model;
	}
	
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
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public int getMemTotal() {
		return memTotal;
	}
	public void setMemTotal(int memTotal) {
		this.memTotal = memTotal;
	}

}
