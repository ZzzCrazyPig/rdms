package com.rdms.base.action;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.rdms.base.PageBean;
import com.rdms.base.action.model.AppModel;
import com.rdms.base.action.model.BaseModel;
import com.rdms.base.service.BaseService;
import com.rdms.base.vo.AppVO;
import com.rdms.base.vo.ErrorCode;

public abstract class GeneralAction<E, S extends BaseService<E>, M extends BaseModel>
		extends ActionSupport implements ModelDriven<AppModel> {
	
	private static final long serialVersionUID = 1L;
	
	@Resource(name="appVO")
	private AppVO appVO;
	@Resource(name="appModel")
	private AppModel appModel;
	
	private BaseService<E> baseService;
	
	@SuppressWarnings("unchecked")
	protected Class<M> getModelClass() {
		Type type = this.getClass().getGenericSuperclass();
		ParameterizedType p = (ParameterizedType) type;
		Class<M> clazz = (Class<M>) p.getActualTypeArguments()[2];
		return clazz;
	}
	
	private Map<String, Class<?>> getComplexObjOfModel() {
		try {
			Class<M> clazz = this.getModelClass();
			M agent = clazz.newInstance();
			return agent.getComplexObjClassMapOfModel();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private M getInstanceOfM() {
		M instance = null;
		try {
			instance = this.getModelClass().newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return instance;
	}
	

	@SuppressWarnings("unchecked")
	public String insert() {
		AppModel appModel = this.getAppModel();
		M m = (M) appModel.attrToBean(getModelClass(), this.getComplexObjOfModel());
		AppVO appVO = this.getAppVO();
		try {
			E e = this.toEntity(m, null);
			this.baseService.save(e);
			m = (M) this.getInstanceOfM().toModel(e);
			appVO.setSuccess(true);
			appVO.setMsg("添加成功");
			appVO.setRow(m);
		} catch(Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常,添加失败");
			appVO.setErrorCode(ErrorCode.SYS_ERROR);
			return ERROR;
		}
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String update() {
		AppModel appModel = this.getAppModel();
		M m = (M) appModel.attrToBean(getModelClass(), this.getComplexObjOfModel());
		AppVO appVO = this.getAppVO();
		try {
			Object id = m.getId();
			E oldE = this.baseService.findById(id);
			E newE = this.toEntity(m, oldE);
			this.baseService.update(newE);
			m = (M) this.getInstanceOfM().toModel(newE);
			appVO.setSuccess(true);
			appVO.setMsg("更新成功");
			appVO.setRow(m);
		} catch(Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常,更新失败");
			appVO.setErrorCode(ErrorCode.SYS_ERROR);
			return ERROR;
		}
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String delete() {
		AppModel appModel = this.getAppModel();
		M m = (M) appModel.attrToBean(getModelClass(), this.getComplexObjOfModel());
		AppVO appVO = this.getAppVO();
		try {
			Object id = m.getId();
			this.baseService.delete(id);
			appVO.setSuccess(true);
			appVO.setMsg("删除成功");
		} catch(Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常,删除失败");
			appVO.setErrorCode(ErrorCode.SYS_ERROR);
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String multiDelete() {
		AppModel appModel = this.getAppModel();
		String _attr = appModel.getAttr();
		String attr = _attr.substring(1, _attr.length() - 1);
		String[] _ids = attr.split(",");
		String[] ids = new String[_ids.length];
		String delIdsStr = "";
		for(int i = 0; i < _ids.length; i++) {
			String _id = _ids[i];
			ids[i] = _id.substring(1, _id.length() - 1);
			delIdsStr = delIdsStr + ids[i] + ",";
		}
		delIdsStr = delIdsStr.substring(0, delIdsStr.length() - 1);
		AppVO appVO = this.getAppVO();
		try {
			this.baseService.deleteByIds(ids);
			appVO.setSuccess(true);
			appVO.setMsg("成功删除" + ids.length + "条数据");
			appVO.setRow(delIdsStr);
		} catch (Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常,删除失败");
			return ERROR;
		}
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String query() {
		AppModel appModel = this.getAppModel();
		M m = (M) appModel.attrToBean(getModelClass(), this.getComplexObjOfModel());
		M agent = this.getInstanceOfM();
		String orderBy = appModel.getSort();
		String order = appModel.getOrder();
		AppVO appVO = this.getAppVO();
		try {
			List<E> beanList = this.baseService.query(m, orderBy, order);
			appVO.setSuccess(true);
			appVO.setMsg("查询成功");
			for(E bean : beanList) {
				appVO.addRow(agent.toModel(bean));
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
	
	@SuppressWarnings("unchecked")
	public String queryByPage() {
		AppModel appModel = this.getAppModel();
		int offset = appModel.getOffset();
		int limit = appModel.getLimit();
		String orderBy = appModel.getSort();
		String order = appModel.getOrder();
		M m = (M) appModel.attrToBean(getModelClass(), this.getComplexObjOfModel());
		M agent = this.getInstanceOfM();
		PageBean<E> pageBean = null;
		AppVO appVO = this.getAppVO();
		try {
			pageBean = this.baseService.queryByPage(offset, limit, m, orderBy, order);
			List<E> beanList = pageBean.getBeanList();
			for(E bean : beanList) {
				m = (M) agent.toModel(bean);
				appVO.addRow(m);
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
	
	protected abstract E toEntity(M model, E entity) throws Exception;

	public AppVO getAppVO() {
		return appVO;
	}

	public void setAppVO(AppVO appVO) {
		this.appVO = appVO;
	}
	
	@Override
	public AppModel getModel() {
		return getAppModel();
	}

	public AppModel getAppModel() {
		return appModel;
	}

	public void setAppModel(AppModel appModel) {
		this.appModel = appModel;
	}

	public BaseService<E> getBaseService() {
		return baseService;
	}

	public void setBaseService(BaseService<E> baseService) {
		this.baseService = baseService;
	}
	
}
