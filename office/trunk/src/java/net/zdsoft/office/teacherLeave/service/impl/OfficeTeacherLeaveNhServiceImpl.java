package net.zdsoft.office.teacherLeave.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.entity.CurrentSemester;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.BasicClassService;
import net.zdsoft.eis.base.common.service.SemesterService;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.subsystemcall.service.TimetableSubsystemService;
import net.zdsoft.eis.component.flowManage.constant.FlowConstant;
import net.zdsoft.jbpm.core.entity.TaskDescription;
import net.zdsoft.jbpm.core.entity.TaskHandlerResult;
import net.zdsoft.jbpm.core.entity.TaskHandlerSave;
import net.zdsoft.jbpm.core.service.ProcessHandlerService;
import net.zdsoft.jbpm.core.service.TaskHandlerService;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keel.util.UUIDUtils;
import net.zdsoft.office.officeFlow.dto.HisTask;
import net.zdsoft.office.officeFlow.service.OfficeFlowService;
import net.zdsoft.office.officeFlow.service.OfficeFlowStepInfoService;
import net.zdsoft.office.studentLeave.entity.OfficeLeaveType;
import net.zdsoft.office.studentLeave.service.OfficeLeaveTypeService;
import net.zdsoft.office.teacherLeave.dao.OfficeTeacherLeaveNhDao;
import net.zdsoft.office.teacherLeave.entity.OfficeTeacherLeaveNh;
import net.zdsoft.office.teacherLeave.service.OfficeTeacherLeaveNhService;
import net.zdsoft.office.util.Constants;
import net.zdsoft.smsplatform.client.SendResult;
import net.zdsoft.smsplatform.client.ZDResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;
/**
 * office_teacher_leave_nh
 * @author 
 * 
 */
public class OfficeTeacherLeaveNhServiceImpl implements OfficeTeacherLeaveNhService{
	
	private UserService userService;
	private TeacherService teacherService;
	private SemesterService semesterService;
	private BasicClassService basicClassService;
	private TaskHandlerService taskHandlerService;
	private ProcessHandlerService processHandlerService;
	private OfficeLeaveTypeService officeLeaveTypeService;
	private TimetableSubsystemService timetableSubsystemService;
	private OfficeTeacherLeaveNhDao officeTeacherLeaveNhDao;
	private OfficeFlowService officeFlowService;
	private OfficeFlowStepInfoService officeFlowStepInfoService;

	@Override
	public OfficeTeacherLeaveNh save(OfficeTeacherLeaveNh officeTeacherLeaveNh){
		return officeTeacherLeaveNhDao.save(officeTeacherLeaveNh);
	}

	@Override
	public Integer delete(String[] ids){
		return officeTeacherLeaveNhDao.delete(ids);
	}

	@Override
	public Integer update(OfficeTeacherLeaveNh officeTeacherLeaveNh){
		return officeTeacherLeaveNhDao.update(officeTeacherLeaveNh);
	}
	
	@Override
	public void startFlow(OfficeTeacherLeaveNh officeTeacherLeaveNh,
			String userId) {
		if(StringUtils.isBlank(officeTeacherLeaveNh.getId())){
			officeTeacherLeaveNh.setId(UUIDUtils.newId());
		}
		Map<String, Object> variables = new HashMap<String, Object>();
		String flowId = processHandlerService.startProcessInstance(FlowConstant.OFFICE_SUBSYSTEM,officeTeacherLeaveNh.getFlowId(), Integer.parseInt(FlowConstant.OFFICE_SUBSYSTEM+officeTeacherLeaveNh.getFlowType()), officeTeacherLeaveNh.getId(), userId, variables);
		officeFlowStepInfoService.batchUpdateByFlowId(officeTeacherLeaveNh.getFlowId(), flowId);
		officeTeacherLeaveNh.setFlowId(flowId);
		
		OfficeTeacherLeaveNh everLeaveNh=officeTeacherLeaveNhDao.getOfficeTeacherLeaveNhById(officeTeacherLeaveNh.getId());
		if(everLeaveNh!=null){
			update(officeTeacherLeaveNh);
		}else{
			save(officeTeacherLeaveNh);
		}
		//update(officeTeacherLeaveNh);
	}

	@Override
	public OfficeTeacherLeaveNh getOfficeTeacherLeaveNhById(String id){
		OfficeTeacherLeaveNh officeTeacherLeaveNh = officeTeacherLeaveNhDao.getOfficeTeacherLeaveNhById(id);
		User user = userService.getUser(officeTeacherLeaveNh.getApplyUserId());
		officeTeacherLeaveNh.setUserName(user.getRealname());
		OfficeLeaveType leaveType = officeLeaveTypeService.getOfficeLeaveTypeById(officeTeacherLeaveNh.getLeaveTypeId());
		officeTeacherLeaveNh.setLeaveTypeName(leaveType.getName());
		if(StringUtils.isNotEmpty(officeTeacherLeaveNh.getFlowId())){
			List<HisTask> hisTask = officeFlowService.getHisTask(officeTeacherLeaveNh.getFlowId());
			officeTeacherLeaveNh.setHisTaskList(hisTask);
		}
		return officeTeacherLeaveNh;
	}
	
	@Override
	public Map<String, OfficeTeacherLeaveNh> getOfficeTeacherLeaveNhMapByIds(String[] ids){
		return officeTeacherLeaveNhDao.getOfficeTeacherLeaveNhMapByIds(ids);
	}

	@Override
	public List<OfficeTeacherLeaveNh> getOfficeTeacherLeaveNhList(){
		return officeTeacherLeaveNhDao.getOfficeTeacherLeaveNhList();
	}

	@Override
	public List<OfficeTeacherLeaveNh> getOfficeTeacherLeaveNhPage(String userId, Date startTime, Date endTime, String state, Pagination page){
		List<OfficeTeacherLeaveNh> officeTeacherLeaveNhs = officeTeacherLeaveNhDao.getOfficeTeacherLeaveNhPage(userId, startTime, endTime, state, page);
		Set<String> leaveTypeIdSet = new HashSet<String>();
		for(OfficeTeacherLeaveNh leaveNh:officeTeacherLeaveNhs){
			leaveTypeIdSet.add(leaveNh.getLeaveTypeId());
		}
		Map<String, OfficeLeaveType> leaveTypeMap = officeLeaveTypeService.getOfficeLeaveTypeMapByIds(leaveTypeIdSet.toArray(new String[0]));
		for(OfficeTeacherLeaveNh leaveNh:officeTeacherLeaveNhs){
			leaveNh.setLeaveTypeName(leaveTypeMap.get(leaveNh.getLeaveTypeId()).getName());
		}
		return officeTeacherLeaveNhs;
	}

	@Override
	public List<OfficeTeacherLeaveNh> getOfficeTeacherLeaveNhByUnitIdList(String unitId){
		return officeTeacherLeaveNhDao.getOfficeTeacherLeaveNhByUnitIdList(unitId);
	}

	@Override
	public List<OfficeTeacherLeaveNh> getOfficeTeacherLeaveNhByUnitIdPage(String unitId, String state, Pagination page){
		return officeTeacherLeaveNhDao.getOfficeTeacherLeaveNhByUnitIdPage(unitId, state, page);
	}
	
	@Override
	public List<OfficeTeacherLeaveNh> toDoAudit(String userId, Date startTime, Date endTime, Pagination page) {
		List<OfficeTeacherLeaveNh> teacherLeaveNhList = new ArrayList<OfficeTeacherLeaveNh>();
		//包含班主任审核和非班主任审核流程
		List<TaskDescription> todoTaskList = new ArrayList<TaskDescription>();
		List<TaskDescription> todoTaskList1 = new ArrayList<TaskDescription>();
		todoTaskList = taskHandlerService.getTodoTasks(userId, Integer.parseInt(FlowConstant.OFFICE_SUBSYSTEM+FlowConstant.OFFICE_LEAVE_CLASS_CHARGE));
		todoTaskList1 = taskHandlerService.getTodoTasks(userId, Integer.parseInt(FlowConstant.OFFICE_SUBSYSTEM+FlowConstant.OFFICE_LEAVE_NOT_CLASS_CHARGE));
//		processInstanceId
		if(CollectionUtils.isNotEmpty(todoTaskList) || CollectionUtils.isNotEmpty(todoTaskList1)){
			Set<String> flowIdSet= new HashSet<String>();
			Map<String, TaskDescription> taskMap = new HashMap<String, TaskDescription>();
			for (TaskDescription task : todoTaskList) {
				flowIdSet.add(task.getProcessInstanceId());	
				taskMap.put(task.getProcessInstanceId(), task);
			}
			for (TaskDescription task : todoTaskList1) {
				flowIdSet.add(task.getProcessInstanceId());
				taskMap.put(task.getProcessInstanceId(), task);
			}
			teacherLeaveNhList = officeTeacherLeaveNhDao.getOfficeTeacherLeaveNhListByFlowIds(flowIdSet.toArray(new String[0]), startTime, endTime,page);
			Set<String> userIdSet = new HashSet<String>();
			Set<String> leaveTypeIdSet = new HashSet<String>();
			for (OfficeTeacherLeaveNh leaveNh : teacherLeaveNhList) {
				userIdSet.add(leaveNh.getApplyUserId());
				leaveTypeIdSet.add(leaveNh.getLeaveTypeId());
			}
			Map<String, OfficeLeaveType> leaveTypeMap = officeLeaveTypeService.getOfficeLeaveTypeMapByIds(leaveTypeIdSet.toArray(new String[0]));
			Map<String,User> userMap = userService.getUserWithDelMap(userIdSet.toArray(new String[0]));
			for (OfficeTeacherLeaveNh leaveNh : teacherLeaveNhList) {
				TaskDescription taskDescription = taskMap.get(leaveNh.getFlowId());
				leaveNh.setTaskId(taskDescription.getTaskId());
				leaveNh.setTaskName(taskDescription.getTaskName());
				User user = userMap.get(leaveNh.getApplyUserId());
				if(user!=null){
					leaveNh.setUserName(user.getRealname());
				}else{
					leaveNh.setUserName("用户已删除");
				}
				leaveNh.setLeaveTypeName(leaveTypeMap.get(leaveNh.getLeaveTypeId()).getName());
			}
		}
		return teacherLeaveNhList;
	}

	@Override
	public List<OfficeTeacherLeaveNh> HaveDoAudit(String userId, Date startTime, Date endTime, Pagination page) {
		List<OfficeTeacherLeaveNh>  teacherLeaveNhList = officeTeacherLeaveNhDao.HaveDoAudit(userId, startTime, endTime, page);
		Set<String> userIdSet = new HashSet<String>();
		Set<String> leaveTypeIdSet = new HashSet<String>();
		for (OfficeTeacherLeaveNh leaveNh : teacherLeaveNhList) {
			userIdSet.add(leaveNh.getApplyUserId());
			leaveTypeIdSet.add(leaveNh.getLeaveTypeId());
		}
		Map<String, OfficeLeaveType> leaveTypeMap = officeLeaveTypeService.getOfficeLeaveTypeMapByIds(leaveTypeIdSet.toArray(new String[0]));
		Map<String,User> userMap = userService.getUserWithDelMap(userIdSet.toArray(new String[0]));
		for (OfficeTeacherLeaveNh leaveNh : teacherLeaveNhList) {
			User user = userMap.get(leaveNh.getApplyUserId());
			if(user!=null){
				leaveNh.setUserName(user.getRealname());
			}else{
				leaveNh.setUserName("用户已删除");
			}
			leaveNh.setLeaveTypeName(leaveTypeMap.get(leaveNh.getLeaveTypeId()).getName());
		}
		return teacherLeaveNhList;
	}
	
	@Override
	public void saveAuditFlow(boolean pass, TaskHandlerSave taskHandlerSave,
			String leaveId, boolean isSchool) {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("pass", pass);
		taskHandlerSave.setVariables(variables);
		TaskHandlerResult result;
		if(pass){
			taskHandlerSave.getComment().setTextComment("[审核通过]"+taskHandlerSave.getComment().getTextComment());
			result = taskHandlerService.completeTask(taskHandlerSave);
		}else{
			taskHandlerSave.getComment().setTextComment("[审核不通过]"+taskHandlerSave.getComment().getTextComment());
			//审核不通过，直接更新状态
			officeTeacherLeaveNhDao.updateState(leaveId,Constants.LEAVE_APPLY_FLOW_FINSH_NOT_PASS);
			result = taskHandlerService.suspendTask(taskHandlerSave);
		}
		if(result.getStatus()==TaskHandlerResult.STATUS_FINISH){
			if(result.getResult()==TaskHandlerResult.RESULT_PASS){
				officeTeacherLeaveNhDao.updateState(leaveId,Constants.LEAVE_APPLY_FLOW_FINSH_PASS);
				if(isSchool){
					sendSms(leaveId);
				}
			}else if(result.getResult()==TaskHandlerResult.RESULT_NOT_PASS){
				officeTeacherLeaveNhDao.updateState(leaveId,Constants.LEAVE_APPLY_FLOW_FINSH_NOT_PASS);
			}
		}
	}
	
	/**
	 * TODO 发送短信
	 * @param leaveId
	 */
	public void sendSms(String leaveId){
		CurrentSemester currentSemester = semesterService.getCurrentSemester();
		if(currentSemester!=null){
			OfficeTeacherLeaveNh leaveNh = officeTeacherLeaveNhDao.getOfficeTeacherLeaveNhById(leaveId);
			User user = userService.getUser(leaveNh.getApplyUserId());
			//获取该教师的任课班级
			List<String> classIds = timetableSubsystemService.getCourseScheduleClassIds(leaveNh.getUnitId(), currentSemester.getAcadyear(), currentSemester.getSemester(), user.getTeacherid());
			//获取班级的班主任
			List<BasicClass> classList = basicClassService.getClasses(classIds.toArray(new String[0]));
			if(CollectionUtils.isNotEmpty(classList)){
				Set<String> teacherIdSet = new HashSet<String>();
				for(BasicClass basicClass:classList){
					teacherIdSet.add(basicClass.getTeacherid());
				}
				if(teacherIdSet.size()>0){
					List<Teacher> teachers = teacherService.getTeachers(teacherIdSet.toArray(new String[0]));
					String smsContent = getSmsContent(user, leaveNh);
					JSONObject json = new JSONObject();
					json.put("msg", smsContent);
					JSONArray receivers = new JSONArray();
					for(Teacher teacher:teachers){
						if(StringUtils.isNotBlank(teacher.getPersonTel())){
							JSONObject receiverJ = new JSONObject();
							receiverJ.put("phone", teacher.getPersonTel());
							receiverJ.put("unitId", teacher.getUnitid());
							receiverJ.put("username", teacher.getName());
							receiverJ.put("userId", "");
							receivers.add(receiverJ);
						}
					}
					if(receivers.size()>0){
						json.put("receivers", receivers);           
						SmsThread smsThread = new SmsThread(json);
						smsThread.start();
					}
				}
			}
		}
	}
	
	private class SmsThread extends Thread{
    	private JSONObject json;
    	public SmsThread(JSONObject json){
    		this.json = json;
    	}

		@Override
		public void run() {
			try {
				SendResult sr = ZDResponse.post(json);
				System.out.println("--教师请假审核通过发送短信--"+sr.getDescription());
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("--教师请假审核通过发送短信失败--");
			}
 		}
    }
	
	public String getSmsContent(User user, OfficeTeacherLeaveNh leaveNh){
		StringBuffer sbf = new StringBuffer();
		sbf.append(user.getRealname()).append("在")
		.append(DateUtils.date2String(leaveNh.getBeginTime(),"yyyy-MM-dd HH:mm:ss"))
		.append("到").append(DateUtils.date2String(leaveNh.getEndTime(),"yyyy-MM-dd HH:mm:ss"))
		.append("时间段内,请假").append(leaveNh.getDays()).append("天，特此告知");
		return sbf.toString();
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public void setTaskHandlerService(TaskHandlerService taskHandlerService) {
		this.taskHandlerService = taskHandlerService;
	}

	public void setProcessHandlerService(ProcessHandlerService processHandlerService) {
		this.processHandlerService = processHandlerService;
	}

	public void setOfficeLeaveTypeService(
			OfficeLeaveTypeService officeLeaveTypeService) {
		this.officeLeaveTypeService = officeLeaveTypeService;
	}

	public void setSemesterService(SemesterService semesterService) {
		this.semesterService = semesterService;
	}

	public void setBasicClassService(BasicClassService basicClassService) {
		this.basicClassService = basicClassService;
	}

	public void setTimetableSubsystemService(
			TimetableSubsystemService timetableSubsystemService) {
		this.timetableSubsystemService = timetableSubsystemService;
	}

	public void setOfficeTeacherLeaveNhDao(OfficeTeacherLeaveNhDao officeTeacherLeaveNhDao){
		this.officeTeacherLeaveNhDao = officeTeacherLeaveNhDao;
	}

	@Override
	public List<OfficeTeacherLeaveNh> getteacherLeaveListBySearchParams(
			String unitId, Date startTime, Date endTime, String state,Pagination page) {
		//控制  现有请假类型 删除的不包括
		List<OfficeLeaveType> leaveTypeList=officeLeaveTypeService.getOfficeLeaveTypeByUnitIdList(unitId, Constants.TEACHER_LEAVE_TYPE);
		List<String> leaveIds=new ArrayList<String>();
		for(OfficeLeaveType off:leaveTypeList){
			leaveIds.add(off.getId());
		}
		List<OfficeTeacherLeaveNh> teachLeavList=new ArrayList<OfficeTeacherLeaveNh>();
		teachLeavList= officeTeacherLeaveNhDao.getteacherLeavListBySearParams(unitId, startTime, endTime, state, page,leaveIds.toArray(new String[0]));
		Set<String> leaveTypeIds=new HashSet<String>();
		Set<String> userIdSet = new HashSet<String>();
		for(OfficeTeacherLeaveNh off:teachLeavList){
			leaveTypeIds.add(off.getLeaveTypeId());
			userIdSet.add(off.getApplyUserId());
		}
		Map<String, OfficeLeaveType> leaveTypeMap=officeLeaveTypeService.getOfficeLeaveTypeMapByIds(leaveTypeIds.toArray(new String[0]));
		Map<String,User> userMap = userService.getUserWithDelMap(userIdSet.toArray(new String[0]));
		for(OfficeTeacherLeaveNh off:teachLeavList){
			User user = userMap.get(off.getApplyUserId());
			if(user!=null){
				off.setUserName(user.getRealname());
			}else{
				off.setUserName("用户已删除");
			}
			off.setLeaveTypeName(leaveTypeMap.get(off.getLeaveTypeId()).getName());
		}
		return teachLeavList;
	}

	@Override
	public List<OfficeTeacherLeaveNh> getSummarylist(String unitId,
			Date startTime, Date endTime) {
		return officeTeacherLeaveNhDao.getSummaryList(unitId, startTime, endTime);
	}

	@Override
	public String[] getUserIdsByUnitId(String unitId, Date startTime,
			Date endTime,String[] leaveIds) {
		return officeTeacherLeaveNhDao.getUserIds(unitId, startTime, endTime,leaveIds);
	}

	@Override
	public Map<String, String> getSumMap(String unitId, Date startTime,
			Date endTime) {
		return officeTeacherLeaveNhDao.getSumMap(unitId, startTime, endTime);
	}
	
	@Override
	public boolean isExistConflict(String id, String applyUserId, Date beginTime,
			Date endTime) {
		return officeTeacherLeaveNhDao.isExistConflict(id, applyUserId,beginTime,endTime);
	}

	public void setOfficeFlowService(OfficeFlowService officeFlowService) {
		this.officeFlowService = officeFlowService;
	}

	public void setOfficeFlowStepInfoService(
			OfficeFlowStepInfoService officeFlowStepInfoService) {
		this.officeFlowStepInfoService = officeFlowStepInfoService;
	}
}