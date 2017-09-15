package net.zdsoft.office.asset.action;

import java.util.List;

import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.frame.action.SwfUploadAction;
import net.zdsoft.office.asset.constant.OfficeAssetConstants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

public class OfficeAssetCommonAction extends SwfUploadAction{
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
	 * 判断是否是校长
	 */
	public boolean isOfficeSchoolMaster(){
		return this.isCustomRole(OfficeAssetConstants.OFFICE_SCHOOLMASTER);
	}
	
	/**
	 * 判断是否是会议负责人
	 */
	public boolean isOfficeMeetingLeader(){
		return this.isCustomRole(OfficeAssetConstants.OFFICE_MEETINGLEADER);
	}
	
	/**
	 * 资产管理资产类别维护权限
	 */
	public boolean isAssetCategoryAuth(){
		return this.isCustomRole(OfficeAssetConstants.ASSET_CATEGORY_AUTH);
	}
	
	/**
	 * 资产管理采购单维护权限
	 */
	public boolean isAssetPurAuth(){
		return this.isCustomRole(OfficeAssetConstants.ASSET_PUR_AUTH);
	}
	
	/**
	 * 资产管理采购审核权限
	 */
	public boolean isAssetPurAuditAuth(){
		return this.isCustomRole(OfficeAssetConstants.ASSET_PURAUDIT_AUTH);
	}
}
