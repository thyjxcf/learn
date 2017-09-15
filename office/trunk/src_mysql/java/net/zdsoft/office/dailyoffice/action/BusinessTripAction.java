package net.zdsoft.office.dailyoffice.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.User;
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
import net.zdsoft.office.dailyoffice.entity.OfficeBusinessTrip;
import net.zdsoft.office.dailyoffice.service.OfficeBusinessTripService;
import net.zdsoft.office.util.Constants;

import org.apache.commons.lang.StringUtils;

/**
* @Package net.zdsoft.office.dailyoffice.action 
* @author songxq  
* @date 2016-5-17 上午10:30:21 
* @version V1.0
 */
@SuppressWarnings("serial")
public class BusinessTripAction extends PageAction{
	
	private OfficeBusinessTripService officeBusinessTripService;
	private FlowManageService flowManageService;
	private TaskHandlerService taskHandlerService;
	private UserService userService;
	private OfficeBusinessTrip officeBusinessTrip=new OfficeBusinessTrip();
	private List<OfficeBusinessTrip> officeBusList=new ArrayList<OfficeBusinessTrip>();
	private List<Flow> flowList;
	private String states;
	private String fromTab;
	
	private String taskHandlerSaveJson;
	private String currentStepId;
	private String flowId;
	private String taskId;
	private boolean isPass;
	
	public String execute() throws Exception{
		return SUCCESS;
	}
	public String myBusinessTrip(){
		return SUCCESS;
	}
	
	public String myBusinessTripList(){
		officeBusList=officeBusinessTripService.getOfficeBusinessTripByUnitIdUserIdPage(getUnitId(), getLoginUser().getUserId(), states, getPage());
		return SUCCESS;
	}
	public String myBusinessTripEdit(){
		flowList=flowManageService.getFinishFlowList(getUnitId(), FlowConstant.FLOW_OWNER_UNIT, FlowConstant.OFFICE_BUSINESS_TRIP,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
		if(StringUtils.isNotBlank(officeBusinessTrip.getId())){
			officeBusinessTrip=officeBusinessTripService.getOfficeBusinessTripById(officeBusinessTrip.getId());
		}else{
			officeBusinessTrip.setBeginTime(new Date());//默认当天
			for(Flow item : flowList){
				if(item.isDefaultFlow()){
					officeBusinessTrip.setFlowId(item.getFlowId());
				}
			}
		}
		return SUCCESS;
	}
	public String myBusinessTripSave(){
		UploadFile file = null;
		try {
			file = StorageFileUtils.handleFile(new String[] {}, 5*1024);
			if(officeBusinessTrip.getBeginTime().compareTo(officeBusinessTrip.getEndTime()) > 0){
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("开始时间与结束时间之间 前后时间不合逻辑，请更正！");
				return SUCCESS;
			}
			boolean flag = officeBusinessTripService.isExistConflict(officeBusinessTrip.getId(),getLoginUser().getUserId(), officeBusinessTrip.getBeginTime(), officeBusinessTrip.getEndTime());
			if(flag) {
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("保存失败，该申请人在该时间段内已经存在申请！");
				return SUCCESS;
			}
			officeBusinessTrip.setState(String.valueOf(Constants.APPLY_STATE_SAVE));
			if(StringUtils.isNotEmpty(officeBusinessTrip.getId())){
				officeBusinessTripService.update(officeBusinessTrip,file);
			}else{
				officeBusinessTrip.setCreateTime(new Date());
				officeBusinessTrip.setApplyUserId(getLoginUser().getUserId());
				officeBusinessTrip.setIsDeleted(false);
				officeBusinessTrip.setUnitId(getUnitId());
				officeBusinessTripService.add(officeBusinessTrip,file);
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
	public String myBusinessTripSubmit(){
		UploadFile file = null;
		try {
			file = StorageFileUtils.handleFile(new String[] {}, 5*1024);
			if(officeBusinessTrip.getBeginTime().compareTo(officeBusinessTrip.getEndTime()) > 0){
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("开始时间与结束时间之间 前后时间不合逻辑，请更正！");
				return SUCCESS;
			}
			boolean flag = officeBusinessTripService.isExistConflict(officeBusinessTrip.getId(),getLoginUser().getUserId(), officeBusinessTrip.getBeginTime(), officeBusinessTrip.getEndTime());
			if(flag) {
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("保存失败，该申请人在该时间段内已经存在申请！");
				return SUCCESS;
			}
			officeBusinessTrip.setState(String.valueOf(Constants.APPLY_STATE_NEED_AUDIT));
			if(StringUtils.isBlank(officeBusinessTrip.getId())){
				officeBusinessTrip.setCreateTime(new Date());
				officeBusinessTrip.setApplyUserId(getLoginUser().getUserId());
				officeBusinessTrip.setIsDeleted(false);
				officeBusinessTrip.setUnitId(getUnitId());
			}
			officeBusinessTripService.startFlow(officeBusinessTrip,getLoginUser().getUserId(),file);
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
	public String myBusinessTripDelete(){
		if(StringUtils.isNotEmpty(officeBusinessTrip.getId())){
			try {
				officeBusinessTripService.delete(new String[]{officeBusinessTrip.getId()});
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
	public String myBusinessTripView(){
		officeBusinessTrip=officeBusinessTripService.getOfficeBusinessTripById(officeBusinessTrip.getId());
		if(officeBusinessTrip==null){
			officeBusinessTrip=new OfficeBusinessTrip();
		}
		return SUCCESS;
	}
	public String businessTrip(){
		return SUCCESS;
	}
	public String businessTripList(){
		if("0".equals(states)){
			officeBusList=officeBusinessTripService.toDoAudit(getLoginUser().getUserId(),getPage());
		}else{
			officeBusList=officeBusinessTripService.HaveDoAudit(getLoginUser().getUserId(),getPage());
		}
		return SUCCESS;
	}
	public String businessTripAudit(){
		officeBusinessTrip = officeBusinessTripService.getOfficeBusinessTripById(officeBusinessTrip.getId());
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
		officeBusinessTrip.setUserName(user.getRealname());
		taskHandlerSave.setComment(comment);
		JSONObject json = new JSONObject().fromObject(taskHandlerSave);
		taskHandlerSaveJson = json.toString();
		officeBusinessTrip.setTaskName(taskHandlerSave.getCurrentTask().getTaskName());
		currentStepId = taskHandlerSave.getCurrentTask().getTaskDefinitionKey();
		flowId = officeBusinessTrip.getFlowId();
		return SUCCESS;
	}
	public String businessTripPass(){
		try {
			JSONObject object = new JSONObject().fromObject(taskHandlerSaveJson);
			TaskHandlerSave taskHandlerSave =(TaskHandlerSave) JSONObject.toBean(object,
				 TaskHandlerSave.class);
			taskHandlerSave.getComment().setOperateTime(new Date());
			officeBusinessTripService.passFlow(isPass(),taskHandlerSave,officeBusinessTrip.getId());
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
	public String businessTripView(){
		officeBusinessTrip=officeBusinessTripService.getOfficeBusinessTripById(officeBusinessTrip.getId());
		if(officeBusinessTrip==null){
			officeBusinessTrip=new OfficeBusinessTrip();
		}
		return SUCCESS;
	}
	public void setOfficeBusinessTripService(
			OfficeBusinessTripService officeBusinessTripService) {
		this.officeBusinessTripService = officeBusinessTripService;
	}
	public OfficeBusinessTrip getOfficeBusinessTrip() {
		return officeBusinessTrip;
	}
	public void setOfficeBusinessTrip(OfficeBusinessTrip officeBusinessTrip) {
		this.officeBusinessTrip = officeBusinessTrip;
	}
	public List<OfficeBusinessTrip> getOfficeBusList() {
		return officeBusList;
	}
	public void setOfficeBusList(List<OfficeBusinessTrip> officeBusList) {
		this.officeBusList = officeBusList;
	}
	public String getStates() {
		return states;
	}
	public void setStates(String states) {
		this.states = states;
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
	public String getFromTab() {
		return fromTab;
	}
	public void setFromTab(String fromTab) {
		this.fromTab = fromTab;
	}
	public void setTaskHandlerService(TaskHandlerService taskHandlerService) {
		this.taskHandlerService = taskHandlerService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
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
	
}
