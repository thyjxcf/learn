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
import net.zdsoft.jbpm.activiti.engine.task.AgileIdentityLinkType;
import net.zdsoft.jbpm.core.entity.Comment;
import net.zdsoft.jbpm.core.entity.HistoricTask;
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
import net.zdsoft.office.teacherLeave.dto.HisTask;
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
				officeAttendLecture.setHisTaskList(getHisTisk(officeAttendLecture.getFlowId()));
			}
		}
		return officeAttendLecture;
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
	public List<OfficeAttendLecture> getOfficeAttendLectureList(String unitId, String userId, String state,
			Date startTime, Date endTime, Pagination page) {
		List<OfficeAttendLecture> officeAttendLectureList = officeAttendLectureDao.getOfficeAttendLectureList(unitId,userId,state,startTime,endTime, page);
		Set<String> classIds = new HashSet<String>();
		for (OfficeAttendLecture item : officeAttendLectureList) {
			classIds.add(item.getClassId());
		}
		Map<String, BasicClass> classMapWithDeleted = basicClassService.getClassMapWithDeleted(classIds.toArray(new String[0]));
		for (OfficeAttendLecture item : officeAttendLectureList) {
			BasicClass basicClass = classMapWithDeleted.get(item.getClassId());
			if(basicClass!=null){
				item.setClassName(basicClass.getClassnamedynamic());
			}
		}
		return officeAttendLectureList;
	}

	@Override
	public void startFlow(OfficeAttendLecture officeAttendLecture, String userId) {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("pass",true);
		if(StringUtils.isBlank(officeAttendLecture.getId())){
			officeAttendLecture.setId(UUIDUtils.newId());
			this.save(officeAttendLecture);
		}
		officeAttendLecture.setFlowId(processHandlerService.startProcessInstance(FlowConstant.OFFICE_SUBSYSTEM, 
				officeAttendLecture.getFlowId(), Integer.parseInt(FlowConstant.OFFICE_SUBSYSTEM+FlowConstant.OFFICE_ATTEND_LECTURE), 
				officeAttendLecture.getId(), userId, variables));
		this.update(officeAttendLecture);
		
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
			BasicClass basicClass = classMapWithDeleted.get(item.getClassId());
			if(basicClass!=null){
				item.setClassName(basicClass.getClassnamedynamic());
			}
		}
		return ArrayUtils.subList(list, page);
	}

	@Override
	public void doAudit(boolean pass, TaskHandlerSave taskHandlerSave, String id) {
		OfficeAttendLecture officeAttendLecture = officeAttendLectureDao.getOfficeAttendLectureById(id);
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("pass", pass);
		taskHandlerSave.setVariables(variables);
		if(pass){
			taskHandlerSave.getComment().setTextComment("[审核通过]"+taskHandlerSave.getComment().getTextComment());
		}else{
			taskHandlerSave.getComment().setTextComment("[审核不通过]"+taskHandlerSave.getComment().getTextComment());
		}
		TaskHandlerResult result = taskHandlerService.completeTask(taskHandlerSave);
		if(result.getStatus()==TaskHandlerResult.STATUS_FINISH){
			if(result.getResult()==TaskHandlerResult.RESULT_PASS){
				officeAttendLecture.setState(Constants.LEAVE_APPLY_FLOW_FINSH_PASS+"");
			}else if(result.getResult()==TaskHandlerResult.RESULT_NOT_PASS){
				officeAttendLecture.setState(Constants.LEAVE_APPLY_FLOW_FINSH_NOT_PASS+"");
			}
			update(officeAttendLecture);
		}
		
		officeConvertFlowService.completeTask(id, taskHandlerSave.getCurrentUserId(), taskHandlerSave.getCurrentTask().getTaskId(), result, pass);
		officeFlowSendMsgService.completeTaskSendMsg(taskHandlerSave.getCurrentUserId(), pass, officeAttendLecture, ConvertFlowConstants.OFFICE_ATTENDLECTURE, result);
	}
	
	public String[] getUserIds(String deptId){
		List<User> userList=userService.getUsersByDeptId(deptId);
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
			String deptId, Date startTime, Date endTime, String applyUserName) {
		String[] userIds=getUserIds(deptId);
		List<OfficeAttendLecture> officeList=officeAttendLectureDao.getOfficeCountList(unitId, userIds, startTime, endTime, applyUserName);
		List<OfficeAttendLecture> officeAttendList=new ArrayList<OfficeAttendLecture>();
		if(CollectionUtils.isNotEmpty(officeList)){
			Map<String,User> userMap=userService.getUserMap(unitId);
			Map<String,Dept> deptMap=deptService.getDeptMap(unitId);
			Map<String,OfficeAttendLecture> officeMap=new HashMap<String, OfficeAttendLecture>();
			boolean flagUser=MapUtils.isNotEmpty(userMap);
			boolean flagDept=MapUtils.isNotEmpty(deptMap);
			for(OfficeAttendLecture office:officeList){
				if(flagUser){
					User user=userMap.get(office.getApplyUserId());
					if(user==null) continue;
					office.setDeptId(user!=null?user.getDeptid():"");					
				}
				OfficeAttendLecture officeAttendLecture=new OfficeAttendLecture();
				if(flagDept){
					Dept dept=deptMap.get(office.getDeptId());
					if(dept==null) continue;
					officeAttendLecture.setDeptName(dept!=null?dept.getDeptname():"");
					officeAttendLecture.setDeptId(office.getDeptId());
				}
				OfficeAttendLecture o=officeMap.get(office.getDeptId());
				if(o==null){
					officeAttendLecture.setTeacherNum(1);
					officeAttendLecture.setLectureNum(office.getLectureNum());
					officeMap.put(office.getDeptId(),officeAttendLecture);
				}else{
					officeAttendLecture.setTeacherNum(o.getTeacherNum()+1);
					officeAttendLecture.setLectureNum(o.getLectureNum()+office.getLectureNum());
					officeMap.put(office.getDeptId(),officeAttendLecture);
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
	public List<OfficeAttendLecture> getOfficeCountInfo(String unitId,Date startTime,Date endTime,String deptId,String applyUserName,Pagination page){
		String[] userIds=getUserIds(deptId);
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
					BasicClass basicClass=mapClass.get(office.getClassId());
					office.setClassName(basicClass!=null?basicClass.getClassnamedynamic():"");
				}
			}
		}
		return officeList;
	}
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	
}