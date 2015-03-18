package com.rdms.comm.action.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.rdms.base.action.model.BaseModel;

@Component("positionModel")
@Scope("prototype")
public class PositionModel extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private String detail;
	
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

}
