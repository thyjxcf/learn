package net.zdsoft.eis.base.common.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.Server;

public interface OrderService {

    /**
     * 个人订购了哪些子系统
     * 
     * @param userId
     * @param ownerType
     * @param unitId
     * @return
     */
    public Set<Integer> getOrderSystem(String userId, int ownerType, String unitId);

    /**
     * 个人订购了哪些子系统
     * 
     * @param userId
     * @param ownerType
     * @param unitId
     * @return
     */
    public List<Server> getOrderServer(String userId, int ownerType, String unitId);
    
    /**
     * 根据单位/用户id，取出订购记录描述信息，用于传送给办公中心嵌入页
     * 
     * @param subSystemCode
     * @param unitId
     * @param userId
     * @param ownerType
     * @return
     */
    public String getFreePeriodString(String subSystemCode, String unitId, String userId,
            int ownerType);

    /**
     * 取有效时间内的个人是否订购了商品列表
     * 
     * @param userId
     * @return
     */
    public Map<String, Boolean> getOrdersByUser(String[] userIds);
}
