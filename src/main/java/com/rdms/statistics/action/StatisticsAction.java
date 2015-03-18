package com.rdms.statistics.action;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.rdms.base.vo.AppVO;
import com.rdms.comm.service.PjGroupService;
import com.rdms.comm.service.ProjectService;
import com.rdms.comm.service.WorkLogService;
import com.rdms.util.DateUtil;

@Controller("statisticsAction")
@Scope("prototype")
public class StatisticsAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;
	@Resource(name="projectService")
	private ProjectService pjService;
	@Resource(name="pjGroupService")
	private PjGroupService pjGroupService;
	@Resource(name="workLogService")
	private WorkLogService workLogService;
	@Resource(name="appVO")
	private AppVO appVO;
	
	// 统计项目总数,每个部门的项目数量及所占的比例
	public String countEachDeptPj() {
		AppVO appVO = this.getAppVO();
		try {
			List<Object[]> itemList = this.pjService.countEachDeptPj();
			int total = 0;
			for(Object[] item : itemList) {
				// String dept = item[0].toString();
				int pjNums = Integer.parseInt(item[1].toString());
				total = total + pjNums;
			}
			for(Object[] item : itemList) {
				// [0]:部门名称 [1]:部门项目数 [2]: 项目总数 [3]:所占比例
				JSONObject jsonObj = new JSONObject();
//				Object[] newItem = new Object[4];
//				newItem[0] = item[0];
//				newItem[1] = item[1];
//				newItem[2] = new Integer(total);
				double ratio = (double)(Double.parseDouble(item[1].toString()) / total) * 100;
				DecimalFormat df = new DecimalFormat("#.00");
//				newItem[3] = df.format(ratio);
				jsonObj.put("dept", item[0]);
				jsonObj.put("pjNums", item[1]);
				jsonObj.put("pjTotal", total);
				jsonObj.put("pjRatio", df.format(ratio));
				appVO.addRow(jsonObj);
			}
			appVO.setSuccess(true);
			appVO.setMsg("统计成功");
		} catch (Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常,统计失败");
			return ERROR;
		}
		return SUCCESS;
	}
	
	// 统计每个部门创建的项目组个数
	public String countEachDeptPjGroup() {
		AppVO appVO = this.getAppVO();
		try {
			List<Object[]> itemList = this.pjGroupService.countEachDeptPjGroup();
			int total = 0;
			for(Object[] item : itemList) {
				// appVO.addRow(item);
				int pjGrNums = Integer.parseInt(item[1].toString());
				total = total + pjGrNums;
			}
			for(Object[] item : itemList) {
				JSONObject jsonObj = new JSONObject();
				double ratio = (double)(Double.parseDouble(item[1].toString()) / total) * 100;
				DecimalFormat df = new DecimalFormat("#.00");
				jsonObj.put("dept", item[0]);
				jsonObj.put("pjGrNums", item[1]);
				jsonObj.put("pjGrTotal", total);
				jsonObj.put("pjGrRatio", df.format(ratio));
				appVO.addRow(jsonObj);
			}
			appVO.setSuccess(true);
			appVO.setMsg("统计成功");
		} catch(Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常,统计失败");
			return ERROR;
		}
		return SUCCESS;
	}
	
	// 统计给定部门每个员工的考勤情况
	public String countSpecDeptEachEmpWorkLog() {
		AppVO appVO = this.getAppVO();
		try {
			String beginDateStr = ServletActionContext.getRequest().getParameter("startDate");
			String endDateStr = ServletActionContext.getRequest().getParameter("endDate");
			String dept = ServletActionContext.getRequest().getParameter("dept");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = sdf.parse(beginDateStr);
			Date endDate = sdf.parse(endDateStr);
			List<Object[]> itemList = this.workLogService.countSpecDeptEachEmpWorkLog(startDate, endDate, dept);
			int defaultWorkDays = DateUtil.workDaysBetween(beginDateStr, endDateStr, 0);
			for(Object[] item : itemList) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("dept", item[0]);
				jsonObj.put("empName", item[1]);
				int workLogCount = Integer.parseInt(item[2].toString());
				jsonObj.put("workLogCount", workLogCount);
				jsonObj.put("defaultWorkDays", defaultWorkDays);
				appVO.addRow(jsonObj);
			}
			appVO.setSuccess(true);
			appVO.setMsg("统计成功");
		} catch(Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常,统计失败");
			return ERROR;
		}
		return SUCCESS;
	}
	
	// TODO 统计各个部门每个人在特定日期之间的考勤情况
	public String countEachDeptEachEmpWorkLog() {
		AppVO appVO = this.getAppVO();
		try {
			String beginDateStr = ServletActionContext.getRequest().getParameter("startDate");
			String endDateStr = ServletActionContext.getRequest().getParameter("endDate");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = sdf.parse(beginDateStr);
			Date endDate = sdf.parse(endDateStr);
			List<Object[]> itemList = this.workLogService.countEachDeptEachEmpWorkLog(startDate, endDate);
			int defaultWorkDays = DateUtil.workDaysBetween(beginDateStr, endDateStr, 0);
			for(Object[] item : itemList) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("dept", item[0]);
				jsonObj.put("empName", item[1]);
				int workLogCount = Integer.parseInt(item[2].toString());
				jsonObj.put("workLogCount", workLogCount);
				jsonObj.put("defaultWorkDays", defaultWorkDays);
				appVO.addRow(jsonObj);
			}
			appVO.setSuccess(true);
			appVO.setMsg("统计成功");
		} catch(Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常,统计失败");
			return ERROR;
		}
		return SUCCESS;
	}

	public AppVO getAppVO() {
		return appVO;
	}

	public void setAppVO(AppVO appVO) {
		this.appVO = appVO;
	}

	
}
