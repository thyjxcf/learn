package net.zdsoft.office.attendLecture.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.BasicClassService;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.component.flowManage.constant.FlowConstant;
import net.zdsoft.jbpm.core.entity.TaskDescription;
import net.zdsoft.jbpm.core.entity.TaskHandlerResult;
import net.zdsoft.jbpm.core.entity.TaskHandlerSave;
import net.zdsoft.jbpm.core.service.ProcessHandlerService;
import net.zdsoft.jbpm.core.service.TaskHandlerService;
import net.zdsoft.keel.util.ArrayUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keel.util.UUIDUtils;
import net.zdsoft.office.attendLecture.dao.OfficeAttendLectureDao;
import net.zdsoft.office.attendLecture.entity.OfficeAttendLecture;
import net.zdsoft.office.attendLecture.service.OfficeAttendLectureService;
import net.zdsoft.office.convertflow.constant.ConvertFlowConstants;
import net.zdsoft.office.convertflow.service.OfficeConvertFlowService;
import net.zdsoft.office.convertflow.service.OfficeFlowSendMsgService;
import net.zdsoft.office.expense.constant.OfficeExpenseConstants;
import net.zdsoft.office.officeFlow.dto.HisTask;
import net.zdsoft.office.officeFlow.service.OfficeFlowService;
import net.zdsoft.office.officeFlow.service.OfficeFlowStepInfoService;
import net.zdsoft.office.util.Constants;
/**
 * office_attend_lecture(听课信息表)
 * @author 
 * 
 */
public class OfficeAttendLectureServiceImpl implements OfficeAttendLectureService{
	private OfficeAttendLectureDao officeAttendLectureDao;
	private BasicClassService basicClassService;
	private ProcessHandlerService processHandlerService;
	private OfficeConvertFlowService officeConvertFlowService;
	private OfficeFlowSendMsgService officeFlowSendMsgService;
	private TaskHandlerService taskHandlerService;
	private UserService userService;
	private DeptService deptService;
	private OfficeFlowService officeFlowService;
	private OfficeFlowStepInfoService officeFlowStepInfoService;
	
	public void setTaskHandlerService(TaskHandlerService taskHandlerService) {
		this.taskHandlerService = taskHandlerService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setProcessHandlerService(ProcessHandlerService processHandlerService) {
		this.processHandlerService = processHandlerService;
	}

	public void setOfficeConvertFlowService(OfficeConvertFlowService officeConvertFlowService) {
		this.officeConvertFlowService = officeConvertFlowService;
	}

	public void setOfficeFlowSendMsgService(OfficeFlowSendMsgService officeFlowSendMsgService) {
		this.officeFlowSendMsgService = officeFlowSendMsgService;
	}

	public void setOfficeAttendLectureDao(OfficeAttendLectureDao officeAttendLectureDao) {
		this.officeAttendLectureDao = officeAttendLectureDao;
	}

	public void setBasicClassService(BasicClassService basicClassService) {
		this.basicClassService = basicClassService;
	}

	@Override
	public OfficeAttendLecture save(OfficeAttendLecture officeAttendLecture){
		return officeAttendLectureDao.save(officeAttendLecture);
	}

	@Override
	public Integer delete(String[] ids){
		return officeAttendLectureDao.delete(ids);
	}

	@Override
	public Integer update(OfficeAttendLecture officeAttendLecture){
		return officeAttendLectureDao.update(officeAttendLecture);
	}

	@Override
	public OfficeAttendLecture getOfficeAttendLectureById(String id){
		OfficeAttendLecture officeAttendLecture = officeAttendLectureDao.getOfficeAttendLectureById(id);
		if(officeAttendLecture != null){
			if(StringUtils.isNotEmpty(officeAttendLecture.getFlowId())){
				List<HisTask> hisTask = officeFlowService.getHisTask(officeAttendLecture.getFlowId());
				officeAttendLecture.setHisTaskList(hisTask);
			}
			
			if(org.apache.commons.lang3.StringUtils.equals("1", officeAttendLecture.getType()+"")){
				officeAttendLecture.setClassId(officeAttendLecture.getClassId());
				officeAttendLecture.setGradeId(officeAttendLecture.getGradeId());
			}
		}
		return officeAttendLecture;
	}

	@Override
	public List<OfficeAttendLecture> getOfficeAttendLectureList(String unitId, String userId, String state,
			Date startTime, Date endTime, Pagination page) {
		List<OfficeAttendLecture> officeAttendLectureList = officeAttendLectureDao.getOfficeAttendLectureList(unitId,userId,state,startTime,endTime, page);
		Set<String> classIds = new HashSet<String>();
		for (OfficeAttendLecture item : officeAttendLectureList) {
			classIds.add(item.getClassId());
		}
		Map<String, BasicClass> classMapWithDeleted = basicClassService.getClassMapWithDeleted(classIds.toArray(new String[0]));
		for (OfficeAttendLecture item : officeAttendLectureList) {
			if(org.apache.commons.lang3.StringUtils.equals("0", item.getType()+"")){
				BasicClass basicClass = classMapWithDeleted.get(item.getClassId());
				if(basicClass!=null){
					item.setClassName(basicClass.getClassnamedynamic());
				}
			}else{
				item.setClassName(item.getClassId());
			}
		}
		return officeAttendLectureList;
	}

	@Override
	public void startFlow(OfficeAttendLecture officeAttendLecture, String userId) {
		String itemId = officeAttendLecture.getId();
		if(StringUtils.isBlank(itemId)){
			itemId = UUIDUtils.newId();
		}
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("pass",true);
		String flowId = processHandlerService.startProcessInstance(FlowConstant.OFFICE_SUBSYSTEM, 
				officeAttendLecture.getFlowId(), Integer.parseInt(FlowConstant.OFFICE_SUBSYSTEM+FlowConstant.OFFICE_ATTEND_LECTURE), 
				itemId, userId, variables);
		officeFlowStepInfoService.batchUpdateByFlowId(officeAttendLecture.getFlowId(), flowId);
		officeAttendLecture.setFlowId(flowId);
		if(StringUtils.isBlank(officeAttendLecture.getId())){
			officeAttendLecture.setId(itemId);
			this.save(officeAttendLecture);
		}else{
			this.update(officeAttendLecture);
		}
		
		officeConvertFlowService.startFlow(officeAttendLecture, ConvertFlowConstants.OFFICE_ATTENDLECTURE);
		officeFlowSendMsgService.startFlowSendMsg(officeAttendLecture, ConvertFlowConstants.OFFICE_ATTENDLECTURE);
		
	}

	@Override
	public List<OfficeAttendLecture> getOfficeAttendLectureAuditList(String unitId, String userId, String searchType,
			Date startTime, Date endTime, String applyUserName, Pagination page) {
		List<OfficeAttendLecture> list = new ArrayList<OfficeAttendLecture>();
		if(StringUtils.isBlank(searchType) || "0".equals(searchType)){//待审核
			List<TaskDescription> todoTaskList = taskHandlerService.getTodoTasks(userId, Integer.parseInt(FlowConstant.OFFICE_SUBSYSTEM+FlowConstant.OFFICE_ATTEND_LECTURE));
			if(CollectionUtils.isNotEmpty(todoTaskList)){
				Set<String> flowIdSet= new HashSet<String>();
				for (TaskDescription task : todoTaskList) {
					flowIdSet.add(task.getProcessInstanceId());	
				}
				Map<String, OfficeAttendLecture> officeAttendLectureMap = officeAttendLectureDao.getAttendLectureMap(flowIdSet.toArray(new String[0]),unitId,startTime,
						endTime,applyUserName);
				Set<String> userIdSet = new HashSet<String>();
				for (String id : officeAttendLectureMap.keySet()) {
					OfficeAttendLecture expense = officeAttendLectureMap.get(id);
					if(expense!=null){
						userIdSet.add(expense.getApplyUserId());
					}
				}
				Map<String,User> userMap = new HashMap<String, User>();
				userMap = userService.getUserWithDelMap(userIdSet.toArray(new String[0]));
				for (TaskDescription task : todoTaskList) {
					OfficeAttendLecture officeAttendLecture = officeAttendLectureMap.get(task.getProcessInstanceId());
					if(officeAttendLecture!=null){
						officeAttendLecture.setTaskId(task.getTaskId());
						officeAttendLecture.setTaskName(task.getTaskName());
						User user = userMap.get(officeAttendLecture.getApplyUserId());
						if(user!=null){
							officeAttendLecture.setApplyUserName(user.getRealname());
						}else{
							officeAttendLecture.setApplyUserName("用户已删除");
						}
						list.add(officeAttendLecture);
					}
				}
			}
		}
		else if("3".equals(searchType)){
			list = this.getOfficeAttendLectureList(unitId, null, null, startTime, endTime, null);
			Set<String> userIdSet = new HashSet<String>();
			for(OfficeAttendLecture item : list){
				userIdSet.add(item.getApplyUserId());
			}
			Map<String,User> userMap = new HashMap<String, User>();
			userMap = userService.getUserWithDelMap(userIdSet.toArray(new String[0]));
			List<OfficeAttendLecture> list2 = new ArrayList<OfficeAttendLecture>();
			for(OfficeAttendLecture item : list){
				User user = userMap.get(item.getApplyUserId());
				if(user != null && user.getRealname().contains(applyUserName)){
					list2.add(item);
				}
			}
			list = officeFlowService.haveDoneAudit(list2, userId, page);
		}
		else{//已审核
			list = officeAttendLectureDao.getAuditedList(userId, new String[]{OfficeExpenseConstants.OFFCIE_EXPENSE_PASS, 
					OfficeExpenseConstants.OFFCIE_EXPENSE_FAILED},unitId,startTime,
					endTime,applyUserName,page);
			if(CollectionUtils.isNotEmpty(list)){
				Set<String> userIdSet = new HashSet<String>();
				for(OfficeAttendLecture item : list){
					userIdSet.add(item.getApplyUserId());
				}
				Map<String,User> userMap = new HashMap<String, User>();
				userMap = userService.getUserWithDelMap(userIdSet.toArray(new String[0]));
				for(OfficeAttendLecture item : list){
					User user = userMap.get(item.getApplyUserId());
					if(user!=null){
						item.setApplyUserName(user.getRealname());
					}else{
						item.setApplyUserName("用户已删除");
					}
				}
			}
		}
		Set<String> classIds = new HashSet<String>();
		Set<String> applyUserIds = new HashSet<String>();
		for (OfficeAttendLecture item : list) {
			classIds.add(item.getClassId());
			applyUserIds.add(item.getApplyUserId());
		}
		Map<String, BasicClass> classMapWithDeleted = basicClassService.getClassMapWithDeleted(classIds.toArray(new String[0]));
		for (OfficeAttendLecture item : list) {
			if(org.apache.commons.lang3.StringUtils.equals("0", item.getType()+"")){
				BasicClass basicClass = classMapWithDeleted.get(item.getClassId());
				if(basicClass!=null){
					item.setClassName(basicClass.getClassnamedynamic());
				}
			}else{
				item.setClassName(item.getClassId());
			}
		}
		return ArrayUtils.subList(list, page);
	}

	@Override
	public void doAudit(boolean pass, TaskHandlerSave taskHandlerSave, String id, String currentStepId) {
		OfficeAttendLecture officeAttendLecture = officeAttendLectureDao.getOfficeAttendLectureById(id);
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("pass", pass);
		taskHandlerSave.setVariables(variables);
		TaskHandlerResult result;
		if(pass){
			taskHandlerSave.getComment().setTextComment("[审核通过]"+taskHandlerSave.getComment().getTextComment());
			result = taskHandlerService.completeTask(taskHandlerSave);
		}else{
			taskHandlerSave.getComment().setTextComment("[审核不通过]"+taskHandlerSave.getComment().getTextComment());
			boolean isNotFinish = taskHandlerService.isExclusiveGatewayForNext(officeAttendLecture.getUnitId(), taskHandlerSave.getSubsystemId(), taskHandlerSave.getCurrentTask().getTaskDefinitionKey(), officeAttendLecture.getFlowId());
			if(isNotFinish){
				result = taskHandlerService.completeTask(taskHandlerSave);
			}else{
				result = taskHandlerService.suspendTask(taskHandlerSave);
			}
		}
		if(result.getStatus()==TaskHandlerResult.STATUS_FINISH){
			if(result.getResult()==TaskHandlerResult.RESULT_PASS){
				officeAttendLecture.setState(Constants.LEAVE_APPLY_FLOW_FINSH_PASS+"");
			}else if(result.getResult()==TaskHandlerResult.RESULT_NOT_PASS){
				officeAttendLecture.setState(Constants.LEAVE_APPLY_FLOW_FINSH_NOT_PASS+"");
			}
			update(officeAttendLecture);
		}
		
		officeConvertFlowService.completeTask(id, officeAttendLecture.getFlowId(), taskHandlerSave.getCurrentUserId(), taskHandlerSave.getCurrentTask().getTaskId(), result, pass);
		if(result.getStatus()==TaskHandlerResult.STATUS_FINISH
				|| officeFlowService.checkSendFlowMsg(officeAttendLecture.getFlowId(), currentStepId)){
			officeFlowSendMsgService.completeTaskSendMsg(taskHandlerSave.getCurrentUserId(), pass, officeAttendLecture, ConvertFlowConstants.OFFICE_ATTENDLECTURE, result);
		}
	}
	
	public String[] getUserIds(String deptId){
		if(StringUtils.isBlank(deptId)){
			return null;
		}
		List<User> userList=userService.getUsersByDeptId(deptId);
		if(userList == null || userList.size() <= 0){
			return new String[]{"-1"};
		}
		String[] userIds=null;
		if(CollectionUtils.isNotEmpty(userList)){
			int length=userList.size();
			userIds=new String[length];
			for(int i=0;i<length;i++){
				userIds[i]=userList.get(i).getId();
			}
		}
		/*if(CollectionUtils.isEmpty(userList)&&StringUtils.isNotEmpty(deptId)){			
			userIds=new String[]{" "};
		}*/
		return userIds;
	}
	@Override
	public List<OfficeAttendLecture> getOfficeCountList(String unitId,
			String[]userIds, Date startTime, Date endTime, String applyUserName) {
		List<OfficeAttendLecture> officeList=officeAttendLectureDao.getOfficeCountMapList(unitId, userIds, startTime, endTime, applyUserName);
		List<OfficeAttendLecture> officeAttendList=new ArrayList<OfficeAttendLecture>();
		if(CollectionUtils.isNotEmpty(officeList)){
			Map<String,User> userMap=userService.getUserMap(unitId);
			Map<String,Dept> deptMap=deptService.getDeptMap(unitId);
			Map<String,OfficeAttendLecture> officeMap=new HashMap<String, OfficeAttendLecture>();
			boolean flagUser=MapUtils.isNotEmpty(userMap);
			boolean flagDept=MapUtils.isNotEmpty(deptMap);
			for(OfficeAttendLecture office:officeList){
				OfficeAttendLecture officeAttendLecture=new OfficeAttendLecture();
				if(flagUser){
					User user=userMap.get(office.getApplyUserId());
					if(user==null) continue;
					office.setDeptId(user!=null?user.getDeptid():"");
					office.setApplyUserName(user.getRealname());
					officeAttendLecture.setApplyUserName(user.getRealname());
					officeAttendLecture.setType(office.getType());
				}
				if(flagDept){
					Dept dept=deptMap.get(office.getDeptId());
					if(dept==null) continue;
					officeAttendLecture.setDeptName(dept!=null?dept.getDeptname():"");
					officeAttendLecture.setDeptId(office.getDeptId());
					officeAttendLecture.setApplyUserId(office.getApplyUserId());
				}
				//OfficeAttendLecture o=officeMap.get(office.getDeptId());
				OfficeAttendLecture o=officeMap.get(office.getApplyUserId());
				if(o==null){
					//officeAttendLecture.setTeacherNum(1);
					//officeAttendLecture.setLectureNum(office.getLectureNum());
					if(org.apache.commons.lang3.StringUtils.equals("0", office.getType()+"")){
						officeAttendLecture.setSchoolInNum(office.getLectureNum());
						officeMap.put(office.getApplyUserId(),officeAttendLecture);
					}else{
						officeAttendLecture.setSchoolOutNum(office.getLectureNum());
						officeMap.put(office.getApplyUserId(), officeAttendLecture);
					}
				}else{
					//officeAttendLecture.setTeacherNum(o.getTeacherNum()+1);
					//officeAttendLecture.setLectureNum(o.getLectureNum()+office.getLectureNum());
					if(org.apache.commons.lang3.StringUtils.equals("0", office.getType()+"")){
						officeAttendLecture.setSchoolInNum(office.getLectureNum()+(o.getSchoolInNum()==null?0:o.getSchoolInNum()));
						officeAttendLecture.setSchoolOutNum(o.getSchoolOutNum());
						officeMap.put(office.getApplyUserId(),officeAttendLecture);
					}else{
						officeAttendLecture.setSchoolOutNum(office.getLectureNum()+(o.getSchoolOutNum()==null?0:o.getSchoolOutNum()));
						officeAttendLecture.setSchoolInNum(o.getSchoolInNum());
						officeMap.put(office.getApplyUserId(), officeAttendLecture);
					}
				}
			}
			Iterator<String> it=officeMap.keySet().iterator();
			while(it.hasNext()){
				officeAttendList.add(officeMap.get((String)it.next()));
			}
		}
		return officeAttendList;
	}
	@Override
	public List<OfficeAttendLecture> getOfficeCountInfo(String unitId,Date startTime,Date endTime,String[] userIds,String applyUserName,Pagination page){
		List<OfficeAttendLecture> officeList=officeAttendLectureDao.getOfficeCountInfo(unitId,startTime,endTime, userIds,applyUserName,page);
		if(CollectionUtils.isNotEmpty(officeList)){
			Map<String,User> mapUser=userService.getUserMap(unitId);
			Map<String,BasicClass> mapClass=basicClassService.getClassMap(unitId);
			boolean flagUser=MapUtils.isNotEmpty(mapUser);
			boolean flagClass=MapUtils.isNotEmpty(mapClass);
			for(OfficeAttendLecture office:officeList){
				if(flagUser){
					User user=mapUser.get(office.getApplyUserId());
					office.setApplyUserName(user!=null?user.getRealname():"");
				}
				if(flagClass){
					if(org.apache.commons.lang3.StringUtils.equals("0", office.getType()+"")){
						BasicClass basicClass=mapClass.get(office.getClassId());
						office.setClassName(basicClass!=null?basicClass.getClassnamedynamic():"");
					}else{
						office.setClassName(office.getClassId());
					}
				}
			}
		}
		return officeList;
	}
	
	@Override
	public void changeFlow(String attendLectureId, String userId, String modelId, String jsonResult){
		OfficeAttendLecture attendLecture = officeAttendLectureDao.getOfficeAttendLectureById(attendLectureId);
		attendLecture.setState(String.valueOf(Constants.APPLY_STATE_NEED_AUDIT));
		
		Map<String,Object> variables=new HashMap<String, Object>();
		variables.put("pass", true);
		String flowId=processHandlerService.startProcessInstance(FlowConstant.OFFICE_SUBSYSTEM, modelId, Integer.parseInt(FlowConstant.OFFICE_SUBSYSTEM+FlowConstant.OFFICE_ATTEND_LECTURE), attendLectureId, userId,jsonResult, variables);
		officeFlowStepInfoService.batchUpdateByFlowId(modelId, flowId);
		attendLecture.setFlowId(flowId);
		update(attendLecture);

		officeConvertFlowService.startFlow(attendLecture, ConvertFlowConstants.OFFICE_ATTENDLECTURE);
		officeFlowSendMsgService.startFlowSendMsg(attendLecture, ConvertFlowConstants.OFFICE_ATTENDLECTURE);
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