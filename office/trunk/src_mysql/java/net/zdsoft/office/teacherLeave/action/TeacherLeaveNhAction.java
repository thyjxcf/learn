package net.zdsoft.office.teacherLeave.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.BasicClassService;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.TeacherService;
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
import net.zdsoft.leadin.util.excel.ZdCell;
import net.zdsoft.leadin.util.excel.ZdExcel;
import net.zdsoft.leadin.util.excel.ZdStyle;
import net.zdsoft.office.studentLeave.entity.OfficeLeaveType;
import net.zdsoft.office.studentLeave.service.OfficeLeaveTypeService;
import net.zdsoft.office.teacherLeave.entity.OfficeTeacherLeaveNh;
import net.zdsoft.office.teacherLeave.service.OfficeTeacherLeaveNhService;
import net.zdsoft.office.util.Constants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;


/**
 * @author chens
 * 宁海教师请假
 * @version 创建时间：2015-8-18 下午2:01:19
 * 
 */
@SuppressWarnings("serial")
public class TeacherLeaveNhAction extends PageAction{

	private BasicClassService basicClassService;
	private FlowManageService flowManageService;
	private TaskHandlerService taskHandlerService;
	private OfficeLeaveTypeService officeLeaveTypeService;
	private OfficeTeacherLeaveNhService officeTeacherLeaveNhService;
	private UserService userService;
	private Integer auditStatus;//审核状态
	private TeacherService teacherService; 
	private DeptService deptService; 
	private CustomRoleService customRoleService;
	private CustomRoleUserService customRoleUserService;
	
	private boolean isPass;
	private boolean canAudit;//是否有审核权限
	
	private String flowId;
	private String taskId;
	private String userName;
	private String queryState;
	private String leaveTypeId;
	private String currentStepId;
	private String teacherLeaveNhId;
	private String taskHandlerSaveJson;
	private Date startTime;
	private Date endTime;
	private String nextDay;
	private String applyUserId;//申请人id
	private String flowType;//申请流程类型
	
	private OfficeLeaveType officeLeaveType;
	private OfficeTeacherLeaveNh officeTeacherLeaveNh;
	
	private Map<String, String> levMap=new HashMap<String, String>();
	private List<User> users=new ArrayList<User>();
	private List<Flow> flowList;
	private List<OfficeLeaveType> leaveTypeList;
	private List<OfficeTeacherLeaveNh> teacherLeaveNhList;
	private List<OfficeLeaveType> officeLeaveTypeList=new ArrayList<OfficeLeaveType>();
	
	
	public String execute() throws Exception{
		canAudit = isPracticeAdmin("teacher_leave_audit");
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
	
	public String leaveApply(){
		return SUCCESS;
	}
	
	public String leaveApplyList(){
		teacherLeaveNhList = officeTeacherLeaveNhService.getOfficeTeacherLeaveNhPage(getLoginUser().getUserId(), startTime, endTime, queryState, getPage());
		return SUCCESS;
	}
	
	public String leaveApplyEdit(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date= new Date();
		date = DateUtils.getNextDay(date);
		nextDay = sdf.format(date)+" 00:00:00";
		if(StringUtils.isNotBlank(teacherLeaveNhId)){
			officeTeacherLeaveNh = officeTeacherLeaveNhService.getOfficeTeacherLeaveNhById(teacherLeaveNhId);
		}else{
			officeTeacherLeaveNh = new OfficeTeacherLeaveNh();
			officeTeacherLeaveNh.setApplyUserId(getLoginUser().getUserId());
			officeTeacherLeaveNh.setUserName(getLoginInfo().getUser().getRealname());
		}
		User user = userService.getUser(officeTeacherLeaveNh.getApplyUserId());
		leaveTypeList=officeLeaveTypeService.getOfficeLeaveTypeByUnitIdList(this.getUnitId(), Constants.TEACHER_LEAVE_TYPE);
		List<BasicClass> classList = basicClassService.getClassesByTeacherIdAll(getUnitId(), user.getTeacherid());
		String flowType = "";
		if(classList.size() > 0){
			flowType = FlowConstant.OFFICE_LEAVE_CLASS_CHARGE;
		}else{
			flowType = FlowConstant.OFFICE_LEAVE_NOT_CLASS_CHARGE;
		}
		officeTeacherLeaveNh.setFlowType(flowType);
		flowList = flowManageService.getFinishFlowList(getUnitId(), FlowConstant.FLOW_OWNER_UNIT, flowType,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
		return SUCCESS;
	}
	
	public String deleteLeaveApply(){
		try {
			officeTeacherLeaveNhService.delete(new String[]{teacherLeaveNhId});
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("操作失败！");
		}
		return SUCCESS;
	}
	
	public String flowDiv(){
		if(StringUtils.isNotBlank(applyUserId)){
			User user = userService.getUser(applyUserId);
			List<BasicClass> classList = basicClassService.getClassesByTeacherIdAll(getUnitId(), user.getTeacherid());
			if(classList.size() > 0){
				flowType = FlowConstant.OFFICE_LEAVE_CLASS_CHARGE;
			}else{
				flowType = FlowConstant.OFFICE_LEAVE_NOT_CLASS_CHARGE;
			}
			flowList = flowManageService.getFinishFlowList(getUnitId(), FlowConstant.FLOW_OWNER_UNIT, flowType,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
		}
		return SUCCESS;
	}
	
	public String saveTeacherLeaveNh(){
		try {
			if(officeTeacherLeaveNh.getBeginTime().compareTo(officeTeacherLeaveNh.getEndTime()) >= 0){
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("开始时间与结束时间之间 前后时间不合逻辑，请更正！");
				return SUCCESS;
			}
			boolean flag = officeTeacherLeaveNhService.isExistConflict(officeTeacherLeaveNh.getId(),officeTeacherLeaveNh.getApplyUserId(), officeTeacherLeaveNh.getBeginTime(), officeTeacherLeaveNh.getEndTime());
			if(flag) {
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("保存失败，该申请人在该时间段内已经存在申请！");
				return SUCCESS;
			}
			officeTeacherLeaveNh.setState(Constants.APPLY_STATE_SAVE);
			if(StringUtils.isNotEmpty(officeTeacherLeaveNh.getId())){
				officeTeacherLeaveNhService.update(officeTeacherLeaveNh);
			}else{
				officeTeacherLeaveNh.setCreateTime(new Date());
				officeTeacherLeaveNh.setCreateUserId(getLoginUser().getUserId());
				officeTeacherLeaveNh.setIsDeleted(false);
				officeTeacherLeaveNh.setUnitId(getUnitId());
				officeTeacherLeaveNhService.save(officeTeacherLeaveNh);
			}
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("保存失败:"+e.getCause().getMessage());
		}
		return SUCCESS;
	}
	
	public String submitTeacherLeaveNh(){
		try {
			if(officeTeacherLeaveNh.getBeginTime().compareTo(officeTeacherLeaveNh.getEndTime()) >= 0){
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("开始时间与结束时间之间 前后时间不合逻辑，请更正！");
				return SUCCESS;
			}
			boolean flag = officeTeacherLeaveNhService.isExistConflict(officeTeacherLeaveNh.getId(),officeTeacherLeaveNh.getApplyUserId(), officeTeacherLeaveNh.getBeginTime(), officeTeacherLeaveNh.getEndTime());
			if(flag) {
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("保存失败，该申请人在该时间段内已经存在申请！");
				return SUCCESS;
			}
			officeTeacherLeaveNh.setState(Constants.APPLY_STATE_NEED_AUDIT);
			if(StringUtils.isBlank(officeTeacherLeaveNh.getId())){
				officeTeacherLeaveNh.setCreateTime(new Date());
				officeTeacherLeaveNh.setCreateUserId(getLoginUser().getUserId());
				officeTeacherLeaveNh.setIsDeleted(false);
				officeTeacherLeaveNh.setUnitId(getUnitId());
			}
			officeTeacherLeaveNhService.startFlow(officeTeacherLeaveNh,getLoginUser().getUserId());
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("提交成功,已进入流程中");
		} catch (Exception e) {
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
	
	public String viewDetail(){
		officeTeacherLeaveNh = officeTeacherLeaveNhService.getOfficeTeacherLeaveNhById(teacherLeaveNhId);
		return SUCCESS;
	}
	
	public String infoPrint(){
		officeTeacherLeaveNh=officeTeacherLeaveNhService.getOfficeTeacherLeaveNhById(teacherLeaveNhId);
		User user=userService.getUser(officeTeacherLeaveNh.getApplyUserId());
		Teacher teacher=teacherService.getTeacher(user.getTeacherid());
		Dept dept=deptService.getDept(teacher.getDeptid());
		officeTeacherLeaveNh.setDeptName(dept.getDeptname());
		return SUCCESS;
	}
	
	public String leaveAudit(){
		return SUCCESS;
	}
	
	public String leaveAuditList(){
		if(auditStatus == null || auditStatus==0){
			teacherLeaveNhList = officeTeacherLeaveNhService.toDoAudit(getLoginUser().getUserId(), startTime, endTime, getPage());
		}else{
			teacherLeaveNhList = officeTeacherLeaveNhService.HaveDoAudit(getLoginUser().getUserId(), startTime, endTime, getPage());
		}
		return SUCCESS;
	}
	public String leaveAuditEdit(){
		try {
			officeTeacherLeaveNh = officeTeacherLeaveNhService.getOfficeTeacherLeaveNhById(teacherLeaveNhId);
			TaskHandlerSave taskHandlerSave = new TaskHandlerSave();
			TaskHandlerShow taskHandlerShow = taskHandlerService.getTaskHandlerShow(taskId, getLoginUser().getUserId());
			taskHandlerSave.setCurrentTask(taskHandlerShow.getCurrentTask());
			taskHandlerSave.setCurrentUserId(getLoginUser().getUserId());
			taskHandlerSave.setCurrentUnitId(getLoginUser().getUnitId());
			taskHandlerSave.setSubsystemId(FlowConstant.OFFICE_SUBSYSTEM);
			User user = getLoginInfo().getUser();
			Comment comment = new Comment();
			comment.setAssigneeType(AgileIdentityLinkType.PRINCIPAL);
			comment.setAssigneeId(user.getId());
			comment.setAssigneeName(user.getRealname());
			userName = user.getRealname();
			taskHandlerSave.setComment(comment);
			JSONObject json = new JSONObject().fromObject(taskHandlerSave);
			taskHandlerSaveJson = json.toString();
			officeTeacherLeaveNh.setTaskName(taskHandlerSave.getCurrentTask().getTaskName());
			currentStepId = taskHandlerSave.getCurrentTask().getTaskDefinitionKey();
			flowId = officeTeacherLeaveNh.getFlowId();
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
	
	public String saveTeacherLeaveNhAudit(){
		try {
			JSONObject object = new JSONObject().fromObject(taskHandlerSaveJson);
			TaskHandlerSave taskHandlerSave =(TaskHandlerSave) JSONObject.toBean(object,
				 TaskHandlerSave.class);
			taskHandlerSave.getComment().setOperateTime(new Date());
			boolean isSchool = false;
			if(getLoginInfo().getUnitClass().equals(Unit.UNIT_CLASS_SCHOOL)){
				isSchool = true;
			}
			officeTeacherLeaveNhService.saveAuditFlow(isPass(),taskHandlerSave,teacherLeaveNhId,isSchool);
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("审核成功");
		} catch (Exception e) {
			promptMessageDto.setOperateSuccess(false);
			e.printStackTrace();
			promptMessageDto.setErrorMessage("审核失败:"+e.getCause().getMessage());
		}
		return SUCCESS;
	}
	
	public String leaveTypeList(){
		leaveTypeList=officeLeaveTypeService.getOfficeLeaveTypeByUnitIdList(this.getUnitId(), Constants.TEACHER_LEAVE_TYPE);
		return SUCCESS;
	}
	
	public String addLeaveType(){
		if(StringUtils.isNotBlank(leaveTypeId)){
			officeLeaveType=officeLeaveTypeService.getOfficeLeaveTypeById(leaveTypeId);
		}else{
			officeLeaveType = new OfficeLeaveType();
		}
		return SUCCESS;
	}
	
	public String saveLeaveType(){
		if(StringUtils.isNotBlank(officeLeaveType.getId())){
			try {
				officeLeaveTypeService.update(officeLeaveType);
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("修改成功！");
			} catch (Exception e) {
				e.printStackTrace();
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setPromptMessage("修改失败！");
			}
		}else{
			try {
				officeLeaveType.setUnitId(this.getUnitId());
				officeLeaveType.setState(Constants.TEACHER_LEAVE_TYPE);
				officeLeaveType.setIsDeleted(0);
				officeLeaveTypeService.save(officeLeaveType);
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("添加成功！");
			} catch (Exception e) {
				e.printStackTrace();
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setPromptMessage("添加失败！");
			}
			
		}
		return SUCCESS;
	}
	
	public String deleteLeaveType(){
		try {
			officeLeaveTypeService.delete(new String[]{leaveTypeId});
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("删除失败！");
		}
		return SUCCESS;
	}
	
	public String leaveSearch(){
	
		return SUCCESS;
	}
	
	public String teacherLeaveList(){
		teacherLeaveNhList=officeTeacherLeaveNhService.getteacherLeaveListBySearchParams(this.getUnitId(), startTime, endTime, queryState, getPage());
		return SUCCESS;
	}
	
	public String leaveSummary(){
		Calendar calendar=Calendar.getInstance();
		endTime=calendar.getTime();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		startTime=calendar.getTime();
		return SUCCESS;
	}
	
	public String leaveSummaryList(){
		leaveTypeList=officeLeaveTypeService.getOfficeLeaveTypeByUnitIdList(this.getUnitId(), Constants.TEACHER_LEAVE_TYPE);
		List<String> leaveIds=new ArrayList<String>();
		for(OfficeLeaveType off:leaveTypeList){
			leaveIds.add(off.getId());
		}
		String[] userIds=officeTeacherLeaveNhService.getUserIdsByUnitId(this.getUnitId(), startTime, endTime,leaveIds.toArray(new String[]{})); 
		users=userService.getUsers(userIds);
		levMap=officeTeacherLeaveNhService.getSumMap(getUnitId(), startTime, endTime);
		return SUCCESS;
	}
	
	public String leaveSummaryExport(){
		leaveTypeList=officeLeaveTypeService.getOfficeLeaveTypeByUnitIdList(getUnitId(), Constants.TEACHER_LEAVE_TYPE);
		List<String> leaveIds=new ArrayList<String>();
		for(OfficeLeaveType off:leaveTypeList){
			leaveIds.add(off.getId());
		}
		String[] userIds=officeTeacherLeaveNhService.getUserIdsByUnitId(getUnitId(), startTime, endTime,leaveIds.toArray(new String[]{})); 
		users=userService.getUsers(userIds);
		levMap=officeTeacherLeaveNhService.getSumMap(getUnitId(), startTime, endTime);
		ZdExcel zdExcel=new ZdExcel();
		ZdStyle style=new ZdStyle(ZdStyle.BOLD|ZdStyle.BORDER);
		ZdStyle style2=new ZdStyle(ZdStyle.BORDER);
		zdExcel.add(new ZdCell("教师请假统计",leaveTypeList.size()+1,2,new ZdStyle(ZdStyle.BORDER|ZdStyle.ALIGN_CENTER|ZdStyle.WRAP_TEXT)));
		List<ZdCell> cellList=new ArrayList<ZdCell>();
		cellList.add(new ZdCell("姓名",1,style));
		for(OfficeLeaveType off:leaveTypeList){
			cellList.add(new ZdCell(off.getName(),1,style));
		}
		zdExcel.add(cellList.toArray(new ZdCell[0]));
		for(User user:users){
			ZdCell[] cells=new ZdCell[leaveTypeList.size()+1];
			cells[0]=new ZdCell(user.getRealname(),1,style2);
			for(int i=0;i<leaveTypeList.size();i++){
				if(StringUtils.isNotEmpty(levMap.get(user.getId()+"_"+leaveTypeList.get(i).getId()))){
					cells[i+1]=new ZdCell(levMap.get(user.getId()+"_"+leaveTypeList.get(i).getId())+"天",1,style2);
				}else{
					cells[i+1]=new ZdCell("0天",1,style2);
				}
			}
			zdExcel.add(cells);
		}
		Sheet sheet=zdExcel.createSheet("sheet1");
		for(int i=0;i<3;i++){
			zdExcel.setCellWidth(sheet, i, 2);
		}
		zdExcel.export("教师请假统计");
		return NONE;
	}
	
	public void setOfficeLeaveTypeService(
			OfficeLeaveTypeService officeLeaveTypeService) {
		this.officeLeaveTypeService = officeLeaveTypeService;
	}

	public String getLeaveTypeId() {
		return leaveTypeId;
	}

	public void setLeaveTypeId(String leaveTypeId) {
		this.leaveTypeId = leaveTypeId;
	}

	public OfficeLeaveType getOfficeLeaveType() {
		return officeLeaveType;
	}

	public void setOfficeLeaveType(OfficeLeaveType officeLeaveType) {
		this.officeLeaveType = officeLeaveType;
	}

	public List<OfficeLeaveType> getLeaveTypeList() {
		return leaveTypeList;
	}

	public void setLeaveTypeList(List<OfficeLeaveType> leaveTypeList) {
		this.leaveTypeList = leaveTypeList;
	}

	public void setBasicClassService(BasicClassService basicClassService) {
		this.basicClassService = basicClassService;
	}

	public List<Flow> getFlowList() {
		return flowList;
	}

	public void setFlowList(List<Flow> flowList) {
		this.flowList = flowList;
	}

	public void setFlowManageService(FlowManageService flowManageService) {
		this.flowManageService = flowManageService;
	}

	public String getTeacherLeaveNhId() {
		return teacherLeaveNhId;
	}

	public void setTeacherLeaveNhId(String teacherLeaveNhId) {
		this.teacherLeaveNhId = teacherLeaveNhId;
	}

	public OfficeTeacherLeaveNh getOfficeTeacherLeaveNh() {
		return officeTeacherLeaveNh;
	}

	public void setOfficeTeacherLeaveNh(OfficeTeacherLeaveNh officeTeacherLeaveNh) {
		this.officeTeacherLeaveNh = officeTeacherLeaveNh;
	}

	public void setOfficeTeacherLeaveNhService(
			OfficeTeacherLeaveNhService officeTeacherLeaveNhService) {
		this.officeTeacherLeaveNhService = officeTeacherLeaveNhService;
	}

	public List<OfficeTeacherLeaveNh> getTeacherLeaveNhList() {
		return teacherLeaveNhList;
	}

	public void setTeacherLeaveNhList(List<OfficeTeacherLeaveNh> teacherLeaveNhList) {
		this.teacherLeaveNhList = teacherLeaveNhList;
	}

	public String getQueryState() {
		return queryState;
	}

	public void setQueryState(String queryState) {
		this.queryState = queryState;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public boolean isPass() {
		return isPass;
	}

	public void setPass(boolean isPass) {
		this.isPass = isPass;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
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

	public String getTaskHandlerSaveJson() {
		return taskHandlerSaveJson;
	}

	public void setTaskHandlerSaveJson(String taskHandlerSaveJson) {
		this.taskHandlerSaveJson = taskHandlerSaveJson;
	}

	public void setTaskHandlerService(TaskHandlerService taskHandlerService) {
		this.taskHandlerService = taskHandlerService;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public List<OfficeLeaveType> getOfficeLeaveTypeList() {
		return officeLeaveTypeList;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public Map<String, String> getLevMap() {
		return levMap;
	}

	public String getNextDay() {
		return nextDay;
	}

	public void setNextDay(String nextDay) {
		this.nextDay = nextDay;
	}

	public String getApplyUserId() {
		return applyUserId;
	}

	public void setApplyUserId(String applyUserId) {
		this.applyUserId = applyUserId;
	}

	public String getFlowType() {
		return flowType;
	}

	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public boolean isCanAudit() {
		return canAudit;
	}

	public void setCanAudit(boolean canAudit) {
		this.canAudit = canAudit;
	}

	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	}

	public void setCustomRoleUserService(CustomRoleUserService customRoleUserService) {
		this.customRoleUserService = customRoleUserService;
	}
	
}
