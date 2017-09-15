package net.zdsoft.eis.base.common.action;

import net.zdsoft.eis.base.common.service.BasicModuleService;
import net.zdsoft.eis.base.common.service.ModuleService;

/**
 * 教师平台帮助
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Aug 24, 2010 4:02:40 PM $
 */
public class TeacherHelpAction extends AbstractHelpAction {
    private static final long serialVersionUID = 1L;

    private ModuleService moduleService;// 模块

    public void setModuleService(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @Override
    public BasicModuleService getBasicModuleService() {
        return moduleService;
    }

}
