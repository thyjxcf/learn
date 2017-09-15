package net.zdsoft.office.teacherAttendance.action;

import java.util.List;

import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.frame.action.SwfUploadAction;
import net.zdsoft.office.asset.constant.OfficeAssetConstants;
import net.zdsoft.office.teacherAttendance.constant.AttendanceConstants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

public class TeacherAttendanceCommonAction extends SwfUploadAction{
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
	
	public boolean isTeacherAttenceAdmin(){
		return this.isCustomRole(AttendanceConstants.TEACHER_ATTENCE_ADMIN);
	}
}
