package com.rdms.comm.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.rdms.base.action.GeneralAction;
import com.rdms.base.vo.AppVO;
import com.rdms.comm.action.model.WorkLogModel;
import com.rdms.comm.domain.Employee;
import com.rdms.comm.domain.WorkLog;
import com.rdms.comm.service.EmployeeService;
import com.rdms.comm.service.WorkLogService;

@Controller("workLogAction")
@Scope("prototype")
public class WorkLogAction extends GeneralAction<WorkLog, WorkLogService, WorkLogModel> {

	private static final long serialVersionUID = 1L;
	private WorkLogService workLogService;
	@Resource(name="employeeService")
	private EmployeeService empService;
	
	@Resource(name="workLogService")
	public void setWorkLogService(WorkLogService workLogService) {
		super.setBaseService(workLogService);
		this.workLogService = workLogService;
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
	
	public String queryOneWeek() {
		String eid = ServletActionContext.getRequest().getParameter("eid");
		if(eid == null) {
			// 查询的是本人work log
			Employee emp = (Employee) ActionContext.getContext().getSession().get("emp");
			eid = emp.getId();
		}
		String startDateUTC = ServletActionContext.getRequest().getParameter("startDate");
		String endDateUTC = ServletActionContext.getRequest().getParameter("endDate");
		Date startDate = new Date(Long.parseLong(startDateUTC));
		Date endDate = new Date(Long.parseLong(endDateUTC));
		System.out.println("开始时间: " + startDate + " , 结束时间: " + endDate);
		AppVO appVO = this.getAppVO();
		try {
			List<WorkLog> beanList = this.workLogService.queryOneWeek(eid, startDate, endDate);
			for(WorkLog bean : beanList) {
//				WorkLogModel workLogModel = (WorkLogModel) this.toModel(bean);
				WorkLogModel workLogModel = WorkLog.toModel(bean);
				appVO.addRow(workLogModel);
			}
		} catch (Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常,查询失败");
		}
		appVO.setSuccess(true);
		appVO.setMsg("查询成功");
		return SUCCESS;
	}

	@Override
	protected WorkLog toEntity(WorkLogModel model, WorkLog entity) {
		WorkLogModel workLogModel = (WorkLogModel) model;
		WorkLog workLogEntity = null;
		if(entity == null) {
			workLogEntity = new WorkLog();
		} else {
			workLogEntity = (WorkLog) entity;
		}
		Employee emp = null;
		try {
			emp = this.empService.findById(workLogModel.getEid());
			workLogEntity.setEmp(emp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		workLogEntity.setContent(workLogModel.getContent());
		workLogEntity.setWorkTimes(workLogModel.getWorkTimes());
		workLogEntity.setCreateTime(workLogModel.getCreateTime());
		return workLogEntity;
	}

//	@Override
//	public Object toModel(Object entity) throws Exception {
//		WorkLog workLog = (WorkLog) entity;
//		WorkLogModel model = new WorkLogModel();
//		model.setId(workLog.getId());
//		model.setEid(workLog.getEmp().getId());
//		model.setCreateTime(workLog.getCreateTime());
//		model.setCreateTimeUTC(workLog.getCreateTime().getTime());
//		model.setWorkTimes(workLog.getWorkTimes());
//		model.setContent(workLog.getContent());
//		return model;
//	}

}
