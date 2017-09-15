/* 
 * @(#)BaseServerAuthorizeService.java    Created on May 26, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.data.service;

import java.util.List;

import net.zdsoft.eis.base.common.entity.ServerAuthorize;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 26, 2011 2:36:38 PM $
 */
public interface BaseServerAuthorizeService {
    /**
     * 删除用户服务
     * @param userIds
     * @param serverKind 服务类别
     */
    public void deleteServerAuthorizes(String[] userIds, int serverKind);

    public void addServerAuthorizes(List<ServerAuthorize> authList);

    /**
     * 按用户保存授权信息（需删除这些用户原先的授权信息）
     * 
     * @param userIds
     * @param serverIds
     */
    public void saveServerAuthorizesFromUser(String[] userIds, Long[] serverIds);

    /**
     * 按角色保存授权信息（需删除这些角色原先的授权信息）
     * 
     * @param serverIds
     * @param userIds
     * @param deleteUserIds
     */
    public void saveServerAuthorizesFromServer(Long[] serverIds, String[] userIds,
            String[] deleteUserIds);

    /**
     * 根据useid获取所有的授权信息（忽略未启用的服务的授权信息）
     * 
     * @param userIds
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
