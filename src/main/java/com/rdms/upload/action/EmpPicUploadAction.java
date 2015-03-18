package com.rdms.upload.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.rdms.base.vo.AppVO;
import com.rdms.comm.action.model.EmployeeModel;
import com.rdms.comm.domain.Employee;
import com.rdms.comm.service.EmployeeService;

@Controller("empPicUploadAction")
@Scope("prototype")
public class EmpPicUploadAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	
	private static final String RESOURCE_ROOT_PATH = "/upload/empPic";
	
	private File empPic; // 上传的文件
	private String empPicFileName; // 上传的文件名
	private String empPicContentType; // 上传的文件类型
	
	@Resource(name="employeeService")
	private EmployeeService empService;
	@Resource(name="appVO")
	private AppVO appVO;
	
	// TODO 实现上传员工头像的逻辑功能
	public String upload() {
		String basePath = ServletActionContext.getServletContext().getRealPath(RESOURCE_ROOT_PATH);
		validFileDir(basePath);
		String fileName = "empPic-" + UUID.randomUUID() + "-" + empPicFileName;
		String filePath = basePath + "/" + fileName;
		String relativePath = "." + RESOURCE_ROOT_PATH + "/" + fileName;
		// System.out.println("filePath = " + filePath);
		AppVO appVO = this.getAppVO();
		try {
			Employee emp = (Employee) ActionContext.getContext().getSession().get("emp");
			String oldPic = emp.getPic();
			if(oldPic != null) {
				String oldPicFilePath = basePath + "/" + oldPic.substring(RESOURCE_ROOT_PATH.length() + 2);
				// System.out.println("oldPicFilePath = " + oldPicFilePath);
				removeOldPic(oldPicFilePath);
			}
			emp.setPic(relativePath);
			this.empService.update(emp);
			ActionContext.getContext().getSession().put("emp", emp);
			EmployeeModel eModel = EmployeeModel.toModel(emp);
			FileOutputStream fout = new FileOutputStream(new File(filePath));
			FileInputStream fin = new FileInputStream(getEmpPic());
			byte[] buffer = new byte[1024];
			int len = 0;
			while((len = fin.read(buffer)) > 0) {
				fout.write(buffer, 0, len);
			}
			fin.close();
			fout.close();
			appVO.setSuccess(true);
			appVO.setMsg("上传成功");
			appVO.setRow(eModel);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常,找不到指定文件,上传失败");
			return ERROR;
		} catch (IOException e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统IO异常,上传失败");
			return ERROR;
		} catch (Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常,上传失败");
			return ERROR;
		}
		return SUCCESS;
	}
	
	// 判断给定的路径是否存在,不存在则创建
	private void validFileDir(String basePath) {
		File file = new File(basePath);
		if(file.exists()) return;
		file.mkdirs();
	}

	private void removeOldPic(String filePath) {
		File file = new File(filePath);
		if(file.exists()) {
			file.delete();
		}
	}

	public File getEmpPic() {
		return empPic;
	}

	public void setEmpPic(File empPic) {
		this.empPic = empPic;
	}

	public String getEmpPicFileName() {
		return empPicFileName;
	}

	public void setEmpPicFileName(String empPicFileName) {
		this.empPicFileName = empPicFileName;
	}

	public String getEmpPicContentType() {
		return empPicContentType;
	}

	public void setEmpPicContentType(String empPicContentType) {
		this.empPicContentType = empPicContentType;
	}

	public AppVO getAppVO() {
		return appVO;
	}

	public void setAppVO(AppVO appVO) {
		this.appVO = appVO;
	}

}
