package com.rdms.interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.rdms.comm.domain.Employee;

public class FontendLoginInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		//System.out.println("=================Fontend Login Interceptor====================");
		ActionContext ctx = invocation.getInvocationContext();
		Employee emp = (Employee) ctx.getSession().get("emp");
		if(emp == null) {
			// ctx.put("tip", "登陆状态失效");
			System.out.println("检测到登陆状态失效,进行拦截");
			return "noLogin"; 
		}
		
		return invocation.invoke();
	}

}
