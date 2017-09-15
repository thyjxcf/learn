/* 
 * @(#)PushMessageTargetItem.java    Created on 2009-8-7
 * Copyright (c) 2009 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.office.bulletin.entity;

import java.io.Serializable;

/**
 * 信息推送时，推送的目标条目
 * 
 * @author zhuhf
 * @version $Revision: 1.0 $, $Date: 2009-8-7 上午09:53:18 $
 */
public class PushMessageTargetItem implements Serializable {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 7646573082788962642L;

    public static final String NONE_PUSH_MESSAGE_ITEM_ID = "0";

    private String id;
    private String name;

    public PushMessageTargetItem(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
