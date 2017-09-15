package net.zdsoft.eis.base.data.dao;

import java.util.List;

import net.zdsoft.eis.base.common.entity.ServerAuthorize;

public interface BaseServerAuthorizeDao {
    public void deleteServerAuthorizes(String[] userIds);

    public void deleteServerAuthorizes(Long[] serverIds, String[] userIds);

    public void addServerAuthorizes(List<ServerAuthorize> authList);

    /**
     * 根据useid获取所有的权限关系
     * 
     * @param userId
     * @return
     */
    public List<ServerAuthorize> getServerAuthorizes(String[] userIds);
    
    /**
     * 根据服务id获取授权信息
     * 
     * @param serverId
     * @return
     */
    public List<ServerAuthorize> getServerAuthorizes(Long serverId);
}
