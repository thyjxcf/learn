/* 
 * @(#)SystemDeployConstant.java    Created on Sep 20, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.deploy;

import net.zdsoft.keel.util.SecurityUtils;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Sep 20, 2010 10:33:18 AM $
 */
public final class SystemDeployUtils {
    /**
     * 当前部署的子系统
     */
    private static String currentDeployAppCode;

    public static String getCurrentDeployAppCode() {
        return currentDeployAppCode;
    }

    public static void initCurrentDeployAppCode(String currentDeployAppCode) {
        SystemDeployUtils.currentDeployAppCode = currentDeployAppCode;
    }

    /**
     * 和运营平台约定的字符加密串
     */
    public static final String OA_SUPPORT_VERIFY_CODE = "4028808B1B24A02A011B24A02A640000";

    public static final int OA_SUPPORT_VERIFY_LENGTH = 13;

    public static String getEssVerifyKey() {
        // code = 32位密文+时间戳
        // 密文 = MD5（数字校园与业务系统约定字符串 + 时间戳）
        String updatestamp = String.valueOf(System.currentTimeMillis());
        String verifyKey = SecurityUtils.encodeByMD5(SystemDeployUtils.OA_SUPPORT_VERIFY_CODE
                + updatestamp)
                + updatestamp;
        return verifyKey;
    }

}
