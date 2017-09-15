package net.zdsoft.eis.remote;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.Module;
import net.zdsoft.eis.base.common.entity.Role;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.ModuleService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.frame.action.RemoteBaseAction;
import net.zdsoft.eis.frame.util.RemoteCallUtils;
import net.zdsoft.eis.system.data.entity.UserRoleRelation;
import net.zdsoft.eis.system.data.service.RolePermService;
import net.zdsoft.eis.system.data.service.RoleService;
import net.zdsoft.eis.system.data.service.UserRoleRelationService;

import org.apache.commons.lang.StringUtils;

public class RemoteSystemAction extends RemoteBaseAction {

	private static final long serialVersionUID = -8744471756011925249L;

	private static final int SUBSYSTEM_INSPE = 62;

	private ModuleService moduleService;

	private UnitService unitService;

	private UserRoleRelationService userRoleRelationService;

	private RolePermService rolePermService;

	public String getModules() throws Exception{
		String userId = getParamValue("userId");
		String unitId = getParamValue("unitId");
		Unit unit = unitService.getUnit(unitId);
		List<Module> moduleFirstList = moduleService.getModulesForPC(
				SUBSYSTEM_INSPE, unit.getUnitclass(), unit.getUnittype(),
				Long.valueOf(-1));
		List<Module> firstModelList = new ArrayList<Module>();
		Set<Integer> modeSet = new HashSet<Integer>();
		List<Module> resultList = new ArrayList<Module>();
		Set<Integer> activeSubSytem = moduleService.getCacheSubsytem();
		List<UserRoleRelation> relaList = userRoleRelationService
				.getUserRoles(new String[] { userId });
		List<Role> roleList = null;
		if (!relaList.isEmpty()) {
			String[] roleIds = new String[relaList.size()];
			for (int i = 0; i < roleIds.length; i++) {
				roleIds[i] = relaList.get(i).getRoleid();
			}
			roleList = rolePermService.getCacheRoleList(roleIds);
		}
		for (Role role : roleList) {
			if (role.getModSet() != null) {
				for (Module m : role.getModSet()) {
					Integer subId = m.getSubsystem();
					if (activeSubSytem.contains(subId)) {
						modeSet.add(m.getId().intValue());
					}
				}
			}
		}
		
		for (Module pm : moduleFirstList) {
			if (modeSet != null && modeSet.contains(pm.getId().intValue())) {
				firstModelList.add(pm);
				resultList.add(pm);
			}
		}
		for (Module pm : firstModelList) {
			List<Module> secondModelList = moduleService.getModules(appId,
					unit.getUnitclass(), pm.getId());
			for (Module m : secondModelList) {
				if (modeSet != null && modeSet.contains(m.getId().intValue())) {
					m.setParentModName(pm.getName());
					resultList.add(m);
				}
			}
		}
		sendResult(RemoteCallUtils.convertJson(resultList).toString());
		return "";
	}

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public void setUserRoleRelationService(
			UserRoleRelationService userRoleRelationService) {
		this.userRoleRelationService = userRoleRelationService;
	}

	public void setRolePermService(RolePermService rolePermService) {
		this.rolePermService = rolePermService;
	}
}
