package com.rdms.bug.dao.impl;

import org.springframework.stereotype.Repository;

import com.rdms.base.dao.impl.BaseDaoImpl;
import com.rdms.bug.dao.BugInfoDao;
import com.rdms.bug.domain.BugInfo;

@Repository("bugInfoDao")
public class BugInfoDaoImpl extends BaseDaoImpl<BugInfo> implements BugInfoDao {

}
