/* 
 * @(#)PayWare.java    Created on 2013-10-31
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.payment.entity;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2013-10-31 下午01:42:59 $
 */
public interface PayWare {
	public String getId();

	public String getSubject();

	public String getBody();

	public String getWareType();

	public double getPrice();

	public int getStatus(); //是否已过有效期	
	
}
