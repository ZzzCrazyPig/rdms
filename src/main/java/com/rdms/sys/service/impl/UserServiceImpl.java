package com.rdms.sys.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rdms.base.PageBean;
import com.rdms.base.service.impl.BaseServiceImpl;
import com.rdms.sys.action.model.UserModel;
import com.rdms.sys.dao.UserDao;
import com.rdms.sys.domain.User;
import com.rdms.sys.service.UserService;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
	
	private UserDao userDao;
	
	@Resource(name="userDao")
	// need to be injected
	public void setUserDao(UserDao userDao) {
		super.setBaseDAO(userDao);
		this.userDao = userDao;
	}
	
	public UserDao getUserDao() {
		return this.userDao;
	}

	public PageBean<User> queryByPage(int offset, int limit, UserModel model,
			String orderBy, String asc) throws Exception {
		return super.queryByPage(offset, limit, model, orderBy, asc);
	}

	public User validate(String account, String pwd) throws Exception {
		// TODO Auto-generated method stub
		return this.userDao.validate(account, pwd);
	}
	
//	public static void main(String[] args) {
//		UserServiceImpl userService = new UserServiceImpl();
//		UserModel model = new UserModel();
//		model.setName("CrazyPig");
//		model.setPwd("woshixin");
//		model.setUsr("d6619309");
//		try {
//			Map<String, Object> whereArgs = userService.getWhereArgs(model);
//		} catch (NoSuchMethodException | SecurityException
//				| IllegalAccessException | IllegalArgumentException
//				| InvocationTargetException e) {
//			e.printStackTrace();
//		}
//	}
	
}
