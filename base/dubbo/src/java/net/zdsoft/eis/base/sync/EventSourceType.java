/* 
 * @(#)EventSourceType.java    Created on Dec 23, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.sync;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 23, 2010 7:30:57 PM $
 */
public enum EventSourceType {
    LOCAL(0), OTHER(1);

    private int value;

    EventSourceType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getStringValue() {
        return String.valueOf(value);
    }

    public static EventSourceType valueOf(int value) {
        EventSourceType key = null;
        switch (value) {
        case 0:
            key = LOCAL;
            break;
        case 1:
            key = OTHER;
            break;
        default:
            key = LOCAL;
            break;
        }
        return key;
    }

    public String getDescription() {
        String desc = null;
        switch (this) {
        case LOCAL:
            desc = "本地来源";
            break;
        case OTHER:
            desc = "其它来源";
            break;
        default:
            desc = "本地来源";
            break;
        }
        return desc;
    }

    @Override
    public String toString() {
        return getDescription();
    }

}
