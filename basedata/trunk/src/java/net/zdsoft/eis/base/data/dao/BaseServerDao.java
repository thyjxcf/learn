/* 
 * @(#)BaseServerDao.java    Created on Dec 20, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.data.dao;

import java.util.List;

import net.zdsoft.eis.base.common.entity.Server;
import net.zdsoft.eis.base.sync.EventSourceType;

public interface BaseServerDao {

    public abstract void insertServer(Server server);

    public abstract void deleteServer(String[] serverIds, EventSourceType eventSource);

    public abstract void updateServer(Server server);

    public List<Server> getServers(int status);
}
