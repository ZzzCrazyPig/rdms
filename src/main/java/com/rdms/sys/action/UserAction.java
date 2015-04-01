package com.rdms.sys.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.rdms.base.action.GeneralAction;
import com.rdms.base.action.model.AppModel;
import com.rdms.base.vo.AppVO;
import com.rdms.sys.action.model.UserModel;
import com.rdms.sys.domain.User;
import com.rdms.sys.service.UserService;
import com.rdms.util.SecurityUtil;

@Controller("userAction")
@Scope("prototype")
public class UserAction extends GeneralAction<User, UserService, UserModel> {

	private static final long serialVersionUID = 1L;
	
	private UserService userService;
	
	@Resource(name="userService")
	public void setUserService(UserService userService) {
		super.setBaseService(userService);
		this.userService = userService;
	}

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
		return super.update();
	}

	@Override
	public String delete() {
		return super.delete();
	}

	@Override
	public String query() {
		return super.query();
	}

	// 分页查询
	@Override
	public String queryByPage() {
		return super.queryByPage();
	}

	@Override
	public String multiDelete() {
		return super.delete();
	}

	@Override
	protected User toEntity(UserModel model, User entity) {
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
