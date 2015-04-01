package com.rdms.comm.action.model;

import java.util.Date;
import java.util.Map;

import org.apache.struts2.json.annotations.JSON;

import com.rdms.base.action.model.BaseModel;
import com.rdms.comm.domain.PjStage;

public class PjStageModel extends BaseModel {
	
	private String id;
	private String pid;
	private String name;
	private Date createTime;
	private Date startTime;
	private Date endTime;
	private Integer preCompleteDay;
	private Integer realCompleteDay;
	private String status;
	private Double progress;
	
	@Override
	@JSON(serialize = false)
	public Map<String, Class<?>> getComplexObjClassMapOfModel() {
		return null;
	}
	
	@Override
	public BaseModel toModel(Object e) {
		if(e == null) return new PjStageModel();
		PjStage entity = (PjStage) e;
		return toModel(entity);
	}
	
	public static PjStageModel toModel(PjStage entity) {
		PjStage pjStage = entity;
		PjStageModel model = new PjStageModel();
		model.setId(pjStage.getId());
		model.setPid(pjStage.getProject().getId());
		model.setName(pjStage.getName());
		model.setCreateTime(pjStage.getCreateTime());
		model.setStartTime(pjStage.getStartTime());
		model.setEndTime(pjStage.getEndTime());
		model.setPreCompleteDay(pjStage.getPreCompleteDay());
		model.setRealCompleteDay(pjStage.getRealCompleteDay());
		model.setStatus(pjStage.getStatus());
		model.setProgress(pjStage.getProgress());
		return model;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getPreCompleteDay() {
		return preCompleteDay;
	}

	public void setPreCompleteDay(Integer preCompleteDay) {
		this.preCompleteDay = preCompleteDay;
	}

	public Integer getRealCompleteDay() {
		return realCompleteDay;
	}

	public void setRealCompleteDay(Integer realCompleteDay) {
		this.realCompleteDay = realCompleteDay;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getProgress() {
		return progress;
	}

	public void setProgress(Double progress) {
		this.progress = progress;
	}

}
