package net.zdsoft.office.remote;

import java.util.Date;

import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.component.flowManage.constant.FlowConstant;
import net.zdsoft.jbpm.activiti.engine.task.AgileIdentityLinkType;
import net.zdsoft.jbpm.core.entity.Comment;
import net.zdsoft.jbpm.core.entity.TaskHandlerSave;
import net.zdsoft.jbpm.core.entity.TaskHandlerShow;
import net.zdsoft.jbpm.core.service.TaskHandlerService;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceColckApply;
import net.zdsoft.office.teacherAttendance.service.OfficeAttendanceColckApplyService;

public class RemoteAttendanceAction extends OfficeJsonBaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private OfficeAttendanceColckApply officeAttendanceColckApply=new OfficeAttendanceColckApply();
	
	private OfficeAttendanceColckApplyService officeAttendanceColckApplyService;
	private TaskHandlerService taskHandlerService;
	private UserService userService;
	private DeptService deptService;
	
	private String userName;
	private String currentStepId;
	private String taskHandlerSaveJson;
	private String id;
	private String unitId;
	private String taskId;
	private String userId;
	private String flowId;
	private boolean isPass;
	
	private String businessType;//客户端查询h5详情页面使用
	private String dateType;//客户端查询h5详情页面使用--99时则返回到客户端
	
	public void auditAttendanceClock(){
		officeAttendanceColckApply=officeAttendanceColckApplyService.getOfficeAttendanceColckApplyById(id);
		if(officeAttendanceColckApply==null) officeAttendanceColckApply=new OfficeAttendanceColckApply();
		
		TaskHandlerSave taskHandlerSave = new TaskHandlerSave();
		TaskHandlerShow taskHandlerShow = taskHandlerService.getTaskHandlerShow(taskId, userId);
		taskHandlerSave.setCurrentTask(taskHandlerShow.getCurrentTask());
		taskHandlerSave.setCurrentUserId(userId);
		taskHandlerSave.setCurrentUnitId(unitId);
		taskHandlerSave.setSubsystemId(FlowConstant.OFFICE_SUBSYSTEM);
		User user = userService.getUser(userId);
		
		Comment comment = new Comment();
		comment.setAssigneeType(AgileIdentityLinkType.PRINCIPAL);
		comment.setAssigneeId(user.getId());
		comment.setAssigneeName(user.getRealname());
		userName = user.getRealname();
		taskHandlerSave.setComment(comment);
		JSONObject jsonStr = new JSONObject().fromObject(taskHandlerSave);
		taskHandlerSaveJson = jsonStr.toString();
		officeAttendanceColckApply.setTaskName(taskHandlerSave.getCurrentTask().getTaskName());
		currentStepId = taskHandlerSave.getCurrentTask().getTaskDefinitionKey();
		flowId = officeAttendanceColckApply.getFlowId();
		
		String photoUrl = userSetService.getUserPhotoUrl(officeAttendanceColckApply.getApplyUserId());
		String deptName = "";
		if(user != null){
			Dept dept = deptService.getDept(user.getDeptid());
			if(dept!=null){
				deptName = dept.getDeptname();
			}
		}
		
		JSONObject json = new JSONObject();
		json.put("id", officeAttendanceColckApply.getId());
		json.put("deptName", deptName);
		json.put("applyUserName", officeAttendanceColckApply.getUserName());
		json.put("photoUrl", photoUrl);
		json.put("attenceDateType", DateUtils.date2String(officeAttendanceColckApply.getAttenceDate(),"yyyy-MM-dd")+
																" "+officeAttendanceColckApply.getTypeWeekTime());
		json.put("reason", officeAttendanceColckApply.getReason());
		json.put("taskHandlerSaveJson", taskHandlerSaveJson);
		json.put("taskName", taskHandlerSave.getCurrentTask().getTaskName());
		json.put("userName", userName);
		json.put("currentStepId", currentStepId);
		json.put("flowId", flowId);
		json.put("hisTaskCommentArray",RemoteOfficeUtils.createHisTaskCommentArray(officeAttendanceColckApply.getHisTaskList(), officeAttendanceColckApply.getUnitId(), officeAttendanceColckApply.getFlowId()));
		
		jsonMap.put(getDetailObjectName(), json);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
		
	}
	
	public void  saveAudit(){
		JSONObject json = new JSONObject();
		try {
			JSONObject object = new JSONObject().fromObject(taskHandlerSaveJson);
			TaskHandlerSave taskHandlerSave =(TaskHandlerSave) JSONObject.toBean(object,
				 TaskHandlerSave.class);
			taskHandlerSave.getComment().setOperateTime(new Date());
			officeAttendanceColckApplyService.passFlow(isPass(),taskHandlerSave,id);
			json.put("result",1);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("result",0);
			if(e.getCause()!=null){
				json.put("msg", "审核失败:"+e.getCause().getMessage());
			}else{
				json.put("msg", "审核失败:"+e.getMessage());
			}
		}
		responseJSON(json);
	}
	public void applyDetail(){
		officeAttendanceColckApply=officeAttendanceColckApplyService.getOfficeAttendanceColckApplyById(id);
		if(officeAttendanceColckApply==null) officeAttendanceColckApply=new OfficeAttendanceColckApply();
		
		String photoUrl = userSetService.getUserPhotoUrl(officeAttendanceColckApply.getApplyUserId());
		User user = userService.getUser(officeAttendanceColckApply.getApplyUserId());
		String deptName = "";
		if(user != null){
			Dept dept = deptService.getDept(user.getDeptid());
			if(dept!=null){
				deptName = dept.getDeptname();
			}
		}
		
		JSONObject json = new JSONObject();
		json.put("applyUserName", officeAttendanceColckApply.getUserName());
		json.put("deptName", deptName);
		json.put("photoUrl", photoUrl);
		json.put("attenceDateType", DateUtils.date2String(officeAttendanceColckApply.getAttenceDate(),"yyyy-MM-dd")+
				" "+officeAttendanceColckApply.getTypeWeekTime());
		json.put("reason", officeAttendanceColckApply.getReason());
		json.put("hisTaskCommentArray",RemoteOfficeUtils.createHisTaskCommentArray(officeAttendanceColckApply.getHisTaskList(), officeAttendanceColckApply.getUnitId(), officeAttendanceColckApply.getFlowId()));
		json.put("applyStatus", officeAttendanceColckApply.getApplyStatus());
		jsonMap.put(getDetailObjectName(), json);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	
	public String appApplyDetail(){
		return SUCCESS;
	}
	
	public String getVersion(){
		return BaseConstant.OA_APP_VERSION;
	}

	@Override
	protected String getListObjectName() {
		return "result_array";
	}

	@Override
	protected String getDetailObjectName() {
		return "result_object";
	}
	public OfficeAttendanceColckApply getOfficeAttendanceColckApply() {
		return officeAttendanceColckApply;
	}
	public void setOfficeAttendanceColckApply(
			OfficeAttendanceColckApply officeAttendanceColckApply) {
		this.officeAttendanceColckApply = officeAttendanceColckApply;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCurrentStepId() {
		return currentStepId;
	}
	public void setCurrentStepId(String currentStepId) {
		this.currentStepId = currentStepId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFlowId() {
		return flowId;
	}
	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
	public void setOfficeAttendanceColckApplyService(
			OfficeAttendanceColckApplyService officeAttendanceColckApplyService) {
		this.officeAttendanceColckApplyService = officeAttendanceColckApplyService;
	}
	public void setTaskHandlerService(TaskHandlerService taskHandlerService) {
		this.taskHandlerService = taskHandlerService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	public void setTaskHandlerSaveJson(String taskHandlerSaveJson) {
		this.taskHandlerSaveJson = taskHandlerSaveJson;
	}

	public boolean isPass() {
		return isPass;
	}

	public void setPass(boolean isPass) {
		this.isPass = isPass;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}
	
}
