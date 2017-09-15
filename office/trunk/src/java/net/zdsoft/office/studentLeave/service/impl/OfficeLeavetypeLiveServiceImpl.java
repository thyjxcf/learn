package net.zdsoft.office.studentLeave.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.entity.Student;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.BasicClassService;
import net.zdsoft.eis.base.common.service.StudentService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.component.flowManage.constant.FlowConstant;
import net.zdsoft.jbpm.core.entity.TaskDescription;
import net.zdsoft.jbpm.core.service.ProcessHandlerService;
import net.zdsoft.jbpm.core.service.TaskHandlerService;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keel.util.UUIDUtils;
import net.zdsoft.office.officeFlow.dto.HisTask;
import net.zdsoft.office.officeFlow.service.OfficeFlowService;
import net.zdsoft.office.officeFlow.service.OfficeFlowStepInfoService;
import net.zdsoft.office.studentLeave.constant.OfficeStuLeaveConstants;
import net.zdsoft.office.studentLeave.dao.OfficeLeavetypeLiveDao;
import net.zdsoft.office.studentLeave.dao.OfficeLeavetypeLongDao;
import net.zdsoft.office.studentLeave.entity.OfficeHwstudentLeave;
import net.zdsoft.office.studentLeave.entity.OfficeLeavetypeLive;
import net.zdsoft.office.studentLeave.entity.OfficeLeavetypeLong;
import net.zdsoft.office.studentLeave.service.OfficeHwstudentLeaveService;
import net.zdsoft.office.studentLeave.service.OfficeLeavetypeLiveService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
/**
 * office_leavetype_live
 * @author 
 * 
 */
public class OfficeLeavetypeLiveServiceImpl implements OfficeLeavetypeLiveService{
	private OfficeLeavetypeLiveDao officeLeavetypeLiveDao;
	private OfficeHwstudentLeaveService officeHwstudentLeaveService;
	private BasicClassService basicClassService;
	private StudentService studentService;
	private ProcessHandlerService processHandlerService;
	private OfficeFlowStepInfoService officeFlowStepInfoService;
	private OfficeFlowService officeFlowService;
	private TaskHandlerService taskHandlerService;
	private UserService userService;
	private OfficeLeavetypeLongDao officeLeavetypeLongDao;
	
	public void setOfficeHwstudentLeaveService(
			OfficeHwstudentLeaveService officeHwstudentLeaveService) {
		this.officeHwstudentLeaveService = officeHwstudentLeaveService;
	}
	public void setBasicClassService(BasicClassService basicClassService) {
		this.basicClassService = basicClassService;
	}
	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}
	public void setProcessHandlerService(ProcessHandlerService processHandlerService) {
		this.processHandlerService = processHandlerService;
	}
	public void setOfficeFlowStepInfoService(
			OfficeFlowStepInfoService officeFlowStepInfoService) {
		this.officeFlowStepInfoService = officeFlowStepInfoService;
	}
	public void setOfficeFlowService(OfficeFlowService officeFlowService) {
		this.officeFlowService = officeFlowService;
	}
	@Override
	public void deleteByLeaveId(String leaveId) {
		officeLeavetypeLiveDao.deleteByLeaveId(leaveId);
	}
	@Override
	public OfficeLeavetypeLive save(OfficeLeavetypeLive officeLeavetypeLive){
		return officeLeavetypeLiveDao.save(officeLeavetypeLive);
	}

	@Override
	public Integer delete(String[] ids){
		return officeLeavetypeLiveDao.delete(ids);
	}

	@Override
	public Integer update(OfficeLeavetypeLive officeLeavetypeLive){
		return officeLeavetypeLiveDao.update(officeLeavetypeLive);
	}

	@Override
	public OfficeLeavetypeLive getOfficeLeavetypeLiveById(String id){
		return officeLeavetypeLiveDao.getOfficeLeavetypeLiveById(id);
	}

	@Override
	public Map<String, OfficeLeavetypeLive> getOfficeLeavetypeLiveMapByIds(String[] ids){
		return officeLeavetypeLiveDao.getOfficeLeavetypeLiveMapByIds(ids);
	}
	@Override
	public Map<String, OfficeLeavetypeLive> getMapByLeaveIds(String[] leaveIds) {
		return officeLeavetypeLiveDao.getMapByLeaveIds(leaveIds);
	}
	@Override
	public List<OfficeLeavetypeLive> getOfficeLeavetypeLiveList(){
		return officeLeavetypeLiveDao.getOfficeLeavetypeLiveList();
	}

	@Override
	public List<OfficeLeavetypeLive> getOfficeLeavetypeLivePage(Pagination page){
		return officeLeavetypeLiveDao.getOfficeLeavetypeLivePage(page);
	}

	public void setOfficeLeavetypeLiveDao(OfficeLeavetypeLiveDao officeLeavetypeLiveDao){
		this.officeLeavetypeLiveDao = officeLeavetypeLiveDao;
	}
	@Override
	public OfficeLeavetypeLive findByLeaveId(String leaveId) {
		OfficeHwstudentLeave stuLeave = officeHwstudentLeaveService.getOfficeHwstudentLeaveById(leaveId);
		OfficeLeavetypeLive live = officeLeavetypeLiveDao.findByLeaveId(leaveId);
		if(live == null || stuLeave == null){
			return null;
		}
		live.setClassId(stuLeave.getClassId());
		live.setStudentId(stuLeave.getStudentId());
		live.setUnitId(stuLeave.getUnitId());
		Student stu = studentService.getStudent(stuLeave.getStudentId());
		live.setStudentName(stu.getStuname());
		BasicClass basicClass = basicClassService.getClass(stuLeave.getClassId());
		live.setClassName(basicClass.getClassnamedynamic());
		live.setFlowId(stuLeave.getFlowId());
		live.setApplyUserId(stuLeave.getApplyUserId());
		if(StringUtils.isNotBlank(stuLeave.getApplyUserId())){
			User user = userService.getUser(stuLeave.getApplyUserId());
			live.setApplyUserName(user.getRealname());
		}
		live.setCreationTime(stuLeave.getCreationTime());
		if(stuLeave != null){
			if(StringUtils.isNotEmpty(stuLeave.getFlowId())){
				List<HisTask> hisTask = officeFlowService.getHisTask(stuLeave.getFlowId());
				live.setHisTaskList(hisTask);
			}
		}
		return live;
	}
	@Override
	public void insertLive(OfficeLeavetypeLive leaveLive) {
		OfficeHwstudentLeave stuLeave = new OfficeHwstudentLeave();
		stuLeave.setId(leaveLive.getLeaveId());
		stuLeave.setApplyDate(new Date());
		stuLeave.setApplyUserId(leaveLive.getApplyUserId());
		stuLeave.setClassId(leaveLive.getClassId());
		stuLeave.setStudentId(leaveLive.getStudentId());
		stuLeave.setState(leaveLive.getState());
		stuLeave.setFlowId(leaveLive.getFlowId());
//		stuLeave.setCreationTime(new Date());
		stuLeave.setCreationTime(leaveLive.getCreationTime());
		stuLeave.setUnitId(leaveLive.getUnitId());
		stuLeave.setType("3");
		stuLeave.setIsDeleted(false);
//		stuLeave.setModifyTime(new Date());
		stuLeave = officeHwstudentLeaveService.save(stuLeave);
		leaveLive.setCreationTime(new Date());
		leaveLive.setLeaveId(stuLeave.getId());
		officeLeavetypeLiveDao.save(leaveLive);
	}
	@Override
	public void updateLive(OfficeLeavetypeLive leaveLive) {
		OfficeHwstudentLeave stuLeave = new OfficeHwstudentLeave();
		stuLeave.setId(leaveLive.getLeaveId());
		stuLeave.setApplyDate(new Date());
		stuLeave.setApplyUserId(leaveLive.getApplyUserId());
		stuLeave.setClassId(leaveLive.getClassId());
		stuLeave.setStudentId(leaveLive.getStudentId());
		stuLeave.setState(leaveLive.getState());
		stuLeave.setFlowId(leaveLive.getFlowId());
		stuLeave.setUnitId(leaveLive.getUnitId());
		stuLeave.setType("3");
		stuLeave.setIsDeleted(false);
		stuLeave.setCreationTime(leaveLive.getCreationTime());
//		stuLeave.setModifyTime(new Date());
		officeHwstudentLeaveService.update(stuLeave);
		leaveLive.setCreationTime(new Date());
		leaveLive.setLeaveId(stuLeave.getId());
		officeLeavetypeLiveDao.delete(new String[]{leaveLive.getId()});
		leaveLive.setId("");
		officeLeavetypeLiveDao.save(leaveLive);
	}
	@Override
	public void startFlow(OfficeLeavetypeLive leaveLive, String userId) {
		if(StringUtils.isBlank(leaveLive.getLeaveId())){
			leaveLive.setLeaveId(UUIDUtils.newId());
			leaveLive.setId(UUIDUtils.newId());
		}
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("pass",true);
		List<User> user = userService.getUsersByOwner(leaveLive.getStudentId());
		String flowId = processHandlerService.startProcessInstance(
				FlowConstant.OFFICE_SUBSYSTEM, leaveLive.getFlowId(), 
				Integer.parseInt(FlowConstant.OFFICE_SUBSYSTEM+FlowConstant.OFFICE_STUDENT_LEAVE_LIVE), 
				leaveLive.getLeaveId(), user.get(0).getId(), variables);
		officeFlowStepInfoService.batchUpdateByFlowId(leaveLive.getFlowId(), flowId);
		leaveLive.setFlowId(flowId);
		OfficeHwstudentLeave office=officeHwstudentLeaveService.getOfficeHwstudentLeaveById(leaveLive.getLeaveId());
		leaveLive.setCreationTime(new Date());
		if(office!=null){
			this.updateLive(leaveLive);
		}else{
			this.insertLive(leaveLive);
		}
	}
	@Override
	public List<OfficeLeavetypeLive> getLeavelist(String unitId, String stuId,
			Pagination page) {
		List<OfficeHwstudentLeave> leaves = officeHwstudentLeaveService.getOfficeHwstudentLeaveByUnitIdPage(unitId,stuId, "3", page);
		Set<String> leaveIds = new HashSet<String>();
		Set<String> stuIds = new HashSet<String>();
		Set<String> classIds = new HashSet<String>();
		Map<String,OfficeHwstudentLeave> leaveMap = new HashMap<String,OfficeHwstudentLeave>();
		for(OfficeHwstudentLeave leave : leaves){
			leaveIds.add(leave.getId());
			stuIds.add(leave.getStudentId());
			classIds.add(leave.getClassId());
			leaveMap.put(leave.getId(), leave);
		}
		Map<String,Student> studentMap = studentService.getStudentMap(stuIds.toArray(new String[0]));
		Map<String,BasicClass> clsMap = basicClassService.getClassMap(classIds.toArray(new String[0]));
		List<OfficeLeavetypeLive> lives = officeLeavetypeLiveDao.findByLeaveIds(leaveIds.toArray(new String[0]));
		for(OfficeLeavetypeLive live : lives){
			leaveInfoToLive(live, leaveMap.get(live.getLeaveId()));
			Student stu =studentMap.get(live.getStudentId());
			BasicClass basicClass = clsMap.get(live.getClassId());
			live.setClassName(basicClass.getClassnamedynamic());
			live.setStudentName(stu.getStuname());
		}
		return lives;
	}
	@Override
	public boolean isExistTime(String id, Date startTime, Date endTime,
			boolean hasLong, String longId, String stuId) {
		List<OfficeLeavetypeLive> lives = officeLeavetypeLiveDao.isExistTime(id,startTime,endTime);
		boolean isExist = false;
		Set<String> leaveIds = new HashSet<String>();
		if(CollectionUtils.isNotEmpty(lives)){
			for (OfficeLeavetypeLive general : lives) {
				leaveIds.add(general.getLeaveId());
			}
			List<OfficeHwstudentLeave> stuLeaves = 
					officeHwstudentLeaveService.getLeavesByIdsAndState(stuId,
							leaveIds.toArray(new String[0]), new String[]{OfficeStuLeaveConstants.OFFCIE_STULEAVE_WAIT,OfficeStuLeaveConstants.OFFCIE_STULEAVE_PASS});
			if(CollectionUtils.isNotEmpty(stuLeaves) && stuLeaves.size() > 0){
				isExist = true;
				return isExist;
			}else{
				isExist = false;
			}
		}else{
			isExist = false;
		}
		if(hasLong){
			List<OfficeLeavetypeLong> longs = officeLeavetypeLongDao.isExistTime(longId,startTime,endTime);
			leaveIds = new HashSet<String>();
			if(CollectionUtils.isNotEmpty(longs)){
				for (OfficeLeavetypeLong t : longs) {
					leaveIds.add(t.getLeaveId());
				}
				List<OfficeHwstudentLeave> stuLeaves = 
						officeHwstudentLeaveService.getLeavesByIdsAndState(stuId,
								leaveIds.toArray(new String[0]), new String[]{OfficeStuLeaveConstants.OFFCIE_STULEAVE_WAIT,OfficeStuLeaveConstants.OFFCIE_STULEAVE_PASS});
				if(CollectionUtils.isNotEmpty(stuLeaves) && stuLeaves.size() > 0){
					isExist = true;
				}else{
					isExist = false;
				}
			}else{
				isExist = false;
			}
		}
		return isExist;
	}
	private static void leaveInfoToLive(OfficeLeavetypeLive live,OfficeHwstudentLeave stuLeave){
		if(live == null || stuLeave == null){
			return;
		}
		live.setClassId(stuLeave.getClassId());
		live.setStudentId(stuLeave.getStudentId());
		live.setUnitId(stuLeave.getUnitId());
		live.setFlowId(stuLeave.getFlowId());
		live.setState(stuLeave.getState());
	}
	@Override
	public List<OfficeLeavetypeLive> findByLeaveTimeAndAppleyTypeAdnLeaveIds(
			Date date, String applyType, String[] leaveIds) {
		return officeLeavetypeLiveDao.findByLeaveTimeAndAppleyTypeAdnLeaveIds(date,applyType,leaveIds);
	}
	
	public void setTaskHandlerService(TaskHandlerService taskHandlerService) {
		this.taskHandlerService = taskHandlerService;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	@Override
	public List<OfficeLeavetypeLive> getAuditList(String searchType,
			String userId, Pagination page) {
		List<OfficeLeavetypeLive> list = new ArrayList<OfficeLeavetypeLive>();
		if(StringUtils.isBlank(searchType) || "0".equals(searchType)){//待审核
			List<TaskDescription> todoTaskList = taskHandlerService.getTodoTasks(userId, Integer.parseInt(FlowConstant.OFFICE_SUBSYSTEM+FlowConstant.OFFICE_STUDENT_LEAVE_LIVE), page);
			if(CollectionUtils.isNotEmpty(todoTaskList)){
				Set<String> flowIdSet= new HashSet<String>();
				for (TaskDescription task : todoTaskList) {
					flowIdSet.add(task.getProcessInstanceId());	
				}
				Map<String,OfficeHwstudentLeave> stuLeaveMap = officeHwstudentLeaveService.findByFlowIds("1", flowIdSet.toArray(new String[0]));
				Set<String> userIdSet = new HashSet<String>();
				Set<String> leaveIdSet = new HashSet<String>();
				for (String leaveId : stuLeaveMap.keySet()) {
					OfficeHwstudentLeave stuLeave = stuLeaveMap.get(leaveId);
					if(stuLeave!=null){
						userIdSet.add(stuLeave.getApplyUserId());
						leaveIdSet.add(stuLeave.getId());
					}
				}
				Map<String,OfficeLeavetypeLive> liveMap = new HashMap<String,OfficeLeavetypeLive>();
				List<OfficeLeavetypeLive> glist = officeLeavetypeLiveDao.findByLeaveIds(leaveIdSet.toArray(new String[0]));
				for(OfficeLeavetypeLive e : glist){
					liveMap.put(e.getLeaveId(), e);
				}
				Map<String,User> userMap = new HashMap<String, User>();
				userMap = userService.getUserWithDelMap(userIdSet.toArray(new String[0]));
				for (TaskDescription task : todoTaskList) {
					OfficeHwstudentLeave stuLeave = stuLeaveMap.get(task.getProcessInstanceId());
					if(stuLeave == null){
						continue;
					}
					OfficeLeavetypeLive stuLive = liveMap.get(stuLeave.getId());
					if(stuLive != null){
						stuLive.setTaskId(task.getTaskId());
						stuLive.setTaskName(task.getTaskName());
						stuLive.setClassId(stuLeave.getClassId());
						stuLive.setStudentId(stuLeave.getStudentId());
						stuLive.setUnitId(stuLeave.getUnitId());
						Student stu = studentService.getStudent(stuLeave.getStudentId());
						stuLive.setStudentName(stu.getStuname());
						BasicClass basicClass = basicClassService.getClass(stuLeave.getClassId());
						stuLive.setClassName(basicClass.getClassnamedynamic());
						stuLive.setFlowId(stuLeave.getFlowId());
						stuLive.setApplyUserId(stuLeave.getApplyUserId());
						stuLive.setState(stuLeave.getState());
						User user = userMap.get(stuLive.getApplyUserId());
						if(user!=null){
							stuLive.setApplyUserName(user.getRealname());
						}else{
							stuLive.setApplyUserName("用户已删除");
						}
						list.add(stuLive);
					}
				}
			}
		}else if("3".equals(searchType)){
			User user = userService.getUser(userId);
			if(user != null){
				String unitId = user.getUnitid();
				list = this.getListByUnitId(unitId);
				list = officeFlowService.haveDoneAudit(list, userId, page);
			}else{
				list = new ArrayList<OfficeLeavetypeLive>();
			}
		}
		else{//已审核
			List<OfficeHwstudentLeave> stuLeaveList = officeHwstudentLeaveService.getAuditedList(
					userId, new String[]{OfficeStuLeaveConstants.OFFCIE_STULEAVE_PASS, 
					OfficeStuLeaveConstants.OFFCIE_STULEAVE_FAILED},"3", page);
			Set<String> leaveIds = new HashSet<String>(); 
			Map<String,OfficeHwstudentLeave> leaveMap = new HashMap<String, OfficeHwstudentLeave>();
			for(OfficeHwstudentLeave leave : stuLeaveList){
				leaveIds.add(leave.getId());
				leaveMap.put(leave.getId(), leave);
			}
			if(CollectionUtils.isNotEmpty(stuLeaveList)){
				list = officeLeavetypeLiveDao.findByLeaveIds(leaveIds.toArray(new String[0]));
				Set<String> userIdSet = new HashSet<String>();
				for(OfficeLeavetypeLive stuLive : list){
					OfficeHwstudentLeave stuLeave = leaveMap.get(stuLive.getLeaveId());
					stuLive.setClassId(stuLeave.getClassId());
					stuLive.setStudentId(stuLeave.getStudentId());
					stuLive.setUnitId(stuLeave.getUnitId());
					Student stu = studentService.getStudent(stuLeave.getStudentId());
					stuLive.setStudentName(stu.getStuname());
					BasicClass basicClass = basicClassService.getClass(stuLeave.getClassId());
					stuLive.setClassName(basicClass.getClassnamedynamic());
					stuLive.setFlowId(stuLeave.getFlowId());
					stuLive.setApplyUserId(stuLeave.getApplyUserId());
					stuLive.setState(stuLeave.getState());
					userIdSet.add(stuLive.getApplyUserId());
				}
				Map<String,User> userMap = new HashMap<String, User>();
				userMap = userService.getUserWithDelMap(userIdSet.toArray(new String[0]));
				for(OfficeLeavetypeLive e : list){
					User user = userMap.get(e.getApplyUserId());
					if(user!=null){
						e.setApplyUserName(user.getRealname());
					}else{
						e.setApplyUserName("用户已删除");
					}
				}
			}
		}
		return list;
	}
	private List<OfficeLeavetypeLive> getListByUnitId(String unitId) {
		List<OfficeHwstudentLeave> stuLeaveList = officeHwstudentLeaveService.getOfficeHwstudentLeaveByUnitIdList(unitId);
		Set<String> leaveIds = new HashSet<String>();
		Map<String,OfficeHwstudentLeave> stuLeaveMap = new HashMap<String, OfficeHwstudentLeave>();
		for(OfficeHwstudentLeave stuLeave : stuLeaveList){
			leaveIds.add(stuLeave.getId());
			stuLeaveMap.put(stuLeave.getId(), stuLeave);
		}
		List<OfficeLeavetypeLive> list = officeLeavetypeLiveDao.findByLeaveIds(leaveIds.toArray(new String[0]));
		for(OfficeLeavetypeLive live : list){
			OfficeHwstudentLeave stuLeave = stuLeaveMap.get(live.getLeaveId());
			if(stuLeave!=null && live != null){
				live.setClassId(stuLeave.getClassId());
				live.setStudentId(stuLeave.getStudentId());
				live.setUnitId(stuLeave.getUnitId());
				Student stu = studentService.getStudent(stuLeave.getStudentId());
				live.setStudentName(stu.getStuname());
				BasicClass basicClass = basicClassService.getClass(stuLeave.getClassId());
				live.setClassName(basicClass.getClassnamedynamic());
				live.setFlowId(stuLeave.getFlowId());
				live.setApplyUserId(stuLeave.getApplyUserId());
				live.setState(stuLeave.getState());
			}
		}
		return list;
	}
	public void setOfficeLeavetypeLongDao(
			OfficeLeavetypeLongDao officeLeavetypeLongDao) {
		this.officeLeavetypeLongDao = officeLeavetypeLongDao;
	}
	
}
