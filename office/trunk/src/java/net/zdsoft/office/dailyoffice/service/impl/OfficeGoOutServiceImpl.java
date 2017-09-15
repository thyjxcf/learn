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
import net.zdsoft.eis.base.common.entity.User;
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
import net.zdsoft.leadin.common.entity.BusinessTask;
import net.zdsoft.office.convertflow.constant.ConvertFlowConstants;
import net.zdsoft.office.convertflow.entity.OfficeConvertFlow;
import net.zdsoft.office.convertflow.service.OfficeConvertFlowService;
import net.zdsoft.office.convertflow.service.OfficeConvertFlowTaskService;
import net.zdsoft.office.convertflow.service.OfficeFlowSendMsgService;
import net.zdsoft.office.dailyoffice.constant.OfficeBusinessTripConstants;
import net.zdsoft.office.dailyoffice.dao.OfficeGoOutDao;
import net.zdsoft.office.dailyoffice.entity.OfficeGoOut;
import net.zdsoft.office.dailyoffice.entity.OfficeJtgoOut;
import net.zdsoft.office.dailyoffice.entity.OfficeLog;
import net.zdsoft.office.dailyoffice.service.OfficeGoOutService;
import net.zdsoft.office.dailyoffice.service.OfficeJtgoOutService;
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
	private OfficeConvertFlowService officeConvertFlowService;
	private OfficeFlowSendMsgService officeFlowSendMsgService;
	private OfficeConvertFlowTaskService officeConvertFlowTaskService;
	private OfficeJtgoOutService officeJtgoOutService;
	private FlowManageService flowManageService;
	private OfficeLogService officeLogService;
	private OfficeFlowService officeFlowService;
	private OfficeFlowStepInfoService officeFlowStepInfoService;
	
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
				officeGoOut.setApplyUserName(user.getRealname());
			}
			if(StringUtils.isNotEmpty(officeGoOut.getFlowId())){
				List<HisTask> hisTask = officeFlowService.getHisTask(officeGoOut.getFlowId());
				officeGoOut.setHisTaskList(hisTask);
			}
			officeGoOut.setAttachments(attachmentService.getAttachments(officeGoOut.getId(), Constants.OFFICE_BUSINESS_TRIP_ATT));
		}
		return officeGoOut;
	}
	@Override
	public Map<String, OfficeGoOut> getOfficeGoOutMapByIds(String[] ids){
		return officeGoOutDao.getOfficeGoOutMapByIds(ids);
	}

	@Override
	public List<OfficeGoOut> getOfficeGoOutList(){
		return officeGoOutDao.getOfficeGoOutList();
	}
	
	public List<OfficeGoOut> getListByStarttimeAndEndtime(Date startTime,Date endTime,String[] userIds){
		return officeGoOutDao.getListByStarttimeAndEndtime(startTime, endTime, userIds);
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
	public OfficeGoOut getOfficeGoOutByOfficeLog(OfficeGoOut officeGoOut,String unitId,String userId){
			List<Flow> flowList = flowManageService.getFinishFlowList(unitId, FlowConstant.FLOW_OWNER_UNIT, 
					FlowConstant.OFFICE_GO_OUT,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
			User user = userService.getUser(userId);
			if(user != null && StringUtils.isNotBlank(user.getDeptid())){
				List<Flow> flowList2  = flowManageService.getFinishFlowList(user.getDeptid(), FlowConstant.FLOW_OWNER_STRING, 
						FlowConstant.OFFICE_GO_OUT,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
				if(CollectionUtils.isNotEmpty(flowList2)){
					flowList.addAll(flowList2);
				}
			}
			
			List<OfficeLog> logList=officeLogService.getOfficeList(unitId, userId,
												String.valueOf(Constants.GO_OUT_MOD_ID),Constants.LOG_APPLY);
			if(CollectionUtils.isNotEmpty(logList)){
				List<Flow> sortFlowList=new ArrayList<Flow>();
				OfficeLog officeLog=logList.get(0);
				String description=officeLog.getDescription();
				JSONObject json=JSONObject.fromObject(description);
				@SuppressWarnings("unchecked")
				Map<String,Integer> map=(Map<String, Integer>) json.get("flowNumMap");
				//String officeId=(String) json.get("officeId");
				String lastFlowId=(String) json.get("flowId");
				
				if(MapUtils.isNotEmpty(map)){
					List<String> flowIdList=new ArrayList<String>();
					Iterator<String> it=map.keySet().iterator();
					while(it.hasNext()){
						flowIdList.add(it.next());
					}
					String[] arr=flowIdList.toArray(new String[0]);
					if(ArrayUtils.isNotEmpty(arr)){
						int length=arr.length;
						for(int i=0;i<length;i++){
							for(int j=i;j<length;j++){
								if(map.get(arr[i])<map.get(arr[j])){
									String flowId=arr[i];
									arr[i]=arr[j];
									arr[j]=flowId;
								}
							}
						}
				}
				if(CollectionUtils.isNotEmpty(flowList)){
					Map<String,Flow> flowMap=new HashMap<String, Flow>();
					for(Flow flow:flowList){
						flowMap.put(flow.getFlowId(),flow);
					}
					for(String flowId:arr){
						Flow flow=flowMap.get(flowId);
						if(flow!=null){
							sortFlowList.add(flow);
						}
					}
					Iterator<String> itFlow=flowMap.keySet().iterator();
					while(itFlow.hasNext()){
						String itFlowId=itFlow.next();
						boolean flag=false;
						for(String flowId:arr){
							if(itFlowId.equals(flowId)){
								flag=true;
							}
						}
						if(!flag){
							sortFlowList.add(flowMap.get(itFlowId));
						}
					}
				}
				}
				officeGoOut.setFlowList(sortFlowList);
				if(StringUtils.isEmpty(officeGoOut.getFlowId())){
					officeGoOut.setFlowId(lastFlowId);
				}
			}
			return officeGoOut;
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
	public String  getTextComment(String unitId,String userId){
		List<OfficeLog> logList=officeLogService.getOfficeList(unitId, userId,
				String.valueOf(Constants.GO_OUT_MOD_ID),Constants.LOG_AUDIT);
		if(CollectionUtils.isNotEmpty(logList)){
				OfficeLog officeLog=logList.get(0);
				return officeLog.getDescription();
		}else return "同意";
	}
	@Override
	public void startFlow(OfficeGoOut officeGoOut, String userId,
			List<UploadFile> files,boolean isMobile,String[] removeAttachment) {
		
		if(StringUtils.isBlank(officeGoOut.getId())){
			officeGoOut.setId(UUIDUtils.newId());
		}
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("pass",true);
		String flowId = processHandlerService.startProcessInstance(FlowConstant.OFFICE_SUBSYSTEM,officeGoOut.getFlowId(), Integer.parseInt(FlowConstant.OFFICE_SUBSYSTEM+FlowConstant.OFFICE_GO_OUT), officeGoOut.getId(), userId, variables);
		officeFlowStepInfoService.batchUpdateByFlowId(officeGoOut.getFlowId(), flowId);
		officeGoOut.setFlowId(flowId);
		officeGoOut.setCreateTime(new Date());
		
		OfficeGoOut office=officeGoOutDao.getOfficeGoOutById(officeGoOut.getId());
		if(office!=null){
			update(officeGoOut,files,isMobile,removeAttachment);
			saveOfficeLog(officeGoOut);
		}else{
			add(officeGoOut,files,isMobile);
			saveOfficeLog(officeGoOut);
		}
		//update(officeGoOut);
		
		officeConvertFlowService.startFlow(officeGoOut, ConvertFlowConstants.OFFICE_GO_OUT);
		officeFlowSendMsgService.startFlowSendMsg(officeGoOut, ConvertFlowConstants.OFFICE_GO_OUT);
	}
	@SuppressWarnings("unchecked")
	public void saveOfficeLog(OfficeGoOut officeGoOut){
		OfficeLog officeLog;
		Map<String,Integer> map;
		JSONObject json;
		List<OfficeLog> logList=officeLogService.getOfficeList(officeGoOut.getUnitId(), 
				officeGoOut.getApplyUserId(),String.valueOf(Constants.GO_OUT_MOD_ID),Constants.LOG_APPLY);
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
					if(flowId.equals(officeGoOut.getFlowId())){
						int num=map.get(flowId);
						map.put(flowId, num+1);
						flag=true;
					}
				}
				if(!flag){
					map.put(officeGoOut.getFlowId(),1);
				}
			}
			json.put("flowNumMap", map);
			json.put("flowId",officeGoOut.getFlowId());
			
			officeLog.setDescription(json.toString());
			officeLogService.update(officeLog);
		}else {
			officeLog=new OfficeLog();
			officeLog.setUnitId(officeGoOut.getUnitId());
			officeLog.setUserId(officeGoOut.getApplyUserId());
			officeLog.setModid(String.valueOf(Constants.GO_OUT_MOD_ID));
			officeLog.setCode(Constants.LOG_APPLY);
			officeLog.setLogtime(new Date());
			
			json=new JSONObject();
			map=new HashMap<String, Integer>();
			map.put(officeGoOut.getFlowId(),1);
			json.put("flowNumMap", map);
			json.put("flowId",officeGoOut.getFlowId());
			
			officeLog.setDescription(json.toString());
			officeLogService.save(officeLog);
		}
	}
	@Override
	public List<OfficeGoOut> getStatistics(String unitId,Date startTime,Date endTime,String[] userIds){
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
					office.setApplyUserName(user==null?"":user.getRealname());
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
	public void update(OfficeGoOut officeGoOut, List<UploadFile> files,boolean isMobile, String[] removeAttachment) {
		officeGoOutDao.update(officeGoOut);
//		List<Attachment> attachments = attachmentService.getAttachments(officeGoOut.getId(), Constants.OFFICE_BUSINESS_TRIP_ATT);
		if (isMobile && !CollectionUtils.isEmpty(files)) {
			if (ArrayUtils.isNotEmpty(removeAttachment)) {
				attachmentService.deleteAttachments(removeAttachment);
			}
//			List<Attachment> attachments=new ArrayList<Attachment>();
			for (UploadFile uploadFile : files) {
				Attachment attachment = new Attachment();
				attachment.setFileName(uploadFile.getFileName());
				attachment.setContentType(uploadFile.getContentType());
				attachment.setFileSize(uploadFile.getFileSize());
				attachment.setUnitId(officeGoOut.getUnitId());
				attachment.setObjectId(officeGoOut.getId());
				attachment.setObjectType(Constants.OFFICE_BUSINESS_TRIP_ATT);
				attachmentService.saveAttachment(attachment, uploadFile, false);
			}
		}
		/*if(file!=null){
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
		}*/
		
	}

	@Override
	public void add(OfficeGoOut officeGoOut,List<UploadFile> files,boolean isMobile) {
		officeGoOutDao.save(officeGoOut);
		if (isMobile && !CollectionUtils.isEmpty(files)) {
			for (UploadFile uploadFile : files) {
				Attachment attachment = new Attachment();
				attachment.setFileName(uploadFile.getFileName());
				attachment.setContentType(uploadFile.getContentType());
				attachment.setFileSize(uploadFile.getFileSize());
				attachment.setUnitId(officeGoOut.getUnitId());
				attachment.setObjectId(officeGoOut.getId());
				attachment.setObjectType(Constants.OFFICE_BUSINESS_TRIP_ATT);
				attachmentService.saveAttachment(attachment, uploadFile);
			}
		}
		/*if(file!=null){
			Attachment attachment = new Attachment();
			attachment.setFileName(file.getFileName());
			attachment.setContentType(file.getContentType());
			attachment.setFileSize(file.getFileSize());
			attachment.setUnitId(officeGoOut.getUnitId());
			attachment.setObjectId(officeGoOut.getId());
			attachment.setObjectType(Constants.OFFICE_BUSINESS_TRIP_ATT);
			attachmentService.saveAttachment(attachment, file);
		}*/
	}
	@Override
	public List<OfficeGoOut> toDoAudit(String userId, Pagination page) {//TODO
		List<OfficeGoOut> offList = new ArrayList<OfficeGoOut>();
		List<TaskDescription> todoTaskList = new ArrayList<TaskDescription>();
		todoTaskList = taskHandlerService.getTodoTasks(userId, Integer.parseInt(FlowConstant.OFFICE_SUBSYSTEM+FlowConstant.OFFICE_GO_OUT), page);
		if(CollectionUtils.isNotEmpty(todoTaskList)){
			Set<String> flowIdSet= new HashSet<String>();
			for (TaskDescription task : todoTaskList) {
				flowIdSet.add(task.getProcessInstanceId());	
			}
			Map<String,OfficeGoOut> leaveMap = officeGoOutDao.getOfficeBusinessTripMapByFlowIds(flowIdSet.toArray(new String[0]));
			Map<String,OfficeJtgoOut> jtGoMap=officeJtgoOutService.getOfficeBusinessTripMapByFlowIds(flowIdSet.toArray(new String[0]));
			Set<String> userIdSet = new HashSet<String>();
			for (String leaveId : leaveMap.keySet()) {
				OfficeGoOut officeGoOut = leaveMap.get(leaveId);
				if(officeGoOut!=null){
					userIdSet.add(officeGoOut.getApplyUserId());
				}
			}
			for (String flowId : jtGoMap.keySet()) {
				OfficeJtgoOut officeJtgoOut=jtGoMap.get(flowId);
				if(officeJtgoOut!=null){
					userIdSet.add(officeJtgoOut.getApplyUserId());
				}
			}
			Map<String,User> userMap = new HashMap<String, User>();
			userMap = userService.getUserWithDelMap(userIdSet.toArray(new String[0]));
			for (TaskDescription task : todoTaskList) {
				OfficeGoOut officeGoOut = leaveMap.get(task.getProcessInstanceId());
				OfficeJtgoOut officeJtgoOut=jtGoMap.get(task.getProcessInstanceId());
				if(officeGoOut==null&&officeJtgoOut==null){
					continue;
				}
				OfficeGoOut officeGoOutJt=null;
				if(officeJtgoOut!=null){//TODO
					officeGoOutJt=new OfficeGoOut();
					officeGoOutJt.setId(officeJtgoOut.getId());
					officeGoOutJt.setApplyUserId(officeJtgoOut.getApplyUserId());
					officeGoOutJt.setDesHours(officeJtgoOut.getDays());
					officeGoOutJt.setOutType(officeJtgoOut.getOutType());
					officeGoOutJt.setTripReason(officeJtgoOut.getTripReason());
					officeGoOutJt.setState(officeJtgoOut.getState());
					officeGoOutJt.setTaskId(task.getTaskId());
					officeGoOutJt.setTaskName(task.getTaskName());
					officeGoOutJt.setCreateTime(officeJtgoOut.getCreateTime());
					officeGoOutJt.setType("1");
					
					User user2 = userMap.get(officeGoOutJt.getApplyUserId());
					if(user2!=null){
						officeGoOutJt.setApplyUserName(user2.getRealname());
					}else{
						officeGoOutJt.setApplyUserName("用户已删除");
					}
					
					offList.add(officeGoOutJt);
				}
				if(officeGoOut!=null){
					officeGoOut.setTaskId(task.getTaskId());
					officeGoOut.setTaskName(task.getTaskName());
					officeGoOut.setDesHours(String.valueOf(officeGoOut.getHours()));
					officeGoOut.setType("0");
					User user = userMap.get(officeGoOut.getApplyUserId());
					if(user!=null){
						officeGoOut.setApplyUserName(user.getRealname());
					}else{
						officeGoOut.setApplyUserName("用户已删除");
					}
					offList.add(officeGoOut);
				}
			}
		}
		Collections.sort(offList, new Comparator<OfficeGoOut>(){

			@Override
			public int compare(OfficeGoOut o1, OfficeGoOut o2) {
				if(o1.getCreateTime() == null){
					return 1;
				}
				if(o2.getCreateTime() == null){
					return -1;
				}
				return o2.getCreateTime().compareTo(o1.getCreateTime());
			}
			
		});
		return offList;
	}

	@Override
	public List<OfficeGoOut> HaveDoAudit(String userId,boolean invalid,Pagination page) {
		List<OfficeGoOut>  offList = officeGoOutDao.HaveDoAudit(userId,invalid,null);
		List<OfficeJtgoOut> officejtList=officeJtgoOutService.doneAudit(userId, invalid, null);
		Set<String> userIdSet = new HashSet<String>();
		for (OfficeGoOut officeGoOut : offList) {
			userIdSet.add(officeGoOut.getApplyUserId());
			if(!invalid){
				userIdSet.add(officeGoOut.getInvalidUser());
			}
			officeGoOut.setType("0");
			officeGoOut.setDesHours(String.valueOf(officeGoOut.getHours()));
		}
		for (OfficeJtgoOut officeJtgoOut : officejtList) {
			userIdSet.add(officeJtgoOut.getApplyUserId());
			if(!invalid){
				userIdSet.add(officeJtgoOut.getInvalidUser());
			}
			
			OfficeGoOut officeGoOut=new OfficeGoOut();
			officeGoOut.setId(officeJtgoOut.getId());
			officeGoOut.setApplyUserId(officeJtgoOut.getApplyUserId());
			officeGoOut.setDesHours(officeJtgoOut.getDays());
			officeGoOut.setOutType(officeJtgoOut.getOutType());
			officeGoOut.setTripReason(officeJtgoOut.getTripReason());
			officeGoOut.setState(officeJtgoOut.getState());
			officeGoOut.setCreateTime(officeJtgoOut.getCreateTime());
			officeGoOut.setInvalidUser(officeJtgoOut.getInvalidUser());
			officeGoOut.setType("1");
			offList.add(officeGoOut);
		}
		Map<String,User> userMap = new HashMap<String, User>();
		userMap = userService.getUserWithDelMap(userIdSet.toArray(new String[0]));
		for (OfficeGoOut officeGoOut : offList) {
			User user = userMap.get(officeGoOut.getApplyUserId());
			if(user!=null){
				officeGoOut.setApplyUserName(user.getRealname());
			}else{
				officeGoOut.setApplyUserName("用户已删除");
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
		Collections.sort(offList, new Comparator<OfficeGoOut>(){

			@Override
			public int compare(OfficeGoOut o1, OfficeGoOut o2) {
				return o2.getCreateTime().compareTo(o1.getCreateTime());
			}
			
		});
		return offList;
	}
	public void saveTextComment(TaskHandlerSave taskHandlerSave){
		OfficeLog officeLog;
		List<OfficeLog> logList=officeLogService.getOfficeList(taskHandlerSave.getCurrentUnitId(), 
				taskHandlerSave.getCurrentUserId(),String.valueOf(Constants.GO_OUT_MOD_ID),Constants.LOG_AUDIT);
		if(CollectionUtils.isNotEmpty(logList)){
			officeLog=logList.get(0);
			officeLog.setDescription(taskHandlerSave.getComment().getTextComment());
			officeLogService.update(officeLog);
		}else {
			officeLog=new OfficeLog();
			officeLog.setUnitId(taskHandlerSave.getCurrentUnitId());
			officeLog.setUserId(taskHandlerSave.getCurrentUserId());
			officeLog.setModid(String.valueOf(Constants.GO_OUT_MOD_ID));
			officeLog.setCode(Constants.LOG_AUDIT);
			officeLog.setLogtime(new Date());
			officeLog.setDescription(taskHandlerSave.getComment().getTextComment());
			officeLogService.save(officeLog);
		}
	}
	@Override
	public void passFlow(boolean pass, TaskHandlerSave taskHandlerSave,
			String leaveId, String currentStepId) {
		OfficeGoOut leave = officeGoOutDao.getOfficeGoOutById(leaveId);
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("pass", pass);
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
			officeFlowSendMsgService.completeTaskSendMsg(taskHandlerSave.getCurrentUserId(), pass, leave, ConvertFlowConstants.OFFICE_GO_OUT, result);
		}
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
	
	@Override
	public void changeFlow(String gooutId, String userId, String modelId, String jsonResult){
		//TODO
		OfficeGoOut goout = this.getOfficeGoOutById(gooutId);
		goout.setState(String.valueOf(Constants.APPLY_STATE_NEED_AUDIT));
		
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("pass",true);
		String flowId = processHandlerService.startProcessInstance(FlowConstant.OFFICE_SUBSYSTEM,modelId, Integer.parseInt(FlowConstant.OFFICE_SUBSYSTEM+FlowConstant.OFFICE_GO_OUT), gooutId, userId, jsonResult, variables);
		officeFlowStepInfoService.batchUpdateByFlowId(modelId, flowId);
		goout.setFlowId(flowId);
		update(goout);
		
		officeConvertFlowService.startFlow(goout, ConvertFlowConstants.OFFICE_GO_OUT);
		officeFlowSendMsgService.startFlowSendMsg(goout, ConvertFlowConstants.OFFICE_GO_OUT);
	}
	
	@Override
	public List<OfficeGoOut> getOfficeTeacherLeavesByUnitIdAndUserId(String unitId, Date date) {
		return officeGoOutDao.getOfficeTeacherLeavesByUnitIdAndUserId(unitId, date);
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

	public void setOfficeJtgoOutService(OfficeJtgoOutService officeJtgoOutService) {
		this.officeJtgoOutService = officeJtgoOutService;
	}

	public void setFlowManageService(FlowManageService flowManageService) {
		this.flowManageService = flowManageService;
	}

	public void setOfficeLogService(OfficeLogService officeLogService) {
		this.officeLogService = officeLogService;
	}

	public void setOfficeFlowService(OfficeFlowService officeFlowService) {
		this.officeFlowService = officeFlowService;
	}

	public void setOfficeFlowStepInfoService(
			OfficeFlowStepInfoService officeFlowStepInfoService) {
		this.officeFlowStepInfoService = officeFlowStepInfoService;
	}
	
}
