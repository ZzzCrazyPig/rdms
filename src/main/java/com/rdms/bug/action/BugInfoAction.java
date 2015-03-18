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
import com.rdms.bug.action.model.BugInfoModel;
import com.rdms.bug.domain.BugInfo;
import com.rdms.bug.service.BugInfoService;
import com.rdms.comm.domain.Employee;
import com.rdms.comm.domain.Project;
import com.rdms.comm.service.EmployeeService;
import com.rdms.comm.service.ProjectService;

@Controller("bugInfoAction")
@Scope("prototype")
public class BugInfoAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	@Resource(name="bugInfoService")
	private BugInfoService bugInfoService;
	@Resource(name="projectService")
	private ProjectService projectService;
	@Resource(name="employeeService")
	private EmployeeService empService;

	@Override
	public String insert() {
		AppModel appModel = this.getAppModel();
		BugInfoModel biModel = (BugInfoModel) appModel.attrToBean(BugInfoModel.class);
		AppVO appVO = this.getAppVO();
		try {
			BugInfo entity = (BugInfo) this.toEntity(biModel, null);
			this.bugInfoService.save(entity);
			biModel = BugInfoModel.toModel(entity);
			appVO.setSuccess(true);
			appVO.setMsg("添加成功");
			appVO.setRow(biModel);
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
		BugInfoModel biModel = (BugInfoModel) appModel.attrToBean(BugInfoModel.class);
		AppVO appVO = this.getAppVO();
		try {
			BugInfo oldEntity = this.bugInfoService.findById(biModel.getId());
			BugInfo newEntity = (BugInfo) this.toEntity(biModel, oldEntity);
			this.bugInfoService.update(newEntity);
			appVO.setSuccess(true);
			appVO.setMsg("更新成功");
			biModel = BugInfoModel.toModel(newEntity);
			appVO.setRow(biModel);
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
		BugInfoModel biModel = (BugInfoModel) appModel.attrToBean(BugInfoModel.class);
		AppVO appVO = this.getAppVO();
		try {
			this.bugInfoService.delete(biModel.getId());
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
			this.bugInfoService.deleteByIds(ids);
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
		BugInfoModel biModel = (BugInfoModel) appModel.attrToBean(BugInfoModel.class);
		String orderBy = appModel.getSort();
		String order = appModel.getOrder();
		AppVO appVO = this.getAppVO();
		try {
			List<BugInfo> beanList = this.bugInfoService.query(biModel, orderBy, order);
			appVO.setSuccess(true);
			appVO.setMsg("查询成功");
			for(BugInfo bean : beanList) {
				appVO.addRow(BugInfoModel.toModel(bean));
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
		BugInfoModel biModel = (BugInfoModel) appModel.attrToBean(BugInfoModel.class);
		PageBean<BugInfo> pageBean = null;
		AppVO appVO = this.getAppVO();
		try {
			pageBean = this.bugInfoService.queryByPage(offset, limit, biModel, orderBy, order);
			List<BugInfo> beanList = pageBean.getBeanList();
			for(BugInfo bean : beanList) {
				biModel = BugInfoModel.toModel(bean);
				appVO.addRow(biModel);
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
		BugInfoModel biModel = (BugInfoModel) model;
		BugInfo biEntity = null;
		if(entity == null) {
			biEntity = new BugInfo();
		} else {
			biEntity = (BugInfo) entity;
		}
//		private String id;
//		private String name;
//		private Project project;
//		private String title;
//		private String detail;
//		private Employee qa;
//		private Employee dev;
//		private String level;
//		private String status;
//		private Date createTime;
//		private int preResolveDay;
//		private int realResolveDay;
//		private Date resolveTime;
//		private String attachment;
		biEntity.setName(biModel.getName());
		String pid = biModel.getPid();
		Project pj = this.projectService.findById(pid);
		biEntity.setProject(pj);
		biEntity.setTitle(biModel.getTitle());
		biEntity.setDetail(biModel.getDetail());
		String qaId = biModel.getQaId();
		String devId = biModel.getDevId();
		Employee qa = this.empService.findById(qaId);
		Employee dev = this.empService.findById(devId);
		biEntity.setQa(qa);
		biEntity.setDev(dev);
		biEntity.setLevel(biModel.getLevel());
		biEntity.setStatus(biModel.getStatus());
		biEntity.setCreateTime(new Date());
		biEntity.setPreResolveDay(biModel.getPreResolveDay());
		biEntity.setRealResolveDay(biModel.getRealResolveDay());
		biEntity.setResolveTime(biModel.getResolveTime());
		biEntity.setAttachment(biModel.getAttachment());
		return biEntity;
	}

}
