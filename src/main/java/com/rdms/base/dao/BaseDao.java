package com.rdms.base.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.rdms.base.PageBean;


/**
 * 通用DAO 给出基本的增删改查接口
 * @author CrazyPig
 *
 * @param <E> 使用泛型
 */
public interface BaseDao<E> {
	
	/**
	 * 持久化一个实体对象到数据库中
	 * @param e 实体对象
	 */
	public Serializable save(E e) throws Exception;
	
	/**
	 * 根据指定id在数据库中删除记录
	 * @param id 实体主键
	 */
	public void delete(Object id) throws Exception;
	
	/**
	 * 删除指定对象
	 * @param e
	 * @throws Exception
	 */
	public void deleteObject(E e) throws Exception;
	
	/**
	 * 根据指定的id数组批量删除
	 * @param ids
	 */
	public void deleteByIds(Object[] ids) throws Exception;
	
	/**
	 * 根据给定的实体更新在数据库中的记录
	 * @param e	实体
	 */
	public void update(E e) throws Exception;
	
	/**
	 * 根据给定的hql执行更新操作
	 * @param hql	hql语句
	 * @param field	需要传递的参数数组(不定长)
	 * @return
	 */
	public int update(String hql, Object...field) throws Exception;
	
	/**
	 * 根据指定id查找实体
	 * @param id 主键
	 * @return
	 */
	public E findById(Object id) throws Exception;
	
	/**
	 * 根据指定的多个id查找多个实体
	 * @param ids 多个id组成的数组
	 * @return
	 */
	public List<E> findByIds(Object[] ids, boolean isIn) throws Exception;
	
	
	/**
	 * 查找所有的实体
	 * @return
	 */
	public List<E> findAll() throws Exception;
	
	/**
	 * 根据给定的hql查找满足要求的实体
	 * @param hql 	hql语句
	 * @param field	需要传递的参数
	 * @return
	 */
	public List<E> find(String hql, Object...field) throws Exception;
	
	/**
	 * 返回实体在数据库中对应记录条数
	 * @return
	 */
	public long getTotalCount() throws Exception;
	
	
	/**
	 * 返回满足条件的记录总数
	 * @param hql
	 * @param field
	 * @return
	 */
	public long getTotalCount(String hql, Object[] field) throws Exception;
	
	
	/**
	 * 分页查询
	 * @param offset	偏移值
	 * @param limit	页面数据量
	 * @return
	 */
	public PageBean<E> getPageBean(int offset, int limit) throws Exception;
	
	/**
	 * 分页查询
	 * @param offset 偏移值
	 * @param limit	页面数据量
	 * @param orderBy	排序字段
	 * @param asc	是否升序
	 * @return
	 */
	public PageBean<E> getPageBean(int offset, int limit, String orderBy, String asc) throws Exception;
	
	/**
	 * 分页查询
	 * @param offset 偏移值
	 * @param limit 页面数据量
	 * @param hql
	 * @param field 查询字段
	 * @param orderBy 排序字段
	 * @param asc 是否升序
	 * @return
	 */
	public PageBean<E> getPageBean(int offset, int limit, String hql, Object[] field, String orderBy, String asc) throws Exception;
	
	/**
	 * 根据条件查询全部
	 * @param whereArgs where参数
	 * @param orderBy 排序字段
	 * @param asc 升序或降序
	 * @return
	 * @throws Exception
	 */
	public List<E> query(Map<String, Object> whereArgs, String orderBy, String asc) throws Exception;
	
	/**
	 * 根据提供的Hql和whereArgs拼接新的Hql语句,查询全部满足的记录
	 * @param hql (适用于带left join fetch这种语法的hql语句)
	 * @param whereArgs where参数
	 * @param orderBy 排序字段
	 * @param asc 是否升序
	 * @return
	 * @throws Exception
	 */
	public List<E> query(String hql0, Map<String, Object> whereArgs, String orderBy, String asc) throws Exception;
	
	/**
	 * 分页查询  自动拼接hql语句
	 * @param offset 偏移值
	 * @param limit 页面数据量
	 * @param whereArgs where参数
	 * @param orderBy 排序字段
	 * @param asc 是否升序
	 * @return
	 * @throws Exception
	 */
	public PageBean<E> queryByPage(int offset, int limit, Map<String, Object> whereArgs, String orderBy, String asc) throws Exception;
	
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
	public PageBean<E> queryByPage(int offset, int limit, String hql, Map<String, Object> whereArgs, String orderBy, String asc) throws Exception;
}
