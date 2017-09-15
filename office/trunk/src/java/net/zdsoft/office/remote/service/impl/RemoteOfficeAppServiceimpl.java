package net.zdsoft.office.remote.service.impl;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.constant.WeikeAppConstant;
import net.zdsoft.eis.base.subsystemcall.service.OfficedocSubsystemService;
import net.zdsoft.eis.frame.util.RemoteCallUtils;
import net.zdsoft.office.convertflow.constant.ConvertFlowConstants;
import net.zdsoft.office.convertflow.dto.OfficeConvertDto;
import net.zdsoft.office.convertflow.service.OfficeConvertFlowService;
import net.zdsoft.office.dailyoffice.entity.OfficeWorkReport;
import net.zdsoft.office.dailyoffice.service.OfficeWorkReportService;
import net.zdsoft.office.msgcenter.dto.MessageSearch;
import net.zdsoft.office.msgcenter.service.OfficeMsgReceivingService;
import net.zdsoft.office.remote.service.RemoteOfficeAppService;
import net.zdsoft.office.repaire.service.OfficeRepaireService;
import net.zdsoft.office.util.Constants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

public class RemoteOfficeAppServiceimpl implements RemoteOfficeAppService {
	private UnitService unitService;
	private UserService userService;
	private OfficeConvertFlowService officeConvertFlowService;
	private OfficeWorkReportService officeWorkReportService;
	private OfficeRepaireService officeRepaireService;
	private OfficedocSubsystemService officedocSubsystemService;
	private OfficeMsgReceivingService officeMsgReceivingService;
	private CustomRoleUserService customRoleUserService;
	private CustomRoleService customRoleService;
	
	@Override
	public String weikeOfficeCount(String remoteParam) {
		try {
			String userId = RemoteCallUtils.getParamValue(remoteParam, "userId");
			
			if(StringUtils.isBlank(userId)){
				return RemoteCallUtils.returnResultError("userId不能为空");
			}
			
			User user = userService.getUser(userId);
			if(user == null){
				return RemoteCallUtils.returnResultError("用户信息不存在或非教师登录！");
			}
			String unitId = user.getUnitid();
			Unit unit = unitService.getUnit(unitId);
			
			String repairCode = String.valueOf(WeikeAppConstant.getAppModuleId(WeikeAppConstant.REPAIR, unit.getUnitclass()));
			String bulletinCode = String.valueOf(WeikeAppConstant.getAppModuleId(WeikeAppConstant.BULLETIN, unit.getUnitclass()));
			String workreportCode = String.valueOf(WeikeAppConstant.getAppModuleId(WeikeAppConstant.WORK_REPORT, unit.getUnitclass()));
			
			JSONArray array = new JSONArray();
			Integer sendCount = 0;
			Integer receiveCount = 0;
			List<OfficeConvertDto> auditList = officeConvertFlowService.getAuditList(unitId, userId, String.valueOf(ConvertFlowConstants.OFFICE_AUDIT_DATATYPE_1), ConvertFlowConstants.OFFICE_ALL, null, null);
			List<OfficeWorkReport> officeWorkeports = officeWorkReportService.getOfficeWorkReportList(userId, null, null, null,null,null,null);
			int repaireCount = officeRepaireService.getOfficeRepaireMangeListH5Count(userId, unitId, null, null, new String[]{"1","2"}, null, null, null);
			MessageSearch messageSearch = new MessageSearch();
			messageSearch.setReceiveUserId(userId);
			if(checkRole(user, "dept_receiver")){
				messageSearch.setReceiveDeptId(user.getDeptid());
			}
			if(checkRole(user, "unit_receiver")){
				messageSearch.setReceiveUnitId(user.getUnitid());
			}
			int msgCount = officeMsgReceivingService.getNumberForReceive(messageSearch, BaseConstant.MSG_TYPE_NOTE+"", Constants.UNREAD+"");
			//int msgCount = officeMsgReceivingService.getNumber(userId, BaseConstant.MSG_TYPE_NOTE+"", Constants.UNREAD+"");
			
			if(officedocSubsystemService!=null){
				Map<String, Integer> officedocNum = officedocSubsystemService.getOfficedocNum(userId, unitId);
				sendCount = officedocNum.get("sendHandle");
				receiveCount = officedocNum.get("receiveSuperviseHandle")+officedocNum.get("receiveUnSuperviseHandle")+officedocNum.get("receiveSign")+officedocNum.get("receiveReadSign");
			}else{
				sendCount = 0;
				receiveCount  = 0;
			}
			
				JSONObject js1 = new JSONObject();
				js1.put("code", WeikeAppConstant.AUDIT_DATA);
				js1.put("number", auditList.size());
				array.add(js1);
				
				JSONObject msgJson = new JSONObject();
				msgJson.put("code", WeikeAppConstant.MESSAGE);
				msgJson.put("number", msgCount);
				array.add(msgJson);
				
				JSONObject js2 = new JSONObject();
				js2.put("code", WeikeAppConstant.OFFICEDOC_SEND);
				js2.put("number", sendCount);
				array.add(js2);
				
				JSONObject js3 = new JSONObject();
				js3.put("code", WeikeAppConstant.OFFICEDOC_RECEIVE);
				js3.put("number", receiveCount);
				array.add(js3);
				
				JSONObject js4 = new JSONObject();
				js4.put("code", workreportCode);//工作汇报
				js4.put("number", officeWorkeports.size());
				array.add(js4);
				
				JSONObject js5 = new JSONObject();
				js5.put("code", repairCode);//报修
				js5.put("number", repaireCount);
				array.add(js5);
				
				JSONObject js6 = new JSONObject();
				js6.put("code", bulletinCode);//通知公告
				js6.put("number", 0);
				array.add(js6);
				
				return RemoteCallUtils.returnResultJsons(array);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
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
	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setOfficeConvertFlowService(
			OfficeConvertFlowService officeConvertFlowService) {
		this.officeConvertFlowService = officeConvertFlowService;
	}

	public void setOfficeWorkReportService(
			OfficeWorkReportService officeWorkReportService) {
		this.officeWorkReportService = officeWorkReportService;
	}

	public void setOfficeRepaireService(OfficeRepaireService officeRepaireService) {
		this.officeRepaireService = officeRepaireService;
	}

	public void setOfficedocSubsystemService(
			OfficedocSubsystemService officedocSubsystemService) {
		this.officedocSubsystemService = officedocSubsystemService;
	}
	public void setOfficeMsgReceivingService(
			OfficeMsgReceivingService officeMsgReceivingService) {
		this.officeMsgReceivingService = officeMsgReceivingService;
	}
	public void setCustomRoleUserService(CustomRoleUserService customRoleUserService) {
		this.customRoleUserService = customRoleUserService;
	}
	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	}
	
}
