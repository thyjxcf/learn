/* 
 * @(#)PayOrder.java    Created on 2013-10-30
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.payment.entity;

import java.util.Date;

import net.zdsoft.eis.frame.client.BaseEntity;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2013-10-30 下午05:38:52 $
 */
public class PayOrder extends BaseEntity {
	private static final long serialVersionUID = 3317959540252450874L;

	private String subject;
	private String body;
	private String buyerId;
	private String wareId;
	private String wareType;
	private double price;
	private int quantity;
	private Date creationTime;
	private Date paymentTime;
	private int status;
	private String remark;

	/**
	 * 订单类型
	 * 
	 * @return
	 */
	public int getOrderType() {
		// TODO
		return 0;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public String getWareId() {
		return wareId;
	}

	public void setWareId(String wareId) {
		this.wareId = wareId;
	}

	public String getWareType() {
		return wareType;
	}

	public void setWareType(String wareType) {
		this.wareType = wareType;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Date getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
