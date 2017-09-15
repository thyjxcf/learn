/* 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author zhangza
 * @since 1.0
 * @version $Id$
 */
package net.zdsoft.eis.system.homepage.action;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.Module;
import net.zdsoft.eis.base.common.service.ModuleService;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.eis.frame.client.LoginInfo;

public class SubsystemIndexRightAction extends BaseAction {
    private static final long serialVersionUID = 1L;

    private ModuleService moduleService;
    private long parentId;
    private List<Module> modules;
    Set<Long> selectedModSet = new HashSet<Long>();
    Set<Long> selectedOperSet = new HashSet<Long>();
    Set<String> selectedExtraSubSystemSet = null;

    public String execute() throws Exception {
        LoginInfo loginInfo = getLoginInfo();
        modules = moduleService.getModules(appId, loginInfo.getUnitClass(),
                loginInfo.getUnitType(), parentId);

        

        int count = modules.size();
        for (int i = count - 1; i >= 0; i--) {
            if (!getLoginInfo().validateAllModel((((Module) modules.get(i)).getId()).intValue())) {
                modules.remove(i);
            }
        }

        int addLength = 0;
        // 让页面显示得更合理，不满四个的，居左
        // *******************************
        if (modules.size() < 4) {
            addLength = 4 - modules.size();
        }
        for (int i = 0; i < addLength; i++) {
            Module module = new Module();
            modules.add(module);
        }
        // *********************************
        return SUCCESS;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public void setModuleService(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

}
