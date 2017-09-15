package net.zdsoft.office.officeFlow.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.subsystemcall.entity.OfficeStepInfoDto;
import net.zdsoft.eis.base.subsystemcall.service.OfficeSubsystemService;
import net.zdsoft.eis.frame.action.PageAction;
import net.zdsoft.jbpm.activiti.engine.task.AgileIdentityLinkType;
import net.zdsoft.jbpm.core.entity.Comment;
import net.zdsoft.jbpm.core.entity.HalfSelectUser;
import net.zdsoft.jbpm.core.entity.TaskHandlerSave;
import net.zdsoft.jbpm.core.entity.TaskHandlerShow;
import net.zdsoft.jbpm.core.service.HalfSelectUserService;
import net.zdsoft.jbpm.core.service.ProcessHandlerService;
import net.zdsoft.jbpm.core.service.TaskHandlerService;
import net.zdsoft.office.officeFlow.dto.TaskDesc;
import net.zdsoft.office.officeFlow.service.OfficeFlowService;

public class OfficeFlowAction extends PageAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7364271577135678682L;

	public static final String OFFICE_SUBSYSTEM = "70";
	
	private String taskId;
	private String currentStepId;
	private String checkHalfUsers;
	private String flowId;
	private String nextStepChangeUserId;
	private String nextStepChangeTaskId;
	private String nextStepChangeStepName;
	private String nextStepChangeStepType;
	
	private OfficeStepInfoDto stepDto;
	
	private List<TaskDesc> taskDescList = new ArrayList<TaskDesc>();
	private List<HalfSelectUser> halfSelectUsers;
	
	private OfficeFlowService officeFlowService;
	private HalfSelectUserService halfSelectUserService;
	private OfficeSubsystemService officeSubsystemService;
	private ProcessHandlerService processHandlerService;
	private TaskHandlerService taskHandlerService;
	private UserService userService;
	
	@Override
    public String execute() {
        return SUCCESS;
    }
	
	public String loadNextStepTaskLayer() {
		taskDescList = officeFlowService.getTaskDescList(getUnitId(), getLoginUser().getUserId(), taskId);
		return SUCCESS;
	}
	
	/**
     * 修改下一步负责人
     *
     * @return
     */
    public String changeNextStep() {
        if (StringUtils.isNotBlank(currentStepId)) {
        	stepDto = officeSubsystemService.getStepInfo(flowId, currentStepId);
        }
        
        if (!(stepDto != null && StringUtils.isNotBlank(stepDto.getStepType()) && stepDto.getStepType().equals("1"))) {
            Map<String, HalfSelectUser> halfMap = halfSelectUserService
                    .getHalfSelectPrincipals(OFFICE_SUBSYSTEM, getUnitId());
            halfSelectUsers = new ArrayList<HalfSelectUser>();
            halfSelectUsers.addAll(halfMap.values());
        }
    	return SUCCESS;
    }
    
    
    public String saveNextStepUsers() {
    	TaskHandlerShow taskHandlerShow = taskHandlerService.getTaskHandlerShow(taskId, getLoginUser().getUserId());
		if (StringUtils.isNotBlank(nextStepChangeStepType)) {
			List<String[]> nextStepList = new ArrayList<String[]>();
			String[] nextStepChangeUserIdArray = nextStepChangeUserId.split("#");
			String[] nextStepChangeTaskIdArray = nextStepChangeTaskId.split("#");
			String[] nextStepChangeStepNameArray = nextStepChangeStepName.split("#");
			String[] nextStepChangeStepTypeArray = nextStepChangeStepType.split("#");
			for (int i = 0; i < nextStepChangeUserIdArray.length; i++) {
				nextStepList.add(new String[] {
						nextStepChangeTaskIdArray[i],
						nextStepChangeUserIdArray[i],
						nextStepChangeStepNameArray[i],
						nextStepChangeStepTypeArray[i],
						"未知"});
			}
			String processDefinitionJson = "";
			try {
				processDefinitionJson = processHandlerService.getProcessDefinitionJson(getUnitId(),
						flowId, OFFICE_SUBSYSTEM, nextStepList);
				
				TaskHandlerSave taskHandlerSave = new TaskHandlerSave();
				taskHandlerSave.setCurrentTask(taskHandlerShow.getCurrentTask());
				taskHandlerSave.setCurrentUserId(getLoginUser().getUserId());
				taskHandlerSave.setCurrentUnitId(getLoginUser().getUnitId());
				taskHandlerSave.setSubsystemId(OFFICE_SUBSYSTEM);
				User user = userService.getUser(getLoginUser().getUserId());
				
				Comment comment = new Comment();
				comment.setAssigneeType(AgileIdentityLinkType.PRINCIPAL);
				comment.setAssigneeId(user.getId());
				comment.setAssigneeName(user.getRealname());
				taskHandlerSave.setComment(comment);
				
				taskHandlerSave.setProcessDefinitionJson(processDefinitionJson);
				
				taskHandlerService.completeTask(taskHandlerSave,false);
				
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage(taskHandlerSave.getCurrentTask().getTaskDefinitionKey());
			}
			catch (Exception e) {
				e.printStackTrace();
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("修改下一步负责人失败");
			}
		}
		else{
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage(taskHandlerShow.getCurrentTask().getTaskDefinitionKey());
		}
		
    	return SUCCESS;
    }

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getCurrentStepId() {
		return currentStepId;
	}

	public void setCurrentStepId(String currentStepId) {
		this.currentStepId = currentStepId;
	}

	public String getCheckHalfUsers() {
		return checkHalfUsers;
	}

	public void setCheckHalfUsers(String checkHalfUsers) {
		this.checkHalfUsers = checkHalfUsers;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public OfficeStepInfoDto getStepDto() {
		return stepDto;
	}

	public void setStepDto(OfficeStepInfoDto stepDto) {
		this.stepDto = stepDto;
	}

	public List<TaskDesc> getTaskDescList() {
		return taskDescList;
	}

	public List<HalfSelectUser> getHalfSelectUsers() {
		return halfSelectUsers;
	}

	public void setOfficeFlowService(OfficeFlowService officeFlowService) {
		this.officeFlowService = officeFlowService;
	}

	public void setHalfSelectUserService(HalfSelectUserService halfSelectUserService) {
		this.halfSelectUserService = halfSelectUserService;
	}

	public void setOfficeSubsystemService(
			OfficeSubsystemService officeSubsystemService) {
		this.officeSubsystemService = officeSubsystemService;
	}

	public String getNextStepChangeUserId() {
		return nextStepChangeUserId;
	}

	public void setNextStepChangeUserId(String nextStepChangeUserId) {
		this.nextStepChangeUserId = nextStepChangeUserId;
	}

	public String getNextStepChangeTaskId() {
		return nextStepChangeTaskId;
	}

	public void setNextStepChangeTaskId(String nextStepChangeTaskId) {
		this.nextStepChangeTaskId = nextStepChangeTaskId;
	}

	public String getNextStepChangeStepName() {
		return nextStepChangeStepName;
	}

	public void setNextStepChangeStepName(String nextStepChangeStepName) {
		this.nextStepChangeStepName = nextStepChangeStepName;
	}

	public String getNextStepChangeStepType() {
		return nextStepChangeStepType;
	}

	public void setNextStepChangeStepType(String nextStepChangeStepType) {
		this.nextStepChangeStepType = nextStepChangeStepType;
	}

	public void setProcessHandlerService(ProcessHandlerService processHandlerService) {
		this.processHandlerService = processHandlerService;
	}

	public void setTaskHandlerService(TaskHandlerService taskHandlerService) {
		this.taskHandlerService = taskHandlerService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
}
