package net.zdsoft.office.convertflow.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.office.attendLecture.entity.OfficeAttendLecture;
import net.zdsoft.office.attendLecture.service.OfficeAttendLectureService;
import net.zdsoft.office.bulletin.entity.OfficeBulletin;
import net.zdsoft.office.bulletin.service.OfficeBulletinService;
import net.zdsoft.office.convertflow.constant.ConvertFlowConstants;
import net.zdsoft.office.convertflow.service.OfficeMobileValidateService;
import net.zdsoft.office.dailyoffice.entity.OfficeBusinessTrip;
import net.zdsoft.office.dailyoffice.entity.OfficeGoOut;
import net.zdsoft.office.dailyoffice.entity.OfficeWorkReport;
import net.zdsoft.office.dailyoffice.service.OfficeBusinessTripService;
import net.zdsoft.office.dailyoffice.service.OfficeGoOutService;
import net.zdsoft.office.dailyoffice.service.OfficeWorkReportService;
import net.zdsoft.office.expense.entity.OfficeExpense;
import net.zdsoft.office.expense.service.OfficeExpenseService;
import net.zdsoft.office.goodmanage.constant.OfficeGoodsConstants;
import net.zdsoft.office.goodmanage.entity.OfficeGoods;
import net.zdsoft.office.goodmanage.entity.OfficeGoodsReq;
import net.zdsoft.office.goodmanage.entity.OfficeGoodsTypeAuth;
import net.zdsoft.office.goodmanage.service.OfficeGoodsReqService;
import net.zdsoft.office.goodmanage.service.OfficeGoodsService;
import net.zdsoft.office.goodmanage.service.OfficeGoodsTypeAuthService;
import net.zdsoft.office.jtgoout.entity.OfficeJtGoout;
import net.zdsoft.office.jtgoout.service.OfficeJtGooutService;
import net.zdsoft.office.msgcenter.entity.OfficeBusinessJump;
import net.zdsoft.office.msgcenter.entity.OfficeMsgReceiving;
import net.zdsoft.office.msgcenter.entity.OfficeMsgSending;
import net.zdsoft.office.msgcenter.service.OfficeMsgReceivingService;
import net.zdsoft.office.msgcenter.service.OfficeMsgRecycleService;
import net.zdsoft.office.msgcenter.service.OfficeMsgSendingService;
import net.zdsoft.office.repaire.entity.OfficeRepaire;
import net.zdsoft.office.repaire.entity.OfficeTypeAuth;
import net.zdsoft.office.repaire.service.OfficeRepaireService;
import net.zdsoft.office.repaire.service.OfficeTypeAuthService;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceColckApply;
import net.zdsoft.office.teacherAttendance.service.OfficeAttendanceColckApplyService;
import net.zdsoft.office.teacherLeave.entity.OfficeTeacherLeave;
import net.zdsoft.office.teacherLeave.service.OfficeTeacherLeaveService;
import net.zdsoft.office.util.Constants;

public class OfficeMobileValidateServiceImpl implements OfficeMobileValidateService {
	
	private TaskService taskService;
	private OfficeTeacherLeaveService officeTeacherLeaveService;
	private OfficeBusinessTripService officeBusinessTripService;
	private OfficeExpenseService officeExpenseService;
	private OfficeGoOutService officeGoOutService; 
	private OfficeJtGooutService officeJtGooutService;
	private OfficeAttendanceColckApplyService officeAttendanceColckApplyService;
	private OfficeAttendLectureService officeAttendLectureService;
	private OfficeBulletinService officeBulletinService;
	private OfficeGoodsService officeGoodsService;
	private OfficeGoodsReqService officeGoodsReqService;
	private OfficeGoodsTypeAuthService officeGoodsTypeAuthService;
	private OfficeRepaireService officeRepaireService;
	private OfficeTypeAuthService officeTypeAuthService;
	private OfficeMsgSendingService officeMsgSendingService;
	private OfficeMsgReceivingService officeMsgReceivingService;
	private OfficeWorkReportService officeWorkReportService;
	private CustomRoleService customRoleService;
	private CustomRoleUserService customRoleUserService;
	private UserService userService;
	
	public JSONObject validateMessage(String unitId, String userId, String messageId){
		List<OfficeMsgReceiving> list =officeMsgReceivingService.getOfficeMsgReceivingListByMessageId(messageId);
		JSONObject json = new JSONObject();
		if(list==null || list.size()==0){
			json.put("msg", "该信息已删除，不能查看！");
			json.put("type", Constants.MOBILE_VALIDATE_TYPE_FAILURE);
			return json;
		}else{
			//判断是否有部门和单位权限
			List<OfficeMsgReceiving> myList = new ArrayList<OfficeMsgReceiving>();
			for(OfficeMsgReceiving ent : list){
				if(ent.getIsDeleted())
					continue;
				if(ent.getReceiverType() == Constants.UNIT){
					boolean isUnit = checkRole(unitId, userId, "unit_receiver");
					if(isUnit && ent.getReceiveUserId().equals(unitId))
						myList.add(ent);
				}else if(ent.getReceiverType() == Constants.DEPT){
					boolean isDept = checkRole(unitId, userId, "dept_receiver");
					User user = userService.getUser(userId);
					if(isDept && ent.getReceiveUserId().equals(user.getDeptid()))
						myList.add(ent);
				}else{//个人
					if(ent.getReceiveUserId().equals(userId))
						myList.add(ent);
				}
			}
			
			if(CollectionUtils.isEmpty(myList)){
				json.put("msg", "该信息已删除，不能查看！");
				json.put("type", Constants.MOBILE_VALIDATE_TYPE_FAILURE);
				return json;
			}
			return null;
		}
	}
	
	public JSONObject validateWorkReport(String id){
		OfficeWorkReport ent = officeWorkReportService.getOfficeWorkReportById(id);
		JSONObject json = new JSONObject();
		if(ent==null){
				json.put("msg", "该汇报已删除，不能查看！");
				json.put("type", Constants.MOBILE_VALIDATE_TYPE_FAILURE);
				return json;
		}else{
			if("8".equals(ent.getState())){
				json.put("msg", "该汇报已撤回，不能查看！");
				json.put("type", Constants.MOBILE_VALIDATE_TYPE_FAILURE);
				return json;
			}
		}
		return null;
	}

	@Override
	public JSONObject validateFlowDetail(String moduleType, String id) {
		boolean isDelete = false;//是否已删除
		boolean isCancel = false;//是否已作废
		if(String.valueOf(ConvertFlowConstants.OFFICE_TEACHER_LEAVE).equals(moduleType)){
			OfficeTeacherLeave obj = officeTeacherLeaveService.getOfficeTeacherLeaveById(id);
			if(obj==null){
				isDelete = true;
			}else{
				if(obj.getApplyStatus()==Constants.APPLY_STATE_INVALID){
					isCancel = true;
				}
			}
		}else if(String.valueOf(ConvertFlowConstants.OFFICE_GO_OUT).equals(moduleType)){
			OfficeGoOut obj = officeGoOutService.getOfficeGoOutById(id);
			if(obj==null){
				isDelete = true;
			}else{
				if(obj.getState().equals(Constants.APPLY_STATE_INVALID+"")){
					isCancel = true;
				}
			}
		}else if(String.valueOf(ConvertFlowConstants.OFFICE_NEWJTGO_OUT).equals(moduleType)){
			OfficeJtGoout obj = officeJtGooutService.getOfficeJtGooutById(id);
			if(obj==null){
				isDelete = true;
			}else{
				if(obj.getState().equals(Constants.APPLY_STATE_INVALID+"")){
					isCancel = true;
				}
			}
		}else if(String.valueOf(ConvertFlowConstants.OFFICE_EVECTION).equals(moduleType)){
			OfficeBusinessTrip obj = officeBusinessTripService.getOfficeBusinessTripById(id);
			if(obj==null){
				isDelete = true;
			}else{
				if(obj.getState().equals(Constants.APPLY_STATE_INVALID+"")){
					isCancel = true;
				}
			}
		}else if(String.valueOf(ConvertFlowConstants.OFFICE_EXPENSE).equals(moduleType)){
			OfficeExpense obj = officeExpenseService.getOfficeExpenseById(id);
			if(obj==null){
				isDelete = true;
			}else{
				if(obj.getState().equals(Constants.APPLY_STATE_INVALID+"")){
					isCancel = true;
				}
			}
		}else if(String.valueOf(ConvertFlowConstants.OFFICE_ATTENDLECTURE).equals(moduleType)){
			OfficeAttendLecture obj = officeAttendLectureService.getOfficeAttendLectureById(id);
			if(obj==null){
				isDelete = true;
			}else{
				if(obj.getState().equals(Constants.APPLY_STATE_INVALID+"")){
					isCancel = true;
				}
			}
		}else if(String.valueOf(ConvertFlowConstants.OFFICE_TEACHER_ATTENDANCE).equals(moduleType)){
			OfficeAttendanceColckApply obj=officeAttendanceColckApplyService.getOfficeAttendanceColckApplyById(id);
			if(obj==null){
				isDelete = true;
			}else{
				if(obj.getApplyStatus()==Constants.APPLY_STATE_INVALID){
					isCancel = true;
				}
			}
		}
		
		JSONObject json = new JSONObject();
		if(isDelete){
			json.put("msg", "该信息已撤销或已删除，不能查看！");
			json.put("type", Constants.MOBILE_VALIDATE_TYPE_FAILURE);
			return json;
		}else{
			if(isCancel){
				json.put("msg", "该信息已作废，不能查看！");
				json.put("type", Constants.MOBILE_VALIDATE_TYPE_FAILURE);
				return json;
			}
		}
		return null;
	}

	@Override
	public JSONObject validateFlowAudit(String moduleType, String id,
			String taskId, String userId) {
		JSONObject json = validateFlowDetail(moduleType, id);
		if(json!=null)
			return json;
		else{
			json = new JSONObject();
		}
		Task task = taskService.createTaskQuery().taskId(taskId).taskInvolvedUser(userId).singleResult();
		if(task==null){
			json.put("msg", "相关信息找不到（已处理或已撤回），不需要处理了！");
			json.put("type", Constants.MOBILE_VALIDATE_TYPE_FAILURE);
			return json;
		}
		return null;
	}
	
	//物品
	public JSONObject validateGoodsDeatil(String unitId, String userId,String id){
		OfficeGoodsReq req = officeGoodsReqService.getOfficeGoodsReqById(id);
		JSONObject json = new JSONObject();
		if(req!=null){
			
		}else{
			json.put("msg", "相关信息找不到！");
			json.put("type", Constants.MOBILE_VALIDATE_TYPE_FAILURE);
			return json;
		}
		
		return null;
	}
	
	//物品
	public JSONObject validateGoodsAudit(String unitId, String userId, String id){
		//判断是否还有权限
		OfficeGoodsReq req = officeGoodsReqService.getOfficeGoodsReqById(id);
		JSONObject json = new JSONObject();
		if(req!=null){
			if(req.getState()!=OfficeGoodsConstants.GOODS_NOT_AUDIT){
				json.put("msg", "该信息已经处理！");
				json.put("type", Constants.MOBILE_VALIDATE_TYPE_FAILURE);
				return json;
			}
			
			OfficeGoods goods = officeGoodsService.getOfficeGoodsById(req.getGoodsId());
			if(goods!=null){
				boolean idAuth = false;
				List<OfficeGoodsTypeAuth> authlist = officeGoodsTypeAuthService.getOfficeGoodsTypeAuthByUserId(unitId, userId);
				if(authlist.size() > 0){
					String[] types = authlist.get(0).getTypeId().split(",");
					for (String string : types) {
						if(string.equals(goods.getType())){
							idAuth = true;
							break;
						}
					}
				}
				if(!idAuth){
					json.put("msg", "已没有该类别物品的审核权限！");
					json.put("type", Constants.MOBILE_VALIDATE_TYPE_FAILURE);
					return json;
				}
			}else{
				json.put("msg", "该物品信息找不到！");
				json.put("type", Constants.MOBILE_VALIDATE_TYPE_FAILURE);
				return json;
			}
		}else{
			json.put("msg", "相关信息找不到！");
			json.put("type", Constants.MOBILE_VALIDATE_TYPE_FAILURE);
			return json;
		}
		
		return null;
	}
	
	//报修
	public JSONObject validateRepairAudit(String userId, String id){
		OfficeRepaire repaire = officeRepaireService.getOfficeRepaireById(id);
		JSONObject json = new JSONObject();
		if(repaire==null || repaire.getIsDeleted()){
			json.put("msg", "该信息已删除，不能查看！");
			json.put("type", Constants.MOBILE_VALIDATE_TYPE_FAILURE);
			return json;
		}else{
			if(repaire.getState().equals(OfficeRepaire.STATE_THREE)){
				json.put("msg", "该信息已经处理（已维修）！");
				json.put("type", Constants.MOBILE_VALIDATE_TYPE_FAILURE);
				return json;
			}
			
			boolean repaireCheck = false;
			//校验是否有维修权限
			List<OfficeTypeAuth> arealist = officeTypeAuthService.getOfficeTypeAuthList(Constants.REPAIRE_STATE, userId);
			for(OfficeTypeAuth e : arealist){
				if(repaire.getType().equals(e.getType())){
					repaireCheck = true;
					break;
				}
			}
			if(!repaireCheck){
				json.put("msg", "已没有该类别的审核权限！");
				json.put("type", Constants.MOBILE_VALIDATE_TYPE_FAILURE);
				return json;
			}
		}
		
		return null;
	}
	
	//报修
	public JSONObject validateRepairDetail(String userId, String id){
		OfficeRepaire repaire = officeRepaireService.getOfficeRepaireById(id);
		JSONObject json = new JSONObject();
		if(repaire==null || repaire.getIsDeleted()){
			json.put("msg", "该信息已删除，不能查看！");
			json.put("type", Constants.MOBILE_VALIDATE_TYPE_FAILURE);
			return json;
		}
		
		return null;
	}
	
	//通知公告
	public JSONObject validateBulletinDeatil(String id){
		OfficeBulletin ent = officeBulletinService.getOfficeBulletinById(id);
		JSONObject json = new JSONObject();
		if(ent==null || ent.getIsDeleted()==1){
			json.put("msg", "相关信息找不到（已删除或已取消发布）！");
			json.put("type", Constants.MOBILE_VALIDATE_TYPE_FAILURE);
			return json;
		}else{
			if(!ent.getState().equals("3")){
				json.put("msg", "相关信息找不到（已删除或已取消发布）！");
				json.put("type", Constants.MOBILE_VALIDATE_TYPE_FAILURE);
				return json;
			}
		}
		return null;
	}
	
	/**
	 * 校验是否有相关权限
	 * @param userId
	 * @param roleCode
	 * @return
	 */
	public boolean checkRole(String unitId, String userId, String roleCode){
		boolean hasRole = true;
		
		CustomRole role = customRoleService.getCustomRoleByRoleCode(unitId, roleCode);
		if(role != null){
			List<CustomRoleUser> roleUserList = customRoleUserService.getCustomRoleUserList(role.getId());
			if(CollectionUtils.isNotEmpty(roleUserList)){
				hasRole = false;
				for(CustomRoleUser item : roleUserList){
					if(StringUtils.equals(userId, item.getUserId())){
						return true;
					}
				}
			}
		}
		return hasRole;
	}

	public void setOfficeTeacherLeaveService(
			OfficeTeacherLeaveService officeTeacherLeaveService) {
		this.officeTeacherLeaveService = officeTeacherLeaveService;
	}

	public void setOfficeExpenseService(OfficeExpenseService officeExpenseService) {
		this.officeExpenseService = officeExpenseService;
	}

	public void setOfficeGoOutService(OfficeGoOutService officeGoOutService) {
		this.officeGoOutService = officeGoOutService;
	}

	public void setOfficeJtGooutService(OfficeJtGooutService officeJtGooutService) {
		this.officeJtGooutService = officeJtGooutService;
	}

	public void setOfficeAttendanceColckApplyService(
			OfficeAttendanceColckApplyService officeAttendanceColckApplyService) {
		this.officeAttendanceColckApplyService = officeAttendanceColckApplyService;
	}

	public void setOfficeAttendLectureService(
			OfficeAttendLectureService officeAttendLectureService) {
		this.officeAttendLectureService = officeAttendLectureService;
	}

	public void setOfficeBusinessTripService(
			OfficeBusinessTripService officeBusinessTripService) {
		this.officeBusinessTripService = officeBusinessTripService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}
	
	public void setOfficeBulletinService(
			OfficeBulletinService officeBulletinService) {
		this.officeBulletinService = officeBulletinService;
	}

	public void setOfficeGoodsService(OfficeGoodsService officeGoodsService) {
		this.officeGoodsService = officeGoodsService;
	}

	public void setOfficeGoodsReqService(OfficeGoodsReqService officeGoodsReqService) {
		this.officeGoodsReqService = officeGoodsReqService;
	}
	public void setOfficeRepaireService(
			OfficeRepaireService officeRepaireService) {
		this.officeRepaireService = officeRepaireService;
	}
	
	public void setOfficeGoodsTypeAuthService(
			OfficeGoodsTypeAuthService officeGoodsTypeAuthService) {
		this.officeGoodsTypeAuthService = officeGoodsTypeAuthService;
	}
	public void setOfficeTypeAuthService(
			OfficeTypeAuthService officeTypeAuthService) {
		this.officeTypeAuthService = officeTypeAuthService;
	}

	public void setOfficeMsgSendingService(
			OfficeMsgSendingService officeMsgSendingService) {
		this.officeMsgSendingService = officeMsgSendingService;
	}
	public void setOfficeMsgReceivingService(
			OfficeMsgReceivingService officeMsgReceivingService) {
		this.officeMsgReceivingService = officeMsgReceivingService;
	}
	public void setOfficeWorkReportService(
			OfficeWorkReportService officeWorkReportService) {
		this.officeWorkReportService = officeWorkReportService;
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
