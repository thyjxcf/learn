/* 
 * @(#)PassportManagerImpl.java    Created on Sep 19, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.deploy;

import net.zdsoft.eis.base.cache.BaseCacheConstants;
import net.zdsoft.eis.base.common.entity.Server;
import net.zdsoft.eis.base.common.entity.SysOption;
import net.zdsoft.eis.base.common.entity.SystemIni;
import net.zdsoft.eis.base.common.entity.SystemVersion;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.ServerService;
import net.zdsoft.eis.base.common.service.SysOptionService;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.common.service.SystemVersionService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.constant.enumeration.VersionType;
import net.zdsoft.leadin.cache.CacheCall.CacheObjectParam;
import net.zdsoft.leadin.cache.SimpleCacheManager;
import net.zdsoft.passport.service.client.PassportClient;
import net.zdsoft.passport.service.client.PassportClientParam;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Sep 19, 2010 11:21:25 AM $
 */
public class SystemDeployServiceImpl implements SystemDeployService {
	private static final Logger log = LoggerFactory
			.getLogger(SystemDeployServiceImpl.class);

	private SystemIniService systemIniService;
	private SysOptionService sysOptionService;
	private ServerService serverService;
	private SimpleCacheManager simpleCacheManager;
	private UnitService unitService;
	private SystemVersionService systemVersionService;

	public void setSystemVersionService(
			SystemVersionService systemVersionService) {
		this.systemVersionService = systemVersionService;
	}

	public void setServerService(ServerService serverService) {
		this.serverService = serverService;
	}

	public void setSystemIniService(SystemIniService systemIniService) {
		this.systemIniService = systemIniService;
	}

	public void setSysOptionService(SysOptionService sysOptionService) {
		this.sysOptionService = sysOptionService;
	}

	public void setSimpleCacheManager(SimpleCacheManager simpleCacheManager) {
		this.simpleCacheManager = simpleCacheManager;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	// ===================以上为set方法=================

	public AppRegisterPassport getAppRegisterPassport() {
		return getAppRegisterPassport(SystemDeployUtils
				.getCurrentDeployAppCode());
	}

	public AppRegisterPassport getAppRegisterPassport(String appCode) {
		int serverId = 0;
		String verifyKey = "";

		Server app = serverService.getServerByServerCode(appCode);
		if (null == app) {
			log.error("根据appCode=" + appCode + "找不到对应的AP");
			appCode = Server.SERVERCODE_STUSYS;// 默认学籍
			app = serverService.getServerByServerCode(appCode);
		}
		if (null == app) {
			log.error("根据appCode=" + appCode + "找不到对应的AP");
		} else {
			serverId = Integer.parseInt(app.getId());
			verifyKey = app.getServerKey();
		}

		AppRegisterPassport arp = new AppRegisterPassport();
		arp.setServerId(serverId);
		arp.setVerifyKey(verifyKey);

		return arp;
	}

	public void initPassportClient() {
		String appCode = SystemDeployUtils.getCurrentDeployAppCode();
		if (isConnectPassport()) {
			try {
				String passportURL = getPassportUrl();
				System.out.println("======= PassportUrl :" + passportURL);
				AppRegisterPassport arp = getAppRegisterPassport(appCode);
				int serverId = arp.getServerId();
				String verifyKey = arp.getVerifyKey();

				// 初始化PassportClient
				PassportClientParam p0 = null;

				// key 为server secondDomain (base_server 表)内网的ip地址 ，值为
				// PASSPORT.SECOND.URL(base_sys_option)
				// 增加内网外访问控制
				// 有配置内网的话，以内网作为默认passport
				Server app = serverService.getServerByServerCode(appCode);
				if (app != null) {
					System.out.println("======= domain :" + app.getDomain());
					String key = app.getSecondDomain();
					String value = sysOptionService
							.getValue(SysOption.PASSPORT_SECOND_URL);
					System.out.println("================passport init key="
							+ key + " value=" + value + " appCode:" + appCode);
					if (key != null) {
						p0 = new PassportClientParam(value,
								serverId, verifyKey);
						p0.addPassportURL(app.getDomain(), passportURL);
					}
				} 
				if(p0 == null){
					p0 = new PassportClientParam(passportURL,
							serverId, verifyKey);
				}
				
				PassportClient.getInstance().init(p0);
				log.debug("PassportClient[" + serverId + "]@[" + passportURL
						+ "] initialed");
			} catch (Exception e) {
				System.out.println("======= initPassportClient : "
						+ e.getMessage());
			}
		}
	}

	public boolean isConnectOffice() {
		return systemIniService.getBooleanValue("SYSTEM.OFFICE.SWITCH");
	}

	public String getOfficeUrl() {
		return serverService.getServerByServerCode(Server.SERVERCODE_OFFICE)
				.getUrl();
	}

	public String getOfficeSecondUrl() {
		return serverService.getServerByServerCode(Server.SERVERCODE_OFFICE)
				.getSecondUrl();
	}

	public String getIndexUrl() {
		return sysOptionService.getValue(SysOption.INDEX_URL);
	}

	public String getIndexSecondUrl() {
		return sysOptionService.getValue(SysOption.INDEX_SECOND_URL);
	}

	public String getEisuUrl() {
		return sysOptionService.getValue(SysOption.EISU_URL);
	}

	public String getEisuSecondUrl() {
		return sysOptionService.getValue(SysOption.EISU_SECOND_URL);
	}

	public String getEisUrl() {
		String eisUrl = System.getProperty("deploy.platform.eis.url");
		if (StringUtils.isNotBlank(eisUrl)) {
			return eisUrl;
		}
		return sysOptionService.getValue(SysOption.EIS_URL);
	}

	public String getEisSecondUrl() {
		return sysOptionService.getValue(SysOption.EIS_SECOND_URL);
	}

	public String getPassportUrl() {
		return sysOptionService.getValue(SysOption.PASSPORT_URL);
	}
	
	public String getPassportSecondUrl(){
		return sysOptionService.getValue(SysOption.PASSPORT_SECOND_URL);
	}

	public boolean isConnectPassport() {
//		return systemIniService
//				.getBooleanValue(SystemIni.SYSTEM_PASSPORT_SWITCH);
		return false;
	}

	public boolean isDeployAsIndependence() {
		return simpleCacheManager
				.getObjectFromCache(new CacheObjectParam<Boolean>() {
					public Boolean fetchObject() {
						Unit unit = unitService.getTopEdu();
						if (unit == null) {
							return false;
						} else {
							if (unit.getUnitclass() == Unit.UNIT_CLASS_SCHOOL) {
								return true;

							} else {
								return false;
							}
						}
					}

					public String fetchKey() {
						return BaseCacheConstants.EIS_DEPLOY_INDEPENDENCE;
					}
				});
	}

	public boolean isOrderMode() {
		return systemIniService.getBooleanValue("SYSTEM.ORDER.MODE.SWITCH");// 定购模式
	}

	public boolean isOpenMq() {
		return systemIniService.getBooleanValue("SYSTEM.DATASYNC.MQ.SWITCH");
	}

	public boolean isDeployWithProvince() {
		return systemIniService.getBooleanValue("SYSTEM.PROVINCE.JOIN");
	}

	public String getProvinceWebService() {
		String url = systemIniService.getValue("SYSTEM.PRVFLAT.SERVICE.URL");
		if (StringUtils.isBlank(url)) {
			return url = "";
		} else {
			url += "/services/";
		}
		return url;
	}

	public String getProductId() {
		String productId = SystemVersion.PRODUCT_EIS;
		SystemVersion sv = systemVersionService.getSystemVersion();
		if (null != sv) {
			if (sv.getProductId()
					.contains(
							SystemVersion.PRODUCT_EIS
									+ SystemVersion.PRODUCT_CONNECTOR)) {
				productId = SystemVersion.PRODUCT_EIS;
			} else if (sv.getProductId().contains(
					SystemVersion.PRODUCT_EISU
							+ SystemVersion.PRODUCT_CONNECTOR)) {
				productId = SystemVersion.PRODUCT_EISU;
			}
		}
		return productId;
	}

	@Override
	public boolean isEisLoginForPassport() {
		return systemIniService
				.getBooleanValue("SYSTEM.USE.EISLOGIN.FOR.PASSPORT");
	}

	@Override
	public VersionType getVersionType() {
		SystemVersion version = systemVersionService.getSystemVersion();
		if (version == null)
			return null;
		return VersionType.getVersionType(version.getProductId());
	}
}