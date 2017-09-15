/* 
 * @(#)BaseEduInfoDao.java    Created on Nov 23, 2009
 * Copyright (c) 2009 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.data.dao;

import net.zdsoft.eis.base.common.entity.EduInfo;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Nov 23, 2009 4:53:04 PM $
 */
public interface BaseEduInfoDao {

    public void insertEduInfo(EduInfo eduInfo);

    public void deleteEduInfo(String[] eduInfoIds);

    public void updateEduInfo(EduInfo eduInfo);
}
