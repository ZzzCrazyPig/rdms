package com.rdms.bug.dao.impl;

import org.springframework.stereotype.Repository;

import com.rdms.base.dao.impl.BaseDaoImpl;
import com.rdms.bug.dao.BugTraceDao;
import com.rdms.bug.domain.BugTrace;

@Repository("bugTraceDao")
public class BugTraceDaoImpl extends BaseDaoImpl<BugTrace> implements BugTraceDao {

}
