/* 
 * @(#)SimpleClassDaoImpl.java    Created on May 28, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.simple.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import net.zdsoft.eis.base.simple.dao.SimpleClassDao;
import net.zdsoft.eis.base.simple.entity.SimpleClass;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 28, 2011 5:23:55 PM $
 */
public class SimpleClassDaoImpl extends AbstractClassDaoImpl<SimpleClass> implements SimpleClassDao {

    @Override
    public SimpleClass setField(ResultSet rs) throws SQLException {
        SimpleClass cls = new SimpleClass();
        setEntity(rs, cls);
        return cls;
    }

}
