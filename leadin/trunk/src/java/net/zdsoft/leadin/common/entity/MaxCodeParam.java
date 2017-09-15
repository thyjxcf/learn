/* 
 * @(#)MaxCode.java    Created on Oct 14, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.common.entity;

/**
 * 参数数据
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Oct 14, 2010 5:47:39 PM $
 */
public class MaxCodeParam {
    private String unitId;// 单位Id
    private String prefix;// 前缀
    private int serialLen;// 流水号长度

    public MaxCodeParam(String prefix, int serialLen) {
        this.prefix = prefix;
        this.serialLen = serialLen;
    }

    public String getUnitId() {
        return unitId;
    }

    public MaxCodeParam setUnitId(String unitId) {
        this.unitId = unitId;
        return this;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getSerialLen() {
        return serialLen;
    }

}
