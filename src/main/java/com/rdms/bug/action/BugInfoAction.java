package com.rdms.bug.action;

import java.util.Date;
import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.rdms.base.action.GeneralAction;
import com.rdms.bug.action.model.BugInfoModel;
import com.rdms.bug.domain.BugInfo;
import com.rdms.bug.service.BugInfoService;
import com.rdms.comm.domain.Employee;
import com.rdms.comm.domain.Project;
import com.rdms.comm.service.EmployeeService;
import com.rdms.comm.service.ProjectService;

@Controller("bugInfoAction")
@Scope("prototype")
public class BugInfoAction extends GeneralAction<BugInfo, BugInfoService, BugInfoModel> {

	private static final long serialVersionUID = 1L;
	private BugInfoService bugInfoService;
	@Resource(name="projectService")
	private ProjectService projectService;
	@Resource(name="employeeService")
	private EmployeeService empService;
	
	@Resource(name="bugInfoService")
	public void setBugInfoService(BugInfoService bugInfoService) {
		super.setBaseService(bugInfoService);
		this.bugInfoService = bugInfoService;
	}

	public BugInfoService getBugInfoService() {
		return bugInfoService;
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
	protected BugInfo toEntity(BugInfoModel model, BugInfo entity) throws Exception {
		BugInfoModel biModel = (BugInfoModel) model;
		BugInfo biEntity = null;
		if(entity == null) {
			biEntity = new BugInfo();
		} else {
			biEntity = (BugInfo) entity;
		}
		biEntity.setName(biModel.getName());
		String pid = biModel.getPid();
		Project pj = this.projectService.findById(pid);
		biEntity.setProject(pj);
		biEntity.setTitle(biModel.getTitle());
		biEntity.setDetail(biModel.getDetail());
		String qaId = biModel.getQaId();
		String devId = biModel.getDevId();
		Employee qa = this.empService.findById(qaId);
		Employee dev = this.empService.findById(devId);
		biEntity.setQa(qa);
		biEntity.setDev(dev);
		biEntity.setLevel(biModel.getLevel());
		biEntity.setStatus(biModel.getStatus());
		biEntity.setCreateTime(new Date());
		biEntity.setPreResolveDay(biModel.getPreResolveDay());
		biEntity.setRealResolveDay(biModel.getRealResolveDay());
		biEntity.setResolveTime(biModel.getResolveTime());
		biEntity.setAttachment(biModel.getAttachment());
		return biEntity;
	}

}
