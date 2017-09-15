/* 
 * @(#)UserRoleAction.java    Created on 2006-11-28
 * Copyright (c) 2006 ZDSoft Networks, Inc. All rights reserved.
 * $Header: f:/44cvsroot/stusys/stusys/src/net/zdsoft/stusys/system/usermanager/action/ViewUserRightAction.java,v 1.6 2007/01/16 10:12:44 luxm Exp $
 */
package net.zdsoft.eis.system.data.action;

import java.util.HashSet;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.Module;
import net.zdsoft.eis.base.common.entity.Role;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.ModuleService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.tree.service.FastTreeNode;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.eis.frame.client.LoginInfo;
import net.zdsoft.eis.system.data.service.RoleService;
import net.zdsoft.eis.system.data.service.UserLoginService;
import net.zdsoft.eis.system.frame.service.ModuleTreeService;

import org.apache.commons.lang3.StringUtils;

import com.opensymphony.xwork2.util.profiling.UtilTimerStack;

/**
 * @author luxingmu
 * @version $Revision: 1.6 $, $Date: 2007/01/16 10:12:44 $
 */
public class ViewUserRightAction extends BaseAction {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -113703969774779384L;

    private String treeData;

    // private UserService userService;
    private UnitService unitService;
    private RoleService roleService;
    private ModuleService moduleService;
    // private ModelOperatorService modelOperatorService;
    // private UserRoleRelationService userRoleRelationService;
    private ModuleTreeService moduleTreeService;
    private UserLoginService userLoginService;
    private UserService userService;
    private String uid;

    //private String treeName;
    private User user;
    private String roleId;

    public String execute() throws Exception {
        String logMsg = "permissionTree";
        UtilTimerStack.push(logMsg);
        user = userService.getUser(uid);
        FastTreeNode tree=null;
        if (StringUtils.isNotBlank(roleId)) {
            Unit unit = unitService.getUnit(user.getUnitid());
            Role role = roleService.getCachedPermRole(roleId);
            Set<Integer> moduleIdSet = new HashSet<Integer>();
            if(role.getModSet().size()==0 && Role.ROLE_IDENTIFIER_DEFAULT.equals(role.getIdentifier())){
                LoginInfo theLoginInfo = userLoginService.initLoginInfo(user.getName());
                Set<Long> defaultModSet = moduleService.getCommonModuleIdSet(theLoginInfo.getUnitClass());
                Set<Integer> defaultIntegerModSet = new HashSet<Integer>();
                for(Long id:defaultModSet){
                	defaultIntegerModSet.add(Integer.valueOf(id.toString()));
                }
                tree = moduleTreeService.getUserPermModuleTree2(theLoginInfo.getUnitClass(), theLoginInfo
                        .getUnitType(), defaultIntegerModSet, new HashSet<String>());
            	
            }else{
	            for (Module module : role.getModSet()) {
	                moduleIdSet.add(module.getId().intValue());
	            }
	            tree = moduleTreeService.getUserPermModuleTree2(unit.getUnitclass(), unit.getUnittype(),
	                    moduleIdSet, role.getOperSet());
            }
        } else {
            LoginInfo theLoginInfo = userLoginService.initLoginInfo(user.getName());
            tree = moduleTreeService.getUserPermModuleTree2(theLoginInfo.getUnitClass(), theLoginInfo
                    .getUnitType(), theLoginInfo.getAllModSet(), theLoginInfo.getAllOperSet());
        }
        this.treeData = tree.toString();
        UtilTimerStack.pop(logMsg);
        return SUCCESS;
    }


    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public void setUserLoginService(UserLoginService userLoginService) {
        this.userLoginService = userLoginService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setUnitService(UnitService unitService) {
        this.unitService = unitService;
    }

    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * @return
     
    private String doErrorOperation(String errorString) {
        promptMessageDto = new PromptMessageDto();
        promptMessageDto.setPromptMessage(errorString);
        promptMessageDto.setOperateSuccess(false);
        promptMessageDto.addOperation(new String[] { "确定", "userAdmin.action" });
        return PROMPTMSG;
    }
     */
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTreeData() {
        return treeData;
    }

    public User getUser() {
        return user;
    }

    public void setModuleTreeService(ModuleTreeService moduleTreeService) {
        this.moduleTreeService = moduleTreeService;
    }

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}
}
