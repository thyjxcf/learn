/* 
 * @(#)TeachRes.java    Created on May 20, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.entity;

import net.zdsoft.eis.frame.client.BaseEntity;

/**
 * 教学资源
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 20, 2011 10:28:19 AM $
 */
public class TeachRes extends BaseEntity {
    private static final long serialVersionUID = -239213497782911754L;

    private String resName;

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

}
