package net.zdsoft.office.studentLeave.action;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.entity.Family;
import net.zdsoft.eis.base.common.entity.Student;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.BasicClassService;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.base.common.service.StudentFamilyService;
import net.zdsoft.eis.base.common.service.StudentService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.subsystemcall.service.OfficeSubsystemService;
import net.zdsoft.eis.component.flowManage.constant.FlowConstant;
import net.zdsoft.eis.component.flowManage.entity.Flow;
import net.zdsoft.eis.component.flowManage.service.FlowManageService;
import net.zdsoft.eis.frame.action.PageAction;
import net.zdsoft.jbpm.activiti.engine.task.AgileIdentityLinkType;
import net.zdsoft.jbpm.core.entity.Comment;
import net.zdsoft.jbpm.core.entity.TaskHandlerSave;
import net.zdsoft.jbpm.core.entity.TaskHandlerShow;
import net.zdsoft.jbpm.core.service.TaskHandlerService;
import net.zdsoft.keel.util.UUIDUtils;
import net.zdsoft.office.expense.constant.OfficeExpenseConstants;
import net.zdsoft.office.studentLeave.constant.OfficeStuLeaveConstants;
import net.zdsoft.office.studentLeave.entity.OfficeHwstudentLeave;
import net.zdsoft.office.studentLeave.entity.OfficeLeaveFlow;
import net.zdsoft.office.studentLeave.entity.OfficeLeavetypeGeneral;
import net.zdsoft.office.studentLeave.entity.OfficeLeavetypeLive;
import net.zdsoft.office.studentLeave.entity.OfficeLeavetypeLong;
import net.zdsoft.office.studentLeave.entity.OfficeLeavetypeTemporary;
import net.zdsoft.office.studentLeave.service.OfficeHwstudentLeaveService;
import net.zdsoft.office.studentLeave.service.OfficeLeaveFlowService;
import net.zdsoft.office.studentLeave.service.OfficeLeavetypeGeneralService;
import net.zdsoft.office.studentLeave.service.OfficeLeavetypeLiveService;
import net.zdsoft.office.studentLeave.service.OfficeLeavetypeLongService;
import net.zdsoft.office.studentLeave.service.OfficeLeavetypeTemporaryService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

public class StudentLeaveManageAction extends PageAction{
	private static final long serialVersionUID = 2380543865645560462L;
	
	private CustomRoleService customRoleService;
	private CustomRoleUserService customRoleUserService;
	private String leaveType;
	private OfficeLeaveFlowService officeLeaveFlowService;
	
	private String leaveId;
	private List<OfficeLeavetypeGeneral> generals;
	private List<OfficeLeavetypeLive> lives;
	private List<OfficeLeavetypeTemporary> temporarys;
	private List<OfficeLeavetypeLong> longs;
	
	private OfficeLeavetypeGeneralService officeLeavetypeGeneralService;
	private List<OfficeLeaveFlow> officeLeaveFlows=new ArrayList<OfficeLeaveFlow>();
	private OfficeHwstudentLeaveService officeHwstudentLeaveService;
	private OfficeLeavetypeLiveService officeLeavetypeLiveService;
	private String type;
	private String flowIds;
	private OfficeLeavetypeTemporaryService officeLeavetypeTemporaryService;
	private OfficeLeavetypeLongService officeLeavetypeLongService;
	private TaskHandlerService taskHandlerService;
	private String gradeId;
	
	private OfficeLeavetypeGeneral leaveGeneral;
	private OfficeLeavetypeTemporary leaveTemporary;
	private OfficeLeavetypeLive leaveLive;
	private OfficeLeavetypeLong leaveLong;
	
	private List<Flow> flowList;
	private FlowManageService flowManageService;
	private BasicClassService basicClassService;
	private StudentService studentService;
	private UserService userService;
	private StudentFamilyService studentFamilyService;
	private boolean viewOnly = false;
	private String searchType;
	private String taskId;
	private String userName;
	private String taskHandlerSaveJson;
	private String currentStepId;
	private boolean isPass;
	private OfficeSubsystemService officeSubsystemService;
	
	public void setOfficeSubsystemService(
			OfficeSubsystemService officeSubsystemService) {
		this.officeSubsystemService = officeSubsystemService;
	}
	public String stuLeaveManage(){
		return SUCCESS;
	}
	//-------------长期请假-----------------
	public String longLeaveAuditEdit(){
		leaveLong = officeLeavetypeLongService.findByLeaveId(leaveId);
		TaskHandlerSave taskHandlerSave = new TaskHandlerSave();
		TaskHandlerShow taskHandlerShow = taskHandlerService.getTaskHandlerShow(taskId, getLoginUser().getUserId());
		taskHandlerSave.setCurrentTask(taskHandlerShow.getCurrentTask());
		taskHandlerSave.setCurrentUserId(getLoginUser().getUserId());
		taskHandlerSave.setCurrentUnitId(getLoginUser().getUnitId());
		taskHandlerSave.setSubsystemId(FlowConstant.OFFICE_SUBSYSTEM);
		User user = userService.getUser(getLoginUser().getUserId());
		Comment comment = new Comment();
		comment.setAssigneeType(AgileIdentityLinkType.PRINCIPAL);
		comment.setAssigneeId(user.getId());
		comment.setAssigneeName(user.getRealname());
		userName = user.getRealname();
		taskHandlerSave.setComment(comment);
		JSONObject json = new JSONObject().fromObject(taskHandlerSave);
		taskHandlerSaveJson = json.toString();
		leaveLong.setTaskName(taskHandlerSave.getCurrentTask().getTaskName());
		currentStepId = taskHandlerSave.getCurrentTask().getTaskDefinitionKey();
		return SUCCESS;
	}
	public String longLeaveAuditList(){
		longs = officeLeavetypeLongService.getAuditList(searchType, getLoginUser().getUserId(), getPage());
		return SUCCESS;
	}
	public String longLeave(){
		leaveType = "4";
		String stuId = null;
		if(getLoginInfo().getUser().getOwnerType() == User.FAMILY_LOGIN){
			Family family = studentFamilyService.getFamily(getLoginInfo().getUser().getTeacherid());
			stuId = family.getStudentId();
		}else if(getLoginInfo().getUser().getOwnerType() == User.STUDENT_LOGIN){
			stuId =getLoginInfo().getUser().getTeacherid();
		}
		longs = officeLeavetypeLongService.getLeavelist(getUnitId(), stuId, getPage());
		System.out.println(officeSubsystemService.getStuLeaveInfo("75B0EE9907FD4DB6A2F181E3BF997B40", "76E55BA3D5174FFEB5D4E7FD3B1709F7"));
		
		return SUCCESS;
	}
	
	public String longLeaveSave() {
		try{
			if(org.apache.commons.lang3.StringUtils.isBlank(leaveLong.getState())){
				leaveLong.setState(OfficeStuLeaveConstants.OFFCIE_STULEAVE_NOT_SUBMIT);
			}
			if(OfficeStuLeaveConstants.OFFCIE_STULEAVE_NOT_SUBMIT.equals(leaveLong.getState())){
				if(StringUtils.isNotBlank(leaveLong.getLeaveId())){
					OfficeHwstudentLeave office=officeHwstudentLeaveService.getOfficeHwstudentLeaveById(leaveLong.getLeaveId());
					if(office!=null){
						officeLeavetypeLongService.updateLong(leaveLong);
					}else{
						officeLeavetypeLongService.insertLong(leaveLong);
					}
				}
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("保存成功");
				promptMessageDto.setErrorMessage(leaveLong.getId());
			}else{
				//申请的时间不能与已有的时间重复
				boolean isExist = officeLeavetypeLiveService.isExistTime(null,leaveLong.getStartTime(),leaveLong.getEndTime(), true, leaveLong.getId(), leaveLong.getStudentId());
				if(!isExist){
					officeLeavetypeLongService.startFlow(leaveLong, getLoginUser().getUserId());
					promptMessageDto.setOperateSuccess(true);
					promptMessageDto.setPromptMessage("提交成功,已进入流程中");
				}else{
					//存在有交叉时间段的数据正在审核中或已经被审核通过
					promptMessageDto.setOperateSuccess(false);
					promptMessageDto.setErrorMessage("提交失败,存在有交叉时间段的信息正在审核中或已经被审核通过");
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			if(OfficeExpenseConstants.OFFCIE_EXPENSE_NOT_SUBMIT.equals(leaveLong.getState())){
				promptMessageDto.setErrorMessage("保存失败:"+e.getMessage());
			}else{
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
		}
		return SUCCESS;
	}
	
	public String longLeaveEdit(){
		List<Flow> allFlowList = flowManageService.getFinishFlowList(getUnitId(), FlowConstant.FLOW_OWNER_UNIT, 
				FlowConstant.OFFICE_STUDENT_LEAVE_LONG,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
		String stuId = null;
		if(getLoginInfo().getUser().getOwnerType() == User.FAMILY_LOGIN){
			Family family = studentFamilyService.getFamily(getLoginInfo().getUser().getTeacherid());
			stuId = family.getStudentId();
		}else if(getLoginInfo().getUser().getOwnerType() == User.STUDENT_LOGIN){
			stuId =getLoginInfo().getUser().getTeacherid();
		}
		Student stu = null;
		BasicClass cls = null;
		if(StringUtils.isNotBlank(stuId)){
			stu = studentService.getStudent(stuId);
			cls = basicClassService.getClass(stu.getClassid());
			gradeId = cls.getGradeId();
			flowList = new ArrayList<Flow>();
			List<OfficeLeaveFlow> flows = officeLeaveFlowService.getFlowsByGradeIdAndType(getUnitId(),gradeId,"4");
			for (Flow flow : allFlowList) {
				for(OfficeLeaveFlow e : flows){
					if(StringUtils.equals(e.getFlowId(), flow.getFlowId())){
						flowList.add(flow);
						break;
					}
				}
			}
		}
		if(StringUtils.isBlank(leaveId)){
			leaveLong = new OfficeLeavetypeLong();
			leaveLong.setUnitId(getUnitId());
			leaveLong.setApplyUserId(getLoginUser().getUserId());
			leaveLong.setApplyUserName(getLoginInfo().getUser().getRealname());
			leaveLong.setLeaveId(UUIDUtils.newId());
			if(getLoginInfo().getUser().getOwnerType() == User.FAMILY_LOGIN){
				leaveLong.setStudentId(stu.getId());
				leaveLong.setClassId(stu.getClassid());
				leaveLong.setClassName(cls.getClassnamedynamic());
				leaveLong.setStudentName(stu.getStuname());
			}else if(getLoginInfo().getUser().getOwnerType() == User.STUDENT_LOGIN){
				leaveLong.setStudentId(stu.getId());
				leaveLong.setClassId(stu.getClassid());
				leaveLong.setClassName(cls.getClassnamedynamic());
				leaveLong.setStudentName(stu.getStuname());
			}
//			for(Flow item : flowList){
//				if(item.isDefaultFlow()){
//					leaveLong.setFlowId(item.getFlowId());
//				}
//			}
		}else{
			leaveLong = officeLeavetypeLongService.findByLeaveId(leaveId);
			User user = userService.getUser(leaveLong.getApplyUserId());
			leaveLong.setApplyUserName(user.getRealname());
		}
		return SUCCESS;
	}
	
	//-------------暂时住校或通校请假-----------------
	public String liveLeaveAuditEdit(){
		leaveLive = officeLeavetypeLiveService.findByLeaveId(leaveId);
		TaskHandlerSave taskHandlerSave = new TaskHandlerSave();
		TaskHandlerShow taskHandlerShow = taskHandlerService.getTaskHandlerShow(taskId, getLoginUser().getUserId());
		taskHandlerSave.setCurrentTask(taskHandlerShow.getCurrentTask());
		taskHandlerSave.setCurrentUserId(getLoginUser().getUserId());
		taskHandlerSave.setCurrentUnitId(getLoginUser().getUnitId());
		taskHandlerSave.setSubsystemId(FlowConstant.OFFICE_SUBSYSTEM);
		User user = userService.getUser(getLoginUser().getUserId());
		Comment comment = new Comment();
		comment.setAssigneeType(AgileIdentityLinkType.PRINCIPAL);
		comment.setAssigneeId(user.getId());
		comment.setAssigneeName(user.getRealname());
		userName = user.getRealname();
		taskHandlerSave.setComment(comment);
		JSONObject json = new JSONObject().fromObject(taskHandlerSave);
		taskHandlerSaveJson = json.toString();
		leaveLive.setTaskName(taskHandlerSave.getCurrentTask().getTaskName());
		currentStepId = taskHandlerSave.getCurrentTask().getTaskDefinitionKey();
		return SUCCESS;
	}
	public String liveLeaveAuditList(){
		lives = officeLeavetypeLiveService.getAuditList(searchType, getLoginUser().getUserId(), getPage());
		return SUCCESS;
	}
	public String liveLeave(){
		leaveType = "3";
		String stuId = null;
		if(getLoginInfo().getUser().getOwnerType() == User.FAMILY_LOGIN){
			Family family = studentFamilyService.getFamily(getLoginInfo().getUser().getTeacherid());
			stuId = family.getStudentId();
		}else if(getLoginInfo().getUser().getOwnerType() == User.STUDENT_LOGIN){
			stuId =getLoginInfo().getUser().getTeacherid();
		}
		lives = officeLeavetypeLiveService.getLeavelist(getUnitId(), stuId, getPage());
		return SUCCESS;
	}
	
	public String liveLeaveSave() {
		try{
			if(org.apache.commons.lang3.StringUtils.isBlank(leaveLive.getState())){
				leaveLive.setState(OfficeStuLeaveConstants.OFFCIE_STULEAVE_NOT_SUBMIT);
			}
			if(OfficeStuLeaveConstants.OFFCIE_STULEAVE_NOT_SUBMIT.equals(leaveLive.getState())){
				if(StringUtils.isNotBlank(leaveLive.getLeaveId())){
					OfficeHwstudentLeave office=officeHwstudentLeaveService.getOfficeHwstudentLeaveById(leaveLive.getLeaveId());
					if(office!=null){
						officeLeavetypeLiveService.updateLive(leaveLive);
					}else{
						officeLeavetypeLiveService.insertLive(leaveLive);
					}
				}
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("保存成功");
				promptMessageDto.setErrorMessage(leaveLive.getId());
			}else{
				//申请的时间不能与已有的时间重复
				boolean isExist = officeLeavetypeLiveService.isExistTime(leaveLive.getId(),leaveLive.getStartTime(),leaveLive.getEndTime(), true, null, leaveLive.getStudentId());
				if(!isExist){
					officeLeavetypeLiveService.startFlow(leaveLive, getLoginUser().getUserId());
					promptMessageDto.setOperateSuccess(true);
					promptMessageDto.setPromptMessage("提交成功,已进入流程中");
				}else{
					//存在有交叉时间段的数据正在审核中或已经被审核通过
					promptMessageDto.setOperateSuccess(false);
					promptMessageDto.setErrorMessage("提交失败,存在有交叉时间段的信息正在审核中或已经被审核通过");
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			if(OfficeExpenseConstants.OFFCIE_EXPENSE_NOT_SUBMIT.equals(leaveLive.getState())){
				promptMessageDto.setErrorMessage("保存失败:"+e.getMessage());
			}else{
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
		}
		return SUCCESS;
	}
	
	public String liveLeaveEdit(){
		List<Flow> allFlowList = flowManageService.getFinishFlowList(getUnitId(), FlowConstant.FLOW_OWNER_UNIT, 
				FlowConstant.OFFICE_STUDENT_LEAVE_LIVE,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
		String stuId = null;
		if(getLoginInfo().getUser().getOwnerType() == User.FAMILY_LOGIN){
			Family family = studentFamilyService.getFamily(getLoginInfo().getUser().getTeacherid());
			stuId = family.getStudentId();
		}else if(getLoginInfo().getUser().getOwnerType() == User.STUDENT_LOGIN){
			stuId =getLoginInfo().getUser().getTeacherid();
		}
		Student stu = null;
		BasicClass cls = null;
		if(StringUtils.isNotBlank(stuId)){
			stu = studentService.getStudent(stuId);
			cls = basicClassService.getClass(stu.getClassid());
			gradeId = cls.getGradeId();
			flowList = new ArrayList<Flow>();
			List<OfficeLeaveFlow> flows = officeLeaveFlowService.getFlowsByGradeIdAndType(getUnitId(),gradeId,"3");
			for (Flow flow : allFlowList) {
				for(OfficeLeaveFlow e : flows){
					if(StringUtils.equals(e.getFlowId(), flow.getFlowId())){
						flowList.add(flow);
						break;
					}
				}
			}
		}
		if(StringUtils.isBlank(leaveId)){
			leaveLive = new OfficeLeavetypeLive();
			leaveLive.setUnitId(getUnitId());
			leaveLive.setApplyUserId(getLoginUser().getUserId());
			leaveLive.setApplyUserName(getLoginInfo().getUser().getRealname());
			leaveLive.setLeaveId(UUIDUtils.newId());
			if(getLoginInfo().getUser().getOwnerType() == User.FAMILY_LOGIN){
				leaveLive.setStudentId(stu.getId());
				leaveLive.setClassId(stu.getClassid());
				leaveLive.setClassName(cls.getClassnamedynamic());
				leaveLive.setStudentName(stu.getStuname());
			}else if(getLoginInfo().getUser().getOwnerType() == User.STUDENT_LOGIN){
				leaveLive.setStudentId(stu.getId());
				leaveLive.setClassId(stu.getClassid());
				leaveLive.setClassName(cls.getClassnamedynamic());
				leaveLive.setStudentName(stu.getStuname());
			}
//			for(Flow item : flowList){
//				if(item.isDefaultFlow()){
//					leaveLive.setFlowId(item.getFlowId());
//				}
//			}
		}else{
			leaveLive = officeLeavetypeLiveService.findByLeaveId(leaveId);
			User user = userService.getUser(leaveLive.getApplyUserId());
			leaveLive.setApplyUserName(user.getRealname());
		}
		return SUCCESS;
	}
	
	//-------------临时出校请假-----------------
	public String temporaryLeaveAuditEdit(){
		leaveTemporary = officeLeavetypeTemporaryService.findByLeaveId(leaveId);
		TaskHandlerSave taskHandlerSave = new TaskHandlerSave();
		TaskHandlerShow taskHandlerShow = taskHandlerService.getTaskHandlerShow(taskId, getLoginUser().getUserId());
		taskHandlerSave.setCurrentTask(taskHandlerShow.getCurrentTask());
		taskHandlerSave.setCurrentUserId(getLoginUser().getUserId());
		taskHandlerSave.setCurrentUnitId(getLoginUser().getUnitId());
		taskHandlerSave.setSubsystemId(FlowConstant.OFFICE_SUBSYSTEM);
		User user = userService.getUser(getLoginUser().getUserId());
		Comment comment = new Comment();
		comment.setAssigneeType(AgileIdentityLinkType.PRINCIPAL);
		comment.setAssigneeId(user.getId());
		comment.setAssigneeName(user.getRealname());
		userName = user.getRealname();
		taskHandlerSave.setComment(comment);
		JSONObject json = new JSONObject().fromObject(taskHandlerSave);
		taskHandlerSaveJson = json.toString();
		leaveTemporary.setTaskName(taskHandlerSave.getCurrentTask().getTaskName());
		currentStepId = taskHandlerSave.getCurrentTask().getTaskDefinitionKey();
		return SUCCESS;
	}
	public String temporaryLeaveAuditList(){
		temporarys = officeLeavetypeTemporaryService.getAuditList(searchType, getLoginUser().getUserId(), getPage());
		return SUCCESS;
	}

	
	public String temporaryLeave(){
		leaveType = "2";
		String stuId = null;
		if(getLoginInfo().getUser().getOwnerType() == User.FAMILY_LOGIN){
			Family family = studentFamilyService.getFamily(getLoginInfo().getUser().getTeacherid());
			stuId = family.getStudentId();
		}else if(getLoginInfo().getUser().getOwnerType() == User.STUDENT_LOGIN){
			stuId =getLoginInfo().getUser().getTeacherid();
		}
		temporarys = officeLeavetypeTemporaryService.getLeavelist(getUnitId(), stuId, getPage());
		return SUCCESS;
	}
	
	public String temporaryLeaveSave() {
		try{
			if(org.apache.commons.lang3.StringUtils.isBlank(leaveTemporary.getState())){
				leaveTemporary.setState(OfficeStuLeaveConstants.OFFCIE_STULEAVE_NOT_SUBMIT);
			}
			if(OfficeStuLeaveConstants.OFFCIE_STULEAVE_NOT_SUBMIT.equals(leaveTemporary.getState())){
				if(StringUtils.isNotBlank(leaveTemporary.getLeaveId())){
					OfficeHwstudentLeave office=officeHwstudentLeaveService.getOfficeHwstudentLeaveById(leaveTemporary.getLeaveId());
					if(office!=null){
						officeLeavetypeTemporaryService.updateTemporary(leaveTemporary);
					}else{
						officeLeavetypeTemporaryService.insertTemporary(leaveTemporary);
					}
				}
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("保存成功");
				promptMessageDto.setErrorMessage(leaveTemporary.getId());
			}else{
				//申请的请假单时间不能与已有的时间重复
				boolean isExist = officeLeavetypeGeneralService.isExistTime(null,leaveTemporary.getStartTime(),leaveTemporary.getEndTime(), true, leaveTemporary.getId(), leaveTemporary.getStudentId());
				if(!isExist){
					officeLeavetypeTemporaryService.startFlow(leaveTemporary, getLoginUser().getUserId());
					promptMessageDto.setOperateSuccess(true);
					promptMessageDto.setPromptMessage("提交成功,已进入流程中");
				}else{
					//存在有交叉时间段的数据正在审核中或已经被审核通过
					promptMessageDto.setOperateSuccess(false);
					promptMessageDto.setErrorMessage("提交失败,存在有交叉时间段的信息正在审核中或已经被审核通过");
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			if(OfficeExpenseConstants.OFFCIE_EXPENSE_NOT_SUBMIT.equals(leaveTemporary.getState())){
				promptMessageDto.setErrorMessage("保存失败:"+e.getMessage());
			}else{
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
		}
		return SUCCESS;
	}
	
	public String temporaryLeaveEdit(){
		List<Flow> allFlowList = flowManageService.getFinishFlowList(getUnitId(), FlowConstant.FLOW_OWNER_UNIT, 
				FlowConstant.OFFICE_STUDENT_LEAVE_TEMPORARY,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
		String stuId = null;
		if(getLoginInfo().getUser().getOwnerType() == User.FAMILY_LOGIN){
			Family family = studentFamilyService.getFamily(getLoginInfo().getUser().getTeacherid());
			stuId = family.getStudentId();
		}else if(getLoginInfo().getUser().getOwnerType() == User.STUDENT_LOGIN){
			stuId =getLoginInfo().getUser().getTeacherid();
		}
		Student stu = null;
		BasicClass cls = null;
		if(StringUtils.isNotBlank(stuId)){
			stu = studentService.getStudent(stuId);
			cls = basicClassService.getClass(stu.getClassid());
			gradeId = cls.getGradeId();
			flowList = new ArrayList<Flow>();
			List<OfficeLeaveFlow> flows = officeLeaveFlowService.getFlowsByGradeIdAndType(getUnitId(),gradeId,"2");
			for (Flow flow : allFlowList) {
				for(OfficeLeaveFlow e : flows){
					if(StringUtils.equals(e.getFlowId(), flow.getFlowId())){
						flowList.add(flow);
						break;
					}
				}
			}
		}
		if(StringUtils.isBlank(leaveId)){
			leaveTemporary = new OfficeLeavetypeTemporary();
			leaveTemporary.setUnitId(getUnitId());
			leaveTemporary.setApplyUserId(getLoginUser().getUserId());
			leaveTemporary.setApplyUserName(getLoginInfo().getUser().getRealname());
			leaveTemporary.setLeaveId(UUIDUtils.newId());
			if(getLoginInfo().getUser().getOwnerType() == User.FAMILY_LOGIN){
				leaveTemporary.setStudentId(stu.getId());
				leaveTemporary.setClassId(stu.getClassid());
				leaveTemporary.setClassName(cls.getClassnamedynamic());
				leaveTemporary.setStudentName(stu.getStuname());
			}else if(getLoginInfo().getUser().getOwnerType() == User.STUDENT_LOGIN){
				leaveTemporary.setStudentId(stu.getId());
				leaveTemporary.setClassId(stu.getClassid());
				leaveTemporary.setClassName(cls.getClassnamedynamic());
				leaveTemporary.setStudentName(stu.getStuname());
			}
//			for(Flow item : flowList){
//				if(item.isDefaultFlow()){
//					leaveTemporary.setFlowId(item.getFlowId());
//				}
//			}
		}else{
			leaveTemporary = officeLeavetypeTemporaryService.findByLeaveId(leaveId);
			User user = userService.getUser(leaveTemporary.getApplyUserId());
			leaveTemporary.setApplyUserName(user.getRealname());
		}
		return SUCCESS;
	}
	//-------------一般请假-----------------
	public String generalLeaveAuditEdit(){
		leaveGeneral = officeLeavetypeGeneralService.findByLeaveId(leaveId);
		TaskHandlerSave taskHandlerSave = new TaskHandlerSave();
		TaskHandlerShow taskHandlerShow = taskHandlerService.getTaskHandlerShow(taskId, getLoginUser().getUserId());
		taskHandlerSave.setCurrentTask(taskHandlerShow.getCurrentTask());
		taskHandlerSave.setCurrentUserId(getLoginUser().getUserId());
		taskHandlerSave.setCurrentUnitId(getLoginUser().getUnitId());
		taskHandlerSave.setSubsystemId(FlowConstant.OFFICE_SUBSYSTEM);
		User user = userService.getUser(getLoginUser().getUserId());
		Comment comment = new Comment();
		comment.setAssigneeType(AgileIdentityLinkType.PRINCIPAL);
		comment.setAssigneeId(user.getId());
		comment.setAssigneeName(user.getRealname());
		userName = user.getRealname();
		taskHandlerSave.setComment(comment);
		JSONObject json = new JSONObject().fromObject(taskHandlerSave);
		taskHandlerSaveJson = json.toString();
		leaveGeneral.setTaskName(taskHandlerSave.getCurrentTask().getTaskName());
		currentStepId = taskHandlerSave.getCurrentTask().getTaskDefinitionKey();
		return SUCCESS;
	}
	public String generalLeaveAuditList(){
		generals = officeLeavetypeGeneralService.getAuditList(searchType, getLoginUser().getUserId(), getPage());
		return SUCCESS;
	}

	public String generalLeave(){
		leaveType = "1";
		String stuId = null;
		if(getLoginInfo().getUser().getOwnerType() == User.FAMILY_LOGIN){
			Family family = studentFamilyService.getFamily(getLoginInfo().getUser().getTeacherid());
			stuId = family.getStudentId();
		}else if(getLoginInfo().getUser().getOwnerType() == User.STUDENT_LOGIN){
			stuId =getLoginInfo().getUser().getTeacherid();
		}
		generals = officeLeavetypeGeneralService.getLeavelist(getUnitId(), stuId, getPage());
		return SUCCESS;
	}
	
	public String generalLeaveSave() {
		if(org.apache.commons.lang3.StringUtils.isBlank(leaveGeneral.getState())){
			leaveGeneral.setState(OfficeStuLeaveConstants.OFFCIE_STULEAVE_NOT_SUBMIT);
		}
		try{
			if(OfficeStuLeaveConstants.OFFCIE_STULEAVE_NOT_SUBMIT.equals(leaveGeneral.getState())){
				if(StringUtils.isNotBlank(leaveGeneral.getLeaveId())){
					OfficeLeavetypeGeneral office=officeLeavetypeGeneralService.findByLeaveId(leaveGeneral.getLeaveId());
					if(office!=null){
						officeLeavetypeGeneralService.updateGeneral(leaveGeneral);
					}else{
						officeLeavetypeGeneralService.insertGeneral(leaveGeneral);
					}
				}
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("保存成功");
				promptMessageDto.setErrorMessage(leaveGeneral.getId());
			}else{
				//申请的请假单时间不能与已有的时间重复
				boolean isExist = officeLeavetypeGeneralService.isExistTime(leaveGeneral.getId(),leaveGeneral.getStartTime(),leaveGeneral.getEndTime(), true, null, leaveGeneral.getStudentId());
				if(!isExist){
					officeLeavetypeGeneralService.startFlow(leaveGeneral, getLoginUser().getUserId());
					promptMessageDto.setOperateSuccess(true);
					promptMessageDto.setPromptMessage("提交成功,已进入流程中");
				}else{
					//存在有交叉时间段的数据正在审核中或已经被审核通过
					promptMessageDto.setOperateSuccess(false);
					promptMessageDto.setErrorMessage("提交失败,存在有交叉时间段的信息正在审核中或已经被审核通过");
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			if(OfficeExpenseConstants.OFFCIE_EXPENSE_NOT_SUBMIT.equals(leaveGeneral.getState())){
				promptMessageDto.setErrorMessage("保存失败:"+e.getMessage());
			}else{
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
		}
		return SUCCESS;
	}
	
	public String generalLeaveEdit(){
		List<Flow> allFlowList = flowManageService.getFinishFlowList(getUnitId(), FlowConstant.FLOW_OWNER_UNIT, 
				FlowConstant.OFFICE_STUDENT_LEAVE_GENERAL,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
		String stuId = null;
		if(getLoginInfo().getUser().getOwnerType() == User.FAMILY_LOGIN){
			Family family = studentFamilyService.getFamily(getLoginInfo().getUser().getTeacherid());
			stuId = family.getStudentId();
		}else if(getLoginInfo().getUser().getOwnerType() == User.STUDENT_LOGIN){
			stuId =getLoginInfo().getUser().getTeacherid();
		}
		Student stu = null;
		BasicClass cls = null;
		if(StringUtils.isNotBlank(stuId)){
			stu = studentService.getStudent(stuId);
			cls = basicClassService.getClass(stu.getClassid());
			gradeId = cls.getGradeId();
			flowList = new ArrayList<Flow>();
			List<OfficeLeaveFlow> flows = officeLeaveFlowService.getFlowsByGradeIdAndType(getUnitId(),gradeId,"1");
			for (Flow flow : allFlowList) {
				for(OfficeLeaveFlow e : flows){
					if(StringUtils.equals(e.getFlowId(), flow.getFlowId())){
						flowList.add(flow);
						break;
					}
				}
			}
		}
		if(StringUtils.isBlank(leaveId)){
			leaveGeneral = new OfficeLeavetypeGeneral();
			leaveGeneral.setUnitId(getUnitId());
			leaveGeneral.setApplyUserId(getLoginUser().getUserId());
			leaveGeneral.setApplyUserName(getLoginInfo().getUser().getRealname());
			leaveGeneral.setLeaveId(UUIDUtils.newId());
			if(getLoginInfo().getUser().getOwnerType() == User.FAMILY_LOGIN){
				leaveGeneral.setStudentId(stu.getId());
				leaveGeneral.setClassId(stu.getClassid());
				leaveGeneral.setClassName(cls.getClassnamedynamic());
				leaveGeneral.setStudentName(stu.getStuname());
			}else if(getLoginInfo().getUser().getOwnerType() == User.STUDENT_LOGIN){
				leaveGeneral.setStudentId(stu.getId());
				leaveGeneral.setClassId(stu.getClassid());
				leaveGeneral.setClassName(cls.getClassnamedynamic());
				leaveGeneral.setStudentName(stu.getStuname());
			}
//			for(Flow item : flowList){
//				if(item.isDefaultFlow()){
//					leaveGeneral.setFlowId(item.getFlowId());
//				}
//			}
		}else{
			leaveGeneral = officeLeavetypeGeneralService.findByLeaveId(leaveId);
			User user = userService.getUser(leaveGeneral.getApplyUserId());
			leaveGeneral.setApplyUserName(user.getRealname());
		}
		return SUCCESS;
	}
	
	public void setFlowManageService(FlowManageService flowManageService) {
		this.flowManageService = flowManageService;
	}
	
	public String deleteLeave(){
		if(StringUtils.isNotEmpty(leaveId)){
			try {
				officeHwstudentLeaveService.deleteById(leaveId);
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("删除成功");
			} catch (Exception e) {
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("删除失败:"+e.getCause().getMessage());
			}
		}else{
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("删除失败:找不到删除的对象");
		}
		return SUCCESS;
	}
	
	public String leaveAuditSave(){
		try {
			JSONObject object = new JSONObject().fromObject(taskHandlerSaveJson);
			TaskHandlerSave taskHandlerSave =(TaskHandlerSave) JSONObject.toBean(object,
				 TaskHandlerSave.class);
			taskHandlerSave.getComment().setOperateTime(new Date());
			officeHwstudentLeaveService.doAudit(isPass(), taskHandlerSave, leaveId, currentStepId);
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("审核成功");
		}catch (Exception e){
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

	
	public List<Flow> getFlowList() {
		return flowList;
	}

	public void setFlowList(List<Flow> flowList) {
		this.flowList = flowList;
	}

	
	public String execute(){
		return SUCCESS;
	}
	
	public String queryFlowList(){
		if(org.apache.commons.lang3.StringUtils.isBlank(type)){
			type="1";
		}
		officeLeaveFlows=officeLeaveFlowService.getOfficeLeaveFlowsByUnitId(getUnitId(),type);
		return SUCCESS;
	}
	
	public String saveFlow(){
		try {
			String[] selectedFlowIds =null;
			if(StringUtils.isNotBlank(flowIds)){
				selectedFlowIds = flowIds.split(",");
			}
			officeLeaveFlowService.saveOfficeLeaveFlowByUnitIdAndType(getUnitId(),gradeId, type, selectedFlowIds);
			promptMessageDto.setOperateSuccess(true);
		} catch (Exception e) {
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage(e.getMessage());
		}
		return SUCCESS;
	}
	/**
	 * 判断其是否为各种管理员
	 */
	private boolean isPracticeAdmin(String str){
		CustomRole role = customRoleService.getCustomRoleByRoleCode(getUnitId(), str);
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

	public void setStudentFamilyService(StudentFamilyService studentFamilyService) {
		this.studentFamilyService = studentFamilyService;
	}

	public OfficeLeavetypeGeneral getLeaveGeneral() {
		return leaveGeneral;
	}

	public void setLeaveGeneral(OfficeLeavetypeGeneral leaveGeneral) {
		this.leaveGeneral = leaveGeneral;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public String getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(String leaveId) {
		this.leaveId = leaveId;
	}

	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	}

	public void setCustomRoleUserService(CustomRoleUserService customRoleUserService) {
		this.customRoleUserService = customRoleUserService;
	}
	

	public List<OfficeLeaveFlow> getOfficeLeaveFlows() {
		return officeLeaveFlows;
	}

	public void setOfficeLeaveFlows(List<OfficeLeaveFlow> officeLeaveFlows) {
		this.officeLeaveFlows = officeLeaveFlows;
	}

	public void setOfficeLeaveFlowService(
			OfficeLeaveFlowService officeLeaveFlowService) {
		this.officeLeaveFlowService = officeLeaveFlowService;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFlowIds() {
		return flowIds;
	}

	public void setFlowIds(String flowIds) {
		this.flowIds = flowIds;
	}

	public String getGradeId() {
		return gradeId;
	}

	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}
	
	public void setBasicClassService(BasicClassService basicClassService) {
		this.basicClassService = basicClassService;
	}

	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setOfficeLeavetypeGeneralService(
			OfficeLeavetypeGeneralService officeLeavetypeGeneralService) {
		this.officeLeavetypeGeneralService = officeLeavetypeGeneralService;
	}

	public void setOfficeLeavetypeLiveService(
			OfficeLeavetypeLiveService officeLeavetypeLiveService) {
		this.officeLeavetypeLiveService = officeLeavetypeLiveService;
	}

	public void setOfficeLeavetypeTemporaryService(
			OfficeLeavetypeTemporaryService officeLeavetypeTemporaryService) {
		this.officeLeavetypeTemporaryService = officeLeavetypeTemporaryService;
	}

	public void setOfficeLeavetypeLongService(
			OfficeLeavetypeLongService officeLeavetypeLongService) {
		this.officeLeavetypeLongService = officeLeavetypeLongService;
	}

	public List<OfficeLeavetypeGeneral> getGenerals() {
		return generals;
	}

	public void setGenerals(List<OfficeLeavetypeGeneral> generals) {
		this.generals = generals;
	}

	public List<OfficeLeavetypeLive> getLives() {
		return lives;
	}

	public void setLives(List<OfficeLeavetypeLive> lives) {
		this.lives = lives;
	}

	public List<OfficeLeavetypeTemporary> getTemporarys() {
		return temporarys;
	}

	public void setTemporarys(List<OfficeLeavetypeTemporary> temporarys) {
		this.temporarys = temporarys;
	}

	public List<OfficeLeavetypeLong> getLongs() {
		return longs;
	}

	public void setLongs(List<OfficeLeavetypeLong> longs) {
		this.longs = longs;
	}

	public boolean isViewOnly() {
		return viewOnly;
	}

	public void setViewOnly(boolean viewOnly) {
		this.viewOnly = viewOnly;
	}
	
	public OfficeLeavetypeTemporary getLeaveTemporary() {
		return leaveTemporary;
	}

	public void setLeaveTemporary(OfficeLeavetypeTemporary leaveTemporary) {
		this.leaveTemporary = leaveTemporary;
	}

	public OfficeLeavetypeLive getLeaveLive() {
		return leaveLive;
	}

	public void setLeaveLive(OfficeLeavetypeLive leaveLive) {
		this.leaveLive = leaveLive;
	}

	public OfficeLeavetypeLong getLeaveLong() {
		return leaveLong;
	}

	public void setLeaveLong(OfficeLeavetypeLong leaveLong) {
		this.leaveLong = leaveLong;
	}
	
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public void setOfficeHwstudentLeaveService(
			OfficeHwstudentLeaveService officeHwstudentLeaveService) {
		this.officeHwstudentLeaveService = officeHwstudentLeaveService;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTaskHandlerSaveJson() {
		return taskHandlerSaveJson;
	}
	public void setTaskHandlerSaveJson(String taskHandlerSaveJson) {
		this.taskHandlerSaveJson = taskHandlerSaveJson;
	}
	public String getCurrentStepId() {
		return currentStepId;
	}
	public void setCurrentStepId(String currentStepId) {
		this.currentStepId = currentStepId;
	}
	public boolean isPass() {
		return isPass;
	}
	public void setPass(boolean isPass) {
		this.isPass = isPass;
	}
	public void setTaskHandlerService(TaskHandlerService taskHandlerService) {
		this.taskHandlerService = taskHandlerService;
	}
	public String getFromType() {
		if(getLoginInfo().getUser().getOwnerType() == User.FAMILY_LOGIN){
			return "stuplatform";
		}else{
			return "";
		}
	}
}
