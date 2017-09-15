/* 
 * @(#)BaseEduInfoService.java    Created on Nov 23, 2009
 * Copyright (c) 2009 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.data.service;

import net.zdsoft.eis.base.common.entity.EduInfo;
import net.zdsoft.eis.base.common.service.EduInfoService;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Nov 23, 2009 4:56:38 PM $
 */
public interface BaseEduInfoService extends EduInfoService {

    public void updateUnitByEduInfo(EduInfo eduinfo);

    public void addEduInfo(EduInfo eduinfo);

    public void updateEduInfo(EduInfo eduinfo);
    
    public EduInfo getBaseEduInfo(String eduid);
}
