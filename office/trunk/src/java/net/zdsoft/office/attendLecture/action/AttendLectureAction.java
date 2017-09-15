package net.zdsoft.office.attendLecture.action;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Grade;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.BasicClassService;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.GradeService;
import net.zdsoft.eis.base.common.service.Mcode;
import net.zdsoft.eis.base.common.service.McodeService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.component.flowManage.constant.FlowConstant;
import net.zdsoft.eis.component.flowManage.entity.Flow;
import net.zdsoft.eis.component.flowManage.service.FlowManageService;
import net.zdsoft.eis.frame.action.PageAction;
import net.zdsoft.jbpm.activiti.engine.task.AgileIdentityLinkType;
import net.zdsoft.jbpm.core.entity.Comment;
import net.zdsoft.jbpm.core.entity.TaskDescription;
import net.zdsoft.jbpm.core.entity.TaskHandlerSave;
import net.zdsoft.jbpm.core.entity.TaskHandlerShow;
import net.zdsoft.jbpm.core.service.TaskHandlerService;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.ServletUtils;
import net.zdsoft.leadin.util.excel.ZdCell;
import net.zdsoft.leadin.util.excel.ZdExcel;
import net.zdsoft.leadin.util.excel.ZdStyle;
import net.zdsoft.office.attendLecture.entity.OfficeAttendLecture;
import net.zdsoft.office.attendLecture.service.OfficeAttendLectureService;
import net.zdsoft.office.officeFlow.service.OfficeFlowService;
import net.zdsoft.office.util.Constants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;

@SuppressWarnings("serial")
public class AttendLectureAction extends PageAction{
	
	private OfficeAttendLectureService officeAttendLectureService;
	private FlowManageService flowManageService;
	private GradeService gradeService;
	private BasicClassService basicClassService;
	private TaskHandlerService taskHandlerService;
	private UserService userService;
	private DeptService deptService;
	private McodeService mcodeService;
	private List<OfficeAttendLecture> officeAttendLectureList;
	private OfficeAttendLecture officeAttendLecture;
	private List<BasicClass> classList;
	private List<Dept> deptList;
	
	private String userName;
	private String taskHandlerSaveJson;
	private String currentStepId;
	private boolean viewOnly = false;
	private boolean isPass;
	private String id;
	private String searchType;
	private Date startTime;
	private Date endTime;
	private String applyUserName;
	private String deptId;
	private String deptName;
	private String noDefault;
	private String tableName;
	
	private Date minDate;
	private boolean unitViewRole;
	private boolean groupHead;
	private CustomRoleService customRoleService;
	private CustomRoleUserService customRoleUserService;
	
	private String reTaskId;
	private String taskKey;
	private String showReBackId;
	private boolean canBeRetract;
	private OfficeFlowService officeFlowService;
	
	private String attendLectureId;
	private String taskId;
	private String jsonResult;
	private String taskDefinitionKey;
	private String processInstanceId;
	private String easyLevel;
	private boolean canChangeNextTask;
	
	private int totalInNum;
	private int totalOutNum;
	private int totalNum;
	
	private boolean total;
	
	public String attendLectureAdmin(){
		return SUCCESS;
	}
	
	/**
	 * 我的听课
	 * @return
	 */
	public String attendLectureList(){
		officeAttendLectureList = officeAttendLectureService.getOfficeAttendLectureList(null,getLoginUser().getUserId(),searchType,startTime,endTime, getPage());
		return SUCCESS;
	}
	
	public String attendLectureEdit(){
		List<Flow> list = this.getFlowList();
		if(StringUtils.isNotBlank(id)){
			officeAttendLecture = officeAttendLectureService.getOfficeAttendLectureById(id);
		}else{
			officeAttendLecture = new OfficeAttendLecture();
			officeAttendLecture.setUnitId(getUnitId());
			officeAttendLecture.setApplyUserId(getLoginUser().getUserId());
			for(Flow item : list){
				if(item.isDefaultFlow()){
					officeAttendLecture.setFlowId(item.getFlowId());
				}
			}
		}
		return SUCCESS;
	}
	
	public String attendLectureSave(){
		try{
			if(String.valueOf(Constants.LEAVE_APPLY_SAVE).equals(officeAttendLecture.getState())){
				if(StringUtils.isNotBlank(officeAttendLecture.getId())){
					officeAttendLectureService.update(officeAttendLecture);
				}else{
					officeAttendLectureService.save(officeAttendLecture);
				}
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("保存成功");
				promptMessageDto.setErrorMessage(officeAttendLecture.getId());
			}
			else{
				minDate = this.getMinDate();
				if(officeAttendLecture.getAttendDate().before(minDate) 
						|| officeAttendLecture.getAttendDate().after(new Date())){
					promptMessageDto.setOperateSuccess(false);
					promptMessageDto.setErrorMessage("听课登记有效时间为一周内，请修改听课时间。");
				}
				else{
					officeAttendLectureService.startFlow(officeAttendLecture, getLoginUser().getUserId());
					promptMessageDto.setOperateSuccess(true);
					promptMessageDto.setPromptMessage("提交成功,已进入流程中");
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			if(String.valueOf(Constants.LEAVE_APPLY_SAVE).equals(officeAttendLecture.getState())){
				promptMessageDto.setErrorMessage("保存失败:"+e.getCause().getMessage());
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
	
	public String attendLectureDelete(){
		try {
			officeAttendLectureService.delete(new String[]{id});
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("删除成功");
		}catch (Exception e){
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("删除失败:"+e.getCause().getMessage());
		}
		return SUCCESS;
	}
	
	public String findClassList(){
		classList = basicClassService.getClassesByGrade(id);
		return SUCCESS;
	}
	
	/**
	 * 审核
	 * @return
	 */
	public String attendLectureAuditList(){
		officeAttendLectureList = officeAttendLectureService.getOfficeAttendLectureAuditList(getUnitId(),getLoginUser().getUserId(),searchType,startTime,endTime, applyUserName, getPage());
		return SUCCESS;
	}
	
	public String attendLectureAuditEdit() {
		officeAttendLecture = officeAttendLectureService.getOfficeAttendLectureById(officeAttendLecture.getId());
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
		officeAttendLecture.setTaskName(taskHandlerSave.getCurrentTask().getTaskName());
		currentStepId = taskHandlerSave.getCurrentTask().getTaskDefinitionKey();
		return SUCCESS;
	}
	
	public String attendLectureAuditSave(){
		try {
			JSONObject object = new JSONObject().fromObject(taskHandlerSaveJson);
			TaskHandlerSave taskHandlerSave =(TaskHandlerSave) JSONObject.toBean(object,
				 TaskHandlerSave.class);
			taskHandlerSave.getComment().setOperateTime(new Date());
			officeAttendLectureService.doAudit(isPass(), taskHandlerSave, officeAttendLecture.getId(), currentStepId);
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
	
	/**
	 * 统计
	 * @return
	 */
	public String attendLectureCountList(){
		deptList=deptService.getDepts(getUnitId());
		if(StringUtils.isEmpty(noDefault)){
			Calendar c = Calendar.getInstance();
			endTime = c.getTime();
			c.set(Calendar.DAY_OF_WEEK, c.getActualMinimum(Calendar.DAY_OF_WEEK));			
			startTime = c.getTime();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			try {
				startTime=sdf.parse(DateUtils.date2String(startTime, "yyyy-MM-dd"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			//startTime=DateUtils.getDate(Integer.parseInt(DateUtils.date2String(startTime,"yyyy")), 
					//Integer.parseInt(DateUtils.date2String(startTime,"MM")), Integer.parseInt(DateUtils.date2String(startTime,"dd")));
		}
		
		//TODO
		officeAttendLectureList=officeAttendLectureService.getOfficeCountList(getUnitId(), this.getUserIds(deptId, true), startTime, endTime, applyUserName);
		
		for (OfficeAttendLecture office : officeAttendLectureList) {
			if(office.getSchoolInNum()!=null){
				totalInNum=Integer.parseInt(office.getSchoolInNum()+"")+totalInNum;
				office.setLectureNum(office.getSchoolInNum());
			}
			if(office.getSchoolOutNum()!=null){
				totalOutNum=Integer.parseInt(office.getSchoolOutNum()+"")+totalOutNum;
				if(office.getLectureNum()!=null){
					office.setLectureNum(Integer.parseInt(office.getLectureNum()+"")+Integer.parseInt(office.getSchoolOutNum()+""));
				}else{
					office.setLectureNum(Integer.parseInt(office.getSchoolOutNum()+""));
				}
			}
		}
		totalNum=totalInNum+totalOutNum;
		
		return SUCCESS;
	}
	
	public String[] getUserIds(String deptid, boolean flag){
		List<User> users = new ArrayList<User>();
		if(!this.isUnitViewRole()){
			String groupId = deptService.isPrincipanGroupHead(getLoginInfo().getUser().getId());
			if(flag){
				if(StringUtils.isBlank(groupId)){//不是部门负责人
					users.add(getLoginInfo().getUser());
				}
				else{
					users = userService.getUsersByDeptId(groupId);
					if(flag && !StringUtils.equals(groupId, getLoginInfo().getUser().getDeptid())){//负责的部门非自己所在部门，查询人员再加自己
						users.add(getLoginInfo().getUser());
					}
				}
			}
			else{
				if(StringUtils.isNotBlank(groupId)&&StringUtils.equals(deptid, groupId)){
					users = userService.getUsersByDeptId(deptid);
				}
				else{
					users.add(getLoginInfo().getUser());
				}
			}
		}
		else{
			if(StringUtils.isBlank(deptid)){
				users = userService.getUsers(getLoginInfo().getUser().getUnitid());
			}
			else{
				users = userService.getUsersByDeptId(deptid);
			}
		}
		Set<String> uids = new HashSet<String>(); 
		for(User item : users){
			uids.add(item.getId());
		}
		
		if(CollectionUtils.isEmpty(users)){
			return new String[]{" "};
		}else{
			return uids.toArray(new String[0]);
		}
	}
	
	public void attendLectureCountExport(){
		String time=startTime==null||endTime==null?"":(DateUtils.date2String(startTime, "yyyy-MM-dd")+"至"+DateUtils.date2String(endTime, "yyyy-MM-dd"));
		if(total&&StringUtils.isNotEmpty(deptId)){
	 		deptName=deptId;
			tableName=deptService.getDept(deptId).getDeptname()+time+"听课教师详情";
		}else{
			tableName=time+"听课教师详情";
		}
		attendLectureCountInfoExport();
	}
	
	public String attendLectureCountInfo(){//TODO
		officeAttendLectureList=officeAttendLectureService.getOfficeCountInfo(getUnitId(),startTime, endTime,total?this.getUserIds(deptName, true):new String[]{deptName},applyUserName,getPage());
		String time=startTime==null||endTime==null?"":(DateUtils.date2String(startTime, "yyyy-MM-dd")+"至"+DateUtils.date2String(endTime, "yyyy-MM-dd"));
		if(total&&StringUtils.isNotEmpty(deptName)){
			tableName=deptService.getDept(deptName).getDeptname()+time+"听课统计说明";
		}else{
			tableName=time+"听课教师详情";
		}
		return SUCCESS;
	}
	public void attendLectureCountInfoExport(){
		String mcodeAttend="DM-TKSD";
	 	String mcodeAttendNum="DM-TKJC";
	 	String userId = "";
		if(!this.isUnitViewRole()){
			deptName = deptService.isPrincipanGroupHead(getLoginInfo().getUser().getId());
			if(StringUtils.isBlank(deptName)){//不是部门负责人
				deptName = getLoginInfo().getUser().getId();
			}
		}
		officeAttendLectureList=officeAttendLectureService.getOfficeCountInfo(getUnitId(),startTime, endTime,total?this.getUserIds(deptName, true):new String[]{deptName},applyUserName,null);
		/*if(StringUtils.isNotEmpty(deptId)){			
			tableName=deptService.getDept(deptId).getDeptname()+" "+DateUtils.date2String(startTime, "yyyy-MM-dd")+" 至 "
			+DateUtils.date2String(endTime, "yyyy-MM-dd")+"听课统计说明";
		}*/
		ZdExcel zdExcel = new ZdExcel();
		ZdStyle style2 = new ZdStyle(ZdStyle.BOLD|ZdStyle.BORDER);
		ZdStyle style3 = new ZdStyle(ZdStyle.BORDER);
		zdExcel.add(new ZdCell(tableName, 9, 2, new ZdStyle(ZdStyle.BOLD|ZdStyle.ALIGN_CENTER|ZdStyle.WRAP_TEXT, 18)));
		
		List<ZdCell>zdlist=new ArrayList<ZdCell>();
		zdlist.add(new ZdCell("听课教师",1,style2));
		zdlist.add(new ZdCell("听课时间",1,style2));
		zdlist.add(new ZdCell("课次",1,style2));
		zdlist.add(new ZdCell("类型",1,style2));
		zdlist.add(new ZdCell("班级",1,style2));
		zdlist.add(new ZdCell("学科",1,style2));
		zdlist.add(new ZdCell("课题",1,style2));
		zdlist.add(new ZdCell("授课教师",1,style2));
		zdlist.add(new ZdCell("授课内容",1,style2));
		zdExcel.add(zdlist.toArray(new ZdCell[0]));
		
		Mcode mcode=mcodeService.getMcode(mcodeAttend);
		Mcode mcodeNum=mcodeService.getMcode(mcodeAttendNum);
		if(CollectionUtils.isNotEmpty(officeAttendLectureList)){
			for(OfficeAttendLecture office:officeAttendLectureList){
				ZdCell[] cells = new ZdCell[9];
				cells[0] = new ZdCell(office.getApplyUserName(), 1, style3);
				cells[1] = new ZdCell(DateUtils.date2String(office.getAttendDate(), "yyyy-MM-dd"), 1, style3);
				cells[2] = new ZdCell(mcode.get(office.getAttendPeriod())+mcodeNum.get(office.getAttendPeriodNum()), 1, style3);
				if(org.apache.commons.lang3.StringUtils.equals("0", office.getType()+"")){
					cells[3] = new ZdCell("校内", 1, style3);
				}else{
					cells[3] = new ZdCell("校外", 1, style3);
				}
				cells[4] = new ZdCell(office.getClassName(), 1, style3);
				cells[5] = new ZdCell(office.getSubjectName(), 1, style3);
				cells[6] = new ZdCell(office.getProjectName(), 1, style3);
				cells[7] = new ZdCell(office.getTeacherName(), 1, style3);
				cells[8] = new ZdCell(office.getProjectContent(), 1, style3);
				zdExcel.add(cells);
			}
		}
		Sheet sheet = zdExcel.createSheet("sheet1");
		for(int i=0;i<8;i++){
			zdExcel.setCellWidth(sheet, i, 2);
		}
		zdExcel.export(tableName);
	}
	
	public String retractFlow(){
		try {
			officeFlowService.retractFlow(showReBackId, reTaskId, taskKey);
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("撤回成功");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("撤回失败:" + e.getMessage());
		}
		return SUCCESS;
	}
	
	public String changeFlow(){//TODO
		String value="success";
		try {
			if(StringUtils.isNotBlank(jsonResult)){
				officeAttendLectureService.changeFlow(attendLectureId, getLoginUser().getUserId(), id, jsonResult);
			}else{
				value="无法获取流程";
			}
		} catch (Exception e) {
			e.printStackTrace();
			if(e.getCause()!=null){
				value="流程启动出错"+e.getCause().getMessage();
			}else{
				value="流程启动出错"+e.getMessage();
			}
		}
		try {
			ServletUtils.print(getResponse(), value);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return NONE;
	}
	public String findCurrentstep(){
		if(StringUtils.isNotBlank(attendLectureId)){
			officeAttendLecture=officeAttendLectureService.getOfficeAttendLectureById(attendLectureId);
			List<TaskDescription> todoTasks = taskHandlerService.getTodoTasks(officeAttendLecture.getFlowId());
			if(todoTasks.size() > 0){
				for (TaskDescription item : todoTasks) {
					if(item.getTaskId().equals(taskId)){
						taskDefinitionKey = item.getTaskDefinitionKey();
						processInstanceId = item.getProcessInstanceId();
						easyLevel = "1";
					}
				}
			}
		}
		return SUCCESS;
	}
	public String changeCurrentstep(){
		String value = "success";
		if(StringUtils.isNotBlank(jsonResult)){
			try {
				JSONObject object = new JSONObject().fromObject(taskHandlerSaveJson);
				TaskHandlerSave taskHandlerSave =(TaskHandlerSave) JSONObject.toBean(object,
					 TaskHandlerSave.class);
				taskHandlerSave.setProcessDefinitionJson(jsonResult);
				List<TaskDescription> todoTasks = taskHandlerService.getTodoTasks(id);
				for (TaskDescription item : todoTasks) {
					if(item.getTaskDefinitionKey().equals(currentStepId)){
						taskHandlerSave.setCurrentTask(item);
						break;
					}
				}
				taskHandlerService.completeTask(taskHandlerSave,false);
			} catch (Exception e) {
				e.printStackTrace();
				if(e.getCause()!=null){
					value ="流程修改出错:"+e.getCause().getMessage();
				}else{
					value ="流程修改出错:"+e.getMessage();
				}
			}
		}else{
			value = "无法获取流程";
		}
		try {
			ServletUtils.print(getResponse(), value);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return NONE;
	}
	
	public String changeSchoolOut(){
		List<Flow> list = this.getFlowList();
		if(StringUtils.isNotBlank(id)){
			officeAttendLecture = officeAttendLectureService.getOfficeAttendLectureById(id);
		}else{
			officeAttendLecture = new OfficeAttendLecture();
			officeAttendLecture.setUnitId(getUnitId());
			officeAttendLecture.setApplyUserId(getLoginUser().getUserId());
			for(Flow item : list){
				if(item.isDefaultFlow()){
					officeAttendLecture.setFlowId(item.getFlowId());
				}
			}
		}
		return SUCCESS;
	}
	
	public String changeScoolIn(){
		List<Flow> list = this.getFlowList();
		if(StringUtils.isNotBlank(id)){
			officeAttendLecture = officeAttendLectureService.getOfficeAttendLectureById(id);
		}else{
			officeAttendLecture = new OfficeAttendLecture();
			officeAttendLecture.setUnitId(getUnitId());
			officeAttendLecture.setApplyUserId(getLoginUser().getUserId());
			for(Flow item : list){
				if(item.isDefaultFlow()){
					officeAttendLecture.setFlowId(item.getFlowId());
				}
			}
		}
		return SUCCESS;
	}
	
	public List<OfficeAttendLecture> getOfficeAttendLectureList() {
		return officeAttendLectureList;
	}

	public void setOfficeAttendLectureList(List<OfficeAttendLecture> officeAttendLectureList) {
		this.officeAttendLectureList = officeAttendLectureList;
	}

	public void setOfficeAttendLectureService(OfficeAttendLectureService officeAttendLectureService) {
		this.officeAttendLectureService = officeAttendLectureService;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public OfficeAttendLecture getOfficeAttendLecture() {
		return officeAttendLecture;
	}

	public void setOfficeAttendLecture(OfficeAttendLecture officeAttendLecture) {
		this.officeAttendLecture = officeAttendLecture;
	}

	public List<Flow> getFlowList() {
		List<Flow> list = flowManageService.getFinishFlowList(getUnitId(), FlowConstant.FLOW_OWNER_UNIT, FlowConstant.OFFICE_ATTEND_LECTURE,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
		List<Flow> flowList2  = flowManageService.getFinishFlowList(getLoginInfo().getUser().getDeptid(), FlowConstant.FLOW_OWNER_STRING, 
				FlowConstant.OFFICE_ATTEND_LECTURE,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
		if(CollectionUtils.isNotEmpty(flowList2)){
			list.addAll(flowList2);
		}
		return list;
	}

	public void setFlowManageService(FlowManageService flowManageService) {
		this.flowManageService = flowManageService;
	}

	public boolean isViewOnly() {
		return viewOnly;
	}

	public void setViewOnly(boolean viewOnly) {
		this.viewOnly = viewOnly;
	}

	public void setGradeService(GradeService gradeService) {
		this.gradeService = gradeService;
	}

	public void setBasicClassService(BasicClassService basicClassService) {
		this.basicClassService = basicClassService;
	}

	public List<Grade> getGradesList() {
		return gradeService.getGrades(getUnitId());
	}

	public List<BasicClass> getClassList() {
		return classList;
	}

	public void setClassList(List<BasicClass> classList) {
		this.classList = classList;
	}

	public String getApplyUserName() {
		return applyUserName;
	}

	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}

	public void setTaskHandlerService(TaskHandlerService taskHandlerService) {
		this.taskHandlerService = taskHandlerService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
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

	public List<Dept> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<Dept> deptList) {
		this.deptList = deptList;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public void setNoDefault(String noDefault) {
		this.noDefault = noDefault;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setMcodeService(McodeService mcodeService) {
		this.mcodeService = mcodeService;
	}

	public Date getMinDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cl = Calendar.getInstance();
		try {
			cl.setTime(sdf.parse(sdf.format(new Date())));
			cl.add(Calendar.DAY_OF_YEAR, 1);
			cl.add(Calendar.WEEK_OF_YEAR, -1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return cl.getTime();
	}

	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}

	public boolean isUnitViewRole() {
		CustomRole role = customRoleService.getCustomRoleByRoleCode(getUnitId(), "attend_lecture_view");
		if(role == null){
			return false;
		}
		List<CustomRoleUser> roleUs = customRoleUserService.getCustomRoleUserListByUserId(getLoginUser().getUserId());
		if(CollectionUtils.isNotEmpty(roleUs)){
			for(CustomRoleUser ru : roleUs){
				if(StringUtils.equals(ru.getRoleId(), role.getId())){
					return true;
				}
			}
		}
		return false;
	}

	public void setUnitViewRole(boolean unitViewRole) {
		this.unitViewRole = unitViewRole;
	}

	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	}

	public void setCustomRoleUserService(CustomRoleUserService customRoleUserService) {
		this.customRoleUserService = customRoleUserService;
	}

	public boolean isGroupHead() {
		String deptid = deptService.isPrincipanGroupHead(getLoginInfo().getUser().getId());
		return StringUtils.isNotBlank(deptid);
	}

	public void setGroupHead(boolean groupHead) {
		this.groupHead = groupHead;
	}

	public String getReTaskId() {
		return reTaskId;
	}

	public void setReTaskId(String reTaskId) {
		this.reTaskId = reTaskId;
	}

	public String getTaskKey() {
		return taskKey;
	}

	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}

	public String getShowReBackId() {
		return showReBackId;
	}

	public void setShowReBackId(String showReBackId) {
		this.showReBackId = showReBackId;
	}

	public boolean isCanBeRetract() {
		//撤回
		try{
			JSONObject json = JSONObject.fromObject(officeFlowService
					.checkRetract(officeAttendLecture.getHisTaskList(), getLoginUser().getUserId(), officeAttendLecture.getFlowId()));
			canBeRetract = json.getBoolean("result");
			if(canBeRetract){
				reTaskId = json.getString("reTaskId");
				taskKey = json.getString("taskKey");
				showReBackId = json.getString("showReBackId");
			}
		}catch (Exception e) {
			e.printStackTrace();
			canBeRetract = false;
		}
		return canBeRetract;
	}

	public void setCanBeRetract(boolean canBeRetract) {
		this.canBeRetract = canBeRetract;
	}

	public void setOfficeFlowService(OfficeFlowService officeFlowService) {
		this.officeFlowService = officeFlowService;
	}

	public String getAttendLectureId() {
		return attendLectureId;
	}

	public void setAttendLectureId(String attendLectureId) {
		this.attendLectureId = attendLectureId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getJsonResult() {
		return jsonResult;
	}

	public void setJsonResult(String jsonResult) {
		this.jsonResult = jsonResult;
	}

	public String getTaskDefinitionKey() {
		return taskDefinitionKey;
	}

	public void setTaskDefinitionKey(String taskDefinitionKey) {
		this.taskDefinitionKey = taskDefinitionKey;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getEasyLevel() {
		return easyLevel;
	}

	public void setEasyLevel(String easyLevel) {
		this.easyLevel = easyLevel;
	}
	public boolean isCanChangeNextTask() {
		int num = officeFlowService.getTaskDescNum(getUnitId(), getLoginUser().getUserId(), taskId);
		return num>0;
	}
	public void setCanChangeNextTask(boolean canChangeNextTask) {
		this.canChangeNextTask = canChangeNextTask;
	}

	public int getTotalInNum() {
		return totalInNum;
	}

	public void setTotalInNum(int totalInNum) {
		this.totalInNum = totalInNum;
	}

	public int getTotalOutNum() {
		return totalOutNum;
	}

	public void setTotalOutNum(int totalOutNum) {
		this.totalOutNum = totalOutNum;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public boolean isTotal() {
		return total;
	}

	public void setTotal(boolean isTotal) {
		this.total = isTotal;
	}
}
