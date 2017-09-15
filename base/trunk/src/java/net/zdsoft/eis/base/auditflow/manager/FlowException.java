/* 
 * @(#)FlowException.java    Created on 2012-11-23
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.auditflow.manager;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2012-11-23 上午10:42:56 $
 */
public class FlowException extends RuntimeException {
	private static final long serialVersionUID = 8327331589406010561L;

	public FlowException(String message) {
		super(message);
	}
}
