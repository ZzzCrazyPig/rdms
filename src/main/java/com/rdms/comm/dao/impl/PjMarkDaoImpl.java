package com.rdms.comm.dao.impl;

import org.springframework.stereotype.Repository;

import com.rdms.base.dao.impl.BaseDaoImpl;
import com.rdms.comm.dao.PjMarkDao;
import com.rdms.comm.domain.PjMark;

@Repository("pjMarkDao")
public class PjMarkDaoImpl extends BaseDaoImpl<PjMark> implements PjMarkDao {

}
