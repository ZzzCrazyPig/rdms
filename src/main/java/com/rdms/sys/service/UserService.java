package com.rdms.sys.service;

import com.rdms.base.PageBean;
import com.rdms.base.service.BaseService;
import com.rdms.sys.action.model.UserModel;
import com.rdms.sys.domain.User;

public interface UserService extends BaseService<User> {
	
	public PageBean<User> queryByPage(int offset, int limit, UserModel model, String orderBy, String asc) throws Exception;
	
	public User validate(String account, String pwd) throws Exception;

}
