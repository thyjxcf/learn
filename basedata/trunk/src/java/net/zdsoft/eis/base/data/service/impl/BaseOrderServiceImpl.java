package net.zdsoft.eis.base.data.service.impl;


import net.zdsoft.eis.base.common.entity.Order;
import net.zdsoft.eis.base.data.dao.BaseOrderDao;
import net.zdsoft.eis.base.data.service.BaseOrderService;
import net.zdsoft.eis.base.sync.EventSourceType;

public class BaseOrderServiceImpl implements BaseOrderService {
	private BaseOrderDao baseOrderDao;

	public void setBaseOrderDao(BaseOrderDao baseOrderDao) {
		this.baseOrderDao = baseOrderDao;
	}

	@Override
	public void deleteOrder(String[] ids, EventSourceType eventSource) {
		baseOrderDao.deleteOrder(ids, eventSource);

	}

	@Override
	public void addOrder(Order order) {
		baseOrderDao.insertOrder(order);
	}

	@Override
	public void updateOrder(Order order) {
		baseOrderDao.updateOrder(order);

	}

}
