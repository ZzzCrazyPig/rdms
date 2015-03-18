package com.rdms.comm.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.rdms.base.PageBean;
import com.rdms.base.action.BaseAction;
import com.rdms.base.action.model.AppModel;
import com.rdms.base.vo.AppVO;
import com.rdms.comm.action.model.PositionModel;
import com.rdms.comm.domain.Position;
import com.rdms.comm.service.PositionService;

@Controller("positionAction")
@Scope("prototype")
public class PositionAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	@Resource(name="positionService")
	private PositionService positionService;
	
	// 添加职位
	@Override
	public String insert() {
		AppModel appModel = this.getAppModel();
		PositionModel posModel = (PositionModel)appModel.attrToBean(PositionModel.class);
		String name = posModel.getName();
		AppVO appVO = this.getAppVO();
		try {
			if(this.positionService.isAvailable(name)) {
				Position entity = (Position) this.toEntity(posModel, null);
				this.positionService.save(entity);
//				posModel = (PositionModel) this.toModel(entity);
				posModel = Position.toModel(entity);
				appVO.setSuccess(true);
				appVO.setMsg("添加成功");
				appVO.setRow(posModel);
			} else {
				appVO.setSuccess(false);
				appVO.setMsg("职位名已存在!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常");
			return ERROR;
		}
		return SUCCESS;
	}

	// 更新职位信息
	@Override
	public String update() {
		AppModel appModel = this.getAppModel();
		PositionModel posModel = (PositionModel) appModel.attrToBean(PositionModel.class);
		AppVO appVO = this.getAppVO();
		try {
			Position entity = this.positionService.findById(posModel.getId());
			entity = (Position) this.toEntity(posModel, entity);
//			entity.setName(posModel.getName());
//			entity.setDetail(posModel.getDetail());
			this.positionService.update(entity);
//			posModel = (PositionModel) this.toModel(entity);
			posModel = Position.toModel(entity);
			appVO.setSuccess(true);
			appVO.setMsg("更新成功");
			appVO.setRow(posModel);
		} catch(Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常");
			return ERROR;
		}
		return SUCCESS;
	}

	// 删除一条
	@Override
	public String delete() {
		AppModel appModel = this.getAppModel();
		PositionModel posModel = (PositionModel) appModel.attrToBean(PositionModel.class);
		AppVO appVO = this.getAppVO();
		try {
			this.positionService.delete(posModel.getId());
			appVO.setSuccess(true);
			appVO.setMsg("删除成功");
		} catch(Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常");
			return ERROR;
		}
		return SUCCESS;
	}

	// 批量删除
	@Override
	public String multiDelete() {
		AppModel appModel = this.getAppModel();
		String attr = appModel.getAttr();
		String[] ids = attr.split(",");
		AppVO appVO = this.getAppVO();
		try {
			this.positionService.deleteByIds(ids);
			appVO.setSuccess(true);
			appVO.setMsg("成功删除" + ids.length + "条数据");
			appVO.setRow(attr);
		} catch(Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常");
			return ERROR;
		}
		return SUCCESS;
	}

	// 查询全部
	@Override
	public String query() {
		AppVO appVO = this.getAppVO();
		try {
			List<Position> beanList = this.positionService.findAll();
			appVO.setTotal(beanList.size());
			for(Position bean : beanList) {
				PositionModel posModel = Position.toModel(bean);
				appVO.addRow(posModel);
			}
			appVO.setSuccess(true);
			appVO.setMsg("查询成功");
		} catch(Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常");
			return ERROR;
		}
		return SUCCESS;
	}

	// 分页查询
	@Override
	public String queryByPage() {
		AppModel appModel = this.getAppModel();
		int offset = appModel.getOffset();
		int limit = appModel.getLimit();
		String orderBy = appModel.getSort();
		String order = appModel.getOrder();
		PositionModel posModel = (PositionModel) appModel.attrToBean(PositionModel.class);
		PageBean<Position> pageBean = null;
		AppVO appVO = this.getAppVO();
		try {
			pageBean = this.positionService.queryByPage(offset, limit, posModel, orderBy, order);
			List<Position> beanList = pageBean.getBeanList();
			for(Position bean : beanList) {
//				posModel = (PositionModel) this.toModel(bean);
				posModel = Position.toModel(bean);
				appVO.addRow(posModel);
			}
			appVO.setTotal(pageBean.getTotalCount());
			appVO.setSuccess(true);
			appVO.setMsg("查询成功");
		} catch (Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常,查询失败");
			return ERROR;
		}
		return SUCCESS;
	}

	@Override
	public Object toEntity(Object model, Object entity) {
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
