/* 
 * @(#)MessageDigestUtils.java    Created on Jun 13, 2012
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.keel.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jun 13, 2012 10:09:46 AM $
 */
public class MessageDigestUtils {
    /**
     * 加密
     * 
     * @param str
     * @param algorithm
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String encode(String str, String algorithm) throws NoSuchAlgorithmException {
        String encodeStr = "";

        MessageDigest alga = MessageDigest.getInstance(algorithm);
        alga.update(str.getBytes());
        byte[] digest = alga.digest();

        encodeStr = byte2hex(digest);
        return encodeStr;
    }

    /**
     * 二行制转字符串
     * 
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs.toUpperCase();
    }

}
