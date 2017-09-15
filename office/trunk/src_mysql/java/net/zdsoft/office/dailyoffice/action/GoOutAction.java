package net.zdsoft.office.dailyoffice.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.DeptService;
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
import net.zdsoft.office.dailyoffice.entity.OfficeGoOut;
import net.zdsoft.office.dailyoffice.service.OfficeGoOutService;
import net.zdsoft.office.util.Constants;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;

/**
* @Package net.zdsoft.office.dailyoffice.action 
* @author songxq  
* @date 2016-5-20 下午2:59:47 
* @version V1.0
 */
@SuppressWarnings("serial")
public class GoOutAction extends PageAction{
	
	private OfficeGoOutService officeGoOutService;
	private FlowManageService flowManageService;
	private TaskHandlerService taskHandlerService;
	private UserService userService;
	
	private List<Flow> flowList;
	private String taskHandlerSaveJson;
	private String currentStepId;
	private String flowId;
	private String taskId;
	private boolean isPass;
	private Date startTime;
	private Date endTime;
	private String deptId;
	private DeptService deptService;
	
	private String states;
	private OfficeGoOut officeGoOut=new OfficeGoOut();
	private List<OfficeGoOut> officeGoOutList=new ArrayList<OfficeGoOut>();
	
	public String execute() throws Exception{
		return SUCCESS;
	}
	public String myGoOut(){
		return SUCCESS;
	}
	public String myGoOutList(){
		officeGoOutList=officeGoOutService.getOfficeGoOutByUnitIdUserIdPage(getUnitId(), getLoginUser().getUserId(), states, getPage());
		return SUCCESS;
	}
	public String myGoOutEdit(){
		flowList=flowManageService.getFinishFlowList(getUnitId(), FlowConstant.FLOW_OWNER_UNIT, FlowConstant.OFFICE_GO_OUT,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
		if(StringUtils.isNotBlank(officeGoOut.getId())){
			officeGoOut=officeGoOutService.getOfficeGoOutById(officeGoOut.getId());
		}else{
			officeGoOut.setBeginTime(new Date());//默认现在
			for(Flow item : flowList){
				if(item.isDefaultFlow()){
					officeGoOut.setFlowId(item.getFlowId());
				}
			}
		}
		return SUCCESS;
	}
	public String myGoOutSave(){
		UploadFile file = null;
		try {
			file = StorageFileUtils.handleFile(new String[] {}, 5*1024);
			if(officeGoOut.getBeginTime().compareTo(officeGoOut.getEndTime()) > 0){
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("开始时间与结束时间之间 前后时间不合逻辑，请更正！");
				return SUCCESS;
			}
			boolean flag = officeGoOutService.isExistConflict(officeGoOut.getId(),getLoginUser().getUserId(), officeGoOut.getBeginTime(), officeGoOut.getEndTime());
			if(flag) {
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("保存失败，该申请人在该时间段内已经存在申请！");
				return SUCCESS;
			}
			officeGoOut.setState(String.valueOf(Constants.APPLY_STATE_SAVE));
			if(StringUtils.isNotEmpty(officeGoOut.getId())){
				officeGoOutService.update(officeGoOut,file);
			}else{
				officeGoOut.setCreateTime(new Date());
				officeGoOut.setApplyUserId(getLoginUser().getUserId());
				officeGoOut.setIsDeleted(false);
				officeGoOut.setUnitId(getUnitId());
				officeGoOutService.add(officeGoOut,file);
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
	public String myGoOutSubmit(){
		UploadFile file = null;
		try {
			file = StorageFileUtils.handleFile(new String[] {}, 5*1024);
			if(officeGoOut.getBeginTime().compareTo(officeGoOut.getEndTime()) > 0){
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("开始时间与结束时间之间 前后时间不合逻辑，请更正！");
				return SUCCESS;
			}
			boolean flag = officeGoOutService.isExistConflict(officeGoOut.getId(),getLoginUser().getUserId(), officeGoOut.getBeginTime(), officeGoOut.getEndTime());
			if(flag) {
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("保存失败，该申请人在该时间段内已经存在申请！");
				return SUCCESS;
			}
			officeGoOut.setState(String.valueOf(Constants.APPLY_STATE_NEED_AUDIT));
			if(StringUtils.isBlank(officeGoOut.getId())){
				officeGoOut.setCreateTime(new Date());
				officeGoOut.setApplyUserId(getLoginUser().getUserId());
				officeGoOut.setIsDeleted(false);
				officeGoOut.setUnitId(getUnitId());
			}
			officeGoOutService.startFlow(officeGoOut,getLoginUser().getUserId(),file);
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
	public String myGoOutDelete(){
		if(StringUtils.isNotEmpty(officeGoOut.getId())){
			try {
				officeGoOutService.delete(new String[]{officeGoOut.getId()});
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
	//取消
	public String myGoOutRevoke(){
		if(StringUtils.isNotEmpty(officeGoOut.getId())){
			try {
				officeGoOutService.deleteRevoke(officeGoOut.getId());
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
	public String myGoOutView(){
		officeGoOut=officeGoOutService.getOfficeGoOutById(officeGoOut.getId());
		if(officeGoOut==null){
			officeGoOut=new OfficeGoOut();
		}
		return SUCCESS;
	}
	public String goOut(){
		return SUCCESS;
	}
	public String goOutList(){
		if("0".equals(states)){
			officeGoOutList=officeGoOutService.toDoAudit(getLoginUser().getUserId(),getPage());
		}else if("2".equals(states)){
			officeGoOutList=officeGoOutService.HaveDoAudit(getLoginUser().getUserId(),false,getPage());
		}else{
			officeGoOutList=officeGoOutService.HaveDoAudit(getLoginUser().getUserId(),true,getPage());
		}
		return SUCCESS;
	}
	public String goOutAudit(){
		officeGoOut = officeGoOutService.getOfficeGoOutById(officeGoOut.getId());
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
		officeGoOut.setUserName(user.getRealname());
		taskHandlerSave.setComment(comment);
		JSONObject json = new JSONObject().fromObject(taskHandlerSave);
		taskHandlerSaveJson = json.toString();
		officeGoOut.setTaskName(taskHandlerSave.getCurrentTask().getTaskName());
		currentStepId = taskHandlerSave.getCurrentTask().getTaskDefinitionKey();
		flowId = officeGoOut.getFlowId();
		return SUCCESS;
	}
	public String goOutPass(){
		try {
			JSONObject object = new JSONObject().fromObject(taskHandlerSaveJson);
			TaskHandlerSave taskHandlerSave =(TaskHandlerSave) JSONObject.toBean(object,
				 TaskHandlerSave.class);
			taskHandlerSave.getComment().setOperateTime(new Date());
			officeGoOutService.passFlow(isPass(),taskHandlerSave,officeGoOut.getId());
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
	public String goOutView(){
		officeGoOut=officeGoOutService.getOfficeGoOutById(officeGoOut.getId());
		if(officeGoOut==null){
			officeGoOut=new OfficeGoOut();
		}
		return SUCCESS;
	}
	/**
	 * 审核通过的作废
	 * @return
	 * 2016年9月12日
	 */
	public String invalidLeave(){
		if(StringUtils.isNotEmpty(officeGoOut.getId())){
			try {
				officeGoOutService.deleteInvalid(officeGoOut.getId(),getLoginInfo().getUser().getId());
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
	
	//外出统计
	public String goStatistics(){
		Calendar c = Calendar.getInstance();
		endTime = c.getTime();
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		startTime = c.getTime();
		return SUCCESS;
	}
	public Date getTMDate(Date time){
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		c.add(c.DATE, 1);
		return c.getTime();
	}
	public String goStatisticsList(){
		if(endTime!=null){
			endTime=getTMDate(endTime);
		}
		officeGoOutList=officeGoOutService.getStatistics(getUnitId(),startTime, endTime, deptId);
		return SUCCESS;
	}
	public String goStatisticsExport(){
		endTime=getTMDate(endTime);
		officeGoOutList=officeGoOutService.getStatistics(getUnitId(),startTime, endTime, deptId);
		ZdExcel zdExcel = new ZdExcel();
		ZdStyle style2 = new ZdStyle(ZdStyle.BOLD|ZdStyle.BORDER);
		ZdStyle style3 = new ZdStyle(ZdStyle.BORDER);
		zdExcel.add(new ZdCell("外出统计", 7, 2, new ZdStyle(ZdStyle.BOLD|ZdStyle.ALIGN_CENTER|ZdStyle.WRAP_TEXT, 18)));
		
		List<ZdCell>zdlist=new ArrayList<ZdCell>();
		zdlist.add(new ZdCell("姓名",1,2,style2));
		zdlist.add(new ZdCell("因公外出",2,1,style2));
		zdlist.add(new ZdCell("因私外出",2,1,style2));
		zdlist.add(new ZdCell("总计",2,1,style2));
		zdExcel.add(zdlist.toArray(new ZdCell[0]));
		
		ZdCell[] cells1 = new ZdCell[7];
		cells1[0] = new ZdCell();
		cells1[1] = new ZdCell("外出次数", 1, style3);
		cells1[2] = new ZdCell("外出时间(小时)", 1, style3);
		cells1[3] = new ZdCell("外出次数", 1, style3);
		cells1[4] = new ZdCell("外出时间(小时)", 1, style3);
		cells1[5] = new ZdCell("外出次数", 1, style3);
		cells1[6] = new ZdCell("外出时间(小时)", 1, style3);
		zdExcel.add(cells1);
		for(OfficeGoOut office:officeGoOutList){
				ZdCell[] cells = new ZdCell[7];
				
				cells[0] = new ZdCell(office.getUserName(), 1, style3);
				cells[1] = new ZdCell(String.valueOf(office.getNumJob()!=null?office.getNumJob():0), 1, style3);
				cells[2] = new ZdCell(String.valueOf(office.getHoursJob()!=null?office.getHoursJob():0), 1, style3);
				cells[3] = new ZdCell(String.valueOf(office.getNumSelf()!=null?office.getNumSelf():0), 1, style3);
				cells[4] = new ZdCell(String.valueOf(office.getHoursSelf()!=null?office.getHoursSelf():0), 1, style3);
				cells[5] = new ZdCell(String.valueOf(office.getOutNum()!=null?office.getOutNum():0), 1, style3);
				cells[6] = new ZdCell(String.valueOf(office.getSumHours()!=null?office.getSumHours():0), 1, style3);
				zdExcel.add(cells);
		}
		
		Sheet sheet = zdExcel.createSheet("sheet1");
		for(int i=0;i<7;i++){
			zdExcel.setCellWidth(sheet, i, 2);
		}
		zdExcel.export("go_out");
		return NONE;
	}
	/**
	 * 单位下教研组
	 * @return
	 */
	public List<Dept> getDirectDepts() {
		return deptService.getDepts(getUnitId());//getDirectDepts(getUnitId(),2,null);
	}
	
	public String getStates() {
		return states;
	}
	public void setStates(String states) {
		this.states = states;
	}
	public OfficeGoOut getOfficeGoOut() {
		return officeGoOut;
	}
	public void setOfficeGoOut(OfficeGoOut officeGoOut) {
		this.officeGoOut = officeGoOut;
	}
	public List<OfficeGoOut> getOfficeGoOutList() {
		return officeGoOutList;
	}
	public void setOfficeGoOutList(List<OfficeGoOut> officeGoOutList) {
		this.officeGoOutList = officeGoOutList;
	}
	public void setOfficeGoOutService(OfficeGoOutService officeGoOutService) {
		this.officeGoOutService = officeGoOutService;
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
	public String getFlowId() {
		return flowId;
	}
	public void setFlowId(String flowId) {
		this.flowId = flowId;
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
	public void setTaskHandlerService(TaskHandlerService taskHandlerService) {
		this.taskHandlerService = taskHandlerService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
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
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	
}
