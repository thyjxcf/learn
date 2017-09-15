/* 
 * @(#)MaxCodeDao.java    Created on Oct 14, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.common.dao;

import net.zdsoft.leadin.common.entity.MaxCodeMetadata;
import net.zdsoft.leadin.common.entity.MaxCodeParam;

public interface MaxCodeDao {

    /**
     * 取最大流水号
     * 
     * @param metadata
     * @param param
     * @return
     */
    public int getMaxSerialNumber(MaxCodeMetadata metadata, MaxCodeParam param);

}
