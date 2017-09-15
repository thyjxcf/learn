/* 
 * @(#)RequestUtils.java    Created on Sep 17, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.util;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Sep 17, 2010 5:47:18 PM $
 */
public class RequestUtils {
	private static final String UNKNOWN = "unknown";
	private static final String X_FORWARDED_FOR_HEADER = "x-forwarded-for";
	private static final String X_REAL_IP_HEADER = "x-real-ip";
	private static final String PROXY_CLIENT_IP_HEADER = "Proxy-Client-IP";
	private static final String WL_PROXY_CLIENT_IP_HEADER = "WL-Proxy-Client-IP";

    /**
     * 在服务器使用多级反向代理(例如Squid)的情况下, 直接调用 {@link ServletRequest#getRemoteAddr()}
     * 方法将无法获取客户端真实的IP地址. 而使用此方法可以获取到客户端真实的IP地址.
     * 
     * @param request 请求对象
     * @return 请求客户端的真实IP地址.
     */
    public static String getRealRemoteAddr(HttpServletRequest request) {
		String remoteAddr = request.getHeader(X_FORWARDED_FOR_HEADER);

		if (remoteAddr == null || remoteAddr.trim().length() == 0
				|| UNKNOWN.equalsIgnoreCase(remoteAddr)) {
			remoteAddr = request.getHeader(X_REAL_IP_HEADER);
		}

		if (remoteAddr == null || remoteAddr.trim().length() == 0
				|| UNKNOWN.equalsIgnoreCase(remoteAddr)) {
			remoteAddr = request.getHeader(PROXY_CLIENT_IP_HEADER);
		}

		if (remoteAddr == null || remoteAddr.trim().length() == 0
				|| UNKNOWN.equalsIgnoreCase(remoteAddr)) {
			remoteAddr = request.getHeader(WL_PROXY_CLIENT_IP_HEADER);
		}

		if (remoteAddr == null || remoteAddr.trim().length() == 0
				|| UNKNOWN.equalsIgnoreCase(remoteAddr)) {
			remoteAddr = request.getRemoteAddr();
		}

		if (remoteAddr != null && remoteAddr.trim().length() != 0) {
			String[] remoteAddrs = remoteAddr.split(",");
			remoteAddr = remoteAddrs[0].trim();
		}

		return remoteAddr;
	}

}
