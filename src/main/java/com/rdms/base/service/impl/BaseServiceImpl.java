package com.rdms.base.service.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.DocumentException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rdms.base.PageBean;
import com.rdms.base.action.model.BaseModel;
import com.rdms.base.dao.BaseDao;
import com.rdms.base.service.BaseService;
import com.rdms.util.StringUtil;


@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class BaseServiceImpl<E> implements BaseService<E>{

	private BaseDao<E> baseDao;
	
	public void setBaseDAO(BaseDao<E> baseDao) {
		this.baseDao = baseDao;
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public Serializable save(E e) throws Exception {
		return baseDao.save(e);
	}

	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void delete(Object id) throws Exception {
		baseDao.delete(id);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void deleteObject(E e) throws Exception {
		baseDao.deleteObject(e);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void deleteByIds(Object[] ids) throws Exception {
		baseDao.deleteByIds(ids);
	}

	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void update(E e) throws Exception {
		baseDao.update(e);
	}

	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public int update(String hql, Object... field) throws Exception {
		return baseDao.update(hql, field);
	}

	public E findById(Object id) throws Exception {
		return baseDao.findById(id);
	}

	public List<E> findByIds(Object[] ids, boolean isIn) throws Exception {
		return baseDao.findByIds(ids, isIn);
	}
	
	public List<E> find(String hql, Object...field) throws Exception {
		return baseDao.find(hql, field);
	}

	public List<E> findAll() throws Exception {
		return baseDao.findAll();
	}

	public long getTotalCount() throws Exception {
		return baseDao.getTotalCount();
	}
	
	public long getTotalCount(String hql, Object[] field) throws Exception {
		return baseDao.getTotalCount(hql, field);
	}

	public PageBean<E> getPageBean(int offset, int limit) throws Exception {
		return baseDao.getPageBean(offset, limit);
	}
	
	public PageBean<E> getPageBean(int offset, int limit, String orderBy, String asc) throws Exception {
		return baseDao.getPageBean(offset, limit, orderBy, asc);
	}

	public PageBean<E> getPageBean(int offset, int limit, String hql,
			Object[] field, String orderBy, String asc) throws Exception {
		return baseDao.getPageBean(offset, limit, hql, field, orderBy, asc);
	}
	
	public List<E> query(BaseModel model, String orderBy, String asc) throws Exception {
		Map<String, Object> whereArgs = this.getWhereArgs(model);
		return baseDao.query(whereArgs, orderBy, asc);
	}

	public PageBean<E> queryByPage(int offset, int limit,
			BaseModel model, String orderBy, String asc)
			throws Exception {
		Map<String, Object> whereArgs = this.getWhereArgs(model);
		return baseDao.queryByPage(offset, limit, whereArgs, orderBy, asc);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String, Object> getWhereArgs(BaseModel model) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, DocumentException {
		Map<String, Object> whereArgs = new HashMap<String, Object>();
		if (model != null) {
			Class clazz = model.getClass();
			// get all the field map (modelField <-> entityField)
			Map<String, String> fieldMap = model.getFieldMap();
			Set<String> modelFieldSet = fieldMap.keySet();
			for(String modelField : modelFieldSet) {
				// use the reflect to get the value and test if the value is blank
				String methodName = "get" + new String("" + modelField.charAt(0)).toUpperCase()
						+ modelField.substring(1);
				Method method = clazz.getMethod(methodName, new Class[] {});
				Object value = method.invoke(model, new Object[] {});
				// test if the value is blank
				if (value != null) {
					if (!StringUtil.isBlank(value.toString())) {
						// if the value is not blank, save it
						whereArgs.put(fieldMap.get(modelField), value);
					}
				}
			}
			
			
//			// get all the model field
//			String[] entityFields = model.getModelFields();
//			// use the reflect to get the value and test if the value is blank
//			for (int i = 0; i < entityFields.length; i++) {
//				String key = entityFields[i];
//				String methodName = "get"
//						+ new String("" + key.charAt(0)).toUpperCase()
//						+ key.substring(1);
//				Method method = clazz.getMethod(methodName, new Class[] {});
//				Object value = method.invoke(model, new Object[] {});
//				// test if the value is blank
//				if (value != null) {
//					if (!StringUtil.isBlank(value.toString())) {
//						// if the value is not blank, save it
//						whereArgs.put(key, value);
//					}
//				}
//			}
		}
		return whereArgs;
	}


}
