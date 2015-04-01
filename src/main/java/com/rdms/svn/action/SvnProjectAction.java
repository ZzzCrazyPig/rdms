package com.rdms.svn.action;

import java.io.IOException;
import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.rdms.base.action.GeneralAction;
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
public class SvnProjectAction extends GeneralAction<SvnProject, SvnProjectService, SvnProjectModel> {

	private static final long serialVersionUID = 1L;
	private SvnProjectService svnProjectService;
	@Resource(name="projectService")
	private ProjectService projectService;
	
	public SvnProjectService getSvnProjectService() {
		return svnProjectService;
	}

	@Resource(name="svnProjectService")
	public void setSvnProjectService(SvnProjectService svnProjectService) {
		super.setBaseService(svnProjectService);
		this.svnProjectService = svnProjectService;
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
	protected SvnProject toEntity(SvnProjectModel model, SvnProject entity)
			throws Exception {
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

}
