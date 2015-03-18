package com.rdms.comm.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rdms.base.service.impl.BaseServiceImpl;
import com.rdms.comm.dao.PjStageDao;
import com.rdms.comm.domain.PjStage;
import com.rdms.comm.service.PjStageService;

@Service("pjStageService")
public class PjStageServiceImpl extends BaseServiceImpl<PjStage> implements PjStageService {

	private PjStageDao pjStageDao;

	public PjStageDao getPjStageDao() {
		return pjStageDao;
	}

	@Resource(name="pjStageDao")
	public void setPjStageDao(PjStageDao pjStageDao) {
		super.setBaseDAO(pjStageDao);
		this.pjStageDao = pjStageDao;
	}
	
}
