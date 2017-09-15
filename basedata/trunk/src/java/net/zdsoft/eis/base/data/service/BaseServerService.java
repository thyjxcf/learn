package net.zdsoft.eis.base.data.service;

import java.util.List;

import net.zdsoft.eis.base.common.entity.Server;
import net.zdsoft.eis.base.common.service.ServerService;
import net.zdsoft.eis.base.sync.EventSourceType;

public interface BaseServerService extends ServerService {
    public abstract void addServer(Server server);

    public abstract void deleteServer(String[] serverIds, EventSourceType eventSource);

    public abstract void updateServer(Server server);

    /**
     * 取要授权的服务
     * 
     * @param unitClass 单位类型
     * @param roleType 角色类型
     * @return
     */
    public List<Server> getAuthorizedServers(int unitClass, int roleType);
}
