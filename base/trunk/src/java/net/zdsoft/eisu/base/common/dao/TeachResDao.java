/* 
 * @(#)TeachResDao.java    Created on May 20, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.eisu.base.common.entity.TeachRes;

/**
 * 教学资源
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 20, 2011 10:31:28 AM $
 */
public interface TeachResDao {

    /**
     * 根据资源id取资源
     * 
     * @param teachResId
     * @return
     */
    public TeachRes getTeachRes(String teachResId);

    /**
     * 取全部资源
     * 
     * @return
     */
    public List<TeachRes> getTeachReses();

    /**
     * 取所有资源Map
     * 
     * @return
     */
    public Map<String, TeachRes> getTeachResMap();

    /**
     * 取资源Map
     * 
     * @param teachResIds
     * @return
     */
    public Map<String, TeachRes> getTeachResMap(String[] teachResIds);

}
