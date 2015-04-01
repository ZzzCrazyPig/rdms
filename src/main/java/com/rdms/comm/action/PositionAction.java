package com.rdms.comm.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.rdms.base.action.GeneralAction;
import com.rdms.comm.action.model.PositionModel;
import com.rdms.comm.domain.Position;
import com.rdms.comm.service.PositionService;

@Controller("positionAction")
@Scope("prototype")
public class PositionAction extends GeneralAction<Position, PositionService, PositionModel> {

	private static final long serialVersionUID = 1L;
	private PositionService positionService;
	
	@Resource(name="positionService")
	public void setPositionService(PositionService positionService) {
		super.setBaseService(positionService);
		this.positionService = positionService;
	}
	
	public PositionService getPositionService() {
		return positionService;
	}
	
	// 添加职位
	@Override
	public String insert() {
		return super.insert();
	}

	// 更新职位信息
	@Override
	public String update() {
		return super.update();
	}

	// 删除一条
	@Override
	public String delete() {
		return super.delete();
	}

	// 批量删除
	@Override
	public String multiDelete() {
		return super.multiDelete();
	}

	// 查询全部
	@Override
	public String query() {
		return super.query();
	}

	// 分页查询
	@Override
	public String queryByPage() {
		return super.queryByPage();
	}

	@Override
	protected Position toEntity(PositionModel model, Position entity) {
		PositionModel posModel = (PositionModel) model;
		Position posEntity = null;
		if(entity == null) {
			posEntity = new Position();
		} else {
			posEntity = (Position) entity;
		}
		posEntity.setName(posModel.getName());
		posEntity.setDetail(posModel.getDetail());
		return posEntity;
	}

//	@Override
//	public Object toModel(Object entity) throws Exception {
//		Position pos = (Position) entity;
//		PositionModel posModel = new PositionModel();
//		posModel.setId(pos.getId());
//		posModel.setName(pos.getName());
//		posModel.setDetail(pos.getDetail());
//		return posModel;
//	}

}
