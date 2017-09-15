package net.zdsoft.eis.sms.service.impl;

import java.util.List;

import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.sms.constant.SmsConstant;
import net.zdsoft.eis.sms.dao.BaseSmsUseConfigDao;
import net.zdsoft.eis.sms.entity.SmsUseConfig;
import net.zdsoft.eis.sms.service.BaseSmsUseConfigService;

import org.apache.commons.lang.StringUtils;

/**
 * @author jiangf
 * @version 创建时间：2011-8-5 下午04:06:15
 */

public class BaseSmsUseConfigServiceImpl implements BaseSmsUseConfigService {

	private BaseSmsUseConfigDao baseSmsUseConfigDao;

	private SystemIniService systemIniService;

	private UnitService unitService;

	public void setBaseSmsUseConfigDao(BaseSmsUseConfigDao baseSmsUseConfigDao) {
		this.baseSmsUseConfigDao = baseSmsUseConfigDao;
	}

	public void setSystemIniService(SystemIniService systemIniService) {
		this.systemIniService = systemIniService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	@Override
	public void saveUseConfig(String unitId, int isSmsUsed, String clientId)
			throws Exception {
		//判断是否开通，开通了就过滤掉
		if(!isSmsUsed(unitId)){
		// 先保存开通短信
		SmsUseConfig config = new SmsUseConfig();
		config.setSign(SmsConstant.CONFIG_SIGN_SMS);
		config.setName("单位是否启用短信功能");
		config.setUnitId(unitId);
		config.setIsused(isSmsUsed);
		baseSmsUseConfigDao.addUseConfig(config);
		if (StringUtils.isNotBlank(clientId)) {
			config = new SmsUseConfig();
			config.setSign(SmsConstant.CONFIG_SIGN_CLIENTID);
			config.setName("短信客户帐号");
			config.setUnitId(unitId);
			config.setNowvalue(clientId);
			baseSmsUseConfigDao.addUseConfig(config);
		}
		}
	}
	
	@Override
	public void deleteUseConfig(String unitId){
		baseSmsUseConfigDao.deleteUseConfig(unitId);
	}

	@Override
	public String getUnitIdByClientId(String clientId) {
		return baseSmsUseConfigDao.getUnitIdByClientId(clientId);
	}

	@Override
	public SmsUseConfig getUseConfig(String unitId, String sign) {
		String model = systemIniService.getValue(SmsConstant.SMS_USE_MODEL);
		if (SmsConstant.SMS_USE_MODEL_TOP.equals(model))
			unitId = unitService.getTopEdu().getId();
		return baseSmsUseConfigDao.getUseConfig(unitId, sign);
	}

	public boolean isSmsUsed(String unitId) {
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
		return null;
	}

	@Override
	public List<SmsUseConfig> getUseConfigByUnitId(String unitId) {
		return baseSmsUseConfigDao.getUseConfigByUnitId(unitId);
	}

	@Override
	public boolean ieExistsConfiginfo(String unitId) {
		return baseSmsUseConfigDao.ieExistsConfiginfo(unitId);
	}

	@Override
	public String isExistClientId(String unitId, String clientId) {
		return baseSmsUseConfigDao.isExistClientId(unitId, clientId);
	}

	@Override
	public void updateUseConfig(SmsUseConfig config) {
		baseSmsUseConfigDao.updateUseConfig(config);
	}

}
