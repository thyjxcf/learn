package net.zdsoft.eis.base.data.service;


import net.zdsoft.eis.base.common.entity.Order;
import net.zdsoft.eis.base.sync.EventSourceType;

public interface BaseOrderService {
	public void addOrder(Order order);

	public void deleteOrder(String[] ids, EventSourceType eventSource);

	public void updateOrder(Order order);
}
