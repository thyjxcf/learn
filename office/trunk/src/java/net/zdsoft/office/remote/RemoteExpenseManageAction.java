package net.zdsoft.office.remote;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.common.service.UserSetService;
import net.zdsoft.eis.base.storage.StorageFileUtils;
import net.zdsoft.eis.component.flowManage.constant.FlowConstant;
import net.zdsoft.eis.component.flowManage.entity.Flow;
import net.zdsoft.eis.component.flowManage.service.FlowManageService;
import net.zdsoft.jbpm.activiti.engine.task.AgileIdentityLinkType;
import net.zdsoft.jbpm.core.entity.Comment;
import net.zdsoft.jbpm.core.entity.TaskDescription;
import net.zdsoft.jbpm.core.entity.TaskHandlerSave;
import net.zdsoft.jbpm.core.entity.TaskHandlerShow;
import net.zdsoft.jbpm.core.service.TaskHandlerService;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.office.expense.constant.OfficeExpenseConstants;
import net.zdsoft.office.expense.entity.OfficeExpense;
import net.zdsoft.office.expense.service.OfficeExpenseService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

public class RemoteExpenseManageAction extends OfficeJsonBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3262569928221244612L;

	private List<OfficeExpense> officeExpenseList = new ArrayList<OfficeExpense>();
	private OfficeExpense officeExpense = new OfficeExpense();
	
	private String userId;
	private String unitId;
	private String userName;
	private String id;
	private int auditStatus; 
	
	private OfficeExpenseService officeExpenseService;
	private UserService userService;
	private FlowManageService flowManageService;
	private UserSetService userSetService;
	private DeptService deptService;
	
	private List<Flow> flowList;
	private String taskId;
	private TaskHandlerService taskHandlerService;
	private String taskHandlerSaveJson;
	private boolean isPass;
	private String currentStepId;
	private String flowId;
	
	private String applyStatus;
	private String removeAttachment;
	
	/**
	 * 我的报销
	 */
	public void applyList() {
		Pagination page = getPage();
		page.setPageSize(15);
		officeExpenseList = officeExpenseService.getOfficeExpenseListByCondition(unitId, applyStatus, userId, page);
		JSONArray array = new JSONArray();
		for(OfficeExpense expense : officeExpenseList){
			JSONObject json = new JSONObject();
			json.put("id", expense.getId());
			String expenseType=expense.getExpenseType();
			if(expenseType.length() > 4){
				expenseType = expenseType.substring(0, 4)+"...";
			}
			json.put("expenseType", expenseType);
			json.put("createTime", DateUtils.date2String(expense.getCreateTime(), "yyyy-MM-dd"));
			String detail = expense.getDetail();
			if(detail.length() > 50){
				detail = detail.substring(0, 50)+"...";
			}
			json.put("detail", expense.getDetail());
			json.put("state", expense.getState());
			array.add(json);
		}
		jsonMap.put(getListObjectName(), array);
		jsonMap.put("page", page);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	
	public void applyExpense() {//TODO
		if(StringUtils.isNotBlank(id)){
			officeExpense = officeExpenseService.getOfficeExpenseById(id);
			User user = userService.getUser(officeExpense.getApplyUserId());
			if(user!=null){
				officeExpense.setApplyUserId(user.getId());
				officeExpense.setApplyUserName(user.getRealname());
			}
		}else{
			officeExpense = new OfficeExpense();
			User user = userService.getUser(userId);
			if(user!=null){
				officeExpense.setApplyUserId(user.getId());
				officeExpense.setApplyUserName(user.getRealname());
			}
		}
		officeExpense.setUnitId(unitId);
		flowList = flowManageService.getFinishFlowList(unitId, FlowConstant.FLOW_OWNER_UNIT, FlowConstant.OFFICE_EXPENSE,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
		User user = userService.getUser(userId);
		if(user != null && StringUtils.isNotBlank(user.getDeptid())){
			List<Flow> flowList2  = flowManageService.getFinishFlowList(user.getDeptid(), FlowConstant.FLOW_OWNER_STRING, 
					FlowConstant.OFFICE_EXPENSE,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
			if(CollectionUtils.isNotEmpty(flowList2)){
				flowList.addAll(flowList2);
			}
		}
		
		JSONObject json = new JSONObject();
		json.put("id", officeExpense.getId());
		json.put("unitId", officeExpense.getUnitId());
		json.put("applyUserId", officeExpense.getApplyUserId());
		if(officeExpense.getExpenseMoney()!=null){
			DecimalFormat df=new DecimalFormat("#.00");
			json.put("expenseMoney", df.format(officeExpense.getExpenseMoney()));
		}
		json.put("expenseType", officeExpense.getExpenseType());
		json.put("detail", officeExpense.getDetail());
		json.put("attachmentArray", RemoteOfficeUtils.createAttachmentArray(officeExpense.getAttachments()));
		json.put("flowId", officeExpense.getFlowId());
		JSONArray flowArray = new JSONArray();
		if(CollectionUtils.isNotEmpty(flowList)){
			for (Flow flow : flowList) {
				JSONObject flowObject=new JSONObject();
				flowObject.put("flowId", flow.getFlowId());
				flowObject.put("flowName", flow.getFlowName());
				flowObject.put("isDefaultFlow", flow.isDefaultFlow());
				flowArray.add(flowObject);
			}
		}
		json.put("flowArray",flowArray);
		
		jsonMap.put(getDetailObjectName(), json);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	
	public void saveExpenseApply(){
//		UploadFile file = null;
		JSONObject json = new JSONObject();
		try {
//			file = StorageFileUtils.handleFile(new String[] {}, 5*1024);
			List<UploadFile> uploadFileList  = StorageFileUtils.handleFiles(new String[] {}, 5*1024, 50*1024);
			
			officeExpense.setState(OfficeExpenseConstants.OFFCIE_EXPENSE_NOT_SUBMIT);
			if(StringUtils.isNotEmpty(officeExpense.getId())){
				String[] removeAttachmentArray = removeAttachment.split(",");
				officeExpenseService.update(officeExpense,uploadFileList,true, removeAttachmentArray);
			}else{
				officeExpenseService.insert(officeExpense,uploadFileList,true);
			}
			json.put("result",1);
		}catch(Exception e){
			e.printStackTrace();
			json.put("result",0);
		}
		responseJSON(json);
	}
	
	public void submitExpenseApply(){
//		UploadFile file = null;
		JSONObject json = new JSONObject();
		try {
//			file = StorageFileUtils.handleFile(new String[] {}, 5*1024);
			officeExpense.setState(OfficeExpenseConstants.OFFCIE_EXPENSE_WAIT);
			List<UploadFile> uploadFileList  = StorageFileUtils.handleFiles(new String[] {}, 5*1024, 50*1024);
			
			String[] removeAttachmentArray = null;
			if(StringUtils.isNotBlank(removeAttachment)){
				removeAttachmentArray = removeAttachment.split(",");
			}
			
			officeExpenseService.startFlow(officeExpense, userId, uploadFileList,true, removeAttachmentArray);
			json.put("id",officeExpense.getId());
			json.put("result",1);
		}catch (Exception e) {
			e.printStackTrace();
			e.printStackTrace();
			if(e.getCause()!=null){
				if(e.getCause().getMessage()!=null){
					json.put("msg", "提交失败:"+e.getCause().getMessage());
				}else{
					json.put("msg", "未设置流程审核人员");
				}
			}else{
				json.put("msg", "提交失败:"+e.getMessage());
			}
		}
		responseJSON(json);
	}
	
	public void deleteExpense(){
		JSONObject json = new JSONObject();
		if(StringUtils.isNotEmpty(id)){
			try {
				officeExpenseService.delete(new String[]{id});
				json.put("result",1);
			} catch (Exception e) {
				json.put("result",0);
			}
		}else{
			json.put("result",0);
		}
		responseJSON(json);
	}
	
	/**
	 * 报销审核
	 */
	public void auditList() {
		Pagination page = getPage();
		page.setPageSize(15);
		List<OfficeExpense> list = new ArrayList<OfficeExpense>();
		if(auditStatus==0){//待审核
			List<TaskDescription> todoTaskList = taskHandlerService.getTodoTasks(userId, Integer.parseInt(FlowConstant.OFFICE_SUBSYSTEM+FlowConstant.OFFICE_EXPENSE), page);
			if(CollectionUtils.isNotEmpty(todoTaskList)){
				Set<String> flowIdSet= new HashSet<String>();
				for (TaskDescription task : todoTaskList) {
					flowIdSet.add(task.getProcessInstanceId());	
				}
				Map<String, OfficeExpense> expenseMap = officeExpenseService.getOfficeExpenseMapByFlowIds(flowIdSet.toArray(new String[0]));
				Set<String> userIdSet = new HashSet<String>();
				for (String expenseId : expenseMap.keySet()) {
					OfficeExpense expense = expenseMap.get(expenseId);
					if(expense!=null){
						userIdSet.add(expense.getApplyUserId());
					}
				}
				Map<String,User> userMap = new HashMap<String, User>();
				userMap = userService.getUserWithDelMap(userIdSet.toArray(new String[0]));
				for (TaskDescription task : todoTaskList) {
					OfficeExpense expense = expenseMap.get(task.getProcessInstanceId());
					if(expense!=null){
						expense.setTaskId(task.getTaskId());
						expense.setTaskName(task.getTaskName());
						User user = userMap.get(expense.getApplyUserId());
						if(user!=null){
							expense.setApplyUserName(user.getRealname());
						}else{
							expense.setApplyUserName("用户已删除");
						}
						list.add(expense);
					}
				}
			}
		}
		else{//已审核
			list = officeExpenseService.getAuditedList(userId, new String[]{OfficeExpenseConstants.OFFCIE_EXPENSE_PASS,OfficeExpenseConstants.OFFCIE_EXPENSE_FAILED}, page);
			if(CollectionUtils.isNotEmpty(list)){
				Set<String> userIdSet = new HashSet<String>();
				for(OfficeExpense expense : list){
					userIdSet.add(expense.getApplyUserId());
				}
				Map<String,User> userMap = new HashMap<String, User>();
				userMap = userService.getUserWithDelMap(userIdSet.toArray(new String[0]));
				for(OfficeExpense expense : list){
					User user = userMap.get(expense.getApplyUserId());
					if(user!=null){
						expense.setApplyUserName(user.getRealname());
					}else{
						expense.setApplyUserName("用户已删除");
					}
				}
			}
		}
		
		JSONArray array = new JSONArray();
		for(OfficeExpense expense : list){
			JSONObject json = new JSONObject();
			json.put("id", expense.getId());
			String expenseType=expense.getExpenseType();
			if(expenseType.length() > 4){
				expenseType = expenseType.substring(0, 4)+"...";
			}
			json.put("expenseType", expenseType);
			json.put("createTime", DateUtils.date2String(expense.getCreateTime(), "yyyy-MM-dd"));
			json.put("applyUserName", expense.getApplyUserName());
			String detail = expense.getDetail();
			if(detail.length() > 50){
				detail = detail.substring(0, 50)+"...";
			}
			json.put("detail", expense.getDetail());
			json.put("state", expense.getState());
			json.put("taskId", expense.getTaskId());
			array.add(json);
		}
		jsonMap.put(getListObjectName(), array);
		jsonMap.put("page", page);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	
	public void auditExpense() {//TODO
		officeExpense = officeExpenseService.getOfficeExpenseById(id);
		TaskHandlerSave taskHandlerSave = new TaskHandlerSave();
		TaskHandlerShow taskHandlerShow = taskHandlerService.getTaskHandlerShow(taskId, userId);
		taskHandlerSave.setCurrentTask(taskHandlerShow.getCurrentTask());
		taskHandlerSave.setCurrentUserId(userId);
		taskHandlerSave.setCurrentUnitId(unitId);
		taskHandlerSave.setSubsystemId(FlowConstant.OFFICE_SUBSYSTEM);
		User user = userService.getUser(userId);
		Comment comment = new Comment();
		comment.setAssigneeType(AgileIdentityLinkType.PRINCIPAL);
		comment.setAssigneeId(user.getId());
		comment.setAssigneeName(user.getRealname());
		userName = user.getRealname();
		taskHandlerSave.setComment(comment);
		JSONObject jsonStr = new JSONObject().fromObject(taskHandlerSave);
		taskHandlerSaveJson = jsonStr.toString();
		officeExpense.setTaskName(taskHandlerSave.getCurrentTask().getTaskName());
		currentStepId = taskHandlerSave.getCurrentTask().getTaskDefinitionKey();
		flowId = officeExpense.getFlowId();
		
		String photoUrl = userSetService.getUserPhotoUrl(officeExpense.getApplyUserId());
		String deptName = "";
		if(user != null){
			Dept dept = deptService.getDept(user.getDeptid());
			if(dept!=null){
				deptName = dept.getDeptname();
			}
		}
		
		JSONObject json = new JSONObject();
		json.put("id", officeExpense.getId());
		json.put("applyUserName", officeExpense.getApplyUserName());
		json.put("deptName", deptName);
		json.put("photoUrl", photoUrl);
		if(officeExpense.getExpenseMoney()!=null){
			DecimalFormat df=new DecimalFormat("#.00");
			json.put("expenseMoney", df.format(officeExpense.getExpenseMoney()));
		}
		json.put("expenseType", officeExpense.getExpenseType());
		json.put("detail", officeExpense.getDetail());
		json.put("attachmentArray", RemoteOfficeUtils.createAttachmentArray(officeExpense.getAttachments()));
		json.put("taskName", taskHandlerSave.getCurrentTask().getTaskName());
		json.put("userName", userName);
		json.put("currentStepId", currentStepId);
		json.put("flowId", flowId);
		json.put("hisTaskCommentArray",RemoteOfficeUtils.createHisTaskCommentArray(officeExpense.getHisTaskList(), officeExpense.getUnitId(), officeExpense.getFlowId()));
		json.put("taskHandlerSaveJson", taskHandlerSaveJson);
		jsonMap.put(getDetailObjectName(), json);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	
	public void saveExpenseAudit() {
		JSONObject json = new JSONObject();
		try {
			JSONObject object = new JSONObject().fromObject(taskHandlerSaveJson);
			TaskHandlerSave taskHandlerSave =(TaskHandlerSave) JSONObject.toBean(object,
				 TaskHandlerSave.class);
			taskHandlerSave.getComment().setOperateTime(new Date());
			officeExpense = officeExpenseService.getOfficeExpenseById(id);
			officeExpenseService.doAudit(isPass(), taskHandlerSave, id,currentStepId);
			json.put("result",1);
		}catch (Exception e){
			e.printStackTrace();
			json.put("result",0);
			if(e.getCause()!=null){
				json.put("msg", "审核失败:"+e.getCause().getMessage());
			}else{
				json.put("msg", "审核失败:"+e.getMessage());
			}
		}
		responseJSON(json);
	}
	
	public void expenseApplyDetail() {
		officeExpense = officeExpenseService.getOfficeExpenseById(id);
		if(officeExpense == null){
			officeExpense = new OfficeExpense();
		}
		User user = userService.getUser(officeExpense.getApplyUserId());
		String photoUrl = userSetService.getUserPhotoUrl(officeExpense.getApplyUserId());
		String deptName = "";
		if(user != null){
			officeExpense.setApplyUserName(user.getRealname());
			Dept dept = deptService.getDept(user.getDeptid());
			if(dept!=null){
				deptName = dept.getDeptname();
			}
		}
		JSONObject json = new JSONObject();
		json.put("id", officeExpense.getId());
		json.put("applyUserName", officeExpense.getApplyUserName());
		json.put("deptName", deptName);
		json.put("photoUrl", photoUrl);
		if(officeExpense.getExpenseMoney()!=null){
			DecimalFormat df=new DecimalFormat("#.00");
			json.put("expenseMoney",df.format(officeExpense.getExpenseMoney()));
		}
		json.put("expenseType", officeExpense.getExpenseType());
		json.put("detail", officeExpense.getDetail());
		json.put("applyStatus", officeExpense.getState());
		json.put("attachmentArray", RemoteOfficeUtils.createAttachmentArray(officeExpense.getAttachments()));
		json.put("attachmentArray",RemoteOfficeUtils.createAttachmentArray(officeExpense.getAttachments()));
		json.put("hisTaskCommentArray",RemoteOfficeUtils.createHisTaskCommentArray(officeExpense.getHisTaskList(), officeExpense.getUnitId(), officeExpense.getFlowId()));
		
		jsonMap.put(getDetailObjectName(), json);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}
	
	@Override
	protected String getListObjectName() {
		return "result_array";
	}

	@Override
	protected String getDetailObjectName() {
		return "result_object";
	}

	public OfficeExpense getOfficeExpense() {
		return officeExpense;
	}

	public void setOfficeExpense(OfficeExpense officeExpense) {
		this.officeExpense = officeExpense;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public List<OfficeExpense> getOfficeExpenseList() {
		return officeExpenseList;
	}

	public List<Flow> getFlowList() {
		return flowList;
	}

	public void setOfficeExpenseService(OfficeExpenseService officeExpenseService) {
		this.officeExpenseService = officeExpenseService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setFlowManageService(FlowManageService flowManageService) {
		this.flowManageService = flowManageService;
	}

	public void setTaskHandlerService(TaskHandlerService taskHandlerService) {
		this.taskHandlerService = taskHandlerService;
	}

	public String getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}
	public void setUserSetService(UserSetService userSetService) {
		this.userSetService = userSetService;
	}
	
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public String getRemoveAttachment() {
		return removeAttachment;
	}

	public void setRemoveAttachment(String removeAttachment) {
		this.removeAttachment = removeAttachment;
	}
	
}
