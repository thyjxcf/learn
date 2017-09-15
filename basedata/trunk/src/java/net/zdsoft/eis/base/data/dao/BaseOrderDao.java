package net.zdsoft.eis.base.data.dao;


import net.zdsoft.eis.base.common.entity.Order;
import net.zdsoft.eis.base.sync.EventSourceType;

public interface BaseOrderDao {
	public void insertOrder(Order order);

	public void deleteOrder(String[] ids, EventSourceType eventSource);

	public void updateOrder(Order order);
}
