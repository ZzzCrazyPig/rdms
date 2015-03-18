package com.rdms.svn.action;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.rdms.base.PageBean;
import com.rdms.base.action.BaseAction;
import com.rdms.base.action.model.AppModel;
import com.rdms.base.vo.AppVO;
import com.rdms.comm.domain.Employee;
import com.rdms.comm.domain.Project;
import com.rdms.comm.service.ProjectService;
import com.rdms.svn.action.model.SvnProjectModel;
import com.rdms.svn.domain.SvnProject;
import com.rdms.svn.service.SvnProjectService;
import com.rdms.util.StatsvnUtil;

@Controller("svnProjectAction")
@Scope("prototype")
public class SvnProjectAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	@Resource(name="svnProjectService")
	private SvnProjectService svnProjectService;
	@Resource(name="projectService")
	private ProjectService projectService;

	@Override
	public String insert() {
		AppModel appModel = this.getAppModel();
		SvnProjectModel svnPjModel = (SvnProjectModel) appModel.attrToBean(SvnProjectModel.class);
		AppVO appVO = this.getAppVO();
		try {
			SvnProject entity = (SvnProject) this.toEntity(svnPjModel, null);
			this.svnProjectService.save(entity);
			svnPjModel = SvnProjectModel.toModel(entity);
			appVO.setSuccess(true);
			appVO.setMsg("添加成功");
			appVO.setRow(svnPjModel);
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
		SvnProjectModel svnPjModel = (SvnProjectModel) appModel.attrToBean(SvnProjectModel.class);
		AppVO appVO = this.getAppVO();
		try {
			SvnProject oldEntity = this.svnProjectService.findById(svnPjModel.getId());
			SvnProject newEntity = (SvnProject) this.toEntity(svnPjModel, oldEntity);
			this.svnProjectService.update(newEntity);
			svnPjModel = SvnProjectModel.toModel(newEntity);
			appVO.setSuccess(true);
			appVO.setMsg("更新成功");
			appVO.setRow(svnPjModel);
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
		SvnProjectModel svnPjModel = (SvnProjectModel) appModel.attrToBean(SvnProjectModel.class);
		AppVO appVO = this.getAppVO();
		try {
			this.svnProjectService.delete(svnPjModel.getId());
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
			this.svnProjectService.deleteByIds(ids);
			appVO.setSuccess(true);
			appVO.setMsg("成功删除" + ids.length + "条数据");
			appVO.setRow(attr);
		} catch (Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常");
			return ERROR;
		}
		return SUCCESS;
	}

	@Override
	public String query() {
		AppModel appModel = this.getAppModel();
		String orderBy = appModel.getSort();
		String order = appModel.getOrder();
		SvnProjectModel svnPjModel = (SvnProjectModel) appModel.attrToBean(SvnProjectModel.class);
		AppVO appVO = this.getAppVO();
		try {
			List<SvnProject> beanList = this.svnProjectService.query(svnPjModel, orderBy, order);
			appVO.setSuccess(true);
			appVO.setMsg("查询成功");
			for(SvnProject bean : beanList) {
				svnPjModel = SvnProjectModel.toModel(bean);
				appVO.addRow(svnPjModel);
			}
		} catch (Exception e) {
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
		SvnProjectModel svnPjModel = (SvnProjectModel) appModel.attrToBean(SvnProjectModel.class);
		PageBean<SvnProject> pageBean = null;
		AppVO appVO = this.getAppVO();
		try {
			pageBean = this.svnProjectService.queryByPage(offset, limit, svnPjModel, orderBy, order);
			List<SvnProject> beanList = pageBean.getBeanList();
			for(SvnProject bean : beanList) {
				svnPjModel = SvnProjectModel.toModel(bean);
				appVO.addRow(svnPjModel);
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
	
	// 实现svn代码统计的功能
	public String statsvn() {
		AppModel appModel = this.getAppModel();
		SvnProjectModel svnPjModel = (SvnProjectModel) appModel.attrToBean(SvnProjectModel.class);
		// 获取svn仓库访问地址
		String url = svnPjModel.getUrl();
		Employee emp =  (Employee) ActionContext.getContext().getSession().get("emp");
		String eid = emp.getId();
		String baseCheckoutDir = ServletActionContext.getServletContext().getRealPath("svn-checkout");
		String baseOutputDir = ServletActionContext.getServletContext().getRealPath("statsvn-output");
		String statsvnDir = ServletActionContext.getServletContext().getRealPath("statsvn");
		String checkoutDir = baseCheckoutDir + "/" + eid;
		String outputDir = baseOutputDir + "/" + eid;
		AppVO appVO = this.getAppVO();
		try {
			StatsvnUtil.runStatsvn(statsvnDir, url, checkoutDir, outputDir);
			String iframeSrc = "./statsvn-output" + "/" + eid + "/index.html";
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("iframeSrc", iframeSrc);
			appVO.setRow(jsonObj);
			appVO.setSuccess(true);
			appVO.setMsg("统计结果生成成功");
		} catch (IOException e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统IO异常,统计失败");
			return ERROR;
		} catch (InterruptedException e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统中断异常,统计失败");
			return ERROR;
		}
		return SUCCESS;
	}

	@Override
	public Object toEntity(Object model, Object entity) throws Exception {
		SvnProjectModel svnPjModel = (SvnProjectModel) model;
		SvnProject svnPjEntity = null;
		if(entity == null) {
			svnPjEntity = new SvnProject();
		} else {
			svnPjEntity = (SvnProject) entity;
		}
		svnPjEntity.setPath(svnPjModel.getPath());
		svnPjEntity.setUrl(svnPjModel.getUrl());
		svnPjEntity.setDetail(svnPjModel.getDetail());
		Project pj = this.projectService.findById(svnPjModel.getPid());
		svnPjEntity.setProject(pj);
		return svnPjEntity;
	}

//	@Override
//	public Object toModel(Object entity) throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
