package net.zdsoft.office.remote;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.subsystemcall.service.OfficedocSubsystemService;
import net.zdsoft.office.convertflow.constant.ConvertFlowConstants;
import net.zdsoft.office.convertflow.dto.OfficeConvertDto;
import net.zdsoft.office.convertflow.service.OfficeConvertFlowService;
import net.zdsoft.office.dailyoffice.entity.OfficeWorkReport;
import net.zdsoft.office.dailyoffice.service.OfficeWorkReportService;
import net.zdsoft.office.msgcenter.dto.MessageSearch;
import net.zdsoft.office.msgcenter.service.OfficeMsgReceivingService;
import net.zdsoft.office.repaire.service.OfficeRepaireService;
import net.zdsoft.office.util.Constants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

public class RemoteManageCountAction extends OfficeJsonBaseAction{
	
	private static final long serialVersionUID = 1L;
	
	private OfficeConvertFlowService officeConvertFlowService;
	private OfficeWorkReportService officeWorkReportService;
	private OfficeRepaireService officeRepaireService;
	private OfficedocSubsystemService officedocSubsystemService;
	private OfficeMsgReceivingService officeMsgReceivingService;
	private CustomRoleService customRoleService;
	private CustomRoleUserService customRoleUserService;
	private UserService userService;
	
	private String userId;
	private String unitId;

	public void manageCount(){
		User user = userService.getUser(userId);
		MessageSearch messageSearch = new MessageSearch();
		messageSearch.setReceiveUserId(userId);
		if(checkRole(user, "dept_receiver")){
			messageSearch.setReceiveDeptId(user.getDeptid());
		}
		if(checkRole(user, "unit_receiver")){
			messageSearch.setReceiveUnitId(user.getUnitid());
		}
		int msgURN = officeMsgReceivingService.getNumberForReceive(messageSearch, BaseConstant.MSG_TYPE_NOTE+"", Constants.UNREAD+"");
		//int msgURN = officeMsgReceivingService.getNumber(userId, BaseConstant.MSG_TYPE_NOTE+"", Constants.UNREAD+"");
		jsonMap.put("msgCount", msgURN);
		List<OfficeConvertDto> auditList = officeConvertFlowService.getAuditList(unitId, userId, String.valueOf(ConvertFlowConstants.OFFICE_AUDIT_DATATYPE_1), ConvertFlowConstants.OFFICE_ALL, null, null);
		jsonMap.put("lotusCount", auditList.size());
		List<OfficeWorkReport> officeWorkeports = officeWorkReportService.getOfficeWorkReportList(userId, null, null, null,null,null,null);
		jsonMap.put("reportCount", officeWorkeports.size());
		int repaireCount = officeRepaireService.getOfficeRepaireMangeListH5Count(userId, unitId, null, null, new String[]{"1","2"}, null, null, null);
		jsonMap.put("repairsCount", repaireCount);
		if(officedocSubsystemService!=null){
			Map<String, Integer> officedocNum = officedocSubsystemService.getOfficedocNum(userId, unitId);
			jsonMap.put("officedocCount", officedocNum.get("receiveSuperviseHandle")+officedocNum.get("receiveUnSuperviseHandle")+officedocNum.get("sendHandle")+officedocNum.get("receiveSign"));
		}else{
			jsonMap.put("officedocCount", 0);
		}
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	/**
	 * 校验是否有相关权限
	 * @param userId
	 * @param roleCode
	 * @return
	 */
	public boolean checkRole(User user, String roleCode){
		boolean hasRole = true;
		if(user == null)
			return false;
		
		CustomRole role = customRoleService.getCustomRoleByRoleCode(user.getUnitid(), roleCode);
		if(role != null){
			List<CustomRoleUser> roleUserList = customRoleUserService.getCustomRoleUserList(role.getId());
			if(CollectionUtils.isNotEmpty(roleUserList)){
				hasRole = false;
				for(CustomRoleUser item : roleUserList){
					if(StringUtils.equals(user.getId(), item.getUserId())){
						return true;
					}
				}
			}
		}
		return hasRole;
	}
	public void setOfficeMsgReceivingService(
			OfficeMsgReceivingService officeMsgReceivingService) {
		this.officeMsgReceivingService = officeMsgReceivingService;
	}

	@Override
	protected String getListObjectName() {
		return "result_array";
	}

	@Override
	protected String getDetailObjectName() {
		return "result_object";
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public void setOfficeConvertFlowService(OfficeConvertFlowService officeConvertFlowService) {
		this.officeConvertFlowService = officeConvertFlowService;
	}

	public void setOfficeWorkReportService(OfficeWorkReportService officeWorkReportService) {
		this.officeWorkReportService = officeWorkReportService;
	}

	public void setOfficeRepaireService(OfficeRepaireService officeRepaireService) {
		this.officeRepaireService = officeRepaireService;
	}

	public void setOfficedocSubsystemService(OfficedocSubsystemService officedocSubsystemService) {
		this.officedocSubsystemService = officedocSubsystemService;
	}
	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	}
	public void setCustomRoleUserService(CustomRoleUserService customRoleUserService) {
		this.customRoleUserService = customRoleUserService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
}
