package com.rdms.comm.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.rdms.base.PageBean;
import com.rdms.base.action.BaseAction;
import com.rdms.base.action.model.AppModel;
import com.rdms.base.vo.AppVO;
import com.rdms.comm.action.model.PjGrMemberModel;
import com.rdms.comm.domain.Employee;
import com.rdms.comm.domain.PjGrMember;
import com.rdms.comm.domain.PjGroup;
import com.rdms.comm.service.EmployeeService;
import com.rdms.comm.service.PjGrMemberService;
import com.rdms.comm.service.PjGroupService;

@Controller("pjGrMemberAction")
@Scope("prototype")
public class PjGrMemberAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	@Resource(name="pjGrMemberService")
	private PjGrMemberService pjGrMemberService;
	@Resource(name="employeeService")
	private EmployeeService empService;
	@Resource(name="pjGroupService")
	private PjGroupService pjGroupService;
	
	@Override
	public String insert() {
		AppModel appModel = this.getAppModel();
		PjGrMemberModel pjGrMemModel = (PjGrMemberModel) appModel.attrToBean(PjGrMemberModel.class);
		AppVO appVO = this.getAppVO();
		try {
			PjGrMember entity = (PjGrMember) this.toEntity(pjGrMemModel, null);
			this.pjGrMemberService.save(entity);
//			pjGrMemModel = (PjGrMemberModel) this.toModel(entity);
			pjGrMemModel = PjGrMember.toModel(entity);
			appVO.setSuccess(true);
			appVO.setMsg("添加成功");
			appVO.setRow(pjGrMemModel);
		} catch (Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常,添加失败");
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String multiInsert() {
		AppModel appModel = this.getAppModel();
		List<Object> beanList = appModel.attrToBeanList(PjGrMemberModel.class);
		AppVO appVO = this.getAppVO();
		try {
			for(Object bean : beanList) {
				PjGrMemberModel pjGrMemModel = (PjGrMemberModel) bean;
				PjGrMember entity = (PjGrMember) this.toEntity(pjGrMemModel, null);
				this.pjGrMemberService.save(entity);
//				pjGrMemModel = (PjGrMemberModel) this.toModel(entity);
				pjGrMemModel = PjGrMember.toModel(entity);
				appVO.addRow(pjGrMemModel);
			}
			appVO.setSuccess(true);
			appVO.setMsg("添加成功");
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
		PjGrMemberModel pjGrMemModel = (PjGrMemberModel) appModel.attrToBean(PjGrMemberModel.class);
		AppVO appVO = this.getAppVO();
		try {
			this.pjGrMemberService.delete(pjGrMemModel.getId());
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
			this.pjGrMemberService.deleteByIds(ids);
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
		PjGrMemberModel pjGrMemModel = (PjGrMemberModel) appModel.attrToBean(PjGrMemberModel.class);
		AppVO appVO = this.getAppVO();
		try {
			List<PjGrMember> beanList = this.pjGrMemberService.query(pjGrMemModel, orderBy, order);
			appVO.setSuccess(true);
			appVO.setMsg("查询成功");
			for(PjGrMember bean : beanList) {
//				pjGrMemModel = (PjGrMemberModel) this.toModel(bean);
				pjGrMemModel = PjGrMember.toModel(bean);
				appVO.addRow(pjGrMemModel);
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
		PjGrMemberModel pjGrMemModel = (PjGrMemberModel) appModel.attrToBean(PjGrMemberModel.class);
		PageBean<PjGrMember> pageBean = null;
		AppVO appVO = this.getAppVO();
		try {
			pageBean = this.pjGrMemberService.queryByPage(offset, limit, pjGrMemModel, orderBy, order);
			List<PjGrMember> beanList = pageBean.getBeanList();
			for(PjGrMember bean : beanList) {
				pjGrMemModel = PjGrMemberModel.toModel(bean);
				appVO.addRow(pjGrMemModel);
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
		PjGrMemberModel pjGrMemModel = (PjGrMemberModel) model;
		PjGrMember pjGrMemEntity = null;
		if(entity == null) {
			pjGrMemEntity = new PjGrMember();
		} else {
			pjGrMemEntity = (PjGrMember) entity;
		}
//		pjGrMemEntity.setId(pjGrMemModel.getId());
		Employee emp = this.empService.findById(pjGrMemModel.getEid());
		pjGrMemEntity.setEmp(emp);
		PjGroup pjGroup = this.pjGroupService.findById(pjGrMemModel.getPjGrId());
		pjGrMemEntity.setPjGroup(pjGroup);
		pjGrMemEntity.setRole(pjGrMemModel.getRole());
		return pjGrMemEntity;
	}

//	@Override
//	public Object toModel(Object entity) throws Exception {
//		PjGrMember pjGrMem = (PjGrMember) entity;
//		PjGrMemberModel model = new PjGrMemberModel();
//		model.setId(pjGrMem.getId());
//		model.setEmp(EmployeeModel.toModel(pjGrMem.getEmp()));
//		model.setEid(pjGrMem.getEmp().getId());
//		model.setPjGrId(pjGrMem.getPjGroup().getId());
//		model.setRole(pjGrMem.getRole());
//		return model;
//	}

}
