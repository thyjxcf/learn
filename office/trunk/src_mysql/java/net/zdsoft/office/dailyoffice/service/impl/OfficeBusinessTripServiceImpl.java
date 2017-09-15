package net.zdsoft.office.dailyoffice.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.eis.base.attachment.service.AttachmentService;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.component.flowManage.constant.FlowConstant;
import net.zdsoft.eis.component.flowManage.entity.Flow;
import net.zdsoft.eis.component.flowManage.service.FlowManageService;
import net.zdsoft.eis.frame.client.BaseDao.MultiRow;
import net.zdsoft.jbpm.activiti.engine.task.AgileIdentityLinkType;
import net.zdsoft.jbpm.core.entity.Comment;
import net.zdsoft.jbpm.core.entity.HistoricTask;
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
import net.zdsoft.office.dailyoffice.dao.OfficeBusinessTripDao;
import net.zdsoft.office.dailyoffice.entity.OfficeBusinessTrip;
import net.zdsoft.office.dailyoffice.entity.OfficeMeetingApply;
import net.zdsoft.office.dailyoffice.service.OfficeBusinessTripService;
import net.zdsoft.office.teacherLeave.dto.HisTask;
import net.zdsoft.office.teacherLeave.entity.OfficeTeacherLeave;
import net.zdsoft.office.util.Constants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
/**
 * office_business_trip
 * @author 
 * 
 */
public class OfficeBusinessTripServiceImpl implements OfficeBusinessTripService{
	private OfficeBusinessTripDao officeBusinessTripDao;
	private ProcessHandlerService processHandlerService;
	private AttachmentService attachmentService;
	private TaskHandlerService taskHandlerService;
	private UserService userService;
	private FlowManageService flowManageService;
	private OfficeConvertFlowService officeConvertFlowService;
	private OfficeFlowSendMsgService officeFlowSendMsgService;

	@Override
	public OfficeBusinessTrip save(OfficeBusinessTrip officeBusinessTrip){
		return officeBusinessTripDao.save(officeBusinessTrip);
	}

	@Override
	public Integer delete(String[] ids){
		List<Attachment> attachments = attachmentService
				.getAttachments(ids[0],Constants.OFFICE_BUSINESS_TRIP_ATT);
		String[] attachmentIds = new String[attachments.size()];
		for(int i = 0; i < attachments.size(); i++){
			attachmentIds[i] = attachments.get(i).getId();
		}
		attachmentService.deleteAttachments(attachmentIds);
		return officeBusinessTripDao.delete(ids);
	}

	@Override
	public Integer update(OfficeBusinessTrip officeBusinessTrip){
		return officeBusinessTripDao.update(officeBusinessTrip);
	}

	@Override
	public OfficeBusinessTrip getOfficeBusinessTripById(String id){
		OfficeBusinessTrip officeBusinessTrip =officeBusinessTripDao.getOfficeBusinessTripById(id);
		if(officeBusinessTrip!=null){
			User user = userService.getUser(officeBusinessTrip.getApplyUserId());
			if(user!=null){
				officeBusinessTrip.setUserName(user.getRealname());
			}
			if(StringUtils.isNotEmpty(officeBusinessTrip.getFlowId())){
				officeBusinessTrip.setHisTaskList(getHisTisk(officeBusinessTrip.getFlowId()));
			}
			officeBusinessTrip.setAttachments(attachmentService.getAttachments(officeBusinessTrip.getId(), Constants.OFFICE_BUSINESS_TRIP_ATT));
		}
		return officeBusinessTrip;
	}
	private List<HisTask> getHisTisk(String flowId){
		List<HisTask> hisTaskList = new ArrayList<HisTask>();
		List<HistoricTask> histasks = taskHandlerService.getHistoricTasks(flowId);
		if(CollectionUtils.isNotEmpty(histasks)){
			for (HistoricTask hisTask : histasks) {
				HisTask task = new HisTask();
				task.setComment(hisTask.getComment());
				task.setTaskName(hisTask.getTaskName());
				task.setCandidateUsers(hisTask.getCandidateUsers());
				Comment comment = task.getComment();
				if(comment!=null){
					if(AgileIdentityLinkType.PROXY.equals(comment.getAssigneeType())){
						String assigneeName = comment.getAssigneeName()+" 代签(";
						if(task.getCandidateUsers().size()>0){
							List<User> userList = userService.getUsers(task.getCandidateUsers().toArray(new String[0]));	
							if(CollectionUtils.isNotEmpty(userList)){
								for (int i=0;i<userList.size();i++) {
									User user= userList.get(i);
									if(user!=null){
										assigneeName +=user.getRealname();
									}
									if((i+1)!=userList.size()){
										assigneeName +=",";
									}
								}
							}
						}
						assigneeName +=")";
						task.setAssigneeName(assigneeName);
					}else{
						task.setAssigneeName(comment.getAssigneeName());
					}
				}
				hisTaskList.add(task);
			}
		}
		return hisTaskList;
	}
	@Override
	public Map<String, OfficeBusinessTrip> getOfficeBusinessTripMapByIds(String[] ids){
		return officeBusinessTripDao.getOfficeBusinessTripMapByIds(ids);
	}

	@Override
	public List<OfficeBusinessTrip> getOfficeBusinessTripList(){
		return officeBusinessTripDao.getOfficeBusinessTripList();
	}

	@Override
	public List<OfficeBusinessTrip> getOfficeBusinessTripPage(Pagination page){
		return officeBusinessTripDao.getOfficeBusinessTripPage(page);
	}

	@Override
	public List<OfficeBusinessTrip> getOfficeBusinessTripByUnitIdList(String unitId){
		return officeBusinessTripDao.getOfficeBusinessTripByUnitIdList(unitId);
	}

	@Override
	public List<OfficeBusinessTrip> getOfficeBusinessTripByUnitIdPage(String unitId, Pagination page){
		return officeBusinessTripDao.getOfficeBusinessTripByUnitIdPage(unitId, page);
	}

	@Override
	public List<OfficeBusinessTrip> getOfficeBusinessTripByUnitIdUserIdPage(
			String unitId, String userId, String States, Pagination page) {
		return officeBusinessTripDao.getOfficeBusinessTripByUnitIdUserIdPage(unitId, userId, States, page);
	}
	
	public void setOfficeBusinessTripDao(OfficeBusinessTripDao officeBusinessTripDao){
		this.officeBusinessTripDao = officeBusinessTripDao;
	}
	
	public void setProcessHandlerService(ProcessHandlerService processHandlerService) {
		this.processHandlerService = processHandlerService;
	}
	
	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	@Override
	public boolean isExistConflict(String id, String applyUserId,
			Date beginTime, Date endTime) {
		return officeBusinessTripDao.isExistConflict(id, applyUserId, beginTime, endTime);
	}

	@Override
	public void startFlow(OfficeBusinessTrip officeBusinessTrip, String userId,UploadFile file) {
		if(StringUtils.isNotEmpty(officeBusinessTrip.getId())){
			update(officeBusinessTrip,file);
		}else{
			officeBusinessTrip.setId(UUIDUtils.newId());
			add(officeBusinessTrip,file);
		}
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("pass",true);
		officeBusinessTrip.setFlowId(processHandlerService.startProcessInstance(FlowConstant.OFFICE_SUBSYSTEM,officeBusinessTrip.getFlowId(), Integer.parseInt(FlowConstant.OFFICE_SUBSYSTEM+FlowConstant.OFFICE_BUSINESS_TRIP), officeBusinessTrip.getId(), userId, variables));
		update(officeBusinessTrip);
		
		officeConvertFlowService.startFlow(officeBusinessTrip, ConvertFlowConstants.OFFICE_EVECTION);
		officeFlowSendMsgService.startFlowSendMsg(officeBusinessTrip, ConvertFlowConstants.OFFICE_EVECTION);
	}

	@Override
	public void update(OfficeBusinessTrip officeBusinessTrip, UploadFile file) {
		officeBusinessTripDao.update(officeBusinessTrip);
		List<Attachment> attachments = attachmentService.getAttachments(officeBusinessTrip.getId(), Constants.OFFICE_BUSINESS_TRIP_ATT);
		if(file!=null){
			if(CollectionUtils.isNotEmpty(attachments)){
				Attachment attachment = attachments.get(0);
				attachment.setFileName(file.getFileName());
				attachment.setContentType(file.getContentType());
				attachment.setFileSize(file.getFileSize());
				attachmentService.updateAttachment(attachment, file, true);
			}else{
				Attachment attachment = new Attachment();
				attachment.setFileName(file.getFileName());
				attachment.setContentType(file.getContentType());
				attachment.setFileSize(file.getFileSize());
				attachment.setUnitId(officeBusinessTrip.getUnitId());
				attachment.setObjectId(officeBusinessTrip.getId());
				attachment.setObjectType(Constants.OFFICE_BUSINESS_TRIP_ATT);
				attachmentService.saveAttachment(attachment, file);
			}
		}
		if(StringUtils.isBlank(officeBusinessTrip.getUploadContentFileInput())&&file==null){
			String[] attachmentIds = new String[attachments.size()];
			for(int i = 0; i < attachments.size(); i++){
				attachmentIds[i] = attachments.get(i).getId();
			}
			attachmentService.deleteAttachments(attachmentIds);
		}
	}

	@Override
	public void add(OfficeBusinessTrip officeBusinessTrip, UploadFile file) {
		officeBusinessTripDao.save(officeBusinessTrip);
		if(file!=null){
			Attachment attachment = new Attachment();
			attachment.setFileName(file.getFileName());
			attachment.setContentType(file.getContentType());
			attachment.setFileSize(file.getFileSize());
			attachment.setUnitId(officeBusinessTrip.getUnitId());
			attachment.setObjectId(officeBusinessTrip.getId());
			attachment.setObjectType(Constants.OFFICE_BUSINESS_TRIP_ATT);
			attachmentService.saveAttachment(attachment, file);
		}
		
	}

	@Override
	public List<OfficeBusinessTrip> getOfficeBusinessTripByIds(String[] ids) {
		return officeBusinessTripDao.getOfficeBusinessTripByIds(ids);
	}

	@Override
	public List<OfficeBusinessTrip> toDoAudit(String userId, Pagination page) {
		List<OfficeBusinessTrip> offList = new ArrayList<OfficeBusinessTrip>();
		List<TaskDescription> todoTaskList = new ArrayList<TaskDescription>();
		todoTaskList = taskHandlerService.getTodoTasks(userId, Integer.parseInt(FlowConstant.OFFICE_SUBSYSTEM+FlowConstant.OFFICE_BUSINESS_TRIP), page);
		if(CollectionUtils.isNotEmpty(todoTaskList)){
			Set<String> flowIdSet= new HashSet<String>();
			for (TaskDescription task : todoTaskList) {
				flowIdSet.add(task.getProcessInstanceId());	
			}
			Map<String,OfficeBusinessTrip> leaveMap = officeBusinessTripDao.getOfficeBusinessTripMapByFlowIds(flowIdSet.toArray(new String[0]));
			Set<String> userIdSet = new HashSet<String>();
			for (String leaveId : leaveMap.keySet()) {
				OfficeBusinessTrip officeBusinessTrip = leaveMap.get(leaveId);
				if(officeBusinessTrip!=null){
					userIdSet.add(officeBusinessTrip.getApplyUserId());
				}
			}
			Map<String,User> userMap = new HashMap<String, User>();
			userMap = userService.getUserWithDelMap(userIdSet.toArray(new String[0]));
			for (TaskDescription task : todoTaskList) {
				OfficeBusinessTrip officeBusinessTrip = leaveMap.get(task.getProcessInstanceId());
				if(officeBusinessTrip==null){
					continue;
				}
				officeBusinessTrip.setTaskId(task.getTaskId());
				officeBusinessTrip.setTaskName(task.getTaskName());
				User user = userMap.get(officeBusinessTrip.getApplyUserId());
				if(user!=null){
					officeBusinessTrip.setUserName(user.getRealname());
				}else{
					officeBusinessTrip.setUserName("用户已删除");
				}
				offList.add(officeBusinessTrip);
			}
		}
		Collections.sort(offList,new Comparator<OfficeBusinessTrip>(){

			@Override
			public int compare(OfficeBusinessTrip o1, OfficeBusinessTrip o2) {
				return o1.getCreateTime().compareTo(o2.getCreateTime());
			}});
		return offList;
	}

	@Override
	public List<OfficeBusinessTrip> HaveDoAudit(String userId, Pagination page) {
		List<OfficeBusinessTrip>  offList = officeBusinessTripDao.HaveDoAudit(userId,page);
		Set<String> userIdSet = new HashSet<String>();
		for (OfficeBusinessTrip officeBusinessTrip : offList) {
			userIdSet.add(officeBusinessTrip.getApplyUserId());
		}
		Map<String,User> userMap = new HashMap<String, User>();
		userMap = userService.getUserWithDelMap(userIdSet.toArray(new String[0]));
		for (OfficeBusinessTrip officeBusinessTrip : offList) {
			User user = userMap.get(officeBusinessTrip.getApplyUserId());
			if(user!=null){
				officeBusinessTrip.setUserName(user.getRealname());
			}else{
				officeBusinessTrip.setUserName("用户已删除");
			}
		}
		return offList;
	}
	@Override
	public void passFlow(boolean pass, TaskHandlerSave taskHandlerSave,
			String leaveId) {
		OfficeBusinessTrip leave = officeBusinessTripDao.getOfficeBusinessTripById(leaveId);
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("pass", pass);
		taskHandlerSave.setVariables(variables);
		if(pass){
			taskHandlerSave.getComment().setTextComment("[审核通过]"+taskHandlerSave.getComment().getTextComment());
		}else{
			taskHandlerSave.getComment().setTextComment("[审核不通过]"+taskHandlerSave.getComment().getTextComment());
			leave.setState(String.valueOf(Constants.LEAVE_APPLY_FLOW_FINSH_NOT_PASS));
			update(leave);
		}
		TaskHandlerResult result = taskHandlerService.completeTask(taskHandlerSave);
		if(result.getStatus()==TaskHandlerResult.STATUS_FINISH){
			if(result.getResult()==TaskHandlerResult.RESULT_PASS){
				leave.setState(String.valueOf(Constants.LEAVE_APPLY_FLOW_FINSH_PASS));
			}else if(result.getResult()==TaskHandlerResult.RESULT_NOT_PASS){
				leave.setState(String.valueOf(Constants.LEAVE_APPLY_FLOW_FINSH_NOT_PASS));
			}
			update(leave);
		}
		
		officeConvertFlowService.completeTask(leaveId, taskHandlerSave.getCurrentUserId(), taskHandlerSave.getCurrentTask().getTaskId(), result, pass);
		officeFlowSendMsgService.completeTaskSendMsg(taskHandlerSave.getCurrentUserId(), pass, leave, ConvertFlowConstants.OFFICE_EVECTION, result);
	}
	public void setTaskHandlerService(TaskHandlerService taskHandlerService) {
		this.taskHandlerService = taskHandlerService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setFlowManageService(FlowManageService flowManageService) {
		this.flowManageService = flowManageService;
	}
	public void setOfficeConvertFlowService(
			OfficeConvertFlowService officeConvertFlowService) {
		this.officeConvertFlowService = officeConvertFlowService;
	}
	
	public void setOfficeFlowSendMsgService(
			OfficeFlowSendMsgService officeFlowSendMsgService) {
		this.officeFlowSendMsgService = officeFlowSendMsgService;
	}
}
