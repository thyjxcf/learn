/* 
 * @(#)BaseEntity.java    Created on Nov 17, 2009
 * Copyright (c) 2009 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.frame.client;

import java.io.Serializable;
import java.util.Date;

import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.eis.frame.entity.JsonDateSerializeFull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Nov 17, 2009 1:26:22 PM $
 */
public class BaseEntity implements Serializable {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -7033630867879059224L;

    protected String id;// 表的UUID主键
    @JsonProperty("isDeleted")
    protected boolean isdeleted; // 软删除
    @JsonSerialize(using = JsonDateSerializeFull.class)  
    protected Date creationTime; // 创建时间戳
    @JsonSerialize(using = JsonDateSerializeFull.class)
    protected Date modifyTime;

    @JsonIgnore
    protected long updatestamp; // 更新戳
    @JsonIgnore
    private EventSourceType eventSource = EventSourceType.LOCAL;// 本地生源

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean getIsdeleted() {
        return this.isdeleted;
    }

    public void setIsdeleted(boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public long getUpdatestamp() {
        return updatestamp;
    }

    public void setUpdatestamp(long updatestamp) {
        this.updatestamp = updatestamp;
    }

    public EventSourceType getEventSource() {
        return eventSource;
    }

    public void setEventSource(EventSourceType eventSource) {
        this.eventSource = eventSource;
    }

    @JsonIgnore
    public boolean isSendMq() {
        return eventSource == EventSourceType.LOCAL;
    }

    @JsonIgnore
    public int getEventSourceValue() {
        return eventSource.getValue();
    }

}
