package net.zdsoft.eis.base.common.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.common.dao.UnitIniDao;
import net.zdsoft.eis.base.common.entity.SystemIni;
import net.zdsoft.eis.base.common.entity.UnitIni;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.common.service.UnitIniService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.leadin.util.PWD;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

public class UnitIniServiceImpl implements UnitIniService {
	private static final String SYSTEM_PASSWORD_CONFIG = "SYSTEM.PASSWORD";// 系统设置标识
	private static final Integer SYSTEMINI_VISIBLE_UNIT = 2;// 系统设置表中,对于单位设置标志

	private UnitIniDao unitIniDao;
	private SystemIniService systemIniService;

	public void setSystemIniService(SystemIniService systemIniService) {
		this.systemIniService = systemIniService;
	}

	public void setUnitIniDao(UnitIniDao unitIniDao) {
		this.unitIniDao = unitIniDao;
	}

	public void saveUnitPasswordOption(String unitId) {
		SystemIni systemIni = systemIniService
				.getSystemIni(SYSTEM_PASSWORD_CONFIG);
		UnitIni unitIni = new UnitIni();
		unitIni.setCreationDate(new Date());
		unitIni.setDefaultValue(systemIni.getDefaultValue());
		unitIni.setDescription(systemIni.getDescription());
		unitIni.setNowValue(systemIni.getNowValue());
		unitIni.setName(systemIni.getName());
		unitIni.setIniid(UnitIni.UNIT_PASSWORD_CONFIG);

		// 密码加密
		if (unitIni.getFlag() != null && unitIni.getFlag().trim().length() > 0) {
			PWD p = new PWD(unitIni.getFlag());
			unitIni.setFlag(p.encode());
		}
		unitIni.setUnitid(unitId);
		unitIniDao.insertUnitIni(unitIni);
	}

	public void updateUnitOption(String unitId, String option, String value,
			String flag) {
		UnitIni unitIni = unitIniDao.getUnitIni(unitId, option);
		String Flag = null;
		if (flag != null) {
			PWD pwd = new PWD(flag);
			Flag = pwd.encode();
		}

		if (unitIni != null) {
			if (value == null) {
				unitIni.setNowValue(unitIni.getDefaultValue());
			} else {
				unitIni.setNowValue(value);
			}
			if (Flag != null) {
				unitIni.setFlag(Flag);
			}
			saveUnitOption(unitIni);
		} else {// 如果该单位设置不存在,则立即生成
			SystemIni systemIni = systemIniService
					.getSystemIni(SYSTEM_PASSWORD_CONFIG);
			unitIni = new UnitIni();
			unitIni.setCreationDate(new Date());
			unitIni.setDefaultValue(systemIni.getDefaultValue().trim());
			unitIni.setDescription(systemIni.getDescription());
			unitIni.setNowValue(value);
			unitIni.setFlag(Flag);
			unitIni.setName(systemIni.getName());
			unitIni.setIniid(UnitIni.UNIT_PASSWORD_CONFIG);

			unitIni.setUnitid(unitId);
			unitIniDao.insertUnitIni(unitIni);
		}
	}

	public void initUnitOption(String unitId) {
		List<SystemIni> list = systemIniService
				.getSystemIniByViewable(SYSTEMINI_VISIBLE_UNIT);
		SystemIni systemIni;
		UnitIni unitIni;
		for (int i = 0; i < list.size(); i++) {
			systemIni = (SystemIni) list.get(i);
			unitIni = new UnitIni();

			unitIni.setIniid(systemIni.getIniid());
			unitIni.setName(systemIni.getName());
			unitIni.setDefaultValue(systemIni.getDefaultValue());
			unitIni.setDescription(systemIni.getDescription());
			unitIni.setNowValue(systemIni.getNowValue());
			unitIni.setValidatejs(systemIni.getValidateJS());
			unitIni.setUnitid(unitId);

			unitIniDao.insertUnitIni(unitIni);
		}
	}

	public void saveUnitOption(String unitId, String option, String value,
			String flag) {
		UnitIni unitIni = unitIniDao.getUnitIni(unitId, option);
		if (unitIni == null) {
			// 如果该信息不存在,则新增
			unitIni = new UnitIni();
			unitIni.setUnitid(unitId);
			unitIni.setIniid(option);
			unitIni.setNowValue(value);
			unitIni.setFlag(flag);
			unitIniDao.insertUnitIni(unitIni);

		} else {
			unitIni.setNowValue(value);
			unitIni.setFlag(flag);
			unitIniDao.updateUnitIni(unitIni);
		}
	}

	public void saveUnitOptions(UnitIni[] options) {
        if (options == null || options.length == 0 ) return;
        for (UnitIni ini : options ) {
            saveUnitOption(ini);
        }
    }

	public void saveUnitOption(UnitIni unitIni) {
		if (null != unitIni.getId() && !"".equals(unitIni.getId())) {
			unitIniDao.updateUnitIni(unitIni);
		} else {
			unitIniDao.insertUnitIni(unitIni);
		}
	}

	public UnitIni getUnitOption(String unitId, String iniId) {
		UnitIni unitIni = unitIniDao.getUnitIni(unitId, iniId);
		if (unitIni == null) {
			return null;
		}
		return unitIni;
	}

    public List<UnitIni> getUnitTreeParamOptions(String unitId) {
        List<UnitIni> options = unitIniDao.getUnitIniList(unitId);
        List<UnitIni> result = new ArrayList<UnitIni>();
        for (UnitIni option : options ) {
            if (option.getIniid().startsWith("SYSTEM.TREE")) {
                result.add(option);
            }
        }
        return result;
    }

	public String getUnitOptionValue(String unitId, String iniId) {
		UnitIni unitIni = unitIniDao.getUnitIni(unitId, iniId);
		if (unitIni == null) {
			return null;
		}
		return unitIni.getNowValue();
	}

	public boolean getUnitOptionBooleanValue(String unitId, String iniId) {
        String value = getUnitOptionValue(unitId, iniId);
        return BooleanUtils.toBoolean(NumberUtils.toInt(value, 1)) || BooleanUtils.toBoolean(value);
    }
	
	public User getUserPass(User user) {
		if (user == null) {
			return user;
		}
		if (StringUtils.isNotBlank(user.findClearPassword())) {
//			user.setPassword(password);
			return user;
		}
		UnitIni unitIni = getUnitOption(user.getUnitid(),
				UnitIni.UNIT_PASSWORD_CONFIG);
		if (unitIni == null) {// 如果该单位规则不存在,则自动生成
			saveUnitPasswordOption(user.getUnitid());
			unitIni = getUnitOption(user.getUnitid(),
					UnitIni.UNIT_PASSWORD_CONFIG);
		}
		if (unitIni == null) {
			return null;
		}

		String password = null;
		if (unitIni.getNowValue().trim().equals(
				String.valueOf(BaseConstant.PASSWORD_GENERIC_RULE_NULL))) {
			password = BaseConstant.PASSWORD_INIT;
		} else if (unitIni.getNowValue().trim().equals(
				String.valueOf(BaseConstant.PASSWORD_GENERIC_RULE_NAME))) {
			if (user.getName() != null && user.getName().length() < 6) {
				user.setPassword(user.getName());
				return user;
			}
			password = user.getName();
		} else if (unitIni.getNowValue().trim().equals(
				String.valueOf(BaseConstant.PASSWORD_GENERIC_RULE_UNIONIZE))) {
			password = unitIni.getFlag();
		}

		user.setPassword(password);
		return user;
	}

}
