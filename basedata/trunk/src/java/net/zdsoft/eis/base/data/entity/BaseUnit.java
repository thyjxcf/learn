/* 
 * @(#)BaseUnit.java    Created on Dec 13, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.data.entity;

import net.zdsoft.eis.base.common.entity.Unit;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 13, 2010 1:25:48 PM $
 */
public class BaseUnit extends Unit {
    private static final long serialVersionUID = 6424125651169981726L;

    //单位名称长度
    public static final int NAME_LENGTH = 64;

    // ===================================辅助字段==========================
    private String[] arrayIds;

    private String sysVersion;
    private String adminName;
    private String password;

    private String markName;// 单位状态名称
    private String unitclassName;// 单位分类名称
    private String relatingSubSys;

    private boolean restricted; // 受发送权限控制    
    private String srcParentId;//原来上级
    
    private String schCode; //学校代码

    private String regionName;  //行政区名称
    
    private String regionPropertyCode; //区域属性码
    /**
     * 学校Unionid长度
     */
    public static final int FINAL_SCH_LENGTH = 12;
    /**
     * 三级教育局unionid最大长度
     */
    public static final int FINAL_EDU_LENGHT = 6;

    /**
     * 第四级教育局Unionid新增部分长度
     */
    public static final int UNIONID_LENGTH_EDU4 = 3;

    /**
     * 学校只使用最末3位
     */
    public static final int SCHOOLE_UNION = 3;

    /**
     * 本地单位，非报送
     */

    /**
     * 未授权
     */
    public static final int UNIT_UNAUTHORIZED = 0;

    /**
     * 已授权
     */
    public static final int UNIT_AUTHORIZED = 1;

    /**
     * 附属授权
     */
    public static final int UNIT_APPAUTHORIZED = 2;

    public static final String UNIT_REGCODE_DEF = "default_regcode";
     
    public void setArrayIds(String[] arrayIds) {
        this.arrayIds = arrayIds;
    }

    public String[] getArrayIds() {
        return this.arrayIds;
    }

    public String getSysVersion() {
        return sysVersion;
    }

    public void setSysVersion(String sysVersion) {
        this.sysVersion = sysVersion;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMarkName() {
        return markName;
    }

    public void setMarkName(String markName) {
        this.markName = markName;
    }

    public String getUnitclassName() {
        return unitclassName;
    }

    public void setUnitclassName(String unitclassName) {
        this.unitclassName = unitclassName;
    }

    public boolean isRestricted() {
        return restricted;
    }

    public void setRestricted(boolean restricted) {
        this.restricted = restricted;
    }

    public String getRelatingSubSys() {
        return relatingSubSys;
    }

    public void setRelatingSubSys(String relatingSubSys) {
        this.relatingSubSys = relatingSubSys;
    }

    public String getSrcParentId() {
        return srcParentId;
    }

    public void setSrcParentId(String srcParentId) {
        this.srcParentId = srcParentId;
    }

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getSchCode() {
		return schCode;
	}

	public void setSchCode(String schCode) {
		this.schCode = schCode;
	}

	public String getRegionPropertyCode() {
		return regionPropertyCode;
	}

	public void setRegionPropertyCode(String regionPropertyCode) {
		this.regionPropertyCode = regionPropertyCode;
	}
}
