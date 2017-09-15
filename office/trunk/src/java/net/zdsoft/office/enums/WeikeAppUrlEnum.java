package net.zdsoft.office.enums;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.constant.WeikeAppConstant;
import net.zdsoft.keelcnet.config.ContainerManager;
import net.zdsoft.office.convertflow.constant.ConvertFlowConstants;
import net.zdsoft.office.convertflow.service.OfficeMobileValidateService;
import net.zdsoft.office.remote.RemoteBusinessTripAction;

/**
 * 微课工作模块 推送消息 跳转地址 详情页地址enum
 * @author Administrator
 */
public enum WeikeAppUrlEnum {
	
	/**邮件消息*/
	MESSAGE_0(WeikeAppConstant.MESSAGE, WeikeAppConstant.DETAILE_URL, "/office/mobileh5/message/messageReceivedDetail.html"){
		public JSONObject validate(String unitId, String userId, HttpServletRequest request) {
			String id = request.getParameter("id");
			if(StringUtils.isNotBlank(unitId) && StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(id))
				return getOfficeMobileValidateService().validateMessage(unitId, userId, id);
			return null;
		}
	},
	
	/**工作汇报*/
	WORK_REPORT_0(WeikeAppConstant.WORK_REPORT, WeikeAppConstant.DETAILE_URL, "/office/mobileh5/workreport/workreportDetail.html"){
		public JSONObject validate(String unitId, String userId, HttpServletRequest request) {
			String id = request.getParameter("id");
			if(StringUtils.isNotBlank(id))
				return getOfficeMobileValidateService().validateWorkReport(id);
			return null;
		}
	},
	
	
	/**物品领用*/
	GOODS_0(String.valueOf(ConvertFlowConstants.OFFICE_GOODS), WeikeAppConstant.DETAILE_URL, "/office/mobileh5/workflow/goodsManagerDetail.html"){
		public JSONObject validate(String unitId, String userId, HttpServletRequest request) {
			String id = request.getParameter("id");
			if(StringUtils.isNotBlank(unitId) && StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(id))
				return getOfficeMobileValidateService().validateGoodsDeatil(unitId, userId, id);
			return null;
		}
	},
	
	GOODS_1(String.valueOf(ConvertFlowConstants.OFFICE_GOODS), WeikeAppConstant.AUDIT_URL, "/office/mobileh5/workflow/goodsManagerDetail.html"){
		public JSONObject validate(String unitId, String userId, HttpServletRequest request) {
			String id = request.getParameter("id");
			if(StringUtils.isNotBlank(unitId) && StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(id))
				return getOfficeMobileValidateService().validateGoodsAudit(unitId, userId, id);
			return null;
		}
	},
	
	/**通知公告*/
	BULLETIN_0(WeikeAppConstant.BULLETIN, WeikeAppConstant.DETAILE_URL, "/office/mobileh5/work/bulletin/bulletinDetail.html"){
		public JSONObject validate(String unitId, String userId, HttpServletRequest request) {
			String id = request.getParameter("id");
			if(StringUtils.isNotBlank(id))
				return getOfficeMobileValidateService().validateBulletinDeatil(id);
			return null;
		}
	},
	
	/**报修*/
	REPAIR_0(WeikeAppConstant.REPAIR, WeikeAppConstant.DETAILE_URL, "/office/mobileh5/repair/repairDetail.html"){
		public JSONObject validate(String unitId, String userId, HttpServletRequest request) {
			String id = request.getParameter("id");
			if(StringUtils.isNotBlank(id))
				return getOfficeMobileValidateService().validateRepairDetail(userId, id);
			return null;
		}
	},
	REPAIR_1(WeikeAppConstant.REPAIR, WeikeAppConstant.AUDIT_URL, "/office/mobileh5/repair/repairAudit.html"){
		public JSONObject validate(String unitId, String userId, HttpServletRequest request) {
			String id = request.getParameter("id");
			if(StringUtils.isNotBlank(id))
				return getOfficeMobileValidateService().validateRepairAudit(userId, id);
			return null;
		}
	},
	
	/**请假*/
	TEACHER_LEAVE_0(String.valueOf(ConvertFlowConstants.OFFICE_TEACHER_LEAVE), WeikeAppConstant.DETAILE_URL, "/office/mobileh5/workflow/leaveDetail.html"){
		public JSONObject validate(String unitId, String userId, HttpServletRequest request) {
			String id = request.getParameter("id");
			if(StringUtils.isNotBlank(id))
				return getOfficeMobileValidateService().validateFlowDetail(this.getModuleType(), id);
			return null;
		}
	},
	TEACHER_LEAVE_1(String.valueOf(ConvertFlowConstants.OFFICE_TEACHER_LEAVE), WeikeAppConstant.AUDIT_URL, "/office/mobileh5/workflow/leaveAuditDetail.html"){
		public JSONObject validate(String unitId, String userId, HttpServletRequest request) {
			String id = request.getParameter("id");
			String taskId = request.getParameter("taskId");
			if(StringUtils.isNotBlank(id) && StringUtils.isNotBlank(taskId) && StringUtils.isNotBlank(userId))
				return getOfficeMobileValidateService().validateFlowAudit(this.getModuleType(), id, taskId, userId);
			return null;
		}
	},
	
	/**外出*/
	GO_OUT_0(String.valueOf(ConvertFlowConstants.OFFICE_GO_OUT), WeikeAppConstant.DETAILE_URL, "/office/mobileh5/workflow/goOutDetail.html"){
		public JSONObject validate(String unitId, String userId, HttpServletRequest request) {
			String id = request.getParameter("id");
			if(StringUtils.isNotBlank(id))
				return getOfficeMobileValidateService().validateFlowDetail(this.getModuleType(), id);
			return null;
		}
	},
	GO_OUT_1(String.valueOf(ConvertFlowConstants.OFFICE_GO_OUT), WeikeAppConstant.AUDIT_URL, "/office/mobileh5/workflow/goOutAuditDetail.html"){
		public JSONObject validate(String unitId, String userId, HttpServletRequest request) {
			String id = request.getParameter("id");
			String taskId = request.getParameter("taskId");
			if(StringUtils.isNotBlank(id) && StringUtils.isNotBlank(taskId) && StringUtils.isNotBlank(userId))
				return getOfficeMobileValidateService().validateFlowAudit(this.getModuleType(), id, taskId, userId);
			return null;
		}
	},
	
	/**出差*/
	EVECTION_0(String.valueOf(ConvertFlowConstants.OFFICE_EVECTION), WeikeAppConstant.DETAILE_URL, "/office/mobileh5/workflow/evectionDetail.html"){
		public JSONObject validate(String unitId, String userId, HttpServletRequest request) {
			String id = request.getParameter("id");
			if(StringUtils.isNotBlank(id))
				return getOfficeMobileValidateService().validateFlowDetail(this.getModuleType(), id);
			return null;
		}
	},
	EVECTION_1(String.valueOf(ConvertFlowConstants.OFFICE_EVECTION), WeikeAppConstant.AUDIT_URL, "/office/mobileh5/workflow/evectionAuditDetail.html"){
		public JSONObject validate(String unitId, String userId, HttpServletRequest request) {
			String id = request.getParameter("id");
			String taskId = request.getParameter("taskId");
			if(StringUtils.isNotBlank(id) && StringUtils.isNotBlank(taskId) && StringUtils.isNotBlank(userId))
				return getOfficeMobileValidateService().validateFlowAudit(this.getModuleType(), id, taskId, userId);
			return null;
		}
	},
	
	/**报销*/
	EXPENSE_0(String.valueOf(ConvertFlowConstants.OFFICE_EXPENSE), WeikeAppConstant.DETAILE_URL, "/office/mobileh5/workflow/expenseDetail.html"){
		public JSONObject validate(String unitId, String userId, HttpServletRequest request) {
			String id = request.getParameter("id");
			if(StringUtils.isNotBlank(id))
				return getOfficeMobileValidateService().validateFlowDetail(this.getModuleType(), id);
			return null;
		}
	},
	EXPENSE_1(String.valueOf(ConvertFlowConstants.OFFICE_EXPENSE), WeikeAppConstant.AUDIT_URL, "/office/mobileh5/workflow/expenseAuditDetail.html"){
		public JSONObject validate(String unitId, String userId, HttpServletRequest request) {
			String id = request.getParameter("id");
			String taskId = request.getParameter("taskId");
			if(StringUtils.isNotBlank(id) && StringUtils.isNotBlank(taskId) && StringUtils.isNotBlank(userId))
				return getOfficeMobileValidateService().validateFlowAudit(this.getModuleType(), id, taskId, userId);
			return null;
		}
	},
	
	/**听课登记*/
	ATTEND_LECTURE_0(String.valueOf(ConvertFlowConstants.OFFICE_ATTENDLECTURE), WeikeAppConstant.DETAILE_URL, "/office/mobileh5/workflow/attendLectureDetail.html"){
		public JSONObject validate(String unitId, String userId, HttpServletRequest request) {
			String id = request.getParameter("id");
			if(StringUtils.isNotBlank(id))
				return getOfficeMobileValidateService().validateFlowDetail(this.getModuleType(), id);
			return null;
		}
	},
	ATTEND_LECTURE_1(String.valueOf(ConvertFlowConstants.OFFICE_ATTENDLECTURE), WeikeAppConstant.AUDIT_URL, "/office/mobileh5/workflow/attendLectureAuditDetail.html"){
		public JSONObject validate(String unitId, String userId, HttpServletRequest request) {
			String id = request.getParameter("id");
			String taskId = request.getParameter("taskId");
			if(StringUtils.isNotBlank(id) && StringUtils.isNotBlank(taskId) && StringUtils.isNotBlank(userId))
				return getOfficeMobileValidateService().validateFlowAudit(this.getModuleType(), id, taskId, userId);
			return null;
		}
	},
	
	/**集体外出*/
	JT_GO_OUT_0(String.valueOf(ConvertFlowConstants.OFFICE_NEWJTGO_OUT), WeikeAppConstant.DETAILE_URL, "/office/mobileh5/workflow/goOutjtDetail.html"){
		public JSONObject validate(String unitId, String userId, HttpServletRequest request) {
			String id = request.getParameter("id");
			if(StringUtils.isNotBlank(id))
				return getOfficeMobileValidateService().validateFlowDetail(this.getModuleType(), id);
			return null;
		}
	},
	JT_GO_OUT_1(String.valueOf(ConvertFlowConstants.OFFICE_NEWJTGO_OUT), WeikeAppConstant.AUDIT_URL, "/office/mobileh5/workflow/goOutJtAuditDetail.html"){
		public JSONObject validate(String unitId, String userId, HttpServletRequest request) {
			String id = request.getParameter("id");
			String taskId = request.getParameter("taskId");
			if(StringUtils.isNotBlank(id) && StringUtils.isNotBlank(taskId) && StringUtils.isNotBlank(userId))
				return getOfficeMobileValidateService().validateFlowAudit(this.getModuleType(), id, taskId, userId);
			return null;
		}
	},
	
	/**教师考勤打卡*/
	TEACHER_ATTENDANCE_0(String.valueOf(ConvertFlowConstants.OFFICE_TEACHER_ATTENDANCE), WeikeAppConstant.DETAILE_URL, "/office/mobileh5/workflow/tchAttendanceDetail.html"){
		public JSONObject validate(String unitId, String userId, HttpServletRequest request) {
			String id = request.getParameter("id");
			if(StringUtils.isNotBlank(id))
				return getOfficeMobileValidateService().validateFlowDetail(this.getModuleType(), id);
			return null;
		}
	},
	TEACHER_ATTENDANCE_1(String.valueOf(ConvertFlowConstants.OFFICE_TEACHER_ATTENDANCE), WeikeAppConstant.AUDIT_URL, "/office/mobileh5/workflow/tchAttendanceAuditDetail.html"){
		public JSONObject validate(String unitId, String userId, HttpServletRequest request) {
			String id = request.getParameter("id");
			String taskId = request.getParameter("taskId");
			if(StringUtils.isNotBlank(id) && StringUtils.isNotBlank(taskId) && StringUtils.isNotBlank(userId))
				return getOfficeMobileValidateService().validateFlowAudit(this.getModuleType(), id, taskId, userId);
			return null;
		}
	},
	;
	
	
	private String moduleType;
	private int state;
	private String url;
	
	WeikeAppUrlEnum(String moduleType, int state, String url){
		this.moduleType = moduleType;
		this.state = state;
		this.url = url;
	}
	
	/**
	 * 预校验(比如信息已撤回、已处理等 就不再需要跳转处理 而应给予提示)
	 * @param unitId TODO
	 * @param userId TODO
	 * @return
	 */
	public abstract JSONObject validate(String unitId, String userId, HttpServletRequest request);
	
	/**
	 * 预校验分类处理
	 * @param unitId TODO
	 * @param userId TODO
	 * @return
	 */
	public static JSONObject validateH5(String unitId, String userId, HttpServletRequest request){
		String moduleType = request.getParameter("moduleType");
		String state = request.getParameter("state");
		if(StringUtils.isNotBlank(moduleType) && StringUtils.isNotBlank(state)){
			WeikeAppUrlEnum entity = WeikeAppUrlEnum.getEntity(moduleType, Integer.valueOf(state));
			if(entity!=null){
				return entity.validate(unitId, userId, request);
			}
		}
		return null;
	}
	
	/**
	 * 返回对应的WeikeAppUrlEnum
	 * @param moduleType
	 * @param state
	 * @return
	 */
	public static WeikeAppUrlEnum getEntity(String moduleType, int state){
		for(WeikeAppUrlEnum entity : WeikeAppUrlEnum.values()){
			if(entity.getModuleType().equals(moduleType)
					&& entity.getState()==state){
				return entity;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param moduleType
	 * @param state
	 * @return
	 */
	public static String getWeikeUrl(String moduleType, int state){
		for(WeikeAppUrlEnum item : values()){
			if(item.getModuleType().equals(moduleType) && item.getState()== state){
				return item.getUrl();
			}
		}
		return "";
	}
	
	/**
	 * 流程相关的url(待我审批、我发起的)
	 * @param moduleType
	 * @param state
	 * @param id
	 * @param taskId
	 * @return
	 */
	public static String getWeikeFlowUrl(String moduleType, int state, String id, String taskId){
		for(WeikeAppUrlEnum item : values()){
			if(item.getModuleType().equals(moduleType) && item.getState()== state){
				StringBuilder sb = new StringBuilder(item.getUrl());
				sb.append("&businessType="+item.getModuleType());
				if(StringUtils.isNotBlank(id)){
					sb.append("&id="+id);
				}
				if(StringUtils.isNotBlank(taskId)){
					sb.append("&taskId="+taskId);
				}
				if(WeikeAppConstant.DETAILE_URL == state){
					sb.append("&dataType=2");
				}else if(WeikeAppConstant.AUDIT_URL == state){
					sb.append("&dataType=1");
				}
				return sb.toString();
			}
		}
		return "";
	}
	
	public String getUrl() {
		if(StringUtils.isNotBlank(url)){
			return url + "?customReturn=1&hideRightButton=1&moduleType="+this.getModuleType()+"&state="+this.getState();
		}
		return url;
	}
	
	public static OfficeMobileValidateService getOfficeMobileValidateService(){
		return (OfficeMobileValidateService)ContainerManager.getComponent("officeMobileValidateService");
	}
	
	public String getModuleType() {
		return moduleType;
	}
	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
