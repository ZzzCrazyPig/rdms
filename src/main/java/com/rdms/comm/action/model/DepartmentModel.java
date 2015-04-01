package com.rdms.comm.action.model;

import java.io.Serializable;
import java.util.Map;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.rdms.base.action.model.BaseModel;
import com.rdms.comm.domain.Department;

@Component("departmentModel")
@Scope("prototype")
public class DepartmentModel extends BaseModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private String deptCode;
	private int memTotal;
	
	@Override
	@JSON(serialize = false)
	public Map<String, Class<?>> getComplexObjClassMapOfModel() {
		return null;
	}
	
	@Override
	public BaseModel toModel(Object e) {
		if(e == null) return new DepartmentModel();
		Department entity = (Department) e;
		return toModel(entity);
	}
	
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
