/**
 * 
 */
package net.zdsoft.eis.base.common.action;

import net.zdsoft.eis.base.common.service.BasicModuleService;
import net.zdsoft.eis.base.common.service.SimpleModuleService;

/**
 * @author zhaosf
 * @since 1.0
 * @version $Id: helpAction.java, v 1.0 2007-12-3 上午11:31:44 zhaosf Exp $
 */

public class SimpleHelpAction extends AbstractHelpAction {
    private static final long serialVersionUID = 1L;

    private SimpleModuleService simpleModuleService;// 模块

    public void setSimpleModuleService(SimpleModuleService simpleModuleService) {
        this.simpleModuleService = simpleModuleService;
    }

    public BasicModuleService getBasicModuleService() {
        return simpleModuleService;
    }

}
