/* 
 * @(#)BatchHandlable.java    Created on Dec 20, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.sync;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseEntity;
import net.zdsoft.leadin.exception.BusinessErrorException;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 20, 2010 5:06:52 PM $
 */
public interface BatchHandlable<E extends BaseEntity> {

    public abstract void addDatas(List<E> entities) throws BusinessErrorException;

    public abstract void updateDatas(List<E> entities) throws BusinessErrorException;

    public abstract void deleteDatas(String[] ids, EventSourceType eventSource)
            throws BusinessErrorException;

    /**
     * 取原来entity数据，更新时使用，如果eis有扩展的字段，则取数据库中直接取。 如果没有扩展字段（即与base表结构完全相同，则直接new
     * entity或null即可）
     * 
     * @param id
     * @return
     */
    public abstract Map<String, E> fetchOldEntities(String[] ids);
}
