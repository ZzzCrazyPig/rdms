package com.rdms.comm.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.rdms.base.PageBean;
import com.rdms.base.action.BaseAction;
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
public class PjGroupAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	@Resource(name="pjGroupService")
	private PjGroupService pjGroupService;
	@Resource(name="employeeService")
	private EmployeeService empService;
	@Resource(name="pjGrMemberService")
	private PjGrMemberService pjGrMemService;

	@Override
	public String insert() {
		AppModel appModel = this.getAppModel();
		PjGroupModel pjGroupModel = (PjGroupModel) appModel.attrToBean(PjGroupModel.class);
		AppVO appVO = this.getAppVO();
		try {
			PjGroup entity = (PjGroup) this.toEntity(pjGroupModel, null);
			this.pjGroupService.save(entity);
			appVO.setSuccess(true);
			appVO.setMsg("添加成功");
//			pjGroupModel = (PjGroupModel) this.toModel(entity);
			pjGroupModel = PjGroupModel.toModel(entity);
			appVO.setRow(pjGroupModel);
		} catch (Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常,添加失败");
			return ERROR;
		}
		return SUCCESS;
	}

	@Override
	public String update() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete() {
		AppModel appModel = this.getAppModel();
		PjGroupModel pjGroupModel = (PjGroupModel) appModel.attrToBean(PjGroupModel.class);
		AppVO appVO = this.getAppVO();
		try {
			this.pjGroupService.delete(pjGroupModel.getId());
			appVO.setSuccess(true);
			appVO.setMsg("删除成功");
		} catch(Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常");
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
			this.pjGroupService.deleteByIds(ids);
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
		PjGroupModel pjGroupModel = (PjGroupModel) appModel.attrToBean(PjGroupModel.class);
		AppVO appVO = this.getAppVO();
		try {
			List<PjGroup> beanList = this.pjGroupService.query(pjGroupModel, orderBy, order);
			appVO.setSuccess(true);
			appVO.setMsg("查询成功");
			for(PjGroup bean : beanList) {
//				pjGroupModel = (PjGroupModel) this.toModel(bean);
				pjGroupModel = PjGroupModel.toModel(bean);
				appVO.addRow(pjGroupModel);
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
		PjGroupModel pjGroupModel = (PjGroupModel) appModel.attrToBean(PjGroupModel.class);
		PageBean<PjGroup> pageBean = null;
		AppVO appVO = this.getAppVO();
		try {
			pageBean = this.pjGroupService.queryByPage(offset, limit, pjGroupModel, orderBy, order);
			List<PjGroup> beanList = pageBean.getBeanList();
			for(PjGroup bean : beanList) {
//				pjGroupModel = (PjGroupModel) this.toModel(bean);
				pjGroupModel = PjGroupModel.toModel(bean);
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
	public Object toEntity(Object model, Object entity) throws Exception {
		PjGroupModel pjGroupModel = (PjGroupModel) model;
		PjGroup pjGrEntity = null;
		if(entity == null) {
			pjGrEntity = new PjGroup();
		} else {
			pjGrEntity = (PjGroup) entity;
		}
//		pjGrEntity.setId(pjGroupModel.getId());
		pjGrEntity.setName(pjGroupModel.getName());
		Employee emp = (Employee) ActionContext.getContext().getSession().get("emp");
		if(emp != null) {
			pjGrEntity.setCreateUser(emp);
		} else {
			emp = this.empService.findById(pjGroupModel.getCreateUser());
		}
		pjGrEntity.setCreateTime(new Date());
		pjGrEntity.setDetail(pjGroupModel.getDetail());
		return pjGrEntity;
	}

	// TODO 考虑是否可用,当关联的对象为hibernate持久化对象时候,序列化会出现问题
//	@Override
//	public Object toModel(Object entity) throws Exception {
//		PjGroup pjGroup = (PjGroup) entity;
//		PjGroupModel model = new PjGroupModel();
//		model.setId(pjGroup.getId());
//		model.setName(pjGroup.getName());
//		model.setCreateUser(pjGroup.getCreateUser().getId());
//		model.setCreateTime(pjGroup.getCreateTime());
//		model.setDetail(pjGroup.getDetail());
//		return model;
//	}

}
