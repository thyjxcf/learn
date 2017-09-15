package net.zdsoft.eis.base.data.sync.service.impl;

import com.winupon.syncdata.basedata.entity.son.MqOrder;

import net.zdsoft.eis.base.common.entity.Order;
import net.zdsoft.eis.base.data.service.BaseOrderService;
import net.zdsoft.eis.base.sync.AbstractHandlerTemplate;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.leadin.exception.BusinessErrorException;

public class OrderSyncServiceImpl extends AbstractHandlerTemplate<Order, MqOrder> {
private BaseOrderService baseOrderService;

	public void setBaseOrderService(BaseOrderService baseOrderService) {
	this.baseOrderService = baseOrderService;
}

	@Override
	public void addData(Order e) throws BusinessErrorException {
		baseOrderService.addOrder(e);
		
	}

	@Override
	public void deleteData(String id, EventSourceType eventSource) throws BusinessErrorException {
		baseOrderService.deleteOrder(new String[]{id}, eventSource);
		
	}

	@Override
	public Order fetchOldEntity(String id) {
		return null;
	}

	@Override
	public void updateData(Order e) throws BusinessErrorException {
		baseOrderService.updateOrder(e);
		
	}

}
