/* 
 * @(#)ServerServiceImpl.java    Created on Nov 23, 2009
 * Copyright (c) 2009 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.common.service.impl;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.dao.ServerDao;
import net.zdsoft.eis.base.common.entity.Server;
import net.zdsoft.eis.base.common.service.ServerService;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Nov 23, 2009 4:20:24 PM $
 */
public class ServerServiceImpl implements ServerService {
    private ServerDao serverDao;

    public void setServerDao(ServerDao serverDao) {
        this.serverDao = serverDao;
    }

    public Server getServer(String serverId) {
        return serverDao.getServer(serverId);
    }

    public Server getServerByServerCode(String serverCode) {
        return serverDao.getServerByServerCode(serverCode);
    }

    public List<Server> getServers(Long[] serverIds){
        return serverDao.getServers(serverIds);
    }
    
    public Map<Long, String> getServerCodeMap() {
        return serverDao.getServerCodeMap();
    }

    public Map<String, Long> getServerMapByServerCode() {
        return serverDao.getServerMapByServerCode();
    }

    public Map<Long, String> getServerTypeMap() {
        return serverDao.getServerTypeMap();
    }

    public Map<Long, Server> getServerMap() {
        return serverDao.getServerMap();
    }
    
    public Map<String, Server> getServerMapByCode(){
    	 return serverDao.getServerMapByCode();
    }
}
