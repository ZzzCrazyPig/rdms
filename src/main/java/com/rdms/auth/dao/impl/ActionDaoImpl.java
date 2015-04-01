package com.rdms.auth.dao.impl;

import org.springframework.stereotype.Repository;

import com.rdms.auth.dao.ActionDao;
import com.rdms.auth.domain.Action;
import com.rdms.base.dao.impl.BaseDaoImpl;

@Repository("actionDao")
public class ActionDaoImpl extends BaseDaoImpl<Action> implements ActionDao { 

}
