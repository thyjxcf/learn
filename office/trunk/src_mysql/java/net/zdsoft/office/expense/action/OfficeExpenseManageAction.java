package net.zdsoft.office.expense.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

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
import net.zdsoft.office.expense.constant.OfficeExpenseConstants;
import net.zdsoft.office.expense.entity.OfficeExpense;
import net.zdsoft.office.expense.service.OfficeExpenseService;

import com.opensymphony.xwork2.ModelDriven;

public class OfficeExpenseManageAction extends PageAction implements ModelDriven<OfficeExpense>{

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
	
	@Override
	public OfficeExpense getModel() {
		return officeExpense;
	}

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
		officeExpense = new OfficeExpense();
		officeExpense.setUnitId(getUnitId());
		officeExpense.setApplyUserId(getLoginUser().getUserId());
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
		return SUCCESS;
	}
	
	public String myExpenseSave() {
		UploadFile file = null;
		try{
			file = StorageFileUtils.handleFile(new String[] {}, 5*1024);
			if(OfficeExpenseConstants.OFFCIE_EXPENSE_NOT_SUBMIT.equals(officeExpense.getState())){
				if(StringUtils.isNotBlank(officeExpense.getId())){
					officeExpenseService.update(officeExpense, file);
				}else{
					officeExpenseService.insert(officeExpense, file);
				}
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("保存成功");
			}
			else{
				officeExpenseService.startFlow(officeExpense, getLoginUser().getUserId(), file);
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("提交成功,已进入流程中");
			}
		}catch (Exception e){
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			if(OfficeExpenseConstants.OFFCIE_EXPENSE_NOT_SUBMIT.equals(officeExpense.getState())){
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
		String taskId = officeExpense.getTaskId();
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
			JSONObject object = new JSONObject().fromObject(taskHandlerSaveJson);
			TaskHandlerSave taskHandlerSave =(TaskHandlerSave) JSONObject.toBean(object,
				 TaskHandlerSave.class);
			taskHandlerSave.getComment().setOperateTime(new Date());
			officeExpenseService.doAudit(isPass(), taskHandlerSave, officeExpense.getId());
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
	
}
