package com.rdms.comm.action.model;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.rdms.base.action.model.BaseModel;

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
	
//	@Override
//	public String[] getModelFields() {
//		String[] modelFields = new String[]{
//			ENTITY_ID, ENTITY_NAME, ENTITY_DEPT_CODE, ENTITY_MEM_TOTAL
//		};
//		return modelFields;
//	}
	
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
