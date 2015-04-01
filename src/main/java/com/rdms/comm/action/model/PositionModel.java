package com.rdms.comm.action.model;

import java.io.Serializable;
import java.util.Map;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.rdms.base.action.model.BaseModel;
import com.rdms.comm.domain.Position;

@Component("positionModel")
@Scope("prototype")
public class PositionModel extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private String detail;
	
	@Override
	@JSON(serialize = false)
	public Map<String, Class<?>> getComplexObjClassMapOfModel() {
		return null;
	}
	
	@Override
	public BaseModel toModel(Object e) {
		if(e == null) return new PositionModel();
		Position entity = (Position) e;
		return toModel(entity);
	}
	
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
