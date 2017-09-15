/* 
 * @(#)UserLoginServiceImpl.java    Created on Sep 13, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.system.data.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Role;
import net.zdsoft.eis.base.common.entity.SubSystem;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.ModuleService;
import net.zdsoft.eis.base.common.service.OrderService;
import net.zdsoft.eis.base.common.service.SubSystemService;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.deploy.SystemDeployService;
import net.zdsoft.eis.base.subsystemcall.service.DataCenterSubsystemService;
import net.zdsoft.eis.frame.client.LoginInfo;
import net.zdsoft.eis.system.data.entity.UserRoleRelation;
import net.zdsoft.eis.system.data.service.RolePermService;
import net.zdsoft.eis.system.data.service.UserLoginService;
import net.zdsoft.eis.system.data.service.UserRoleRelationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Sep 13, 2010 2:30:28 PM $
 */
public class UserLoginServiceImpl implements UserLoginService {
    private static final Logger log = LoggerFactory.getLogger(UserLoginServiceImpl.class);
    
    private UserService userService;
    private TeacherService teacherService;
    private DeptService deptService;
    private UnitService unitService;
    private RolePermService rolePermService; // 角色模块
    private UserRoleRelationService userRoleRelationService; // 用户角色关系
    private ModuleService moduleService;// 模块信息
    private SubSystemService subSystemService;// 子系统信息
    private OrderService orderService;
    private SystemDeployService systemDeployService;
    private DataCenterSubsystemService dataCenterSubsystemService;
    
    public void setDataCenterSubsystemService(
			DataCenterSubsystemService dataCenterSubsystemService) {
		this.dataCenterSubsystemService = dataCenterSubsystemService;
	}

	public void setSystemDeployService(SystemDeployService systemDeployService) {
        this.systemDeployService = systemDeployService;
    }
    
    public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public void setDeptService(DeptService deptService) {
        this.deptService = deptService;
    }

    public void setRolePermService(RolePermService rolePermService) {
        this.rolePermService = rolePermService;
    }

    public void setUserRoleRelationService(UserRoleRelationService userRoleRelationService) {
        this.userRoleRelationService = userRoleRelationService;
    }

    public void setModuleService(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setUnitService(UnitService unitService) {
        this.unitService = unitService;
    }

    public void setSubSystemService(SubSystemService subSystemService) {
        this.subSystemService = subSystemService;
    }

    // ========================以上为set方法===============================
    public LoginInfo initLoginInfo(String uid) {
        // 根据模块，取出所有可用的子系统id
        Set<Integer> activeSubSytem = moduleService.getCacheSubsytem();
        User user = userService.getUserByUserName(uid);
        Teacher teacher =teacherService.getTeacher(user.getTeacherid());
        // 初始化用户所在部门信息
        Dept dept = deptService.getDept(user.getDeptid());
        if (dept != null) {
            user.setDeptid(dept.getId());
            user.setDeptName(dept.getDeptname());
            //user.setInstituteId(dept.getInstituteId());
        }
        user.setInstituteId(teacher.getInstituteId());
        Unit unit = unitService.getUnit(user.getUnitid());
        List<Role> roleList = null;
        if (user.getType().intValue() == User.USER_TYPE_TOPADMIN
                || user.getType().intValue() == User.USER_TYPE_SUBADMIN) {
            // 单位管理员,自动生成一种权限列表存在cache(ArrayList) 这里内部没有角色的涉及
            roleList = rolePermService.getAdminRole(user.getUnitid(), unit.getUnitclass(), unit.getUnittype());
        } else {
            List<UserRoleRelation> relaList = userRoleRelationService.getUserRoles(new String[] { user.getId()
                    .toString() });
            if (!relaList.isEmpty()) {
            	String[] roleIds = new String[relaList.size()];
                for (int i = 0; i < roleIds.length; i++) {
                    roleIds[i] = relaList.get(i).getRoleid();
                }
                roleList = rolePermService.getCacheRoleList(roleIds);
            } 
//            else {
//                try {
//                    // 设置新增用户为默认角色
//                    Role role = roleService.getRole(user.getUnitid(), Role.ROLE_IDENTIFIER_DEFAULT);
//                    if (role != null) {
//                        // 得到此新增用户的id
//                        // 得到用户所在单位的默认角色id
//                        userRoleRelationService.saveUserRolesFromUser(new String[] { user.getId() }, new String[] { role
//                                .getId() }, user.getUnitid());
//                        roleList = rolePermService.getCacheRoleList(new String[] { role.getId() });
//                    }
//                } catch (Exception e) {
//                    log.error("add default role to user :" + user.getName() + " error!", e);
//                }
//            }
        }

        LoginInfo loginInfo = null;
        List<Role> commonRoleList = rolePermService.getCommonRole(unit.getUnitclass());
        Map<Integer, SubSystem> allSubSystem = subSystemService.getCacheSubSystemMap();
        boolean isHaveLastData = false;
        boolean isPreEdu = false;
        //如果子系统中包含数据中心，则需查询基表中上年度是否有上年度结转数据
        if(activeSubSytem.contains(SubSystem.SUBSYSTEM_DATACENTER)){
	        if(dataCenterSubsystemService != null){
	        	isHaveLastData = dataCenterSubsystemService.isHaveLastYearData(user.getUnitid());
	        	isPreEdu = dataCenterSubsystemService.isPreEdu(user.getUnitid());
	        }
        }
        
        // 如果是数字校园，则用户能用的子系统等信息还需要做过滤
        if (systemDeployService.isOrderMode()) {
            activeSubSytem = orderService.getOrderSystem(user.getId(), user.getOwnerType(), user.getUnitid());
            // if (user.getType().intValue() ==
            // GlobalConstant.USER_TYPE_TOPADMIN
            // || user.getType().intValue() ==
            // GlobalConstant.USER_TYPE_SUBADMIN) {
            // 系统管理默认是都打开的
            activeSubSytem.add(SubSystem.SUBSYSTEM_SYSTEM);
            // }
        }
        try {
            loginInfo = new LoginInfo(user, unit, roleList, commonRoleList, allSubSystem, activeSubSytem,isHaveLastData,isPreEdu);
        } catch (Exception e) {
            log.error("create loginInfo object error!", e);
        }

        return loginInfo;
    }
}
