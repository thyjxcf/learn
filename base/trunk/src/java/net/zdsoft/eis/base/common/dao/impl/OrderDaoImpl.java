package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.dao.OrderDao;
import net.zdsoft.eis.base.common.entity.Order;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.util.DateUtils;

public class OrderDaoImpl extends BaseDao<Order> implements OrderDao {
    private static final String SQL_FIND_ORDER_BY_CUSTOMERID = "SELECT * FROM base_order "
            + "WHERE customer_id=? AND customer_type=? AND state=1 AND is_deleted=0 "
            + "AND (trunc(start_time,'DD') <= to_date(?,'yyyy-mm-dd') AND trunc(end_time,'DD') >=to_date(?,'yyyy-mm-dd'))";

    private static final String SQL_FIND_ORDER_BY_CUSTOMERIDS = "SELECT * FROM base_order "
        + "WHERE customer_type=? AND state=1 AND is_deleted=0 "
        + "AND (trunc(start_time,'DD') <= to_date(?,'yyyy-mm-dd') AND trunc(end_time,'DD') >=to_date(?,'yyyy-mm-dd')) AND customer_id IN ";

    private static final String SQL_FIND_ORDER_BY_SERVERID = "SELECT * FROM base_order WHERE is_deleted=0 AND server_id=? AND customer_id IN ";

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

    public List<Order> getOrderListByUser(String userId) {
        String formattedTime = DateUtils.date2StringByDay(new Date());

        return query(SQL_FIND_ORDER_BY_CUSTOMERID, new Object[] { userId, 1, formattedTime,
                formattedTime }, new MultiRow());
    }

    public Map<String, Boolean> getOrderMapByUser(String[] userIds) {
        String formattedTime = DateUtils.date2StringByDay(new Date());
        return queryForInSQL(SQL_FIND_ORDER_BY_CUSTOMERIDS, new Object[] { 1, formattedTime,
                formattedTime }, userIds, new MapRowMapper<String, Boolean>() {

            public String mapRowKey(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("customer_id");
            }

            public Boolean mapRowValue(ResultSet rs, int rowNum) throws SQLException {
                return Boolean.TRUE;
            }
        });
    }
    
    public List<Order> getOrderListByUnit(String unitId) {
        String formattedTime = DateUtils.date2StringByDay(new Date());
        return query(SQL_FIND_ORDER_BY_CUSTOMERID, new Object[] { unitId, 0, formattedTime,
                formattedTime }, new MultiRow());
    }

    public List<Order> getServiceOrder(int serverId, String... customerIds) {
        return queryForInSQL(SQL_FIND_ORDER_BY_SERVERID, new Object[] { serverId}, customerIds,
                new MultiRow());
    }

}
