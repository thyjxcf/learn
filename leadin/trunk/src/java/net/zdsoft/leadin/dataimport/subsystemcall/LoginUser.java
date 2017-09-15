/* 
 * @(#)LoginUser.java    Created on May 28, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.dataimport.subsystemcall;

import java.io.Serializable;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 28, 2010 3:04:14 PM $
 */
public class LoginUser implements Serializable {
    private static final long serialVersionUID = -8073372403042895504L;
    
    private String userId;
    private String unitId;
    private String skin;//个人皮肤
    private String layout;// 桌面布局
    private String bgColor;// 桌面背景颜色
    private String bgImg;// 桌面背景图片
    private String theme;// 主题

    private long userIntId;

    /**
     * @return Returns the userId.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @return Returns the unitId.
     */
    public String getUnitId() {
        return unitId;
    }

    /**
     * @return Returns the userIntId.
     */
    public long getUserIntId() {
        return userIntId;
    }

    /**
     * @param userId The userId to set.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @param unitId The unitId to set.
     */
    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    /**
     * @param userIntId The userIntId to set.
     */
    public void setUserIntId(long userIntId) {
        this.userIntId = userIntId;
    }

	public String getSkin() {
		return skin;
	}

	public void setSkin(String skin) {
		this.skin = skin;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public String getBgColor() {
		return bgColor;
	}

	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

	public String getBgImg() {
		return bgImg;
	}

	public void setBgImg(String bgImg) {
		this.bgImg = bgImg;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

}
