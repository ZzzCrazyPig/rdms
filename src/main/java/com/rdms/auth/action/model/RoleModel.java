package com.rdms.auth.action.model;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

import com.rdms.auth.domain.Action;
import com.rdms.auth.domain.Role;
import com.rdms.base.action.model.BaseModel;
import com.rdms.comm.action.model.EmployeeModel;
import com.rdms.comm.domain.Employee;

public class RoleModel extends BaseModel {
	
	private String id;
	private String name;
	private String detail;
	
	private Set<EmployeeModel> emps = new HashSet<EmployeeModel>();
	private Set<ActionModel> bans = new HashSet<ActionModel>();
	
	public RoleModel() {}
	
	@Override
	@JSON(serialize = false)
	public Map<String, Class<?>> getComplexObjClassMapOfModel() {
		return getClassMap();
	}
	
	public static Map<String, Class<?>> getClassMap() {
		return null;
	}
	
	@Override
	public BaseModel toModel(Object e) {
		if(e == null) return new RoleModel();
		Role entity = (Role) e;
		return toModel(entity);
	}
	
	public static RoleModel toModel(Role entity) {
		RoleModel model = new RoleModel();
		model.setId(entity.getId());
		model.setName(entity.getName());
		model.setDetail(entity.getDetail());
		Set<Action> bans = entity.getBans();
		Set<Employee> emps = entity.getEmps();
		for(Action ban : bans) {
			model.getBans().add(ActionModel.toModel(ban));
		}
		for(Employee emp : emps) {
			model.getEmps().add(EmployeeModel.toModel(emp));
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

	public Set<ActionModel> getBans() {
		return bans;
	}

	public void setBans(Set<ActionModel> bans) {
		this.bans = bans;
	}

	public Set<EmployeeModel> getEmps() {
		return emps;
	}

	public void setEmps(Set<EmployeeModel> emps) {
		this.emps = emps;
	}

}
