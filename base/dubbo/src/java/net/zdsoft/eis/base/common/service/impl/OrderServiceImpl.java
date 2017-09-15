package net.zdsoft.eis.base.common.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.dao.OrderDao;
import net.zdsoft.eis.base.common.entity.Order;
import net.zdsoft.eis.base.common.entity.Server;
import net.zdsoft.eis.base.common.entity.SubSystem;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.entity.Ware;
import net.zdsoft.eis.base.common.service.OrderService;
import net.zdsoft.eis.base.common.service.ServerService;
import net.zdsoft.eis.base.common.service.SubSystemService;
import net.zdsoft.eis.base.common.service.WareService;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

public class OrderServiceImpl implements OrderService {
	
	private OrderDao orderDao;
	private WareService wareService;
	private SubSystemService subSystemService;
	private ServerService serverService;
	
	public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public void setSubSystemService(SubSystemService subSystemService) {
        this.subSystemService = subSystemService;
    }

    public void setWareService(WareService wareService) {
        this.wareService = wareService;
    }

    public void setServerService(ServerService serverService) {
        this.serverService = serverService;
    }

    public Set<Integer> getOrderSystem(String userId,int ownerType,String unitId) {
		
		Set<Integer> systemMap = new HashSet<Integer>();
		List<Order> userOrders = orderDao.getOrderListByUser(userId);
		List<Order> unitOrders = orderDao.getOrderListByUnit(unitId);
		
		List<Order> allOrders = new ArrayList<Order>();
		allOrders.addAll(userOrders);
		allOrders.addAll(unitOrders);
			
		Set<String> wareIdSet = new HashSet<String>();
		for(Order order : allOrders) {
			wareIdSet.add(order.getWareId());
		}
		String[] wareIds = wareIdSet.toArray(new String[wareIdSet.size()]);
		 Map<String,Ware> wareMap = wareService.getWares(wareIds);
		for(Order order : unitOrders) {
			Ware ware = wareMap.get(order.getWareId());
			if (ware == null) {
				continue;
			}
			Integer type = ware.getOrderType();
			//'单位订购个人免费'和'免费'类型则属此个人订购了该商品
			if(type == null || type == 1 || type == 4) {
				userOrders.add(order);
			}
		}
		
		List<Order> retOrders = new ArrayList<Order>();
		for(Order order : userOrders) {
			Ware ware = wareMap.get(order.getWareId());
			String role = ware.getRole();
			if(StringUtils.isBlank(role)) {
				retOrders.add(order);
				continue;
			}
			String[] roles = role.split(",");
			if(ArrayUtils.contains(roles, "-1")) {
				retOrders.add(order);
				continue;
			}
			
			if(User.STUDENT_LOGIN == ownerType || User.FAMILY_LOGIN == ownerType) {
				if(ArrayUtils.contains(roles, String.valueOf(ownerType))) {
					retOrders.add(order);
					continue;
				}
			}else if(User.TEACHER_LOGIN == ownerType) {
				if(ArrayUtils.contains(roles, String.valueOf(ownerType))
						|| ArrayUtils.contains(roles, String.valueOf(User.TEACHER_EDU_LOGIN))) {
					retOrders.add(order);
					continue;
				}
			} 				
		}
		
		Map<Integer, SubSystem> allSubSytem = subSystemService.getCacheSubSystemMap();
		Map<String, Integer> mapOfSubsystemCode = new HashMap<String, Integer>();
		for(Integer id : allSubSytem.keySet()) {
			SubSystem system = allSubSytem.get(id);
			mapOfSubsystemCode.put(system.getCode(), id);
		}
			
		Map<Long, String> serMap = serverService.getServerCodeMap();
		for(Order order : retOrders) {
			Ware ware = wareMap.get(order.getWareId());		
			String subsystemCode = serMap.get(ware.getServerId());
			if(subsystemCode == null)
				continue;
			
			if(mapOfSubsystemCode.get(subsystemCode) != null) {
				systemMap.add(mapOfSubsystemCode.get(subsystemCode));
			}
		}
		return systemMap;
	}
	
	public String getFreePeriodString(String subSystemCode, String unitId, String userId, int ownerType) {
		Set<Integer> orderSet = getOrderSystem(userId, ownerType, unitId);
		Long id = subSystemService.getSubSystemByCode(subSystemCode).getId();
		if (!orderSet.contains(id)){
			return "该功能未订购";	
		}
	
		Map<Long, String> serverMap = serverService.getServerCodeMap();
		int serverId = 0;
		for(Long sid : serverMap.keySet()){
			String scode = serverMap.get(sid);
			if (StringUtils.isNotBlank(scode)){
				if (scode.equalsIgnoreCase(subSystemCode)){
					serverId = sid.intValue();
					break;
				}
			}
		}
		List<Order> orders = orderDao.getServiceOrder(serverId, unitId);
		if (orders == null || orders.size() == 0)
			return "该功能未订购";

		class orderComparator implements Comparator<Order> {
			public int compare(Order o1, Order o2) {
				Date startDate1 = o1.getStartTime();
				Date startDate2 = o2.getStartTime();
				if (startDate1.after(startDate2))
					return 1;
				else if (startDate1.before(startDate2))
					return -1;
				return 0;
			}
		}
		boolean hasNullDate = false;
		for (Order order : orders) {
			if (order.getStartTime() == null || order.getEndTime() == null) {
				hasNullDate = true;
				break;
			}
		}
		if (hasNullDate)
			return "免费";
		Collections.sort(orders, new orderComparator());
		DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
		String startStr = "";
		String endStr = "";
		int lastIndex = orders.size() - 1;
		startStr = dFormat.format(orders.get(0).getStartTime());
		endStr = dFormat.format(orders.get(lastIndex).getEndTime());
		return "免费:" + startStr + "至" + endStr;
	}
	
    public Map<String, Boolean> getOrdersByUser(String[] userIds){
        return orderDao.getOrderMapByUser(userIds);
    }

    @Override
    public List<Server> getOrderServer(String userId, int ownerType, String unitId) {
        
        List<Order> userOrders = orderDao.getOrderListByUser(userId);
        List<Order> unitOrders = orderDao.getOrderListByUnit(unitId);
        
        List<Order> allOrders = new ArrayList<Order>();
        allOrders.addAll(userOrders);
        allOrders.addAll(unitOrders);
        
        Set<String> wareIdSet = new HashSet<String>();
        for (Order order : allOrders) {
            wareIdSet.add(order.getWareId());
        }
        
        String[] wareIds = wareIdSet.toArray(new String[wareIdSet.size()]);
        Map<String, Ware> wareMap = wareService.getWares(wareIds);
        for (Order order : unitOrders) {
            Ware ware = wareMap.get(order.getWareId());
            if (ware == null) {
                continue;
            }
            Integer type = ware.getOrderType();
            // '单位订购个人免费'和'免费'类型则属此个人订购了该商品
            if (type == null || type == 1 || type == 4) {
                userOrders.add(order);
            }
        }
        
        List<Order> retOrders = new ArrayList<Order>();
        for (Order order : userOrders) {
            Ware ware = wareMap.get(order.getWareId());
            String role = ware.getRole();
            if (StringUtils.isBlank(role)) {
                retOrders.add(order);
                continue;
            }
            String[] roles = role.split(",");
            if (ArrayUtils.contains(roles, "-1")) {
                retOrders.add(order);
                continue;
            }
            
            if (User.STUDENT_LOGIN == ownerType
                    || User.FAMILY_LOGIN == ownerType) {
                if (ArrayUtils.contains(roles, String.valueOf(ownerType))) {
                    retOrders.add(order);
                    continue;
                }
            } else if (User.TEACHER_LOGIN == ownerType) {
                if (ArrayUtils.contains(roles, String.valueOf(ownerType))
                        || ArrayUtils.contains(roles,
                                String.valueOf(User.TEACHER_EDU_LOGIN))) {
                    retOrders.add(order);
                    continue;
                }
            }
        }
        
        List<Server> servers = new ArrayList<Server>();
        Map<Long, Server> serMap = serverService.getServerMap();
        for (Order order : retOrders) {
            Ware ware = wareMap.get(order.getWareId());
            Server server = serMap.get(ware.getServerId());
            if (server == null)
                continue;
            servers.add(server);
        }
        return servers;
    }
}
