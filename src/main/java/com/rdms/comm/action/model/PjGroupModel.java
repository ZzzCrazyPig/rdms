package com.rdms.comm.action.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

import com.rdms.base.action.model.BaseModel;
import com.rdms.comm.domain.PjGrMember;
import com.rdms.comm.domain.PjGroup;

public class PjGroupModel extends BaseModel {
	
	private String id;
	private String name;
	private String detail;
	private String createUser;
	private Date createTime;
	
	private EmployeeModel emp;
	private Set<PjGrMemberModel> pjGrMems = new HashSet<PjGrMemberModel>();
	
	@Override
	@JSON(serialize = false)
	public Map<String, Class<?>> getComplexObjClassMapOfModel() {
		return null;
	}
	
	@Override
	public BaseModel toModel(Object e) {
		if(e == null) return new PjGroupModel();
		PjGroup entity = (PjGroup) e;
		return toModel(entity);
	}
	
	public static PjGroupModel toModel(PjGroup entity) {
		PjGroup pjGroup = entity;
		PjGroupModel model = new PjGroupModel();
		model.setId(pjGroup.getId());
		model.setName(pjGroup.getName());
		model.setCreateUser(pjGroup.getCreateUser().getId());
		model.setCreateTime(pjGroup.getCreateTime());
		model.setDetail(pjGroup.getDetail());
		model.setEmp(EmployeeModel.toModel(pjGroup.getCreateUser()));
		return model;
	}
	
	public static PjGroupModel toModel(PjGroup pjGroup, boolean fetch) {
		PjGroupModel model = new PjGroupModel();
		model.setId(pjGroup.getId());
		model.setName(pjGroup.getName());
		model.setCreateUser(pjGroup.getCreateUser().getId());
		model.setCreateTime(pjGroup.getCreateTime());
		model.setDetail(pjGroup.getDetail());
		model.setEmp(EmployeeModel.toModel(pjGroup.getCreateUser()));
		if(fetch && pjGroup.getPjGrMems().size() > 0) {
			Set<PjGrMember> pjGrMems = pjGroup.getPjGrMems();
			for(PjGrMember pjGrMem : pjGrMems) {
				PjGrMemberModel pjGrMemModel = PjGrMemberModel.toModel(pjGrMem);
				model.getPjGrMems().add(pjGrMemModel);
			}
		}
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

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Set<PjGrMemberModel> getPjGrMems() {
		return pjGrMems;
	}

	public void setPjGrMems(Set<PjGrMemberModel> pjGrMems) {
		this.pjGrMems = pjGrMems;
	}

	public EmployeeModel getEmp() {
		return emp;
	}

	public void setEmp(EmployeeModel emp) {
		this.emp = emp;
	}
	
}
