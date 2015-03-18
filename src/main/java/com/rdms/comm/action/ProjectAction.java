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
import com.rdms.comm.action.model.EmployeeModel;
import com.rdms.comm.action.model.ProjectModel;
import com.rdms.comm.domain.Employee;
import com.rdms.comm.domain.PjGroup;
import com.rdms.comm.domain.Project;
import com.rdms.comm.service.EmployeeService;
import com.rdms.comm.service.PjGroupService;
import com.rdms.comm.service.ProjectService;
import com.rdms.util.StringUtil;

@Controller("projectAction")
@Scope("prototype")
public class ProjectAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	@Resource(name="projectService")
	private ProjectService projectService;
	@Resource(name="employeeService")
	private EmployeeService empService;
	@Resource(name="pjGroupService")
	private PjGroupService pjGroupService;

	@Override
	public String insert() {
		AppModel appModel = this.getAppModel();
		ProjectModel pjModel = (ProjectModel) appModel.attrToBean(ProjectModel.class, ProjectModel.getClassMap());
		AppVO appVO = this.getAppVO();
		try {
			Project entity = (Project) this.toEntity(pjModel, null);
			this.projectService.save(entity);
//			pjModel = (ProjectModel) this.toModel(entity);
			pjModel = ProjectModel.toModel(entity);
			appVO.setSuccess(true);
			appVO.setMsg("添加成功");
			appVO.setRow(pjModel);
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
		ProjectModel pjModel = (ProjectModel) appModel.attrToBean(ProjectModel.class, ProjectModel.getClassMap());
		AppVO appVO = this.getAppVO();
		try {
			Project oldEntity = this.projectService.findById(pjModel.getId());
			Project newEntity = (Project) this.toEntity(pjModel, oldEntity);
			this.projectService.update(newEntity);
			appVO.setSuccess(true);
			appVO.setMsg("更新成功");
			pjModel = ProjectModel.toModel(newEntity);
			appVO.setRow(pjModel);
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
		ProjectModel pjModel = (ProjectModel) appModel.attrToBean(ProjectModel.class, ProjectModel.getClassMap());
		AppVO appVO = this.getAppVO();
		try {
			this.projectService.delete(pjModel.getId());
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
			this.projectService.deleteByIds(ids);
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
		ProjectModel pjModel = (ProjectModel) appModel.attrToBean(ProjectModel.class, ProjectModel.getClassMap());
		AppVO appVO = this.getAppVO();
		try {
			List<Project> beanList = this.projectService.query(pjModel, orderBy, order);
			appVO.setSuccess(true);
			appVO.setMsg("查询成功");
			for(Project bean : beanList) {
//				pjModel = (ProjectModel) this.toModel(bean);
				pjModel = ProjectModel.toModel(bean);
				appVO.addRow(pjModel);
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
		ProjectModel pjModel = (ProjectModel) appModel.attrToBean(ProjectModel.class, ProjectModel.getClassMap());
		PageBean<Project> pageBean = null;
		AppVO appVO = this.getAppVO();
		try {
			pageBean = this.projectService.queryByPage(offset, limit, pjModel, orderBy, order);
			List<Project> beanList = pageBean.getBeanList();
			for(Project bean : beanList) {
//				pjModel = (ProjectModel) this.toModel(bean);
				pjModel = ProjectModel.toModel(bean);
				appVO.addRow(pjModel);
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
	
	// 查找给定员工参与的Project
	public String queryJoinPj() {
		AppModel appModel = this.getAppModel();
		EmployeeModel eModel = (EmployeeModel) appModel.attrToBean(EmployeeModel.class);
		AppVO appVO = this.getAppVO();
		String eid = null;
		if(eModel == null) {
			Employee emp = (Employee) ActionContext.getContext().getSession().get("emp");
			eid = emp.getId();
		} else {
			eid = eModel.getId();
		}
		try {
			// step1: 从PjGrMember中找出员工所参与的PjGroup
			List<PjGroup> pjGrList = this.pjGroupService.queryAllJoinPjGroup(eid);
			// step2: 从Project的pjGroup用in查询出满足的Project对象即为给定员工参与的Project
			String[] pjGrIds = new String[pjGrList.size()];
			for(int i = 0, n = pjGrList.size(); i < n; i++) {
				PjGroup pjGroup = pjGrList.get(i);
				pjGrIds[i] = pjGroup.getId();
			}
			List<Project> beanList = this.projectService.queryJoinPj(pjGrIds);
			appVO.setSuccess(true);
			appVO.setMsg("查询成功");
			for(Project bean : beanList) {
				appVO.addRow(ProjectModel.toModel(bean));
			}
		} catch (Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常,查询失败");
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String queryJoinPjByPage() {
		AppModel appModel = this.getAppModel();
		EmployeeModel eModel = (EmployeeModel) appModel.attrToBean(EmployeeModel.class);
		AppVO appVO = this.getAppVO();
		String eid = null;
		if(eModel == null) {
			Employee emp = (Employee) ActionContext.getContext().getSession().get("emp");
			eid = emp.getId();
		} else {
			eid = eModel.getId();
		}
		try {
			// step1: 从PjGrMember中找出员工所参与的PjGroup
			List<PjGroup> pjGrList = this.pjGroupService.queryAllJoinPjGroup(eid);
			// step2: 从Project的pjGroup用in查询出满足的Project对象即为给定员工参与的Project
			String[] pjGrIds = new String[pjGrList.size()];
			for(int i = 0, n = pjGrList.size(); i < n; i++) {
				PjGroup pjGroup = pjGrList.get(i);
				pjGrIds[i] = pjGroup.getId();
			}
			int offset = appModel.getOffset();
			int limit = appModel.getLimit();
			String orderBy = appModel.getSort();
			String order = appModel.getOrder();
			PageBean<Project> pageBean = null;
			if(StringUtil.isBlank(orderBy)) {
				pageBean = this.projectService.queryJoinPjByPage(pjGrIds, offset, limit, null, false);
			} else {
				if("asc".equalsIgnoreCase(order)) {
					pageBean = this.projectService.queryJoinPjByPage(pjGrIds, offset, limit, orderBy, true);
				} else {
					pageBean = this.projectService.queryJoinPjByPage(pjGrIds, offset, limit, orderBy, false);
				}
			}
			List<Project> beanList = pageBean.getBeanList();
			appVO.setSuccess(true);
			appVO.setMsg("查询成功");
			for(Project bean : beanList) {
				appVO.addRow(ProjectModel.toModel(bean));
			}
			appVO.setTotal(pageBean.getTotalCount());
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
		ProjectModel pjModel = (ProjectModel) model;
		Project pjEntity = null;
		if(entity == null) {
			pjEntity = new Project();
		} else {
			pjEntity = (Project) entity;
		}
//		pjEntity.setId(pjModel.getId());
		pjEntity.setName(pjModel.getName());
		pjEntity.setDetail(pjModel.getDetail());
		Employee emp = (Employee) ActionContext.getContext().getSession().get("emp");
		if(emp == null) {
			emp = this.empService.findById(pjModel.getCreateUser());
		}
		pjEntity.setCreateUser(emp);
		pjEntity.setCreateTime(new Date());
		pjEntity.setStartTime(pjModel.getStartTime());
		pjEntity.setEndTime(pjModel.getEndTime());
		pjEntity.setPreCompleteDay(pjModel.getPreCompleteDay());
		pjEntity.setRealCompleteDay(pjModel.getRealCompleteDay());
		PjGroup pjGroup = this.pjGroupService.findById(pjModel.getPjGrId());
		pjEntity.setPjGroup(pjGroup);
		pjEntity.setProgress(pjModel.getProgress());
		pjEntity.setStatus(pjModel.getStatus());
		return pjEntity;
	}
	
	public String queryPjByDept() {
		String deptName = ServletActionContext.getRequest().getParameter("deptName");
		AppVO appVO = this.getAppVO();
		try {
			List<Project> beanList = this.projectService.queryPjByDept(deptName);
			for(Project bean : beanList) {
				ProjectModel pjModel = ProjectModel.toModel(bean);
				appVO.addRow(pjModel);
			}
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

//	@Override
//	public Object toModel(Object entity) throws Exception {
//		Project pj = (Project) entity;
//		ProjectModel model = new ProjectModel();
//		model.setId(pj.getId());
//		model.setName(pj.getName());
//		model.setDetail(pj.getDetail());
//		model.setCreateTime(pj.getCreateTime());
//		model.setStartTime(pj.getStartTime());
//		model.setEndTime(pj.getEndTime());
//		model.setPreCompleteDay(pj.getPreCompleteDay());
//		model.setRealCompleteDay(pj.getRealCompleteDay());
//		model.setProgress(pj.getProgress());
//		model.setStatus(pj.getStatus());
//		model.setCreateUser(pj.getCreateUser().getId());
//		model.setPjGrId(pj.getPjGroup().getId());
//		
//		model.setPjGroup(PjGroupModel.toModel(pj.getPjGroup(), false));
//		model.setEmp(EmployeeModel.toModel(pj.getCreateUser()));
//		
//		return model;
//	}

}
