package com.rdms.sys.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rdms.base.dao.impl.BaseDaoImpl;
import com.rdms.sys.dao.UserDao;
import com.rdms.sys.domain.User;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	public User validate(String account, String pwd) throws Exception {
		final String hql = "FROM User AS u WHERE u.account = ? AND u.pwd = ?";
		List<User> result = this.find(hql, account, pwd);
		return result.size() > 0 ? result.get(0) : null;
	}

}
