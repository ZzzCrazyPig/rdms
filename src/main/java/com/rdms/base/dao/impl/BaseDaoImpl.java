package com.rdms.base.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.rdms.base.PageBean;
import com.rdms.base.dao.BaseDao;
import com.rdms.util.StringUtil;


/**
 * 通用DAO的实现 给出基本的增删改查接口的实现
 * @author CrazyPig
 *
 * @param <E> 使用泛型
 */
public class BaseDaoImpl<E> implements BaseDao<E> {

	private SessionFactory sessionFactory;
	protected Class<E> clazz;
	
	@Resource
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		this.clazz = (Class<E>) type.getActualTypeArguments()[0];
	}
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	/**
	 * 持久化一个实体对象到数据库中
	 * @param e 实体对象
	 */
	public Serializable save(E e) throws Exception {
		return getSession().save(e);
	}

	/**
	 * 根据指定id在数据库中删除记录
	 * @param id 实体主键
	 */
	public void delete(Object id) throws Exception {
		Object object = getSession().get(clazz, (Serializable) id);
		getSession().delete(object);
	}
	
	/**
	 * 删除指定对象
	 * @param e
	 * @throws Exception
	 */
	public void deleteObject(E e) throws Exception {
		getSession().delete(e);
	}

	/**
	 * 根据给定的实体更新在数据库中的记录
	 * @param e	实体
	 */
	public void update(E e) throws Exception {
		getSession().update(e);
	}
	
	/**
	 * 根据给定的hql执行更新操作
	 * @param hql	hql语句
	 * @param field	需要传递的参数数组(不定长)
	 * @return
	 */
	public int update(String hql, Object...field) throws Exception {
		Query query = getSession().createQuery(hql);
		if(field.length != 0) {
			for(int i = 0; i < field.length; i++) {
				query.setParameter(i, field[i]);
			}
		}
		return query.executeUpdate();
	}

	/**
	 * 根据指定id查找实体
	 * @param id 主键
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public E findById(Object id) throws Exception {
		return (E) getSession().get(clazz, (Serializable) id);
	}

	/**
	 * 根据指定的多个id查找多个实体
	 * @param ids 多个id组成的数组
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<E> findByIds(Object[] ids, boolean isIn) throws Exception {
		if(ids == null || ids.length == 0) {
			return Collections.emptyList();
		}
		if(!isIn) {
			return getSession().createQuery("FROM " + clazz.getSimpleName() + " WHERE id NOT IN (:ids)").setParameterList("ids", ids).list();
		}
		return getSession().createQuery("FROM " + clazz.getSimpleName() + " WHERE id IN (:ids)").setParameterList("ids", ids).list();
	}
	
	/**
	 * 根据指定的id数组批量删除
	 * @param ids
	 */
	public void deleteByIds(Object[] ids) throws Exception {
		if(ids == null || ids.length == 0) return ;
		getSession().createQuery("DELETE FROM " + clazz.getSimpleName() + " WHERE id IN (:ids)").setParameterList("ids", ids).executeUpdate();
	}

	/**
	 * 查找所有的实体
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<E> findAll() throws Exception {
		String hql = "FROM " + clazz.getSimpleName();
		return getSession().createQuery(hql).list();
	}
	
	/**
	 * 根据给定的hql查找满足要求的实体
	 * @param hql 	hql语句
	 * @param field	需要传递的参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<E> find(String hql, Object...field) throws Exception {
		Query query = getSession().createQuery(hql);
		for(int i = 0; i < field.length; i++) {
			query.setParameter(i, field[i]);
		}
		return query.list();
	}

	/**
	 * 返回实体在数据库中对应记录条数
	 * @return
	 */
	public long getTotalCount() throws Exception {
		long totalCount = (Long)getSession().createQuery("SELECT COUNT(*) FROM " + clazz.getSimpleName()).list().get(0);
		return totalCount;
	}
	
	
	/**
	 * 返回满足条件的记录总数
	 * @param hql
	 * @param field
	 * @return
	 */
	public long getTotalCount(String hql, Object[] field) throws Exception {
		Query query = getSession().createQuery(hql);
		for(int i = 0; i < field.length; i++) {
			query.setParameter(i, field[i]);
		}
		return (Long)query.list().get(0);
	}

	/**
	 * 分页查询
	 * @param pageNo	页面下标
	 * @param pageSize	页面数据量
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageBean<E> getPageBean(int offset, int limit) throws Exception {
		PageBean<E> pageBean = new PageBean<E>(offset, limit, getTotalCount());
		Query query = getSession().createQuery("FROM " + clazz.getSimpleName());
		query.setFirstResult(offset);
		query.setMaxResults(offset + limit);
		List<E> beanList = query.list();
		pageBean.setBeanList(beanList);
		return pageBean;
	}
	
	/**
	 * 分页查询
	 * @param offset 偏移值
	 * @param limit	页面数据量
	 * @param orderBy	排序字段
	 * @param asc	是否升序
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageBean<E> getPageBean(int offset, int limit, String orderBy, String asc) throws Exception {
		PageBean<E> pageBean = new PageBean<E>(offset, limit, getTotalCount());
		Query query = null;
		if(!StringUtil.isBlank(orderBy)) {
			query = getSession().createQuery("FROM " + clazz.getSimpleName() + " ORDER BY " + orderBy + " " + asc.toUpperCase());
		} else {
			query = getSession().createQuery("FROM " + clazz.getSimpleName());
		}
		query.setFirstResult(offset);
		query.setMaxResults(offset + limit);
		List<E> beanList = query.list();
		pageBean.setBeanList(beanList);
		return pageBean;
	}
	
	/**
	 * 分页查询
	 * @param pageNo 页面下标
	 * @param pageSize 页面数据量
	 * @param hql
	 * @param field 查询字段
	 * @param orderBy 排序字段
	 * @param asc 是否升序
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageBean<E> getPageBean(int offset, int limit, String hql, Object[] field, String orderBy, String asc) throws Exception {
		String cHql = "SELECT COUNT(*) " + hql;
		int fetchIndex = 0, whereIndex = 0;
		if((fetchIndex = cHql.indexOf("LEFT JOIN FETCH")) > 0) {
			if((whereIndex = cHql.indexOf("WHERE")) > 0) {
				cHql = cHql.substring(0, fetchIndex) + cHql.substring(whereIndex);
			} else {
				cHql = cHql.substring(0, fetchIndex);
			}
		}
		PageBean<E> pageBean = new PageBean<E>(offset, limit, getTotalCount(cHql, field));
		String newHql = hql;
		// 在这里拼接 order by 语句
		if(!StringUtil.isBlank(orderBy)) {
			if(StringUtil.isBlank(asc)) {
				asc = "ASC";
			}
			newHql = newHql + " ORDER BY " + orderBy + " " + asc.toUpperCase();
		}
		Query query = getSession().createQuery(newHql);
		query.setFirstResult(offset);
		query.setMaxResults(offset + limit);
		for(int i = 0; i < field.length; i++) {
			query.setParameter(i, field[i]);
		}
		List<E> beanList = query.list();
		pageBean.setBeanList(beanList);
		return pageBean;
	}
	
	/**
	 * 根据提供的Hql和whereArgs拼接新的Hql语句,查询全部满足的记录
	 * @param hql (适用于带left join fetch这种语法的hql语句)
	 * @param whereArgs where参数
	 * @param orderBy 排序字段
	 * @param asc 是否升序
	 * @return
	 * @throws Exception
	 */
	public List<E> query(Map<String, Object> whereArgs, String orderBy, String asc) throws Exception {
		String hql0 = "FROM " + clazz.getSimpleName();
		return this.query(hql0, whereArgs, orderBy, asc);
	}
	
	public List<E> query(String hql0, Map<String, Object> whereArgs, String orderBy, String asc) throws Exception {
		StringBuffer sb = new StringBuffer(hql0);
		String hql = "", newHql = "";
		Object[] field = null;
		List<E> resultList = null;
		if(whereArgs != null && whereArgs.size() > 0) {
			sb.append(" WHERE ");
			Set<Entry<String, Object>> entrySet = whereArgs.entrySet();
			List<Object> valueList = new ArrayList<Object>();
			for(Entry<String, Object> entry : entrySet) {
				String key = entry.getKey();
				Object value = entry.getValue();
				sb.append(key + "=? AND ");
				valueList.add(value);
			}
			hql = sb.toString();
			hql = hql.substring(0, hql.lastIndexOf("AND"));
			field = new Object[valueList.size()];
			valueList.toArray(field);
		} else {
			hql = sb.toString();
			field = new Object[]{};
		}
		newHql = hql;
		// 在这里拼接 order by 语句
		if(!StringUtil.isBlank(orderBy)) {
			if(StringUtil.isBlank(asc)) {
				asc = "ASC";
			}
			newHql = newHql + " ORDER BY " + orderBy + " " + asc.toUpperCase();
		}
		resultList = this.find(hql, field);
		return resultList;
	}

	/**
	 * 通用分页查询  自动拼接hql语句
	 * @param offset 偏移值
	 * @param limit 页面数据量
	 * @param whereArgs where参数
	 * @param orderBy 排序字段
	 * @param asc 是否升序
	 * @return
	 * @throws Exception
	 */
	public PageBean<E> queryByPage(int offset, int limit,
			Map<String, Object> whereArgs, String orderBy, String asc)
			throws Exception {
//		StringBuffer sb = new StringBuffer("FROM " + clazz.getSimpleName());
//		String hql = "";
//		PageBean<E> pageBean = null;
//		if(whereArgs != null && whereArgs.size() > 0) {
//			sb.append(" WHERE ");
//			Set<Entry<String, Object>> entrySet = whereArgs.entrySet();
//			List<Object> valueList = new ArrayList<Object>();
//			for(Entry<String, Object> entry : entrySet) {
//				String key = entry.getKey();
//				Object value = entry.getValue();
//				sb.append(key + "=? AND ");
//				valueList.add(value);
//			}
//			hql = sb.toString();
//			hql = hql.substring(0, hql.lastIndexOf("AND"));
//			Object[] field = new Object[valueList.size()];
//			valueList.toArray(field);
//			pageBean = this.getPageBean(offset, limit, hql, field, orderBy, asc);
//		} else {
//			hql = sb.toString();
//			pageBean = this.getPageBean(offset, limit, hql, new Object[]{}, orderBy, asc);
//		}
		String hql = "FROM " + clazz.getSimpleName();
		return this.queryByPage(offset, limit, hql, whereArgs, orderBy, asc);
	}
	
	
	/**
	 * 分页查询 根据提供的Hql(适用于带left join fetch这种语法的hql)和whereArgs拼接新的hql语句
	 * @param offset 偏移值
	 * @param limit 页面数据量
	 * @param hql (带left join fetch这种语法的hql)
	 * @param whereArgs where查询参数
	 * @param orderBy 排序字段
	 * @param asc 是否升序
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageBean<E> queryByPage(int offset, int limit, String hql,
			Map<String, Object> whereArgs, String orderBy, String asc)
			throws Exception {
		StringBuffer sb = new StringBuffer(hql);
		String newHql = "";
		PageBean<E> pageBean = null;
		if(whereArgs != null && whereArgs.size() > 0) {
			sb.append(" WHERE ");
			Set<Entry<String, Object>> entrySet = whereArgs.entrySet();
			List<Object> valueList = new ArrayList<Object>();
			for(Entry<String, Object> entry : entrySet) {
				String key = entry.getKey();
				Object value = entry.getValue();
				sb.append(key + "=? AND ");
				valueList.add(value);
			}
			newHql = sb.toString();
			newHql = newHql.substring(0, newHql.lastIndexOf("AND"));
			Object[] field = new Object[valueList.size()];
			valueList.toArray(field);
			pageBean = this.getPageBean(offset, limit, newHql, field, orderBy, asc);
		} else {
			newHql = sb.toString();
			pageBean = this.getPageBean(offset, limit, newHql, new Object[]{}, orderBy, asc);
		}
		return pageBean;
	}
	

}
