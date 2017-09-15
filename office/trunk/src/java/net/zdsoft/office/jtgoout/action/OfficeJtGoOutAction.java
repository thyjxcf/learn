package net.zdsoft.office.jtgoout.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;
import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.eis.base.attachment.service.AttachmentService;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.UnitService;
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
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keel.util.ServletUtils;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.leadin.common.entity.BusinessTask;
import net.zdsoft.leadin.util.UUIDGenerator;
import net.zdsoft.office.jtgoout.entity.GooutStudentEx;
import net.zdsoft.office.jtgoout.entity.GooutTeacherEx;
import net.zdsoft.office.jtgoout.entity.OfficeJtGoout;
import net.zdsoft.office.jtgoout.service.GooutStudentExService;
import net.zdsoft.office.jtgoout.service.GooutTeacherExService;
import net.zdsoft.office.jtgoout.service.OfficeJtGooutService;
import net.zdsoft.office.officeFlow.service.OfficeFlowService;
import net.zdsoft.office.util.Constants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 集体外出管理
* @Package net.zdsoft.office.jtgoout.action 
* @author songxq  
* @date 2017-2-17 下午4:20:59 
* @version V1.0
 */

@SuppressWarnings("serial")
public class OfficeJtGoOutAction extends PageAction{
	
	private OfficeJtGooutService officeJtGooutService;
	private FlowManageService flowManageService;
	private AttachmentService attachmentService;
	private GooutStudentExService gooutStudentExService;
	private GooutTeacherExService gooutTeacherExService;
	private TaskHandlerService taskHandlerService;
	private UserService userService;
	private UnitService unitService;
	private ConverterFileTypeService converterFileTypeService;
	
	private String applyStatus;//状态
	private String removeAttachmentId;//附件id
	private String jtGoOutId;//外出id
	private String taskId;//任务id
	
	//审核
	private String userName;
	private String taskHandlerSaveJson;
	private String currentStepId;
	private String flowId;
	private boolean isPass;
	private String jsonResult;
	private String textComment;
	
	//查询
	private String startTime;
	private String endTime;
	private String type;
	private String unitName;
	
	//流程
	private String taskDefinitionKey;
	private String processInstanceId;
	private String easyLevel;
	private String id;//流程
	
	private String fromTab;
	private String fileSize;
	
	private List<OfficeJtGoout> officeJtGoOutList=new ArrayList<OfficeJtGoout>();
	private OfficeJtGoout officeJtGoout=new OfficeJtGoout();
	private GooutStudentEx gooutStudentEx=new GooutStudentEx();
	private GooutTeacherEx gooutTeacherEx=new GooutTeacherEx();
	private List<Flow> flowList=new ArrayList<Flow>();
	
	private String reTaskId;
	private String taskKey;
	private String showReBackId;
	private boolean canBeRetract;
	private boolean canChangeNextTask;
	private OfficeFlowService officeFlowService;
	
	public String execute(){
		return SUCCESS;
	}
	public String jtGoOutAdmin(){
		return SUCCESS;
	}
	public String jtGoOutList(){
		officeJtGoOutList=officeJtGooutService.getOfficeJtGooutByUnitIdAndState(getUnitId(), getLoginUser().getUserId(), applyStatus, getPage());
		return SUCCESS;
	}
	public String jtGoOutEdit(){
		
		if(StringUtils.isNotBlank(officeJtGoout.getId())){
			officeJtGoout=officeJtGooutService.getOfficeJtGooutById(officeJtGoout.getId());
			officeJtGoout=officeJtGooutService.getOfficeJtGooutByUnitIdAndUserId(officeJtGoout, getUnitId(), getLoginUser().getUserId());
			flowList=officeJtGoout.getFlows();
			
			if(CollectionUtils.isEmpty(flowList)){
				flowList=flowManageService.getFinishFlowList(getUnitId(), FlowConstant.FLOW_OWNER_UNIT, FlowConstant.OFFICE_JT_GOOUT, FlowConstant.OFFICE_SUBSYSTEM, FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
				List<Flow> flowList2  = flowManageService.getFinishFlowList(getLoginInfo().getUser().getDeptid(), FlowConstant.FLOW_OWNER_STRING, 
						FlowConstant.OFFICE_JT_GOOUT,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
				if(CollectionUtils.isNotEmpty(flowList2)){
					flowList.addAll(flowList2);
				}
			}
			
			if(officeJtGoout!=null&&StringUtils.equals("1", officeJtGoout.getType())){
				gooutStudentEx=gooutStudentExService.getGooutStudentExByJtGoOutId(officeJtGoout.getId());
			}
			if(officeJtGoout!=null&&StringUtils.equals("2", officeJtGoout.getType())){
				gooutTeacherEx=gooutTeacherExService.getGooutTeacherExByJtGooutId(officeJtGoout.getId());
			}
		}else{
			officeJtGoout=officeJtGooutService.getOfficeJtGooutByUnitIdAndUserId(officeJtGoout, getUnitId(), getLoginUser().getUserId());
			flowList=officeJtGoout.getFlows();
			
			if(CollectionUtils.isEmpty(flowList)){
				flowList=flowManageService.getFinishFlowList(getUnitId(), FlowConstant.FLOW_OWNER_UNIT, FlowConstant.OFFICE_JT_GOOUT, FlowConstant.OFFICE_SUBSYSTEM, FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
				List<Flow> flowList2  = flowManageService.getFinishFlowList(getLoginInfo().getUser().getDeptid(), FlowConstant.FLOW_OWNER_STRING, 
						FlowConstant.OFFICE_JT_GOOUT,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
				if(CollectionUtils.isNotEmpty(flowList2)){
					flowList.addAll(flowList2);
				}
			}
			
			officeJtGoout.setUnitId(getUnitId());
			officeJtGoout.setType("1");
			officeJtGoout.setStartTime(new Date());
			
			if(StringUtils.isBlank(officeJtGoout.getFlowId())){
				for(Flow item : flowList){
					if(item.isDefaultFlow()){
						officeJtGoout.setFlowId(item.getFlowId());
					}
				}
			}
		}
		if(StringUtils.isBlank(officeJtGoout.getId())){
			officeJtGoout.setId(UUIDGenerator.getUUID());
		}
		return SUCCESS;
	}
	public String jtGoOutSave(){
		try {
			officeJtGoout.setState(String.valueOf(Constants.APPLY_STATE_SAVE));
			OfficeJtGoout officeJtGooutOld=officeJtGooutService.getOfficeJtGooutById(officeJtGoout.getId());
			if(officeJtGooutOld!=null&&StringUtils.isNotBlank(officeJtGooutOld.getId())){
				officeJtGooutOld.setStartTime(officeJtGoout.getStartTime());
				officeJtGooutOld.setEndTime(officeJtGoout.getEndTime());
				officeJtGooutOld.setType(officeJtGoout.getType());
				officeJtGooutOld.setState(String.valueOf(Constants.APPLY_STATE_SAVE));
				officeJtGooutOld.setFlowId(officeJtGoout.getFlowId());
				officeJtGooutService.update(officeJtGooutOld, gooutStudentEx, gooutTeacherEx,null,null);
			}else{
				officeJtGoout.setApplyUserId(getLoginUser().getUserId());
				officeJtGoout.setCreateTime(new Date());
				officeJtGoout.setUnitId(getUnitId());
				officeJtGoout.setIsDeleted(false);
				officeJtGooutService.save(officeJtGoout, gooutStudentEx, gooutTeacherEx,null);
			}
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("操作成功");
			promptMessageDto.setErrorMessage(officeJtGoout.getId());
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("操作失败");
		}
		return SUCCESS;
	}
	public String jtGoOutSubmit(){
		try {
			officeJtGoout.setState(String.valueOf(Constants.APPLY_STATE_NEED_AUDIT));
			OfficeJtGoout officeJtGooutOld=officeJtGooutService.getOfficeJtGooutById(officeJtGoout.getId());
			if(officeJtGooutOld==null){
				officeJtGoout.setApplyUserId(getLoginUser().getUserId());
				officeJtGoout.setCreateTime(new Date());
				officeJtGoout.setIsDeleted(false);
				officeJtGoout.setUnitId(getUnitId());
			}
			officeJtGooutService.startFlow(officeJtGoout, gooutStudentEx, gooutTeacherEx, getLoginUser().getUserId(),null,null,getPage());
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("提交成功,已进入流程中");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("提交失败:"+e.getCause().getMessage());
		}
		return SUCCESS;
	}
	public String jtGoOutDelete(){
		try {
			if(StringUtils.isNotBlank(jtGoOutId)){
				officeJtGooutService.delete(new String[]{jtGoOutId});
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("删除成功");
			}else{
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("删除失败：找不到Id");
			}
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("删除失败");
		}
		return SUCCESS;
	}
	public String jtGoOutRevoke(){
		if(StringUtils.isNotEmpty(officeJtGoout.getId())){
			try {
				officeJtGooutService.deleteRevoke(officeJtGoout.getId());
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
	public String jtGoOutView(){
		officeJtGoout=officeJtGooutService.getOfficeJtGooutById(jtGoOutId);
		if(officeJtGoout!=null&&StringUtils.equals("1", officeJtGoout.getType())){
			gooutStudentEx=gooutStudentExService.getGooutStudentExByJtGoOutId(jtGoOutId);
		}
		if(officeJtGoout!=null&&StringUtils.equals("2", officeJtGoout.getType())){
			gooutTeacherEx=gooutTeacherExService.getGooutTeacherExByJtGooutId(jtGoOutId);
		}
		
		//撤回
		try{
			JSONObject json = JSONObject.fromObject(officeFlowService
					.checkRetract(officeJtGoout.getHisTaskList(), getLoginUser().getUserId(), officeJtGoout.getFlowId()));
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
		return SUCCESS;
	}
	public String jtGoOutAuditAdmin(){
		return SUCCESS;
	}
	public String jtGoOutAuditList(){
		if(StringUtils.isBlank(applyStatus)){
			officeJtGoOutList=officeJtGooutService.toDoAudit(getLoginUser().getUserId(),getPage());
		}else if(StringUtils.equals("1", applyStatus)){
			officeJtGoOutList=officeJtGooutService.myAuditList(getLoginUser().getUserId(), getUnitId(), null);
		}else if(StringUtils.equals("2", applyStatus)){
			officeJtGoOutList=officeJtGooutService.HaveDoneAudit(getLoginUser().getUserId(), false, null);
		}else{
			officeJtGoOutList=officeJtGooutService.HaveDoneAudit(getLoginUser().getUserId(), true, null);
		}
		
		Pagination page = getPage();
		Integer maxRow = officeJtGoOutList.size();
		page.setMaxRowCount(maxRow);
		page.initialize();
		if(CollectionUtils.isNotEmpty(officeJtGoOutList)){
			Integer oldCur = page.getCurRowNum();
			Integer newCur = page.getPageIndex()*page.getPageSize();
			newCur = newCur>maxRow?maxRow:newCur;
			List<OfficeJtGoout> newList = new ArrayList<OfficeJtGoout>();
			
			newList.addAll(officeJtGoOutList.subList(oldCur-1, newCur));
			officeJtGoOutList = newList;
		}
		return SUCCESS;
	}
	public String jtGoOutAuditEdit(){
		officeJtGoout=officeJtGooutService.getOfficeJtGooutById(jtGoOutId);
		if(officeJtGoout!=null&&StringUtils.equals("1", officeJtGoout.getType())){
			gooutStudentEx=gooutStudentExService.getGooutStudentExByJtGoOutId(jtGoOutId);
		}
		if(officeJtGoout!=null&&StringUtils.equals("2", officeJtGoout.getType())){
			gooutTeacherEx=gooutTeacherExService.getGooutTeacherExByJtGooutId(jtGoOutId);
		}
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
		userName=(user.getRealname());
		textComment="同意";
		taskHandlerSave.setComment(comment);
		JSONObject json = new JSONObject().fromObject(taskHandlerSave);
		taskHandlerSaveJson=json.toString();
		officeJtGoout.setTaskName(taskHandlerSave.getCurrentTask().getTaskName());
		currentStepId=(taskHandlerSave.getCurrentTask().getTaskDefinitionKey());
		flowId=(officeJtGoout.getFlowId());
		return SUCCESS;
	}
	public String auditPassJtGoOut(){
		try {
			JSONObject object = new JSONObject().fromObject(taskHandlerSaveJson);
			TaskHandlerSave taskHandlerSave =(TaskHandlerSave) JSONObject.toBean(object,
				 TaskHandlerSave.class);
			taskHandlerSave.getComment().setOperateTime(new Date());
			officeJtGooutService.passFlow(isPass(),taskHandlerSave,jtGoOutId,currentStepId);
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
	//审核通过的作废
	public String invalidJtGoOut(){
		if(StringUtils.isNotEmpty(jtGoOutId)){
			try {
				officeJtGooutService.deleteInvalid(jtGoOutId,getLoginInfo().getUser().getId());
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
	//打印
	public String printJtGoOut(){
		officeJtGoout=officeJtGooutService.getOfficeJtGooutById(jtGoOutId);
		if(officeJtGoout!=null){
			if(CollectionUtils.isNotEmpty(officeJtGoout.getAttachments())){
				List<String> fileNames=new ArrayList<String>();
				for (Attachment attach : officeJtGoout.getAttachments()) {
					fileNames.add(attach.getFileName());
				}
				officeJtGoout.setFileNames(fileNames);
				officeJtGoout.setAttachments(null);
			}
			if(officeJtGoout.getStartTime()!=null){
				officeJtGoout.setStartTimeStr(DateUtils.date2String(officeJtGoout.getStartTime(), "yyyy-MM-dd"));
			}
			if(officeJtGoout.getEndTime()!=null){
				officeJtGoout.setEndTimeStr(DateUtils.date2String(officeJtGoout.getEndTime(), "yyyy-MM-dd"));
			}
			if(StringUtils.isNotBlank(officeJtGoout.getType())&&StringUtils.equals("1", officeJtGoout.getType())){
				officeJtGoout.setTypeName("学生集体活动");
				gooutStudentEx=gooutStudentExService.getGooutStudentExByJtGoOutId(jtGoOutId);
				if(gooutStudentEx!=null){
					if(gooutStudentEx.getIsDrivinglicence()){
						gooutStudentEx.setDrivinglicence("是");
					}else{
						gooutStudentEx.setDrivinglicence("否");
					}
					if(gooutStudentEx.getIsOrganization()){
						gooutStudentEx.setOrganization("是");
					}else{
						gooutStudentEx.setOrganization("否");
					}
					if(gooutStudentEx.getIsInsurance()){
						gooutStudentEx.setInsurance("已买");
					}else{
						gooutStudentEx.setInsurance("未买");
					}
					if(gooutStudentEx.getActivityIdeal()){
						gooutStudentEx.setActivity("有");
					}else{
						gooutStudentEx.setActivity("无");
					}
					if(gooutStudentEx.getSaftIdeal()){
						gooutStudentEx.setSaft("有");
					}else{
						gooutStudentEx.setSaft("无");
					}
				}
			}
			if(StringUtils.isNotBlank(officeJtGoout.getType())&&StringUtils.equals("2", officeJtGoout.getType())){
				officeJtGoout.setTypeName("教师集体培训");
				gooutTeacherEx=gooutTeacherExService.getGooutTeacherExByJtGooutId(jtGoOutId);
			}
		}
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("officeJtGoout", officeJtGoout);
		if(officeJtGoout!=null&&StringUtils.isNotBlank(officeJtGoout.getType())&&StringUtils.equals("1", officeJtGoout.getType())){
			jsonMap.put("gooutStudentEx", gooutStudentEx);
		}
		if(officeJtGoout!=null&&StringUtils.isNotBlank(officeJtGoout.getType())&&StringUtils.equals("2", officeJtGoout.getType())){
			jsonMap.put("gooutTeacherEx", gooutTeacherEx);
		}
		JSONObject jsonObject = new JSONObject();
		for (String key : jsonMap.keySet()) {
			jsonObject.put(key, jsonMap.get(key));
		}
		try {
			ServletUtils.print(getResponse(), jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String jtGoOutQueryAdmin(){
		return SUCCESS;
	}
	public String jtGoOutQueryList(){
		Set<String> unitIds=new HashSet<String>();
		Unit unit=unitService.getUnit(getUnitId());
		if(unit.getUnitclass()==Unit.UNIT_CLASS_EDU){
			if(StringUtils.isNotBlank(unitName)){
				List<Unit> units=unitService.getUnitsByFaintness(unitName);
				for (Unit unit2 : units) {
					if((StringUtils.equals(unit2.getParentid(), getUnitId())&&StringUtils.equals("2", unit2.getUnitclass()+""))||StringUtils.equals(unit2.getId(), getUnitId())){
						unitIds.add(unit2.getId());
					}
				}
			}else{
				List<Unit> units=unitService.getUnderlingUnits(getUnitId(), true);
				for (Unit unit2 : units) {
						if(unit2.getUnitclass()==Unit.UNIT_CLASS_SCHOOL){
							unitIds.add(unit2.getId());
						}
				}
				unitIds.add(getUnitId());
			}
		}else{//学校端
			unitIds.add(getUnitId());
		}
		officeJtGoOutList=officeJtGooutService.getOfficeJtGooutsByUnitNameAndType(unitIds.toArray(new String[0]), type, startTime, endTime, getPage());
		return SUCCESS;
	}
	public String changeFlow() throws IOException{//TODO
		String value = "success";
		if(StringUtils.isNotBlank(jsonResult)){
			try {
				officeJtGooutService.changeFlow(jtGoOutId,getLoginUser().getUserId(),id,jsonResult,getPage());
			}catch (Exception e) {
				e.printStackTrace();
				if(e.getCause()!=null){
					value ="流程启动出错:"+e.getCause().getMessage();
				}else{
					value ="流程启动出错:"+e.getMessage();
				}
			}
		}else{
			value = "无法获取流程";
		}
		ServletUtils.print(getResponse(), value);
		return NONE;
	}
	public String findCurrentstep(){
		OfficeJtGoout officeJtGoout = officeJtGooutService.getOfficeJtGooutById(jtGoOutId);
		List<TaskDescription> todoTasks = taskHandlerService.getTodoTasks(officeJtGoout.getFlowId());
		if(todoTasks.size() > 0){
			for (TaskDescription item : todoTasks) {
				if(item.getTaskId().equals(taskId)){
					taskDefinitionKey = item.getTaskDefinitionKey();
					processInstanceId = item.getProcessInstanceId();
					easyLevel = "1";
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
	//附件保存
	public String saveAttachment(){
		try {
			List<UploadFile> uploadFileList  = StorageFileUtils.handleFiles(new String[] {},0);//js已限制
			if(StringUtils.isNotBlank(removeAttachmentId)){
				attachmentService.deleteAttachments(new String[]{removeAttachmentId});
			}
			Attachment attachment = new Attachment();
			if(!CollectionUtils.isEmpty(uploadFileList)){
				for (UploadFile uploadFile : uploadFileList) {
					if(uploadFile.getFieldName().equals(Constants.FILE_CONTENT)){
						attachment.setId(UUIDGenerator.getUUID());
						attachment.setFileName(uploadFile.getFileName());
						attachment.setContentType(uploadFile.getContentType());
						attachment.setFileSize(uploadFile.getFileSize());
						attachment.setUnitId(getLoginUser().getUnitId());
						attachment.setObjectId(jtGoOutId);
						attachment.setObjectType(Constants.OFFICE_JTGO_OUT_ATT);
						attachment.setConStatus(BusinessTask.TASK_STATUS_NO_HAND);
						attachmentService.saveAttachment(attachment, uploadFile);
					}else if(uploadFile.getFieldName().equals(Constants.FILE_ATTACHMENT)){
						attachment.setId(UUIDGenerator.getUUID());
						attachment.setFileName(uploadFile.getFileName());
						attachment.setContentType(uploadFile.getContentType());
						attachment.setFileSize(uploadFile.getFileSize());
						attachment.setUnitId(getLoginUser().getUnitId());
						attachment.setObjectId(jtGoOutId);
						attachment.setObjectType(Constants.OFFICE_JTGO_OUT_ATT);
						String fileExt = net.zdsoft.keel.util.FileUtils.getExtension(attachment.getFileName());
						if(converterFileTypeService.isVideo(fileExt)||converterFileTypeService.isDocument(fileExt)){
							attachment.setConStatus(BusinessTask.TASK_STATUS_NO_HAND);
						}
						if(converterFileTypeService.isPicture(fileExt)||converterFileTypeService.isAudio(fileExt)){
							attachment.setConStatus(BusinessTask.TASK_STATUS_SUCCESS);
						}
						//if(fileExt){
						//	}
						attachmentService.saveAttachment(attachment, uploadFile);
					}
				}
			}
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("文件保存成功");
			promptMessageDto.setBusinessValue(attachment.getId()+"*"+attachment.getDownloadPath());
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("文件保存出现问题");
		}
		return SUCCESS;
	}
	
	public String deleteAttachment(){
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
	
	public String getApplyStatus() {
		return applyStatus;
	}
	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}
	public List<OfficeJtGoout> getOfficeJtGoOutList() {
		return officeJtGoOutList;
	}
	public void setOfficeJtGoOutList(List<OfficeJtGoout> officeJtGoOutList) {
		this.officeJtGoOutList = officeJtGoOutList;
	}
	public OfficeJtGoout getOfficeJtGoout() {
		return officeJtGoout;
	}
	public void setOfficeJtGoout(OfficeJtGoout officeJtGoout) {
		this.officeJtGoout = officeJtGoout;
	}
	public List<Flow> getFlowList() {
		return flowList;
	}
	public void setFlowList(List<Flow> flowList) {
		this.flowList = flowList;
	}
	public void setOfficeJtGooutService(OfficeJtGooutService officeJtGooutService) {
		this.officeJtGooutService = officeJtGooutService;
	}
	public void setFlowManageService(FlowManageService flowManageService) {
		this.flowManageService = flowManageService;
	}
	public GooutStudentEx getGooutStudentEx() {
		return gooutStudentEx;
	}
	public void setGooutStudentEx(GooutStudentEx gooutStudentEx) {
		this.gooutStudentEx = gooutStudentEx;
	}
	public GooutTeacherEx getGooutTeacherEx() {
		return gooutTeacherEx;
	}
	public void setGooutTeacherEx(GooutTeacherEx gooutTeacherEx) {
		this.gooutTeacherEx = gooutTeacherEx;
	}
	public String getJtGoOutId() {
		return jtGoOutId;
	}
	public void setJtGoOutId(String jtGoOutId) {
		this.jtGoOutId = jtGoOutId;
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
	public void setGooutStudentExService(GooutStudentExService gooutStudentExService) {
		this.gooutStudentExService = gooutStudentExService;
	}
	public void setGooutTeacherExService(GooutTeacherExService gooutTeacherExService) {
		this.gooutTeacherExService = gooutTeacherExService;
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
	public void setTaskHandlerService(TaskHandlerService taskHandlerService) {
		this.taskHandlerService = taskHandlerService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public boolean isPass() {
		return isPass;
	}
	public void setPass(boolean isPass) {
		this.isPass = isPass;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
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
	public String getTextComment() {
		return textComment;
	}
	public void setTextComment(String textComment) {
		this.textComment = textComment;
	}
	public String getFromTab() {
		return fromTab;
	}
	public void setFromTab(String fromTab) {
		this.fromTab = fromTab;
	}
	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFileSize() {
		return systemIniService.getValue(Constants.FILE_INIID);
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getExtNames(){
		String[] extNames = converterFileTypeService.getAllExtNames();
		StringBuffer sb = new StringBuffer();
		if(extNames!=null){//rtf,doc,docx,xls,xlsx,csv,ppt,pptx,pdf,bmp,jpg,jpeg,png,gif//ppt,pptx
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
	public void setConverterFileTypeService(
			ConverterFileTypeService converterFileTypeService) {
		this.converterFileTypeService = converterFileTypeService;
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
}
