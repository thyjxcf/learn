package net.zdsoft.eis.base.data.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.Server;
import net.zdsoft.eis.base.common.entity.SubSystem;
import net.zdsoft.eis.base.common.entity.Ware;
import net.zdsoft.eis.base.common.service.SubSystemService;
import net.zdsoft.eis.base.common.service.impl.ServerServiceImpl;
import net.zdsoft.eis.base.data.dao.BaseServerDao;
import net.zdsoft.eis.base.data.dao.BaseWareDao;
import net.zdsoft.eis.base.data.service.BaseServerService;
import net.zdsoft.eis.base.deploy.SystemDeployService;
import net.zdsoft.eis.base.sync.EventSourceType;

public class BaseServerServiceImpl extends ServerServiceImpl implements BaseServerService {
    private BaseServerDao baseServerDao;
    private SystemDeployService systemDeployService;
    private BaseWareDao baseWareDao;
    private SubSystemService subSystemService;

    public void setSystemDeployService(SystemDeployService systemDeployService) {
        this.systemDeployService = systemDeployService;
    }

    public void setBaseServerDao(BaseServerDao baseServerDao) {
        this.baseServerDao = baseServerDao;
    }

    public void setBaseWareDao(BaseWareDao baseWareDao) {
        this.baseWareDao = baseWareDao;
    }

    public void setSubSystemService(SubSystemService subSystemService) {
        this.subSystemService = subSystemService;
    }

    @Override
    public void deleteServer(String[] serverIds, EventSourceType eventSource) {
        baseServerDao.deleteServer(serverIds, eventSource);
        updateServerEx();
    }

    @Override
    public void addServer(Server server) {
        baseServerDao.insertServer(server);
        updateServerEx();
    }

    @Override
    public void updateServer(Server server) {
        baseServerDao.updateServer(server);
        updateServerEx();
    }

    private void updateServerEx() {
        systemDeployService.initPassportClient();
    }

    public List<Server> getAuthorizedServers(int unitClass, int roleType) {
        // 启用的server
        List<Server> servers = baseServerDao.getServers(Server.STATUS_TURNON);

        // 需授权的serverId
        Set<String> authorizedServerIds = baseWareDao.getServerIds(unitClass, roleType,
                Ware.STATE_NORMAL, Ware.ORDER_RULE_AUTHORIZE);

        // eis的子系统
        Set<String> serverCodes = new HashSet<String>();
        List<SubSystem> subsystems = subSystemService.getSubSystems();
        for (SubSystem subSystem : subsystems) {
            serverCodes.add(subSystem.getCode());
        }

        List<Server> authorizedServers = new ArrayList<Server>();
        for (Iterator<Server> iterator = servers.iterator(); iterator.hasNext();) {
            Server server = iterator.next();

            // 如果不需要授权，则过滤掉
            if (!(authorizedServerIds.contains(server.getId()))) {
                continue;
            }

            // 如果是eis的子系统，则过滤掉
            if (serverCodes.contains(server.getServerCode())) {
                continue;
            }

            authorizedServers.add(server);
        }
        return authorizedServers;
    }
}
