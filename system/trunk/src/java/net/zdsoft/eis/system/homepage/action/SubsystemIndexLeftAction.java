package net.zdsoft.eis.system.homepage.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.Module;
import net.zdsoft.eis.base.common.entity.SystemIni;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.ModuleService;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.common.service.UserLogService;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.eis.frame.client.LoginInfo;
import net.zdsoft.eis.system.constant.SystemConstant;

public class SubsystemIndexLeftAction extends BaseAction {
    private static final long serialVersionUID = -1598343647344392998L;
    
    private long parentId;
    private List<Module> modules;
    private Module module;
    private List<Module> topModules;// 返回模块第一层的目录
    private Map<Long, List<Module>> secondModules = new HashMap<Long, List<Module>>();// 第二层的目录

    private ModuleService moduleService;
    private UserLogService userLogService;// 用户日志
    private SystemIniService systemIniService;// 系统选项

    public void setModuleService(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    public void setUserLogService(UserLogService userLogService) {
        this.userLogService = userLogService;
    }

    public void setSystemIniService(SystemIniService systemIniService) {
        this.systemIniService = systemIniService;
    }

    public String execute() throws Exception {

        LoginInfo loginInfo = getLoginInfo();       
        modules = new ArrayList<Module>();
        List<Module> tempModules = new ArrayList<Module>();

        if (getModuleID()>0) {
            module = moduleService.getModuleByIntId(getModuleID());
            if (module == null) {
                setModuleID(0);
            } 
        } else {
            setModuleID(0);
        }

        if (parentId <= 0) {
            tempModules = moduleService.getModules(appId, loginInfo.getUnitClass(),
                    loginInfo.getUnitType(), (module == null ? -1 : module.getParentid()));
        } else {
            tempModules = moduleService.getModules(appId, loginInfo.getUnitClass(),
                    loginInfo.getUnitType(), parentId);
        }

        for (Iterator<Module> iter = tempModules.iterator(); iter.hasNext();) {
            Module m = (Module) iter.next();
            if (loginInfo.validateAllModel(m.getId().intValue())) {
                modules.add(m);
            }
        }
        return SUCCESS;
    }

    public String smallLeft() throws Exception {
        LoginInfo loginInfo = this.getLoginInfo();
        topModules = new ArrayList<Module>();

        List<Module> tempList = moduleService.getModules(appId, loginInfo
                .getUnitClass(), loginInfo.getUnitType(), -1L);

        // 检查用户是否有此模块的权限
        for (Iterator<Module> iter = tempList.iterator(); iter.hasNext();) {
            Module module = (Module) iter.next();
            if (loginInfo.validateAllModel(module.getId().intValue())) {
                topModules.add(module);
            }
        }

        // 取二级模块
        for (Iterator<Module> iter = topModules.iterator(); iter.hasNext();) {
            Module module = (Module) iter.next();
            List<Module> tlist = moduleService.getModules(appId, loginInfo
                    .getUnitClass(), loginInfo.getUnitType(), module.getId());

            List<Module> resultList = new ArrayList<Module>();
            // 检查用户是否有此模块的权限
            for (Iterator<Module> iters = tlist.iterator(); iters.hasNext();) {
                Module modules = (Module) iters.next();
                if (loginInfo.validateAllModel(modules.getId().intValue())) {
                    resultList.add(modules);
                }
            }

            if (resultList != null) {
                secondModules.put(module.getId(), resultList);

            }
        }

        // 日志提醒
        Integer logCnt = userLogService.getLogCount();
        if (null != logCnt) {
            @SuppressWarnings("unused")
            long remindCnt = 0;
            SystemIni systemIniDto = systemIniService.getSystemIni(SystemConstant.SYSTEM_MAXLOG);
            if (null != systemIniDto) {
                String value = systemIniDto.getNowValue();
                if (null != value && !"".equals(value.trim())) {
                    remindCnt = Long.parseLong(value);
                }
            }
        }

        if (loginInfo.getUnitClass().intValue() != Unit.UNIT_CLASS_EDU
                && loginInfo.getUnitClass().intValue() != Unit.UNIT_CLASS_SCHOOL) {
            addActionError("未知的单位分类代码，请确认单位的分类信息是否正确！");
            return ERROR;
        }
        /*
        if (modID != null && !modID.equals("")) {
            Module module = moduleService.getModule(modID, loginInfo.getUnitClass());
        }*/
        return SUCCESS;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }
 
    public List<Module> getTopModules() {
        return topModules;
    }

    public Map<Long, List<Module>> getSecondModules() {
        return secondModules;
    }

    public void setTopModules(List<Module> topModules) {
        this.topModules = topModules;
    }

}
