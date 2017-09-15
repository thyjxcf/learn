/* 
 * @(#)EisuStudent.java    Created on May 16, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.entity;

import net.zdsoft.eis.base.simple.entity.SimpleStudent;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 16, 2011 11:36:34 AM $
 */
public class EisuStudent extends SimpleStudent {
    private static final long serialVersionUID = -1453103484578246077L;

    // ===========================辅助字段===============
    private String specName;// 专业名称
    private String specpointName;// 专业方向名称

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public String getSpecpointName() {
        return specpointName;
    }

    public void setSpecpointName(String specpointName) {
        this.specpointName = specpointName;
    }

}
