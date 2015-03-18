package com.rdms.comm.action.model;

import com.rdms.base.action.model.BaseModel;
import com.rdms.comm.domain.PjGrMember;

public class PjGrMemberModel extends BaseModel {
	
	private String id;
	private String pjGrId;
	private String eid;
	private String role;
	
	private PjGroupModel pjGroup;
	private EmployeeModel emp;
	
	/**
	 * 将实体转化为与前台交互的model类
	 * @param pjGrMem
	 * @return
	 */
	public static PjGrMemberModel toModel(PjGrMember pjGrMem) {
		PjGrMemberModel model = new PjGrMemberModel();
		model.setId(pjGrMem.getId());
		model.setEid(pjGrMem.getEmp().getId());
		model.setPjGrId(pjGrMem.getPjGroup().getId());
		model.setRole(pjGrMem.getRole());
		model.setEmp(EmployeeModel.toModel(pjGrMem.getEmp()));
		model.setPjGroup(PjGroupModel.toModel(pjGrMem.getPjGroup(), false));
		return model;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPjGrId() {
		return pjGrId;
	}

	public void setPjGrId(String pjGrId) {
		this.pjGrId = pjGrId;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public EmployeeModel getEmp() {
		return emp;
	}

	public void setEmp(EmployeeModel emp) {
		this.emp = emp;
	}

	public PjGroupModel getPjGroup() {
		return pjGroup;
	}

	public void setPjGroup(PjGroupModel pjGroup) {
		this.pjGroup = pjGroup;
	}

}
