package net.zdsoft.eis.base.data.sync.converter;

import com.winupon.syncdata.basedata.entity.son.MqOrder;

import net.zdsoft.eis.base.common.entity.Order;
import net.zdsoft.eis.base.sync.SyncObjectConvertable;

public class OrderConverter implements SyncObjectConvertable<Order, MqOrder> {
	@Override
	public void toEntity(MqOrder m, Order e) {
		// e.setIsdeleted(m.isdeleted());
		// e.setModifyTime(m.getModifyTime());
		// e.setCreationTime(m.getCreationTime());
		e.setCustomerId(m.getCustomerId());
		e.setCustomerType(m.getCustomerType());
		e.setWareId(m.getWareId());
		e.setStartTime(m.getStartTime());
		e.setEndTime(m.getEndTime());
		e.setState(m.getState());
		e.setServerId((long) (m.getServerId()));
		e.setPayType(m.getPayType());
		e.setRegionCode(m.getRegionCode());
		e.setId(m.getId());
	}

	@Override
	public void toMq(Order e, MqOrder m) {
		m.setCustomerId(e.getCustomerId());
		m.setCustomerType(e.getCustomerType());
		m.setWareId(e.getWareId());
		m.setStartTime(e.getStartTime());
		m.setEndTime(e.getEndTime());
		m.setState(e.getState());
		m.setServerId((e.getServerId()).intValue());
		m.setPayType(e.getPayType());
		m.setRegionCode(e.getRegionCode());
		m.setId(e.getId());

	}

}
