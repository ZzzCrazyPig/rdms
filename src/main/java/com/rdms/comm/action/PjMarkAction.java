package com.rdms.comm.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.rdms.base.PageBean;
import com.rdms.base.action.BaseAction;
import com.rdms.base.action.model.AppModel;
import com.rdms.base.vo.AppVO;
import com.rdms.comm.action.model.PjMarkModel;
import com.rdms.comm.domain.PjMark;
import com.rdms.comm.domain.Project;
import com.rdms.comm.service.PjMarkService;
import com.rdms.comm.service.ProjectService;

@Controller("pjMarkAction")
@Scope("prototype")
public class PjMarkAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	@Resource(name="pjMarkService")
	private PjMarkService pjMarkService;
	@Resource(name="projectService")
	private ProjectService projectService;

	@Override
	public String insert() {
		AppModel appModel = this.getAppModel();
		PjMarkModel pjMarkModel = (PjMarkModel) appModel.attrToBean(PjMarkModel.class);
		AppVO appVO = this.getAppVO();
		try {
			PjMark entity = (PjMark) this.toEntity(pjMarkModel, null);
			this.pjMarkService.save(entity);
			pjMarkModel = PjMark.toModel(entity);
			appVO.setSuccess(true);
			appVO.setMsg("添加成功");
			appVO.setRow(pjMarkModel);
		} catch (Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常,添加失败");
			return ERROR;
		}
		return SUCCESS;
	}

	@Override
	public String update() {
		AppModel appModel = this.getAppModel();
		PjMarkModel pjMarkModel = (PjMarkModel) appModel.attrToBean(PjMarkModel.class);
		AppVO appVO = this.getAppVO();
		try {
			PjMark oldEntity = this.pjMarkService.findById(pjMarkModel.getId());
			PjMark newEntity = (PjMark) this.toEntity(pjMarkModel, oldEntity);
			this.pjMarkService.update(newEntity);
			appVO.setSuccess(true);
			appVO.setMsg("更新成功");
			pjMarkModel = PjMark.toModel(newEntity);
			appVO.setRow(newEntity);
		} catch(Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常,更新失败");
			return ERROR;
		}
		return SUCCESS;
	}

	@Override
	public String delete() {
		AppModel appModel = this.getAppModel();
		PjMarkModel pjMarkModel = (PjMarkModel) appModel.attrToBean(PjMarkModel.class);
		AppVO appVO = this.getAppVO();
		try {
			this.pjMarkService.delete(pjMarkModel.getId());
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

	@Override
	public String multiDelete() {
		AppModel appModel = this.getAppModel();
		String attr = appModel.getAttr();
		String[] ids = attr.split(",");
		AppVO appVO = this.getAppVO();
		try {
			this.pjMarkService.deleteByIds(ids);
			appVO.setSuccess(true);
			appVO.setMsg("成功删除" + ids.length + "条数据");
			appVO.setRow(attr);
		} catch (Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常");
			return ERROR;
		}
		return SUCCESS;
	}

	@Override
	public String query() {
		AppModel appModel = this.getAppModel();
		String orderBy = appModel.getSort();
		String order = appModel.getOrder();
		PjMarkModel pjMarkModel = (PjMarkModel) appModel.attrToBean(PjMarkModel.class);
		AppVO appVO = this.getAppVO();
		try {
			List<PjMark> beanList = this.pjMarkService.query(pjMarkModel, orderBy, order);
			appVO.setSuccess(true);
			appVO.setMsg("查询成功");
			for(PjMark bean : beanList) {
//				pjMarkModel = (PjMarkModel) this.toModel(bean);
				pjMarkModel = PjMark.toModel(bean);
				appVO.addRow(pjMarkModel);
			}
		} catch (Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常,查询失败");
			return ERROR;
		}
		return SUCCESS;
	}

	@Override
	public String queryByPage() {
		AppModel appModel = this.getAppModel();
		int offset = appModel.getOffset();
		int limit = appModel.getLimit();
		String orderBy = appModel.getSort();
		String order = appModel.getOrder();
		PjMarkModel pjMarkModel = (PjMarkModel) appModel.attrToBean(PjMarkModel.class);
		PageBean<PjMark> pageBean = null;
		AppVO appVO = this.getAppVO();
		try {
			pageBean = this.pjMarkService.queryByPage(offset, limit, pjMarkModel, orderBy, order);
			List<PjMark> beanList = pageBean.getBeanList();
			for(PjMark bean : beanList) {
//				pjMarkModel = (PjMarkModel) this.toModel(bean);
				pjMarkModel = PjMark.toModel(bean);
				appVO.addRow(pjMarkModel);
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
	public Object toEntity(Object model, Object entity) throws Exception {
		PjMarkModel pjMarkModel = (PjMarkModel) model;
		PjMark pjMarkEntity = null;
		if(entity == null) {
			pjMarkEntity = new PjMark();
		} else {
			pjMarkEntity = (PjMark) entity;
		}
//		pjMarkEntity.setId(pjMarkModel.getId());
		pjMarkEntity.setContent(pjMarkModel.getContent());
		pjMarkEntity.setAttachment(pjMarkModel.getAttachment());
		Project project = this.projectService.findById(pjMarkModel.getPid());
		pjMarkEntity.setProject(project);
		pjMarkEntity.setCreateTime(new Date());
		return pjMarkEntity;
	}

//	@Override
//	public Object toModel(Object entity) throws Exception {
//		PjMark pjMark = (PjMark) entity;
//		PjMarkModel model = new PjMarkModel();
//		model.setId(pjMark.getId());
//		model.setContent(pjMark.getContent());
//		model.setAttachment(pjMark.getAttachment());
//		model.setPid(pjMark.getProject().getId());
//		model.setCreateTime(pjMark.getCreateTime());
//		return model;
//	}

}
