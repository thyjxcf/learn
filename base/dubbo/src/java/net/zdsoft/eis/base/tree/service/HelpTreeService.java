package net.zdsoft.eis.base.tree.service;

import java.util.Set;

import net.zdsoft.eis.base.common.service.BasicModuleService;

public interface HelpTreeService {
    /**
     * 
     * @param contextPath
     * @param basicModuleService
     * @param subsystem
     * @param unitClass
     * @param platform 
     * @return
     * @throws Exception
     */
    public String getXTree(String contextPath, BasicModuleService basicModuleService, int subsystem, int unitClass, int platform)
            throws Exception;
    
    /**
     * 
     * @param contextPath
     * @param basicModuleService
     * @param subsystems
     * @param unitClass
     * @return
     * @throws Exception
     */
    public String getXTree(String contextPath, BasicModuleService basicModuleService,
            Set<Integer> subsystems, int unitClass) throws Exception;
}
