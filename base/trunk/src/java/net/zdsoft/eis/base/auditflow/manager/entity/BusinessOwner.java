/* 
 * @(#)BusinessOwner.java    Created on 2012-11-26
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.auditflow.manager.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2012-11-26 上午10:41:07 $
 */
public class BusinessOwner implements Serializable {
	private static final long serialVersionUID = -6694125168059496149L;

	private String id;
	private String code;//对于老师 是 职工编号  对于学生是学藉号
	private String name;
	private Map<String, String> otherMap;//存放其它参数  页面上还没加 因为还没有用到 若有用到 请把这行字去除
	public BusinessOwner() {
		super();
	}

	public BusinessOwner(String id, String code, String name) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
	}
	
	public BusinessOwner(String id, String code, String name,Map<String, String> otherMap) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.otherMap=otherMap;
	}

	public String getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public Map<String, String> getOtherMap() {// TreeMap保证顺序
		if(otherMap==null){
			otherMap = new TreeMap<String, String>();
		}
		return otherMap;
	}

	public void setOtherMap(Map<String, String> otherMap) {
		this.otherMap = otherMap;
	}

}
