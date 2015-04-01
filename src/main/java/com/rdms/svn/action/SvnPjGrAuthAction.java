package com.rdms.svn.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.rdms.base.action.GeneralAction;
import com.rdms.svn.action.model.SvnPjGrAuthModel;
import com.rdms.svn.domain.SvnPjGrAuth;
import com.rdms.svn.service.SvnPjGrAuthService;

@Controller("svnPjGrAuthAction")
@Scope("prototype")
public class SvnPjGrAuthAction extends GeneralAction<SvnPjGrAuth, SvnPjGrAuthService, SvnPjGrAuthModel> {

	private static final long serialVersionUID = 1L;
	@Resource(name="svnPjGrAuthService")
	private SvnPjGrAuthService svnPjGrAuthService;

	@Override
	public String insert() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String multiDelete() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String query() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String queryByPage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected SvnPjGrAuth toEntity(SvnPjGrAuthModel model, SvnPjGrAuth entity)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public Object toModel(Object entity) throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
