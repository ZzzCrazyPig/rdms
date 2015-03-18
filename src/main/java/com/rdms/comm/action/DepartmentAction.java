package com.rdms.comm.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.rdms.base.PageBean;
import com.rdms.base.action.BaseAction;
import com.rdms.base.action.model.AppModel;
import com.rdms.base.vo.AppVO;
import com.rdms.comm.action.model.DepartmentModel;
import com.rdms.comm.domain.Department;
import com.rdms.comm.service.DepartmentService;

@Controller("departmentAction")
@Scope("prototype")
public class DepartmentAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	@Resource(name="departmentService")
	private DepartmentService departmentService;

	// 添加部门
	@Override
	public String insert() {
		AppModel appModel = this.getAppModel();
		DepartmentModel deptModel = (DepartmentModel)appModel.attrToBean(DepartmentModel.class);
		String deptCode = deptModel.getDeptCode();
		String name = deptModel.getName();
		AppVO appVO = this.getAppVO();
		try {
			if(this.departmentService.isAvailable(name, deptCode)) {
				Department entity = (Department) this.toEntity(deptModel, null);
				this.departmentService.save(entity);
//				deptModel = (DepartmentModel) this.toModel(entity);
				deptModel = Department.toModel(entity);
				appVO.setSuccess(true);
				appVO.setMsg("添加成功");
				appVO.setRow(deptModel);
			} else {
				appVO.setSuccess(false);
				appVO.setMsg("部门命名或者部门代码已经被使用过!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常");
			return ERROR;
		}
		return SUCCESS;
	}

	// 更新
	@Override
	public String update() {
		AppModel appModel = this.getAppModel();
		DepartmentModel deptModel = (DepartmentModel) appModel.attrToBean(DepartmentModel.class);
		AppVO appVO = this.getAppVO();
		try {
			Department entity = this.departmentService.findById(deptModel.getId());
//			entity.setDeptCode(deptModel.getDeptCode());
//			entity.setName(deptModel.getName());
//			entity.setMemTotal(deptModel.getMemTotal());
			entity = (Department) this.toEntity(deptModel, entity);
			this.departmentService.update(entity);
//			deptModel = (DepartmentModel) this.toModel(entity);
			deptModel = Department.toModel(entity);
			appVO.setSuccess(true);
			appVO.setMsg("更新成功");
			appVO.setRow(deptModel);
		} catch(Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常");
			return ERROR;
		}
		return SUCCESS;
	}

	// 删除一条
	@Override
	public String delete() {
		AppModel appModel = this.getAppModel();
		DepartmentModel deptModel = (DepartmentModel) appModel.attrToBean(DepartmentModel.class);
		AppVO appVO = this.getAppVO();
		try {
			this.departmentService.delete(deptModel.getId());
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

	
	// 批量删除
	@Override
	public String multiDelete() {
		AppModel appModel = this.getAppModel();
		String attr = appModel.getAttr();
		String[] ids = attr.split(",");
		AppVO appVO = this.getAppVO();
		try {
			this.departmentService.deleteByIds(ids);
			appVO.setSuccess(true);
			appVO.setMsg("成功删除" + ids.length + "条数据");
			appVO.setRow(attr);
		} catch(Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常");
			return ERROR;
		}
		return SUCCESS;
	}

	// 查询全部
	@Override
	public String query() {
		AppVO appVO = this.getAppVO();
		try {
			List<Department> beanList = this.departmentService.findAll();
			appVO.setTotal(beanList.size());
			for(Department bean : beanList) {
//				DepartmentModel deptModel = (DepartmentModel) this.toModel(bean);
				DepartmentModel deptModel = Department.toModel(bean);
				appVO.addRow(deptModel);
			}
			appVO.setSuccess(true);
			appVO.setMsg("查询成功");
		} catch(Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常");
			return ERROR;
		}
		return SUCCESS;
	}

	// 分页查询
	@Override
	public String queryByPage() {
		AppModel appModel = this.getAppModel();
		int offset = appModel.getOffset();
		int limit = appModel.getLimit();
		String orderBy = appModel.getSort();
		String order = appModel.getOrder();
		DepartmentModel deptModel = (DepartmentModel) appModel.attrToBean(DepartmentModel.class);
		PageBean<Department> pageBean = null;
		AppVO appVO = this.getAppVO();
		try {
			pageBean = this.departmentService.queryByPage(offset, limit, deptModel, orderBy, order);
			List<Department> beanList = pageBean.getBeanList();
			for(Department bean : beanList) {
				deptModel = Department.toModel(bean);
				appVO.addRow(deptModel);
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
	public Object toEntity(Object model, Object entity) {
		DepartmentModel deptModel = (DepartmentModel) model;
		Department deptEntity = null;
		if(entity == null) {
			deptEntity = new Department();
		} else {
			deptEntity = (Department) entity;
		}
		deptEntity.setName(deptModel.getName());
		deptEntity.setDeptCode(deptModel.getDeptCode());
		deptEntity.setMemTotal(deptModel.getMemTotal());
		return deptEntity;
	}

//	@Override
//	public Object toModel(Object entity) throws Exception {
//		Department dept = (Department) entity;
//		DepartmentModel model = new DepartmentModel();
//		model.setId(dept.getId());
//		model.setName(dept.getName());
//		model.setDeptCode(dept.getDeptCode());
//		model.setMemTotal(dept.getMemTotal());
//		return model;
//	}

}
