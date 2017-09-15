package net.zdsoft.eis.base.event;

import java.util.Date;
import java.util.Map;

import net.zdsoft.leadin.event.BaseEvent;

public class UserEvent implements BaseEvent {

    public static final int USER_SAVE = 1;// 用户新增
    public static final int USER_UPDATE = 2;// 用户修改
    public static final int USER_DELETE = 3;// 用户删除
    public static final int USER_DIMISSION = 4;// 用户离职锁定

    private int eventType;
    private String[] userIds;

    public UserEvent(String[] userIds, int eventType) {
        this.userIds = userIds;
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

    public String[] getUserIds() {
        return userIds;
    }

}
