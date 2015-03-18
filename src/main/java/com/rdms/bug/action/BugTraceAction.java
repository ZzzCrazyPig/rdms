package com.rdms.bug.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.rdms.base.PageBean;
import com.rdms.base.action.BaseAction;
import com.rdms.base.action.model.AppModel;
import com.rdms.base.vo.AppVO;
import com.rdms.bug.action.model.BugTraceModel;
import com.rdms.bug.domain.BugInfo;
import com.rdms.bug.domain.BugTrace;
import com.rdms.bug.service.BugInfoService;
import com.rdms.bug.service.BugTraceService;
import com.rdms.comm.domain.Employee;
import com.rdms.comm.service.EmployeeService;

@Controller("bugTraceAction")
@Scope("prototype")
public class BugTraceAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	@Resource(name="bugTraceService")
	private BugTraceService bugTraceService;
	@Resource(name="bugInfoService")
	private BugInfoService bugInfoService;
	@Resource(name="employeeService")
	private EmployeeService empService;

	@Override
	public String insert() {
		AppModel appModel = this.getAppModel();
		BugTraceModel btModel = (BugTraceModel) appModel.attrToBean(BugTraceModel.class);
		AppVO appVO = this.getAppVO();
		try {
			BugTrace entity = (BugTrace) this.toEntity(btModel, null);
			this.bugTraceService.save(entity);
			btModel = BugTraceModel.toModel(entity);
			appVO.setSuccess(true);
			appVO.setMsg("添加成功");
			appVO.setRow(btModel);
		} catch(Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常,添加失败");
			return ERROR;
		}
		return SUCCESS;
	}

	@Override
	public String update() {
		AppModel appModel = this.getAppModel();
		BugTraceModel btModel = (BugTraceModel) appModel.attrToBean(BugTraceModel.class);
		AppVO appVO = this.getAppVO();
		try {
			BugTrace oldEntity = this.bugTraceService.findById(btModel.getId());
			BugTrace newEntity = (BugTrace) this.toEntity(btModel, oldEntity);
			this.bugTraceService.update(newEntity);
			appVO.setSuccess(true);
			appVO.setMsg("更新成功");
			btModel = BugTraceModel.toModel(newEntity);
			appVO.setRow(btModel);
		} catch(Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常,更新失败");
			return ERROR;
		}
		return SUCCESS;
	}

	@Override
	public String delete() {
		AppModel appModel = this.getAppModel();
		BugTraceModel btModel = (BugTraceModel) appModel.attrToBean(BugTraceModel.class);
		AppVO appVO = this.getAppVO();
		try {
			this.bugTraceService.delete(btModel.getId());
			appVO.setSuccess(true);
			appVO.setMsg("删除成功");
		} catch(Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常,删除失败");
			return ERROR;
		}
		return SUCCESS;
	}

	@Override
	public String multiDelete() {
		AppModel appModel = this.getAppModel();
		String attr = appModel.getAttr();
		String[] ids = attr.split(",");
		AppVO appVO = this.getAppVO();
		try {
			this.bugTraceService.deleteByIds(ids);
			appVO.setSuccess(true);
			appVO.setMsg("成功删除" + ids.length + "条数据");
			appVO.setRow(attr);
		} catch (Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常,删除失败");
			return ERROR;
		}
		return SUCCESS;
	}

	@Override
	public String query() {
		AppModel appModel = this.getAppModel();
		BugTraceModel btModel = (BugTraceModel) appModel.attrToBean(BugTraceModel.class);
		String orderBy = appModel.getSort();
		String order = appModel.getOrder();
		AppVO appVO = this.getAppVO();
		try {
			List<BugTrace> beanList = this.bugTraceService.query(btModel, orderBy, order);
			appVO.setSuccess(true);
			appVO.setMsg("查询成功");
			for(BugTrace bean : beanList) {
				appVO.addRow(BugTraceModel.toModel(bean));
			}
		} catch(Exception e) {
			e.printStackTrace();
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常,查询失败");
			return ERROR;
		}
		return SUCCESS;
	}

	@Override
	public String queryByPage() {
		AppModel appModel = this.getAppModel();
		int offset = appModel.getOffset();
		int limit = appModel.getLimit();
		String orderBy = appModel.getSort();
		String order = appModel.getOrder();
		BugTraceModel btModel = (BugTraceModel) appModel.attrToBean(BugTraceModel.class);
		PageBean<BugTrace> pageBean = null;
		AppVO appVO = this.getAppVO();
		try {
			pageBean = this.bugTraceService.queryByPage(offset, limit, btModel, orderBy, order);
			List<BugTrace> beanList = pageBean.getBeanList();
			for(BugTrace bean : beanList) {
				btModel = BugTraceModel.toModel(bean);
				appVO.addRow(btModel);
			}
			appVO.setTotal(pageBean.getTotalCount());
			appVO.setSuccess(true);
			appVO.setMsg("查询成功");
		} catch (Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常,查询失败");
			return ERROR;
		}
		return SUCCESS;
	}

	@Override
	public Object toEntity(Object model, Object entity) throws Exception {
		BugTraceModel btModel = (BugTraceModel) model;
		BugTrace btEntity = null;
		if(entity == null) {
			btEntity = new BugTrace();
			btEntity.setCreateTime(new Date());
		} else {
			btEntity = (BugTrace) entity;
		}
//		private String id;
//		private BugInfo bug;
//		private String type;
//		private Date createTime;
//		private String detail;
//		private String attachment;
//		
//		private Employee fromWho;
//		private Employee toWho;
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

//	@Override
//	public Object toModel(Object entity) throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
