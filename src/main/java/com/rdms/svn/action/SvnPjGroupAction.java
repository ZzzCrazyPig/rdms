package com.rdms.svn.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.rdms.base.action.GeneralAction;
import com.rdms.svn.action.model.SvnPjGroupModel;
import com.rdms.svn.domain.SvnPjGroup;
import com.rdms.svn.service.SvnPjGroupService;

@Controller("svnPjGroupAction")
@Scope("prototype")
public class SvnPjGroupAction extends GeneralAction<SvnPjGroup, SvnPjGroupService, SvnPjGroupModel> {

	private static final long serialVersionUID = 1L;
	@Resource(name="svnPjGroupService")
	private SvnPjGroupService svnPjGroupService;

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
	protected SvnPjGroup toEntity(SvnPjGroupModel model, SvnPjGroup entity)
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
