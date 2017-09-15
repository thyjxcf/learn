package net.zdsoft.office.officeFlow.service.impl;

import java.io.ByteArrayInputStream;
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

import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.ExclusiveGatewayActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.ParallelGatewayActivityBehavior;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.jbpm.activiti.bridge.converter.utils.XmlJsonConverter;
import net.zdsoft.jbpm.activiti.core.impl.ServiceImpl;
import net.zdsoft.jbpm.activiti.engine.task.AgileIdentityLinkType;
import net.zdsoft.jbpm.core.entity.Comment;
import net.zdsoft.jbpm.core.entity.HalfSelectUser;
import net.zdsoft.jbpm.core.entity.HistoricTask;
import net.zdsoft.jbpm.core.entity.TaskDescription;
import net.zdsoft.jbpm.core.entity.TaskHandlerShow;
import net.zdsoft.jbpm.core.service.HalfSelectUserService;
import net.zdsoft.jbpm.core.service.ProcessHandlerService;
import net.zdsoft.jbpm.core.service.TaskHandlerService;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.convertflow.entity.OfficeConvertFlow;
import net.zdsoft.office.convertflow.entity.OfficeConvertFlowTask;
import net.zdsoft.office.convertflow.service.OfficeConvertFlowService;
import net.zdsoft.office.convertflow.service.OfficeConvertFlowTaskService;
import net.zdsoft.office.officeFlow.dto.HisTask;
import net.zdsoft.office.officeFlow.dto.TaskDesc;
import net.zdsoft.office.officeFlow.service.OfficeFlowService;
import net.zdsoft.office.util.Constants;

public class OfficeFlowServiceImpl extends ServiceImpl implements OfficeFlowService {

	private static final String OFFICE_SUBSYSTEM = "70";
	private static final String DEPT_PRINCIPAL_ID = "00000000000000000000000000000001";//部门负责人
	private static final String DEPT_LEADERI_D = "00000000000000000000000000000002";//分管领导
	private static final String INITIATOR_ID = "00000000000000000000000000000003";//发起人
	private static final String DEPUTY_HEAD_ID = "00000000000000000000000000000004";//分管校长
	private static final String ROLE_CODE_SCHOOLMASTER = "office_schoolmaster";
	
	private UserService userService;
	private DeptService deptService;
	private TaskHandlerService taskHandlerService;
	private HalfSelectUserService halfSelectUserService;
	private ProcessHandlerService processHandlerService;
	private OfficeConvertFlowService officeConvertFlowService;
	private OfficeConvertFlowTaskService officeConvertFlowTaskService;
	private CustomRoleService customRoleService;
	private CustomRoleUserService customRoleUserService;
	
	@Override
	public List<HisTask> getHisTask(String flowId) {
		List<HisTask> hisTaskList = new ArrayList<HisTask>();
		List<HistoricTask> histasks = taskHandlerService.getHistoricTasks(flowId);
		if(CollectionUtils.isNotEmpty(histasks)){
			for (HistoricTask hisTask : histasks) {
				HisTask task = new HisTask();
				task.setComment(hisTask.getComment());
				task.setTaskName(hisTask.getTaskName());
				task.setCandidateUsers(hisTask.getCandidateUsers());
				
				task.setTaskId(hisTask.getTaskId());
				task.setAssigneeId(hisTask.getAssigneeId());
				task.setTaskDefinitionKey(hisTask.getTaskDefinitionKey());
				task.setStep(hisTask.getStep());
				
				Comment comment = task.getComment();
				if(comment!=null){
					task.setAssigneeName(comment.getAssigneeName());
				}
				hisTaskList.add(task);
			}
		}
		return hisTaskList;
	}

	@Override
	public String checkRetract(List<HisTask> hisTaskList, String userId, String flowId) {
		JSONObject json = new JSONObject();
		if (CollectionUtils.isNotEmpty(hisTaskList)) {
			Date tempDate = null;
			String reTaskId = "";
			String taskKey = "";
			String showReBackId = "";
			boolean canBeRetract = false;
			//上一步为自己审核的可以撤回
			for(HisTask hisTask : hisTaskList){
				if (userId.equals(hisTask.getAssigneeId())) {
					Date date = hisTask.getComment().getOperateTime();
					if (tempDate == null
							|| date.compareTo(tempDate) > 0) {
						reTaskId = hisTask.getTaskId();
						tempDate = date;
						taskKey = hisTask.getTaskDefinitionKey();
					}
				}
			}
			if (StringUtils.isNotEmpty(reTaskId)) {
				showReBackId = taskHandlerService.isCanbeRevoke(taskKey, flowId);
				if(showReBackId ==null){
					showReBackId = "";
				}
				else{
					canBeRetract = true;
				}
			}
			if(canBeRetract){
				json.put("reTaskId", reTaskId);
				json.put("taskKey", taskKey);
				json.put("showReBackId", showReBackId);
				json.put("result", true);
			}
			else{
				json.put("result", false);
			}
		}
		else{
			json.put("result", false);
		}
		return json.toString();
	}

	
	@Override
	public <T> List<T> haveDoneAudit(List<T> list, String userId, Pagination page) {
		List<T> returnList = new ArrayList<T>();
		try {
			Set<String> flowIdSet=new HashSet<String>();
			Map<String, T> map = new HashMap<String, T>();
			for(T t : list){
				String flowId = (String)(t.getClass().getDeclaredMethod("getFlowId").invoke(t));
				flowIdSet.add(flowId);
				map.put(flowId, t);
			}
			//获取指定人审核过的流程
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
			
			//获取待办任务
			List<String> flowIdList = new ArrayList<String>(flowIdSet);
			Map<String, List<TaskDescription>> todoTaskMap = new HashMap<String, List<TaskDescription>>();
			//如果参数数量超过500，则分批获取
			int len = 500;
			int size = flowIdList.size();
			if(size > len){
				int count = (size + len - 1) / len;
				for (int i = 0; i < count; i++) {  
					List<String> subList = flowIdList.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
					Map<String, List<TaskDescription>> subMap = taskHandlerService.getTodoTaskMap(subList);
					todoTaskMap.putAll(subMap);
				}  
			}
			else{
				todoTaskMap = taskHandlerService.getTodoTaskMap(flowIdList);
			}
			
			Set<String> userIdSet = new HashSet<String>();
			for(String flowId : flowDes){
				T t = map.get(flowId);
				if(t != null){
					List<TaskDescription> todoTasks = todoTaskMap.get(flowId);
					//对于待办任务是自己的需要处理的，设置待办任务ID
					boolean flag = false;
					if(CollectionUtils.isNotEmpty(todoTasks)){
						for(TaskDescription todoTask : todoTasks){
							if(CollectionUtils.isNotEmpty(todoTask.getCandidateUsers())){
								for(String candidateUser : todoTask.getCandidateUsers()){
									if(userId.equals(candidateUser)){
										flag = true;
										T t1 = (T) BeanUtils.cloneBean(t);
										t1.getClass().getDeclaredMethod("setTaskId", String.class).invoke(t1, new Object[]{todoTask.getTaskId()});
										t1.getClass().getDeclaredMethod("setTaskName", String.class).invoke(t1, new Object[]{todoTask.getTaskName()});
										returnList.add(t1);
									}
								}
							}
						}
					}
					if(!flag)
						returnList.add(t);
					String applyUserId = (String)(t.getClass().getDeclaredMethod("getApplyUserId").invoke(t));
					userIdSet.add(applyUserId);
				}
			}

			if(CollectionUtils.isNotEmpty(returnList)){
				Map<String,User> userMap = userService.getUserWithDelMap(userIdSet.toArray(new String[0]));
				for(T t : returnList){
					String applyUserId = (String)(t.getClass().getDeclaredMethod("getApplyUserId").invoke(t));
					User applyUser = userMap.get(applyUserId);
					if(applyUser!=null){
						t.getClass().getDeclaredMethod("setApplyUserName", String.class).invoke(t, new Object[]{applyUser.getRealname()});
					}else{
						t.getClass().getDeclaredMethod("setApplyUserName", String.class).invoke(t, new Object[]{"用户已删除"});
					}
				}
				
				//排序
				if(list.get(0).getClass().getDeclaredField("createTime") != null){
					Collections.sort(returnList, new Comparator<T>(){
						@Override
						public int compare(T o1, T o2) {
							try {
								Date d1 = (Date)(o1.getClass().getDeclaredMethod("getCreateTime").invoke(o1));
								Date d2 = (Date)(o2.getClass().getDeclaredMethod("getCreateTime").invoke(o2));
								return d2.compareTo(d1);
							} catch (Exception e) {
								e.printStackTrace();
								return 1;
							} 
						}
					});
				}
				
				//分页
				page.setMaxRowCount(returnList.size());
				page.initialize();
				if(page.getPageIndex() != page.getMaxPageIndex()) {
					returnList = returnList.subList(page.getCurRowNum() - 1, page.getCurRowNum() + page.getPageSize() -1);
				}else {
					returnList = returnList.subList(page.getCurRowNum() - 1, page.getMaxRowCount());
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(CollectionUtils.isEmpty(returnList))
				return new ArrayList<T>();
			else
				return returnList;
		}
	}
	
	@Override
	public List<TaskDesc> getTaskDescList(String unitId, String userId, String taskId) {
		TaskHandlerShow taskHandlerShow = taskHandlerService.getTaskHandlerShow(taskId, userId);
		TaskDescription currentTask = taskHandlerShow.getCurrentTask();
		
		List<TaskDesc> taskDescList = new ArrayList<TaskDesc>();
		List<TaskDescription> taskDescriptions =  taskHandlerService.getNextTasks(unitId,OFFICE_SUBSYSTEM,currentTask.getTaskDefinitionKey(), currentTask.getProcessInstanceId());
		Set<String>userIds=new HashSet<String>();
		Map<String, HalfSelectUser> halfMap = halfSelectUserService
				.getHalfSelectPrincipals(OFFICE_SUBSYSTEM,unitId);
		TaskDesc tds;
		userIds=new HashSet<String>();
		for (TaskDescription item : taskDescriptions) {
			tds=new TaskDesc();
			tds.setProcessInstanceId(item.getProcessInstanceId());
			tds.setTaskId(item.getTaskId());
			tds.setTaskDefinitionKey(item.getTaskDefinitionKey());
			tds.setTaskName(item.getTaskName());
			String nextStepAssigneeId = "";
			int j = 0;
			for (String userid : item.getPrincipals()) {
				if(j==0){
					nextStepAssigneeId += userid;
				}else{
					nextStepAssigneeId +=","+ userid;
				}
				j++;
			}
			String taskDetailName= "";
			String taskUserName = "";
			String checkHalfUsers ="";
			String checkHalfUserName ="";
			if (StringUtils.isNotBlank(nextStepAssigneeId)) {
				String[] nextUserIds = nextStepAssigneeId.split(",");
				Map<String, User> userMap = userService.getUsersMap(nextUserIds);
				for (int i = 0; i < nextUserIds.length; i++) {
					if (halfMap.get(nextUserIds[i]) != null) {
						checkHalfUsers += nextUserIds[i] + ",";
						if (nextStepAssigneeId.indexOf(nextUserIds[i] + ",") >= 0) {
							nextStepAssigneeId = nextStepAssigneeId
									.replaceAll(nextUserIds[i] + ",", "");
							checkHalfUserName += ","+halfMap.get(nextUserIds[i]) .getHalfName();
						} else if (nextStepAssigneeId.indexOf("," + nextUserIds[i]) >= 0) {
							nextStepAssigneeId = nextStepAssigneeId
									.replaceAll("," + nextUserIds[i], "");
							checkHalfUserName += "," +halfMap.get(nextUserIds[i]) .getHalfName();
						} else if (nextStepAssigneeId.indexOf(nextUserIds[i]) >= 0) {
							nextStepAssigneeId = nextStepAssigneeId.replaceAll(nextUserIds[i], "");
							checkHalfUserName +=","+ halfMap.get(nextUserIds[i]) .getHalfName();
						}
					} else {
						User nextUser = userMap.get(nextUserIds[i]);
						if (nextUser != null) {
							taskUserName += nextUser.getRealname();
						} else {
							taskUserName += "该用户已删除";
						}
						if (i < (nextUserIds.length - 1)) {
							taskUserName += ",";
						}
					}
				}
				if(StringUtils.isNotBlank(taskUserName)){
					if(taskUserName.endsWith(",")){
						taskUserName =taskUserName+checkHalfUserName.substring(1, checkHalfUserName.length());
					}else{
						taskUserName +=checkHalfUserName;
					}
				}else{
					if(StringUtils.isNotBlank(checkHalfUserName)){
						taskUserName +=checkHalfUserName.substring(1, checkHalfUserName.length());
					}
				}
				
				if(StringUtils.isNotBlank(nextStepAssigneeId)){
					taskDetailName = userService.getUserDetailNamesStr(nextStepAssigneeId
							.split(","));
				}
			}
			
			//处理半选人
			tds.setAssigneeId(nextStepAssigneeId);
			tds.setAssigneeName(taskUserName);
			tds.setAssigneeDetailNames(taskDetailName);
			tds.setCheckHalfUsers(checkHalfUsers);
			
			
			tds.setCandidateUsers(item.getCandidateUsers());
			tds.setProxyUsers(item.getProxyUsers());
			tds.setDelegateUsers(item.getDelegateUsers());
			tds.setStep(item.getStep());
			userIds.add(tds.getProxyUsersIds());
			userIds.add(tds.getDelegateUsersIds());
			
			taskDescList.add(tds);
		}
		return taskDescList;
	}
	
	@Override
	public int getTaskDescNum(String unitId, String userId, String taskId){
		TaskHandlerShow taskHandlerShow = taskHandlerService.getTaskHandlerShow(taskId, userId);
		TaskDescription currentTask = taskHandlerShow.getCurrentTask();
		
		List<TaskDescription> taskDescriptions =  taskHandlerService.getNextTasks(unitId,OFFICE_SUBSYSTEM,currentTask.getTaskDefinitionKey(), currentTask.getProcessInstanceId());
		if(CollectionUtils.isNotEmpty(taskDescriptions)){
			return taskDescriptions.size();
		}else{
			return 0;
		}
	}
	
	/**
	 * 寻找elementId对应的节点所连接的所有分支的下一个任务节点
	 * @param map	任务节点MAP
	 * @param elementId	起点
	 * @param dataList	连线节点
	 * @param condition 条件
	 * @param result
	 */
	public void getUserTaskElement(Map<String, JSONObject> map, String elementId, 
			List<Element> dataList, JSONArray result, String condition){
		if(CollectionUtils.isNotEmpty(dataList)){
			for(Element e : dataList){
				String sourceRef = e.attributeValue("sourceRef");
				String targetRef = e.attributeValue("targetRef");
				//当指定的元素连接到任务节点，则添加该任务
				if(StringUtils.isNotBlank(sourceRef) && StringUtils.isNotBlank(targetRef) && StringUtils.equals(sourceRef, elementId)){
					//如果是条件
					if(elementId.startsWith("ExclusiveGateway")){
						Element conditionExpression = e.element("conditionExpression");
						if(conditionExpression != null){
							String s = conditionExpression.getTextTrim();
							if(condition != null && !(StringUtils.isNotBlank(s) && ("${"+condition+"}").equals(s.replaceAll(" ", "")))){
								continue;
							}
						}
					}
					if(targetRef.startsWith("UserTask")){
						JSONObject node = map.get(targetRef);
						if(node != null){
							result.add(node);
						}
					}
					else{
						//当连接的不是任务节点，如条件节点、并行节点，则继续寻找下个节点
						getUserTaskElement(map, targetRef, dataList, result, condition);
					}
				}
			}
		}
	}
	
	/**
	 * 获取流程定义的第一步审核步骤详情
	 * @param json
	 * @param unitId
	 * @param flowId
	 * @param condition
	 * @return
	 */
	@Override
	public String getFirstTask(String unitId, String userId, String flowId, String condition){
		JSONObject json = new JSONObject();
		try {
			//取流程定义，并转成XML
			byte[] bytes = repositoryService.getModelEditorSource(flowId);
			byte[] bpmnBytes = XmlJsonConverter.convertToXML(bytes);
			
			//读取XML
			SAXReader saxReader = new SAXReader();
			Document doc = saxReader.read(new ByteArrayInputStream(bpmnBytes));
			//获取开始节点
			Element root = doc.getRootElement();
			Element startEvent = root.element("process").element("startEvent");
			if(startEvent != null){
				String startEventId = startEvent.attributeValue("id");
				List<Element> sequenceFlows = root.element("process").elements("sequenceFlow");//连接线节点
				List<Element> userTasks = root.element("process").elements("userTask");//任务节点
				Map<String, JSONObject> userTaskMap = new HashMap<String, JSONObject>();
				Set<String> userIdSet = new HashSet<String>();
				
				for(Element e : userTasks){//将需要的任务信息封装成map
					JSONObject node = new JSONObject();
					node.put("userTaskId", e.attributeValue("id"));
					node.put("taskName", e.attributeValue("name"));

					
					try {
						Element child = e.element("extensionElements").element("customResource").element("resourceAssignmentExpression").element("formalExpression");
						if(child != null){
							String[] users = child.getTextTrim().split(",");
							String userIdStr = "";
							for(String item : users){
								String userid = item.substring(item.indexOf("(")+1, item.indexOf(")"));
								if(StringUtils.isBlank(userIdStr)){
									userIdStr = userid;
								}else{
									userIdStr += "," + userid;
								}
								userIdSet.add(userid);
							}
							node.put("assigneeId", userIdStr);
						}
					} catch (Exception e1) {
						e1.printStackTrace();
						node.put("assigneeId", "-1");
					}
					userTaskMap.put(e.attributeValue("id"), node);
				}
				
				//设置用户姓名
				Map<String, String> userUserMap = this.getHalfSelectPrincipals(unitId, userId, userIdSet);
				
				
				for(String key : userTaskMap.keySet()){
					JSONObject node = userTaskMap.get(key);
					String ids = (String)node.get("assigneeId");
					String usernames = "";
					if(StringUtils.isNotBlank(ids) && !"-1".equals(ids)){
						String[] userids = ids.split(",");
						if(userids.length > 0){
							for(String userid : userids){
								String username = userUserMap.get(userid);
								if(StringUtils.isBlank(username)){
									username = "该用户已删除";
								}
								if(StringUtils.isBlank(usernames)){
									usernames = username;
								}else{
									usernames += "," + username;
								}
							}
						}
						node.put("assigneeName", usernames);
					}
					else{
						node.put("assigneeName", "未设置审核人员");
					}
					userTaskMap.put(key, node);
				}
				
				JSONArray userNodes = new JSONArray();
				getUserTaskElement(userTaskMap, startEventId, sequenceFlows, userNodes, condition);
				json.put("result_array", userNodes);
				json.put("result_status", 1);
			}
			else{
				json.put("result_status", 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.put("result_status", 0);
		}
		
		return json.toString();
	}
	
	/**
	 * 获取下一步审核步骤详情
	 * @param unitId
	 * @param userId
	 * @param taskId
	 * @param condition
	 * @return
	 */
	@Override
	public String getNextTask(String unitId, String userId, String taskId, String condition){
		JSONObject json = new JSONObject();
		
		try{
		//获取当前任务
		Task task = taskService.createTaskQuery().taskId(taskId)
				.taskInvolvedUser(userId).singleResult();
		if (task == null) {
			json.put("result_status", 0);
			return json.toString();
		}
		
		//获取流程定义
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(task.getProcessDefinitionId());
		List<ActivityImpl> activitiList = processDefinition.getActivities();
	    String activitiId = task.getTaskDefinitionKey();
	    
	    Set<String> outgoings = new HashSet<String>();
		for (ActivityImpl activityImpl : activitiList) {
			String id = activityImpl.getId();
			if (activitiId.equals(id)) {
				List<PvmTransition> outTransitions = activityImpl
						.getOutgoingTransitions();// 获取从某个节点出来的所有线路
				getOutgoing(outTransitions, outgoings, condition);
				break;
			}
		}
		
		Map<String, String[]> taskMap = processHandlerService
				.getTaskMapByProcessDefinitionJson(unitId, task.getProcessInstanceId(),
						OFFICE_SUBSYSTEM);

		JSONArray userNodes = new JSONArray();
		for (Iterator<String> iterator = outgoings.iterator(); iterator
				.hasNext();) {
			String userTaskId = iterator.next();
			if (taskMap.containsKey(userTaskId)) {
				String[] t = taskMap.get(userTaskId);
				JSONObject node = new JSONObject();
				node.put("userTaskId", userTaskId);
				node.put("taskName", t[3]);
				node.put("assigneeId", t[0]);
				node.put("assigneeName", t[1]);
				userNodes.add(node);
			}
		}
		
		json.put("result_array", userNodes);
		json.put("result_status", 1);
		
		} catch (Exception e) {
			e.printStackTrace();
			json.put("result_status", 0);
		}
		
		return json.toString();
	}
	
	private void getOutgoing(List<PvmTransition> outTransitions,
			Set<String> outgoings, String condition) {
		for (PvmTransition transition : outTransitions) {
			ActivityImpl destination = (ActivityImpl) transition
					.getDestination();
			ActivityBehavior behavior = destination.getActivityBehavior();
			
			Object s = transition.getProperty("conditionText");
			if(condition == null || (s != null && ("${"+condition+"}").equals(s.toString().replaceAll(" ", "")))){
				outgoings.add(destination.getId());
			}
			
			if (behavior instanceof ParallelGatewayActivityBehavior
					|| behavior instanceof ExclusiveGatewayActivityBehavior) {
				getOutgoing(destination.getOutgoingTransitions(), outgoings, condition);
			}
		}
	}
	
	@Override
	public String getNextTaskInfo(String unitId, String userId, String state, String flowId, String taskId, String condition){
		if("0".equals(state)){//未进流程
			return this.getFirstTask(unitId, userId, flowId, condition);
		}else{//流程中
			return this.getCurrentTasks(flowId);
		}
	}
	
	@Override
	public String getCurrentTasks(String flowId){
		JSONObject json = new JSONObject();
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(flowId).list();
		
		JSONArray userNodes = new JSONArray();
		if(CollectionUtils.isNotEmpty(tasks)){
			for(Task task : tasks){
				List<IdentityLink> identityLinks = taskService.getIdentityLinksForTask(task.getId());
				
				String assigneeId = "";
				String assigneeName = "";
				for (IdentityLink identityLink : identityLinks) {
					if (AgileIdentityLinkType.CANDIDATE.equals(identityLink.getType())) {
						if(StringUtils.isBlank(assigneeId)){
							assigneeId = identityLink.getUserId();
						}else{
							assigneeId += "," + identityLink.getUserId();
						}
					}
				}
				if(StringUtils.isNotBlank(assigneeId)){
					String[] ids = assigneeId.split(",");
					List<User> users = userService.getUsersWithDel(ids);
					Map<String, User> userMap = new HashMap<String, User>();
					for(User user:users){
						userMap.put(user.getId(), user);
					}
					for(String id:ids){
						User user = userMap.get(id);
						String username = "";
						if(user != null){
							username = user.getRealname();
							if(user.getIsdeleted()){
								username += "(已删除)";
							}
						}else{
							username = "(该用户已删除)";
						}
						if(StringUtils.isBlank(assigneeName)){
							assigneeName = username;
						}else{
							assigneeName += "," + username;
						}
					}
				}
				
				JSONObject node = new JSONObject();
				node.put("userTaskId", task.getTaskDefinitionKey());
				node.put("taskName", task.getName());
				node.put("assigneeId", assigneeId);
				node.put("assigneeName",assigneeName);
				
				userNodes.add(node);
			}
		}
		
		json.put("result_array", userNodes);
		json.put("result_status", 1);
		
		return json.toString();
	}
	
	@Override
	public void retractFlow(String nowTaskId, String oldTaskId, String taskDefinitionKey){
		try {
			Task task = taskService.createTaskQuery().taskId(nowTaskId).active().singleResult();
			String processInstanceId = task.getProcessInstanceId();
			
			taskHandlerService.revokeTask(oldTaskId, taskDefinitionKey, nowTaskId);
			
			List<Task> list = taskService.createTaskQuery()
					.processInstanceId(processInstanceId)
					.active().list();
			Task currentTask = null;
			for(Task item : list){
				if(taskDefinitionKey.equals(item.getTaskDefinitionKey())){
					currentTask = item;
					break;
				}
			}
			
			OfficeConvertFlowTask nowTask = officeConvertFlowTaskService.getConvertFlowByTaskId(nowTaskId);
			if(nowTask != null){
				//将老的任务信息删除
				String convertFlowId = nowTask.getConvertFlowId();
				officeConvertFlowTaskService.delete(convertFlowId, new String[]{nowTaskId, oldTaskId});
				
				//新增当前待审核
				OfficeConvertFlow ocf = officeConvertFlowService.getOfficeConvertFlowById(convertFlowId);
				if(ocf != null && currentTask != null){
					List<IdentityLink> identityLinks = taskService
							.getIdentityLinksForTask(currentTask.getId());
					String userIds = "";
					for (IdentityLink identityLink : identityLinks) {
						if (AgileIdentityLinkType.CANDIDATE.equals(identityLink.getType())) {
							if(StringUtils.isBlank(userIds)){
								userIds = identityLink.getUserId();
							}else{
								userIds += "," + identityLink.getUserId();
							}
						}
					}
					
					OfficeConvertFlowTask ent = new OfficeConvertFlowTask();
					ent.setConvertFlowId(ocf.getId());
					ent.setAuditParm(userIds);
					ent.setParm(currentTask.getId());
					ent.setStatus(Constants.APPLY_STATE_NEED_AUDIT);
					
					officeConvertFlowTaskService.save(ent);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean checkSendFlowMsg(String flowId, String taskDefinitionKey){
		try {
			ProcessInstance processInstance = runtimeService
					.createProcessInstanceQuery()
					.processInstanceId(flowId).singleResult();
			if (processInstance == null) {
				return false;
			}
			String processDefinitionId = processInstance.getProcessDefinitionId();
			
			if(StringUtils.isBlank(taskDefinitionKey) && StringUtils.isBlank(processDefinitionId))
				return false;
			
			ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
					.getDeployedProcessDefinition(processDefinitionId);
			List<ActivityImpl> activitiList = processDefinition.getActivities();
			
			Set<String> outgoings = new HashSet<String>();
			for (ActivityImpl activityImpl : activitiList) {
				String id = activityImpl.getId();
				if (taskDefinitionKey.equals(id)) {
					List<PvmTransition> outTransitions = activityImpl.getOutgoingTransitions();// 获取从某个节点出来的所有线路
					if(CollectionUtils.isEmpty(outTransitions)){
						return false;
					}
					else{
						getOutgoing(outTransitions, outgoings, null);
						break;
					}
				}
			}
			List<Task> tasks = taskService.createTaskQuery().processInstanceId(flowId).active().list();
			if(CollectionUtils.isEmpty(tasks))
				return false;
			
			for(Task task : tasks){//待处理任务
				for(String key : outgoings){//目标节点后所有任务节点
					if(key.equals(task.getTaskDefinitionKey())){
						return true;
					}
				}
			}
			
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Map<String, String> getHalfSelectPrincipals(String unitId, String userId, Set<String> userIdSet){//TODO
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> halfmap = new HashMap<String, String>();
		
		User user = userService.getUser(userId);
		Dept dept = deptService.getDept(user.getDeptid());
		if(dept != null){
			userIdSet.add(dept.getPrincipan());
			halfmap.put(DEPT_PRINCIPAL_ID, dept.getPrincipan());
			
			userIdSet.add(dept.getLeaderId());
			halfmap.put(DEPT_LEADERI_D, dept.getLeaderId());
			
			userIdSet.add(dept.getDeputyHeadId());
			halfmap.put(DEPUTY_HEAD_ID, dept.getDeputyHeadId());
		}
		userIdSet.add(userId);
		halfmap.put(INITIATOR_ID, userId);

		CustomRole role = customRoleService.getCustomRoleByRoleCode(unitId, ROLE_CODE_SCHOOLMASTER);
		if(role != null){
			List<CustomRoleUser> roleUserList = customRoleUserService.getCustomRoleUserList(role.getId());
			if(CollectionUtils.isNotEmpty(roleUserList)){
				userIdSet.add(roleUserList.get(0).getUserId());
				halfmap.put(ROLE_CODE_SCHOOLMASTER, roleUserList.get(0).getUserId());
			}
		}
		Map<String, User> userMap = userService.getUserWithDelMap(userIdSet.toArray(new String[0]));
		for(String key : userMap.keySet()){
			map.put(key, userMap.get(key).getRealname());
		}
		
		this.setHalfMapInfo(map, userMap, halfmap, DEPT_PRINCIPAL_ID);
		this.setHalfMapInfo(map, userMap, halfmap, DEPT_LEADERI_D);
		this.setHalfMapInfo(map, userMap, halfmap, DEPUTY_HEAD_ID);
		this.setHalfMapInfo(map, userMap, halfmap, INITIATOR_ID);
		this.setHalfMapInfo(map, userMap, halfmap, ROLE_CODE_SCHOOLMASTER);
		
		return map;
	}
	
	private void setHalfMapInfo(Map<String, String> map, Map<String, User> userMap, Map<String, String> halfMap, String halfRoleId){
		String halfUserId = halfMap.get(halfRoleId);
		if(StringUtils.isNotBlank(halfUserId)){
			User user = userMap.get(halfUserId);
			if(user != null){
				map.put(halfRoleId, user.getRealname());
			}else{
				map.put(halfRoleId, "该用户已删除");
			}
		}
	}
	
	public void setTaskHandlerService(TaskHandlerService taskHandlerService) {
		this.taskHandlerService = taskHandlerService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setHalfSelectUserService(HalfSelectUserService halfSelectUserService) {
		this.halfSelectUserService = halfSelectUserService;
	}

	public void setProcessHandlerService(ProcessHandlerService processHandlerService) {
		this.processHandlerService = processHandlerService;
	}

	public void setOfficeConvertFlowService(
			OfficeConvertFlowService officeConvertFlowService) {
		this.officeConvertFlowService = officeConvertFlowService;
	}

	public void setOfficeConvertFlowTaskService(
			OfficeConvertFlowTaskService officeConvertFlowTaskService) {
		this.officeConvertFlowTaskService = officeConvertFlowTaskService;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	}

	public void setCustomRoleUserService(CustomRoleUserService customRoleUserService) {
		this.customRoleUserService = customRoleUserService;
	}

}
