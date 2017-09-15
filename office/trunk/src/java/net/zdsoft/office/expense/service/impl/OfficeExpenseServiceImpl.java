package net.zdsoft.office.expense.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.eis.base.attachment.service.AttachmentService;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.component.flowManage.constant.FlowConstant;
import net.zdsoft.jbpm.core.entity.TaskDescription;
import net.zdsoft.jbpm.core.entity.TaskHandlerResult;
import net.zdsoft.jbpm.core.entity.TaskHandlerSave;
import net.zdsoft.jbpm.core.service.ProcessHandlerService;
import net.zdsoft.jbpm.core.service.TaskHandlerService;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keel.util.UUIDUtils;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.office.convertflow.constant.ConvertFlowConstants;
import net.zdsoft.office.convertflow.service.OfficeConvertFlowService;
import net.zdsoft.office.convertflow.service.OfficeFlowSendMsgService;
import net.zdsoft.office.expense.constant.OfficeExpenseConstants;
import net.zdsoft.office.expense.dao.OfficeExpenseDao;
import net.zdsoft.office.expense.entity.OfficeExpense;
import net.zdsoft.office.expense.service.OfficeExpenseService;
import net.zdsoft.office.officeFlow.dto.HisTask;
import net.zdsoft.office.officeFlow.service.OfficeFlowService;
import net.zdsoft.office.officeFlow.service.OfficeFlowStepInfoService;
import net.zdsoft.office.util.Constants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
/**
 * office_expense
 * @author 
 * 
 */
public class OfficeExpenseServiceImpl implements OfficeExpenseService{
	
	private UserService userService;
	private AttachmentService attachmentService;
	private TaskHandlerService taskHandlerService;
	private ProcessHandlerService processHandlerService;
	private OfficeExpenseDao officeExpenseDao;
	private OfficeConvertFlowService officeConvertFlowService;
	private OfficeFlowSendMsgService officeFlowSendMsgService;
	private OfficeFlowService officeFlowService;
	private OfficeFlowStepInfoService officeFlowStepInfoService;
	private TeacherService teacherService;
	private DeptService deptService;
	
	@Override
	public OfficeExpense save(OfficeExpense officeExpense){
		return officeExpenseDao.save(officeExpense);
	}

	@Override
	public Integer delete(String[] ids){
		List<OfficeExpense> list = this.getOfficeExpenseByIds(ids);
		for(OfficeExpense officeExpense : list){
			if(OfficeExpenseConstants.OFFCIE_EXPENSE_WAIT.equals(officeExpense.getState())
					|| OfficeExpenseConstants.OFFCIE_EXPENSE_PASS.equals(officeExpense.getState())
					|| OfficeExpenseConstants.OFFCIE_EXPENSE_FAILED.equals(officeExpense.getState())){
				processHandlerService.deleteProcessInstance(officeExpense.getFlowId(), true);
			}
		}
		return officeExpenseDao.delete(ids);
	}

	@Override
	public Integer update(OfficeExpense officeExpense){
		return officeExpenseDao.update(officeExpense);
	}

	@Override
	public OfficeExpense getOfficeExpenseById(String id){
		OfficeExpense officeExpense = officeExpenseDao.getOfficeExpenseById(id);
		if(officeExpense != null){
			if(StringUtils.isNotEmpty(officeExpense.getFlowId())){
				List<HisTask> hisTask = officeFlowService.getHisTask(officeExpense.getFlowId());
				officeExpense.setHisTaskList(hisTask);
			}
			List<Attachment> attachments = attachmentService.getAttachments(officeExpense.getId(), OfficeExpenseConstants.OFFICE_EXPENSE_ATT);
			if(CollectionUtils.isNotEmpty(attachments) && attachments.size() > 0){
				officeExpense.setAttachments(attachments);
				officeExpense.setFileName(attachments.get(0).getFileName());
			}
			User user = userService.getUser(officeExpense.getApplyUserId());
			if(user!=null){
				officeExpense.setApplyUserName(user.getRealname());
				String teacherId=user.getTeacherid();
				if(org.apache.commons.lang3.StringUtils.isNotBlank(teacherId)){
					Teacher teacher=teacherService.getTeacher(teacherId);
					if(teacher!=null){
						String deptId=teacher.getDeptid();
						if(org.apache.commons.lang3.StringUtils.isNotBlank(deptId)){
							Dept dept=deptService.getDept(deptId);
							if(dept!=null){
								officeExpense.setDeptName(dept.getDeptname());
							}
						}
					}
				}
			}
		}
		return officeExpense;
	}

	@Override
	public Map<String, OfficeExpense> getOfficeExpenseMapByIds(String[] ids){
		return officeExpenseDao.getOfficeExpenseMapByIds(ids);
	}

	@Override
	public List<OfficeExpense> getOfficeExpenseList(){
		return officeExpenseDao.getOfficeExpenseList();
	}

	@Override
	public List<OfficeExpense> getOfficeExpensePage(Pagination page){
		return officeExpenseDao.getOfficeExpensePage(page);
	}

	@Override
	public List<OfficeExpense> getOfficeExpenseByUnitIdList(String unitId){
		return officeExpenseDao.getOfficeExpenseByUnitIdList(unitId);
	}

	@Override
	public List<OfficeExpense> getOfficeExpenseByUnitIdPage(String unitId, Pagination page){
		return officeExpenseDao.getOfficeExpenseByUnitIdPage(unitId, page);
	}

	@Override
	public List<OfficeExpense> getOfficeExpenseListByCondition(String unitId, String searchType, String applyUserId, Pagination page){
		return officeExpenseDao.getOfficeExpenseListByCondition(unitId, searchType, applyUserId, page);
	}
	
	@Override
	public void insert(OfficeExpense officeExpense, List<UploadFile> uploadFileList,boolean isMobile){
		officeExpenseDao.save(officeExpense);
		if (isMobile && !CollectionUtils.isEmpty(uploadFileList)) {
			for (UploadFile uploadFile : uploadFileList) {
				Attachment attachment = new Attachment();
				attachment.setFileName(uploadFile.getFileName());
				attachment.setContentType(uploadFile.getContentType());
				attachment.setFileSize(uploadFile.getFileSize());
				attachment.setUnitId(officeExpense.getUnitId());
				attachment.setObjectId(officeExpense.getId());
				attachment.setObjectType(OfficeExpenseConstants.OFFICE_EXPENSE_ATT);
				attachmentService.saveAttachment(attachment, uploadFile);
			}
		}
		/*if(uploadFile!=null){
			Attachment attachment = new Attachment();
			attachment.setFileName(uploadFile.getFileName());
			attachment.setContentType(uploadFile.getContentType());
			attachment.setFileSize(uploadFile.getFileSize());
			attachment.setUnitId(officeExpense.getUnitId());
			attachment.setObjectId(officeExpense.getId());
			attachment.setObjectType(OfficeExpenseConstants.OFFICE_EXPENSE_ATT);
			attachmentService.saveAttachment(attachment, uploadFile);
		}*/
	}
	
	@Override
	public void update(OfficeExpense officeExpense, List<UploadFile> uploadFileList,boolean isMobile,String[] removeAttachmentArray){
//		String fileName = officeExpense.getFileName();
		officeExpenseDao.update(officeExpense);
		if (isMobile && !CollectionUtils.isEmpty(uploadFileList)) {
			if (ArrayUtils.isNotEmpty(removeAttachmentArray)) {
				attachmentService.deleteAttachments(removeAttachmentArray);
			}
			for (UploadFile uploadFile : uploadFileList) {
				Attachment attachment = new Attachment();
				attachment.setFileName(uploadFile.getFileName());
				attachment.setContentType(uploadFile.getContentType());
				attachment.setFileSize(uploadFile.getFileSize());
				attachment.setUnitId(officeExpense.getUnitId());
				attachment.setObjectId(officeExpense.getId());
				attachment.setObjectType(OfficeExpenseConstants.OFFICE_EXPENSE_ATT);
				attachmentService.saveAttachment(attachment, uploadFile);
			}
		}
	/*	if(uploadFile != null){
			//删除原有的授课计划附件
			deleteFile(officeExpense.getId());
			
			Attachment attachment = new Attachment();
			attachment.setFileName(uploadFile.getFileName());
			attachment.setContentType(uploadFile.getContentType());
			attachment.setFileSize(uploadFile.getFileSize());
			attachment.setUnitId(officeExpense.getUnitId());
			attachment.setObjectId(officeExpense.getId());
			attachment.setObjectType(OfficeExpenseConstants.OFFICE_EXPENSE_ATT);
			attachmentService.saveAttachment(attachment, uploadFile);
		}
		
		if(StringUtils.isBlank(fileName)){
			deleteFile(officeExpense.getId());
		}*/
	}
	
	public void deleteFile(String applyId){
		List<Attachment> attachments = attachmentService
				.getAttachments(applyId, OfficeExpenseConstants.OFFICE_EXPENSE_ATT);
		String[] attachmentIds = new String[attachments.size()];
		for(int i = 0; i < attachments.size(); i++){
			attachmentIds[i] = attachments.get(i).getId();
		}
		attachmentService.deleteAttachments(attachmentIds);
	}
	
	@Override
	public void startFlow(OfficeExpense officeExpense, String userId, 
				List<UploadFile> uploadFileList,boolean isMobile,String[] removeAttachmentArray){
		if(StringUtils.isBlank(officeExpense.getId())){
			officeExpense.setId(UUIDUtils.newId());
		}
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("pass",true);
		officeExpense.setCreateTime(new Date());
		String flowId = processHandlerService.startProcessInstance(FlowConstant.OFFICE_SUBSYSTEM, officeExpense.getFlowId(), Integer.parseInt(FlowConstant.OFFICE_SUBSYSTEM+FlowConstant.OFFICE_EXPENSE), officeExpense.getId(), userId, variables);
		officeFlowStepInfoService.batchUpdateByFlowId(officeExpense.getFlowId(), flowId);
		officeExpense.setFlowId(flowId);
		
		OfficeExpense office=officeExpenseDao.getOfficeExpenseById(officeExpense.getId());
		if(office!=null){
			this.update(officeExpense,uploadFileList,isMobile,removeAttachmentArray);
		}else{
			this.insert(officeExpense,uploadFileList,isMobile);
		}
		//this.update(officeExpense);
		
		officeConvertFlowService.startFlow(officeExpense, ConvertFlowConstants.OFFICE_EXPENSE);
		officeFlowSendMsgService.startFlowSendMsg(officeExpense, ConvertFlowConstants.OFFICE_EXPENSE);
	}
	
	@Override
	public List<OfficeExpense> getOfficeExpenseByIds(String[] ids){
		return officeExpenseDao.getOfficeExpenseByIds(ids);
	}
	
	@Override
	public Map<String, OfficeExpense> getOfficeExpenseMapByFlowIds(String[] flowIds){
		return officeExpenseDao.getOfficeExpenseMapByFlowIds(flowIds);
	}
	
	@Override
	public List<OfficeExpense> getAuditedList(String userId, String[] state, Pagination page){
		return officeExpenseDao.getAuditedList(userId, state, page);
	}
	
	@Override
	public List<OfficeExpense> getAuditList(String searchType, String userId, Pagination page){
		List<OfficeExpense> list = new ArrayList<OfficeExpense>();
		if(StringUtils.isBlank(searchType) || "0".equals(searchType)){//待审核
			List<TaskDescription> todoTaskList = taskHandlerService.getTodoTasks(userId, Integer.parseInt(FlowConstant.OFFICE_SUBSYSTEM+FlowConstant.OFFICE_EXPENSE), page);
			if(CollectionUtils.isNotEmpty(todoTaskList)){
				Set<String> flowIdSet= new HashSet<String>();
				for (TaskDescription task : todoTaskList) {
					flowIdSet.add(task.getProcessInstanceId());	
				}
				Map<String, OfficeExpense> expenseMap = officeExpenseDao.getOfficeExpenseMapByFlowIds(flowIdSet.toArray(new String[0]));
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
		else if("3".equals(searchType)){
			User user = userService.getUser(userId);
			if(user != null){
				String unitId = user.getUnitid();
				list = this.getOfficeExpenseByUnitIdList(unitId);
				list = officeFlowService.haveDoneAudit(list, userId, page);
			}
			else{
				list = new ArrayList<OfficeExpense>();
			}
		}
		else{//已审核
			list = officeExpenseDao.getAuditedList(userId, new String[]{OfficeExpenseConstants.OFFCIE_EXPENSE_PASS, 
					OfficeExpenseConstants.OFFCIE_EXPENSE_FAILED}, page);
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
		return list;
	}
	
	@Override
	public void doAudit(boolean pass, TaskHandlerSave taskHandlerSave, String id, String currentStepId){
		OfficeExpense updateExpense = officeExpenseDao.getOfficeExpenseById(id);
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("pass", pass);
		taskHandlerSave.setVariables(variables);
		TaskHandlerResult result;
		if(pass){
			taskHandlerSave.getComment().setTextComment("[审核通过]"+taskHandlerSave.getComment().getTextComment());
			result = taskHandlerService.completeTask(taskHandlerSave);
		}else{
			taskHandlerSave.getComment().setTextComment("[审核不通过]"+taskHandlerSave.getComment().getTextComment());
			boolean isNotFinish = taskHandlerService.isExclusiveGatewayForNext(updateExpense.getUnitId(), taskHandlerSave.getSubsystemId(), taskHandlerSave.getCurrentTask().getTaskDefinitionKey(), updateExpense.getFlowId());
			if(isNotFinish){
				result = taskHandlerService.completeTask(taskHandlerSave);
			}else{
				result = taskHandlerService.suspendTask(taskHandlerSave);
			}
		}
		if(result.getStatus()==TaskHandlerResult.STATUS_FINISH){
			if(result.getResult()==TaskHandlerResult.RESULT_PASS){
				updateExpense.setState(Constants.LEAVE_APPLY_FLOW_FINSH_PASS+"");
			}else if(result.getResult()==TaskHandlerResult.RESULT_NOT_PASS){
				updateExpense.setState(Constants.LEAVE_APPLY_FLOW_FINSH_NOT_PASS+"");
			}
			update(updateExpense);
		}
		
		officeConvertFlowService.completeTask(id, updateExpense.getFlowId(), taskHandlerSave.getCurrentUserId(), taskHandlerSave.getCurrentTask().getTaskId(), result, pass);
		if(result.getStatus()==TaskHandlerResult.STATUS_FINISH
				|| officeFlowService.checkSendFlowMsg(updateExpense.getFlowId(), currentStepId)){
			officeFlowSendMsgService.completeTaskSendMsg(taskHandlerSave.getCurrentUserId(), pass, updateExpense, ConvertFlowConstants.OFFICE_EXPENSE, result);
		}
	}
	
	@Override
	public void changeFlow(String expenseId, String userId, String modelId, String jsonResult){
		OfficeExpense expense = officeExpenseDao.getOfficeExpenseById(expenseId);
		expense.setState(String.valueOf(Constants.APPLY_STATE_NEED_AUDIT));
		
		Map<String,Object> variables=new HashMap<String, Object>();
		variables.put("pass", true);
		String flowId=processHandlerService.startProcessInstance(FlowConstant.OFFICE_SUBSYSTEM, modelId, Integer.parseInt(FlowConstant.OFFICE_SUBSYSTEM+FlowConstant.OFFICE_EXPENSE), expenseId, userId,jsonResult, variables);
		officeFlowStepInfoService.batchUpdateByFlowId(modelId, flowId);
		expense.setFlowId(flowId);
		update(expense);

		officeConvertFlowService.startFlow(expense, ConvertFlowConstants.OFFICE_EXPENSE);
		officeFlowSendMsgService.startFlowSendMsg(expense, ConvertFlowConstants.OFFICE_EXPENSE);
		
	}
	
	@Override
	public List<OfficeExpense> getOfficeExpensesByUnitIdAndOthers(
			String unitId, String State, Date startTime, Date Endtime,
			String applyUserName, Pagination page) {
		List<OfficeExpense> officeExpenses=officeExpenseDao.getOfficeExpensesByUnitIdAndOthers(unitId, State, startTime, Endtime, applyUserName, page);
		Set<String> userIdSet=new HashSet<String>();
		if(CollectionUtils.isNotEmpty(officeExpenses)){
			for (OfficeExpense officeExpense : officeExpenses) {
				userIdSet.add(officeExpense.getApplyUserId());
			}
			Map<String,User> userMap=new HashMap<String, User>();
			userMap=userService.getUserWithDelMap(userIdSet.toArray(new String[0]));
			Set<String> teacherIdSet=new HashSet<String>();
			for (OfficeExpense officeExpense : officeExpenses) {
				if(userMap.containsKey(officeExpense.getApplyUserId())){
					User user=userMap.get(officeExpense.getApplyUserId());
					if(user!=null){
						officeExpense.setApplyUserName(user.getRealname());
						teacherIdSet.add(user.getTeacherid());
					}else{
						officeExpense.setApplyUserName("用户已删除");
					}
				}
			}
			Map<String,Teacher> teacherMap=new HashMap<String, Teacher>();
			teacherMap=teacherService.getTeacherMap(teacherIdSet.toArray(new String[0]));
			Map<String,Dept> deptMap=new HashMap<String, Dept>();
			deptMap=deptService.getDeptMap(unitId);
			for (OfficeExpense officeExpense : officeExpenses) {
				if(userMap.containsKey(officeExpense.getApplyUserId())){
					User user=userMap.get(officeExpense.getApplyUserId());
					if(user!=null){
						officeExpense.setApplyUserName(user.getRealname());
						Teacher teacher=teacherMap.get(user.getTeacherid());
						if(teacher!=null){
							Dept dept=deptMap.get(teacher.getDeptid());
							if(dept!=null){
								officeExpense.setDeptName(dept.getDeptname());
							}
						}
					}else{
						officeExpense.setApplyUserName("用户已删除");
					}
				}
			}
		}
		return officeExpenses;
	}

	public void setOfficeExpenseDao(OfficeExpenseDao officeExpenseDao){
		this.officeExpenseDao = officeExpenseDao;
	}

	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setTaskHandlerService(TaskHandlerService taskHandlerService) {
		this.taskHandlerService = taskHandlerService;
	}

	public void setProcessHandlerService(ProcessHandlerService processHandlerService) {
		this.processHandlerService = processHandlerService;
	}
	
	public void setOfficeConvertFlowService(
			OfficeConvertFlowService officeConvertFlowService) {
		this.officeConvertFlowService = officeConvertFlowService;
	}
	
	public void setOfficeFlowSendMsgService(
			OfficeFlowSendMsgService officeFlowSendMsgService) {
		this.officeFlowSendMsgService = officeFlowSendMsgService;
	}

	public void setOfficeFlowService(OfficeFlowService officeFlowService) {
		this.officeFlowService = officeFlowService;
	}

	public void setOfficeFlowStepInfoService(
			OfficeFlowStepInfoService officeFlowStepInfoService) {
		this.officeFlowStepInfoService = officeFlowStepInfoService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	
}
	