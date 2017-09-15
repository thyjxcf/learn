package net.zdsoft.office.expense.action;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.tools.ant.filters.StringInputStream;

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
import net.zdsoft.office.expense.constant.OfficeExpenseConstants;
import net.zdsoft.office.expense.entity.OfficeExpense;
import net.zdsoft.office.expense.service.OfficeExpenseService;
import net.zdsoft.office.officeFlow.service.OfficeFlowService;
import net.zdsoft.office.util.Constants;

public class OfficeExpenseManageAction extends PageAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8271299727067755981L;

	private OfficeExpense officeExpense = new OfficeExpense();
	
	private String searchType;
	private boolean viewOnly = false;
	private String[] checkid;
	private String userName;
	private String taskHandlerSaveJson;
	private String currentStepId;
	private String fromTab;
	private boolean isPass;
	
	private List<OfficeExpense> officeExpenseList = new ArrayList<OfficeExpense>();
	private List<Flow> flowList;
	
	private OfficeExpenseService officeExpenseService;
	private FlowManageService flowManageService;
	private TaskHandlerService taskHandlerService;
	private UserService userService;
	private ConverterFileTypeService converterFileTypeService;
	private CustomRoleService customRoleService;
	private CustomRoleUserService customRoleUserService;
	
	private String expenseId;
	private String removeAttachmentId;
	private AttachmentService attachmentService;
	private String fileSize;
	
	private String taskId;
	private String jsonResult;
	private String id;
	private String taskDefinitionKey;
	private String processInstanceId;
	private String easyLevel;
	private boolean canChangeNextTask;
	
	private String reTaskId;
	private String taskKey;
	private String showReBackId;
	private boolean canBeRetract;
	private OfficeFlowService officeFlowService;
	
	private boolean canQuery;
	
	private Date startTime;
	private Date endTime;
	
	public String execute() {
		return SUCCESS;
	}
	
	/**
	 * 我的报销
	 * @return
	 */
	public String myExpense() {
		return SUCCESS;
	}
	
	public String myExpenseList() {
		officeExpenseList = officeExpenseService.getOfficeExpenseListByCondition(getUnitId(), searchType, getLoginUser().getUserId(), getPage());
		return SUCCESS;
	}
	
	public String myExpenseAdd() {
		flowList = flowManageService.getFinishFlowList(getUnitId(), FlowConstant.FLOW_OWNER_UNIT, FlowConstant.OFFICE_EXPENSE,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
		List<Flow> flowList2  = flowManageService.getFinishFlowList(getLoginInfo().getUser().getDeptid(), FlowConstant.FLOW_OWNER_STRING, 
				FlowConstant.OFFICE_EXPENSE,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
		if(CollectionUtils.isNotEmpty(flowList2)){
			flowList.addAll(flowList2);
		}
		
		officeExpense = new OfficeExpense();
		officeExpense.setUnitId(getUnitId());
		officeExpense.setApplyUserId(getLoginUser().getUserId());
		officeExpense.setId(UUIDGenerator.getUUID());
		for(Flow item : flowList){
			if(item.isDefaultFlow()){
				officeExpense.setFlowId(item.getFlowId());
			}
		}
		return SUCCESS;
	}
	
	public String myExpenseEdit() {
		officeExpense = officeExpenseService.getOfficeExpenseById(officeExpense.getId());
		flowList = flowManageService.getFinishFlowList(getUnitId(), FlowConstant.FLOW_OWNER_UNIT, FlowConstant.OFFICE_EXPENSE,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
		List<Flow> flowList2  = flowManageService.getFinishFlowList(getLoginInfo().getUser().getDeptid(), FlowConstant.FLOW_OWNER_STRING, 
				FlowConstant.OFFICE_EXPENSE,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
		if(CollectionUtils.isNotEmpty(flowList2)){
			flowList.addAll(flowList2);
		}
		return SUCCESS;
	}
	
	public String myExpenseSave() {
		UploadFile file = null;
		try{
			//file = StorageFileUtils.handleFile(new String[] {},Integer.parseInt(systemIniService.getValue(Constants.FILE_INIID))*1024);
			if(OfficeExpenseConstants.OFFCIE_EXPENSE_NOT_SUBMIT.equals(officeExpense.getState())){
				if(StringUtils.isNotBlank(officeExpense.getId())){
					OfficeExpense office=officeExpenseService.getOfficeExpenseById(officeExpense.getId());
					if(office!=null){
						officeExpenseService.update(officeExpense, null,false,null);
					}else{
						officeExpenseService.insert(officeExpense, null,false);
					}
				}
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("保存成功");
				promptMessageDto.setErrorMessage(officeExpense.getId());
			}
			else{
				officeExpenseService.startFlow(officeExpense, getLoginUser().getUserId(), null,false,null);
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("提交成功,已进入流程中");
			}
		}catch (Exception e){
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			if(OfficeExpenseConstants.OFFCIE_EXPENSE_NOT_SUBMIT.equals(officeExpense.getState())){
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
	
	public String myExpenseDelete() {
		try {
			officeExpenseService.delete(checkid);
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("删除成功");
		}catch (Exception e){
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("删除失败:"+e.getCause().getMessage());
		}
		return SUCCESS;
	}
	
	/**
	 * 报销审批
	 * @return
	 */
	public String expenseAudit() {
		return SUCCESS;
	}
	
	public String expenseAuditEdit() {//TODO
		officeExpense = officeExpenseService.getOfficeExpenseById(officeExpense.getId());
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
		officeExpense.setTaskName(taskHandlerSave.getCurrentTask().getTaskName());
		currentStepId = taskHandlerSave.getCurrentTask().getTaskDefinitionKey();
		return SUCCESS;
	}
	
	public String expenseAuditList() {
		officeExpenseList = officeExpenseService.getAuditList(searchType, getLoginUser().getUserId(), getPage());
		return SUCCESS;
	}
	
	public String expenseAuditSave() {
		try {
			System.out.println("taskHandlerSaveJson2:"+taskHandlerSaveJson);
			JSONObject object = new JSONObject().fromObject(taskHandlerSaveJson);
			TaskHandlerSave taskHandlerSave =(TaskHandlerSave) JSONObject.toBean(object,
				 TaskHandlerSave.class);
			taskHandlerSave.getComment().setOperateTime(new Date());
			officeExpenseService.doAudit(isPass(), taskHandlerSave, officeExpense.getId(), currentStepId);
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
					attachment.setObjectId(expenseId);
					attachment.setObjectType(OfficeExpenseConstants.OFFICE_EXPENSE_ATT);
					String fileExt = net.zdsoft.keel.util.FileUtils.getExtension(attachment.getFileName());
					if(converterFileTypeService.isVideo(fileExt)||converterFileTypeService.isDocument(fileExt)){
						attachment.setConStatus(BusinessTask.TASK_STATUS_NO_HAND);
					}
					if(converterFileTypeService.isPicture(fileExt)||converterFileTypeService.isAudio(fileExt)){
						attachment.setConStatus(BusinessTask.TASK_STATUS_SUCCESS);
					}
					attachmentService.saveAttachment(attachment, uploadFile);
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
	
	public String changeFlow(){//TODO
		String value="success";
		try {
			if(StringUtils.isNotBlank(jsonResult)){
				officeExpenseService.changeFlow(expenseId, getLoginUser().getUserId(), id, jsonResult);
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
		if(StringUtils.isNotBlank(expenseId)){
			officeExpense=officeExpenseService.getOfficeExpenseById(expenseId);
			List<TaskDescription> todoTasks = taskHandlerService.getTodoTasks(officeExpense.getFlowId());
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
	
	public String expenseQueryAdmin(){
		return SUCCESS;
	}
	public String expenseQueryList(){//TODO
		officeExpenseList=officeExpenseService.getOfficeExpensesByUnitIdAndOthers(getUnitId(), searchType, startTime, endTime, userName, getPage());
		return SUCCESS;
	}
	public String expenseExport(){
		officeExpenseList=officeExpenseService.getOfficeExpensesByUnitIdAndOthers(getUnitId(), searchType, null, null, userName, null);
		
		ZdExcel zdExcel = new ZdExcel();
		ZdStyle style2 = new ZdStyle(ZdStyle.BOLD|ZdStyle.BORDER);
		ZdStyle style3 = new ZdStyle(ZdStyle.BORDER|ZdStyle.ALIGN_CENTER|ZdStyle.WRAP_TEXT);
		
		List<ZdCell> zdcells=new ArrayList<ZdCell>();
		zdcells.add(new ZdCell("序号", 1, style2));
		zdcells.add(new ZdCell("申请人", 1, style2));
		zdcells.add(new ZdCell("部门科室", 1, style2));
		zdcells.add(new ZdCell("报销金额", 1, style2));
		zdcells.add(new ZdCell("报销类别", 1, style2));
		zdcells.add(new ZdCell("费用明细", 1, style2));
		zdcells.add(new ZdCell("审核状态", 1, style2));
		zdExcel.add(new ZdCell("教师报销查询", zdcells.size(), 2, new ZdStyle(ZdStyle.BOLD|ZdStyle.ALIGN_CENTER|ZdStyle.WRAP_TEXT, 18)));
		zdExcel.add(zdcells.toArray(new ZdCell[0]));
		
		int j=1;
		for (OfficeExpense officeExpense : officeExpenseList) {
			List<ZdCell> zdcellss=new ArrayList<ZdCell>();
			zdcellss.add(new ZdCell(j+"", 1, style3));
			zdcellss.add(new ZdCell(officeExpense.getApplyUserName(), 1, style3));
			zdcellss.add(new ZdCell(officeExpense.getDeptName(), 1, style3));
			zdcellss.add(new ZdCell(officeExpense.getExpenseMoney()+"", 1, style3));
			zdcellss.add(new ZdCell(officeExpense.getExpenseType(), 1, style3));
			zdcellss.add(new ZdCell(officeExpense.getDetail(), 1, style3));
			if(org.apache.commons.lang3.StringUtils.equals("2", officeExpense.getState())){
				zdcellss.add(new ZdCell("待审核", 1, style3));
			}else if (org.apache.commons.lang3.StringUtils.equals("3", officeExpense.getState())) {
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
		zdExcel.export("office_Expense");
		return NONE;
	}
	public String expenseDown(){
		try {
			officeExpense=officeExpenseService.getOfficeExpenseById(officeExpense.getId());
			
			Map<String,Object> dataMap=new HashMap<String, Object>();
			dataMap.put("officeExpense", officeExpense);
			dataMap.put("hisTaskList", officeExpense.getHisTaskList());
			
			String sb=DocumentHandler.createDocString(dataMap, "officeExpenseTemp.xml");
			InputStream in=new StringInputStream(sb, "utf-8");
			ServletUtils.download(in, getRequest(), getResponse(), "用款申请书.doc");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return NONE;
	}
	public OfficeExpense getOfficeExpense() {
		return officeExpense;
	}

	public void setOfficeExpense(OfficeExpense officeExpense) {
		this.officeExpense = officeExpense;
	}

	public boolean isViewOnly() {
		return viewOnly;
	}

	public void setViewOnly(boolean viewOnly) {
		this.viewOnly = viewOnly;
	}

	public String[] getCheckid() {
		return checkid;
	}

	public void setCheckid(String[] checkid) {
		this.checkid = checkid;
	}

	public List<OfficeExpense> getOfficeExpenseList() {
		return officeExpenseList;
	}

	public void setOfficeExpenseService(OfficeExpenseService officeExpenseService) {
		this.officeExpenseService = officeExpenseService;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public void setFlowManageService(FlowManageService flowManageService) {
		this.flowManageService = flowManageService;
	}

	public List<Flow> getFlowList() {
		return flowList;
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

	public String getFromTab() {
		return fromTab;
	}

	public void setFromTab(String fromTab) {
		this.fromTab = fromTab;
	}

	public boolean isPass() {
		return isPass;
	}

	public void setPass(boolean isPass) {
		this.isPass = isPass;
	}

	public String getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(String expenseId) {
		this.expenseId = expenseId;
	}

	public String getRemoveAttachmentId() {
		return removeAttachmentId;
	}

	public void setRemoveAttachmentId(String removeAttachmentId) {
		this.removeAttachmentId = removeAttachmentId;
	}

	public String getFileSize() {
		return systemIniService.getValue(Constants.FILE_INIID);
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
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
					.checkRetract(officeExpense.getHisTaskList(), getLoginUser().getUserId(), officeExpense.getFlowId()));
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public boolean isCanQuery() {
		CustomRole customRole=customRoleService.getCustomRoleByRoleCode(getUnitId(), "office_expense_query");
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

	public void setCanQuery(boolean canQuery) {
		this.canQuery = canQuery;
	}

	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	}

	public void setCustomRoleUserService(CustomRoleUserService customRoleUserService) {
		this.customRoleUserService = customRoleUserService;
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
	
}
