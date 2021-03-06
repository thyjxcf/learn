package net.zdsoft.eis.base.common.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.TypeReference;

import net.zdsoft.eis.base.util.SUtils;
import net.zdsoft.eis.frame.client.BaseEntity;

/**
 * BaseOrder generated by MyEclipse Persistence Tools
 */

public class Order extends BaseEntity {

	public static List<Order> dt(String data) {
		List<Order> ts = SUtils.dt(data, new TypeReference<List<Order>>() {
		});
		if (ts == null)
			ts = new ArrayList<Order>();
		return ts;

	}

	public static Order dc(String data) {
		return SUtils.dc(data, Order.class);
	}
	
    private static final long serialVersionUID = -5898796445057790447L;
    // Fields

    private String customerId;
    private int customerType;
    private String wareId;
    private Date startTime;
    private Date endTime;
    private int state;
    private Long serverId;
    private int payType;
    private String regionCode;

    // Constructors

    /** default constructor */
    public Order() {
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getCustomerType() {
        return customerType;
    }

    public void setCustomerType(int customerType) {
        this.customerType = customerType;
    }

    public String getWareId() {
        return wareId;
    }

    public void setWareId(String wareId) {
        this.wareId = wareId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

}
