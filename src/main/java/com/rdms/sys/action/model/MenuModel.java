package com.rdms.sys.action.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.rdms.base.action.model.BaseModel;
import com.rdms.sys.domain.Menu;
import com.rdms.sys.domain.Page;
import com.rdms.util.JsonDateValueProcessor;

@Component("menuModel")
@Scope("prototype")
public class MenuModel extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String code;
	private String name;
	private Integer sortIndex;
	private Date createTime;
	private String createUser;
	
	private String parentId;
	private String parentCode;
	private String pageId;
	private String pageCode;
	
	private String state = "closed";
	
	private Set<MenuModel> children = null;
	
	@Override
	@JSON(serialize = false)
	public Map<String, Class<?>> getComplexObjClassMapOfModel() {
		return getClassMap();
	}
	
	public static Map<String, Class<?>> getClassMap() {
		return null;
	}
	
	@Override
	public BaseModel toModel(Object e) {
		if(e == null) return new MenuModel();
		Menu entity = (Menu) e;
		return toModel(entity);
	}
	
	public static MenuModel toModel(Menu entity) {
		Menu menuEntity = entity;
		MenuModel model = new MenuModel();
		model.setId(menuEntity.getId());
		model.setCode(menuEntity.getCode());
		model.setName(menuEntity.getName());
		model.setSortIndex(menuEntity.getSortIndex());
		model.setCreateUser(menuEntity.getCreateUser());
		model.setCreateTime(menuEntity.getCreateTime());
		Menu parent = menuEntity.getParent();
		if(parent != null) {
			model.setParentId(menuEntity.getParent().getId());
			model.setParentCode(menuEntity.getParent().getCode());
		}
		Page page = menuEntity.getPage();
		if(page != null) {
			model.setPageId(menuEntity.getPage().getId());
			model.setPageCode(menuEntity.getPage().getCode());
		}
		// 处理子菜单
		if(menuEntity.hasChildren()) {
			Set<Menu> children = menuEntity.getChildren();
			for(Menu child : children) {
				MenuModel childModel = toModel(child);
				model.addChild(childModel);
			}
		}
		
		return model;
	}
	
	public void addChild(MenuModel childModel) {
		if(children == null) {
			children = new HashSet<MenuModel>();
		}
		this.getChildren().add(childModel);
	}
	
	public JSONObject toJSONObject() {
		MenuModel model = this;
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONObject jsonObj = JSONObject.fromObject(model, jsonConfig);
		jsonObj = this.processNoChildren(jsonObj);
		return jsonObj;
	}
	
	private JSONObject processNoChildren(JSONObject jsonObj) {
		JSONArray children = (JSONArray) jsonObj.get("children");
		if(children == null || children.size() == 0) {
			jsonObj.remove("children");
			jsonObj.remove("state");
			return jsonObj;
		}
		for(int i = 0, n = children.size(); i < n; i++) {
			JSONObject child = children.getJSONObject(i);
			this.processNoChildren(child);
		}
		return jsonObj;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(Integer sortIndex) {
		this.sortIndex = sortIndex;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getPageCode() {
		return pageCode;
	}

	public void setPageCode(String pageCode) {
		this.pageCode = pageCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Set<MenuModel> getChildren() {
		return children;
	}

	public void setChildren(Set<MenuModel> children) {
		this.children = children;
	}

}
