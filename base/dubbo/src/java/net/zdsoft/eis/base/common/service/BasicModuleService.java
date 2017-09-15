/* 
 * @(#)BaseModuleService.java    Created on Aug 24, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.common.service;

import java.util.List;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.BasicModule;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Aug 24, 2010 2:51:14 PM $
 */
public interface BasicModuleService {
    /**
     * 取得指定父节点的module的列表，使帮助统一起来
     * @param platform 
     * @param subsystem
     * @param unitClass
     * @param parentId
     * 
     * @return
     */
    public List<BasicModule> getBasicModules(int platform, int subsystem, int unitClass, Long parentId);

    /**
     * 取模块的所有上级
     * 
     * @param moduleId
     * @param hasSubsystem 是否包含所属子系统
     * @return
     */
    public List<BasicModule> getParentBasicModules(Long moduleId, boolean hasSubsystem);

    /**
     * 取启用的子系统
     * 
     * @return
     */
    public Set<Integer> getActiveSubsytems();
    
    /**
     * 取启用的子系统
     * 
     * @return
     */
    public Set<Integer> getActiveSubsytems(int platform);
}
