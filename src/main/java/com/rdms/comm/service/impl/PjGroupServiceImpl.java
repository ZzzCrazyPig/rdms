package com.rdms.comm.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rdms.base.PageBean;
import com.rdms.base.action.model.BaseModel;
import com.rdms.base.service.impl.BaseServiceImpl;
import com.rdms.comm.dao.PjGroupDao;
import com.rdms.comm.domain.PjGroup;
import com.rdms.comm.service.PjGroupService;

@Service("pjGroupService")
public class PjGroupServiceImpl extends BaseServiceImpl<PjGroup> implements PjGroupService {

	private PjGroupDao pjGroupDao;

	public PjGroupDao getPjGroupDao() {
		return pjGroupDao;
	}

	@Resource(name="pjGroupDao")
	public void setPjGroupDao(PjGroupDao pjGroupDao) {
		super.setBaseDAO(pjGroupDao);
		this.pjGroupDao = pjGroupDao;
	}

	public PageBean<PjGroup> queryFetchPjGrMemsByPage(int offset, int limit,
			BaseModel model, String orderBy, String asc) throws Exception {
		Map<String, Object> whereArgs = this.getWhereArgs(model);
		return this.pjGroupDao.queryFetchPjGrMemsByPage(offset, limit, whereArgs, orderBy, asc);
	}

	@Override
	public List<PjGroup> queryAllJoinPjGroup(String eid) throws Exception {
		return this.pjGroupDao.queryAllJoinPjGroup(eid);
	}

	@Override
	public List<Object[]> countEachDeptPjGroup() throws Exception {
		return this.pjGroupDao.countEachDeptPjGroup();
	}
	
}
