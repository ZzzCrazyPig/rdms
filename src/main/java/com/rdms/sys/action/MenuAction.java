package com.rdms.sys.action;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.rdms.base.PageBean;
import com.rdms.base.action.BaseAction;
import com.rdms.base.action.model.AppModel;
import com.rdms.base.action.model.ComboTreeModel;
import com.rdms.base.action.model.TreeModel;
import com.rdms.base.vo.AppVO;
import com.rdms.sys.action.model.MenuModel;
import com.rdms.sys.domain.Menu;
import com.rdms.sys.domain.Page;
import com.rdms.sys.domain.User;
import com.rdms.sys.service.MenuService;
import com.rdms.sys.service.PageService;
import com.rdms.util.JsonDateValueProcessor;
import com.rdms.util.StringUtil;

@Controller("menuAction")
@Scope("prototype")
public class MenuAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	@Resource(name="menuService")
	private MenuService menuService;
	@Resource(name="pageService")
	private PageService pageService;

	@Override
	public String insert() {
		AppModel appModel = this.getAppModel();
		MenuModel menuModel = (MenuModel) appModel.attrToBean(MenuModel.class, MenuModel.getClassMap());
		AppVO appVO = this.getAppVO();
		try {
			if(this.menuService.isAvailable(menuModel.getCode())) {
				Menu entity = (Menu) this.toEntity(menuModel, null);
				this.menuService.save(entity);
//				menuModel = (MenuModel) this.toModel(entity);
				menuModel = MenuModel.toModel(entity);
				appVO.setSuccess(true);
				appVO.setMsg("添加成功");
				appVO.setRow(menuModel);
			} else {
				appVO.setSuccess(false);
				appVO.setMsg("CODE已被使用");
			}
		} catch(Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常,添加失败");
			return ERROR;
		}
		return SUCCESS;
	}

	@Override
	public String update() {
		AppModel appModel = this.getAppModel();
		AppVO appVO = this.getAppVO();
		try {
			MenuModel menuModel = (MenuModel) appModel.attrToBean(MenuModel.class, MenuModel.getClassMap());
			Menu entity = this.menuService.findById(menuModel.getId());
			Menu newEntity = (Menu) this.toEntity(menuModel, entity);
			newEntity.setId(menuModel.getId());
			this.menuService.update(newEntity);
			appVO.setSuccess(true);
			appVO.setMsg("更新成功");
//			menuModel = (MenuModel) this.toModel(newEntity);
			menuModel = MenuModel.toModel(newEntity);
			JSONObject jsonModel = menuModel.toJSONObject();
			appVO.setRow(jsonModel);
		} catch(Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常,更新失败");
			return ERROR;
		}
		return SUCCESS;
	}

	@Override
	public String delete() {
		AppModel appModel = this.getAppModel();
		MenuModel menuModel = (MenuModel) appModel.attrToBean(MenuModel.class, MenuModel.getClassMap());
		AppVO appVO = this.getAppVO();
		try {
			this.menuService.delete(menuModel.getId());
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
			this.menuService.deleteByIds(ids);
			appVO.setSuccess(true);
			appVO.setMsg("成功删除" + ids.length + "条数据");
			appVO.setRow(attr);
		} catch(Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常,查询失败");
			return ERROR;
		}
		return SUCCESS;
	}

	@Override
	public String query() {
		AppVO appVO = this.getAppVO();
		try {
			List<Menu> menuList = this.menuService.findAll();
			for(Menu menu : menuList) {
//				MenuModel menuModel = (MenuModel) this.toModel(menu);
				MenuModel menuModel = MenuModel.toModel(menu);
				appVO.addRow(menuModel);
			}
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
	public String queryByPage() {
		AppModel appModel = this.getAppModel();
		int offset = appModel.getOffset();
		int limit = appModel.getLimit();
		String orderBy = appModel.getSort();
		String order = appModel.getOrder();
		MenuModel menuModel = (MenuModel) appModel.attrToBean(MenuModel.class, MenuModel.getClassMap());
		PageBean<Menu> pageBean = null;
		AppVO appVO = this.getAppVO();
		try {
			pageBean = this.menuService.queryByPage(offset, limit, menuModel, orderBy, order);
			List<Menu> beanList = pageBean.getBeanList();
			for(Menu bean : beanList) {
//				menuModel = (MenuModel) this.toModel(bean);
				menuModel = MenuModel.toModel(bean);
				appVO.addRow(menuModel);
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
	
	public String queryMenuTree() {
		AppVO appVO = this.getAppVO();
		try {
			List<Menu> beanList = this.menuService.queryTree("sortIndex", "asc");
			for(Menu bean : beanList) {
				TreeModel treeModel = (TreeModel) this.toTreeModel(bean);
				JSONObject jsonModel = treeModel.toJSONObject();
				appVO.addRow(jsonModel);
			}
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
	
	public String queryComboTree() {
		AppVO appVO = this.getAppVO();
		try {
			List<Menu> beanList = this.menuService.queryTree("sortIndex", "asc");
			for(Menu bean : beanList) {
				ComboTreeModel comboTreeModel = (ComboTreeModel) this.toComboTreeModel(bean);
				JSONObject jsonModel = comboTreeModel.toJSONObject();
				appVO.addRow(jsonModel);
			}
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
	
	public String queryTree() {
		AppModel appModel = this.getAppModel();
		MenuModel menuModel = (MenuModel) appModel.attrToBean(MenuModel.class, MenuModel.getClassMap());
		AppVO appVO = this.getAppVO();
		try {
			List<Menu> beanList = this.menuService.queryTree("sortIndex", "asc");
			for(Menu bean : beanList) {
//				menuModel = (MenuModel) this.toModel(bean);
				menuModel = MenuModel.toModel(bean);
				JSONObject jsonModel = menuModel.toJSONObject();
//				System.out.println(jsonModel);
				appVO.addRow(jsonModel);
			}
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
	
	public String queryTreeByPage() {
		AppModel appModel = this.getAppModel();
		int offset = appModel.getOffset();
		int limit = appModel.getLimit();
		String orderBy = appModel.getSort();
		String order = appModel.getOrder();
		MenuModel menuModel = (MenuModel) appModel.attrToBean(MenuModel.class, MenuModel.getClassMap());
		if(StringUtil.isBlank(orderBy)) {
			orderBy = "sortIndex";
		}
		if(StringUtil.isBlank(order)) {
			order = "asc";
		}
		PageBean<Menu> pageBean = null;
		AppVO appVO = this.getAppVO();
		try {
			pageBean = this.menuService.queryTreeByPage(offset, limit, orderBy, order);
			List<Menu> beanList = pageBean.getBeanList();
			for(Menu bean : beanList) {
//				menuModel = (MenuModel) this.toModel(bean);
				menuModel = MenuModel.toModel(bean);
				JSONObject jsonModel = menuModel.toJSONObject();
//				System.out.println(jsonModel);
				appVO.addRow(jsonModel);
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
		MenuModel menuModel = (MenuModel) model;
		Menu menuEntity = null;
		if(entity == null) {
			menuEntity = new Menu();
		} else {
			menuEntity = (Menu) entity;
		}
		menuEntity.setName(menuModel.getName());
		menuEntity.setCode(menuModel.getCode());
		menuEntity.setSortIndex(menuModel.getSortIndex());
		String parentId = menuModel.getParentId();
		if(!StringUtil.isBlank(parentId)) {
			Menu parent = this.menuService.findById(parentId);
			menuEntity.setParent(parent);
		}
		String pageCode = menuModel.getPageCode();
		Page page = null;
		if(!StringUtil.isBlank(pageCode)) {
			page = this.pageService.findByCode(pageCode);
			menuEntity.setPage(page);
		}
		ActionContext ctx = ActionContext.getContext();
		User user = (User)ctx.getSession().get("user");
		menuEntity.setCreateUser(user.getAccount());
		menuEntity.setCreateTime(new Date());
		return menuEntity;
	}
	
	public JSONArray toJSONArray(List<MenuModel> modelList) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONArray jsonArr = JSONArray.fromObject(modelList, jsonConfig);
		for(int i = 0, n = jsonArr.size(); i < n; i++) {
			JSONObject jsonObj = jsonArr.getJSONObject(i);
			JSONArray children = (JSONArray) jsonObj.get("children");
			if(children == null || children.size() == 0) {
				jsonObj.remove("children");
				jsonObj.remove("state");
			}
		}
		return jsonArr;
	}
	
	
//	public JSONObject toJSONObject(Object model) {
//		JsonConfig jsonConfig = new JsonConfig();
//		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
//		JSONObject jsonObj = JSONObject.fromObject(model, jsonConfig);
//		jsonObj = this.processNoChildren(jsonObj);
//		return jsonObj;
//	}
	
//	public JSONObject processNoChildren(JSONObject jsonObj) {
//		JSONArray children = (JSONArray) jsonObj.get("children");
//		if(children == null || children.size() == 0) {
//			jsonObj.remove("children");
//			jsonObj.remove("state");
//			return jsonObj;
//		}
//		for(int i = 0, n = children.size(); i < n; i++) {
//			JSONObject child = children.getJSONObject(i);
//			this.processNoChildren(child);
//		}
//		return jsonObj;
//	}
	
	public Object toTreeModel(Object entity) {
		Menu menuEntity = (Menu) entity;
		TreeModel treeModel = new TreeModel();
		treeModel.setId(menuEntity.getId());
		treeModel.setText(menuEntity.getName());
		if(menuEntity.getPage() != null) {
			treeModel.setHref(menuEntity.getPage().getUrl());
		}
		if(menuEntity.hasChildren()) {
			Set<Menu> children = menuEntity.getChildren();
			for(Menu child : children) {
				TreeModel childModel = (TreeModel) this.toTreeModel(child);
				treeModel.addChild(childModel);
			}
		}
		return treeModel;
	}
	
	public Object toComboTreeModel(Object entity) {
		Menu menuEntity = (Menu) entity;
		ComboTreeModel comboTreeModel = new ComboTreeModel();
		comboTreeModel.setId(menuEntity.getId());
		comboTreeModel.setText(menuEntity.getName());
		// 处理子菜单
		if (menuEntity.hasChildren()) {
			Set<Menu> children = menuEntity.getChildren();
			for (Menu child : children) {
				ComboTreeModel childModel = (ComboTreeModel) this.toComboTreeModel(child);
				comboTreeModel.addChild(childModel);
			}
		}
		return comboTreeModel;
	}

//	@Override
//	public Object toModel(Object entity) throws Exception {
//		Menu menuEntity = (Menu) entity;
//		MenuModel model = new MenuModel();
//		model.setId(menuEntity.getId());
//		model.setCode(menuEntity.getCode());
//		model.setName(menuEntity.getName());
//		model.setSortIndex(menuEntity.getSortIndex());
//		model.setCreateUser(menuEntity.getCreateUser());
//		model.setCreateTime(menuEntity.getCreateTime());
//		Menu parent = menuEntity.getParent();
//		if(parent != null) {
//			model.setParentId(menuEntity.getParent().getId());
//			model.setParentCode(menuEntity.getParent().getCode());
//		}
//		Page page = menuEntity.getPage();
//		if(page != null) {
//			model.setPageId(menuEntity.getPage().getId());
//			model.setPageCode(menuEntity.getPage().getCode());
//		}
//		// 处理子菜单
//		if(menuEntity.hasChildren()) {
//			Set<Menu> children = menuEntity.getChildren();
//			for(Menu child : children) {
//				MenuModel childModel = (MenuModel) this.toModel(child);
//				model.addChild(childModel);
//			}
//		}
//		
//		return model;
//	}

}
