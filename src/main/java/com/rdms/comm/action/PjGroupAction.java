package com.rdms.comm.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.rdms.base.PageBean;
import com.rdms.base.action.GeneralAction;
import com.rdms.base.action.model.AppModel;
import com.rdms.base.vo.AppVO;
import com.rdms.comm.action.model.EmployeeModel;
import com.rdms.comm.action.model.PjGroupModel;
import com.rdms.comm.domain.Employee;
import com.rdms.comm.domain.PjGrMember;
import com.rdms.comm.domain.PjGroup;
import com.rdms.comm.service.EmployeeService;
import com.rdms.comm.service.PjGrMemberService;
import com.rdms.comm.service.PjGroupService;

@Controller("pjGroupAction")
@Scope("prototype")
public class PjGroupAction extends GeneralAction<PjGroup, PjGroupService, PjGroupModel> {

	private static final long serialVersionUID = 1L;
	
	private PjGroupService pjGroupService;
	@Resource(name="employeeService")
	private EmployeeService empService;
	@Resource(name="pjGrMemberService")
	private PjGrMemberService pjGrMemService;
	
	@Resource(name="pjGroupService")
	public void setPjGroupService(PjGroupService pjGroupService) {
		super.setBaseService(pjGroupService);
		this.pjGroupService = pjGroupService;
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
	
	// 分页获取PjGroup对象,同时返回关联的PjGrMember对象集合
	public String queryFetchPjGrMemsByPage() {
		AppModel appModel = this.getAppModel();
		int offset = appModel.getOffset();
		int limit = appModel.getLimit();
		String orderBy = appModel.getSort();
		String order = appModel.getOrder();
		PjGroupModel pjGroupModel = (PjGroupModel) appModel.attrToBean(PjGroupModel.class);
		PageBean<PjGroup> pageBean = null;
		AppVO appVO = this.getAppVO();
		try {
			pageBean = this.pjGroupService.queryFetchPjGrMemsByPage(offset, limit, pjGroupModel, orderBy, order);
			List<PjGroup> beanList = pageBean.getBeanList();
			for(PjGroup bean : beanList) {
				pjGroupModel = PjGroupModel.toModel(bean, true);
				appVO.addRow(pjGroupModel);
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
	
	// 查询不在当前项目组的员工对象
	public String queryEmpNoInPjGroup() {
		AppModel appModel = this.getAppModel();
		AppVO appVO = this.getAppVO();
		PjGroupModel pjGroupModel = (PjGroupModel) appModel
				.attrToBean(PjGroupModel.class);
		String pjGrId = pjGroupModel.getId();
		// 先找出当前项目组中的项目成员
		try {
			List<PjGrMember> pjGrMemList = this.pjGrMemService
					.queryBySpecPjGroup(pjGrId);
			List<String> idsList = new ArrayList<String>(16);
			// 封装ids数组
			for (PjGrMember pjGrMem : pjGrMemList) {
				idsList.add(pjGrMem.getEmp().getId());
			}
			List<Employee> beanList = null;
			int size = idsList.size();
			if (size == 0) {
				beanList = this.empService.findAll();
			} else {
				String[] ids = new String[idsList.size()];
				idsList.toArray(ids);
				// 查找员工对象,排除已经存在于当前项目组的员工
				beanList = this.empService.findByIds(ids, false);
			}
			for(Employee bean : beanList) {
				EmployeeModel empModel = EmployeeModel.toModel(bean);
				appVO.addRow(empModel);
			}
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
	protected PjGroup toEntity(PjGroupModel model, PjGroup entity) {
		PjGroupModel pjGroupModel = (PjGroupModel) model;
		PjGroup pjGrEntity = null;
		if(entity == null) {
			pjGrEntity = new PjGroup();
		} else {
			pjGrEntity = (PjGroup) entity;
		}
		pjGrEntity.setName(pjGroupModel.getName());
		Employee emp = (Employee) ActionContext.getContext().getSession().get("emp");
		if(emp != null) {
			pjGrEntity.setCreateUser(emp);
		} else {
			try {
				emp = this.empService.findById(pjGroupModel.getCreateUser());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		pjGrEntity.setCreateTime(new Date());
		pjGrEntity.setDetail(pjGroupModel.getDetail());
		return pjGrEntity;
	}

}
