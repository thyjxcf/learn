package net.zdsoft.office.jtgoout.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.component.flowManage.constant.FlowConstant;
import net.zdsoft.eis.component.flowManage.entity.Flow;
import net.zdsoft.eis.component.flowManage.service.FlowManageService;
import net.zdsoft.jbpm.activiti.engine.task.AgileIdentityLinkType;
import net.zdsoft.jbpm.core.entity.Comment;
import net.zdsoft.jbpm.core.entity.HistoricTask;
import net.zdsoft.jbpm.core.entity.TaskDescription;
import net.zdsoft.jbpm.core.entity.TaskHandlerResult;
import net.zdsoft.jbpm.core.entity.TaskHandlerSave;
import net.zdsoft.jbpm.core.entity.TaskHandlerShow;
import net.zdsoft.jbpm.core.service.ProcessHandlerService;
import net.zdsoft.jbpm.core.service.TaskHandlerService;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keel.util.UUIDUtils;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.leadin.common.entity.BusinessTask;
import net.zdsoft.office.convertflow.constant.ConvertFlowConstants;
import net.zdsoft.office.convertflow.entity.OfficeConvertFlow;
import net.zdsoft.office.convertflow.service.OfficeConvertFlowService;
import net.zdsoft.office.convertflow.service.OfficeConvertFlowTaskService;
import net.zdsoft.office.convertflow.service.OfficeFlowSendMsgService;
import net.zdsoft.office.dailyoffice.constant.OfficeBusinessTripConstants;
import net.zdsoft.office.dailyoffice.entity.OfficeLog;
import net.zdsoft.office.dailyoffice.service.OfficeLogService;
import net.zdsoft.office.jtgoout.dao.GooutStudentExDao;
import net.zdsoft.office.jtgoout.dao.GooutTeacherExDao;
import net.zdsoft.office.jtgoout.dao.OfficeJtGooutDao;
import net.zdsoft.office.jtgoout.entity.GooutStudentEx;
import net.zdsoft.office.jtgoout.entity.GooutTeacherEx;
import net.zdsoft.office.jtgoout.entity.OfficeJtGoout;
import net.zdsoft.office.jtgoout.service.GooutStudentExService;
import net.zdsoft.office.jtgoout.service.GooutTeacherExService;
import net.zdsoft.office.jtgoout.service.OfficeJtGooutService;
import net.zdsoft.office.officeFlow.dto.HisTask;
import net.zdsoft.office.officeFlow.service.OfficeFlowService;
import net.zdsoft.office.officeFlow.service.OfficeFlowStepInfoService;
import net.zdsoft.office.util.Constants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
/**
 * office_jt_goout
 * @author 
 * 
 */
public class OfficeJtGooutServiceImpl implements OfficeJtGooutService{
	private OfficeJtGooutDao officeJtGooutDao;
	private GooutStudentExDao gooutStudentExDao;
	private GooutTeacherExDao gooutTeacherExDao;
	private ProcessHandlerService processHandlerService;
	private OfficeFlowSendMsgService officeFlowSendMsgService;
	private AttachmentService attachmentService;
	private TaskHandlerService taskHandlerService;
	private UserService userService;
	private UnitService unitService;
	private OfficeLogService officeLogService;
	private FlowManageService flowManageService;
	private OfficeConvertFlowService officeConvertFlowService;
	private OfficeConvertFlowTaskService officeConvertFlowTaskService;
	private OfficeFlowService officeFlowService;
	private GooutTeacherExService gooutTeacherExService;
	private GooutStudentExService gooutStudentExService;
	private OfficeFlowStepInfoService officeFlowStepInfoService;
	
	@Override
	public OfficeJtGoout save(OfficeJtGoout officeJtGoout){
		return officeJtGooutDao.save(officeJtGoout);
	}

	@Override
	public void save(OfficeJtGoout officeJtGoout,
			GooutStudentEx gooutStudentEx, GooutTeacherEx gooutTeacherEx,List<UploadFile> files) {
		if(StringUtils.isNotBlank(officeJtGoout.getType())&&StringUtils.equals("2", officeJtGoout.getType())){
			OfficeJtGoout out = officeJtGooutDao.save(officeJtGoout);
			gooutTeacherEx.setJtgooutId(out.getId());
			gooutTeacherEx.setUnitId(officeJtGoout.getUnitId());
			gooutTeacherExDao.save(gooutTeacherEx);
		}
		if(StringUtils.isNotBlank(officeJtGoout.getType())&&StringUtils.equals("1", officeJtGoout.getType())){
			OfficeJtGoout out=officeJtGooutDao.save(officeJtGoout);
			gooutStudentEx.setJtgooutId(out.getId());
			gooutStudentEx.setUnitId(officeJtGoout.getUnitId());
			gooutStudentExDao.save(gooutStudentEx);
		}
		if (!CollectionUtils.isEmpty(files)) {
			for (UploadFile uploadFile : files) {
				Attachment attachment = new Attachment();
				attachment.setFileName(uploadFile.getFileName());
				attachment.setContentType(uploadFile.getContentType());
				attachment.setFileSize(uploadFile.getFileSize());
				attachment.setUnitId(officeJtGoout.getUnitId());
				attachment.setObjectId(officeJtGoout.getId());
				attachment.setObjectType(Constants.OFFICE_JTGO_OUT_ATT);
				attachment.setConStatus(BusinessTask.TASK_STATUS_NOT_NEED_HAND);
				attachmentService.saveAttachment(attachment, uploadFile, false);
			}
		}
	}

	@Override
	public void update(OfficeJtGoout officeJtGoout,
			GooutStudentEx gooutStudentEx, GooutTeacherEx gooutTeacherEx,List<UploadFile> files,String[] removeAttachmentArray) {
		if(StringUtils.isNotBlank(officeJtGoout.getType())&&StringUtils.equals("1", officeJtGoout.getType())){
			officeJtGooutDao.update(officeJtGoout);
			gooutStudentExDao.deleteByJtGoOutId(officeJtGoout.getId());
			gooutTeacherExDao.deleteByjtGooutId(officeJtGoout.getId());
			gooutStudentEx.setJtgooutId(officeJtGoout.getId());
			gooutStudentEx.setUnitId(officeJtGoout.getUnitId());
			gooutStudentExDao.save(gooutStudentEx);
		}
		if(StringUtils.isNotBlank(officeJtGoout.getType())&&StringUtils.equals("2", officeJtGoout.getType())){
			officeJtGooutDao.update(officeJtGoout);
			gooutTeacherExDao.deleteByjtGooutId(officeJtGoout.getId());
			gooutStudentExDao.deleteByJtGoOutId(officeJtGoout.getId());
			gooutTeacherEx.setJtgooutId(officeJtGoout.getId());
			gooutTeacherEx.setUnitId(officeJtGoout.getUnitId());
			gooutTeacherExDao.save(gooutTeacherEx);
		}
		if (!CollectionUtils.isEmpty(files)) {
			if (ArrayUtils.isNotEmpty(removeAttachmentArray)) {
				attachmentService.deleteAttachments(removeAttachmentArray);
			}
			for (UploadFile uploadFile : files) {
				Attachment attachment = new Attachment();
				attachment.setFileName(uploadFile.getFileName());
				attachment.setContentType(uploadFile.getContentType());
				attachment.setFileSize(uploadFile.getFileSize());
				attachment.setUnitId(officeJtGoout.getUnitId());
				attachment.setObjectId(officeJtGoout.getId());
				attachment.setObjectType(Constants.OFFICE_JTGO_OUT_ATT);
				attachment.setConStatus(BusinessTask.TASK_STATUS_NOT_NEED_HAND);
				attachmentService.saveAttachment(attachment, uploadFile, false);
			}
		}
	}

	@Override
	public void startFlow(OfficeJtGoout officeJtgoOut,GooutStudentEx gooutStudentEx,GooutTeacherEx gooutTeacherEx, String userId,List<UploadFile> files,String[] removeAttachmentArray,Pagination page) {
		if(StringUtils.isBlank(officeJtgoOut.getId())){
			officeJtgoOut.setId(UUIDUtils.newId());
		}
		Map<String,Object> variable=new HashMap<String, Object>();
		variable.put("pass", true);
		String flowId = processHandlerService.startProcessInstance(FlowConstant.OFFICE_SUBSYSTEM, officeJtgoOut.getFlowId(), Integer.parseInt(FlowConstant.OFFICE_SUBSYSTEM+FlowConstant.OFFICE_JT_GOOUT), officeJtgoOut.getId(), userId, variable);
		officeFlowStepInfoService.batchUpdateByFlowId(officeJtgoOut.getFlowId(), flowId);
		officeJtgoOut.setFlowId(flowId);
		officeJtgoOut.setIsDeleted(false);
		officeJtgoOut.setCreateTime(new Date());
		
		OfficeJtGoout officeJtGooutOld=officeJtGooutDao.getOfficeJtGooutById(officeJtgoOut.getId());
		if(officeJtGooutOld!=null){
			officeJtGooutOld.setStartTime(officeJtgoOut.getStartTime());
			officeJtGooutOld.setEndTime(officeJtgoOut.getEndTime());
			officeJtGooutOld.setType(officeJtgoOut.getType());
			officeJtGooutOld.setState(String.valueOf(Constants.APPLY_STATE_NEED_AUDIT));
			officeJtGooutOld.setFlowId(officeJtgoOut.getFlowId());
			update(officeJtGooutOld, gooutStudentEx, gooutTeacherEx,files,removeAttachmentArray);
		}else{
			save(officeJtgoOut, gooutStudentEx, gooutTeacherEx,files);
		}
		this.saveLog(officeJtgoOut);
		//update(officeJtgoOut);
		
		officeConvertFlowService.startFlow(officeJtgoOut, ConvertFlowConstants.OFFICE_NEWJTGO_OUT);
		officeFlowSendMsgService.startFlowSendMsg(officeJtgoOut, ConvertFlowConstants.OFFICE_NEWJTGO_OUT);
	}
	
	@Override
	public OfficeJtGoout getOfficeJtGooutByUnitIdAndUserId(
			OfficeJtGoout officeJtGoout, String unitId, String userId) {
		List<Flow> flowList=new ArrayList<Flow>();
		List<Flow> flowLists = flowManageService.getFinishFlowList(unitId, FlowConstant.FLOW_OWNER_UNIT, 
				FlowConstant.OFFICE_JT_GOOUT,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
		User user = userService.getUser(userId);
		if(user != null && StringUtils.isNotBlank(user.getDeptid())){
			List<Flow> flowList2  = flowManageService.getFinishFlowList(user.getDeptid(), FlowConstant.FLOW_OWNER_STRING, 
					FlowConstant.OFFICE_JT_GOOUT,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
			if(CollectionUtils.isNotEmpty(flowList2)){
				flowList.addAll(flowList2);
			}
		}
		
		Map<String,Flow> flowMap=new HashMap<String, Flow>();
		for (Flow flow : flowLists) {
			flowMap.put(flow.getFlowId(), flow);
		}
		
		List<OfficeLog> officeLogs=officeLogService.getOfficeList(unitId, userId, String.valueOf(Constants.Jt_GOOUT_MOD_ID), Constants.LOG_APPLY);
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
			if(StringUtils.isBlank(officeJtGoout.getFlowId())){
				officeJtGoout.setFlowId(lastFlowId);
			}
			officeJtGoout.setFlows(flowList);
		}
		return officeJtGoout;
	}

	private void saveLog(OfficeJtGoout officeJtGoout){
			OfficeLog officeLog=null;
			Map<String,Integer> map=null;
			JSONObject json=null;
			List<OfficeLog> logList=officeLogService.getOfficeList(officeJtGoout.getUnitId(), 
					officeJtGoout.getApplyUserId(),String.valueOf(Constants.Jt_GOOUT_MOD_ID),Constants.LOG_APPLY);
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
						if(flowId.equals(officeJtGoout.getFlowId())){
							int num=map.get(flowId);
							map.put(flowId, num+1);
							flag=true;
						}
					}
					if(!flag){
						map.put(officeJtGoout.getFlowId(),1);
					}
				}
				json.put("flowNumMap", map);
				json.put("flowId", officeJtGoout.getFlowId());
				
				officeLog.setDescription(json.toString());
				officeLogService.update(officeLog);
			}else {
				officeLog=new OfficeLog();
				officeLog.setUnitId(officeJtGoout.getUnitId());
				officeLog.setUserId(officeJtGoout.getApplyUserId());
				officeLog.setModid(String.valueOf(Constants.Jt_GOOUT_MOD_ID));
				officeLog.setCode(Constants.LOG_APPLY);
				officeLog.setLogtime(new Date());
				
				json=new JSONObject();
				map=new HashMap<String, Integer>();
				map.put(officeJtGoout.getFlowId(),1);
				json.put("flowNumMap", map);
				json.put("flowId", officeJtGoout.getFlowId());
				
				officeLog.setDescription(json.toString());
				officeLogService.save(officeLog);
			}
	}
	@Override
	public void passFlow(boolean pass, TaskHandlerSave taskHandlerSave,
			String id, String currentStepId) {
		OfficeJtGoout officeJtGoout = officeJtGooutDao.getOfficeJtGooutById(id);
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("pass", pass);
		taskHandlerSave.setVariables(variables);
		TaskHandlerResult result;
		if(pass){
			taskHandlerSave.getComment().setTextComment("[审核通过]"+taskHandlerSave.getComment().getTextComment());
			//TODO 发送消息提醒
			result = taskHandlerService.completeTask(taskHandlerSave);
		}else{
			taskHandlerSave.getComment().setTextComment("[审核不通过]"+taskHandlerSave.getComment().getTextComment());
			boolean isNotFinish = taskHandlerService.isExclusiveGatewayForNext(officeJtGoout.getUnitId(), taskHandlerSave.getSubsystemId(), taskHandlerSave.getCurrentTask().getTaskDefinitionKey(), officeJtGoout.getFlowId());
			if(isNotFinish){
				result = taskHandlerService.completeTask(taskHandlerSave);
			}else{
				result = taskHandlerService.suspendTask(taskHandlerSave);
			}
			
		}
		if(result.getStatus()==TaskHandlerResult.STATUS_FINISH){
			if(result.getResult()==TaskHandlerResult.RESULT_PASS){
				officeJtGoout.setState(String.valueOf(Constants.LEAVE_APPLY_FLOW_FINSH_PASS));
			}else if(result.getResult()==TaskHandlerResult.RESULT_NOT_PASS){
				officeJtGoout.setState(String.valueOf(Constants.LEAVE_APPLY_FLOW_FINSH_NOT_PASS));
			}
			update(officeJtGoout);
		}
		officeConvertFlowService.completeTask(officeJtGoout.getId(), officeJtGoout.getFlowId(), taskHandlerSave.getCurrentUserId(), taskHandlerSave.getCurrentTask().getTaskId(), result, pass);
		if(result.getStatus()==TaskHandlerResult.STATUS_FINISH
				|| officeFlowService.checkSendFlowMsg(officeJtGoout.getFlowId(), currentStepId)){
			officeFlowSendMsgService.completeTaskSendMsg(taskHandlerSave.getCurrentUserId(), pass, officeJtGoout, ConvertFlowConstants.OFFICE_NEWJTGO_OUT, result);
		}
	}

	@Override
	public Integer delete(String[] ids){
		OfficeJtGoout officeJtGoout=officeJtGooutDao.getOfficeJtGooutById(ids[0]);
		if(officeJtGoout!=null&&Integer.parseInt(officeJtGoout.getState()) > OfficeBusinessTripConstants.OFFCIE_BUSINESSTRIP_NOT_SUBMIT){
			processHandlerService.deleteProcessInstance(officeJtGoout.getFlowId(), true);
		}
		gooutStudentExDao.deleteByJtGoOutId(ids[0]);
		gooutTeacherExDao.deleteByjtGooutId(ids[0]);
		List<Attachment> attachments=attachmentService.getAttachments(ids[0], Constants.OFFICE_JTGO_OUT_ATT);
		Set<String> attchmentIds=new HashSet<String>();
		for (Attachment attachment : attachments) {
			attchmentIds.add(attachment.getId());
		}
		attachmentService.deleteAttachments(attchmentIds.toArray(new String[0]));
		return officeJtGooutDao.delete(ids);
	}

	@Override
	public void deleteRevoke(String id) {
		OfficeConvertFlow officeConvertFlow = officeConvertFlowService.getObjByBusinessId(id);
		if(officeConvertFlow!=null&&StringUtils.isNotBlank(officeConvertFlow.getId())){
			officeConvertFlowTaskService.deleteByConvertFlowId(officeConvertFlow.getId());
		}
		officeConvertFlowService.deleteByBusinessId(id);
		officeJtGooutDao.deleteRevoke(id);
	}

	@Override
	public Integer update(OfficeJtGoout officeJtGoout){
		return officeJtGooutDao.update(officeJtGoout);
	}

	@Override
	public OfficeJtGoout getOfficeJtGooutById(String id){
		OfficeJtGoout officeJtGoout=officeJtGooutDao.getOfficeJtGooutById(id);
		if(officeJtGoout!=null){
			if(StringUtils.isNotBlank(officeJtGoout.getFlowId())){
				List<HisTask> hisTask = officeFlowService.getHisTask(officeJtGoout.getFlowId());
				officeJtGoout.setHisTaskList(hisTask);
			}
			if(StringUtils.isNotBlank(id)){
				List<Attachment> attachments=attachmentService.getAttachments(id, Constants.OFFICE_JTGO_OUT_ATT);
				officeJtGoout.setAttachments(attachments);
			}
		}
		return officeJtGoout;
	}
	
	@Override
	public List<OfficeJtGoout> myAuditList(String userId, String unitId,
			Pagination page) {
		Unit unit=unitService.getUnit(unitId);
		Set<String> unitIsSet=new HashSet<String>();
		List<OfficeJtGoout> officeJtGoouts=new ArrayList<OfficeJtGoout>();
		if(unit.getUnitclass()==Unit.UNIT_CLASS_EDU){
			List<Unit> unites=unitService.getUnderlingSchools(unitId);
			unites.add(unit);
			for (Unit unit2 : unites) {
				unitIsSet.add(unit2.getId());
			}
			officeJtGoouts=officeJtGooutDao.getOfficeJtGooutsByUnitIds(unitIsSet.toArray(new String[0]));
		}else{
			officeJtGoouts=officeJtGooutDao.getOfficeJtGooutsByUnitIds(new String[]{unitId});
		}
		Set<String> flowIdSet=new HashSet<String>();
		for (OfficeJtGoout officeJtGoout : officeJtGoouts) {
			flowIdSet.add(officeJtGoout.getFlowId());
		}
		List<HistoricTask> historyTasks=taskHandlerService.getHistoricTasksByFlowIds(flowIdSet.toArray(new String[0]));
		Set<String> flowDes=new HashSet<String>();
		for (HistoricTask historicTask : historyTasks) {
			Comment comment=historicTask.getComment();
			if(comment!=null){
					if(StringUtils.equals(userId, comment.getAssigneeId())){
						flowDes.add(historicTask.getProcessInstanceId());
					}
			}
		}
		officeJtGoouts=officeJtGooutDao.getOfficeJtGooutsByFlowIds(flowDes.toArray(new String[0]), page);
		this.setJtGoOutThing(officeJtGoouts,false);
		return officeJtGoouts;
	}

	@Override
	public List<OfficeJtGoout> HaveDoneAudit(String userId, boolean invalid,
			Pagination page) {
		List<OfficeJtGoout> officeJtGoouts=new ArrayList<OfficeJtGoout>();
		officeJtGoouts=officeJtGooutDao.HaveDoneAudit(userId, invalid, page);
		this.setJtGoOutThing(officeJtGoouts,invalid);
		return officeJtGoouts;
	}
	private void setJtGoOutThing(List<OfficeJtGoout> officeJtGoouts,boolean invalid){
		if(CollectionUtils.isNotEmpty(officeJtGoouts)){
			Set<String> userIdSet = new HashSet<String>();
			Set<String> unitIdSet=new HashSet<String>();
			for (OfficeJtGoout officeJtGoout : officeJtGoouts) {
				userIdSet.add(officeJtGoout.getApplyUserId());
				if(invalid){
					userIdSet.add(officeJtGoout.getInvalidUserId());
				}
				unitIdSet.add(officeJtGoout.getUnitId());
			}
			Map<String,User> userMap = new HashMap<String, User>();
			Map<String,Unit> unitMap=new HashMap<String,Unit>();
			userMap = userService.getUserWithDelMap(userIdSet.toArray(new String[0]));
			unitMap=unitService.getUnitMap(unitIdSet.toArray(new String[0]));
			for (OfficeJtGoout officeJtGoout : officeJtGoouts) {
				if(userMap.containsKey(officeJtGoout.getApplyUserId())){
					User user=userMap.get(officeJtGoout.getApplyUserId());
					if(user!=null){
						officeJtGoout.setApplyUserName(user.getRealname());
					}else{
						officeJtGoout.setApplyUserName("用户已删除");
					}
				}
				if(invalid&&userMap.containsKey(officeJtGoout.getInvalidUserId())){
					User user=userMap.get(officeJtGoout.getInvalidUserId());
					if(user!=null){
						officeJtGoout.setInvalidUserName(user.getRealname());
					}else{
						officeJtGoout.setInvalidUserName("用户已删除");
					}
				}
				if(unitMap.containsKey(officeJtGoout.getUnitId())){
					Unit unit1=unitMap.get(officeJtGoout.getUnitId());
					if(unit1!=null){
						officeJtGoout.setUnitName(unit1.getName());
					}else{
						officeJtGoout.setUnitName("单位已删除");
					}
				}
			}
		}
	}
	@Override
	public List<OfficeJtGoout> toDoAudit(String userId, Pagination page) {
		List<OfficeJtGoout> officeJtGoouts = new ArrayList<OfficeJtGoout>();
		List<TaskDescription> todoTaskList = new ArrayList<TaskDescription>();
		todoTaskList = taskHandlerService.getTodoTasks(userId, Integer.parseInt(FlowConstant.OFFICE_SUBSYSTEM+FlowConstant.OFFICE_JT_GOOUT), page);
//		processInstanceId
		if(CollectionUtils.isNotEmpty(todoTaskList)){
			Set<String> flowIdSet= new HashSet<String>();
			for (TaskDescription task : todoTaskList) {
				flowIdSet.add(task.getProcessInstanceId());	
			}
			Map<String,OfficeJtGoout> jtMap = officeJtGooutDao.getOfficeJtGooutMapByFlowId(flowIdSet.toArray(new String[0]),null);
			Set<String> userIdSet = new HashSet<String>();
			Set<String> unitIdSet=new HashSet<String>();
			for (String leaveId : jtMap.keySet()) {
				OfficeJtGoout officeJtGoout = jtMap.get(leaveId);
				if(officeJtGoout!=null){
					userIdSet.add(officeJtGoout.getApplyUserId());
					unitIdSet.add(officeJtGoout.getUnitId());
				}
			}
			Map<String,User> userMap = new HashMap<String, User>();
			Map<String,Unit> unitMap=new HashMap<String,Unit>();
			userMap = userService.getUserWithDelMap(userIdSet.toArray(new String[0]));
			unitMap=unitService.getUnitMap(unitIdSet.toArray(new String[0]));
			for (TaskDescription task : todoTaskList) {
				OfficeJtGoout officeJtGoout = jtMap.get(task.getProcessInstanceId());
				if(officeJtGoout != null){
					officeJtGoout.setTaskId(task.getTaskId());
					officeJtGoout.setTaskName(task.getTaskName());
					User user = userMap.get(officeJtGoout.getApplyUserId());
					Unit unit=unitMap.get(officeJtGoout.getUnitId());
					if(user!=null){
						officeJtGoout.setApplyUserName(user.getRealname());
					}else{
						officeJtGoout.setApplyUserName("用户已删除");
					}
					if(unit!=null){
						officeJtGoout.setUnitName(unit.getName());
					}else{
						officeJtGoout.setUnitName("单位已删除");
					}
					officeJtGoouts.add(officeJtGoout);
				}
			}
		}
		return officeJtGoouts;
	}
	public Map<String,Set<String>> getMapStatistcGoOutSum(String unitId , String[] userIds, Date startTime , Date endTime){
		List<OfficeJtGoout> jtGoOutStuList = getOfficeJtGooutsByUnitAndType(unitId,"1",startTime , endTime);
		List<String> jtIdStuList = new ArrayList<String>();
		for(OfficeJtGoout jt:jtGoOutStuList){
			jtIdStuList.add(jt.getId());
		}
		List<OfficeJtGoout> jtGoOutTeaList = getOfficeJtGooutsByUnitAndType(unitId,"2",startTime , endTime);
		List<String> jtIdTeaList = new ArrayList<String>();
		for(OfficeJtGoout jt:jtGoOutTeaList){
			jtIdTeaList.add(jt.getId());
		}
		List<GooutTeacherEx> teaList = gooutTeacherExService.getGooutTeacherExByUnitIdJtIds(unitId, jtIdTeaList.toArray(new String[]{}));
		Map<String,GooutTeacherEx> teaMap = new HashMap<String , GooutTeacherEx>();
		for(GooutTeacherEx tea:teaList){
			teaMap.put(tea.getJtgooutId(), tea);
		}
		List<GooutStudentEx> stuList = gooutStudentExService.getGooutStudentExByUnitIdJtids(unitId, jtIdStuList.toArray(new String[]{}));
		Map<String,GooutStudentEx> stuMap = new HashMap<String,GooutStudentEx>();
		for(GooutStudentEx stu:stuList){
			stuMap.put(stu.getJtgooutId(), stu);
		}
		Map<String,Set<String>> dateMap = new HashMap<String , Set<String>>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		for(OfficeJtGoout jt:jtGoOutStuList){
			Date start = jt.getStartTime();
			Date end = jt.getEndTime();
			Set<String> set = new HashSet<String>();
			GooutStudentEx stu = stuMap.get(jt.getId());
			set.add(stu.getActivityLeaderId());
			set.add(stu.getLeadGroupId());
			String[] ids = stu.getOtherTeacherId().split(",");
			for(String id:ids){
				set.add(id);
			}
			Date next = null;
			int i=0;
			while(true){
				next = DateUtils.addDays(start, i);
				i++;
				if(next.compareTo(end) ==1){
					break;
				}
				String dateStr = format.format(next);
				Set<String> idSet = dateMap.get(dateStr);
				if(idSet == null){
					idSet = new HashSet<String>();
					idSet.addAll(set);
					dateMap.put(dateStr, idSet);
				}else{
					idSet.addAll(set);
				}
			}
			
		}
		for(OfficeJtGoout jt:jtGoOutTeaList){
			Date start = jt.getStartTime();
			Date end = jt.getEndTime();
			GooutTeacherEx tea = teaMap.get(jt.getId());
			Set<String> set = new HashSet<String>();
			if(StringUtils.isNotBlank(tea.getPartakePersonId())){
				String[] ids = tea.getPartakePersonId().split(",");
				for(String id:ids){
					set.add(id);
				}
			}
			Date next = null;
			int i=0;
			while(true){
				next = DateUtils.addDays(start, i);
				i++;
				if(next.compareTo(end) ==1){
					break;
				}
				String dateStr = format.format(next);
				Set<String> idSet = dateMap.get(dateStr);
				if(idSet == null){
					idSet = new HashSet<String>();
					idSet.addAll(set);
					dateMap.put(dateStr, idSet);
				}else{
					idSet.addAll(set);
				}
			}
			
		}
		Set<String> userSet = new HashSet<String>();
		for(String u:userIds){
			userSet.add(u);
		}
		Map<String,Set<String>> teaNum = new HashMap<String,Set<String>>();
		for(Map.Entry<String, Set<String> > key:dateMap.entrySet()){
			Set<String> s = key.getValue();
			s.retainAll(userSet);
			teaNum.put(key.getKey(), s);
		}
		return teaNum;
	}
	
	@Override
	public List<OfficeJtGoout> getOfficeJtGooutsByUnitNameAndType(
			String[] unitIds, String type, String startTime, String endTime,
			Pagination page) {
		List<OfficeJtGoout> officeJtGoouts=officeJtGooutDao.getOfficeJtGooutsByUnitNameAndType(unitIds, type, startTime, endTime, page);
		for (OfficeJtGoout officeJtGoout : officeJtGoouts) {
			if(officeJtGoout!=null&&StringUtils.equals("1", officeJtGoout.getType())){
				GooutStudentEx gooutStudentEx=gooutStudentExDao.getGooutStudentExByJtGoOutId(officeJtGoout.getId());
				if(gooutStudentEx!=null){
					officeJtGoout.setContent(gooutStudentEx.getContent());
				}
			}
			if(officeJtGoout!=null&&StringUtils.equals("2", officeJtGoout.getType())){
				GooutTeacherEx gooutTeacherEx=gooutTeacherExDao.getGooutTeacherExByJtGooutId(officeJtGoout.getId());
				if(gooutTeacherEx!=null){
					officeJtGoout.setContent(gooutTeacherEx.getContent());
				}
			}
		}
		this.setJtGoOutThing(officeJtGoouts, false);
		return officeJtGoouts;
	}

	@Override
	public void deleteInvalid(String id, String userId) {
		OfficeJtGoout officeJtGoout =getOfficeJtGooutById(id);
		if(officeJtGoout!=null){
			officeJtGoout.setState(String.valueOf(Constants.APPLY_STATE_INVALID));
			officeJtGoout.setInvalidUserId(userId);
			update(officeJtGoout);
		}
	}

	@Override
	public void changeFlow(String jtGoOutId, String userId, String modelId,
			String jsonResult,Pagination page) {
		OfficeJtGoout officeJtGoout=officeJtGooutDao.getOfficeJtGooutById(jtGoOutId);
		officeJtGoout.setState(String.valueOf(Constants.LEAVE_APPLY_FLOWING));
		
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("pass", true);
		
		String flowId = processHandlerService.startProcessInstance(FlowConstant.OFFICE_SUBSYSTEM,modelId, Integer.parseInt(FlowConstant.OFFICE_SUBSYSTEM+FlowConstant.OFFICE_JT_GOOUT), jtGoOutId, userId, jsonResult, variables);
		officeFlowStepInfoService.batchUpdateByFlowId(modelId, flowId);
		officeJtGoout.setFlowId(flowId);
		update(officeJtGoout);
		
		officeConvertFlowService.startFlow(officeJtGoout, ConvertFlowConstants.OFFICE_NEWJTGO_OUT);
		officeFlowSendMsgService.startFlowSendMsg(officeJtGoout, ConvertFlowConstants.OFFICE_NEWJTGO_OUT);
	}

	@Override
	public Map<String, OfficeJtGoout> getOfficeJtGooutMapByIds(String[] ids){
		return officeJtGooutDao.getOfficeJtGooutMapByIds(ids);
	}

	@Override
	public List<OfficeJtGoout> getOfficeJtGooutsByUnitIds(String[] unitIds) {
		return officeJtGooutDao.getOfficeJtGooutsByUnitIds(unitIds);
	}

	@Override
	public List<OfficeJtGoout> getOfficeJtGooutList(){
		return officeJtGooutDao.getOfficeJtGooutList();
	}

	@Override
	public List<OfficeJtGoout> getOfficeJtGooutPage(Pagination page){
		return officeJtGooutDao.getOfficeJtGooutPage(page);
	}

	@Override
	public List<OfficeJtGoout> getOfficeJtGooutByUnitIdList(String unitId){
		return officeJtGooutDao.getOfficeJtGooutByUnitIdList(unitId);
	}

	@Override
	public List<OfficeJtGoout> getOfficeJtGooutByUnitIdPage(String unitId, Pagination page){
		return officeJtGooutDao.getOfficeJtGooutByUnitIdPage(unitId, page);
	}

	@Override
	public List<OfficeJtGoout> getOfficeJtGooutByUnitIdAndState(String unitId,
			String userId, String state, Pagination page) {
		return officeJtGooutDao.getOfficeJtGooutByUnitIdAndState(unitId, userId, state, page);
	}

	public void setOfficeJtGooutDao(OfficeJtGooutDao officeJtGooutDao){
		this.officeJtGooutDao = officeJtGooutDao;
	}

	public void setGooutStudentExDao(GooutStudentExDao gooutStudentExDao) {
		this.gooutStudentExDao = gooutStudentExDao;
	}

	public void setGooutTeacherExDao(GooutTeacherExDao gooutTeacherExDao) {
		this.gooutTeacherExDao = gooutTeacherExDao;
	}

	public void setProcessHandlerService(ProcessHandlerService processHandlerService) {
		this.processHandlerService = processHandlerService;
	}

	public void setOfficeFlowSendMsgService(
			OfficeFlowSendMsgService officeFlowSendMsgService) {
		this.officeFlowSendMsgService = officeFlowSendMsgService;
	}

	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	public void setTaskHandlerService(TaskHandlerService taskHandlerService) {
		this.taskHandlerService = taskHandlerService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public void setOfficeLogService(OfficeLogService officeLogService) {
		this.officeLogService = officeLogService;
	}

	public void setFlowManageService(FlowManageService flowManageService) {
		this.flowManageService = flowManageService;
	}

	public void setOfficeConvertFlowService(
			OfficeConvertFlowService officeConvertFlowService) {
		this.officeConvertFlowService = officeConvertFlowService;
	}

	public void setOfficeConvertFlowTaskService(
			OfficeConvertFlowTaskService officeConvertFlowTaskService) {
		this.officeConvertFlowTaskService = officeConvertFlowTaskService;
	}

	public void setOfficeFlowService(OfficeFlowService officeFlowService) {
		this.officeFlowService = officeFlowService;
	}

	@Override
	public List<OfficeJtGoout> getOfficeJtGooutsByUnitAndType(String unitId,
			String type, Date startTime, Date endTime) {
		// TODO Auto-generated method stub
		return officeJtGooutDao.getOfficeJtGooutsByUnitAndType(unitId, type, startTime, endTime);
	}

	public void setGooutTeacherExService(GooutTeacherExService gooutTeacherExService) {
		this.gooutTeacherExService = gooutTeacherExService;
	}

	public void setGooutStudentExService(GooutStudentExService gooutStudentExService) {
		this.gooutStudentExService = gooutStudentExService;
	}

	@Override
	public List<OfficeJtGoout> getListByStarttimeAndEndtime(String unitId,
			Date startTime, Date endTime) {
		return officeJtGooutDao.getListByStarttimeAndEndtime(unitId, startTime, endTime);
	}

	@Override
	public List<OfficeJtGoout> getListByUnitIdAndDate(String unitId, Date date) {
		List<OfficeJtGoout> officeJtGoouts=officeJtGooutDao.getListByUnitIdAndDate(unitId, date);
		
		Set<String> stuIds = new HashSet<String>();
		Set<String> teaIds = new HashSet<String>();
		for(OfficeJtGoout ent : officeJtGoouts){
			if(StringUtils.equals("1", ent.getType())){
				stuIds.add(ent.getId());
			}
			if(StringUtils.equals("2", ent.getType())){
				teaIds.add(ent.getId());
			}
		}
		
		List<GooutStudentEx> stuList = gooutStudentExService.getGooutStudentExListByjtId(stuIds.toArray(new String[0]));
		System.out.println("学生集体活动List数量"+stuList.size());
		List<GooutTeacherEx> teaList = gooutTeacherExService.getGooutTeacherExListByjtId(teaIds.toArray(new String[0]));
		System.out.println("教师集体培训List数量"+teaList.size());
		Set<String> stuSets= new HashSet<String>();
		for(GooutStudentEx ent : stuList){
			//活动负责人,带队老师,其他老师
//			ent.getActivityLeaderId();ent.getLeadGroupId();ent.getOtherTeacherId()
			stuSets.add(ent.getActivityLeaderId());
			stuSets.add(ent.getLeadGroupId());
			if(StringUtils.isNotBlank(ent.getOtherTeacherId())){
				String[] ss =StringUtils.split(ent.getOtherTeacherId(), ",");
				for(String s : ss){
					stuSets.add(s);
				}
			}
		}
		
		Set<String> teaSets = new HashSet<String>();
		for(GooutTeacherEx ent : teaList){
			if(StringUtils.isNotBlank(ent.getPartakePersonId())){
				String[] ss = StringUtils.split(ent.getPartakePersonId(),",");
				for(String s : ss){
					teaSets.add(s);
				}
			}
		}
		
		List<OfficeJtGoout> list=new ArrayList<OfficeJtGoout>();
		if(CollectionUtils.isNotEmpty(stuSets)){
			for (String string : stuSets) {
				OfficeJtGoout officeJtGoout=new OfficeJtGoout();
				officeJtGoout.setApplyUserId(string);
				officeJtGoout.setContent("学生集体活动");
				list.add(officeJtGoout);
			}
		}
		if(CollectionUtils.isNotEmpty(teaSets)){
			for (String string : teaSets) {
				OfficeJtGoout officeJtGoout=new OfficeJtGoout();
				officeJtGoout.setApplyUserId(string);
				officeJtGoout.setContent("教师集体培训");
				list.add(officeJtGoout);
			}
		}
		return list;
	}

	public void setOfficeFlowStepInfoService(
			OfficeFlowStepInfoService officeFlowStepInfoService) {
		this.officeFlowStepInfoService = officeFlowStepInfoService;
	}
	
}
