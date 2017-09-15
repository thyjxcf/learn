package net.zdsoft.office.dailyoffice.action;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.eis.base.attachment.service.AttachmentService;
import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.converter.service.ConverterFileTypeService;
import net.zdsoft.eis.base.storage.StorageFileUtils;
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
import net.zdsoft.keel.util.ServletUtils;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.leadin.common.entity.BusinessTask;
import net.zdsoft.leadin.doc.DocumentHandler;
import net.zdsoft.leadin.util.UUIDGenerator;
import net.zdsoft.leadin.util.excel.ZdCell;
import net.zdsoft.leadin.util.excel.ZdExcel;
import net.zdsoft.leadin.util.excel.ZdStyle;
import net.zdsoft.office.dailyoffice.entity.OfficeBusinessTrip;
import net.zdsoft.office.dailyoffice.service.OfficeBusinessTripService;
import net.zdsoft.office.officeFlow.service.OfficeFlowService;
import net.zdsoft.office.util.Constants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.tools.ant.filters.StringInputStream;

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
	
	private String jsonResult;
	private String id;
	private String businessTripId;
	private String taskDefinitionKey;
	private String processInstanceId;
	private String easyLevel;
	
	private String removeAttachmentId;
	private AttachmentService attachmentService;
	private ConverterFileTypeService converterFileTypeService;
	private String fileSize;
	
	private String reTaskId;
	private String taskKey;
	private String showReBackId;
	private boolean canBeRetract;
	private boolean canChangeNextTask;
	private OfficeFlowService officeFlowService;
	
	private String applyUserName;
	private Date startTime;
	private Date endTime;
	
	private boolean businessTripQuery=false;
	private CustomRoleService customRoleService;
	private CustomRoleUserService customRoleUserService;
	
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
		if(StringUtils.isNotBlank(officeBusinessTrip.getId())){
			
			officeBusinessTrip=officeBusinessTripService.getOfficeJtGooutByUnitIdAndUserId(officeBusinessTrip, getUnitId(), getLoginUser().getUserId());
			flowList=officeBusinessTrip.getFlows();
			
			if(CollectionUtils.isEmpty(flowList)){
				flowList=flowManageService.getFinishFlowList(getUnitId(), FlowConstant.FLOW_OWNER_UNIT, FlowConstant.OFFICE_BUSINESS_TRIP,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
				List<Flow> flowList2  = flowManageService.getFinishFlowList(getLoginInfo().getUser().getDeptid(), FlowConstant.FLOW_OWNER_STRING, 
						FlowConstant.OFFICE_BUSINESS_TRIP,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
				if(CollectionUtils.isNotEmpty(flowList2)){
					flowList.addAll(flowList2);
				}
			}
			
			officeBusinessTrip=officeBusinessTripService.getOfficeBusinessTripById(officeBusinessTrip.getId());
		}else{
			
			officeBusinessTrip=officeBusinessTripService.getOfficeJtGooutByUnitIdAndUserId(officeBusinessTrip, getUnitId(), getLoginUser().getUserId());
			flowList=officeBusinessTrip.getFlows();
			if(CollectionUtils.isEmpty(flowList)){
				flowList=flowManageService.getFinishFlowList(getUnitId(), FlowConstant.FLOW_OWNER_UNIT, FlowConstant.OFFICE_BUSINESS_TRIP,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
				List<Flow> flowList2  = flowManageService.getFinishFlowList(getLoginInfo().getUser().getDeptid(), FlowConstant.FLOW_OWNER_STRING, 
						FlowConstant.OFFICE_BUSINESS_TRIP,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
				if(CollectionUtils.isNotEmpty(flowList2)){
					flowList.addAll(flowList2);
				}
			}
			
			officeBusinessTrip.setBeginTime(new Date());//默认当天
			officeBusinessTrip.setUnitId(getUnitId());
			officeBusinessTrip.setId(UUIDGenerator.getUUID());
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
			//file = StorageFileUtils.handleFile(new String[] {}, Integer.parseInt(systemIniService.getValue(Constants.FILE_INIID))*1024);
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
				OfficeBusinessTrip office=officeBusinessTripService.getOfficeBusinessTripById(officeBusinessTrip.getId());
				if(office!=null){
					officeBusinessTripService.update(officeBusinessTrip,null,false, null);
				}else{
					officeBusinessTrip.setCreateTime(new Date());
					officeBusinessTrip.setApplyUserId(getLoginUser().getUserId());
					officeBusinessTrip.setIsDeleted(false);
					officeBusinessTrip.setUnitId(getUnitId());
					officeBusinessTripService.add(officeBusinessTrip,null,false);
				}
			}
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("保存成功");
			promptMessageDto.setErrorMessage(officeBusinessTrip.getId());
			System.out.println(officeBusinessTrip.getId());
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("保存失败:"+e.getMessage());
		}
		return SUCCESS;
	}
	public String myBusinessTripSubmit(){
		UploadFile file = null;
		try {
			//file = StorageFileUtils.handleFile(new String[] {}, Integer.parseInt(systemIniService.getValue(Constants.FILE_INIID))*1024);
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
			if(StringUtils.isNotBlank(officeBusinessTrip.getId())){
				OfficeBusinessTrip office=officeBusinessTripService.getOfficeBusinessTripById(officeBusinessTrip.getId());
				if(office==null){
					officeBusinessTrip.setCreateTime(new Date());
					officeBusinessTrip.setApplyUserId(getLoginUser().getUserId());
					officeBusinessTrip.setIsDeleted(false);
					officeBusinessTrip.setUnitId(getUnitId());
				}
			}
			officeBusinessTripService.startFlow(officeBusinessTrip,getLoginUser().getUserId(),null,false, null);
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
		}else if("3".equals(states)){//我已审核
			officeBusList = officeBusinessTripService.getOfficeBusinessTripByUnitIdList(getLoginInfo().getUnitID());
			officeBusList = officeFlowService.haveDoneAudit(officeBusList, getLoginUser().getUserId(), getPage());
			officeBusList = officeBusinessTripService.setInfo(officeBusList);
		}else if(org.apache.commons.lang3.StringUtils.equals("1", states)){
			officeBusList=officeBusinessTripService.HaveDoAudit(getLoginUser().getUserId(),true,getPage());
		}else{//已作废
			officeBusList=officeBusinessTripService.HaveDoAudit(getLoginUser().getUserId(), false, getPage());
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
		officeBusinessTrip.setApplyUserName(user.getRealname());
		taskHandlerSave.setComment(comment);
		JSONObject json = new JSONObject().fromObject(taskHandlerSave);
		taskHandlerSaveJson = json.toString();
		officeBusinessTrip.setTaskName(taskHandlerSave.getCurrentTask().getTaskName());
		currentStepId = taskHandlerSave.getCurrentTask().getTaskDefinitionKey();
		flowId = officeBusinessTrip.getFlowId();
		return SUCCESS;
	}
	
	public String revokeBusinessTrip(){//TODO
		try {
			officeBusinessTripService.revokeBusinessTrip(businessTripId);
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("撤销成功");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("撤销失败");
		}
		return SUCCESS;
	}
	public String invalidBusinessTrip(){
		try {
			officeBusinessTripService.invalidBusinessTrip(businessTripId, getLoginUser().getUserId());
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("作废成功");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("作废失败");
		}
		return SUCCESS;
	}
	public String businessTripPass(){
		try {
			JSONObject object = new JSONObject().fromObject(taskHandlerSaveJson);
			TaskHandlerSave taskHandlerSave =(TaskHandlerSave) JSONObject.toBean(object,
				 TaskHandlerSave.class);
			taskHandlerSave.getComment().setOperateTime(new Date());
			officeBusinessTripService.passFlow(isPass(),taskHandlerSave,officeBusinessTrip.getId(),currentStepId);
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
	
	public String changeFlow(){//TODO
		String value="success";
		try {
			if(org.apache.commons.lang3.StringUtils.isNotBlank(jsonResult)){
				officeBusinessTripService.changeFlow(businessTripId, getLoginUser().getUserId(), id, jsonResult);
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
		if(org.apache.commons.lang3.StringUtils.isNotBlank(businessTripId)){
			officeBusinessTrip=officeBusinessTripService.getOfficeBusinessTripById(businessTripId);
			List<TaskDescription> todoTasks = taskHandlerService.getTodoTasks(officeBusinessTrip.getFlowId());
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
	
	public String deleteFileAttach(){
		try {
			if(StringUtils.isNotBlank(removeAttachmentId)){
				attachmentService.deleteAttachments(new String[]{removeAttachmentId});
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("文件删除成功");
			}else{
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("文件删除出现问题,文件未找到或者已删除");
			}
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("文件删除出现问题,文件未找到或者已删除");
		}
		return SUCCESS;
	}
	public String saveFileAttach(){
		try{
			List<UploadFile> files=StorageFileUtils.handleFiles(new String[] {}, 0);//js已限制
			Attachment attachment=null;
			if(!CollectionUtils.isEmpty(files)){
				for (UploadFile uploadFile : files) {
					attachment=new Attachment();
					attachment.setFileName(uploadFile.getFileName());
					attachment.setContentType(uploadFile.getContentType());
					attachment.setFileSize(uploadFile.getFileSize());
					attachment.setUnitId(getUnitId());
					attachment.setObjectId(businessTripId);
					attachment.setObjectType(Constants.OFFICE_BUSINESS_TRIP_ATT);
					String fileExt = net.zdsoft.keel.util.FileUtils.getExtension(attachment.getFileName());
					if(converterFileTypeService.isVideo(fileExt)||converterFileTypeService.isDocument(fileExt)){
						attachment.setConStatus(BusinessTask.TASK_STATUS_NO_HAND);
					}
					if(converterFileTypeService.isPicture(fileExt)||converterFileTypeService.isAudio(fileExt)){
						attachment.setConStatus(BusinessTask.TASK_STATUS_SUCCESS);
					}
					attachmentService.saveAttachment(attachment, uploadFile, false);
				}
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("文件保存成功");
				promptMessageDto.setBusinessValue(attachment.getId()+"*"+attachment.getDownloadPath());
			}
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("文件保存出现问题");
		}
	
		return SUCCESS;
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
	
	public String businessTripQueryList(){//TODO
		officeBusList=officeBusinessTripService.getOfficeBusinessTripsByUnitIdAndOthers(getUnitId(), states, startTime, endTime, applyUserName, getPage());
		return SUCCESS;
	}
	
	public String businessTripExport(){
		officeBusList=officeBusinessTripService.getOfficeBusinessTripsByUnitIdAndOthers(getUnitId(), states, startTime, endTime, applyUserName, null);
		
		ZdExcel zdExcel = new ZdExcel();
		ZdStyle style2 = new ZdStyle(ZdStyle.BOLD|ZdStyle.BORDER);
		ZdStyle style3 = new ZdStyle(ZdStyle.BORDER|ZdStyle.ALIGN_CENTER|ZdStyle.WRAP_TEXT);
		
		List<ZdCell> zdcells=new ArrayList<ZdCell>();
		zdcells.add(new ZdCell("序号", 1, style2));
		zdcells.add(new ZdCell("申请人", 1, style2));
		zdcells.add(new ZdCell("部门（科室）", 1, style2));
		zdcells.add(new ZdCell("出差地点", 1, style2));
		zdcells.add(new ZdCell("开始时间", 1, style2));
		zdcells.add(new ZdCell("结束时间", 1, style2));
		zdcells.add(new ZdCell("出差天数", 1, style2));
		zdcells.add(new ZdCell("出差事由", 1, style2));
		zdcells.add(new ZdCell("审核状态", 1, style2));
		zdExcel.add(new ZdCell("教师出差查询", zdcells.size(), 2, new ZdStyle(ZdStyle.BOLD|ZdStyle.ALIGN_CENTER|ZdStyle.WRAP_TEXT, 18)));
		zdExcel.add(zdcells.toArray(new ZdCell[0]));
		
		int j=1;
		for (OfficeBusinessTrip officeBusinessTrip : officeBusList) {
			List<ZdCell> zdcellss=new ArrayList<ZdCell>();
			zdcellss.add(new ZdCell(j+"", 1, style3));
			zdcellss.add(new ZdCell(officeBusinessTrip.getApplyUserName(), 1, style3));
			zdcellss.add(new ZdCell(officeBusinessTrip.getDeptName(), 1, style3));
			zdcellss.add(new ZdCell(officeBusinessTrip.getPlace(), 1, style3));
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			String startTimeStr=sdf.format(officeBusinessTrip.getBeginTime());
			String endTimeStr=sdf.format(officeBusinessTrip.getEndTime());
			zdcellss.add(new ZdCell(startTimeStr, 1, style3));
			zdcellss.add(new ZdCell(endTimeStr, 1, style3));
			zdcellss.add(new ZdCell(String.valueOf(officeBusinessTrip.getDays()), 1, style3));
			zdcellss.add(new ZdCell(officeBusinessTrip.getTripReason(), 1, style3));
			if(org.apache.commons.lang3.StringUtils.equals("2", officeBusinessTrip.getState())){
				zdcellss.add(new ZdCell("待审核", 1, style3));
			}else if (org.apache.commons.lang3.StringUtils.equals("3", officeBusinessTrip.getState())) {
				zdcellss.add(new ZdCell("审核通过", 1, style3));
			}else{
				zdcellss.add(new ZdCell("审核不通过", 1, style3));
			}
			j++;
			zdExcel.add(zdcellss.toArray(new ZdCell[0]));
		}
		
		Sheet sheet = zdExcel.createSheet("sheet1");
		for(int i=0;i<3;i++){
			zdExcel.setCellWidth(sheet, i, 2);
		}
		zdExcel.export("business_Trip");
		return NONE;
	}
	public String businessTripDown(){
		try {
			officeBusinessTrip=officeBusinessTripService.getOfficeBusinessTripById(officeBusinessTrip.getId());
			Map<String,Object> dataMap=new HashMap<String, Object>();
			dataMap.put("officeBusinessTrip", officeBusinessTrip);
			dataMap.put("hisTaskList", officeBusinessTrip.getHisTaskList());
			
			String s=DocumentHandler.createDocString(dataMap, "officeBusinessTripTemp.xml");
			InputStream in=new StringInputStream(s, "utf-8");
			ServletUtils.download(in, getRequest(), getResponse(), "出差审批表.doc");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getJsonResult() {
		return jsonResult;
	}
	public void setJsonResult(String jsonResult) {
		this.jsonResult = jsonResult;
	}
	public String getBusinessTripId() {
		return businessTripId;
	}
	public void setBusinessTripId(String businessTripId) {
		this.businessTripId = businessTripId;
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
	public String getRemoveAttachmentId() {
		return removeAttachmentId;
	}
	public void setRemoveAttachmentId(String removeAttachmentId) {
		this.removeAttachmentId = removeAttachmentId;
	}
	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}
	public String getFileSize() {
		return systemIniService.getValue(Constants.FILE_INIID);
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public void setConverterFileTypeService(
			ConverterFileTypeService converterFileTypeService) {
		this.converterFileTypeService = converterFileTypeService;
	}
	
	public String getExtNames(){
		String[] extNames = converterFileTypeService.getAllExtNames();
		StringBuffer sb = new StringBuffer();
		if(extNames!=null){//rtf,doc,docx,xls,xlsx,csv,ppt,pptx,pdf,bmp,jpg,jpeg,png,gif
			for (String extName : extNames) {
				boolean contain=extName.contains("bmp")||extName.contains("jpg")||extName.contains("jpeg")||extName.contains("png")||extName.contains("gif");
				boolean contain2=extName.contains("doc")||extName.contains("docx")||extName.contains("xls")||extName.contains("xlsx")||extName.contains("pdf");
				boolean contain3=extName.contains("ppt")||extName.contains("pptx");
				if(contain||contain2||contain3){
					sb.append(extName+",");
				}
			}
		}
		return sb.toString();
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
					.checkRetract(officeBusinessTrip.getHisTaskList(), getLoginUser().getUserId(), officeBusinessTrip.getFlowId()));
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
	public boolean isCanChangeNextTask() {
		int num = officeFlowService.getTaskDescNum(getUnitId(), getLoginUser().getUserId(), taskId);
		return num>0;
	}
	public void setCanChangeNextTask(boolean canChangeNextTask) {
		this.canChangeNextTask = canChangeNextTask;
	}
public String getApplyUserName() {
		return applyUserName;
	}
	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
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
	public boolean isBusinessTripQuery() {
		CustomRole customRole=customRoleService.getCustomRoleByRoleCode(getUnitId(), "business_trip_query");
		if(customRole==null){
			return false;
		}
		List<CustomRoleUser> customRoleUsers=customRoleUserService.getCustomRoleUserListByUserId(getLoginUser().getUserId());
		if(CollectionUtils.isNotEmpty(customRoleUsers)){
			for (CustomRoleUser customRoleUser : customRoleUsers) {
				if(org.apache.commons.lang3.StringUtils.equals(customRole.getId(), customRoleUser.getRoleId())){
					return true;
				}
			}
		}
		return false;
	}
	public void setBusinessTripQuery(boolean businessTripQuery) {
		this.businessTripQuery = businessTripQuery;
	}
	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	}
	public void setCustomRoleUserService(CustomRoleUserService customRoleUserService) {
		this.customRoleUserService = customRoleUserService;
	}

	
}
