package net.zdsoft.office.attendLecture.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Grade;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.BasicClassService;
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
import net.zdsoft.jbpm.core.entity.TaskHandlerSave;
import net.zdsoft.jbpm.core.entity.TaskHandlerShow;
import net.zdsoft.jbpm.core.service.TaskHandlerService;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.leadin.util.excel.ZdCell;
import net.zdsoft.leadin.util.excel.ZdExcel;
import net.zdsoft.leadin.util.excel.ZdStyle;
import net.zdsoft.office.attendLecture.entity.OfficeAttendLecture;
import net.zdsoft.office.attendLecture.service.OfficeAttendLectureService;
import net.zdsoft.office.dailyoffice.entity.OfficeGoOut;
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
			}
			else{
				officeAttendLectureService.startFlow(officeAttendLecture, getLoginUser().getUserId());
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("提交成功,已进入流程中");
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
		officeAttendLectureList = officeAttendLectureService.getOfficeAttendLectureAuditList(null,getLoginUser().getUserId(),searchType,startTime,endTime, applyUserName, getPage());
		return SUCCESS;
	}
	
	public String attendLectureAuditEdit() {
		String taskId = officeAttendLecture.getTaskId();
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
			officeAttendLectureService.doAudit(isPass(), taskHandlerSave, officeAttendLecture.getId());
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
		officeAttendLectureList=officeAttendLectureService.getOfficeCountList(getUnitId(), deptId, startTime, endTime, applyUserName);
		return SUCCESS;
	}
	
	public void attendLectureCountExport(){
		String time=startTime==null||endTime==null?"":(DateUtils.date2String(startTime, "yyyy-MM-dd")+"至"+DateUtils.date2String(endTime, "yyyy-MM-dd"));
		if(StringUtils.isNotEmpty(deptId)){
	 		deptName=deptId;
			tableName=deptService.getDept(deptId).getDeptname()+time+"听课教师详情";
		}else{
			tableName=time+"听课教师详情";
		}
		attendLectureCountInfoExport();
		/*officeAttendLectureList=officeAttendLectureService.getOfficeCountList(getUnitId(), deptId, startTime, endTime, applyUserName);
		ZdExcel zdExcel = new ZdExcel();
		ZdStyle style2 = new ZdStyle(ZdStyle.BOLD|ZdStyle.BORDER);
		ZdStyle style3 = new ZdStyle(ZdStyle.BORDER);
		zdExcel.add(new ZdCell("听课统计", 3, 2, new ZdStyle(ZdStyle.BOLD|ZdStyle.ALIGN_CENTER|ZdStyle.WRAP_TEXT, 18)));
		
		List<ZdCell>zdlist=new ArrayList<ZdCell>();
		zdlist.add(new ZdCell("部门名",1,style2));
		zdlist.add(new ZdCell("教师数",1,style2));
		zdlist.add(new ZdCell("听课数",1,style2));
		zdExcel.add(zdlist.toArray(new ZdCell[0]));
		if(CollectionUtils.isNotEmpty(officeAttendLectureList)){
			for(OfficeAttendLecture office:officeAttendLectureList){
				ZdCell[] cells = new ZdCell[3];
				cells[0] = new ZdCell(office.getDeptName(), 1, style3);
				cells[1] = new ZdCell(String.valueOf(office.getTeacherNum()), 1, style3);
				cells[2] = new ZdCell(String.valueOf(office.getLectureNum()), 1, style3);
				zdExcel.add(cells);
			}
		}
		Sheet sheet = zdExcel.createSheet("sheet1");
		for(int i=0;i<6;i++){
			zdExcel.setCellWidth(sheet, i, 2);
		}
		zdExcel.export("听课统计");*/
	}
	public String attendLectureCountInfo(){
		officeAttendLectureList=officeAttendLectureService.getOfficeCountInfo(getUnitId(),startTime, endTime, deptName,applyUserName,getPage());
		if(StringUtils.isNotEmpty(deptName)){
			String time=startTime==null||endTime==null?"":(DateUtils.date2String(startTime, "yyyy-MM-dd")+"至"+DateUtils.date2String(endTime, "yyyy-MM-dd"));
			tableName=deptService.getDept(deptName).getDeptname()+time+"听课统计说明";
		}
		return SUCCESS;
	}
	public void attendLectureCountInfoExport(){
		String mcodeAttend="DM-TKSD";
	 	String mcodeAttendNum="DM-TKJC";
		officeAttendLectureList=officeAttendLectureService.getOfficeCountInfo(getUnitId(),startTime, endTime, deptName,applyUserName,null);
		/*if(StringUtils.isNotEmpty(deptId)){			
			tableName=deptService.getDept(deptId).getDeptname()+" "+DateUtils.date2String(startTime, "yyyy-MM-dd")+" 至 "
			+DateUtils.date2String(endTime, "yyyy-MM-dd")+"听课统计说明";
		}*/
		ZdExcel zdExcel = new ZdExcel();
		ZdStyle style2 = new ZdStyle(ZdStyle.BOLD|ZdStyle.BORDER);
		ZdStyle style3 = new ZdStyle(ZdStyle.BORDER);
		zdExcel.add(new ZdCell(tableName, 8, 2, new ZdStyle(ZdStyle.BOLD|ZdStyle.ALIGN_CENTER|ZdStyle.WRAP_TEXT, 18)));
		
		List<ZdCell>zdlist=new ArrayList<ZdCell>();
		zdlist.add(new ZdCell("听课教师",1,style2));
		zdlist.add(new ZdCell("听课时间",1,style2));
		zdlist.add(new ZdCell("课次",1,style2));
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
				ZdCell[] cells = new ZdCell[8];
				cells[0] = new ZdCell(office.getApplyUserName(), 1, style3);
				cells[1] = new ZdCell(DateUtils.date2String(office.getAttendDate(), "yyyy-MM-dd"), 1, style3);
				cells[2] = new ZdCell(mcode.get(office.getAttendPeriod())+mcodeNum.get(office.getAttendPeriodNum()), 1, style3);
				cells[3] = new ZdCell(office.getClassName(), 1, style3);
				cells[4] = new ZdCell(office.getSubjectName(), 1, style3);
				cells[5] = new ZdCell(office.getProjectName(), 1, style3);
				cells[6] = new ZdCell(office.getTeacherName(), 1, style3);
				cells[7] = new ZdCell(office.getProjectContent(), 1, style3);
				zdExcel.add(cells);
			}
		}
		Sheet sheet = zdExcel.createSheet("sheet1");
		for(int i=0;i<8;i++){
			zdExcel.setCellWidth(sheet, i, 2);
		}
		zdExcel.export(tableName);
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
		return flowManageService.getFinishFlowList(getUnitId(), FlowConstant.FLOW_OWNER_UNIT, FlowConstant.OFFICE_ATTEND_LECTURE,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
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
	
}
