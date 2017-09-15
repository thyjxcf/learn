/* 
 * @(#)BaseMcodeDetailServiceImpl.java    Created on Nov 23, 2009
 * Copyright (c) 2009 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.data.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.common.service.impl.McodedetailServiceImpl;
import net.zdsoft.eis.base.data.dao.BaseMcodeDetailDao;
import net.zdsoft.eis.base.data.service.BaseMcodeDetailService;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Nov 23, 2009 10:39:36 AM $
 */
public class BaseMcodeDetailServiceImpl extends McodedetailServiceImpl
		implements BaseMcodeDetailService {
	private BaseMcodeDetailDao baseMcodeDetailDao;

	public void setBaseMcodeDetailDao(BaseMcodeDetailDao baseMcodeDetailDao) {
		this.baseMcodeDetailDao = baseMcodeDetailDao;
	}

	public boolean updateUsingStatus(String mcodelistId, String mcodedetailId) {
		Mcodedetail item = getMcodeDetail(mcodedetailId);

		int using = 0;
		Integer isUsing = item.getIsUsing();
		if (isUsing == null || isUsing == 0) {
			using = 1;
		} else {
			using = 0;
		}
		baseMcodeDetailDao.updateStateChange(mcodedetailId, using);
		clearCache();
		return true;
	}

	public void updateMcodeDetail(Mcodedetail mcodedetail) {
		Mcodedetail prv = getMcodeDetail(String.valueOf(mcodedetail.getId()));
		mcodedetail.setMcodeId(prv.getMcodeId());
		mcodedetail.setType(prv.getType());
		//		mcodedetail.setContent(prv.getContent());
		//		mcodedetail.setThisId(prv.getThisId());
		//		mcodedetail.setOrderId(prv.getOrderId());
		baseMcodeDetailDao.updateMcodeDetail(mcodedetail);
		clearCache();
	}

	public void insertMcodeDetail(Mcodedetail mcodedetail) {
		baseMcodeDetailDao.insertMcodeDetail(mcodedetail);
		clearCache();
	}

	public void deleteMcodeDetail(String[] ids) {
		baseMcodeDetailDao.deleteMcodeDetail(ids);
		clearCache();
	}

	public Integer getAvaOrderId(String mcodeid) {
		return baseMcodeDetailDao.getAvaOrderId(mcodeid);
	}

	public String[] getMcodeContentByIds(String[] ids) {
		List<Mcodedetail> list = new ArrayList<Mcodedetail>();
		for (String id : ids) {
			list.add(getMcodeDetail(id));
		}
		String[] resultStr = new String[ids.length];
		Mcodedetail mcodedetail;
		for (int i = 0; i < ids.length; i++) {
			if (i >= list.size()) {
				resultStr[i] = "";
			} else {
				mcodedetail = (Mcodedetail) list.get(i);
				resultStr[i] = mcodedetail.getContent();
			}
		}
		return resultStr;
	}

    
    /**
     * 取出微代码信息
     * 
     * @param detailId 微代码ID，32位的GUID
     * @return 微代码信息
     */
    public Mcodedetail getMcodeDetail(final String detailId) {
        return baseMcodeDetailDao.getMcodeDetail(detailId);
    }
}
