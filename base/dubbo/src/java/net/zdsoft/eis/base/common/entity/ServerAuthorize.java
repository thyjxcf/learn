package net.zdsoft.eis.base.common.entity;

import net.zdsoft.eis.frame.client.BaseEntity;

public class ServerAuthorize extends BaseEntity {
    /**
     * 
     */

    private static final long serialVersionUID = 968111863010922426L;
    private Long serverId;
    private String userId;

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
