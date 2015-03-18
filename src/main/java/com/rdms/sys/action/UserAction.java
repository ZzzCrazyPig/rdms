package com.rdms.sys.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.rdms.base.PageBean;
import com.rdms.base.action.BaseAction;
import com.rdms.base.action.model.AppModel;
import com.rdms.base.vo.AppVO;
import com.rdms.sys.action.model.UserModel;
import com.rdms.sys.domain.User;
import com.rdms.sys.service.UserService;
import com.rdms.util.SecurityUtil;

@Controller("userAction")
@Scope("prototype")
public class UserAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	@Resource(name="userService")
	private UserService userService;

	@Override
	public String insert() {
		AppModel appModel = this.getAppModel();
		UserModel userModel = (UserModel)appModel.attrToBean(UserModel.class, UserModel.getClassMap());
		String pwd = userModel.getPwd();
		String md5Pwd = SecurityUtil.md5(pwd);
		userModel.setPwd(md5Pwd);
		AppVO appVO = this.getAppVO();
		try {
			User entity = (User) this.toEntity(userModel, null);
			this.userService.save(entity);
			userModel = UserModel.toModel(entity);
			appVO.setSuccess(true);
			appVO.setMsg("添加成功");
			appVO.setRow(userModel);
		} catch(Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常");
			return ERROR;
		}
		return SUCCESS;
	}

	@Override
	public String update() {
		AppModel appModel = this.getAppModel();
		UserModel userModel = (UserModel) appModel.attrToBean(UserModel.class, UserModel.getClassMap());
		AppVO appVO = this.getAppVO();
		try {
			User entity = this.userService.findById(userModel.getId());
//			entity.setAccount(userModel.getAccount());
//			entity.setName(userModel.getName());
//			entity.setPwd(SecurityUtil.md5(userModel.getPwd()));
			entity = (User) this.toEntity(userModel, entity);
			this.userService.update(entity);
//			userModel = (UserModel) this.toModel(entity);
			userModel = UserModel.toModel(entity);
			appVO.setSuccess(true);
			appVO.setMsg("更新成功");
			appVO.setRow(userModel);
		} catch(Exception e) {
			e.printStackTrace();
			appVO.setSuccess(false);
			appVO.setMsg("系统异常");
			return ERROR;
		}
		return SUCCESS;
	}

	@Override
	public String delete() {
		AppModel appModel = this.getAppModel();
		UserModel userModel = (UserModel) appModel.attrToBean(UserModel.class, UserModel.getClassMap());
		AppVO appVO = this.getAppVO();
		try {
			this.userService.delete(userModel.getId());
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
	public String query() {
		// TODO Auto-generated method stub
		return null;
	}

	// 分页查询
	@Override
	public String queryByPage() {
		AppModel appModel = this.getAppModel();
		int offset = appModel.getOffset();
		int limit = appModel.getLimit();
		String orderBy = appModel.getSort();
		String order = appModel.getOrder();
		UserModel userModel = (UserModel) appModel.attrToBean(UserModel.class, UserModel.getClassMap());
		PageBean<User> pageBean = null;
		AppVO appVO = this.getAppVO();
		try {
			pageBean = this.userService.queryByPage(offset, limit, userModel, orderBy, order);
			List<User> beanList = pageBean.getBeanList();
			for(User bean : beanList) {
//				userModel = (UserModel) this.toModel(bean);
				userModel = UserModel.toModel(bean);
				appVO.addRow(userModel);
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
		UserModel userModel = (UserModel) model;
		User userEntity = null;
		if(entity == null) {
			userEntity = new User();
		} else {
			userEntity = (User) entity;
		}
		userEntity.setName(userModel.getName());
		userEntity.setPwd(SecurityUtil.md5(userModel.getPwd()));
		userEntity.setAccount(userModel.getAccount());
		return userEntity;
	}

	@Override
	public String multiDelete() {
		AppModel appModel = this.getAppModel();
		String attr = appModel.getAttr();
		String[] ids = attr.split(",");
		AppVO appVO = this.getAppVO();
		try {
			this.userService.deleteByIds(ids);
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

//	@Override
//	public Object toModel(Object entity) throws Exception {
//		User userEntity = (User) entity;
//		UserModel model = new UserModel();
//		model.setId(userEntity.getId());
//		model.setName(userEntity.getName());
//		model.setAccount(userEntity.getAccount());
//		return model;
//	}

}
