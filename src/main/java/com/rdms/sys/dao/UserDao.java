package com.rdms.sys.dao;

import com.rdms.base.dao.BaseDao;
import com.rdms.sys.domain.User;

public interface UserDao extends BaseDao<User> {
	
	public User validate(String account, String pwd) throws Exception ;
	
}
