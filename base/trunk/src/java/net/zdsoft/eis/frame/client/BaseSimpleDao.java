/* 
 * @(#)BaseSimpleDao.java    Created on May 14, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.frame.client;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 用于多级继承的dao
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 14, 2011 2:33:47 PM $
 */
public abstract class BaseSimpleDao<T> extends BaseDao<T> {

    public abstract void setEntity(ResultSet rs, T t) throws SQLException;

    /**
     * 取前缀
     * 
     * @return
     */
    public String getFindPrefix() {
        return null;
    }
}
