/* 
 * @(#)SimpleGroup.java    Created on May 28, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.simple.entity;

import net.zdsoft.eis.frame.client.BaseEntity;

/**
 * 分组：班级或部门
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 28, 2011 5:50:49 PM $
 */
public class SimpleGroup extends BaseEntity {
    private static final long serialVersionUID = -2019112962959788046L;

    private String groupCode;// 代码
    private String groupName;// 名称
    private String count;// 人数

    public SimpleGroup(String id, String groupCode, String groupName) {
        this.id = id;
        this.groupCode = groupCode;
        this.groupName = groupName;
    }

    public SimpleGroup(String id, String groupCode, String groupName, String count) {
        this(id, groupCode, groupName);
        this.count = count;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

}
