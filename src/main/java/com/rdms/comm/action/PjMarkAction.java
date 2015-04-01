package com.rdms.comm.action;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.rdms.base.action.GeneralAction;
import com.rdms.comm.action.model.PjMarkModel;
import com.rdms.comm.domain.PjMark;
import com.rdms.comm.domain.Project;
import com.rdms.comm.service.PjMarkService;
import com.rdms.comm.service.ProjectService;

@Controller("pjMarkAction")
@Scope("prototype")
public class PjMarkAction extends GeneralAction<PjMark, PjMarkService, PjMarkModel> {

	private static final long serialVersionUID = 1L;
	private PjMarkService pjMarkService;
	@Resource(name="projectService")
	private ProjectService projectService;
	
	@Resource(name="pjMarkService")
	public void setPjMarkService(PjMarkService pjMarkService) {
		super.setBaseService(pjMarkService);
		this.pjMarkService = pjMarkService;
	}
	
	public PjMarkService getPjMarkService() {
		return pjMarkService;
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
	protected PjMark toEntity(PjMarkModel model, PjMark entity) {
		PjMarkModel pjMarkModel = (PjMarkModel) model;
		PjMark pjMarkEntity = null;
		if(entity == null) {
			pjMarkEntity = new PjMark();
		} else {
			pjMarkEntity = (PjMark) entity;
		}
		pjMarkEntity.setContent(pjMarkModel.getContent());
		pjMarkEntity.setAttachment(pjMarkModel.getAttachment());
		Project project = null;
		try {
			project = this.projectService.findById(pjMarkModel.getPid());
			pjMarkEntity.setProject(project);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
