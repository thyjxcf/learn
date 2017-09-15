package net.zdsoft.eis.sms.service.impl;

import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.sms.constant.SmsConstant;
import net.zdsoft.eis.sms.dao.SmsUseConfigDao;
import net.zdsoft.eis.sms.entity.SmsUseConfig;
import net.zdsoft.eis.sms.service.SmsUseConfigService;

/**
 * 类说明
 * 
 * @author jiangf
 * @version 创建时间：2011-8-6 上午11:55:19
 */

public class SmsUseConfigServiceImpl implements SmsUseConfigService {

	private SmsUseConfigDao smsUseConfigDao;

	private SystemIniService systemIniService;

	private UnitService unitService;

	public void setSmsUseConfigDao(SmsUseConfigDao smsUseConfigDao) {
		this.smsUseConfigDao = smsUseConfigDao;
	}

	public void setSystemIniService(SystemIniService systemIniService) {
		this.systemIniService = systemIniService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	@Override
	public boolean isSmsUsed(String unitId) throws Exception {
		SmsUseConfig config = getUseConfig(unitId, SmsConstant.CONFIG_SIGN_SMS);
		if (config != null)
			if (config.getIsused() == 1)
				return true;
		return false;
	}

	public String getClientId(String unitId) {
		SmsUseConfig config = getUseConfig(unitId,
				SmsConstant.CONFIG_SIGN_CLIENTID);
		if (config != null)
			return config.getNowvalue();
		return "";
	}

	private SmsUseConfig getUseConfig(String unitId, String sign) {
		String model = systemIniService.getValue(SmsConstant.SMS_USE_MODEL);
		if (SmsConstant.SMS_USE_MODEL_TOP.equals(model))
			unitId = unitService.getTopEdu().getId();
		return smsUseConfigDao.getUseConfig(unitId, sign);
	}

}
