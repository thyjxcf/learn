/**
 * 
 */
package net.zdsoft.eis.base.data.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.zdsoft.eis.base.common.entity.SystemIni;
import net.zdsoft.eis.base.common.service.ModuleService;
import net.zdsoft.eis.base.common.service.impl.SystemIniServiceImpl;
import net.zdsoft.eis.base.data.BasedataConstants;
import net.zdsoft.eis.base.data.dao.BaseSystemIniDao;
import net.zdsoft.eis.base.data.service.BaseSystemIniService;
import net.zdsoft.eis.base.deploy.SystemDeployService;
import net.zdsoft.keel.util.StringUtils;
import net.zdsoft.keel.util.Validators;
import net.zdsoft.leadin.exception.FormatException;

/**
 * @author yanb
 * 
 */
public class BaseSystemIniServiceImpl extends SystemIniServiceImpl implements
		BaseSystemIniService {
    private Logger log = LoggerFactory.getLogger(BaseSystemIniServiceImpl.class);

	private BaseSystemIniDao baseSystemIniDao;
	private ModuleService moduleService;
	private SystemDeployService systemDeployService;

    public void setSystemDeployService(SystemDeployService systemDeployService) {
        this.systemDeployService = systemDeployService;
    }
    
	public void setBaseSystemIniDao(BaseSystemIniDao baseSystemIniDao) {
		this.baseSystemIniDao = baseSystemIniDao;
	}

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	public void saveMod(String iniId, String nowValue) {
		baseSystemIniDao.updateNowValue(iniId, nowValue);
		clearCache();
		
		if(SystemIni.SYSTEM_PASSPORT_SWITCH.equals(iniId)){
		    systemDeployService.initPassportClient();
		}
	}

	public void saveAllMod(SystemIni[] systemIniDtos) throws FormatException {
		SystemIni systemIniDto = null, dto = null;
		for (int i = 0; i < systemIniDtos.length; i++) {
			systemIniDto = systemIniDtos[i];
			if (systemIniDto == null)
				continue;
			String iniId = systemIniDto.getIniid();
			String nowValue = systemIniDto.getNowValue();

			if (nowValue != null) {
				if (BasedataConstants.SYSTEMINI_VALUE_MAXLENGTH < StringUtils
						.getRealLength(nowValue)) {
					throw new FormatException(systemIniDto.getName() + "值长度超过"
							+ BasedataConstants.SYSTEMINI_VALUE_MAXLENGTH,
							"systemIniDtos[" + i + "].nowValue");
				}
			}

			dto = this.getSystemIni(iniId);
			if (dto == null) {
				log.info("no this SystemIni for iniId: " + iniId);
			}
		
			saveMod(iniId, nowValue);
		}
	}

	public void saveDefaultValue(String iniId) {
		SystemIni systemIniDto = getSystemIni(iniId);
		saveMod(iniId, systemIniDto.getDefaultValue());
	}

	public List<SystemIni> getVisibleSystemIni(int isVisible) {
		List<SystemIni> list = baseSystemIniDao.getAllSystemInis();
		if (list == null) {
			return null;
		}

		List<SystemIni> returnList = new ArrayList<SystemIni>();
		Iterator<SystemIni> it = list.iterator();
		// 检索出这个系统所有可用的子系统
		Set<Integer> mapOfSubSystemDto = moduleService.getCacheSubsytem();

		// 根据每项内容进行判断，此项目是否可以被显示
		boolean display;
		while (it.hasNext()) {
			display = true;
			SystemIni s = it.next();
			if (s.getSubSystemId() != null
					&& !s.getSubSystemId().trim().equals("")) {
				String[] ids = s.getSubSystemId().split(",");
				for (String id : ids) {
					if (Validators.isNumber(id)) {
						if (mapOfSubSystemDto.contains(Integer.valueOf(id))) {
							display = true;
							break;
						} else {
							display = false;
						}
					}
				}
			}
			if (!display) {
				continue;
			}

			// isVisble为1时前台平台基础信息中显示，为0时后台管理中显示
			if (s.getIsVisible() == isVisible) {
				returnList.add(s);
			}
		}
		return returnList;
	}

}
