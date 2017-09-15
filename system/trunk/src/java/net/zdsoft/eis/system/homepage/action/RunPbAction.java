/* 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author zhangza
 * @since 1.0
 * @version $Id$
 */
package net.zdsoft.eis.system.homepage.action;

import net.zdsoft.eis.base.common.entity.Module;
import net.zdsoft.eis.base.common.entity.School;
import net.zdsoft.eis.base.common.service.ModuleService;
import net.zdsoft.eis.base.common.service.SchoolService;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.eis.system.homepage.dto.PBModule;

public class RunPbAction extends BaseAction {
    private static final long serialVersionUID = 1L;

    private ModuleService moduleService;
    private PBModule pbModule;
    private SchoolService schoolService;

    public void setSchoolService(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    public String execute() throws Exception {
        int moduleId = getModuleID();
        Module module = moduleService.getModuleByIntId(moduleId);
        School school = schoolService.getSchool(getLoginInfo().getUnitID());
        if (school == null) {
            addActionError("学校信息尚未维护，请先到学籍系统中维护学校信息！");
            return ERROR;
        }
        if (module == null) {
            addActionError("模块不存在！(" + moduleId + ")");
            return ERROR;
        } else {
            pbModule = new PBModule();
            pbModule.setModuleId(moduleId);
            pbModule.setWinName(module.getWin());
            pbModule.setActionParam(String.valueOf(module.getActionenable()));
            pbModule.setFileList(module.getFilelist());
            pbModule.setFileUrl(module.getReldir());
            pbModule.setHeight(module.getHeight());
            pbModule.setWidth(module.getWidth());
            pbModule.setIsResize(module.getLimit());
            pbModule.setMainFile(module.getMainfile());
            pbModule.setPbd(module.getPbcommon());
            pbModule.setProject("EISS");
            pbModule.setVersion(module.getVersion());
            return SUCCESS;
        }

    }

    public PBModule getPbModule() {
        return pbModule;
    }

    public void setModuleService(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

}
