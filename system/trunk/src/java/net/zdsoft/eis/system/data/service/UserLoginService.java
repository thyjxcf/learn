/* 
 * @(#)UserLoginService.java    Created on Sep 13, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.system.data.service;

import net.zdsoft.eis.frame.client.LoginInfo;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Sep 13, 2010 2:30:02 PM $
 */
public interface UserLoginService {

    /**
     * 初始化loginInfo
     * 
     * @param uid 用户姓名
     * @return
     */
    public LoginInfo initLoginInfo(String uid);
}
