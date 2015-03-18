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
import com.rdms.comm.action.model.PjStageModel;
import com.rdms.comm.domain.PjStage;
import com.rdms.comm.domain.Project;
import com.rdms.comm.service.PjStageService;
import com.rdms.comm.service.ProjectService;

@Controller("pjStageAction")
@Scope("prototype")
public class PjStageAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	@Resource(name="pjStageService")
	private PjStageService pjStageService;
	@Resource(name="projectService")
	private ProjectService projectService;

	@Override
	public String insert() {
		AppModel appModel = this.getAppModel();
		PjStageModel pjStageModel = (PjStageModel) appModel.attrToBean(PjStageModel.class);
		AppVO appVO = this.getAppVO();
		try {
			PjStage entity = (PjStage) this.toEntity(pjStageModel, null);
			this.pjStageService.save(entity);
			pjStageModel = PjStageModel.toModel(entity);
			appVO.setSuccess(true);
			appVO.setMsg("添加成功");
			appVO.setRow(pjStageModel);
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
		PjStageModel pjStageModel = (PjStageModel) appModel.attrToBean(PjStageModel.class);
		AppVO appVO = this.getAppVO();
		try {
			PjStage oldEntity = this.pjStageService.findById(pjStageModel.getId());
			PjStage newEntity = (PjStage) this.toEntity(pjStageModel, oldEntity);
			this.pjStageService.update(newEntity);
			appVO.setSuccess(true);
			appVO.setMsg("更新成功");
			pjStageModel = PjStageModel.toModel(newEntity);
			appVO.setRow(pjStageModel);
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
		PjStageModel pjStageModel = (PjStageModel) appModel.attrToBean(PjStageModel.class);
		AppVO appVO = this.getAppVO();
		try {
			this.pjStageService.delete(pjStageModel.getId());
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
			this.pjStageService.deleteByIds(ids);
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
		PjStageModel pjStageModel = (PjStageModel) appModel.attrToBean(PjStageModel.class);
		AppVO appVO = this.getAppVO();
		try {
			List<PjStage> beanList = this.pjStageService.query(pjStageModel, orderBy, order);
			appVO.setSuccess(true);
			appVO.setMsg("查询成功");
			for(PjStage bean : beanList) {
//				pjStageModel = (PjStageModel) this.toModel(bean);
				pjStageModel = PjStageModel.toModel(bean);
				appVO.addRow(pjStageModel);
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
		PjStageModel pjStageModel = (PjStageModel) appModel.attrToBean(PjStageModel.class);
		PageBean<PjStage> pageBean = null;
		AppVO appVO = this.getAppVO();
		try {
			pageBean = this.pjStageService.queryByPage(offset, limit, pjStageModel, orderBy, order);
			List<PjStage> beanList = pageBean.getBeanList();
			for(PjStage bean : beanList) {
//				pjStageModel = (PjStageModel) this.toModel(bean);
				pjStageModel = PjStageModel.toModel(bean);
				appVO.addRow(pjStageModel);
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
		PjStageModel pjStageModel = (PjStageModel) model;
		PjStage pjStageEntity = null;
		if(entity == null) {
			pjStageEntity = new PjStage();
		} else {
			pjStageEntity = (PjStage) entity;
		}
//		pjStageEntity.setId(pjStageModel.getId());
		Project project = this.projectService.findById(pjStageModel.getPid());
		pjStageEntity.setProject(project);
		pjStageEntity.setName(pjStageModel.getName());
		pjStageEntity.setCreateTime(new Date());
		pjStageEntity.setStartTime(pjStageModel.getStartTime());
		pjStageEntity.setEndTime(pjStageModel.getEndTime());
		pjStageEntity.setPreCompleteDay(pjStageModel.getPreCompleteDay());
		pjStageEntity.setRealCompleteDay(pjStageModel.getRealCompleteDay());
		pjStageEntity.setStatus(pjStageModel.getStatus());
		pjStageEntity.setProgress(pjStageModel.getProgress());
		return pjStageEntity;
	}

//	@Override
//	public Object toModel(Object entity) throws Exception {
//		PjStage pjStage = (PjStage) entity;
//		PjStageModel model = new PjStageModel();
//		model.setId(pjStage.getId());
//		model.setPid(pjStage.getProject().getId());
//		model.setName(pjStage.getName());
//		model.setCreateTime(pjStage.getCreateTime());
//		model.setStartTime(pjStage.getStartTime());
//		model.setEndTime(pjStage.getEndTime());
//		model.setPreCompleteDay(pjStage.getPreCompleteDay());
//		model.setRealCompleteDay(pjStage.getRealCompleteDay());
//		model.setStatus(pjStage.getStatus());
//		model.setProgress(pjStage.getProgress());
//		return model;
//	}

}
