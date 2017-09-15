/* 
 * @(#)BaseImportAction.java    Created on May 31, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.frame.action;

import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.common.entity.CurrentSemester;
import net.zdsoft.eis.base.common.entity.SubSystem;
import net.zdsoft.eis.base.common.service.SemesterService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.deploy.SystemDeployService;
import net.zdsoft.eis.frame.client.LoginInfo;
import net.zdsoft.eis.frame.dto.PromptMessageDto;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keelcnet.config.ContainerManager;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ActionContext;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 31, 2010 10:22:25 AM $
 */
public abstract class DataImportBaseAction extends
		net.zdsoft.leadin.dataimport.action.DataImportBaseAction implements
		ResultNameAction {

	private static final long serialVersionUID = -2117556388165204523L;

	// ======================同BaseAction=====================

	protected PromptMessageDto promptMessageDto = new PromptMessageDto();

	private LoginInfo loginInfo;

	protected int appId = -1;
	
	/**
	 * 导入类型
	 */
	protected String importType;
	/**
	 * 返回需要的url
	 */
	protected String url;
	/**
	 * 自定义url
	 */
	protected String userDefinedUrl;

	public LoginInfo getLoginInfo() {
		if (loginInfo == null) {
			loginInfo = (LoginInfo) this
					.getSession(BaseConstant.SESSION_LOGININFO);
		}
		return loginInfo;
	}

	public String getUnitId() {
		return getLoginInfo().getUnitID();
	}

	protected Object getSession(String parm) {
		return ActionContext.getContext().getSession().get(parm);
	}

	// =====================以下为get/set方法=======================

	/**
	 * 取当前年份
	 * 
	 * @return
	 */
	public String getCurrentYear() {
		Date today = new Date();
		String year = DateUtils.date2String(today, "yyyy");
		return year;
	}

	/**
	 * 取页面title
	 * 
	 * @return 应用系统的title
	 */
	public String getWebAppTitle() {
		return SubSystem.getTitle(appId);
	}

	/**
	 * 取页面title
	 * 
	 * @return 应用系统名称 + 模块名称
	 */
	public String getWebModuleTitle() {
		return SubSystem.getTitle(appId);// TODO待扩展;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	// 传到页面的提示信息对象
	public PromptMessageDto getPromptMessageDto() {
		return promptMessageDto;
	}

	public void setPromptMessageDto(PromptMessageDto promptMessageDto) {
		this.promptMessageDto = promptMessageDto;
	}
	
	protected SystemDeployService systemDeployService;

	/**
	 * 学校是否独立部署
	 * 
	 * @return
	 */
	public boolean isSchoolSelfDeploy() {
		if (systemDeployService == null) {
			systemDeployService = (SystemDeployService) ContainerManager
					.getComponent("systemDeployService");
		}
		return systemDeployService.isDeployAsIndependence();
	}

	// ======================同BaseSemesterAction=====================
	protected SemesterService semesterService;

	protected String acadyear;
	protected String semester;

	/**
	 * 学年列表
	 */
	public List<String> getAcadyearList() {
		return semesterService.getAcadyears();
	}

	public CurrentSemester getCurrentSemester() {
		return semesterService.getCurrentSemester();
	}

	/**
	 * @return the curAcadyear
	 */
	public String getAcadyear() {
		if (StringUtils.isEmpty(acadyear)) {
			CurrentSemester sem = getCurrentSemester();
			if (sem != null)
				acadyear = getCurrentSemester().getAcadyear();
			else
				acadyear = "";
		}
		return acadyear;
	}

	/**
	 * @return the curSemester
	 */
	public String getSemester() {
		if (StringUtils.isEmpty(semester)) {
			CurrentSemester sem = getCurrentSemester();
			if (sem != null)
				semester = getCurrentSemester().getSemester();
			else
				semester = "";
		}
		return semester;
	}

	public void setAcadyear(String acadyear) {
		this.acadyear = acadyear;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	/**
	 * 获取importType
	 * @return importType
	 */
	public String getImportType() {
	    return importType;
	}

	/**
	 * 设置importType
	 * @param importType importType
	 */
	public void setImportType(String importType) {
	    this.importType = importType;
	}

	/**
	 * 获取返回需要的url
	 * @return 返回需要的url
	 */
	public String getUrl() {
	    return url;
	}

	/**
	 * 设置返回需要的url
	 * @param url 返回需要的url
	 */
	public void setUrl(String url) {
	    this.url = url;
	}

	/**
	 * 获取自定义url
	 * @return 自定义url
	 */
	public String getUserDefinedUrl() {
	    return userDefinedUrl;
	}

	/**
	 * 设置自定义url
	 * @param userDefinedUrl 自定义url
	 */
	public void setUserDefinedUrl(String userDefinedUrl) {
	    this.userDefinedUrl = userDefinedUrl;
	}

	public void setSemesterService(SemesterService semesterService) {
		this.semesterService = semesterService;
	}
}
