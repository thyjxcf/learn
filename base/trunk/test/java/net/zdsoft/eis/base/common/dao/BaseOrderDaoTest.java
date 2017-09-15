package net.zdsoft.eis.base.common.dao;

import java.util.List;

import net.zdsoft.eis.base.common.dao.OrderDao;
import net.zdsoft.eis.base.common.entity.Order;

public class BaseOrderDaoTest extends BaseDaoTestCase {

    private OrderDao orderDao;

    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    // public void testSelect() {
    // List list = baseOrderDao.findAll();
    // for(int i=0;i<list.size();i++) {
    // BaseOrder order = (BaseOrder)list.get(i);
    // System.out.println(order.getCustomerId());
    // }
    //		
    // //this.setComplete();
    // }

    public void testGetOrderListByUser() {
        String userId = "402880dd2069cdba012069eb8c46001f";
        List<Order> list = orderDao.getOrderListByUser(userId);
        for (Order order : list) {
            System.out.println("startime:" + order.getStartTime() + ";endtime:"
                    + order.getEndTime());
        }

    }

}
