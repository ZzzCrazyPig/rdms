package com.rdms.base.service;

import java.io.Serializable;
import java.util.List;

import com.rdms.base.PageBean;
import com.rdms.base.action.model.BaseModel;


/**
 * 通用Service 给出基本的增删改查接口
 * @author CrazyPig
 *
 * @param <E>
 */
public interface BaseService<E> {

	public Serializable save(E e) throws Exception;
	
	public void delete(Object id) throws Exception;
	
	public void deleteObject(E e) throws Exception;
	
	public void deleteByIds(Object[] ids) throws Exception;
	
	public void update(E e) throws Exception;
	
	public int update(String hql, Object...field) throws Exception;
	
	public E findById(Object id) throws Exception;
	
	public List<E> findByIds(Object[] ids, boolean isIn) throws Exception;
	
	public List<E> find(String hql, Object...field) throws Exception;
	
	public List<E> findAll() throws Exception;
	
	public long getTotalCount() throws Exception;
	
	public long getTotalCount(String hql, Object[] field) throws Exception;
	
	/**
	 * 最基本的分页
	 * @param offset 偏移量
	 * @param limit 页面数据量
	 * @return
	 */
	public PageBean<E> getPageBean(int offset, int limit) throws Exception;
	
	/**
	 * 最基本的分页排序
	 * @param offset 偏移量
	 * @param limit 页面数据量
	 * @param orderBy 排序字段
	 * @param asc 升序或降序
	 * @return
	 */
	public PageBean<E> getPageBean(int offset, int limit, String orderBy, String asc) throws Exception;
	
	/**
	 * 分页排序,自定义hql和查询参数
	 * @param offset 偏移量
	 * @param limit 页面数据量
	 * @param hql 自定义的hql语句
	 * @param field 查询字段值
	 * @param orderBy 排序字段
	 * @param asc 升序或降序
	 * @return
	 */
	public PageBean<E> getPageBean(int offset, int limit, String hql, Object[] field, String orderBy, String asc) throws Exception;
	
	/**
	 * 根据条件查询全部
	 * @param model baseModel类,所有model类必须继承该类,并实现返回所有字段的方法(getModelFields)
	 * @param orderBy 排序字段
	 * @param asc 升序或降序
	 * @return
	 * @throws Exception
	 */
	public List<E> query(BaseModel model, String orderBy, String asc) throws Exception;
	
	/**
	 * 通用分页排序,根据model类得到查询参数,自行拼接hql
	 * @param offset 偏移量
	 * @param limit  页面数据量
	 * @param model  baseModel类,所有model类必须继承该类,并且实现返回所有字段的方法(getModelFields)
	 * @param orderBy 排序字段
	 * @param asc  升序或降序
	 * @return
	 * @throws Exception
	 */
	public PageBean<E> queryByPage(int offset, int limit, BaseModel model, String orderBy, String asc) throws Exception;
}
