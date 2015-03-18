package com.rdms.bug.action.model;

import java.util.Date;

import com.rdms.base.action.model.BaseModel;
import com.rdms.bug.domain.BugInfo;
import com.rdms.comm.action.model.EmployeeModel;
import com.rdms.comm.action.model.ProjectModel;

public class BugInfoModel extends BaseModel {
	
	private String id;
	private String name;
	private String pid;
	private String title;
	private String detail;
	private String qaId;
	private String devId;
	private String level;
	private String status;
	private Date createTime;
	private Integer preResolveDay;
	private Integer realResolveDay;
	private Date resolveTime;
	private String attachment;
	
	private ProjectModel project;
	private EmployeeModel qa;
	private EmployeeModel dev;

//	@Override
//	public String[] getModelFields() {
//		return null;
//	}
	
	public BugInfoModel() {}
	
	public static BugInfoModel toModel(BugInfo entity) {
		BugInfoModel model = new BugInfoModel();
		model.setId(entity.getId());
		model.setName(entity.getName());
		model.setPid(entity.getProject().getId());
		model.setTitle(entity.getTitle());
		model.setDetail(entity.getDetail());
		model.setQaId(entity.getQa().getId());
		model.setDevId(entity.getDev().getId());
		model.setLevel(entity.getLevel());
		model.setStatus(entity.getStatus());
		model.setCreateTime(entity.getCreateTime());
		model.setPreResolveDay(entity.getPreResolveDay());
		model.setRealResolveDay(entity.getRealResolveDay());
		model.setResolveTime(entity.getResolveTime());
		model.setAttachment(entity.getAttachment());
		
		model.setProject(ProjectModel.toModel(entity.getProject()));
		model.setQa(EmployeeModel.toModel(entity.getQa()));
		model.setDev(EmployeeModel.toModel(entity.getDev()));
		
		return model;
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

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getQaId() {
		return qaId;
	}

	public void setQaId(String qaId) {
		this.qaId = qaId;
	}

	public String getDevId() {
		return devId;
	}

	public void setDevId(String devId) {
		this.devId = devId;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getPreResolveDay() {
		return preResolveDay;
	}

	public void setPreResolveDay(Integer preResolveDay) {
		this.preResolveDay = preResolveDay;
	}

	public Integer getRealResolveDay() {
		return realResolveDay;
	}

	public void setRealResolveDay(Integer realResolveDay) {
		this.realResolveDay = realResolveDay;
	}

	public Date getResolveTime() {
		return resolveTime;
	}

	public void setResolveTime(Date resolveTime) {
		this.resolveTime = resolveTime;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public EmployeeModel getQa() {
		return qa;
	}

	public void setQa(EmployeeModel qa) {
		this.qa = qa;
	}

	public EmployeeModel getDev() {
		return dev;
	}

	public void setDev(EmployeeModel dev) {
		this.dev = dev;
	}

	public ProjectModel getProject() {
		return project;
	}

	public void setProject(ProjectModel project) {
		this.project = project;
	}
	
}
