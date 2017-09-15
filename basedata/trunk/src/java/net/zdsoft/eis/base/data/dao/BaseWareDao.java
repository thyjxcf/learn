package net.zdsoft.eis.base.data.dao;

import java.util.Set;

import net.zdsoft.eis.base.common.entity.Ware;
import net.zdsoft.eis.base.sync.EventSourceType;

public interface BaseWareDao {
    public void insertWare(Ware ware);

    public void deleteWare(String[] wareIds, EventSourceType eventSource);

    public void updateWare(Ware ware);

    /**
     * 取serverId
     * 
     * @param unitClass 单位类型
     * @param roleType 角色类型
     * @param state 状态
     * @param rule 规则
     * @return
     */
    public Set<String> getServerIds(int unitClass, int roleType, int state, int rule);

}
