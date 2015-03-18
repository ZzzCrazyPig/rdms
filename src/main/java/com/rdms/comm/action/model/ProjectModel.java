package com.rdms.comm.action.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.json.annotations.JSON;

import com.rdms.base.action.model.BaseModel;
import com.rdms.comm.domain.Project;

public class ProjectModel extends BaseModel {
	
	private String id;
	private String name;
	private String detail;
	private Date createTime;
	private Date startTime;
	private Date endTime;
	private Integer preCompleteDay;
	private Integer realCompleteDay;
	private Double progress;
	private String status;
	
	private String createUser;
	private String pjGrId;
	private String pjGrName;
	
	private EmployeeModel emp;
	private PjGroupModel pjGroup;
	
	@SuppressWarnings("rawtypes")
	public static Map<String, Class> getClassMap() {
		Map<String, Class> classMap = new HashMap<String, Class>();
		classMap.put("emp", EmployeeModel.class);
		classMap.put("pjGroup", PjGroupModel.class);
		return classMap;
	}
	
	public static ProjectModel toModel(Project entity) {
		Project pj = entity;
		ProjectModel model = new ProjectModel();
		model.setId(pj.getId());
		model.setName(pj.getName());
		model.setDetail(pj.getDetail());
		model.setCreateTime(pj.getCreateTime());
		model.setStartTime(pj.getStartTime());
		model.setEndTime(pj.getEndTime());
		model.setPreCompleteDay(pj.getPreCompleteDay());
		model.setRealCompleteDay(pj.getRealCompleteDay());
		model.setProgress(pj.getProgress());
		model.setStatus(pj.getStatus());
		model.setCreateUser(pj.getCreateUser().getId());
		model.setPjGrId(pj.getPjGroup().getId());
		model.setPjGrName(pj.getPjGroup().getName());
		model.setEmp(EmployeeModel.toModel(pj.getCreateUser()));
//		model.setPjGroup(PjGroupModel.toModel(pj.getPjGroup(), false));
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

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
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

	public Double getProgress() {
		return progress;
	}

	public void setProgress(Double progress) {
		this.progress = progress;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getPjGrId() {
		return pjGrId;
	}

	public void setPjGrId(String pjGrId) {
		this.pjGrId = pjGrId;
	}

	public EmployeeModel getEmp() {
		return emp;
	}

	public void setEmp(EmployeeModel emp) {
		this.emp = emp;
	}

	public PjGroupModel getPjGroup() {
		return pjGroup;
	}

	public void setPjGroup(PjGroupModel pjGroup) {
		this.pjGroup = pjGroup;
	}

	public String getPjGrName() {
		return pjGrName;
	}

	public void setPjGrName(String pjGrName) {
		this.pjGrName = pjGrName;
	}
	
}
