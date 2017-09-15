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

import net.sf.json.JSONObject;
import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.eis.base.attachment.service.AttachmentService;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.component.flowManage.constant.FlowConstant;
import net.zdsoft.eis.component.flowManage.entity.Flow;
import net.zdsoft.eis.component.flowManage.service.FlowManageService;
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
import net.zdsoft.office.dailyoffice.dao.OfficeBusinessTripDao;
import net.zdsoft.office.dailyoffice.entity.OfficeBusinessTrip;
import net.zdsoft.office.dailyoffice.entity.OfficeLog;
import net.zdsoft.office.dailyoffice.service.OfficeBusinessTripService;
import net.zdsoft.office.dailyoffice.service.OfficeLogService;
import net.zdsoft.office.officeFlow.dto.HisTask;
import net.zdsoft.office.officeFlow.service.OfficeFlowService;
import net.zdsoft.office.officeFlow.service.OfficeFlowStepInfoService;
import net.zdsoft.office.util.Constants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
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
	private OfficeLogService officeLogService;
	private TeacherService teacherService;
	private DeptService deptService;	
	private OfficeFlowService officeFlowService;
	private OfficeFlowStepInfoService officeFlowStepInfoService;
	
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
	public OfficeBusinessTrip getOfficeJtGooutByUnitIdAndUserId(
			OfficeBusinessTrip officeBusinessTrip, String unitId, String userId) {
		List<Flow> flowList=new ArrayList<Flow>();
		List<Flow> flowLists = flowManageService.getFinishFlowList(unitId, FlowConstant.FLOW_OWNER_UNIT, 
				FlowConstant.OFFICE_BUSINESS_TRIP,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
		
		User user = userService.getUser(userId);
		if(user != null && StringUtils.isNotBlank(user.getDeptid())){
			List<Flow> flowList2  = flowManageService.getFinishFlowList(user.getDeptid(), FlowConstant.FLOW_OWNER_STRING, 
					FlowConstant.OFFICE_BUSINESS_TRIP,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
			if(CollectionUtils.isNotEmpty(flowList2)){
				flowLists.addAll(flowList2);
			}
		}
		Map<String,Flow> flowMap=new HashMap<String, Flow>();
		for (Flow flow : flowLists) {
			flowMap.put(flow.getFlowId(), flow);
		}
		
		List<OfficeLog> officeLogs=officeLogService.getOfficeList(unitId, userId, String.valueOf(Constants.BUSSINESS_MOD_ID), Constants.LOG_APPLY);
		if(CollectionUtils.isNotEmpty(officeLogs)){
			OfficeLog officeLog=officeLogs.get(0);
			String descr=officeLog.getDescription();
			JSONObject js=JSONObject.fromObject(descr);
			String lastFlowId=(String) js.get("flowId");
			Map<String,Integer> desMap=(Map<String, Integer>) js.get("flowNumMap");
			if(MapUtils.isNotEmpty(desMap)){
				List<String> flowIds=new ArrayList<String>();
				Iterator<String> it=desMap.keySet().iterator();
				while(it.hasNext()){
					flowIds.add((String) it.next());
				}
				String[] flowIdArr=flowIds.toArray(new String[0]);
				if(ArrayUtils.isNotEmpty(flowIdArr)){
					for (int i = 0; i < flowIdArr.length; i++) {
						for (int j = i; j < flowIdArr.length; j++) {
							if(desMap.get(flowIdArr[i])<desMap.get(flowIdArr[j])){
								String flowId=flowIdArr[j];
								flowIdArr[j]=flowIdArr[i];
								flowIdArr[i]=flowId;
							}
						}
					}
				}
				for (String string : desMap.keySet()) {
					if(flowMap.containsKey(string)){
						Flow flow=flowMap.get(string);
						if(flow!=null){
							flowList.add(flow);
						}
					}
				}
				for (String string : flowMap.keySet()) {
					if(!desMap.containsKey(string)){
						flowList.add(flowMap.get(string));
					}
				}
			}
			officeBusinessTrip.setFlowId(lastFlowId);
			officeBusinessTrip.setFlows(flowList);
		}
		return officeBusinessTrip;
	}

	@Override
	public OfficeBusinessTrip getOfficeBusinessTripById(String id){
		OfficeBusinessTrip officeBusinessTrip =officeBusinessTripDao.getOfficeBusinessTripById(id);
		if(officeBusinessTrip!=null){
			User user = userService.getUser(officeBusinessTrip.getApplyUserId());
			Teacher teacher=teacherService.getTeacher(user.getTeacherid());
			if(teacher!=null){
				Dept dept=deptService.getDept(teacher.getDeptid());
				if(dept!=null){
					officeBusinessTrip.setDeptName(dept.getDeptname());
				}
			}
			if(user!=null){
				officeBusinessTrip.setApplyUserName(user.getRealname());
			}
			if(StringUtils.isNotEmpty(officeBusinessTrip.getFlowId())){
				List<HisTask> hisTask = officeFlowService.getHisTask(officeBusinessTrip.getFlowId());
				officeBusinessTrip.setHisTaskList(hisTask);
			}
			officeBusinessTrip.setAttachments(attachmentService.getAttachments(officeBusinessTrip.getId(), Constants.OFFICE_BUSINESS_TRIP_ATT));
		}
		return officeBusinessTrip;
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
	public void startFlow(OfficeBusinessTrip officeBusinessTrip, String userId,
			 List<UploadFile> files,boolean isMobile,String[] removeAttachment) {
		if(StringUtils.isBlank(officeBusinessTrip.getId())){
			officeBusinessTrip.setId(UUIDUtils.newId());
		}
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("pass",true);
		String flowId = processHandlerService.startProcessInstance(FlowConstant.OFFICE_SUBSYSTEM,officeBusinessTrip.getFlowId(), Integer.parseInt(FlowConstant.OFFICE_SUBSYSTEM+FlowConstant.OFFICE_BUSINESS_TRIP), officeBusinessTrip.getId(), userId, variables);
		officeFlowStepInfoService.batchUpdateByFlowId(officeBusinessTrip.getFlowId(), flowId);
		officeBusinessTrip.setFlowId(flowId);
		officeBusinessTrip.setCreateTime(new Date());
		
		OfficeBusinessTrip office=officeBusinessTripDao.getOfficeBusinessTripById(officeBusinessTrip.getId());
		if(office!=null){
			update(officeBusinessTrip,files,isMobile,removeAttachment);
			saveOfficeLog(officeBusinessTrip);
		}else{
			add(officeBusinessTrip,files,isMobile);
		}
		//update(officeBusinessTrip);
		
		officeConvertFlowService.startFlow(officeBusinessTrip, ConvertFlowConstants.OFFICE_EVECTION);
		officeFlowSendMsgService.startFlowSendMsg(officeBusinessTrip, ConvertFlowConstants.OFFICE_EVECTION);
	}
	
	public void saveOfficeLog(OfficeBusinessTrip officeBusinessTrip){
		OfficeLog officeLog;
		Map<String,Integer> map;
		JSONObject json;
		List<OfficeLog> logList=officeLogService.getOfficeList(officeBusinessTrip.getUnitId(), 
				officeBusinessTrip.getApplyUserId(),String.valueOf(Constants.BUSSINESS_MOD_ID),Constants.LOG_APPLY);
		if(CollectionUtils.isNotEmpty(logList)){
			officeLog=logList.get(0);
			String desciription=officeLog.getDescription();
			json=JSONObject.fromObject(desciription);
			map=(Map<String, Integer>) json.get("flowNumMap");
			if(MapUtils.isNotEmpty(map)){
				Iterator<String> it=map.keySet().iterator();
				boolean flag=false;
				while(it.hasNext()){
					String flowId=it.next();
					if(flowId.equals(officeBusinessTrip.getFlowId())){
						int num=map.get(flowId);
						map.put(flowId, num+1);
						flag=true;
					}
				}
				if(!flag){
					map.put(officeBusinessTrip.getFlowId(),1);
				}
			}
			json.put("flowNumMap", map);
			json.put("flowId",officeBusinessTrip.getFlowId());
			
			officeLog.setDescription(json.toString());
			officeLogService.update(officeLog);
		}else {
			officeLog=new OfficeLog();
			officeLog.setUnitId(officeBusinessTrip.getUnitId());
			officeLog.setUserId(officeBusinessTrip.getApplyUserId());
			officeLog.setModid(String.valueOf(Constants.BUSSINESS_MOD_ID));
			officeLog.setCode(Constants.LOG_APPLY);
			officeLog.setLogtime(new Date());
			
			json=new JSONObject();
			map=new HashMap<String, Integer>();
			map.put(officeBusinessTrip.getFlowId(),1);
			json.put("flowNumMap", map);
			json.put("flowId",officeBusinessTrip.getFlowId());
			
			officeLog.setDescription(json.toString());
			officeLogService.save(officeLog);
		}
	}
	
	@Override
	public void changeFlow(String businessTripId, String userId, String modelId,
			String jsonResult) {
		OfficeBusinessTrip officeBusinessTrip=officeBusinessTripDao.getOfficeBusinessTripById(businessTripId);
		officeBusinessTrip.setState(String.valueOf(Constants.APPLY_STATE_NEED_AUDIT));
		
		Map<String,Object> variables=new HashMap<String, Object>();
		variables.put("pass", true);
		String flowId=processHandlerService.startProcessInstance(FlowConstant.OFFICE_SUBSYSTEM, modelId, Integer.parseInt(FlowConstant.OFFICE_SUBSYSTEM+FlowConstant.OFFICE_BUSINESS_TRIP), businessTripId, userId,jsonResult, variables);
		officeFlowStepInfoService.batchUpdateByFlowId(modelId, flowId);
		officeBusinessTrip.setFlowId(flowId);
		update(officeBusinessTrip);
		
		officeConvertFlowService.startFlow(officeBusinessTrip, ConvertFlowConstants.OFFICE_EVECTION);
		officeFlowSendMsgService.startFlowSendMsg(officeBusinessTrip, ConvertFlowConstants.OFFICE_EVECTION);
		
	}

	@Override
	public void update(OfficeBusinessTrip officeBusinessTrip,  List<UploadFile> files,boolean isMobile,String[] removeAttachment) {
		officeBusinessTripDao.update(officeBusinessTrip);
		
		if (isMobile && !CollectionUtils.isEmpty(files)) {
			if (ArrayUtils.isNotEmpty(removeAttachment)) {
				attachmentService.deleteAttachments(removeAttachment);
			}
			for (UploadFile uploadFile : files) {
				Attachment attachment = new Attachment();
				attachment.setFileName(uploadFile.getFileName());
				attachment.setContentType(uploadFile.getContentType());
				attachment.setFileSize(uploadFile.getFileSize());
				attachment.setUnitId(officeBusinessTrip.getUnitId());
				attachment.setObjectId(officeBusinessTrip.getId());
				attachment.setObjectType(Constants.OFFICE_BUSINESS_TRIP_ATT);
				attachmentService.saveAttachment(attachment, uploadFile, false);
			}
		}
		/*List<Attachment> attachments = attachmentService.getAttachments(officeBusinessTrip.getId(), Constants.OFFICE_BUSINESS_TRIP_ATT);
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
		}*/
	}

	@Override
	public void add(OfficeBusinessTrip officeBusinessTrip,  List<UploadFile> files,boolean isMobile) {
		officeBusinessTripDao.save(officeBusinessTrip);
		if (isMobile && !CollectionUtils.isEmpty(files)) {
			for (UploadFile uploadFile : files) {
				Attachment attachment = new Attachment();
				attachment.setFileName(uploadFile.getFileName());
				attachment.setContentType(uploadFile.getContentType());
				attachment.setFileSize(uploadFile.getFileSize());
				attachment.setUnitId(officeBusinessTrip.getUnitId());
				attachment.setObjectId(officeBusinessTrip.getId());
				attachment.setObjectType(Constants.OFFICE_BUSINESS_TRIP_ATT);
				attachmentService.saveAttachment(attachment, uploadFile);
			}
		}
		/*if(file!=null){
			Attachment attachment = new Attachment();
			attachment.setFileName(file.getFileName());
			attachment.setContentType(file.getContentType());
			attachment.setFileSize(file.getFileSize());
			attachment.setUnitId(officeBusinessTrip.getUnitId());
			attachment.setObjectId(officeBusinessTrip.getId());
			attachment.setObjectType(Constants.OFFICE_BUSINESS_TRIP_ATT);
			attachmentService.saveAttachment(attachment, file);
		}*/
		
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
			for (TaskDescription task : todoTaskList) {
				OfficeBusinessTrip officeBusinessTrip = leaveMap.get(task.getProcessInstanceId());
				if(officeBusinessTrip==null){
					continue;
				}
				officeBusinessTrip.setTaskId(task.getTaskId());
				officeBusinessTrip.setTaskName(task.getTaskName());
				
				offList.add(officeBusinessTrip);
			}
			offList = this.setInfo(offList);
		}
		Collections.sort(offList,new Comparator<OfficeBusinessTrip>(){

			@Override
			public int compare(OfficeBusinessTrip o1, OfficeBusinessTrip o2) {
				if(o1.getCreateTime() == null){
					return 1;
				}
				if(o2.getCreateTime() == null){
					return -1;
				}
				return o2.getCreateTime().compareTo(o1.getCreateTime());
			}});
		return offList;
	}

	/**
	 * 为list设置如姓名、部门等信息
	 * @param list
	 * @return
	 */
	@Override
	public List<OfficeBusinessTrip> setInfo(List<OfficeBusinessTrip> list){
		if(CollectionUtils.isNotEmpty(list)){
			Set<String> userIdSet = new HashSet<String>();
			Set<String> teacherIdSet=new HashSet<String>();
			Set<String> deptIdSet=new HashSet<String>();
			
			for (OfficeBusinessTrip officeBusinessTrip : list) {
				userIdSet.add(officeBusinessTrip.getApplyUserId());
				if(StringUtils.isNotBlank(officeBusinessTrip.getInvalidUser())){
					userIdSet.add(officeBusinessTrip.getInvalidUser());
				}
			}
			Map<String,User> userMap = userService.getUserWithDelMap(userIdSet.toArray(new String[0]));
			
			for(String userId : userMap.keySet()){
				User user = userMap.get(userId);
				if(user != null){
					teacherIdSet.add(user.getTeacherid());
				}
			}
			Map<String,Teacher> teacherMap=teacherService.getTeacherMap(teacherIdSet.toArray(new String[0]));
			
			for(String teacherId : teacherMap.keySet()){
				Teacher teacher = teacherMap.get(teacherId);
				deptIdSet.add(teacher.getDeptid());
			}
			Map<String, Dept> deptMap = deptService.getDeptMap(deptIdSet.toArray(new String[0]));
			
			for (OfficeBusinessTrip officeBusinessTrip : list) {
				User user=userMap.get(officeBusinessTrip.getApplyUserId());
				if(user!=null){
					officeBusinessTrip.setApplyUserName(user.getRealname());
				}else{
					officeBusinessTrip.setApplyUserName("用户已删除");
				}
				
				if(StringUtils.isNotBlank(officeBusinessTrip.getInvalidUser())){
					User user2=userMap.get(officeBusinessTrip.getInvalidUser());
					if(user2!=null){
						officeBusinessTrip.setInvalidUserName(user2.getRealname());
					}else{
						officeBusinessTrip.setInvalidUserName("用户已删除");
					}
				}
					
				if(user!=null&&teacherMap.containsKey(user.getTeacherid())){
					Teacher teacher=teacherMap.get(user.getTeacherid());
					if(teacher!=null&&deptMap.containsKey(teacher.getDeptid())){
						Dept dept=deptMap.get(teacher.getDeptid());
						if(dept!=null){
							officeBusinessTrip.setDeptName(dept.getDeptname());
						}
					}
				}else{
					officeBusinessTrip.setDeptName("无");
				}
			}
		}
		return list;
	}
	
	@Override
	public List<OfficeBusinessTrip> HaveDoAudit(String userId,boolean invalid, Pagination page) {
		List<OfficeBusinessTrip>  offList = officeBusinessTripDao.HaveDoAudit(userId,invalid,page);
		offList = this.setInfo(offList);
		return offList;
	}
	public void saveTextComment(TaskHandlerSave taskHandlerSave){
		OfficeLog officeLog;
		List<OfficeLog> logList=officeLogService.getOfficeList(taskHandlerSave.getCurrentUnitId(), 
				taskHandlerSave.getCurrentUserId(),String.valueOf(Constants.BUSSINESS_MOD_ID),Constants.LOG_AUDIT);
		if(CollectionUtils.isNotEmpty(logList)){
			officeLog=logList.get(0);
			officeLog.setDescription(taskHandlerSave.getComment().getTextComment());
			officeLogService.update(officeLog);
		}else {
			officeLog=new OfficeLog();
			officeLog.setUnitId(taskHandlerSave.getCurrentUnitId());
			officeLog.setUserId(taskHandlerSave.getCurrentUserId());
			officeLog.setModid(String.valueOf(Constants.BUSSINESS_MOD_ID));
			officeLog.setCode(Constants.LOG_AUDIT);
			officeLog.setLogtime(new Date());
			officeLog.setDescription(taskHandlerSave.getComment().getTextComment());
			officeLogService.save(officeLog);
		}
	}
	@Override
	public void passFlow(boolean pass, TaskHandlerSave taskHandlerSave,
			String leaveId, String currentStepId) {
		OfficeBusinessTrip leave = officeBusinessTripDao.getOfficeBusinessTripById(leaveId);
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("pass", pass);
		variables.put("days", leave.getDays());
		taskHandlerSave.setVariables(variables);
		TaskHandlerResult result;
		saveTextComment(taskHandlerSave);
		if(pass){
			taskHandlerSave.getComment().setTextComment("[审核通过]"+taskHandlerSave.getComment().getTextComment());
			result = taskHandlerService.completeTask(taskHandlerSave);
		}else{
			taskHandlerSave.getComment().setTextComment("[审核不通过]"+taskHandlerSave.getComment().getTextComment());
			leave.setState(String.valueOf(Constants.LEAVE_APPLY_FLOW_FINSH_NOT_PASS));
			update(leave);
			boolean isNotFinish = taskHandlerService.isExclusiveGatewayForNext(leave.getUnitId(), taskHandlerSave.getSubsystemId(), taskHandlerSave.getCurrentTask().getTaskDefinitionKey(), leave.getFlowId());
			if(isNotFinish){
				result = taskHandlerService.completeTask(taskHandlerSave);
			}else{
				result = taskHandlerService.suspendTask(taskHandlerSave);
			}
		}
		if(result.getStatus()==TaskHandlerResult.STATUS_FINISH){
			if(result.getResult()==TaskHandlerResult.RESULT_PASS){
				leave.setState(String.valueOf(Constants.LEAVE_APPLY_FLOW_FINSH_PASS));
			}else if(result.getResult()==TaskHandlerResult.RESULT_NOT_PASS){
				leave.setState(String.valueOf(Constants.LEAVE_APPLY_FLOW_FINSH_NOT_PASS));
			}
			update(leave);
		}
		
		officeConvertFlowService.completeTask(leaveId, leave.getFlowId(), taskHandlerSave.getCurrentUserId(), taskHandlerSave.getCurrentTask().getTaskId(), result, pass);
		if(result.getStatus()==TaskHandlerResult.STATUS_FINISH
				|| officeFlowService.checkSendFlowMsg(leave.getFlowId(), currentStepId)){
			officeFlowSendMsgService.completeTaskSendMsg(taskHandlerSave.getCurrentUserId(), pass, leave, ConvertFlowConstants.OFFICE_EVECTION, result);
		}
	}
	
	public List<OfficeBusinessTrip> getListByStarttimeAndEndtime(Date startTime,Date endTime,String[] userIds){
		return officeBusinessTripDao.getListByStarttimeAndEndtime(startTime, endTime, userIds);
	}
	
	@Override
	public void revokeBusinessTrip(String businessTripId) {
		officeConvertFlowService.deleteFlow(businessTripId);
		delete(new String[]{businessTripId});
	}

	@Override
	public void invalidBusinessTrip(String businessTrip, String userId) {
		OfficeBusinessTrip officeBusinessTrip=officeBusinessTripDao.getOfficeBusinessTripById(businessTrip);
		if(officeBusinessTrip!=null){
			officeBusinessTrip.setState(String.valueOf(Constants.APPLY_STATE_INVALID));
			officeBusinessTrip.setInvalidUser(userId);
			update(officeBusinessTrip);
		}
		officeConvertFlowService.update(Constants.APPLY_STATE_INVALID, businessTrip);
	}

	@Override
	public List<OfficeBusinessTrip> getOfficeBusinessTripsByUnitIdAndOthers(
			String unitId, String state, Date startTime, Date endTime,
			String applyUserName,Pagination page) {
		List<OfficeBusinessTrip> officeBusinessTrips=officeBusinessTripDao.getOfficeBusinessTripsByUnitIdAndOthers(unitId, state, startTime, endTime, applyUserName, page);
		officeBusinessTrips = this.setInfo(officeBusinessTrips);
		Collections.sort(officeBusinessTrips, new Comparator<OfficeBusinessTrip>(){
			@Override
			public int compare(OfficeBusinessTrip o1, OfficeBusinessTrip o2) {
				if(o1.getCreateTime()==null){
					return 1;
				}
				if(o2.getCreateTime()==null){
					return -1;
				}
				return o1.getCreateTime().compareTo(o2.getCreateTime());
			}
			
		});
		return officeBusinessTrips;
	}

	@Override
	public List<OfficeBusinessTrip> getListByUnitIdAndDate(String unitId,
			Date date) {
		return officeBusinessTripDao.getListByUnitIdAndDate(unitId, date);
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

	public void setOfficeLogService(OfficeLogService officeLogService) {
		this.officeLogService = officeLogService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	public void setOfficeFlowService(OfficeFlowService officeFlowService) {
		this.officeFlowService = officeFlowService;
	}

	public void setOfficeFlowStepInfoService(
			OfficeFlowStepInfoService officeFlowStepInfoService) {
		this.officeFlowStepInfoService = officeFlowStepInfoService;
	}	
}
