/* 
 * @(#)PaymentException.java    Created on 2013-10-30
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.payment;

/**
 * 校验异常
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2013-10-30 下午06:12:10 $
 */
public class PaymentValidateException extends RuntimeException {
	private static final long serialVersionUID = -3844604953943895674L;

	private PaymentResultEnum result;

	public PaymentValidateException(PaymentResultEnum result) {
		super();
		this.result = result;
	}

	public PaymentValidateException(String message) {
		super(message);
	}

	public PaymentResultEnum getResult() {
		return result;
	}

}
