package com.rdms.base.action.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.rdms.util.StringUtil;

@Component("appModel")
@Scope("prototype")
public class AppModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer offset;
	private Integer limit;
	
	// the easyui pagination param
	private Integer page = 1;
	private Integer rows;
	
	private String sort;
	private String order;
	
	private String attr;
	
	public Object attrToBean(Class<?> beanClass) {
		if(StringUtil.isBlank(attr)) return null;
		JSONObject jsonObj = JSONObject.fromObject(attr);
		System.out.println(jsonObj.toString());
		// 这里需要统一前台和后台的日期处理格式(前台必须约定好这种格式)
		String[] dateFormats = new String[] {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"};
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats));
		return JSONObject.toBean(jsonObj, beanClass);
	}
	
	@SuppressWarnings("rawtypes")
	public Object attrToBean(Class beanClass, Map classMap) {
		if(StringUtil.isBlank(attr)) return null;
		JSONObject jsonObj = JSONObject.fromObject(attr);
		System.out.println(jsonObj.toString());
		// 这里需要统一前台和后台的日期处理格式(前台必须约定好这种格式)
		String[] dateFormats = new String[] {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"};
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats));
		if(classMap == null || classMap.size() == 0) {
			return JSONObject.toBean(jsonObj, beanClass);
		}
		Object bean = JSONObject.toBean(jsonObj, beanClass, classMap);
		return bean;
	}
	
	public JSONObject attrToJSONObject() {
		if(StringUtils.isBlank(attr)) return null;
		JSONObject jsonObj = JSONObject.fromObject(attr);
		return jsonObj;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Object> attrToBeanList(Class beanClass) {
		if(StringUtil.isBlank(attr)) return null;
		JSONArray jsonArr = JSONArray.fromObject(attr);
		String[] dateFormats = new String[] {"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd"};
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats));
		return JSONArray.toList(jsonArr, beanClass);
	}

	public Integer getOffset() {
		// bootstrapTable所用到的分页参数为 offset limit
		if(this.offset != null) {
			return offset;
		}
		// easyui 分页所用到的参数为 page rows
		this.offset = (this.getPage() - 1) * this.getRows();
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getLimit() {
		if(this.limit != null) return this.limit;
		limit = this.getRows();
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getAttr() {
		return attr;
	}

	public void setAttr(String attr) {
		this.attr = attr;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

}
