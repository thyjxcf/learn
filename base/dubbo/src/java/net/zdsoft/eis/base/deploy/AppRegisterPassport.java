/* 
 * @(#)Passport.java    Created on Sep 19, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.deploy;

/**
 * 应用系统在passport中的注册信息
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Sep 19, 2010 11:21:44 AM $
 */
public class AppRegisterPassport {
    private int serverId;
    private String verifyKey;

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public String getVerifyKey() {
        return verifyKey;
    }

    public void setVerifyKey(String verifyKey) {
        this.verifyKey = verifyKey;
    }

}
