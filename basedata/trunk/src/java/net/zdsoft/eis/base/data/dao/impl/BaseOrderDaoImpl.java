package net.zdsoft.eis.base.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.entity.Order;
import net.zdsoft.eis.base.data.dao.BaseOrderDao;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.eis.frame.client.BaseDao;

public class BaseOrderDaoImpl extends BaseDao<Order> implements BaseOrderDao {
	private static final String SQL_INSERT_ORDER = "INSERT INTO base_order(id,customer_id,customer_type,"
			+ "ware_id,start_time,end_time,state,creation_time,"
			+ "server_id,pay_type,region_code,event_source,is_deleted) "
			+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private static final String SQL_DELETE_ORDER_BY_IDS = "UPDATE base_order SET is_deleted = 1,event_source=? WHERE id IN ";

	private static final String SQL_UPDATE_ORDER = "UPDATE base_order SET customer_id=?,customer_type=?,"
			+ "ware_id=?,start_time=?,end_time=?,state=?,"
			+ "server_id=?,pay_type=?,region_code=?,event_source=?,is_deleted=? WHERE id=?";

	public Order setField(ResultSet rs) throws SQLException {
		Order order = new Order();
		order.setId(rs.getString("id"));
		order.setCustomerId(rs.getString("customer_id"));
		order.setCustomerType(rs.getInt("customer_type"));
		order.setWareId(rs.getString("ware_id"));
		order.setStartTime(rs.getTimestamp("start_time"));
		order.setEndTime(rs.getTimestamp("end_time"));
		order.setState(rs.getInt("state"));
		order.setCreationTime(rs.getTimestamp("creation_time"));
		order.setServerId(rs.getLong("server_id"));
		order.setPayType(rs.getInt("pay_type"));
		order.setRegionCode(rs.getString("region_code"));
		return order;
	}

	@Override
	public void deleteOrder(String[] ids, EventSourceType eventSource) {
		updateForInSQL(SQL_DELETE_ORDER_BY_IDS, new Object[] { eventSource
				.getValue() }, ids);
	}

	@Override
	public void insertOrder(Order order) {
		if (StringUtils.isEmpty(order.getId())) {
			order.setId(createId());
		}
		order.setCreationTime(new Date());
		order.setModifyTime(new Date());
		order.setIsdeleted(false);

		update(SQL_INSERT_ORDER, new Object[] { order.getId(),
				order.getCustomerId(), order.getCustomerType(),
				order.getWareId(), order.getStartTime(), order.getEndTime(),
				order.getState(), order.getCreationTime(), order.getServerId(),
				order.getPayType(), order.getRegionCode(),
				order.getEventSourceValue(), order.getIsdeleted() ? 1 : 0 },
				new int[] { Types.CHAR, Types.CHAR, Types.INTEGER, Types.CHAR,
						Types.DATE, Types.DATE, Types.INTEGER, Types.TIMESTAMP,
						Types.INTEGER, Types.INTEGER, Types.CHAR,
						Types.INTEGER, Types.INTEGER });

	}

	@Override
	public void updateOrder(Order order) {
		update(SQL_UPDATE_ORDER, new Object[] { order.getCustomerId(),
				order.getCustomerType(), order.getWareId(),
				order.getStartTime(), order.getEndTime(), order.getState(),
				order.getServerId(),
				order.getPayType(), order.getRegionCode(),
				order.getEventSourceValue(), order.getIsdeleted() ? 1 : 0,
				order.getId() }, new int[] { Types.CHAR, Types.INTEGER,
				Types.CHAR, Types.DATE, Types.DATE, Types.INTEGER, 
				Types.INTEGER, Types.INTEGER, Types.CHAR, Types.INTEGER,
				Types.INTEGER, Types.CHAR });

	}

}
