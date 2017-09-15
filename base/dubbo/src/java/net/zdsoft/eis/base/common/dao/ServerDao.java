package net.zdsoft.eis.base.common.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.Server;

public interface ServerDao {
    public Server getServer(String serverId);

    public Server getServerByServerCode(String serverCode);

    public List<Server> getServers(Long[] serverIds);
    
    public Map<Long, String> getServerCodeMap();

    public Map<Long, String> getServerTypeMap();

    /**
     * 取server map
     * 
     * @return key=server_code,value=id
     */
    public Map<String, Long> getServerMapByServerCode();
    
    public Map<Long, Server> getServerMap();
    
    public Map<String, Server> getServerMapByCode();
}
