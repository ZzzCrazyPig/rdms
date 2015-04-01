package com.rdms.bug.action;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.rdms.base.action.GeneralAction;
import com.rdms.bug.action.model.BugTraceModel;
import com.rdms.bug.domain.BugInfo;
import com.rdms.bug.domain.BugTrace;
import com.rdms.bug.service.BugInfoService;
import com.rdms.bug.service.BugTraceService;
import com.rdms.comm.domain.Employee;
import com.rdms.comm.service.EmployeeService;

@Controller("bugTraceAction")
@Scope("prototype")
public class BugTraceAction extends GeneralAction<BugTrace, BugTraceService, BugTraceModel> {

	private static final long serialVersionUID = 1L;
	private BugTraceService bugTraceService;
	@Resource(name="bugInfoService")
	private BugInfoService bugInfoService;
	@Resource(name="employeeService")
	private EmployeeService empService;
	
	public BugTraceService getBugTraceService() {
		return bugTraceService;
	}
	
	@Resource(name="bugTraceService")
	public void setBugTraceService(BugTraceService bugTraceService) {
		super.setBaseService(bugTraceService);
		this.bugTraceService = bugTraceService;
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

	@Override
	protected BugTrace toEntity(BugTraceModel model, BugTrace entity)
			throws Exception {
		BugTraceModel btModel = (BugTraceModel) model;
		BugTrace btEntity = null;
		if(entity == null) {
			btEntity = new BugTrace();
			btEntity.setCreateTime(new Date());
		} else {
			btEntity = (BugTrace) entity;
		}
		String bid = btModel.getBid();
		BugInfo bugInfo = this.bugInfoService.findById(bid);
		btEntity.setTitle(btModel.getTitle());
		btEntity.setBug(bugInfo);
		btEntity.setType(btModel.getType());
		btEntity.setDetail(btModel.getDetail());
		btEntity.setAttachment(btModel.getAttachment());
		String fromWhoId = btModel.getFromWhoId();
		String toWhoId = btModel.getToWhoId();
		Employee fromWho = this.empService.findById(fromWhoId);
		Employee toWho = this.empService.findById(toWhoId);
		btEntity.setFromWho(fromWho);
		btEntity.setToWho(toWho);
		return btEntity;
	}

}
