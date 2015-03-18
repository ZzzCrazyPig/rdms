package com.rdms.comm.domain;

import java.io.Serializable;

import com.rdms.comm.action.model.PositionModel;

public class Position implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private String detail;
	
	public Position() {}
	
	public static PositionModel toModel(Position entity) {
		Position pos = (Position) entity;
		PositionModel posModel = new PositionModel();
		posModel.setId(pos.getId());
		posModel.setName(pos.getName());
		posModel.setDetail(pos.getDetail());
		return posModel;
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
	

}
