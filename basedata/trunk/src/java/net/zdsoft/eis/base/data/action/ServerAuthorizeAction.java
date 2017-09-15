/* 
 * @(#)ServerAuthorizeAction.java    Created on May 25, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.data.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Server;
import net.zdsoft.eis.base.common.entity.ServerAuthorize;
import net.zdsoft.eis.base.common.entity.SystemVersion;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.entity.Ware;
import net.zdsoft.eis.base.common.service.BasicClassService;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.data.service.BaseServerAuthorizeService;
import net.zdsoft.eis.base.data.service.BaseServerService;
import net.zdsoft.eis.base.data.service.BaseUserService;
import net.zdsoft.eis.base.simple.entity.SimpleClass;
import net.zdsoft.eis.base.simple.entity.SimpleGroup;
import net.zdsoft.eis.base.simple.service.AbstractClassService;
import net.zdsoft.eis.base.simple.service.SimpleClassService;
import net.zdsoft.eis.frame.action.PageAction;

/**
 * 第三方服务授权
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 25, 2011 4:54:22 PM $
 */
public class ServerAuthorizeAction extends PageAction {
    private static final long serialVersionUID = 4439366561911920825L;

    private BaseServerService baseServerService;
    private BaseUserService baseUserService;
    private BaseServerAuthorizeService baseServerAuthorizeService;

    private DeptService deptService;
    private SimpleClassService simpleClassService;
    private BasicClassService basicClassService;

    private int roleType = Ware.ROLE_TYPE_TEACHER;// 服务角色类型
    private int ownerType = User.TEACHER_LOGIN;// 角色类型
    private int userType;// 用户类型
    private String queryUserName;
    private String queryUserRealName;
    private Long[] serverIds;
    private String[] userIds;
    private String[] allUserIds;
    private String[] groupIds;// 部门或班级id
    private int saveByGroupType;// 按服务按部门或班级授权类型：1 授权；2 退订

    private String batchType;// 按服务批量授权类型：部门dept、班级class、用户user

    private List<Server> servers;// 需授权的服务
    private List<User> users;// 用户列表
    private List<SimpleGroup> groups = new ArrayList<SimpleGroup>();// 部门或班级列表

    /**
     * 主页面
     */
    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }

    public List<String[]> getOwnerTypes() {
        List<String[]> list = new ArrayList<String[]>();
        list.add(new String[] { String.valueOf(User.TEACHER_LOGIN), "教师" });
        list.add(new String[] { String.valueOf(User.STUDENT_LOGIN), "学生" });
        list.add(new String[] { String.valueOf(User.FAMILY_LOGIN), "家长" });
        return list;
    }

    public List<String[]> getRoleTypes() {
        List<String[]> list = new ArrayList<String[]>();
        list.add(new String[] { String.valueOf(Ware.ROLE_TYPE_TEACHER), "普通教师" });
        // list.add(new String[] { String.valueOf(Ware.ROLE_TYPE_ADMIN), "单位管理员"
        // });//管理员只能通过按用户进行授权
        list.add(new String[] { String.valueOf(Ware.ROLE_TYPE_STUDENT), "学生" });
        list.add(new String[] { String.valueOf(Ware.ROLE_TYPE_FAMILY), "家长" });
        return list;
    }

    /**
     * 服务列表页面
     */
    public String serverList() {
        servers = queryServerList(roleType);
        return "serverList";
    }

    private List<Server> queryServerList(int roleType) {
        return baseServerService.getAuthorizedServers(getLoginInfo().getUnitClass(), roleType);
    }

    /**
     * 用户列表页面
     * 
     * @return
     */
    public String userList() {
        users = queryUserList(ownerType, null);
        return "userList";
    }

    private List<User> queryUserList(int ownerType, String userTypes) {
        String _queryUserName = queryConvert(queryUserName);
        String _queryUserRlName = queryConvert(queryUserRealName);
        return baseUserService.getUserByNameRealName(getLoginInfo().getUnitID(), ownerType,
                userTypes, _queryUserName, _queryUserRlName, getPage());
    }

    // 将oracle中使用的单个和多个模糊查询符号加上转义符
    private String queryConvert(String str) {
        if (str == null) {
            str = "";
        }
        if (str.equals("")) {
            return str;
        }

        if (str.indexOf("%") != -1) {
            str = str.replace("%", "\\%");
        }
        if (str.indexOf("_") != -1) {
            str = str.replace("_", "\\_");
        }
        if (str.indexOf("'") != -1) {
            str = str.replace("'", "''");
        }
        return str;
    }

    /**
     * 显示用户对应的服务授权
     * 
     * @return
     */
    public String authorizeByUser() {
        // 角色类型，对应Ware.ROLE_TYPE
        int roleType = Ware.ROLE_TYPE_TEACHER;

        if (User.USER_TYPE_COMMON != userType) {
            roleType = Ware.ROLE_TYPE_ADMIN;
        } else {
            switch (ownerType) {
            case User.STUDENT_LOGIN:
                roleType = Ware.ROLE_TYPE_STUDENT;
                break;
            case User.TEACHER_LOGIN:
                roleType = Ware.ROLE_TYPE_TEACHER;
                break;
            case User.FAMILY_LOGIN:
                roleType = Ware.ROLE_TYPE_FAMILY;
                break;
            default:
                break;
            }
        }
        servers = queryServerList(roleType);

        users = baseUserService.getUsers(userIds);
        List<ServerAuthorize> relations = baseServerAuthorizeService.getServerAuthorizes(userIds);
        Set<Long> container = new HashSet<Long>();
        for (ServerAuthorize sa : relations) {
            container.add(sa.getServerId());
        }
        for (Server server : servers) {
            if (container.contains(new Long(server.getId()))) {
                server.setChecked(true);
            }
        }
        return "authorizeByUser";
    }

    /**
     * 显示服务对应的用户授权
     * 
     * @return
     */
    public String authorizeByServer() {
        servers = baseServerService.getServers(serverIds);
        return "authorizeByServer";
    }

    /**
     * 部门或班级列表
     * 
     * @return
     */
    public String groupList() {
        String unitId = getLoginInfo().getUnitID();
        switch (roleType) {
        case Ware.ROLE_TYPE_STUDENT:
        case Ware.ROLE_TYPE_FAMILY:
            List<? extends SimpleClass> classes = getClassService().getClasses(unitId);
            for (SimpleClass cls : classes) {
                groups.add(new SimpleGroup(cls.getId(), cls.getClasscode(), cls
                        .getClassnamedynamic()));
            }
            break;
        case Ware.ROLE_TYPE_TEACHER:
            List<Dept> depts = deptService.getDepts(unitId);
            for (Dept dept : depts) {
                groups.add(new SimpleGroup(dept.getId(), dept.getDeptCode(), dept.getDeptname()));
            }
            break;
        default:
            break;
        }

        return "groupList";
    }

    private int getOwnerTypeByRoleType(int roleType) {
        int ownerType = User.TEACHER_LOGIN;
        switch (roleType) {
        case Ware.ROLE_TYPE_STUDENT:
            ownerType = User.STUDENT_LOGIN;
            break;
        case Ware.ROLE_TYPE_TEACHER:
            ownerType = User.TEACHER_LOGIN;
            break;
        case Ware.ROLE_TYPE_FAMILY:
            ownerType = User.FAMILY_LOGIN;
            break;
        default:
            break;
        }
        return ownerType;
    }

    /**
     * 用户列表
     * 
     * @return
     */
    public String authorizeByServerUser() {
        int ownerType = User.TEACHER_LOGIN;
        // String userTypes = String.valueOf(User.USER_TYPE_COMMON);

        if (Ware.ROLE_TYPE_ADMIN == roleType) {
            // userTypes = String.valueOf(User.USER_TYPE_TOPADMIN) + ","
            // + String.valueOf(User.USER_TYPE_SUBADMIN);

        } else {
            ownerType = getOwnerTypeByRoleType(roleType);
        }

        if (null == groupIds) {
            // 小组分类名称：按部门、按班级
            String groupTypeName = null;
            switch (roleType) {
            case Ware.ROLE_TYPE_STUDENT:
            case Ware.ROLE_TYPE_FAMILY:
                groupTypeName = "班级";
                break;
            default:
                groupTypeName = "部门";
                break;
            }
            promptMessageDto.setInIframe(true);
            promptMessageDto.setOperateSuccess(false);
            promptMessageDto.setPromptMessage("请选择" + groupTypeName);
            promptMessageDto.addHiddenText(new String[] { "roleType", String.valueOf(roleType) });
            promptMessageDto.addOperation(new String[] { "取消", "", "parent.cancel();" });
            return "jsonSuccess";
        } else {
            users = baseUserService.getUsersByGroupId(ownerType, groupIds[0], false);

            // 单个服务委派，显示默认选中的用户
            if (serverIds.length == 1 && users != null) {
                List<ServerAuthorize> relations = baseServerAuthorizeService
                        .getServerAuthorizes(serverIds[0]);
                Set<String> container = new HashSet<String>();
                for (ServerAuthorize sa : relations) {
                    container.add(sa.getUserId());
                }
                for (User user : users) {
                    if (container.contains(user.getId())) {
                        user.setChecked("checked");
                    }
                }
            }
            promptMessageDto.setOperateSuccess(true);
        }

        return "authorizeByServerUser";
    }

    /**
     * 按用户保存授权信息
     * 
     * @return
     */
    public String saveByUser() {
        promptMessageDto.setInIframe(true);
        promptMessageDto.addOperation(new String[] { "确定",
                "serverAuthorize-userList.action" });
        promptMessageDto.addHiddenText(new String[] { "ownerType", String.valueOf(ownerType) });
        String userName = "";
        try {
            userName = baseUserService.getUserNameByIds(userIds);
            baseServerAuthorizeService.saveServerAuthorizesFromUser(userIds, serverIds);
        } catch (Exception e) {
            log.error(e.toString());
            jsonError = userName + "授权出错！";
            promptMessageDto.setErrorMessage(userName + "授权出错！");
            promptMessageDto.setOperateSuccess(false);
            return PROMPTMSG;
        }
        promptMessageDto.setPromptMessage(userName + "授权成功！");
        promptMessageDto.setOperateSuccess(true);
        return "jsonSuccess";
    }

    /**
     * 按服务保存授权信息
     * 
     * @return
     */
    public String saveByServer() {
        promptMessageDto.setInIframe(true);
        promptMessageDto.addOperation(new String[] { "确定", "", "parent.cancel();" });

        int userCnt = 0;// 用户数
        try {
            if (null != batchType && "user".equals(batchType)) {// 按用户授权
                baseServerAuthorizeService.saveServerAuthorizesFromServer(serverIds, userIds,
                        allUserIds);
                if (null != userIds) {
                    userCnt = userIds.length;
                }

            } else {
                if (null != groupIds) {
                    for (String groupId : groupIds) {
                        String[] userIds = getUserIdsByGroupId(groupId);
                        String[] authorizeUserIds = null;
                        if (saveByGroupType == 1) {
                            authorizeUserIds = userIds;
                        } else {
                            authorizeUserIds = null;
                        }
                        baseServerAuthorizeService.saveServerAuthorizesFromServer(serverIds,
                                authorizeUserIds, userIds);

                        if (null != userIds) {
                            userCnt += userIds.length;
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.toString());
            jsonError = "操作出错！";
            promptMessageDto.setErrorMessage("操作出错！");
            promptMessageDto.setOperateSuccess(false);
            return "jsonSuccess";
        }
        promptMessageDto.setPromptMessage("操作成功！用户数：" + String.valueOf(userCnt) + "人");
        promptMessageDto.setOperateSuccess(true);
        return "jsonSuccess";
    }

    /**
     * 根据班级或部门返回用户id
     * 
     * @param groupId
     * @return
     */
    private String[] getUserIdsByGroupId(String groupId) {
        String[] userIds = new String[0];
        int ownerType = getOwnerTypeByRoleType(roleType);
        List<User> users = baseUserService.getUsersByGroupId(ownerType, groupId, false);
        Set<String> userIdSet = new HashSet<String>();
        for (User user : users) {
            userIdSet.add(user.getId());
        }
        if (userIdSet.size() > 0) {
            userIds = userIdSet.toArray(new String[0]);
        }

        return userIds;
    }

    public String saveByServerUser() {
        batchType = "user";
        return saveByServer();
    }

    private AbstractClassService<? extends SimpleClass> getClassService() {
        if (SystemVersion.PRODUCT_EIS.equals(systemDeployService.getProductId())) {
            return basicClassService;
        } else {
            return simpleClassService;
        }
    }

    public void setBaseServerService(BaseServerService baseServerService) {
        this.baseServerService = baseServerService;
    }

    public void setBaseUserService(BaseUserService baseUserService) {
        this.baseUserService = baseUserService;
    }

    public void setBaseServerAuthorizeService(BaseServerAuthorizeService baseServerAuthorizeService) {
        this.baseServerAuthorizeService = baseServerAuthorizeService;
    }

    public void setDeptService(DeptService deptService) {
        this.deptService = deptService;
    }

    public void setSimpleClassService(SimpleClassService simpleClassService) {
        this.simpleClassService = simpleClassService;
    }

    public void setBasicClassService(BasicClassService basicClassService) {
        this.basicClassService = basicClassService;
    }

    public List<Server> getServers() {
        return servers;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<SimpleGroup> getGroups() {
        return groups;
    }

    public void setOwnerType(int ownerType) {
        this.ownerType = ownerType;
    }

    public int getOwnerType() {
        return ownerType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public void setQueryUserName(String queryUserName) {
        this.queryUserName = queryUserName;
    }

    public void setQueryUserRealName(String queryUserRealName) {
        this.queryUserRealName = queryUserRealName;
    }

    public void setServerIds(Long[] serverIds) {
        this.serverIds = serverIds;
    }

    public void setUserIds(String[] userIds) {
        this.userIds = userIds;
    }

    public Long[] getServerIds() {
        return serverIds;
    }

    public String[] getUserIds() {
        return userIds;
    }

    public void setGroupIds(String[] groupIds) {
        this.groupIds = groupIds;
    }

    public void setAllUserIds(String[] allUserIds) {
        this.allUserIds = allUserIds;
    }

    public int getRoleType() {
        return roleType;
    }

    public void setRoleType(int roleType) {
        this.roleType = roleType;
    }

    public String getQueryUserName() {
        return queryUserName;
    }

    public String getQueryUserRealName() {
        return queryUserRealName;
    }

    public void setSaveByGroupType(int saveByGroupType) {
        this.saveByGroupType = saveByGroupType;
    }

}
