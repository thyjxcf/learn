/* 
 * @(#)InvalidateAction.java    Created on 2006-9-4
 * Copyright (c) 2005 ZDSoft.net, Inc. All rights reserved.
 * $Header: /project/member/src/net/zdsoft/member/action/InvalidateAction.java,v 1.3 2006/09/05 01:24:12 liangxiao Exp $
 */
package net.zdsoft.eis.homepage.action;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.eis.frame.passport.SessionManager;
import net.zdsoft.keel.util.ServletUtils;
import net.zdsoft.keel.util.Validators;
import net.zdsoft.passport.service.client.PassportClient;

public class InvalidateAction extends BaseAction {
	private static final long serialVersionUID = -6154521081540164730L;

	private String ticket;
	private String auth;

	public InvalidateAction() {
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String execute() throws Exception {
		log.debug("ticket[" + ticket + "],auth[" + auth + "]");

		HttpServletResponse response = getResponse();

		if (Validators.isEmpty(ticket) || Validators.isEmpty(auth)) {
			ServletUtils.print(response, ERROR);
			return ERROR;
		}

		if (!PassportClient.getInstance().isValidInvalidateAuth(ticket, auth)) {
			log.debug("Invalid auth[" + auth + "]");
			ServletUtils.print(response, ERROR);
			return ERROR;
		}

		// 从ticket2SessionMap中通过ticket取出session，使其失效
		HttpSession session = SessionManager.removeTicketInMap(ticket);
		if (session != null) {
			// 清除ticket是为了在session失效的时候不再重复通知中心
			try {
				session.removeAttribute("ticket");
				session.invalidate();
			} catch (Exception e) {

			}
		}
		getRequest().getSession().removeAttribute(BaseConstant.SESSION_LOGININFO);
		getRequest().getSession().removeAttribute(BaseConstant.SESSION_LOGINUSER);
		getRequest().getSession().invalidate();
		// 输出success字符串
		ServletUtils.print(response, SUCCESS);
		return null;
	}

}
