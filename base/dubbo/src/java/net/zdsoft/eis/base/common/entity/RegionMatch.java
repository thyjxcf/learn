/* 
 * @(#)BasicGrade.java    Created on 2007-7-17
 * Copyright (c) 2006 ZDSoft Networks, Inc. All rights reserved.
 * $Header$
 */
package net.zdsoft.eis.base.common.entity;

import net.zdsoft.eis.frame.client.BaseEntity;

public class RegionMatch extends BaseEntity {
	private static final long serialVersionUID = 1206770150063040398L;
	
	private String regionCode;
	private String regionCodeMatch;

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getRegionCodeMatch() {
		return regionCodeMatch;
	}

	public void setRegionCodeMatch(String regionCodeMatch) {
		this.regionCodeMatch = regionCodeMatch;
	}

}
