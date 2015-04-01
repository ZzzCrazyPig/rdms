package com.rdms.comm.action;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.rdms.base.action.GeneralAction;
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
public class EmployeeAction extends GeneralAction<Employee, EmployeeService, EmployeeModel> {

	private static final long serialVersionUID = 1L;
	
	private EmployeeService employeeService;
	@Resource(name="departmentService")
	private DepartmentService departmentService;
	
	@Resource(name="employeeService")
	public void setEmployeeService(EmployeeService employeeService) {
		super.setBaseService(employeeService);
		this.employeeService = employeeService;
	}
	
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
		AppVO appVO = this.getAppVO();
		try {
			Employee entity = (Employee) this.toEntity(eModel, null);
			// 添加员工
			this.employeeService.save(entity);
			// 员工对应部门人数+1
			Department dept =  this.departmentService.findByName(entity.getDept());
			dept.setMemTotal(dept.getMemTotal() + 1);
			this.departmentService.update(dept);
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
			this.employeeService.update(entity);
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
			// 原部门人数-1
			Department dept = this.departmentService.findByName(eModel.getDept());
			dept.setMemTotal(dept.getMemTotal() - 1);
			this.departmentService.update(dept);
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
		String _attr = appModel.getAttr();
		String attr = _attr.substring(1, _attr.length() - 1);
		String[] _ids = attr.split(",");
		String[] ids = new String[_ids.length];
		String delIdsStr = "";
		for(int i = 0; i < _ids.length; i++) {
			String _id = _ids[i];
			ids[i] = _id.substring(1, _id.length() - 1);
			delIdsStr = delIdsStr + ids[i] + ",";
		}
		delIdsStr = delIdsStr.substring(0, delIdsStr.length() - 1);
		AppVO appVO = this.getAppVO();
		try {
			this.employeeService.deleteByIds(ids);
			// 对应员工部门人数需要进行修改
			List<Object[]> itemList = this.employeeService.countNumsByDept(ids);
			for(Object[] item : itemList) {
				String deptName = item[0].toString();
				Integer nums = Integer.parseInt(item[1].toString());
				Department dept = this.departmentService.findByName(deptName);
				dept.setMemTotal(dept.getMemTotal() - nums);
				this.departmentService.update(dept);
			}
			appVO.setSuccess(true);
			appVO.setMsg("成功删除" + ids.length + "条数据");
			appVO.setRow(delIdsStr);
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
		return super.query();
	}

	// 分页查询
	@Override
	public String queryByPage() {
		return super.queryByPage();
	}

	@Override
	protected Employee toEntity(EmployeeModel model, Employee entity)
			throws Exception {
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


}
