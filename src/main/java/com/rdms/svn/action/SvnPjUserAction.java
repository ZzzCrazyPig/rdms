package com.rdms.svn.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.rdms.base.action.GeneralAction;
import com.rdms.svn.action.model.SvnPjUserModel;
import com.rdms.svn.domain.SvnPjUser;
import com.rdms.svn.service.SvnPjUserService;

@Controller("svnPjUserAction")
@Scope("prototype")
public class SvnPjUserAction extends GeneralAction<SvnPjUser, SvnPjUserService, SvnPjUserModel> {

	private static final long serialVersionUID = 1L;
	@Resource(name="svnPjUserService")
	private SvnPjUserService svnPjUserService;

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
	protected SvnPjUser toEntity(SvnPjUserModel model, SvnPjUser entity)
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
