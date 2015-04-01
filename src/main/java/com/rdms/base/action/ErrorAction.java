package com.rdms.base.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.rdms.base.vo.AppVO;
import com.rdms.base.vo.ErrorCode;

/**
 * 该类用于拦截器进行登陆验证、权限验证跳转
 * 当登陆验证不通过调用noLogin方法
 * 当权限验证不通过调用noPermission方法
 * @author CrazyPig
 *
 */
@Controller("errorAction")
@Scope("prototype")
public class ErrorAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	@Resource(name="appVO")
	private AppVO appVO;
	
	public String noPermission() {
		AppVO appVO = this.getAppVO();
		appVO.setSuccess(false);
		appVO.setMsg("没有权限进行此操作");
		appVO.setErrorCode(ErrorCode.NO_PERMISSION);
		return ERROR;
	}
	
	public String noLogin() {
		AppVO appVO = this.getAppVO();
		appVO.setSuccess(false);
		appVO.setMsg("登陆状态失效,请重新登陆");
		appVO.setErrorCode(ErrorCode.NO_LOGIN);
		return ERROR;
	}

	public AppVO getAppVO() {
		return appVO;
	}

	public void setAppVO(AppVO appVO) {
		this.appVO = appVO;
	}

}
