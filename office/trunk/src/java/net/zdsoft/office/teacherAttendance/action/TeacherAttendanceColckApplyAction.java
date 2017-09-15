package net.zdsoft.office.teacherAttendance.action;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.component.flowManage.constant.FlowConstant;
import net.zdsoft.eis.component.flowManage.entity.Flow;
import net.zdsoft.eis.component.flowManage.service.FlowManageService;
import net.zdsoft.eis.frame.action.PageAction;
import net.zdsoft.jbpm.activiti.engine.task.AgileIdentityLinkType;
import net.zdsoft.jbpm.core.entity.Comment;
import net.zdsoft.jbpm.core.entity.TaskHandlerSave;
import net.zdsoft.jbpm.core.entity.TaskHandlerShow;
import net.zdsoft.jbpm.core.service.TaskHandlerService;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.office.teacherAttendance.constant.AttendanceConstants;
import net.zdsoft.office.teacherAttendance.constant.WeekdayEnum;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceColckApply;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceGroupUser;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceSet;
import net.zdsoft.office.teacherAttendance.service.OfficeAttendanceColckApplyService;
import net.zdsoft.office.teacherAttendance.service.OfficeAttendanceGroupUserService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

public class TeacherAttendanceColckApplyAction  extends PageAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private OfficeAttendanceColckApplyService officeAttendanceColckApplyService;
	private UserService userService;
	private TaskHandlerService taskHandlerService;
	private FlowManageService flowManageService;
	private OfficeAttendanceGroupUserService officeAttendanceGroupUserService;
	
	private OfficeAttendanceColckApply officeAttendanceColckApply=new OfficeAttendanceColckApply();
	private List<OfficeAttendanceColckApply> attendanceColckApplyList;
	
	private int auditStatus;
	private int applyStatus;
	private String taskId;
	private String id;
	private String userId;
	private boolean isPass;
	private String attenceDate;
	private String type;
	private String typeWeekTime;//补卡班次
	private String auditComment;
	
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
	public String applyList(){
		attendanceColckApplyList = officeAttendanceColckApplyService.getApplyList(getLoginUser().getUserId(),getUnitId(),applyStatus,getPage());
		return SUCCESS;
	}
	public String clockApply(){
		Calendar c=Calendar.getInstance();
		c.setTime(DateUtils.string2Date(attenceDate));
		String weekDay=WeekdayEnum.getName(c.get(Calendar.DAY_OF_WEEK));
		OfficeAttendanceSet set=officeAttendanceColckApplyService.getSet(userId);
		
		if(type.equals(AttendanceConstants.ATTENDANCE_TYPE_AM)){
			typeWeekTime=weekDay+" "+set.getStartTime();
		}else{
			typeWeekTime=weekDay+" "+set.getEndTime();
		}
		return SUCCESS;
	}
	public String submitAttendanceColck(){
		try {
			
			OfficeAttendanceGroupUser officeAttendanceGroupUser=officeAttendanceGroupUserService.getItemByUserId(getLoginUser().getUserId());
			if(officeAttendanceGroupUser==null){
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("没有所属的考勤组，不能提交补卡申请");
				return SUCCESS;
			}
			
			officeAttendanceColckApply.setUnitId(getUnitId());
			officeAttendanceColckApply.setApplyUserId(getLoginUser().getUserId());
			officeAttendanceColckApply.setAttenceDate(DateUtils.string2Date(attenceDate));
			officeAttendanceColckApply.setType(type);
//			officeAttendanceColckApply.setTypeWeekTime(typeWeekTime);
			officeAttendanceColckApply.setCreationTime(new Date());
			officeAttendanceColckApply.setIsdeleted(false);
			List<Flow> flowList = flowManageService.getFinishFlowList(getUnitId(), FlowConstant.FLOW_OWNER_UNIT, 
					FlowConstant.OFFICE_ATTENDANCE,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
			List<Flow> flowList2  = flowManageService.getFinishFlowList(getLoginInfo().getUser().getDeptid(), FlowConstant.FLOW_OWNER_STRING, 
					FlowConstant.OFFICE_ATTENDANCE,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
			if(CollectionUtils.isNotEmpty(flowList2)){
				flowList.addAll(flowList2);
			}
			
			if(CollectionUtils.isNotEmpty(flowList)){
				for(Flow item : flowList){
					if(item.isDefaultFlow()){
						officeAttendanceColckApply.setFlowId(item.getFlowId());
					}
				}
				if(StringUtils.isBlank(officeAttendanceColckApply.getFlowId())){
					officeAttendanceColckApply.setFlowId(flowList.get(0).getFlowId());
				}
			}else{
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("没有流程，不能提交补卡申请");
				return SUCCESS;
			}
			officeAttendanceColckApplyService.startFlow(officeAttendanceColckApply,getLoginUser().getUserId());
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("提交成功,已进入流程中");
		}catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			if(e.getCause()!=null){
				if(e.getCause().getMessage()!=null){
					promptMessageDto.setErrorMessage("提交失败:"+e.getCause().getMessage());
				}else{
					promptMessageDto.setErrorMessage("未设置流程审核人员");
				}
			}else{
				promptMessageDto.setErrorMessage("提交失败:"+e.getMessage());
			}
		}
		return SUCCESS;
	}
	public String getAttendanceAuditList(){
		if(auditStatus==0){
			attendanceColckApplyList=officeAttendanceColckApplyService.toDoAudit(getLoginUser().getUserId(),getUnitId(),getPage());
		}else{
			attendanceColckApplyList=officeAttendanceColckApplyService.HaveDoAudit(getLoginUser().getUserId(),getUnitId(),getPage());
		}
		return SUCCESS;
	}
	public String clockAuditEdit(){
		return SUCCESS;
	}
	public String auditIsPassApply(){
		TaskHandlerSave taskHandlerSave = new TaskHandlerSave();
		TaskHandlerShow taskHandlerShow = taskHandlerService.getTaskHandlerShow(taskId, getLoginUser().getUserId());
		taskHandlerSave.setCurrentTask(taskHandlerShow.getCurrentTask());
		taskHandlerSave.setCurrentUserId(getLoginUser().getUserId());
		taskHandlerSave.setCurrentUnitId(getLoginUser().getUnitId());
		taskHandlerSave.setSubsystemId(FlowConstant.OFFICE_SUBSYSTEM);
		User user = userService.getUser(getLoginUser().getUserId());
		
		if(auditComment==null) auditComment="";
		Comment comment = new Comment();
		comment.setAssigneeType(AgileIdentityLinkType.PRINCIPAL);
		comment.setAssigneeId(user.getId());
		comment.setAssigneeName(user.getRealname());
		comment.setTextComment(auditComment);
		taskHandlerSave.setComment(comment);
		
		try {
			taskHandlerSave.getComment().setOperateTime(new Date());
			officeAttendanceColckApplyService.passFlow(isPass(),taskHandlerSave,id);
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("审核成功");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			if(e.getCause()!=null){
				promptMessageDto.setErrorMessage("审核失败:"+e.getCause().getMessage());
			}else{
				promptMessageDto.setErrorMessage("审核失败:"+e.getMessage());
			}
		}
		return SUCCESS;
	}
	
	public OfficeAttendanceColckApply getOfficeAttendanceColckApply() {
		return officeAttendanceColckApply;
	}

	public void setOfficeAttendanceColckApply(
			OfficeAttendanceColckApply officeAttendanceColckApply) {
		this.officeAttendanceColckApply = officeAttendanceColckApply;
	}

	public List<OfficeAttendanceColckApply> getAttendanceColckApplyList() {
		return attendanceColckApplyList;
	}

	public void setAttendanceColckApplyList(
			List<OfficeAttendanceColckApply> attendanceColckApplyList) {
		this.attendanceColckApplyList = attendanceColckApplyList;
	}

	public int getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}

	public void setOfficeAttendanceColckApplyService(
			OfficeAttendanceColckApplyService officeAttendanceColckApplyService) {
		this.officeAttendanceColckApplyService = officeAttendanceColckApplyService;
	}
	public boolean isPass() {
		return isPass;
	}

	public void setPass(boolean isPass) {
		this.isPass = isPass;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setTaskHandlerService(TaskHandlerService taskHandlerService) {
		this.taskHandlerService = taskHandlerService;
	}
	public int getApplyStatus() {
		return applyStatus;
	}
	public void setApplyStatus(int applyStatus) {
		this.applyStatus = applyStatus;
	}
	public String getAttenceDate() {
		return attenceDate;
	}
	public void setAttenceDate(String attenceDate) {
		this.attenceDate = attenceDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setFlowManageService(FlowManageService flowManageService) {
		this.flowManageService = flowManageService;
	}
	public void setOfficeAttendanceGroupUserService(
			OfficeAttendanceGroupUserService officeAttendanceGroupUserService) {
		this.officeAttendanceGroupUserService = officeAttendanceGroupUserService;
	}
	public String getAuditComment() {
		return auditComment;
	}
	public void setAuditComment(String auditComment) {
		this.auditComment = auditComment;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTypeWeekTime() {
		return typeWeekTime;
	}
	public void setTypeWeekTime(String typeWeekTime) {
		this.typeWeekTime = typeWeekTime;
	}
	
}
