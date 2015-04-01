package com.rdms.comm.action;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.rdms.base.action.GeneralAction;
import com.rdms.comm.action.model.PjStageModel;
import com.rdms.comm.domain.PjStage;
import com.rdms.comm.domain.Project;
import com.rdms.comm.service.PjStageService;
import com.rdms.comm.service.ProjectService;

@Controller("pjStageAction")
@Scope("prototype")
public class PjStageAction extends GeneralAction<PjStage, PjStageService, PjStageModel> {

	private static final long serialVersionUID = 1L;
	private PjStageService pjStageService;
	@Resource(name="projectService")
	private ProjectService projectService;
	
	@Resource(name="pjStageService")
	public void setPjStageService(PjStageService pjStageService) {
		super.setBaseService(pjStageService);
		this.pjStageService = pjStageService;
	}
	
	public PjStageService getPjStageService() {
		return pjStageService;
	}

	@Override
	public String insert() {
		return super.insert();
	}

	@Override
	public String update() {
		return super.update();
	}

	@Override
	public String delete() {
		return super.delete();
	}

	@Override
	public String multiDelete() {
		return super.multiDelete();
	}

	@Override
	public String query() {
		return super.query();
	}

	@Override
	public String queryByPage() {
		return super.queryByPage();
	}

	@Override
	protected PjStage toEntity(PjStageModel model, PjStage entity) {
		PjStageModel pjStageModel = (PjStageModel) model;
		PjStage pjStageEntity = null;
		if(entity == null) {
			pjStageEntity = new PjStage();
		} else {
			pjStageEntity = (PjStage) entity;
		}
		Project project = null;
		try {
			project = this.projectService.findById(pjStageModel.getPid());
			pjStageEntity.setProject(project);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
