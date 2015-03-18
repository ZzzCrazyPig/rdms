package com.rdms.comm.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Controller("exitAction")
@Scope("prototype")
public class ExitAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	
	public String exit() {
		ActionContext ctx = ActionContext.getContext();
		ctx.getSession().remove("emp");
		return SUCCESS;
	}

}
