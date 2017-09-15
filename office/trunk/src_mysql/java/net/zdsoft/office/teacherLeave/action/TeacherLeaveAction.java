package net.zdsoft.office.teacherLeave.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.McodedetailService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.storage.StorageFileUtils;
import net.zdsoft.eis.component.flowManage.constant.FlowConstant;
import net.zdsoft.eis.component.flowManage.entity.Flow;
import net.zdsoft.eis.component.flowManage.service.FlowManageService;
import net.zdsoft.eis.frame.action.PageAction;
import net.zdsoft.jbpm.activiti.engine.task.AgileIdentityLinkType;
import net.zdsoft.jbpm.core.entity.Comment;
import net.zdsoft.jbpm.core.entity.TaskHandlerSave;
import net.zdsoft.jbpm.core.entity.TaskHandlerShow;
import net.zdsoft.jbpm.core.service.TaskHandlerService;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.leadin.util.excel.ZdCell;
import net.zdsoft.leadin.util.excel.ZdExcel;
import net.zdsoft.leadin.util.excel.ZdStyle;
import net.zdsoft.office.teacherLeave.entity.OfficeTeacherLeave;
import net.zdsoft.office.teacherLeave.service.OfficeTeacherLeaveService;
import net.zdsoft.office.util.Constants;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;

public class TeacherLeaveAction extends PageAction{

	private static final long serialVersionUID = 409443973348389654L;
	
	private List<OfficeTeacherLeave> officeTeacherLeaveList;
	private OfficeTeacherLeaveService officeTeacherLeaveService;
	private DeptService deptService;
	private UserService userService;
	private OfficeTeacherLeave officeTeacherLeave;
	private FlowManageService flowManageService;
	private McodedetailService mcodedetailService;
	
	private int applyStatus;
	
	private String userId;
	private String userName;
	private String deptId;
	private Date startTime;
	private Date endTime;
	private String id;
	
	private List<Flow> flowList;
	private String taskId;
	private TaskHandlerService taskHandlerService;
	private String taskHandlerSaveJson;
	private boolean isPass;
	private String currentStepId;
	private String flowId;
	
	private int auditStatus;
	private String fromTab;
	
	private List<Mcodedetail> mcodedetails;
	private Map<String, String> sumMap;
	private List<User> users;
	
	public String execute() throws Exception {
		
		return SUCCESS;
	}
	
	public String applyList(){
		officeTeacherLeaveList = officeTeacherLeaveService.getApplyList(getLoginUser().getUserId(),getUnitId(),applyStatus,getPage());
		return SUCCESS;
	}
	
	public String applyTeacherLeave(){
		flowList = flowManageService.getFinishFlowList(getUnitId(), FlowConstant.FLOW_OWNER_UNIT, FlowConstant.OFFICE_LEAVE,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
		if(StringUtils.isNotEmpty(id)){
			officeTeacherLeave = officeTeacherLeaveService.getOfficeTeacherLeaveById(id);
			if(officeTeacherLeave==null){
				officeTeacherLeave = new OfficeTeacherLeave();
				User user = userService.getUser(getLoginUser().getUserId());
				officeTeacherLeave.setApplyUserId(user.getId());
				officeTeacherLeave.setUserName(user.getRealname());
				officeTeacherLeave.setUnitId(getUnitId());
				officeTeacherLeave.setDeptId(user.getDeptid());
			}
		}else{
			officeTeacherLeave = new OfficeTeacherLeave();
			User user = userService.getUser(getLoginUser().getUserId());
			officeTeacherLeave.setApplyUserId(user.getId());
			officeTeacherLeave.setUserName(user.getRealname());
			officeTeacherLeave.setUnitId(getUnitId());
			officeTeacherLeave.setDeptId(user.getDeptid());
			officeTeacherLeave.setLeaveBeignTime(new Date());//默认当天
			for(Flow item : flowList){
				if(item.isDefaultFlow()){
					officeTeacherLeave.setFlowId(item.getFlowId());
				}
			}
		}
		return SUCCESS;
	}
	
	public String saveTeacherLeave(){
		UploadFile file = null;
		try {
			file = StorageFileUtils.handleFile(new String[] {}, 5*1024);
			officeTeacherLeave.setApplyStatus(Constants.LEAVE_APPLY_SAVE);
			officeTeacherLeave.setCreateUserId(getLoginUser().getUserId());
			if(StringUtils.isNotEmpty(officeTeacherLeave.getId())){
				officeTeacherLeaveService.update(officeTeacherLeave,file);
			}else{
				officeTeacherLeaveService.add(officeTeacherLeave,file);
			}
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("保存成功");
		}catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("保存失败:"+e.getCause().getMessage());
		}
		return SUCCESS;
	}
	
	public String submitTeacherLeave(){
		UploadFile file = null;
		try {
			file = StorageFileUtils.handleFile(new String[] {}, 5*1024);
			officeTeacherLeave.setCreateUserId(getLoginUser().getUserId());
			officeTeacherLeaveService.startFlow(officeTeacherLeave,getLoginUser().getUserId(),file);
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
	/**
	 * 在最后一步审核结束前删除
	 * @return
	 * 2016年9月12日
	 */
	public String revokeLeave(){
		if(StringUtils.isNotEmpty(id)){
			try {
				officeTeacherLeaveService.deleteRevoke(id);
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("撤销成功");
			} catch (Exception e) {
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("撤销失败:"+e.getCause().getMessage());
			}
		}else{
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("撤销失败:找不到取消的对象");
		}
		
		return SUCCESS;
	}
	/**
	 * 审核通过的作废
	 * @return
	 * 2016年9月12日
	 */
	public String invalidLeave(){
		if(StringUtils.isNotEmpty(id)){
			try {
				officeTeacherLeaveService.deleteInvalid(id,getLoginInfo().getUser().getId());
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("作废成功");
			} catch (Exception e) {
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("作废失败:"+e.getCause().getMessage());
			}
		}else{
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("作废失败:找不到撤销的对象");
		}
		
		return SUCCESS;
	}
	public String deleteLeave(){
		if(StringUtils.isNotEmpty(id)){
			try {
				officeTeacherLeaveService.delete(new String[]{id});
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("删除成功");
			} catch (Exception e) 
			{
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("删除失败:"+e.getCause().getMessage());
			}
		}else{
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("删除失败:找不到删除的对象");
		}
		
		return SUCCESS;
	}
	
	public String auditList(){
		if(auditStatus==0){
			officeTeacherLeaveList=officeTeacherLeaveService.toDoAudit(getLoginUser().getUserId(),getPage());
		}else 
			if(auditStatus==2){//已作废
			officeTeacherLeaveList=officeTeacherLeaveService.HaveDoAudit(getLoginUser().getUserId(),true,getPage());
		}else{
			officeTeacherLeaveList=officeTeacherLeaveService.HaveDoAudit(getLoginUser().getUserId(),false,getPage());
		}
		return SUCCESS;
	}
	
	public String auditTeacherLeave(){
		officeTeacherLeave = officeTeacherLeaveService.getOfficeTeacherLeaveById(id);
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
		officeTeacherLeave.setTaskName(taskHandlerSave.getCurrentTask().getTaskName());
		currentStepId = taskHandlerSave.getCurrentTask().getTaskDefinitionKey();
		flowId = officeTeacherLeave.getFlowId();
		return SUCCESS;
	}
	
	public String auditPassLeave(){
		try {
			JSONObject object = new JSONObject().fromObject(taskHandlerSaveJson);
			TaskHandlerSave taskHandlerSave =(TaskHandlerSave) JSONObject.toBean(object,
				 TaskHandlerSave.class);
			taskHandlerSave.getComment().setOperateTime(new Date());
			officeTeacherLeaveService.passFlow(isPass(),taskHandlerSave,id);
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
	
	
	public String applyQueryList(){
		officeTeacherLeaveList=officeTeacherLeaveService.getQueryList(getUnitId(),userId,userName,deptId,startTime,endTime, getPage());
		return SUCCESS;
	}
	public String applyDetail(){
		officeTeacherLeave=officeTeacherLeaveService.getOfficeTeacherLeaveById(id);
		if(officeTeacherLeave==null){
			officeTeacherLeave=new OfficeTeacherLeave();
		}
		return SUCCESS;
	}
	
	public String applySummary(){
		Calendar c = Calendar.getInstance();
		endTime = c.getTime();
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		startTime = c.getTime();
		return SUCCESS;
	}
	public String applySummaryList(){
		mcodedetails = mcodedetailService.getMcodeDetails("DM-QJLX");
		String[] userIds = officeTeacherLeaveService.getApplyUserIds(getLoginInfo().getUnitID(),startTime,endTime,deptId);
		users = userService.getUsers(userIds);
		sumMap = officeTeacherLeaveService.getSumMap(getLoginInfo().getUnitID(),startTime,endTime,deptId);
		return SUCCESS;
	}
	
	public String applySummaryExport(){
		mcodedetails = mcodedetailService.getMcodeDetails("DM-QJLX");
		String[] userIds = officeTeacherLeaveService.getApplyUserIds(getLoginInfo().getUnitID(),startTime,endTime,deptId);
		users = userService.getUsers(userIds);
		sumMap = officeTeacherLeaveService.getSumMap(getLoginInfo().getUnitID(),startTime,endTime,deptId);
		ZdExcel zdExcel = new ZdExcel();
		ZdStyle style2 = new ZdStyle(ZdStyle.BOLD|ZdStyle.BORDER);
		ZdStyle style3 = new ZdStyle(ZdStyle.BORDER);
		zdExcel.add(new ZdCell("请假统计", mcodedetails.size()+1, 2, new ZdStyle(ZdStyle.BOLD|ZdStyle.ALIGN_CENTER|ZdStyle.WRAP_TEXT, 18)));
		
		List<ZdCell>zdlist=new ArrayList<ZdCell>();
		zdlist.add(new ZdCell("姓名",1,style2));
		for(Mcodedetail mcodedetail:mcodedetails){
			zdlist.add(new ZdCell(mcodedetail.getContent(),1,style2));
		}
		zdExcel.add(zdlist.toArray(new ZdCell[0]));
		
		for(User user:users){
			ZdCell[] cells = new ZdCell[mcodedetails.size()+1];
			cells[0] = new ZdCell(user.getRealname(), 1, style3);
			for(int i=0;i<mcodedetails.size();i++){
				if(StringUtils.isNotEmpty(sumMap.get(user.getId()+"_"+mcodedetails.get(i).getThisId()))){
					cells[i+1] = new ZdCell(sumMap.get(user.getId()+"_"+mcodedetails.get(i).getThisId())+"天", 1, style3);
				}else{
					cells[i+1] = new ZdCell("0天", 1, style3);
				}
			}
			zdExcel.add(cells);
		}
		
		Sheet sheet = zdExcel.createSheet("sheet1");
		for(int i=0;i<3;i++){
			zdExcel.setCellWidth(sheet, i, 2);
		}
		zdExcel.export("teacher_leave");
		return NONE;
	}
	/**
	 * 单位下教研组
	 * @return
	 */
	public List<Dept> getDirectDepts() {
		return deptService.getDepts(getUnitId());//getDirectDepts(getUnitId(),2,null);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public List<OfficeTeacherLeave> getOfficeTeacherLeaveList() {
		return officeTeacherLeaveList;
	}

	public void setOfficeTeacherLeaveList(
			List<OfficeTeacherLeave> officeTeacherLeaveList) {
		this.officeTeacherLeaveList = officeTeacherLeaveList;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
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

	public void setOfficeTeacherLeaveService(
			OfficeTeacherLeaveService officeTeacherLeaveService) {
		this.officeTeacherLeaveService = officeTeacherLeaveService;
	}

	public OfficeTeacherLeave getOfficeTeacherLeave() {
		return officeTeacherLeave;
	}

	public void setOfficeTeacherLeave(OfficeTeacherLeave officeTeacherLeave) {
		this.officeTeacherLeave = officeTeacherLeave;
	}
	

	public int getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(int applyStatus) {
		this.applyStatus = applyStatus;
	}

	public List<Flow> getFlowList() {
		return flowList;
	}

	public void setFlowList(List<Flow> flowList) {
		this.flowList = flowList;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setFlowManageService(FlowManageService flowManageService) {
		this.flowManageService = flowManageService;
	}

	public int getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskHandlerSaveJson() {
		return taskHandlerSaveJson;
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

	public void setTaskHandlerService(TaskHandlerService taskHandlerService) {
		this.taskHandlerService = taskHandlerService;
	}

	public String getCurrentStepId() {
		return currentStepId;
	}

	public void setCurrentStepId(String currentStepId) {
		this.currentStepId = currentStepId;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getFromTab() {
		return fromTab;
	}

	public void setFromTab(String fromTab) {
		this.fromTab = fromTab;
	}

	public List<Mcodedetail> getMcodedetails() {
		return mcodedetails;
	}

	public void setMcodedetails(List<Mcodedetail> mcodedetails) {
		this.mcodedetails = mcodedetails;
	}

	public Map<String, String> getSumMap() {
		return sumMap;
	}

	public void setSumMap(Map<String, String> sumMap) {
		this.sumMap = sumMap;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public void setMcodedetailService(McodedetailService mcodedetailService) {
		this.mcodedetailService = mcodedetailService;
	}
	
}