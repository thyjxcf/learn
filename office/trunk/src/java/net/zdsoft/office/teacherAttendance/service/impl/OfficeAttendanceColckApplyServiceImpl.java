package net.zdsoft.office.teacherAttendance.service.impl;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.component.flowManage.constant.FlowConstant;
import net.zdsoft.jbpm.core.entity.TaskDescription;
import net.zdsoft.jbpm.core.entity.TaskHandlerResult;
import net.zdsoft.jbpm.core.entity.TaskHandlerSave;
import net.zdsoft.jbpm.core.service.ProcessHandlerService;
import net.zdsoft.jbpm.core.service.TaskHandlerService;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keel.util.UUIDUtils;
import net.zdsoft.office.convertflow.constant.ConvertFlowConstants;
import net.zdsoft.office.convertflow.service.OfficeConvertFlowService;
import net.zdsoft.office.convertflow.service.OfficeFlowSendMsgService;
import net.zdsoft.office.officeFlow.dto.HisTask;
import net.zdsoft.office.officeFlow.service.OfficeFlowService;
import net.zdsoft.office.officeFlow.service.OfficeFlowStepInfoService;
import net.zdsoft.office.teacherAttendance.constant.AttendanceConstants;
import net.zdsoft.office.teacherAttendance.constant.WeekdayEnum;
import net.zdsoft.office.teacherAttendance.dao.OfficeAttendanceColckApplyDao;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceColckApply;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceGroup;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceGroupUser;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceInfo;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceSet;
import net.zdsoft.office.teacherAttendance.service.OfficeAttendanceColckApplyService;
import net.zdsoft.office.teacherAttendance.service.OfficeAttendanceColckLogService;
import net.zdsoft.office.teacherAttendance.service.OfficeAttendanceGroupService;
import net.zdsoft.office.teacherAttendance.service.OfficeAttendanceGroupUserService;
import net.zdsoft.office.teacherAttendance.service.OfficeAttendanceInfoService;
import net.zdsoft.office.teacherAttendance.service.OfficeAttendanceSetService;
import net.zdsoft.office.util.Constants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
/**
 * 考勤补卡申请表
 * @author 
 * 
 */
public class OfficeAttendanceColckApplyServiceImpl implements OfficeAttendanceColckApplyService{
	private OfficeAttendanceColckApplyDao officeAttendanceColckApplyDao;
	private OfficeAttendanceInfoService officeAttendanceInfoService;
	private OfficeAttendanceGroupService officeAttendanceGroupService;
	private OfficeAttendanceSetService officeAttendanceSetService;
	private OfficeAttendanceGroupUserService officeAttendanceGroupUserService;
	private OfficeAttendanceColckLogService officeAttendanceColckLogService;
	
	private TaskHandlerService taskHandlerService;
	private UserService userService;
	private DeptService deptService;
	private OfficeConvertFlowService officeConvertFlowService;
	private OfficeFlowSendMsgService officeFlowSendMsgService;
	private ProcessHandlerService processHandlerService;
	private OfficeFlowService officeFlowService;
	private OfficeFlowStepInfoService officeFlowStepInfoService;
	
	@Override
	public OfficeAttendanceColckApply save(OfficeAttendanceColckApply officeAttendanceColckApply){
		return officeAttendanceColckApplyDao.save(officeAttendanceColckApply);
	}

	@Override
	public Integer delete(String[] ids){
		//暂时只有一个id
		OfficeAttendanceColckApply apply = officeAttendanceColckApplyDao.getOfficeAttendanceColckApplyById((ids!=null && ids.length>0)?ids[0]:"");
		if(apply.getApplyStatus() > Constants.LEAVE_APPLY_SAVE){
			processHandlerService.deleteProcessInstance(apply.getFlowId(), true);
		}
		return officeAttendanceColckApplyDao.delete(ids);
	}

	@Override
	public Integer update(OfficeAttendanceColckApply officeAttendanceColckApply){
		return officeAttendanceColckApplyDao.update(officeAttendanceColckApply);
	}
	
	public OfficeAttendanceColckApply getObjByDateType(String userId, String attenceDate, String type){
		OfficeAttendanceColckApply ent = officeAttendanceColckApplyDao.getObjByDateType(userId, attenceDate, type);
		//给客户端用， 状态有所改变
		if(ent!=null){
			if(Constants.APPLY_STATE_PASS == ent.getApplyStatus()){
				ent.setApplyStatus(2);
				ent.setApplyStatusName("审批通过");
			}else if(Constants.APPLY_STATE_NOPASS == ent.getApplyStatus()){
				ent.setApplyStatus(3);
				ent.setApplyStatusName("审批未通过");
			}else{
				ent.setApplyStatus(1);//待审批
				ent.setApplyStatusName("待审批");
			}
		}
		return ent;
	}

	//获取userId对应的考勤制度
	@Override
	public OfficeAttendanceSet getSet(String userId){
		OfficeAttendanceGroupUser groupUser=officeAttendanceGroupUserService.getItemByUserId(userId);
		if(groupUser==null) groupUser=new  OfficeAttendanceGroupUser();
		OfficeAttendanceGroup group=officeAttendanceGroupService.getOfficeAttendanceGroupById(groupUser.getGroupId());
		if(group==null) group=new OfficeAttendanceGroup();
		OfficeAttendanceSet set=officeAttendanceSetService.getOfficeAttendanceSetById(group.getAttSetId());
		if(set==null) set=new OfficeAttendanceSet();
		return set;
	}
	public String getWeekDay(Date d,Calendar c){
		String[] weekDays={"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
		c.setTime(d);
		int w=c.get(Calendar.DAY_OF_WEEK)-1;
		if(w<0)	w=0;
		return weekDays[w];
	}
	@Override
	public OfficeAttendanceColckApply getOfficeAttendanceColckApplyById(String id){
		OfficeAttendanceColckApply clockApply=officeAttendanceColckApplyDao.getOfficeAttendanceColckApplyById(id);
		if(clockApply!=null){
			User user=userService.getUser(clockApply.getApplyUserId());
			clockApply.setUserName(user!=null?user.getRealname():"");
			
			clockApply=getApplyTypeWeek(clockApply);
			
		}
		if(StringUtils.isNotEmpty(clockApply.getFlowId())){
			List<HisTask> hisTask = officeFlowService.getHisTask(clockApply.getFlowId());
			clockApply.setHisTaskList(hisTask);
		}
		return clockApply;
	}
	
	public List<OfficeAttendanceColckApply> getListByDate(String userId, String startDate, String endDate){
		return officeAttendanceColckApplyDao.getListByDate(userId, startDate, endDate);
	}
	//得到补卡班次
	public OfficeAttendanceColckApply getApplyTypeWeek(OfficeAttendanceColckApply officeAttendanceColckApply){
		OfficeAttendanceSet set=getSet(officeAttendanceColckApply.getApplyUserId());
		Calendar c=Calendar.getInstance();
		c.setTime(officeAttendanceColckApply.getAttenceDate());
		String weekDay=WeekdayEnum.getName(c.get(Calendar.DAY_OF_WEEK));
		if(officeAttendanceColckApply.getType().equals(AttendanceConstants.ATTENDANCE_TYPE_AM)){
			officeAttendanceColckApply.setTypeWeekTime(weekDay+" "+set.getStartTime());
		}else{
			officeAttendanceColckApply.setTypeWeekTime(weekDay+" "+set.getEndTime());
		}
		if(officeAttendanceColckApply.getTypeWeekTime()==null) officeAttendanceColckApply.setTypeWeekTime("");
		
		return officeAttendanceColckApply;
	}
	@Override
	public void startFlow(OfficeAttendanceColckApply officeAttendanceColckApply, String userId){
		officeAttendanceColckApply.setApplyStatus(Constants.APPLY_STATE_NEED_AUDIT);
		if(StringUtils.isBlank(officeAttendanceColckApply.getId())){
			officeAttendanceColckApply.setId(UUIDUtils.newId());
		}
		officeAttendanceColckApply=getApplyTypeWeek(officeAttendanceColckApply);
		
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("pass",true);
		String flowId = processHandlerService.startProcessInstance(FlowConstant.OFFICE_SUBSYSTEM,
				officeAttendanceColckApply.getFlowId(), Integer.parseInt(FlowConstant.OFFICE_SUBSYSTEM+FlowConstant.OFFICE_ATTENDANCE), officeAttendanceColckApply.getId(), userId, variables);
		officeFlowStepInfoService.batchUpdateByFlowId(officeAttendanceColckApply.getFlowId(), flowId);
		officeAttendanceColckApply.setFlowId(flowId);
		
		OfficeAttendanceColckApply everClock=officeAttendanceColckApplyDao.getObjByDateType
				(userId, DateUtils.date2String(officeAttendanceColckApply.getAttenceDate()), officeAttendanceColckApply.getType());
		if(everClock!=null){
			//再次申请补卡  删去原有的补卡信息   //TODO 
			this.delete(new String[]{everClock.getId()});
		}
		
		officeAttendanceColckApply=save(officeAttendanceColckApply);
		//update(officeAttendanceColckApply);
		
		officeConvertFlowService.startFlow(officeAttendanceColckApply, ConvertFlowConstants.OFFICE_TEACHER_ATTENDANCE);
		officeFlowSendMsgService.startFlowSendMsg(officeAttendanceColckApply, ConvertFlowConstants.OFFICE_TEACHER_ATTENDANCE);
		
	}
	@Override
	public List<OfficeAttendanceColckApply> getApplyList(String userId, String unitId,int appplyStatus,Pagination page){
		List<OfficeAttendanceColckApply>  attendanceColckApplyList=officeAttendanceColckApplyDao.getApplyList(userId,unitId,appplyStatus,page);
		return attendanceColckApplyList;
	}
	@Override
	public void passFlow(boolean pass, TaskHandlerSave taskHandlerSave,String id){
//		String auditUserId = taskHandlerSave.getCurrentUserId();
		OfficeAttendanceColckApply clockApply = officeAttendanceColckApplyDao.getOfficeAttendanceColckApplyById(id);
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("pass", pass);
//		variables.put("days", clockApply.getDays());
		taskHandlerSave.setVariables(variables);
		TaskHandlerResult result;
		String textComment=taskHandlerSave.getComment().getTextComment();
		if(pass){
			taskHandlerSave.getComment().setTextComment("[审核通过]"+textComment);
			result = taskHandlerService.completeTask(taskHandlerSave);
		}else{
			taskHandlerSave.getComment().setTextComment("[审核不通过]"+textComment);
			boolean isNotFinish = taskHandlerService.isExclusiveGatewayForNext(clockApply.getUnitId(), taskHandlerSave.getSubsystemId(), taskHandlerSave.getCurrentTask().getTaskDefinitionKey(), clockApply.getFlowId());
			if(isNotFinish){
				result = taskHandlerService.completeTask(taskHandlerSave);
			}else{
				result = taskHandlerService.suspendTask(taskHandlerSave);
			}
			
		}
		if(result.getStatus()==TaskHandlerResult.STATUS_FINISH){
			if(result.getResult()==TaskHandlerResult.RESULT_PASS){
				clockApply.setApplyStatus(Constants.APPLY_STATE_PASS);
				//补卡申请 审核通过 添加考勤信息
				saveInfo(clockApply);
			}else if(result.getResult()==TaskHandlerResult.RESULT_NOT_PASS){
				clockApply.setApplyStatus(Constants.APPLY_STATE_NOPASS);
			}
			update(clockApply);
		}
		//获取补卡班次 发送信息
		clockApply=getApplyTypeWeek(clockApply);
		clockApply.setAuditTextComment(textComment);
		
		officeConvertFlowService.completeTask(id, clockApply.getFlowId(), taskHandlerSave.getCurrentUserId(), taskHandlerSave.getCurrentTask().getTaskId(), result, pass);
		officeFlowSendMsgService.completeTaskSendMsg(taskHandlerSave.getCurrentUserId(), pass, clockApply, ConvertFlowConstants.OFFICE_TEACHER_ATTENDANCE, result);
		
	}
	public void saveInfo(OfficeAttendanceColckApply clockApply){
		//获取考勤制度
		OfficeAttendanceSet set=getSet(clockApply.getApplyUserId());
		
		String setTime="";
		String setTimeTo="";
		if(clockApply.getType().equals(AttendanceConstants.ATTENDANCE_TYPE_AM)){
			setTime=set.getStartTime();
			setTimeTo=set.getStartTimeStr();
		}else{
			setTime=set.getEndTime();
			setTimeTo=set.getEndTimeStr();
		}
		setTime=StringUtils.isNotBlank(setTime)?setTime:"";
		Date setTimeDate=DateUtils.string2Date(DateUtils.date2String(clockApply.getAttenceDate(),"yyyy-MM-dd")+" "+setTimeTo,"yyyy-MM-dd HH:mm:ss");
		
		OfficeAttendanceInfo info=new OfficeAttendanceInfo();
		info.setUnitId(clockApply.getUnitId());
		info.setUserId(clockApply.getApplyUserId());
		info.setAttenceDate(clockApply.getAttenceDate());
		info.setIsHoliday(false);
		info.setType(clockApply.getType());
		info.setLogType(AttendanceConstants.ATTENDANCE_LOG_TYPE_1);
		info.setClockState(AttendanceConstants.ATTENDANCE_CLOCK_STATE_DEFAULT);
		info.setClockTime(setTimeDate);
		info.setRemark(null);
		info.setAttendanceTime(setTime);
		info.setCreationTime(new Date());
		officeAttendanceInfoService.save(info);
		//新增流水表
//		officeAttendanceColckLogService.saveLogByInfo(info);
	}
	//得到userId 对应 考勤制度set 的map
	@Override
	public Map<String,OfficeAttendanceSet> getSetMapByUserIds(String[] userIds,String unitId){
		Map<String,OfficeAttendanceSet> userIdSetMap=null;
		if(userIds!=null && userIds.length>0){
			//得到userId--groupId map
			Map<String,String> groupUserMap=officeAttendanceGroupUserService.getGroupUser(userIds);
			if(MapUtils.isEmpty(groupUserMap)) groupUserMap=new HashMap<String, String>();
			
			//得到groupId--attSetId map
			Map<String,String> groupMap=new HashMap<String, String>();
			List<OfficeAttendanceGroup> groupList=officeAttendanceGroupService.listOfficeAttendanceGroupByUnitId(unitId);
			if(CollectionUtils.isNotEmpty(groupList)){
				for(OfficeAttendanceGroup group:groupList){
					groupMap.put(group.getId(), group.getAttSetId());
				}
			}
			//得到attSetId--OfficeAttendanceSet map
			Map<String,OfficeAttendanceSet> setMap=officeAttendanceSetService.getOfficeAttendanceSetMapByUnitId(unitId);
			if(MapUtils.isEmpty(setMap)) setMap=new HashMap<String, OfficeAttendanceSet>();
			
			userIdSetMap=new HashMap<String,OfficeAttendanceSet>();
			//最终得到userId--OfficeAttendanceSet map
			for(String userId:userIds){
				String groupId=groupUserMap.get(userId);
				String attSetId=groupMap.get(groupId);
				OfficeAttendanceSet set=setMap.get(attSetId);
				if(set==null) set=new OfficeAttendanceSet();
				
				userIdSetMap.put(userId, set);
			}
		}
		 
	  return userIdSetMap;
	}
	
	@Override
	public List<OfficeAttendanceColckApply> HaveDoAudit(String userId,String unitId, Pagination page){
		List<OfficeAttendanceColckApply> clockApplyList=officeAttendanceColckApplyDao.HaveDoAudit(userId, page);
		if(CollectionUtils.isNotEmpty(clockApplyList)){
			Set<String> userIds=new HashSet<String>();
			for(OfficeAttendanceColckApply clock:clockApplyList){
				userIds.add(clock.getApplyUserId());
			}
			Map<String,Dept> deptMap=deptService.getDeptMap(unitId);
			Map<String,User> userMap=userService.getUsersMap(userIds.toArray(new String[0]));
			if(MapUtils.isEmpty(deptMap)) deptMap=new HashMap<String,Dept>();
			if(MapUtils.isEmpty(userMap)) userMap=new HashMap<String,User>();
			
			//得到userId 对应 考勤制度set 的map
			Map<String,OfficeAttendanceSet> userSetMap=getSetMapByUserIds(userIds.toArray(new String[0]),unitId);
			boolean userSetMapFlag=MapUtils.isNotEmpty(userSetMap);
			
			Calendar c=Calendar.getInstance();
			for(OfficeAttendanceColckApply clock:clockApplyList){
				User user=userMap.get(clock.getApplyUserId());
				if(user!=null){
					clock.setUserName(user.getRealname());
					Dept dept=deptMap.get(user.getDeptid());
					if(dept!=null) clock.setDeptName(dept.getDeptname());
				}
				c.setTime(clock.getAttenceDate());
				String weekDay=WeekdayEnum.getName(c.get(Calendar.DAY_OF_WEEK));
				if(userSetMapFlag){
					OfficeAttendanceSet set=userSetMap.get(clock.getApplyUserId());
					if(set!=null){
						if(clock.getType().equals(AttendanceConstants.ATTENDANCE_TYPE_AM)){//上班时间
							clock.setTypeWeekTime(weekDay+" "+set.getStartTime());
						}else{
							clock.setTypeWeekTime(weekDay+" "+set.getEndTime());
						}
					}
				}
				
				
			}
		}
		
		return clockApplyList;
	}
	@Override
	public List<OfficeAttendanceColckApply> toDoAudit(String userId,String unitId, Pagination page) {
		List<OfficeAttendanceColckApply> colckApplyList = new ArrayList<OfficeAttendanceColckApply>();
		List<TaskDescription> todoTaskList = new ArrayList<TaskDescription>();
		todoTaskList = taskHandlerService.getTodoTasks(userId, Integer.parseInt(FlowConstant.OFFICE_SUBSYSTEM+FlowConstant.OFFICE_ATTENDANCE), page);
//		processInstanceId
		if(CollectionUtils.isNotEmpty(todoTaskList)){
			Set<String> flowIdSet= new HashSet<String>();
			for (TaskDescription task : todoTaskList) {
				flowIdSet.add(task.getProcessInstanceId());	
			}
			Map<String,OfficeAttendanceColckApply> colckApplyMap = officeAttendanceColckApplyDao.getAttendanceColckApplyMapByFlowIds(flowIdSet.toArray(new String[0]));
			Set<String> userIdSet = new HashSet<String>();
			for (String colckApplyId : colckApplyMap.keySet()) {
				OfficeAttendanceColckApply attendanceColckApply = colckApplyMap.get(colckApplyId);
				if(attendanceColckApply!=null){
					userIdSet.add(attendanceColckApply.getApplyUserId());
				}
			}
			Map<String,Dept> deptMap=deptService.getDeptMap(unitId);
			Map<String,User> userMap = new HashMap<String, User>();
			userMap = userService.getUserWithDelMap(userIdSet.toArray(new String[0]));
			
			//得到userId 对应 考勤制度set 的map
			Map<String,OfficeAttendanceSet> userSetMap=getSetMapByUserIds(userIdSet.toArray(new String[0]),unitId);
			boolean userSetMapFlag=MapUtils.isNotEmpty(userSetMap);
			Calendar c=Calendar.getInstance();
			
			for (TaskDescription task : todoTaskList) {
				OfficeAttendanceColckApply attendanceColckApply = colckApplyMap.get(task.getProcessInstanceId());
				if(attendanceColckApply != null){
					attendanceColckApply.setTaskId(task.getTaskId());
					attendanceColckApply.setTaskName(task.getTaskName());
					User user = userMap.get(attendanceColckApply.getApplyUserId());
					if(user!=null){
						attendanceColckApply.setUserName(user.getRealname());
						if(MapUtils.isNotEmpty(deptMap)){
							Dept dept=deptMap.get(user.getDeptid());
							attendanceColckApply.setDeptName(dept!=null?dept.getDeptname():"");
						}
					}else{
						attendanceColckApply.setUserName("用户已删除");
					}
					
					c.setTime(attendanceColckApply.getAttenceDate());
					String weekDay=WeekdayEnum.getName(c.get(Calendar.DAY_OF_WEEK));
					if(userSetMapFlag){
						OfficeAttendanceSet set=userSetMap.get(attendanceColckApply.getApplyUserId());
						if(set!=null){
							if(attendanceColckApply.getType().equals(AttendanceConstants.ATTENDANCE_TYPE_AM)){//上班时间
								attendanceColckApply.setTypeWeekTime(weekDay+" "+set.getStartTime());
							}else{
								attendanceColckApply.setTypeWeekTime(weekDay+" "+set.getEndTime());
							}
						}
					}
					colckApplyList.add(attendanceColckApply);
				}
			}
		}
		return colckApplyList;
	}
	
	
	public Map<String, OfficeAttendanceColckApply> getMapByDate(String userId, Date startDate, Date endDate){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		List<OfficeAttendanceColckApply> list = officeAttendanceColckApplyDao.getListByDate(userId, df.format(startDate), df.format(endDate));
		
		Map<String, OfficeAttendanceColckApply> map = new HashMap<String, OfficeAttendanceColckApply>();
		for(OfficeAttendanceColckApply ent : list){
			//给客户端用， 状态有所改变
			if(Constants.APPLY_STATE_PASS == ent.getApplyStatus()){
				ent.setApplyStatus(2);
				ent.setApplyStatusName("审批通过");
			}else if(Constants.APPLY_STATE_NOPASS == ent.getApplyStatus()){
				ent.setApplyStatus(3);
				ent.setApplyStatusName("审批未通过");
			}else{
				ent.setApplyStatus(1);//待审批
				ent.setApplyStatusName("待审批");
			}
			
			String key = df.format(ent.getAttenceDate())+"_"+ent.getType();
			map.put(key, ent);
		}
		
		return map;
	}
	
	@Override
	public Map<String,OfficeAttendanceColckApply> getOfficeAttendanceClockByUnitIdMap(String unitId,String[] userIds,String startTimeStr,String endTimeStr){
		return officeAttendanceColckApplyDao.getOfficeAttendanceClockByUnitIdMap(unitId, userIds,startTimeStr,endTimeStr);
	}
	public void setOfficeAttendanceColckApplyDao(
			OfficeAttendanceColckApplyDao officeAttendanceColckApplyDao) {
		this.officeAttendanceColckApplyDao = officeAttendanceColckApplyDao;
	}

	public void setTaskHandlerService(TaskHandlerService taskHandlerService) {
		this.taskHandlerService = taskHandlerService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public void setOfficeConvertFlowService(
			OfficeConvertFlowService officeConvertFlowService) {
		this.officeConvertFlowService = officeConvertFlowService;
	}

	public void setOfficeFlowSendMsgService(
			OfficeFlowSendMsgService officeFlowSendMsgService) {
		this.officeFlowSendMsgService = officeFlowSendMsgService;
	}

	public void setOfficeAttendanceInfoService(
			OfficeAttendanceInfoService officeAttendanceInfoService) {
		this.officeAttendanceInfoService = officeAttendanceInfoService;
	}

	public void setProcessHandlerService(ProcessHandlerService processHandlerService) {
		this.processHandlerService = processHandlerService;
	}

	public void setOfficeAttendanceGroupService(
			OfficeAttendanceGroupService officeAttendanceGroupService) {
		this.officeAttendanceGroupService = officeAttendanceGroupService;
	}

	public void setOfficeAttendanceSetService(
			OfficeAttendanceSetService officeAttendanceSetService) {
		this.officeAttendanceSetService = officeAttendanceSetService;
	}

	public void setOfficeAttendanceGroupUserService(
			OfficeAttendanceGroupUserService officeAttendanceGroupUserService) {
		this.officeAttendanceGroupUserService = officeAttendanceGroupUserService;
	}

	public void setOfficeAttendanceColckLogService(
			OfficeAttendanceColckLogService officeAttendanceColckLogService) {
		this.officeAttendanceColckLogService = officeAttendanceColckLogService;
	}

	public void setOfficeFlowService(OfficeFlowService officeFlowService) {
		this.officeFlowService = officeFlowService;
	}

	public void setOfficeFlowStepInfoService(
			OfficeFlowStepInfoService officeFlowStepInfoService) {
		this.officeFlowStepInfoService = officeFlowStepInfoService;
	}
	
	
}