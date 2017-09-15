/* 
 * @(#)ModelOperatorService.java    Created on Jun 22, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.system.frame.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.system.frame.entity.ModelOperator;
import net.zdsoft.leadin.cache.CacheManager;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jun 22, 2010 2:33:03 PM $
 */
public interface ModelOperatorService extends CacheManager {
    /**
     * 取出模块功能点
     * 
     * @param operationIntId 模块功能点数字型id
     * @return 模块功能点
     */
    public ModelOperator getModuleOperationByIntId(long operationIntId);
    
    /**
     * 取出所有有效的模块功能点
     * 
     * @return 有效的模块功能点列表
     */
    public List<ModelOperator> getModuleOperationList();
    
    /**
     * 操作对应模块id缓存
     * 
     * @return <id,modelId>
     */
    public Map<Long, Long> getModelOperatorMap();
    
    /**
     * 获取所有操作主键ID
     * 
     * @return
     */
    public Set<Long> getOperatorIdSet();
}
