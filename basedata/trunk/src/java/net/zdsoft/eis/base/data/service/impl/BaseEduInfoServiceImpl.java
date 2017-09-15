/* 
 * @(#)BaseEduInfoServiceImpl.java    Created on Nov 23, 2009
 * Copyright (c) 2009 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.data.service.impl;

import net.zdsoft.eis.base.common.entity.EduInfo;
import net.zdsoft.eis.base.common.service.impl.EduInfoServiceImpl;
import net.zdsoft.eis.base.data.dao.BaseEduInfoDao;
import net.zdsoft.eis.base.data.entity.BaseUnit;
import net.zdsoft.eis.base.data.service.BaseEduInfoService;
import net.zdsoft.eis.base.data.service.BaseUnitService;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Nov 23, 2009 4:57:04 PM $
 */
public class BaseEduInfoServiceImpl extends EduInfoServiceImpl implements
		BaseEduInfoService {
	private BaseEduInfoDao baseEduInfoDao;
	private BaseUnitService baseUnitService;

	public void setBaseUnitService(BaseUnitService baseUnitService) {
		this.baseUnitService = baseUnitService;
	}

	public void setBaseEduInfoDao(BaseEduInfoDao baseEduInfoDao) {
		this.baseEduInfoDao = baseEduInfoDao;
	}

	public void updateUnitByEduInfo(EduInfo eduinfo) {
		EduInfo oldEdu = getBaseEduInfo(eduinfo.getId());
		if (null != oldEdu) {
			baseEduInfoDao.updateEduInfo(eduinfo);
		} else {
			baseEduInfoDao.insertEduInfo(eduinfo);
		}

		BaseUnit unit = baseUnitService.getBaseUnit(eduinfo.getId());
		unit.setLinkPhone(eduinfo.getTelephone());
		unit.setAddress(eduinfo.getAddress());
		unit.setPostalcode(eduinfo.getPostalcode());
		unit.setFax(eduinfo.getFax());
		unit.setEmail(eduinfo.getEmail());
		unit.setHomepage(eduinfo.getHomepage());
		unit.setLinkMan(eduinfo.getPrincipal());
		baseUnitService.updateUnit(unit);
	}

	public void addEduInfo(EduInfo eduInfo) {
		baseEduInfoDao.insertEduInfo(eduInfo);
	}

	public void updateEduInfo(EduInfo eduInfo) {
		baseEduInfoDao.updateEduInfo(eduInfo);
	}

    public EduInfo getBaseEduInfo(String eduid) {
        EduInfo edu = getEduInfo(eduid);
        if (edu == null)
            return null;

        BaseUnit unit = baseUnitService.getBaseUnit(eduid);
        edu.setId(unit.getId());
        edu.setName(unit.getName());
        edu.setTelephone(unit.getLinkPhone());
        edu.setPostalcode(unit.getPostalcode());
        edu.setAddress(unit.getAddress());
        edu.setFax(unit.getFax());
        edu.setEmail(unit.getEmail());
        edu.setCreationTime(unit.getCreationTime());
        edu.setPrincipal(unit.getLinkMan());

        return edu;
    }
}
