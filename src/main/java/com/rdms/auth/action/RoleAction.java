package com.rdms.auth.action;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.rdms.auth.action.model.RoleModel;
import com.rdms.auth.domain.Action;
import com.rdms.auth.domain.Role;
import com.rdms.auth.service.ActionService;
import com.rdms.auth.service.RoleService;
import com.rdms.base.action.GeneralAction;
import com.rdms.base.action.model.AppModel;
import com.rdms.base.vo.AppVO;
import com.rdms.base.vo.ErrorCode;
import com.rdms.comm.action.model.EmployeeModel;
import com.rdms.comm.domain.Employee;
import com.rdms.comm.service.EmployeeService;

@Controller("roleAction")
@Scope("prototype")
public class RoleAction extends GeneralAction<Role, RoleService, RoleModel> {

	private static final long serialVersionUID = 1L;
	
	private RoleService roleService;
	@Resource(name="employeeService")
	private EmployeeService empService;
	@Resource(name="actionService")
	private ActionService actionService;

	public RoleService getRoleService() {
		return roleService;
	}

	@Resource(name="roleService")
	public void setRoleService(RoleService roleService) {
		super.setBaseService(roleService);
		this.roleService = roleService;
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
	
	// 映射禁止的请求到该角色
	public String mapBan() {
		AppModel appModel = this.getAppModel();
		JSONObject jsonObj = appModel.attrToJSONObject();
		String[] ids = (String[]) jsonObj.get("ids");
		String roleId = jsonObj.getString("roleId");
		AppVO appVO = this.getAppVO();
		try {
			Role role = this.roleService.findById(roleId);
			List<Action> banMapList = this.actionService.findByIds(ids, true);
			role.getBans().addAll(banMapList);
			appVO.setSuccess(true);
			appVO.setMsg("映射成功");
		} catch(Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常,映射失败");
			appVO.setErrorCode(ErrorCode.SYS_ERROR);
			return ERROR;
		}
		return SUCCESS;
	}
	
	// 映射禁止的请求到该角色
	public String unMapBan() {
		AppModel appModel = this.getAppModel();
		JSONObject jsonObj = appModel.attrToJSONObject();
		JSONArray idsJsonArr = jsonObj.getJSONArray("ids");
		int n = idsJsonArr.size();
		String[] ids = new String[n];
		for(int i = 0; i < n; i++) {
			ids[i] = idsJsonArr.getString(i);
		}
		String roleId = jsonObj.getString("roleId");
		AppVO appVO = this.getAppVO();
		try {
			Role role = this.roleService.findById(roleId);
			List<Action> unMapBanList = this.actionService.findByIds(ids, true);
			role.getBans().removeAll(unMapBanList);
			this.roleService.update(role);
			appVO.setSuccess(true);
			appVO.setMsg("成功解除映射");
		} catch(Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常,解除映射失败");
			appVO.setErrorCode(ErrorCode.SYS_ERROR);
			return ERROR;
		}
		return SUCCESS;
	}
	
	// 映射员工到该角色
	public String mapEmp() {
		AppModel appModel = this.getAppModel();
		JSONObject jsonObj = appModel.attrToJSONObject();
		JSONArray idsJsonArr = jsonObj.getJSONArray("ids");
		int n = idsJsonArr.size();
		String[] ids = new String[n];
		for(int i = 0; i < n; i++) {
			ids[i] = idsJsonArr.getString(i);
		}
		String roleId = jsonObj.getString("roleId");
		AppVO appVO =  this.getAppVO();
		try {
			Role role = this.roleService.findById(roleId);
			List<Employee> mapEmpList = this.empService.findByIds(ids, true);
			role.getEmps().addAll(mapEmpList);
			this.roleService.update(role);
			appVO.setSuccess(true);
			appVO.setMsg("映射成功");
			// 返回所映射的员工ids
			for(String id : ids) {
				appVO.addRow(id);
			}
			// 返回更新后的role对象
			appVO.setRow(RoleModel.toModel(role));
		} catch (Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常,映射失败");
			appVO.setErrorCode(ErrorCode.SYS_ERROR);
			return ERROR; 
		}
		return SUCCESS;
	}
	
	// 解除员工到该角色的映射
	public String unMapEmp() {
		AppModel appModel = this.getAppModel();
		JSONObject jsonObj = appModel.attrToJSONObject();
		JSONArray idsJsonArr = jsonObj.getJSONArray("ids");
		int n = idsJsonArr.size();
		String[] ids = new String[n];
		for(int i = 0; i < n; i++) {
			ids[i] = idsJsonArr.getString(i);
		}
		String roleId = jsonObj.getString("roleId");
		AppVO appVO = this.getAppVO();
		try {
			Role role = this.roleService.findById(roleId);
			List<Employee> unMapEmpList = this.empService.findByIds(ids, true);
			role.getEmps().removeAll(unMapEmpList);
			this.roleService.update(role);
			appVO.setSuccess(true);
			appVO.setMsg("成功解除映射");
			appVO.setRow(RoleModel.toModel(role));
			for(String id : ids) {
				appVO.addRow(id);
			}
		} catch(Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常,解除映射失败");
			appVO.setErrorCode(ErrorCode.SYS_ERROR);
			return ERROR;
		}
		return SUCCESS;
	}
	
	// 查询所有可供映射到指定角色的员工
	public String queryAvailableEmp() {
		AppModel appModel = this.getAppModel();
		RoleModel roleModel = (RoleModel) appModel.attrToBean(RoleModel.class);
		AppVO appVO = this.getAppVO();
		try {
			String roleId = roleModel.getId();
			Role role = this.roleService.findById(roleId);
			Set<Employee> usedEmps = role.getEmps();
			List<Employee> allEmps = this.empService.findAll();
			
			for(Employee usedEmp : usedEmps) {
				allEmps.remove(usedEmp);
			}
			appVO.setSuccess(true);
			appVO.setMsg("查询成功");
			for(Employee emp : allEmps) {
				appVO.addRow(EmployeeModel.toModel(emp));
			}
			appVO.setTotal(allEmps.size());
		} catch(Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常,查询失败");
			appVO.setErrorCode(ErrorCode.SYS_ERROR);
			return ERROR;
		}
		return SUCCESS;
	}

	@Override
	protected Role toEntity(RoleModel model, Role entity) throws Exception {
		RoleModel roleModel = (RoleModel) model;
		Role roleEntity = null;
		if(entity == null) {
			roleEntity = new Role();
		} else {
			roleEntity = (Role) entity;
		}
		roleEntity.setId(roleModel.getId());
		roleEntity.setName(roleModel.getName());
		roleEntity.setDetail(roleModel.getDetail());
		return roleEntity;
	}

}
