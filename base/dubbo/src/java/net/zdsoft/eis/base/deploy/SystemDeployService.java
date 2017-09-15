/* 
 * @(#)PassportManager.java    Created on Sep 19, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.deploy;

import net.zdsoft.eis.base.constant.enumeration.VersionType;

/**
 * 系统发布
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Sep 19, 2010 11:21:05 AM $
 */
public interface SystemDeployService {
	/**
	 * 取passport地址
	 * 
	 * @return
	 */
	public String getPassportUrl();
	
	/**
	 * 取passport second地址
	 * 
	 * @return
	 */
	public String getPassportSecondUrl();


	/**
	 * 是否链接passport
	 * 
	 * @return
	 */
	public boolean isConnectPassport();

	/**
	 * 取当前部署的AP在passport的注册信息
	 * 
	 * @return
	 */
	public AppRegisterPassport getAppRegisterPassport();

	/**
	 * 取Ap在passport的注册信息，如果取不到，则取默认AP(学籍)的注册信息
	 * 
	 * @param appCode
	 * @return
	 */
	public AppRegisterPassport getAppRegisterPassport(String appCode);

	/**
	 * 初始化PassportClient
	 */
	public void initPassportClient();

	/**
	 * 是否链接office
	 * 
	 * @return
	 */
	public boolean isConnectOffice();

	/**
	 * 取office地址
	 * 
	 * @return
	 */
	public String getOfficeUrl();

	/**
	 * 取office地址
	 * 
	 * @return
	 */
	public String getOfficeSecondUrl();

	/**
	 * 取index地址
	 * 
	 * @return
	 */
	public String getIndexUrl();

	/**
	 * 取内网index地址
	 * 
	 * @return
	 */
	public String getIndexSecondUrl();

	/**
	 * 取index地址
	 * 
	 * @return
	 */
	public String getEisuUrl();

	/**
	 * 取内网index地址
	 * 
	 * @return
	 */
	public String getEisuSecondUrl();

	/**
	 * 取index地址
	 * 
	 * @return
	 */
	public String getEisUrl();

	/**
	 * 取内网index地址
	 * 
	 * @return
	 */
	public String getEisSecondUrl();

	/**
	 * 是否独立部署，如果顶级单位是学校，则认为是独立部署
	 * 
	 * @return
	 */
	public boolean isDeployAsIndependence();

	/**
	 * 是否为定购模式
	 * 
	 * @return
	 */
	public boolean isOrderMode();

	/**
	 * 是否开启MQ发送和接收消息功能
	 * 
	 * @return
	 */
	public boolean isOpenMq();

	/**
	 * 是否和省平台对接
	 * 
	 * @return
	 */
	public boolean isDeployWithProvince();

	/**
	 * 取省级平台的webService地址
	 * 
	 * @return
	 */
	public String getProvinceWebService();

	/**
	 * 取产品标识
	 * 
	 * @return
	 */
	public String getProductId();

	/**
	 * 连接passport的情况下时候从eis登陆
	 * @return
	 */
	public boolean isEisLoginForPassport();
	
	/**
	 * 取得部署类型
	 * @return
	 */
	public VersionType getVersionType();
}
