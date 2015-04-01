package com.rdms.comm.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.rdms.base.action.GeneralAction;
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
public class PjGrMemberAction extends GeneralAction<PjGrMember, PjGrMemberService, PjGrMemberModel> {

	private static final long serialVersionUID = 1L;
	
	private PjGrMemberService pjGrMemberService;
	@Resource(name="employeeService")
	private EmployeeService empService;
	@Resource(name="pjGroupService")
	private PjGroupService pjGroupService;
	
	@Resource(name="pjGrMemberService")
	public void setPjGrMemberService(PjGrMemberService pjGrMemberService) {
		super.setBaseService(pjGrMemberService);
		this.pjGrMemberService = pjGrMemberService;
	}
	
	@Override
	public String insert() {
		return super.insert();
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
	protected PjGrMember toEntity(PjGrMemberModel model, PjGrMember entity) {
		PjGrMemberModel pjGrMemModel = (PjGrMemberModel) model;
		PjGrMember pjGrMemEntity = null;
		if(entity == null) {
			pjGrMemEntity = new PjGrMember();
		} else {
			pjGrMemEntity = (PjGrMember) entity;
		}
		Employee emp = null;
		try {
			emp = this.empService.findById(pjGrMemModel.getEid());
			pjGrMemEntity.setEmp(emp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		PjGroup pjGroup = null;
		try {
			pjGroup = this.pjGroupService.findById(pjGrMemModel.getPjGrId());
			pjGrMemEntity.setPjGroup(pjGroup);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
