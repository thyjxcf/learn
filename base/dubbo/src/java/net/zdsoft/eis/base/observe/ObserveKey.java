/* 
 * @(#)ObserveKey.java    Created on Sep 14, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.observe;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Sep 14, 2010 2:51:45 PM $
 */
public enum ObserveKey {
    UNKNOWN(0),
    /** 业务角色 */
    BUSINESSROLE(1);

    private int value;

    ObserveKey(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getStringValue() {
        return String.valueOf(value);
    }

    public static ObserveKey valueOf(int value) {
        ObserveKey key = null;
        switch (value) {
        case 1:
            key = BUSINESSROLE;
            break;
        default:
            key = UNKNOWN;
            break;
        }
        return key;
    }

    public String getDescription() {
        String desc = null;
        switch (this) {
        case BUSINESSROLE:
            desc = "业务角色";
            break;
        default:
            desc = "未知";
            break;
        }
        return desc;
    }

    @Override
    public String toString() {
        return getDescription();
    }

}
