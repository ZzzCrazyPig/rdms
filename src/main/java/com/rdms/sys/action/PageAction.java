package com.rdms.sys.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.rdms.base.action.GeneralAction;
import com.rdms.base.action.model.AppModel;
import com.rdms.base.vo.AppVO;
import com.rdms.sys.action.model.MenuModel;
import com.rdms.sys.action.model.PageModel;
import com.rdms.sys.domain.Menu;
import com.rdms.sys.domain.Page;
import com.rdms.sys.domain.User;
import com.rdms.sys.service.MenuService;
import com.rdms.sys.service.PageService;

@Controller("pageAction")
@Scope("prototype")
public class PageAction extends GeneralAction<Page, PageService, PageModel> {

	private static final long serialVersionUID = 1L;
	
	private PageService pageService;
	@Resource(name="menuService")
	private MenuService menuService;
	
	@Resource(name="pageService")
	public void setPageService(PageService pageService) {
		super.setBaseService(pageService);
		this.pageService = pageService;
	}

	// 添加页面
	@Override
	public String insert() {
		AppModel appModel = this.getAppModel();
		PageModel pageModel = (PageModel) appModel.attrToBean(PageModel.class, PageModel.getClassMap());
		AppVO appVO = this.getAppVO();
		try {
			if(this.pageService.isAvailable(pageModel.getCode())) {
				Page page = (Page) this.toEntity(pageModel, null);
				this.pageService.save(page);
//				pageModel = (PageModel) this.toModel(page);
				pageModel = PageModel.toModel(page);
				appVO.setSuccess(true);
				appVO.setMsg("添加成功");
				appVO.setRow(pageModel);
			} else {
				appVO.setSuccess(false);
				appVO.setMsg("添加失败,CODE已被使用");
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
		return super.update();
	}

//	@Override
//	public String update() {
//		AppModel appModel = this.getAppModel();
//		PageModel pageModel = (PageModel) appModel.attrToBean(PageModel.class, PageModel.getClassMap());
//		AppVO appVO = this.getAppVO();
//		try {
//			Page entity = this.pageService.findById(pageModel.getId());
//			entity = (Page) this.toEntity(pageModel, entity);
//			this.pageService.update(entity);
//			pageModel = PageModel.toModel(entity);
//			appVO.setSuccess(true);
//			appVO.setMsg("更新成功");
//			appVO.setRow(pageModel);
//		} catch(Exception e) {
//			e.printStackTrace();
//			appVO.setSuccess(false);
//			appVO.setMsg("系统异常");
//			return ERROR;
//		}
//		return SUCCESS;
//	}

	@Override
	public String delete() {
		AppModel appModel = this.getAppModel();
		PageModel pageModel = (PageModel) appModel.attrToBean(PageModel.class);
		AppVO appVO = this.getAppVO();
		try {
			this.delete(pageModel.getId());
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
	
	private void delete(Object id) throws Exception {
		// 判断是否被menu引用,如果是解除该引用,对menu进行update,再删除page
		Page entity = this.pageService.findById(id);
		MenuModel memuModel = new MenuModel();
		memuModel.setPageId(entity.getId());
//		List<Menu> menuList = this.menuService.findMenuWithSpecPage(entity.getId());
		List<Menu> menuList = this.menuService.query(memuModel, null, null);
		for(Menu menu : menuList) {
			menu.setPage(null);
			this.menuService.update(menu);
		}
		this.pageService.deleteObject(entity);
	}

	@Override
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
			for(String id : ids) {
				this.delete(id);
			}
			appVO.setSuccess(true);
			appVO.setMsg("成功删除" + ids.length + "条数据");
			appVO.setRow(delIdsStr);
		} catch(Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常,删除失败");
			return ERROR;
		}
		return SUCCESS;
	}

//	@Override
//	public String queryByPage() {
//		AppModel appModel = this.getAppModel();
//		int offset = appModel.getOffset();
//		int limit = appModel.getLimit();
//		String orderBy = appModel.getSort();
//		String order = appModel.getOrder();
//		PageModel pageModel = (PageModel) appModel.attrToBean(PageModel.class);
//		PageBean<Page> pageBean = null;
//		AppVO appVO = this.getAppVO();
//		try {
//			pageBean = this.pageService.queryByPage(offset, limit, pageModel, orderBy, order);
//			List<Page> beanList = pageBean.getBeanList();
//			for(Page bean : beanList) {
////				pageModel = (PageModel) this.toModel(bean);
//				pageModel = PageModel.toModel(bean);
//				appVO.addRow(pageModel);
//			}
//			appVO.setTotal(pageBean.getTotalCount());
//			appVO.setSuccess(true);
//			appVO.setMsg("查询成功");
//		} catch (Exception e) {
//			e.printStackTrace();
//			appVO.setSuccess(false);
//			appVO.setMsg("系统异常,查询失败");
//			return ERROR;
//		}
//		return SUCCESS;
//	}

//	@Override
//	public Object toEntity(Object model, Object entity) {
//		PageModel pageModel = (PageModel) model;
//		Page pageEntity = null;
//		if(entity == null) {
//			pageEntity = new Page();
//		} else {
//			pageEntity = (Page) entity;
//		}
//		pageEntity.setCode(pageModel.getCode());
//		pageEntity.setName(pageModel.getName());
//		pageEntity.setUrl(pageModel.getUrl());
//		ActionContext ctx = ActionContext.getContext();
//		User user = (User)ctx.getSession().get("user");
//		pageEntity.setCreateUser(user.getAccount());
//		pageEntity.setCreateTime(new Date());
//		return pageEntity;
//	}

	@Override
	protected Page toEntity(PageModel model, Page entity) {
		PageModel pageModel = (PageModel) model;
		Page pageEntity = null;
		if(entity == null) {
			pageEntity = new Page();
		} else {
			pageEntity = (Page) entity;
		}
		pageEntity.setCode(pageModel.getCode());
		pageEntity.setName(pageModel.getName());
		pageEntity.setUrl(pageModel.getUrl());
		ActionContext ctx = ActionContext.getContext();
		User user = (User)ctx.getSession().get("user");
		pageEntity.setCreateUser(user.getAccount());
		pageEntity.setCreateTime(new Date());
		return pageEntity;
	}

//	@Override
//	public Object toModel(Object entity) throws Exception {
//		Page pageEntity = (Page) entity;
//		PageModel model = new PageModel();
//		model.setId(pageEntity.getId());
//		model.setCode(pageEntity.getCode());
//		model.setName(pageEntity.getName());
//		model.setUrl(pageEntity.getUrl());
//		model.setCreateUser(pageEntity.getCreateUser());
//		model.setCreateTime(pageEntity.getCreateTime());
//		return model;
//	}

}
