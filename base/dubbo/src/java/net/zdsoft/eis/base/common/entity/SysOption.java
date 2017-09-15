/* 
 * @(#)BasicGrade.java    Created on 2007-7-17
 * Copyright (c) 2006 ZDSoft Networks, Inc. All rights reserved.
 * $Header$
 */
package net.zdsoft.eis.base.common.entity;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.TypeReference;

import net.zdsoft.eis.base.util.SUtils;
import net.zdsoft.eis.frame.client.BaseEntity;

public class SysOption extends BaseEntity {
	private static final long serialVersionUID = 1206770150063040398L;

	public static final String PASSPORT_URL = "PASSPORT.URL";
	public static final String PASSPORT_SECOND_URL = "PASSPORT.SECOND.URL";
	public static final String INDEX_URL = "INDEX.URL";
	public static final String INDEX_SECOND_URL = "INDEX.SECOND.URL";
	public static final String EISU_URL = "EISU.URL";
	public static final String EISU_SECOND_URL = "EISU.SECOND.URL";
	public static final String EIS_URL = "EIS.URL";
	public static final String EIS_SECOND_URL = "EIS.SECOND.URL";
	public static final String MEMBER_URL = "MEMBER.URL";
	public static final String FILE_URL = "FILE.URL";
	public static final String FILE_PATH = "FILE.PATH";
	public static final String SMS_PORT = "SMS.PORT";
	public static final String UC_SERVER_URL = "UC.SERVER.URL";
	public static final String CHARGENUMBER_OPEN = "CHARGENUMBER.OPEN";
	public static final String RECEIVER_RANGE = "RECEIVER.RANGE";
	public static final String CODE_OF_EDU_UNIFIED_PAYMENT = "EDU.UNIFIED.PAYMENT";// 是否教育局统一付费，1：是，0：否
	public static final String CODE_OF_SMS_SERVER_INFO = "SMS.SERVER.INFO";// 短信接口服务器参数
	public static final String SYSTEM_DEPLOY_REGION = "SYSTEM.DEPLOY.REGION";// 系统部署地
	public static final String EDU_PAYMENT = "1";// 教育局统一付费

	private String optionCode;
	private String name;
	private String defaultValue;
	private String description;
	private String nowValue;
	private String validateJS;
	private Integer viewable;

	public static SysOption dc(String data) {
		return SUtils.dc(data, SysOption.class);
	}

	public static List<SysOption> dt(String data) {
		List<SysOption> ts = SUtils.dt(data, new TypeReference<List<SysOption>>() {
		});
		if (ts == null)
			ts = new ArrayList<SysOption>();
		return ts;

	}

	public String getOptionCode() {
		return optionCode;
	}

	public void setOptionCode(String optionCode) {
		this.optionCode = optionCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNowValue() {
		return nowValue;
	}

	public void setNowValue(String nowValue) {
		this.nowValue = nowValue;
	}

	public String getValidateJS() {
		return validateJS;
	}

	public void setValidateJS(String validateJS) {
		this.validateJS = validateJS;
	}

	public Integer getViewable() {
		return viewable;
	}

	public void setViewable(Integer viewable) {
		this.viewable = viewable;
	}

}
