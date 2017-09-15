package net.zdsoft.eis.frame.action;

import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;

import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.common.service.SystemVersionService;
import net.zdsoft.eis.base.common.service.UserSetService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.deploy.SystemDeployService;
import net.zdsoft.eis.frame.client.LoginInfo;
import net.zdsoft.leadin.dataimport.subsystemcall.LoginUser;

public class BaseAction extends BaseActionSupport {
	private static final long serialVersionUID = -1680215748066884557L;

	public static final String TEACHER = "teacher";

	public static final String STUDENT = "student";

	public static final String FAMILY = "family";

	private LoginInfo loginInfo;

	private int showFrame;

	private String systemDeploySchool;

	public int getShowFrame() {
		return showFrame;
	}

	public void setShowFrame(int showFrame) {
		this.showFrame = showFrame;
	}

	public LoginInfo getLoginInfo() {
		if (loginInfo == null) {
			loginInfo = (LoginInfo) this
					.getSession(BaseConstant.SESSION_LOGININFO);
		}
		return loginInfo;
	}

	public LoginUser getLoginUser() {
		return (LoginUser) getSession(BaseConstant.SESSION_LOGINUSER);
	}

	public String getProductId() {
		return systemDeployService.getProductId();
	}

	public String getUnitId() {
		return getLoginInfo().getUnitID();
	}

	public String getTimeBucket() {
		Calendar nowDate = Calendar.getInstance();
		int hour = nowDate.get(Calendar.HOUR_OF_DAY);
		if (hour > 18) {// 晚上
			return "晚上";
		} else if (hour < 12) {// 上午
			return "上午";
		} else {// 下午
			return "下午";
		}
	}

	// =====================部署参数=======================
	protected SystemDeployService systemDeployService;

	protected UserSetService userSetService;

	protected SystemVersionService systemVersionService;

	protected SystemIniService systemIniService;

	public void setSystemDeployService(SystemDeployService systemDeployService) {
		this.systemDeployService = systemDeployService;
	}

	public void setUserSetService(UserSetService userSetService) {
		this.userSetService = userSetService;
	}

	public void setSystemVersionService(
			SystemVersionService systemVersionService) {
		this.systemVersionService = systemVersionService;
	}

	public void setSystemIniService(SystemIniService systemIniService) {
		this.systemIniService = systemIniService;
	}

	/**
	 * 学校是否独立部署
	 * 
	 * @return
	 */
	public boolean isSchoolSelfDeploy() {
		return systemDeployService.isDeployAsIndependence();
	}

	public boolean isEisDeploy() {
		if (systemDeployService.isConnectPassport()) {
			return false;
		}
		return true;
	}

	/**
	 * 获取系统部署学校
	 * 
	 * @return
	 */
	public String getSystemDeploySchool() {
		if (StringUtils.isBlank(systemDeploySchool)) {
			systemDeploySchool = systemIniService
					.getValue(BaseConstant.SYSTEM_DEPLOY_SCHOOL);
		}
		return org.apache.commons.lang.StringUtils
				.isNotBlank(systemDeploySchool) ? systemDeploySchool
				: org.apache.commons.lang.StringUtils.EMPTY;
	}

	/**
	 * 首页title显示方式
	 * 
	 * @return
	 */
	public String getTitleNameShowSwitch() {
		String titleNameSwitch = systemIniService
				.getValue(BaseConstant.SHOW_TITLE_NAME_SWITH);
		return org.apache.commons.lang.StringUtils.isNotBlank(titleNameSwitch) ? titleNameSwitch
				: org.apache.commons.lang.StringUtils.EMPTY;
	}

	public String getPlatFormName() {
		String platformName = System.getProperty("deploy.platform.name");
		if (StringUtils.isNotBlank(platformName)) {
			return platformName;
		}
		return systemVersionService.getSystemVersion().getName();
	}

	public String getPlatFormSubsystem() {
		String platformSubsystem = System
				.getProperty("deploy.platform.subsystem");
		return org.apache.commons.lang.StringUtils
				.isNotBlank(platformSubsystem) ? platformSubsystem
				: org.apache.commons.lang.StringUtils.EMPTY;
	}

	public String getDeployPlatformEisUrl() {
		String eisUrl = System.getProperty("deploy.platform.eis.url");
		return org.apache.commons.lang.StringUtils.isNotBlank(eisUrl) ? eisUrl
				: org.apache.commons.lang.StringUtils.EMPTY;
	}

	public String getIndexUrl() {
		String indexUrl = systemDeployService.getIndexUrl();
		if (indexUrl != null
				&& indexUrl.indexOf(getRequest().getServerName()) < 0) {
			indexUrl = systemDeployService.getIndexSecondUrl();
		}
		return indexUrl;
	}

	public boolean isSecondUrl() {
		String indexUrl = systemDeployService.getIndexUrl();
		String indexSecondUrl = systemDeployService.getIndexSecondUrl();
		if (indexUrl != null && indexUrl.equalsIgnoreCase(indexSecondUrl)) {
			return false;
		}
		if (indexSecondUrl != null
				&& indexSecondUrl.indexOf(getRequest().getServerName()) >= 0) {
			return true;
		}
		return false;
	}

	public boolean isLoginForOther() {
		String loginForOther = systemIniService
				.getValue("LOGIN.FOR.OTHER.MODE");
		if (StringUtils.isNotBlank(loginForOther)
				&& "Y".equalsIgnoreCase(loginForOther)) {
			return true;
		}
		return false;
	}

	private String moduleOperation;

	public String getModuleOperation() {
		return moduleOperation;
	}

	public void setModuleOperation(String moduleOperation) {
		this.moduleOperation = moduleOperation;
	}

	public static void main(String[] args) {

	}

}
