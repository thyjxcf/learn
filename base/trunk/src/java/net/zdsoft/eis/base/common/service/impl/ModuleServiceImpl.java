package net.zdsoft.eis.base.common.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import net.zdsoft.eis.base.cache.BaseCacheConstants;
import net.zdsoft.eis.base.common.dao.ModuleDao;
import net.zdsoft.eis.base.common.entity.BasicModule;
import net.zdsoft.eis.base.common.entity.Module;
import net.zdsoft.eis.base.common.entity.SubSystem;
import net.zdsoft.eis.base.common.service.ModuleService;
import net.zdsoft.eis.base.common.service.SubSystemService;
import net.zdsoft.eis.frame.cache.DefaultCacheManager;

public class ModuleServiceImpl extends DefaultCacheManager implements ModuleService {

    private ModuleDao moduleDao;
    protected SubSystemService subSystemService;

    public void setModuleDao(ModuleDao moduleDao) {
        this.moduleDao = moduleDao;
    }

    public void setSubSystemService(SubSystemService subSystemService) {
        this.subSystemService = subSystemService;
    }

    // -------------------缓存信息 begin------------------------

    public void enableModules() {
        moduleDao.enableModules();
        clearCache();
    }

    public void disableModules(int subSystemId) {
        moduleDao.disableModules(subSystemId);
        clearCache();
    }
    

	 public void enableModules(int subSystemId) {
		 moduleDao.enableModules(subSystemId);
		 clearCache();
	 }
    
    /**
     * 检索模块信息
     * 
     * @param unitClass
     * @param ur 模块url
     * @return 模块信息
     */
    public List<Module> getModulesByUrl(final int unitClass, final String url) {
        return getEntitiesFromCache(new CacheEntitiesParam<Module>() {

            public List<Module> fetchObjects() {
                return moduleDao.getModulesByUrl(unitClass, url);
            }

            public String fetchKey() {
                return BaseCacheConstants.EIS_MODULE_URL + unitClass + url;
            }
        });
    }

    /**
     * 根据数字型id，检索模块信息
     * 
     * @param intId 模块的数字型id
     * @return 模块信息
     */
    public Module getModuleByIntId(final long intId) {
        return getEntityFromCache(new CacheEntityParam<Module>() {

            public Module fetchObject() {
                return moduleDao.getModule((int) intId);
            }

            public String fetchKey() {
                return fetchCacheEntityKey() + intId;
            }
        });
    }


    public Module getModule(final String mid, final Integer unitClass) {
        return getEntityFromCache(new CacheEntityParam<Module>() {

            public Module fetchObject() {
                return moduleDao.getModule(mid, unitClass);
            }

            public String fetchKey() {
                return BaseCacheConstants.EIS_MODULE_MID_UNITCLASS + mid + unitClass.toString();
            }
        });
    }
    
    /**
     * 检索已经启用的模块信息
     * 
     * @param unitClass 适用单位类型，教育局1， 学校2
     * @return 模块信息
     */
    private List<Module> getModuleListByUnitClass(final int unitClass) {
        return getEntitiesFromCache(new CacheEntitiesParam<Module>() {

            public List<Module> fetchObjects() {
                return moduleDao.getEnabledModulesByUnitClass(unitClass);
            }

            public String fetchKey() {
                return BaseCacheConstants.EIS_MODULE_UNITCLASS_ + unitClass;
            }
        });
    }

    /**
     * 获取所有公共模块主键id
     * 
     * @param unitClass
     * @return
     */
    public Set<Long> getCommonModuleIdSet(int unitClass) {
        List<Module> list = getModuleListByUnitClass(unitClass);
        Set<Long> moduleIdSet = new HashSet<Long>();
        for (Module module : list) {
            if (module.getUnitclass().intValue() == unitClass && module.isCommon()) {
                moduleIdSet.add(module.getId());
            }
        }
        return moduleIdSet;
    }

    /**
     * 获取所有公共模块id
     * 
     * @param unitClass
     * @return
     */
    public synchronized Set<Module> getCommonModelIdSet(int unitClass) {
        List<Module> list = getModuleListByUnitClass(unitClass);

        Set<Module> modSet = new HashSet<Module>();
        for (Module module : list) {
            if (module.getUnitclass().intValue() == unitClass && module.isCommon()) {
                modSet.add(module);
            }
        }
        return modSet;
    }

    public List<Integer> getAllModuleIds() {
        return getObjectsFromCache(new CacheObjectsParam<Integer>() {

            public List<Integer> fetchObjects() {
                return moduleDao.getAllModuleIds();
            }

            public String fetchKey() {
                return BaseCacheConstants.EIS_MODULE_ID_LIST;
            }
        });
    }

    public Map<Integer, Module> getAllModuleMap() {
        return getObjectMapFromCache(new CacheObjectMapParam<Integer, Module>() {

            public Map<Integer, Module> fetchObjects() {
                return moduleDao.getAllModuleMap();
            }

            public String fetchKey() {
                return BaseCacheConstants.EIS_MODULE_MAP;
            }
        });
    }
    
    /**
     * 检索所有可用的模块信息
     * 
     * @return 可用的模块列表
     */
    public List<Module> getEnabledModules() {
        return getEntitiesFromCache(new CacheEntitiesParam<Module>() {

            public List<Module> fetchObjects() {
                return moduleDao.getEnabledModules();
            }

            public String fetchKey() {
                return BaseCacheConstants.EIS_MODULE_LIST;
            }
        });
    }
    
    public List<Module> getEnabledModules(long subsystemId){
    	return moduleDao.getEnabledModules(subsystemId);
    }

    public Set<Integer> getCacheSubsytem() {
        Set<Integer> set = new HashSet<Integer>();
        List<Module> list = getEnabledModules();
        for (Object object : list) {
            Module module = (Module) object;
            set.add(module.getSubsystem());
        }
        return set;
    }

    public Map<String, String> getCacheSubsytemMap() {
        Map<String, String> map = new HashMap<String, String>();
        List<Module> list = getEnabledModules();
        for (Object object : list) {
            Module module = (Module) object;
            map.put(String.valueOf(module.getSubsystem()), String.valueOf(module.getSubsystem()));
        }
        return map;
    }

    // -------------------缓存信息 end--------------------------

    public List<BasicModule> getBasicModules(int platform, int subsystem, int unitClass, Long parentId) {
        return convertBasicModules(getModules(subsystem, unitClass, parentId));
    }

    public List<BasicModule> getParentBasicModules(Long moduleId, boolean hasSubsystem) {
        List<BasicModule> rtnList = new ArrayList<BasicModule>();
        Module module = getModuleByIntId(moduleId);
        if (null == module){
            return rtnList;
        }

        rtnList.add(module);
        while (module.getParentid() != -1) {
            module = getModuleByIntId(module.getParentid());
            rtnList.add(module);
        }
        
        if(hasSubsystem){
            Integer subsystemId = module.getSubsystem();
            module = new Module();
            module.setName(subSystemService.getSubSystem(subsystemId).getName());
            rtnList.add(module);
        }
        return rtnList;
    }

	public Set<Integer> getActiveSubsytems() {
		return getCacheSubsytem();
	}
	
	public Set<Integer> getActiveSubsytems(int platform) {
		 return Collections.emptySet();
	}

	/**
	 * 转化
	 * 
	 * @param modules
	 * @return
	 */
	private List<BasicModule> convertBasicModules(List<Module> modules) {
		List<BasicModule> list = new ArrayList<BasicModule>();
		for (Module module : modules) {
			list.add((BasicModule) module);
		}
		return list;
	}

    public Module getModuleForPB(String mid) {
        return moduleDao.getModuleForPB(mid);
    }

    public Set<Integer> getSubSystemSet(Set<Long> modIds, int unitClass) {
        //模块与系统的对应关系
        Map<Long, Integer> modSubSystemMap = new HashMap<Long, Integer>();
        List<Module> modList = getModuleListByUnitClass(unitClass);
        for (Iterator<Module> iter = modList.iterator(); iter.hasNext();) {
            Module module = (Module) iter.next();
            modSubSystemMap.put(module.getId(), module.getSubsystem());
        }
        
        Set<Integer> subSystemSet = new HashSet<Integer>();
        for (Long modId : modIds) {
            Integer subSystemId = modSubSystemMap.get(modId);
            if (subSystemId != null) {
                subSystemSet.add(subSystemId);
            }
        }
        return subSystemSet;
    }

    public List<Module> getModules(String[] modIds, Integer unitClass) {       
        return moduleDao.getModules(modIds, unitClass);
    }

    /**
     * 缓存中取
     */
    public List<Module> getEnabledModules(int unitClass, int unitType) {
        List<Module> list = getModuleListByUnitClass(unitClass);
        List<Module> modList = new ArrayList<Module>();
        for (Module module : list) {
            if (StringUtils.trimToEmpty(module.getUsertype()).indexOf(unitType + ",") != -1) {
                modList.add(module);
            }
        }
        return modList;
    }

    public List<Module> getModules(int subsystemId, int unitClass) {
        return moduleDao.getModules(subsystemId, unitClass);
    }

    public List<Module> getModules(int subsystemId, int unitClass, Long parentId) {
        return moduleDao.getModules(subsystemId, unitClass, parentId);
    }

    public List<Module> getModules(int subsystemId, int unitClass, int unitType, Long parentId) {
        return moduleDao.getModules(subsystemId, unitClass, parentId, unitType);
    }
    
    public List<Module> getModulesForPC(int subsystemId, int unitClass, int unitType, Long parentId) {
        return moduleDao.getModulesForPc(subsystemId, unitClass, parentId, unitType);
    }
    
    public List<Module> getModulesForMobile(Long parentId, int unitClass, int unitType, String parm) {
        return moduleDao.getModulesForMobile(parentId, unitClass, unitType, parm);
    }

	public List<Module> getModules(Integer... intIds) {
		return moduleDao.getModules(intIds);
	}
	
	public Map<Integer, Module> getModulesMap(Integer... intIds) {
		return moduleDao.getModulesMap(intIds);
	}

    public List<SubSystem> getSubSystems(int unitClass, int unitType) {
        Integer[] idStr = null;
        List<Integer> list = moduleDao.getEnabledSubsytems(unitClass, unitType);
        idStr = new Integer[list.size()];
        for (int i = 0; i < list.size(); i++) {
            idStr[i] = list.get(i);
        }

        if (null != idStr && idStr.length > 0) {
            return subSystemService.getSubSystems(idStr);
        } else {
            return new ArrayList<SubSystem>();
        }
    }

}
