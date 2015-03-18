package com.rdms.comm.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.rdms.base.PageBean;
import com.rdms.base.action.BaseAction;
import com.rdms.base.action.model.AppModel;
import com.rdms.base.vo.AppVO;
import com.rdms.comm.action.model.WorkLogModel;
import com.rdms.comm.domain.Employee;
import com.rdms.comm.domain.WorkLog;
import com.rdms.comm.service.EmployeeService;
import com.rdms.comm.service.WorkLogService;

@Controller("workLogAction")
@Scope("prototype")
public class WorkLogAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	@Resource(name="workLogService")
	private WorkLogService workLogService;
	@Resource(name="employeeService")
	private EmployeeService empService;

	@Override
	public String insert() {
		AppModel appModel = this.getAppModel();
		WorkLogModel workLogModel = (WorkLogModel) appModel.attrToBean(WorkLogModel.class);
		AppVO appVO = this.getAppVO();
		try {
			WorkLog entity = (WorkLog) this.toEntity(workLogModel, null);
			this.workLogService.save(entity);
			workLogModel = WorkLog.toModel(entity);
			appVO.setSuccess(true);
			appVO.setMsg("添加成功");
			appVO.setRow(workLogModel);
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
		WorkLogModel workLogModel = (WorkLogModel) appModel.attrToBean(WorkLogModel.class);
		AppVO appVO = this.getAppVO();
		try {
			WorkLog entity = this.workLogService.findById(workLogModel.getId());
			entity = (WorkLog) this.toEntity(workLogModel, entity);
//			Employee emp = this.empService.findById(workLogModel.getEid());
//			entity.setContent(workLogModel.getContent());
//			entity.setWorkTimes(workLogModel.getWorkTimes());
//			// 一般情况下只是由本人修改日志,所以应该不用更新emp
//			entity.setEmp(emp);
			this.workLogService.update(entity);
			appVO.setSuccess(true);
			appVO.setMsg("更新成功");
			appVO.setRow(entity);
		} catch (Exception e) {
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
		WorkLogModel workLogModel = (WorkLogModel) appModel.attrToBean(WorkLogModel.class);
		AppVO appVO = this.getAppVO();
		try {
			this.workLogService.delete(workLogModel.getId());
			appVO.setSuccess(true);
			appVO.setMsg("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常,删除失败");
			return ERROR;
		}
		return ERROR;
	}

	@Override
	public String multiDelete() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String query() {
		AppModel appModel = this.getAppModel();
		String orderBy = appModel.getSort();
		String order = appModel.getOrder();
		WorkLogModel workLogModel = (WorkLogModel) appModel.attrToBean(WorkLogModel.class);
		AppVO appVO = this.getAppVO();
		try {
			List<WorkLog> beanList = this.workLogService.query(workLogModel, orderBy, order);
			appVO.setSuccess(true);
			appVO.setMsg("查询成功");
			for(WorkLog bean : beanList) {
//				workLogModel = (WorkLogModel) this.toModel(bean);
				workLogModel = WorkLog.toModel(bean);
				appVO.addRow(workLogModel);
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
		WorkLogModel workLogModel = (WorkLogModel) appModel.attrToBean(WorkLogModel.class);
		PageBean<WorkLog> pageBean = null;
		AppVO appVO = this.getAppVO();
		try {
			pageBean = this.workLogService.queryByPage(offset, limit, workLogModel, orderBy, order);
			List<WorkLog> beanList = pageBean.getBeanList();
			for(WorkLog bean : beanList) {
//				workLogModel = (WorkLogModel) this.toModel(bean);
				workLogModel = WorkLog.toModel(bean);
				appVO.addRow(workLogModel);
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
	public Object toEntity(Object model, Object entity) throws Exception {
		WorkLogModel workLogModel = (WorkLogModel) model;
		WorkLog workLogEntity = null;
		if(entity == null) {
			workLogEntity = new WorkLog();
		} else {
			workLogEntity = (WorkLog) entity;
		}
		Employee emp = this.empService.findById(workLogModel.getEid());
		workLogEntity.setEmp(emp);
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
