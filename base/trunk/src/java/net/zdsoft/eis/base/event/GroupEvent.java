package net.zdsoft.eis.base.event;

import java.util.Date;
import java.util.Map;

import net.zdsoft.leadin.event.BaseEvent;

public class GroupEvent implements BaseEvent {
    public static final int GROUP_DELETE = 2;// 部门删除

    private String[] groupIds;
    private int eventType;

    public GroupEvent(String[] groupIds, int eventType) {
        this.groupIds = groupIds;
        this.eventType = eventType;
    }

    public Date getDate() {
        return new Date();
    }

    public int getEventType() {
        return this.eventType;
    }

    @SuppressWarnings("unchecked")
    public Map getParams() {
        return null;
    }

    public String[] getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(String[] groupIds) {
        this.groupIds = groupIds;
    }

}
