package com.rdms.comm.domain;

import java.io.Serializable;

import com.rdms.comm.action.model.EmployeeModel;
import com.rdms.comm.action.model.PjGrMemberModel;

public class PjGrMember implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private PjGroup pjGroup;
	private Employee emp;
	private String role;
	
	public PjGrMember() {}
	
	public static PjGrMemberModel toModel(PjGrMember entity) {
		PjGrMember pjGrMem = entity;
		PjGrMemberModel model = new PjGrMemberModel();
		model.setId(pjGrMem.getId());
		model.setEmp(EmployeeModel.toModel(pjGrMem.getEmp()));
		model.setEid(pjGrMem.getEmp().getId());
		model.setPjGrId(pjGrMem.getPjGroup().getId());
		model.setRole(pjGrMem.getRole());
		return model;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public PjGroup getPjGroup() {
		return pjGroup;
	}
	public void setPjGroup(PjGroup pjGroup) {
		this.pjGroup = pjGroup;
	}
	public Employee getEmp() {
		return emp;
	}
	public void setEmp(Employee emp) {
		this.emp = emp;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	

}
