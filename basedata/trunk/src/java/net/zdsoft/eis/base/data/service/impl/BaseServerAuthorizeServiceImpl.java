/* 
 * @(#)BaseServerAuthorizeServiceImpl.java    Created on May 26, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.data.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.Server;
import net.zdsoft.eis.base.common.entity.ServerAuthorize;
import net.zdsoft.eis.base.common.entity.SubSystem;
import net.zdsoft.eis.base.common.service.ServerService;
import net.zdsoft.eis.base.common.service.SubSystemService;
import net.zdsoft.eis.base.data.dao.BaseServerAuthorizeDao;
import net.zdsoft.eis.base.data.service.BaseServerAuthorizeService;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 26, 2011 2:37:02 PM $
 */
public class BaseServerAuthorizeServiceImpl implements BaseServerAuthorizeService {
    private BaseServerAuthorizeDao baseServerAuthorizeDao;
    private ServerService serverService;
    private SubSystemService subSystemService;

    public void setSubSystemService(SubSystemService subSystemService) {
        this.subSystemService = subSystemService;
    }

    public void setServerService(ServerService serverService) {
        this.serverService = serverService;
    }

    public void setBaseServerAuthorizeDao(BaseServerAuthorizeDao baseServerAuthorizeDao) {
        this.baseServerAuthorizeDao = baseServerAuthorizeDao;
    }

    public void addServerAuthorizes(List<ServerAuthorize> authList) {
        baseServerAuthorizeDao.addServerAuthorizes(authList);
    }

    public void deleteServerAuthorizes(String[] userIds, int serverKind) {
        // 内部server
        Set<Long> innerServerIds = new HashSet<Long>();
        Map<String, Long> serverMap = serverService.getServerMapByServerCode();
        List<SubSystem> subsystems = subSystemService.getSubSystems();
        for (SubSystem subsystem : subsystems) {
            String code = subsystem.getCode();
            Long serverId = serverMap.get(code);
            if (null != serverId) {
                innerServerIds.add(serverId);
            }
        }
        // 外部server
        Set<Long> outerServerIds = new HashSet<Long>();
        Collection<Long> serverIds = serverMap.values();
        for (Long serverId : serverIds) {
            if (!(innerServerIds.contains(serverId))) {
                outerServerIds.add(serverId);
            }
        }

        Long[] operateServerIds = null;
        if (Server.SERVER_INNER == serverKind) {
            operateServerIds = innerServerIds.toArray(new Long[0]);
        } else if (Server.SERVER_OUTER == serverKind) {
            operateServerIds = outerServerIds.toArray(new Long[0]);
        }
        baseServerAuthorizeDao.deleteServerAuthorizes(operateServerIds, userIds);
    }

    public void saveServerAuthorizesFromUser(String[] userIds, Long[] serverIds) {
        deleteServerAuthorizes(userIds, Server.SERVER_OUTER);

        addServerAuthorizes(userIds, serverIds);
    }

    public void saveServerAuthorizesFromServer(Long[] serverIds, String[] userIds,
            String[] deleteUserIds) {
        baseServerAuthorizeDao.deleteServerAuthorizes(serverIds, deleteUserIds);

        addServerAuthorizes(userIds, serverIds);
    }

    private void addServerAuthorizes(String[] userIds, Long[] serverIds) {
        if (serverIds != null && userIds != null) {
            List<ServerAuthorize> list = new ArrayList<ServerAuthorize>();
            for (String userId : userIds) {
                for (long serverId : serverIds) {
                    ServerAuthorize sa = new ServerAuthorize();
                    sa.setServerId(serverId);
                    sa.setUserId(userId);
                    list.add(sa);
                }
            }
            if (!list.isEmpty()) {
                addServerAuthorizes(list);
            }
        }
    }

    public List<ServerAuthorize> getServerAuthorizes(String[] userIds) {
        Map<Long, Server> serverMap = serverService.getServerMap();
        List<ServerAuthorize> list = baseServerAuthorizeDao.getServerAuthorizes(userIds);

        // 如果server不存在，或未启用，则过滤掉
        for (Iterator<ServerAuthorize> iterator = list.iterator(); iterator.hasNext();) {
            ServerAuthorize sa = iterator.next();
            Server server = serverMap.get(sa.getServerId());
            if (null == server) {
                iterator.remove();
            } else {
                if (Server.STATUS_TURNON != server.getStatus()) {
                    iterator.remove();
                }
            }
        }
        return list;
    }

    public List<ServerAuthorize> getServerAuthorizes(Long serverId) {
        return baseServerAuthorizeDao.getServerAuthorizes(serverId);
    }
}
