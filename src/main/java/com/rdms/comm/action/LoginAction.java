package com.rdms.comm.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.rdms.base.action.model.AppModel;
import com.rdms.base.vo.AppVO;
import com.rdms.comm.action.model.EmployeeModel;
import com.rdms.comm.domain.Employee;
import com.rdms.comm.service.EmployeeService;
import com.rdms.util.SecurityUtil;

@Controller("loginAction")
@Scope("prototype")
public class LoginAction extends ActionSupport implements ModelDriven<AppModel> {

	private static final long serialVersionUID = 1L;
	@Resource(name="appModel")
	private AppModel appModel;
	@Resource(name="employeeService")
	private EmployeeService employeeService;
	@Resource(name="appVO")
	private AppVO appVO;
	
	public String login() {
		EmployeeModel empModel = (EmployeeModel) this.getModel().attrToBean(EmployeeModel.class, EmployeeModel.getClassMap());
		String account = empModel.getAccount();
		String pwd = empModel.getPwd();
		AppVO appVO = this.getAppVO();
		try {
			Employee emp = this.employeeService.validate(account, SecurityUtil.md5(pwd));
			if(emp != null) {
				ActionContext ctx = ActionContext.getContext();
				ctx.getSession().put("emp", emp);
				appVO.setSuccess(true);
				appVO.setMsg("验证成功");
			} else {
				appVO.setSuccess(false);
				appVO.setMsg("验证失败");
			}
		} catch(Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常,登陆失败");
		}
		return SUCCESS;
	}

	public AppModel getModel() {
		return this.appModel;
	}

	public AppVO getAppVO() {
		return appVO;
	}

	public void setAppVO(AppVO appVO) {
		this.appVO = appVO;
	}

}
