/* 
 * @(#)ModelOperatorServiceImpl.java    Created on Jun 22, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.system.frame.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.frame.cache.DefaultCacheManager;
import net.zdsoft.eis.system.cache.SystemCacheConstants;
import net.zdsoft.eis.system.frame.dao.ModelOperatorDao;
import net.zdsoft.eis.system.frame.entity.ModelOperator;
import net.zdsoft.eis.system.frame.service.ModelOperatorService;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jun 22, 2010 2:33:28 PM $
 */
public class ModelOperatorServiceImpl extends DefaultCacheManager implements ModelOperatorService {
    private ModelOperatorDao moduleOperatorDao;
 
    public void setModuleOperatorDao(ModelOperatorDao moduleOperatorDao) {
        this.moduleOperatorDao = moduleOperatorDao;
    }

    // -------------------缓存信息 begin------------------------
    /**
     * 取出模块功能点
     * 
     * @param operationIntId 模块功能点数字型id
     * @return 模块功能点
     */
    public ModelOperator getModuleOperationByIntId(final long operationIntId) {
        return getEntityFromCache(new CacheEntityParam<ModelOperator>() {

            public ModelOperator fetchObject() {
                return moduleOperatorDao.getModelOperator(operationIntId);
            }

            public String fetchKey() {
                return fetchCacheEntityKey() + operationIntId;
            }
        });
    }

    /**
     * 取出所有有效的模块功能点
     * 
     * @return 有效的模块功能点列表
     */
    public List<ModelOperator> getModuleOperationList() {
        return getEntitiesFromCache(new CacheEntitiesParam<ModelOperator>() {
            public List<ModelOperator> fetchObjects() {
                return moduleOperatorDao.getModelOperators();
            }

            public String fetchKey() {
                return SystemCacheConstants.EIS_MODULEOPERATION;
            }
        });
    }


    public Map<Long, Long> getModelOperatorMap() {
        Map<Long, Long> map = new HashMap<Long, Long>(); // 操作对应模块id缓存
        List<ModelOperator> operList = getModuleOperationList();
        for (Iterator<ModelOperator> iter = operList.iterator(); iter.hasNext();) {
            ModelOperator modelOperator = (ModelOperator) iter.next();
            Long operId = modelOperator.getId();
            Long moduleid = modelOperator.getModuleid();
            map.put(operId, moduleid);
        }
        return Collections.unmodifiableMap(map);
    }
    
    /**
     * 获取所有操作主键ID
     * 
     * @return
     */
    public Set<Long> getOperatorIdSet() {
        List<ModelOperator> list = getModuleOperationList();
        Set<Long> modSet = new HashSet<Long>();
        for (ModelOperator modelOperator : list) {
            modSet.add(modelOperator.getId());
        }
        return modSet;
    }
    // -------------------缓存信息 end--------------------------
}
