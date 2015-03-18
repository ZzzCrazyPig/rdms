package com.rdms.comm.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.rdms.base.PageBean;
import com.rdms.base.action.BaseAction;
import com.rdms.base.action.model.AppModel;
import com.rdms.base.vo.AppVO;
import com.rdms.comm.action.model.ChangePwdModel;
import com.rdms.comm.action.model.EmployeeModel;
import com.rdms.comm.domain.Department;
import com.rdms.comm.domain.Employee;
import com.rdms.comm.service.DepartmentService;
import com.rdms.comm.service.EmployeeService;
import com.rdms.util.SecurityUtil;

@Controller("employeeAction")
@Scope("prototype")
public class EmployeeAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	@Resource(name="employeeService")
	private EmployeeService employeeService;
	@Resource(name="departmentService")
	private DepartmentService departmentService;
	
	public String changePwd() {
		AppModel appModel = this.getAppModel();
		ChangePwdModel model = (ChangePwdModel) appModel.attrToBean(ChangePwdModel.class);
		String eid = model.getEid();
		AppVO appVO = this.getAppVO();
		try {
			Employee emp = this.employeeService.findById(eid);
			if(emp.getPwd().equals(SecurityUtil.md5(model.getOldPwd()))) {
				if(model.getNewPwd().equals(model.getConfirmPwd())) {
					emp.setPwd(SecurityUtil.md5(model.getNewPwd()));
					// 进行更新操作
					this.employeeService.update(emp);
					ActionContext.getContext().getSession().put("emp", emp);
					appVO.setSuccess(true);
					appVO.setMsg("修改密码成功");
				} else {
					appVO.setSuccess(false);
					appVO.setMsg("输入的新密码与确认密码不一致,修改密码失败");
					return ERROR;
				}
			} else {
				appVO.setSuccess(false);
				appVO.setMsg("输入的原始密码有误,修改密码失败");
				return ERROR;
			}
		} catch (Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常,修改密码失败");
			return ERROR;
		}
		return SUCCESS;
	}
	
	// 添加
	@Override
	public String insert() {
		AppModel appModel = this.getAppModel();
		EmployeeModel eModel = (EmployeeModel) appModel.attrToBean(EmployeeModel.class, EmployeeModel.getClassMap());
		Employee entity = (Employee) this.toEntity(eModel, null);
		AppVO appVO = this.getAppVO();
		try {
			// 添加员工
			this.employeeService.save(entity);
			// 员工对应部门人数+1
			Department dept =  this.departmentService.findByName(entity.getDept());
			dept.setMemTotal(dept.getMemTotal() + 1);
			this.departmentService.update(dept);
//			eModel = (EmployeeModel) this.toModel(entity);
			eModel = Employee.toModel(entity);
			appVO.setSuccess(true);
			appVO.setMsg("添加成功");
			appVO.setRow(eModel);
		} catch (Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常");
			return ERROR;
		}
		return SUCCESS;
	}

	// 更新
	@Override
	public String update() {
		AppModel appModel = this.getAppModel();
		EmployeeModel eModel = (EmployeeModel) appModel.attrToBean(EmployeeModel.class, EmployeeModel.getClassMap());
		AppVO appVO = this.getAppVO();
		try {
			Employee entity = this.employeeService.findById(eModel.getId());
			// 部门改变了
			if(eModel.getDept() != null && !entity.getDept().equals(eModel.getDept())) {
				// 原部门人数-1
				Department dept = this.departmentService.findByName(entity.getDept());
				dept.setMemTotal(dept.getMemTotal() - 1);
				this.departmentService.update(dept);
				// 新部门人数+1
				Department newDept = this.departmentService.findByName(eModel.getDept());
				newDept.setMemTotal(newDept.getMemTotal() + 1);
				this.departmentService.update(newDept);
			}
			
			entity = (Employee) this.toEntity(eModel, entity);
			
//			entity.setDept(eModel.getDept());
//			entity.setName(eModel.getName());
//			entity.setAccount(eModel.getAccount());
//			entity.setPwd(eModel.getPwd());
//			entity.setGender(eModel.getGender());
//			entity.setBirthDate(eModel.getBirthDate());
//			entity.setEntryDate(eModel.getEntryDate());
//			entity.setEmail(eModel.getEmail());
//			entity.setPhone(eModel.getPhone());
//			entity.setPosition(eModel.getPosition());
//			entity.setStats(eModel.getStats());
			
			this.employeeService.update(entity);
//			eModel = (EmployeeModel) this.toModel(entity);
			
			Employee emp = (Employee) ActionContext.getContext().getSession().get("emp");
			if(emp.getId().equals(entity.getId())) {
				ActionContext.getContext().getSession().put("emp", entity);
			}
			
			eModel = Employee.toModel(entity);
			appVO.setSuccess(true);
			appVO.setMsg("更新成功");
			appVO.setRow(eModel);
		} catch(Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常");
			return ERROR;
		}
		return SUCCESS;
	}

	// 删除
	@Override
	public String delete() {
		AppModel appModel = this.getAppModel();
		EmployeeModel eModel = (EmployeeModel) appModel.attrToBean(EmployeeModel.class, EmployeeModel.getClassMap());
		AppVO appVO = this.getAppVO();
		try {
			this.employeeService.delete(eModel.getId());
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
	
	// 批量删除
	@Override
	public String multiDelete() {
		AppModel appModel = this.getAppModel();
		String attr = appModel.getAttr();
		String[] ids = attr.split(",");
		AppVO appVO = this.getAppVO();
		try {
			this.employeeService.deleteByIds(ids);
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

	// 查全部
	@Override
	public String query() {
		AppModel appModel = this.getAppModel();
		String orderBy = appModel.getSort();
		String order = appModel.getOrder();
		EmployeeModel eModel = (EmployeeModel) appModel.attrToBean(EmployeeModel.class, EmployeeModel.getClassMap());
		AppVO appVO = this.getAppVO();
		try {
			List<Employee> beanList = this.employeeService.query(eModel, orderBy, order);
			appVO.setSuccess(true);
			appVO.setMsg("查询成功");
			for(Employee bean : beanList) {
//				eModel = (EmployeeModel) this.toModel(bean);
				eModel = Employee.toModel(bean);
				appVO.addRow(eModel);
			}
		} catch (Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常,查询失败");
			return ERROR;
		}
		return SUCCESS;
	}

	// 分页查询
	@Override
	public String queryByPage() {
		AppModel appModel = this.getAppModel();
		int offset = appModel.getOffset();
		int limit = appModel.getLimit();
		String orderBy = appModel.getSort();
		String order = appModel.getOrder();
		EmployeeModel employeeModel = (EmployeeModel) appModel.attrToBean(EmployeeModel.class, EmployeeModel.getClassMap());
		PageBean<Employee> pageBean = null;
		AppVO appVO = this.getAppVO();
		try {
			pageBean = this.employeeService.queryByPage(offset, limit, employeeModel, orderBy, order);
			List<Employee> beanList = pageBean.getBeanList();
			for(Employee bean : beanList) {
//				employeeModel = (EmployeeModel) this.toModel(bean);
				employeeModel = Employee.toModel(bean);
				appVO.addRow(employeeModel);
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
	public Object toEntity(Object model, Object entity) {
		EmployeeModel eModel = (EmployeeModel) model;
		Employee empEntity = null;
		if(entity == null) {
			empEntity = new Employee();
		} else {
			empEntity = (Employee) entity;
		}
		empEntity.setName(eModel.getName() == null ? empEntity.getName() : eModel.getName());
		empEntity.setAccount(eModel.getAccount() == null ? empEntity.getAccount() : eModel.getAccount());
		if(eModel.getPwd() != null) {
			empEntity.setPwd(SecurityUtil.md5(eModel.getPwd()));
		} else if(entity == null){
			// 设置初始化密码(与账号保持一致)
			empEntity.setPwd(SecurityUtil.md5(eModel.getAccount()));
		}
		empEntity.setGender(eModel.getGender() == null ? empEntity.getGender() : eModel.getGender());
		empEntity.setBirthDate(eModel.getBirthDate() == null ? empEntity.getBirthDate() : eModel.getBirthDate());
		empEntity.setEntryDate(eModel.getEntryDate() == null ? empEntity.getEntryDate() : eModel.getEntryDate());
		empEntity.setEmail(eModel.getEmail() == null ? empEntity.getEmail() : eModel.getEmail());
		empEntity.setPhone(eModel.getPhone() == null ? empEntity.getPhone() : eModel.getPhone());
		empEntity.setDept(eModel.getDept() == null ? empEntity.getDept() : eModel.getDept());
		empEntity.setPosition(eModel.getPosition() == null ? empEntity.getPosition() : eModel.getPosition());
		empEntity.setStats(eModel.getStats() == null ? empEntity.getStats() : eModel.getStats());
		empEntity.setPic(eModel.getPic() == null ? empEntity.getPic() : eModel.getPic());
		return empEntity;
	}

//	@Override
//	public Object toModel(Object entity) throws Exception {
//		Employee emp = (Employee) entity;
//		EmployeeModel model = new EmployeeModel();
//		
//		model.setId(emp.getId());
//		model.setAccount(emp.getAccount());
//		model.setName(emp.getName());
//		model.setGender(emp.getGender());
//		model.setBirthDate(emp.getBirthDate());
//		model.setEntryDate(emp.getEntryDate());
//		model.setEmail(emp.getEmail());
//		model.setPhone(emp.getPhone());
//		model.setDept(emp.getDept());
//		model.setPosition(emp.getPosition());
//		model.setStats(emp.getStats());
//		
//		return model;
//	}


}
