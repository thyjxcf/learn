package net.zdsoft.eis.system.data.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.AppRegistry;
import net.zdsoft.eis.base.common.entity.Module;
import net.zdsoft.eis.base.common.entity.Role;
import net.zdsoft.eis.base.common.entity.SubSystem;
import net.zdsoft.eis.base.common.service.AppRegistryService;
import net.zdsoft.eis.base.common.service.ModuleService;
import net.zdsoft.eis.frame.cache.DefaultCacheManager;
import net.zdsoft.eis.system.cache.SystemCacheConstants;
import net.zdsoft.eis.system.data.dao.RolePermDao;
import net.zdsoft.eis.system.data.entity.RolePerm;
import net.zdsoft.eis.system.data.service.RolePermService;
import net.zdsoft.eis.system.data.service.RoleService;
import net.zdsoft.eis.system.frame.entity.ModelOperator;
import net.zdsoft.eis.system.frame.service.ModelOperatorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author luxingmu
 * @version $Revision: 1.16 $, $Date: 2007/01/15 02:53:32 $
 */
public class RolePermServiceImpl extends DefaultCacheManager implements RolePermService {

    protected transient final Logger log = LoggerFactory.getLogger(getClass());

    private AppRegistryService appRegistryService;
    private ModuleService moduleService;
    private ModelOperatorService modelOperatorService;
    private RoleService roleService;
    private RolePermDao rolePermDao;

    public void setRolePermDao(RolePermDao rolePermDao) {
        this.rolePermDao = rolePermDao;
    }

    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    public void setModelOperatorService(ModelOperatorService modelOperatorService) {
        this.modelOperatorService = modelOperatorService;
    }

    public void setAppRegistryService(AppRegistryService appRegistryService) {
        this.appRegistryService = appRegistryService;
    }

    public void setModuleService(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    // -------------------缓存信息 begin------------------------
    /**
     * 根据roleId删除缓存中的角色权限信息
     * 
     * @param roleId 角色id
     */
    public void removeRolePermsFromCacheByRoleId(String roleId) {
    	String key = SystemCacheConstants.EIS_ROLEPERM_ROLEINTID + roleId;
        removeFromCache(key);
    }
    
    /**
     * 根据角色数字型id，检索该角色拥有的权限信息列表
     * 
     * @param intId 角色数字型id
     * @return 权限信息列表
     */
    public List<RolePerm> getCachedRolePerm(final String roleId) {
        return getEntitiesFromCache(new CacheEntitiesParam<RolePerm>() {

            public List<RolePerm> fetchObjects() {
                return rolePermDao.getRolePerms(roleId);
            }

            public String fetchKey() {
            	return SystemCacheConstants.EIS_ROLEPERM_ROLEINTID + roleId;
            }
        });
    }

    /**
     * 获取所有操作
     * 
     * @return
     */
    private Set<String> getOperatorSet() {
        List<ModelOperator> list = modelOperatorService.getModuleOperationList();
        Set<String> modSet = new HashSet<String>();
        for (ModelOperator modelOperator : list) {
            modSet.add(modelOperator.getOperheading());
        }
        return modSet;
    }

    // -------------------缓存信息 end--------------------------

    /**
     * 生成管理员角色
     * 
     * @param unitId
     * @param unitClass
     * @param unitType
     * @return
     */
    public List<Role> getAdminRole(String unitId, int unitClass, Integer unitType) {
        List<Role> roles = new ArrayList<Role>();
        Set<Module> modelIdSet = new HashSet<Module>();
        Set<Integer> subSystemSet = new HashSet<Integer>();
        List<Module> modelList = moduleService.getEnabledModules(unitClass, unitType);
        log.info("getAdminRole:" + modelList.size());
        subSystemSet.add(SubSystem.SUBSYSTEM_SYSTEM);

        for (Module module : modelList) {
            if (module.getSubsystem() == SubSystem.SUBSYSTEM_SYSTEM// 系统管理的模块
            ) {
                modelIdSet.add(module);// 当前用户最终可以操作的权限
            }
        }
       
        Role adminRole = new Role();
        adminRole.setName("admin");
        adminRole.setRoletype(Role.ROLE_TYPE_UNITADMIN);
        adminRole.setModSet(modelIdSet);
        adminRole.setOperSet(getOperatorSet());
        adminRole.setSubSystemSet(subSystemSet);
        HashSet<String> extraSubSystemSet = new HashSet<String>();
        List<AppRegistry> extraSubSystemList = appRegistryService
                .getAppRegistriesDefaultPerm(unitId);
        for (Iterator<AppRegistry> iter = extraSubSystemList.iterator(); iter.hasNext();) {
            AppRegistry appRegistry = (AppRegistry) iter.next();
            extraSubSystemSet.add(appRegistry.getId());
        }
        adminRole.setExtraSubSystemSet(extraSubSystemSet);

        roles.add(adminRole);
        return roles;
    }

    /**
     * 生成公共权限角色
     * 
     * @param unitClass
     * @return
     */
    public List<Role> getCommonRole(int unitClass) {
        List<Role> roles = new ArrayList<Role>();
        Role commonRole = new Role();
        commonRole.setName("common");
        commonRole.setRoletype(Role.ROLE_TYPE_COMMON);
        commonRole.setModSet(moduleService.getCommonModelIdSet(unitClass));
        commonRole.setOperSet(new HashSet<String>());
        Set<Integer> set = new HashSet<Integer>();
        set.add(SubSystem.SUBSYSTEM_OFFICE);
        commonRole.setSubSystemSet(set);
        commonRole.setExtraSubSystemSet(new HashSet<String>());
        roles.add(commonRole);
        return roles;
    }

    /**
     * 获取登录用户权限角色信息
     * 
     * @param roleId
     * @param withoutCache， 是否取缓存
     * @return
     */
    public List<Role> getCacheRoleList(String[] roleIds) {
        Long beginTime = System.currentTimeMillis();

        List<Role> roles = new ArrayList<Role>();
        for (int i = 0; i < roleIds.length; i++) {
            Role role = (Role) roleService.getCachedPermRole(roleIds[i]);
            if (!role.getIsactive()) { // 忽略锁定的角色
                continue;
            }
            roles.add(role);
        }
        if (log.isInfoEnabled()) {
            log.info("RolePermServiceImpl.getCacheRoleList method was cost "
                    + (System.currentTimeMillis() - beginTime) + " ms");
        }
        return roles;
    }

    public Role wrapRoleWithPerm(Role role) {
        if (role.getSubsystem() != null && !role.getSubsystem().trim().equals("")) {
            String[] subSystemId = role.getSubsystem().split(",");
            Set<Integer> subSystemSet = new HashSet<Integer>();
            for (int i = 0; i < subSystemId.length; i++) {
                subSystemSet.add(Integer.valueOf(subSystemId[i]));
            }
            role.setSubSystemSet(subSystemSet);
        }
        List<RolePerm> rolePermList = getCachedRolePerm(role.getId());
        Set<Module> modSet = new HashSet<Module>();
        Set<String> operSet = new HashSet<String>();
        Set<String> selectedExtraSubSystemSet = new HashSet<String>();
        if (!rolePermList.isEmpty()) {
            for (RolePerm rolePerm : rolePermList) {
                Module module = moduleService.getModuleByIntId(rolePerm.getModuleid());
                if (module != null) {
                    modSet.add(module);
                }
                if (rolePerm.getOperids() != null && !rolePerm.getOperids().trim().equals("")) {
                    String[] operIds = rolePerm.getOperids().split(",");
                    for (int i = 0; i < operIds.length; i++) {
                        ModelOperator operator = modelOperatorService
                                .getModuleOperationByIntId(new Long(operIds[i]));
                        if (operator != null) {
                            operSet.add(operator.getOperheading());
                        }
                    }
                }

            }
        }

        // 获取附属子系统权限关系，附属子系统部分去掉

        role.setModSet(modSet);
        role.setOperSet(operSet);
        role.setExtraSubSystemSet(selectedExtraSubSystemSet);
        return role;
    }

	/* (non-Javadoc)
	 * @see net.zdsoft.eis.system.data.service.RolePermService#getRolePerm(java.lang.String[], java.lang.String)
	 */
	@Override
	public List<String> getRolePerm(String[] roleIds, String moduleId) {
		return rolePermDao.getRolePerm(roleIds, moduleId);
	}

}
