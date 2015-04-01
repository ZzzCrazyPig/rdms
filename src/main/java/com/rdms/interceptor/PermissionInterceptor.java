package com.rdms.interceptor;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.rdms.auth.domain.Action;

public class PermissionInterceptor extends AbstractInterceptor {
	
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionProxy actionProxy = invocation.getProxy();
		String namespace = actionProxy.getNamespace();
		String actionName = actionProxy.getActionName();
		System.out.println("namespace : " + namespace);
		System.out.println("actionName : " + actionName);
		
		String requestUrl = "." + namespace + "/" + actionName;
		
		// 1. 查询用户所被禁止的权限集合(请求action集合)
		
		ActionContext ctx = ActionContext.getContext();
		List<Action> banList = (List<Action>) ctx.getSession().get("banList");
		
		// 2. 如果当前的请求action存在 1 中得到的集合,则该操作为越权操作,提示用户没有权限进行接下来的操作
		
		boolean hasPermission = hasPermission(requestUrl, banList);
		
		if(!hasPermission) {
			// TODO 这里需要解决一个问题,如何返回JSON数据
			return "noPermission";
		}
		
		return invocation.invoke();
	}
	
	// 检查对于请求的url是否具有访问权限
	private boolean hasPermission(String requestUrl, List<Action> banList) {
		for(Action ban : banList) {
			String banRequestUrl = ban.getUrl();
			if(requestUrl.equals(banRequestUrl)) {
				return false;
			}
		}
		return true;
	}

}
