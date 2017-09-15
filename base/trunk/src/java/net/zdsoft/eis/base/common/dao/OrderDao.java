package net.zdsoft.eis.base.common.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.Order;

public interface OrderDao {

	/**
	 * 取有效时间内的个人订购商品列表
	 * @param userId
	 * @return
	 */
	public List<Order> getOrderListByUser(String userId);
	
	/**
     * 取有效时间内的个人是否订购了商品列表
     * 
     * @param userId
     * @return
     */
    public Map<String, Boolean> getOrderMapByUser(String[] userIds);
	
	/**
	 * 取有效时间内的单位订购商品列表
	 * @param unitId
	 * @return
	 */
	public List<Order> getOrderListByUnit(String unitId);
	
	/**
	 * 根据serverID和用户/单位id数组，检索订购的记录
	 * @param serverId
	 * @param customerIds
	 * @return
	 */
	public List<Order> getServiceOrder(int serverId, String... customerIds);
}


