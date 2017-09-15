package net.zdsoft.office.dailyoffice.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.eis.base.attachment.service.AttachmentService;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.component.flowManage.constant.FlowConstant;
import net.zdsoft.eis.component.flowManage.service.FlowManageService;
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
import net.zdsoft.office.convertflow.entity.OfficeConvertFlow;
import net.zdsoft.office.convertflow.service.OfficeConvertFlowService;
import net.zdsoft.office.convertflow.service.OfficeConvertFlowTaskService;
import net.zdsoft.office.convertflow.service.OfficeFlowSendMsgService;
import net.zdsoft.office.dailyoffice.constant.OfficeBusinessTripConstants;
import net.zdsoft.office.dailyoffice.dao.OfficeGoOutDao;
import net.zdsoft.office.dailyoffice.entity.OfficeGoOut;
import net.zdsoft.office.dailyoffice.service.OfficeGoOutService;
import net.zdsoft.office.teacherLeave.dto.HisTask;
import net.zdsoft.office.teacherLeave.entity.OfficeTeacherLeave;
import net.zdsoft.office.util.Constants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
/**
 * office_go_out
 * @author 
 * 
 */
public class OfficeGoOutServiceImpl implements OfficeGoOutService{
	private OfficeGoOutDao officeGoOutDao;
	private ProcessHandlerService processHandlerService;
	private AttachmentService attachmentService;
	private UserService userService;
	private TaskHandlerService taskHandlerService;
	private FlowManageService flowManageService;
	private OfficeConvertFlowService officeConvertFlowService;
	private OfficeFlowSendMsgService officeFlowSendMsgService;
	private DeptService deptService;
	private OfficeConvertFlowTaskService officeConvertFlowTaskService;
	@Override
	public OfficeGoOut save(OfficeGoOut officeGoOut){
		return officeGoOutDao.save(officeGoOut);
	}

	@Override
	public Integer delete(String[] ids){
		List<OfficeGoOut> officeGoOuts = officeGoOutDao.getOfficeGoOutByIds(ids);
		for (OfficeGoOut officeGoOut : officeGoOuts) {
			if(Integer.parseInt(officeGoOut.getState()) > OfficeBusinessTripConstants.OFFCIE_BUSINESSTRIP_NOT_SUBMIT){
				processHandlerService.deleteProcessInstance(officeGoOut.getFlowId(), true);
			}
		}
		List<Attachment> attachments = attachmentService
				.getAttachments(ids[0],Constants.OFFICE_BUSINESS_TRIP_ATT);
		String[] attachmentIds = new String[attachments.size()];
		for(int i = 0; i < attachments.size(); i++){
			attachmentIds[i] = attachments.get(i).getId();
		}
		attachmentService.deleteAttachments(attachmentIds);
		return officeGoOutDao.delete(ids);
	}
	@Override
	public void deleteRevoke(String id) {
		//1.删除convertFlow关联表信息
		OfficeConvertFlow officeConvertFlow = officeConvertFlowService.getObjByBusinessId(id);
		if(officeConvertFlow!=null&&StringUtils.isNotBlank(officeConvertFlow.getId())){
			officeConvertFlowTaskService.deleteByConvertFlowId(officeConvertFlow.getId());
		}
		officeConvertFlowService.deleteByBusinessId(id);
		//2.删除对应流程表信息，本次撤销功能不发信息
		//3.删除申请表
		delete(new String[]{id} );
	}
	@Override
	public Integer update(OfficeGoOut officeGoOut){
		return officeGoOutDao.update(officeGoOut);
	}

	@Override
	public OfficeGoOut getOfficeGoOutById(String id){
		OfficeGoOut officeGoOut =officeGoOutDao.getOfficeGoOutById(id);
		if(officeGoOut!=null){
			User user = userService.getUser(officeGoOut.getApplyUserId());
			if(user!=null){
				officeGoOut.setUserName(user.getRealname());
			}
			if(StringUtils.isNotEmpty(officeGoOut.getFlowId())){
				officeGoOut.setHisTaskList(getHisTisk(officeGoOut.getFlowId()));
			}
			officeGoOut.setAttachments(attachmentService.getAttachments(officeGoOut.getId(), Constants.OFFICE_BUSINESS_TRIP_ATT));
		}
		return officeGoOut;
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
	public Map<String, OfficeGoOut> getOfficeGoOutMapByIds(String[] ids){
		return officeGoOutDao.getOfficeGoOutMapByIds(ids);
	}

	@Override
	public List<OfficeGoOut> getOfficeGoOutList(){
		return officeGoOutDao.getOfficeGoOutList();
	}

	@Override
	public List<OfficeGoOut> getOfficeGoOutPage(Pagination page){
		return officeGoOutDao.getOfficeGoOutPage(page);
	}

	@Override
	public List<OfficeGoOut> getOfficeGoOutByUnitIdList(String unitId){
		return officeGoOutDao.getOfficeGoOutByUnitIdList(unitId);
	}

	@Override
	public List<OfficeGoOut> getOfficeGoOutByUnitIdPage(String unitId, Pagination page){
		return officeGoOutDao.getOfficeGoOutByUnitIdPage(unitId, page);
	}

	public void setOfficeGoOutDao(OfficeGoOutDao officeGoOutDao){
		this.officeGoOutDao = officeGoOutDao;
	}

	@Override
	public List<OfficeGoOut> getOfficeGoOutByUnitIdUserIdPage(String unitId,
			String userId, String states, Pagination page) {
		return officeGoOutDao.getOfficeGoOutByUnitIdUserIdPage(unitId, userId, states, page);
	}

	@Override
	public boolean isExistConflict(String id, String applyUserId,
			Date beginTime, Date endTime) {
		return officeGoOutDao.isExistConflict(id, applyUserId, beginTime, endTime);
	}

	@Override
	public void startFlow(OfficeGoOut officeGoOut, String userId,
			UploadFile file) {
		if(StringUtils.isNotEmpty(officeGoOut.getId())){
			update(officeGoOut,file);
		}else{
			officeGoOut.setId(UUIDUtils.newId());
			add(officeGoOut,file);
		}
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("pass",true);
		officeGoOut.setFlowId(processHandlerService.startProcessInstance(FlowConstant.OFFICE_SUBSYSTEM,officeGoOut.getFlowId(), Integer.parseInt(FlowConstant.OFFICE_SUBSYSTEM+FlowConstant.OFFICE_GO_OUT), officeGoOut.getId(), userId, variables));
		update(officeGoOut);
		
		officeConvertFlowService.startFlow(officeGoOut, ConvertFlowConstants.OFFICE_GO_OUT);
		officeFlowSendMsgService.startFlowSendMsg(officeGoOut, ConvertFlowConstants.OFFICE_GO_OUT);
	}
	@Override
	public List<OfficeGoOut> getStatistics(String unitId,Date startTime,Date endTime,String deptId){
		List<User> userList=userService.getUsersByDeptId(deptId);
		String[] userIds=null;
		if(CollectionUtils.isNotEmpty(userList)){
			int length=userList.size();
			userIds=new String[length];
			for(int i=0;i<length;i++){
				userIds[i]=userList.get(i).getId();
			}
		}
		if(CollectionUtils.isEmpty(userList)&&StringUtils.isNotEmpty(deptId)){			
			userIds=new String[]{" "};
		}
		List<OfficeGoOut> goOutList=officeGoOutDao.getStatistics(unitId,startTime, endTime, userIds);
		List<OfficeGoOut> officeList=new ArrayList<OfficeGoOut>();
		if(CollectionUtils.isNotEmpty(goOutList)){
			Map<String,User> mapUser=userService.getUserMap(unitId);
			boolean flag=MapUtils.isNotEmpty(mapUser);
			
			Map<String,OfficeGoOut> map=new HashMap<String, OfficeGoOut>();
			for(OfficeGoOut goOut:goOutList){
				String applyUserId=goOut.getApplyUserId();
				OfficeGoOut mapGoOut=map.get(applyUserId);
					if(mapGoOut==null){
						if(StringUtils.isNotEmpty(goOut.getOutType())){
							if(goOut.getOutType().equals(OfficeBusinessTripConstants.OUT_TYPE_JOB)){
								goOut.setNumJob(goOut.getOutNum());
								goOut.setHoursJob(goOut.getSumHours());
							}else{
								goOut.setNumSelf(goOut.getOutNum());
								goOut.setHoursSelf(goOut.getSumHours());
							}
						}
						map.put(applyUserId,goOut);
					}else{
						if(StringUtils.isNotEmpty(goOut.getOutType())){
							if(goOut.getOutType().equals(OfficeBusinessTripConstants.OUT_TYPE_JOB)){
								mapGoOut.setNumJob(goOut.getOutNum());
								mapGoOut.setHoursJob(goOut.getSumHours());
							}else{
								mapGoOut.setNumSelf(goOut.getOutNum());
								mapGoOut.setHoursSelf(goOut.getSumHours());
							}
						}
						mapGoOut.setOutNum(mapGoOut.getOutNum()+goOut.getOutNum());
						mapGoOut.setSumHours(mapGoOut.getSumHours()+goOut.getSumHours());
						map.put(applyUserId,mapGoOut);
					}
			}
			for(String key:map.keySet()){
				officeList.add(map.get(key));
			}
			for(OfficeGoOut office:officeList){
				if(flag){
					User user=mapUser.get(office.getApplyUserId());
					office.setUserName(user==null?"":user.getRealname());
				}
			}
		}
		/*if(countMap != null && countMap.size()>0){
			for(String key:countMap.keySet()){
				OfficeGoOut officeGoOut=new OfficeGoOut();
				officeGoOut.setApplyUserId(key);
				officeGoOut.setSum(countMap.get(key));
				officeList.add(officeGoOut);
			}
			Iterator<OfficeGoOut> it=officeList.iterator();
			while(it.hasNext()){
				User user=map.get(it.next().getApplyUserId());
				if(user==null){
					it.remove();
				}
			}
			for(OfficeGoOut office:officeList){
				if(flag){
					User user=map.get(office.getApplyUserId());
					office.setUserName(user==null?"":user.getRealname());
				}
			}
		}*/
		return officeList;
	}
	@Override
	public void update(OfficeGoOut officeGoOut, UploadFile file) {
		officeGoOutDao.update(officeGoOut);
		List<Attachment> attachments = attachmentService.getAttachments(officeGoOut.getId(), Constants.OFFICE_BUSINESS_TRIP_ATT);
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
				attachment.setUnitId(officeGoOut.getUnitId());
				attachment.setObjectId(officeGoOut.getId());
				attachment.setObjectType(Constants.OFFICE_BUSINESS_TRIP_ATT);
				attachmentService.saveAttachment(attachment, file);
			}
		}
		if(StringUtils.isBlank(officeGoOut.getUploadContentFileInput())&&file==null){
			String[] attachmentIds = new String[attachments.size()];
			for(int i = 0; i < attachments.size(); i++){
				attachmentIds[i] = attachments.get(i).getId();
			}
			attachmentService.deleteAttachments(attachmentIds);
		}
		
	}

	@Override
	public void add(OfficeGoOut officeGoOut, UploadFile file) {
		officeGoOutDao.save(officeGoOut);
		if(file!=null){
			Attachment attachment = new Attachment();
			attachment.setFileName(file.getFileName());
			attachment.setContentType(file.getContentType());
			attachment.setFileSize(file.getFileSize());
			attachment.setUnitId(officeGoOut.getUnitId());
			attachment.setObjectId(officeGoOut.getId());
			attachment.setObjectType(Constants.OFFICE_BUSINESS_TRIP_ATT);
			attachmentService.saveAttachment(attachment, file);
		}
	}
	@Override
	public List<OfficeGoOut> toDoAudit(String userId, Pagination page) {
		List<OfficeGoOut> offList = new ArrayList<OfficeGoOut>();
		List<TaskDescription> todoTaskList = new ArrayList<TaskDescription>();
		todoTaskList = taskHandlerService.getTodoTasks(userId, Integer.parseInt(FlowConstant.OFFICE_SUBSYSTEM+FlowConstant.OFFICE_GO_OUT), page);
		if(CollectionUtils.isNotEmpty(todoTaskList)){
			Set<String> flowIdSet= new HashSet<String>();
			for (TaskDescription task : todoTaskList) {
				flowIdSet.add(task.getProcessInstanceId());	
			}
			Map<String,OfficeGoOut> leaveMap = officeGoOutDao.getOfficeBusinessTripMapByFlowIds(flowIdSet.toArray(new String[0]));
			Set<String> userIdSet = new HashSet<String>();
			for (String leaveId : leaveMap.keySet()) {
				OfficeGoOut officeGoOut = leaveMap.get(leaveId);
				if(officeGoOut!=null){
					userIdSet.add(officeGoOut.getApplyUserId());
				}
			}
			Map<String,User> userMap = new HashMap<String, User>();
			userMap = userService.getUserWithDelMap(userIdSet.toArray(new String[0]));
			for (TaskDescription task : todoTaskList) {
				OfficeGoOut officeGoOut = leaveMap.get(task.getProcessInstanceId());
				if(officeGoOut==null){
					continue;
				}
				officeGoOut.setTaskId(task.getTaskId());
				officeGoOut.setTaskName(task.getTaskName());
				User user = userMap.get(officeGoOut.getApplyUserId());
				if(user!=null){
					officeGoOut.setUserName(user.getRealname());
				}else{
					officeGoOut.setUserName("用户已删除");
				}
				offList.add(officeGoOut);
			}
		}
		Collections.sort(offList, new Comparator<OfficeGoOut>(){

			@Override
			public int compare(OfficeGoOut o1, OfficeGoOut o2) {
				return o1.getCreateTime().compareTo(o1.getCreateTime());
			}
			
		});
		return offList;
	}

	@Override
	public List<OfficeGoOut> HaveDoAudit(String userId,boolean invalid,Pagination page) {
		List<OfficeGoOut>  offList = officeGoOutDao.HaveDoAudit(userId,invalid,page);
		Set<String> userIdSet = new HashSet<String>();
		for (OfficeGoOut officeGoOut : offList) {
			userIdSet.add(officeGoOut.getApplyUserId());
			if(!invalid){
				userIdSet.add(officeGoOut.getInvalidUser());
			}
		}
		Map<String,User> userMap = new HashMap<String, User>();
		userMap = userService.getUserWithDelMap(userIdSet.toArray(new String[0]));
		for (OfficeGoOut officeGoOut : offList) {
			User user = userMap.get(officeGoOut.getApplyUserId());
			if(user!=null){
				officeGoOut.setUserName(user.getRealname());
			}else{
				officeGoOut.setUserName("用户已删除");
			}
			if(StringUtils.isNotBlank(officeGoOut.getInvalidUser())){
				User user2 = userMap.get(officeGoOut.getInvalidUser());
				if(user2!=null){
					officeGoOut.setInvalidUserName(user2.getRealname());
				}else{
					officeGoOut.setInvalidUserName("用户已删除");
				}
			}
		}
		return offList;
	}
	@Override
	public void passFlow(boolean pass, TaskHandlerSave taskHandlerSave,
			String leaveId) {
		OfficeGoOut leave = officeGoOutDao.getOfficeGoOutById(leaveId);
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
		officeFlowSendMsgService.completeTaskSendMsg(taskHandlerSave.getCurrentUserId(), pass, leave, ConvertFlowConstants.OFFICE_GO_OUT, result);
	}
	
	@Override
	public void deleteInvalid(String id,String userId) {
		OfficeGoOut officeGoOut = officeGoOutDao.getOfficeGoOutById(id);
		if(officeGoOut!=null){
			officeGoOut.setState(String.valueOf(Constants.APPLY_STATE_INVALID));
			officeGoOut.setInvalidUser(userId);
			update(officeGoOut);
		}
		officeConvertFlowService.update(Constants.APPLY_STATE_INVALID, id);
	}
	public void setProcessHandlerService(ProcessHandlerService processHandlerService) {
		this.processHandlerService = processHandlerService;
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

	public void setOfficeConvertFlowTaskService(
			OfficeConvertFlowTaskService officeConvertFlowTaskService) {
		this.officeConvertFlowTaskService = officeConvertFlowTaskService;
	}
	
}
