package net.zdsoft.office.expenditure.action;

import java.util.List;

import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.frame.action.SwfUploadAction;
import net.zdsoft.office.expenditure.constant.ExpenConstants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

public class OfficeExpenditureCommonAction extends SwfUploadAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected CustomRoleService customRoleService;
	protected CustomRoleUserService customRoleUserService;

	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	} 
	
	public void setCustomRoleUserService(CustomRoleUserService customRoleUserService) {
		this.customRoleUserService = customRoleUserService;
	}
	
	/**
	 * 判断当前用户是否指定角色
	 * @param roleCode
	 */
	public boolean isCustomRole(String roleCode){
		CustomRole role = customRoleService.getCustomRoleByRoleCode(getUnitId(), roleCode);
		boolean flag;
		if(role == null){
			flag = false;
			return flag;
		}
		List<CustomRoleUser> roleUs = customRoleUserService.getCustomRoleUserListByUserId(getLoginUser().getUserId());
		if(CollectionUtils.isNotEmpty(roleUs)){
			for(CustomRoleUser ru : roleUs){
				if(StringUtils.equals(ru.getRoleId(), role.getId())){
					flag = true;
					return flag;
				}
			}
		}
		flag = false;
		return flag;
	}
	
	/**
	 * 判断是否是财务开支管理员
	 */
	public boolean isAdminAuth(){
		return this.isCustomRole(ExpenConstants.ADMIN_AUTH);
	}
}
