package net.zdsoft.eis.base.common.util;

import java.util.Date;

import net.zdsoft.eis.base.common.entity.SubSystem;
import net.zdsoft.eis.base.common.service.BaseStringService;
import net.zdsoft.eis.base.common.service.Mcode;
import net.zdsoft.eis.base.common.service.McodeService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.StringUtils;
import net.zdsoft.keelcnet.config.ContainerManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppSetting {
	static Logger logger = LoggerFactory.getLogger(AppSetting.class);
	private static AppSetting appSetting = new AppSetting();

	public static AppSetting getInstance() {
		return appSetting;
	}

	protected AppSetting() {
		logger.info(">>>>>>>>>初始化AppSetting...");
	}

	/**
	 * 根据子系统id，取得页面title的名称
	 * 
	 * @return
	 */
	public String getWebTitle(int appId) {
		return SubSystem.getTitle(appId);
	}

	/**
	 * 取得loginInfo在session中的变量名
	 * 
	 * @return
	 */
	public String getLoginSessionName() {
		return BaseConstant.SESSION_LOGININFO;
	}

	public String getString(String code) {
		BaseStringService baseStringService = (BaseStringService) ContainerManager
				.getComponent("baseStringService");
		return baseStringService.getValue(code);
	}

	/**
	 * 取得一个指定微代码的显示名称
	 * 
	 * @param mcodeid
	 * @param mDetailId
	 * @return
	 */
	public String getMcodeName(String mcodeId, String mDetailId) {
		McodeService mcodeService = (McodeService) ContainerManager
				.getComponent("mcodeService");
		if (mcodeService == null)
			return null;
		return mcodeService.getMcodeContext(mcodeId, mDetailId);
	}

	/**
	 * 获取一些指定微代码的显示名称</br>
	 * 请确保参数的正确和合法性</br>
	 * 
	 * @param mcodeId
	 * @param mDetailIds 用,分隔
	 * @param more 1 多个thisId使用 , 分割   0单个thisId
	 * @return 对应微代码的名称使用 , 分隔
	 */
	public String getMcodeName(String mcodeId, String mDetailId, String more) {
		if("0".equals(more)){
			return getMcodeName(mcodeId, mDetailId);
		}
		
		McodeService mcodeService = (McodeService) ContainerManager.getComponent("mcodeService");
		if (mcodeService == null || org.apache.commons.lang3.StringUtils.isEmpty(mDetailId))
			return null;
		StringBuffer sb = new StringBuffer();
		for (String id : mDetailId.split(",")) {
			sb.append(org.apache.commons.lang3.StringUtils.isEmpty(id)?"":mcodeService.getMcodeContext(mcodeId, id)+",");
		}
		return org.apache.commons.lang3.StringUtils.isEmpty(sb.toString())?null:sb.substring(0, sb.length()-1);
	}

	/**
	 * @deprecated 用getSelectCode取代
	 * @param mcodeId
	 * @return
	 */
	public Mcode getMcode(String mcodeId) {
		McodeService mcodeService = (McodeService) ContainerManager
				.getComponent("mcodeService");
		if (mcodeService == null)
			return null;
		return mcodeService.getMcode(mcodeId);
	}

	public int getDayOfWeek(Date date) {
		return DateUtils.getDayOfWeek(date);
	}

	public String htmlFilter(String content) {
		return StringUtils.htmlFilter(StringUtils.ignoreNull(content));
	}

	/**
	 * 用于一些特殊的下拉返回内容，为了和前面的保持兼容，结果封装成mcode，这个可以取代getMcode接口
	 * 
	 * @param mcodeId
	 * @return
	 */
	public Mcode getSelectCode(String mcodeId) {
		return getMcode(mcodeId);
	}
}
