/* 
 * @(#)RoleAccreditAction.java    Created on 2006-10-13
 * Copyright (c) 2006 ZDSoft Networks, Inc. All rights reserved.
 * $Header: f:/44cvsroot/stusys/stusys/src/net/zdsoft/stusys/system/usermanager/action/RoleAccreditAction.java,v 1.8 2007/01/18 12:46:53 luxm Exp $
 */
package net.zdsoft.eis.system.data.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.eis.frame.client.LoginInfo;
import net.zdsoft.eis.frame.dto.PromptMessageDto;
import net.zdsoft.eis.system.data.entity.UserRoleRelation;
import net.zdsoft.eis.system.data.service.UserRoleRelationService;
import net.zdsoft.leadin.util.AssembleTool;

import org.apache.commons.lang.StringUtils;

/**
 * @author luxingmu
 * @version $Revision: 1.8 $, $Date: 2007/01/18 12:46:53 $
 */
public class RoleAccreditAction extends BaseAction {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -1395882369549706139L;
    
    private static final String RESULT_TYPE_INDEX = "index";
    private static final String RESULT_TYPE_SAVE = "save";
    
    private String deptIdOfTopDept = BaseConstant.ZERO_GUID;
    private String roleids;

    private String operation;

    private String deptId;

    private List<User> userList;

    private UserService userService;

    private UserRoleRelationService userRoleRelationService;

    private String[] userids;
    private String unitId;
    private String allUserIds;
    private String noSelectedUserIds;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public final String getUnitId() {
        return unitId;
    }

    public final void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String[] getUserids() {
        return userids;
    }

    public void setUserids(String[] userids) {
        this.userids = userids;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getRoleids() {
        return roleids;
    }

    public void setRoleids(String roleids) {
        this.roleids = roleids;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public String frameAction() {
        LoginInfo loginInfo = getLoginInfo();
        unitId = loginInfo.getUser().getUnitid();
        return operation;
    }

    @SuppressWarnings("unchecked")
    public String execute() throws Exception {
        String oper = getOperation();
        if (StringUtils.isEmpty(oper)) {
            userList = new ArrayList<User>();
            oper = RESULT_TYPE_INDEX;
        }
        LoginInfo info = getLoginInfo();
        if (RESULT_TYPE_INDEX.equals(oper)) {
            if (StringUtils.isEmpty(roleids)) {
                userList = new ArrayList<User>();
            } else {
                if (deptId.equals(deptIdOfTopDept)) {
                    userList = userService.getUsersByUnitAndType(info.getUnitID(),
                            User.USER_TYPE_COMMON);
                } else {
                    userList = userService.getUsers(info.getUnitID(), deptId);
                }
                if (roleids.endsWith(",")) {
                    roleids = roleids.substring(0, roleids.lastIndexOf(","));
                }
                String[] roleIdString = roleids.split(",");
                // 单个角色委派，显示默认选中的用户
                if (roleIdString.length == 1 && userList != null) {
                    List<UserRoleRelation> relations = userRoleRelationService
                            .getUserRoles(new String(roleIdString[0]));
                    Set<String> container = new HashSet<String>();
                    for (UserRoleRelation userRole : relations) {
                        container.add(userRole.getUserid());
                    }
                    for (User userDto : userList) {
                        if (container.contains(userDto.getId())) {
                            userDto.setChecked("checked");
                        }
                    }
                }
            }
        } else if (RESULT_TYPE_SAVE.equals(oper)) {
            if (roleids.endsWith(",")) {
                roleids = roleids.substring(0, roleids.lastIndexOf(","));
            }
            String[] roleIdString = roleids.split(",");
//            Long[] roleIdInteger = new Long[roleIdString.length];
//            for (int i = 0; i < roleIdString.length; i++) {
//                roleIdInteger[i] = new Long(roleIdString[i]);
//            }
            oper = saveRoleRelation(userids, roleIdString,deptId);
        }

        return oper;
    }

    /**
     * 保存用户角色关系数据
     * 
     * @return
     */
    private String saveRoleRelation(String[] userids, String[] roleids,String deptId) {
        promptMessageDto = new PromptMessageDto();
        LoginInfo loginInfo = getLoginInfo();
        unitId = loginInfo.getUser().getUnitid();
        try {
            // 下面两行是新增 2009-3-19
            String[] noUserids = allUserIds.split(",");
            allUserIds = AssembleTool.getSqlInString(noUserids, ",");
            userRoleRelationService.saveUserRoles(userids, roleids, unitId,deptId);
        } catch (Exception e) {
            log.error(e.toString());
            promptMessageDto.setErrorMessage("出错啦！");
            promptMessageDto.setOperateSuccess(false);
            promptMessageDto.addOperation(new String[] {
                    "确定",
                    "roleAccredit.action?moduleID=" + this.getModuleID() + "&operation=index&roleids="
                            + this.roleids + ",&deptId=" + deptIdOfTopDept + "&unitId=" + unitId });
            return "jsonSuccess";
        }
        promptMessageDto.setPromptMessage("数据保存成功！");
        promptMessageDto.setOperateSuccess(true);
        promptMessageDto.addOperation(new String[] { "确定", "roleAdmin.action" });
        return "jsonSuccess";
    }

    public void setUserRoleRelationService(UserRoleRelationService userRoleRelationService) {
        this.userRoleRelationService = userRoleRelationService;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public final String getNoSelectedUserIds() {
        return noSelectedUserIds;
    }

    public final void setNoSelectedUserIds(String noSelectedUserIds) {
        this.noSelectedUserIds = noSelectedUserIds;
    }

    public final String getAllUserIds() {
        return allUserIds;
    }

    public final void setAllUserIds(String selectedUserIds) {
        this.allUserIds = selectedUserIds;
    }

}
