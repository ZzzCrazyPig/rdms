package com.rdms.sys.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.rdms.base.action.model.AppModel;
import com.rdms.base.vo.AppVO;
import com.rdms.sys.action.model.UserModel;
import com.rdms.sys.domain.User;
import com.rdms.sys.service.UserService;
import com.rdms.util.SecurityUtil;

@Controller("sysLoginAction")
@Scope("prototype")
public class LoginAction extends ActionSupport implements ModelDriven<AppModel> {

	private static final long serialVersionUID = 1L;
	
	@Resource(name="userService")
	private UserService userService;
	@Resource(name="appModel")
	private AppModel appModel;
	@Resource(name="appVO")
	private AppVO appVO;
	
	// 登录功能
	public String login() {
		UserModel userModel = (UserModel) this.getModel().attrToBean(UserModel.class, UserModel.getClassMap());
		String account = userModel.getAccount();
		String pwd = userModel.getPwd();
		String md5Pwd = SecurityUtil.md5(pwd);
		try {
			User user = this.userService.validate(account, md5Pwd);
			if(user != null) {
				ActionContext ctx = ActionContext.getContext();
				ctx.getSession().put("user", user);
				AppVO appVO = this.getAppVO();
				appVO.setSuccess(true);
				appVO.setMsg("验证成功");
			} else {
				appVO.setSuccess(false);
				appVO.setMsg("验证失败,请检查用户名和密码是否输入有误");
			}
		} catch (Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常,登录失败");
		}
		return SUCCESS;
	}

	public AppModel getModel() {
		return appModel;
	}

	public AppModel getAppModel() {
		return appModel;
	}

	public void setAppModel(AppModel appModel) {
		this.appModel = appModel;
	}

	public AppVO getAppVO() {
		return appVO;
	}

	public void setAppVO(AppVO appVO) {
		this.appVO = appVO;
	}

	
	
}
