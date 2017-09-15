/**
 * 
 */
package net.zdsoft.eis.base.common.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.struts2.ServletActionContext;

import net.zdsoft.eis.base.common.entity.BasicModule;
import net.zdsoft.eis.base.common.entity.SubSystem;
import net.zdsoft.eis.base.common.entity.SystemVersion;
import net.zdsoft.eis.base.common.service.SubSystemService;
import net.zdsoft.eis.base.common.service.SystemVersionService;
import net.zdsoft.eis.base.tree.service.HelpTreeService;
import net.zdsoft.eis.frame.action.BaseAction;

/**
 * @author zhaosf
 * @since 1.0
 * @version $Id: helpAction.java, v 1.0 2007-12-3 上午11:31:44 zhaosf Exp $
 */

public abstract class AbstractHelpAction extends BaseAction implements Helpable {
    private static final long serialVersionUID = 1L;

    private HelpTreeService helpTree;
    private SystemVersionService systemVersionService;// 版本
    private SubSystemService subSystemService;// 子系统

    protected String treeJSCode;

    private SystemVersion systemVersion;// 系统版本
    private List<BasicModule> searchModuleList;
    private SubSystem subSystem;

    @Override
    public String execute() throws Exception {
        subSystem = subSystemService.getSubSystem(appId);
        systemVersion = systemVersionService.getSystemVersion();
        return SUCCESS;
    }

    public String genTree() throws Exception {
        systemVersion = systemVersionService.getSystemVersion();
        if(appId == 0){
            Set<Integer> subsystems = getBasicModuleService().getActiveSubsytems();
            treeJSCode = helpTree.getXTree(ServletActionContext.getRequest().getContextPath(),
                    getBasicModuleService(), subsystems, getLoginInfo().getUnitClass());
        }else{
            //后台管理登录时为null
            int unitClass = 0;
            if(null != getLoginInfo()){
                unitClass = getLoginInfo().getUnitClass();
            }
            treeJSCode = helpTree.getXTree(ServletActionContext.getRequest().getContextPath(),
                    getBasicModuleService(), appId, unitClass, platform);
        }

        if (getModuleID() == 0) {
            searchModuleList = new ArrayList<BasicModule>();
        } else {            
            searchModuleList = getBasicModuleService().getParentBasicModules(
                    Long.valueOf(String.valueOf(getModuleID())), appId == 0 ? true : false);
        }
        subSystem = subSystemService.getSubSystem(appId);
        return SUCCESS;
    }

    public SystemVersion getSystemVersion() {
        return systemVersion;
    }

    public String getTreeJSCode() {
        return treeJSCode;
    }

    public List<BasicModule> getSearchModuleList() {
        return searchModuleList;
    }

    public void setHelpTree(HelpTreeService helpTree) {
        this.helpTree = helpTree;
    }

    public SubSystem getSubSystem() {
        return subSystem;
    }

    public void setSystemVersionService(SystemVersionService systemVersionService) {
        this.systemVersionService = systemVersionService;
    }

    public void setSubSystemService(SubSystemService subSystemService) {
        this.subSystemService = subSystemService;
    }
    
}
