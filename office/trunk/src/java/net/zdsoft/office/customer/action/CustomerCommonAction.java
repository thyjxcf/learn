package net.zdsoft.office.customer.action;



import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.frame.action.PageAction;

import org.apache.commons.lang.StringUtils;

public class CustomerCommonAction  extends PageAction{

	private static final long serialVersionUID = 1L;
	
	private static final String subsystemId = "70";
	
	protected CustomRoleService customRoleService;

	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	} 
	
	/**
	 * 判断当前用户是否指定角色
	 * @param roleCode
	 */
	public boolean isCustomRole(String roleCode){
		if(StringUtils.isNotBlank(roleCode)){
			List<CustomRole> customRoles = customRoleService.getCustomRoleListByUserId(getLoginInfo().getUnitID(), getLoginInfo().getUser().getId(), subsystemId);
			for(CustomRole customRole : customRoles){
				if(roleCode.equals(customRole.getRoleCode())){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 获取当前用户角色权限
	 */
	public Set<String> getCustomRoleCode(){
		Set<String> codes = new HashSet<String>();
		List<CustomRole> customRoles = customRoleService.getCustomRoleListByUserId(getLoginInfo().getUnitID(), getLoginInfo().getUser().getId(), subsystemId);
		for(CustomRole customRole : customRoles){
			codes.add(customRole.getRoleCode());
		}
		return codes;
	}
	
	/**
	 * 获取当前用户角色权限
	 */
	public List<CustomRole> getCustomRoles(){
		return  customRoleService.getCustomRoleListByUserId(getLoginInfo().getUnitID(), getLoginInfo().getUser().getId(), subsystemId);
	}
	
}
