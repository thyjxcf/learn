package net.zdsoft.eis.base.data.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import net.zdsoft.eis.base.common.entity.Module;
import net.zdsoft.eis.base.common.entity.SubSystem;
import net.zdsoft.eis.base.common.service.impl.ModuleServiceImpl;
import net.zdsoft.eis.base.data.dao.BaseModuleDao;
import net.zdsoft.eis.base.data.service.BaseModuleService;
import net.zdsoft.eis.system.frame.serial.SerialManager;
import net.zdsoft.keel.util.Pagination;

public class BaseModuleServiceImpl extends ModuleServiceImpl implements
		BaseModuleService {
	private SerialManager serialManager;
	private BaseModuleDao baseModuleDao;
	

	public void setSerialManager(SerialManager serialManager) {
		this.serialManager = serialManager;
	}

	public void setBaseModuleDao(BaseModuleDao baseModuleDao) {
		this.baseModuleDao = baseModuleDao;
	}

	@Override
	public List<Module> findAllModules(String unitClass, String subSystemId,
			Pagination page) {
		List<Module> moduleList = baseModuleDao.takeModulesByPage(unitClass,
				subSystemId, page);
		updateModBySerial(moduleList);
		Map<Long, String> parentModMap = takeParentModMap();
		Map<Integer, SubSystem> subSysMap = subSystemService
				.getCacheSubSystemMap();
		for (Module m : moduleList) {
			if (null != subSysMap.get(m.getSubsystem())) {
				m.setSubSysName(subSysMap.get(m.getSubsystem()).getName());
			}
			if (null != parentModMap.get(m.getParentid())) {
				m.setParentModName(parentModMap.get(m.getParentid()));
			}

		}
		return moduleList;
	}

	/**
	 * 根据serialManager 过滤一下显示的模块列表，如果没有，则过滤掉该条，并将mark置为0
	 *
	 *@author "yangk"
	 * Jul 13, 2010 2:23:00 PM
	 * @param moduleList
	 */
	private void updateModBySerial(List<Module> moduleList) {
		List<Long> idList = new ArrayList<Long>();
		Set<Integer> modSet = serialManager.getModuleIds();
		Iterator<Module> ite = moduleList.iterator();
		Module m;
		while (ite.hasNext()) {
			m = ite.next();
			if (!modSet.contains(new Integer(m.getId().toString()))) {
				idList.add(m.getId());
				ite.remove();
			}
		}
		if (CollectionUtils.isNotEmpty(idList)) {
			baseModuleDao.updateCloseMark(idList.toArray(new Long[0]));
		}

	}

	/**
	 * 得到父级模块 id，name的map
	 *
	 *@author "yangk"
	 * Jul 13, 2010 1:58:52 PM
	 * @return
	 */
	private Map<Long, String> takeParentModMap() {
		List<Module> parentModList = findParentModules(null, null);
		Map<Long, String> parentModMap = new HashMap<Long, String>();
		for (Module m : parentModList) {
			parentModMap.put(m.getId(), m.getName());
		}
		return parentModMap;
	}

	public List<Module> findParentModules(String subSysId, String unitclass) {
		List<Module> parentModList = baseModuleDao.takeParentModules(subSysId,
				unitclass);
		return null == parentModList ? new ArrayList<Module>() : parentModList;
	}

	public void updateModule(Module m) {
		baseModuleDao.updateModule(m);
		clearCache();
	}

    public void updateModuleMark() {
        Set<Integer> modIds = serialManager.getModuleIds();
        baseModuleDao.updateCloseMarkNotIn(modIds.toArray(new Long[0]));
        clearCache();
    }

}
