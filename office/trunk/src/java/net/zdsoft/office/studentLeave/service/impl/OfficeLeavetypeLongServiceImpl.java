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
import net.zdsoft.office.studentLeave.dao.OfficeLeavetypeLongDao;
import net.zdsoft.office.studentLeave.entity.OfficeHwstudentLeave;
import net.zdsoft.office.studentLeave.entity.OfficeLeavetypeLong;
import net.zdsoft.office.studentLeave.service.OfficeHwstudentLeaveService;
import net.zdsoft.office.studentLeave.service.OfficeLeavetypeLongService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
/**
 * office_leavetype_long
 * @author 
 * 
 */
public class OfficeLeavetypeLongServiceImpl implements OfficeLeavetypeLongService{
	private OfficeLeavetypeLongDao officeLeavetypeLongDao;
	private OfficeHwstudentLeaveService officeHwstudentLeaveService;
	private BasicClassService basicClassService;
	private StudentService studentService;
	private ProcessHandlerService processHandlerService;
	private OfficeFlowStepInfoService officeFlowStepInfoService;
	private OfficeFlowService officeFlowService;
	private TaskHandlerService taskHandlerService;
	private UserService userService;
	
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
	public OfficeLeavetypeLong save(OfficeLeavetypeLong officeLeavetypeLong){
		return officeLeavetypeLongDao.save(officeLeavetypeLong);
	}
	@Override
	public void deleteByLeaveId(String leaveId) {
		officeLeavetypeLongDao.deleteByLeaveId(leaveId);
	}
	@Override
	public Integer delete(String[] ids){
		return officeLeavetypeLongDao.delete(ids);
	}

	@Override
	public Integer update(OfficeLeavetypeLong officeLeavetypeLong){
		return officeLeavetypeLongDao.update(officeLeavetypeLong);
	}

	@Override
	public OfficeLeavetypeLong getOfficeLeavetypeLongById(String id){
		return officeLeavetypeLongDao.getOfficeLeavetypeLongById(id);
	}

	@Override
	public Map<String, OfficeLeavetypeLong> getOfficeLeavetypeLongMapByIds(String[] ids){
		return officeLeavetypeLongDao.getOfficeLeavetypeLongMapByIds(ids);
	}
	@Override
	public Map<String, OfficeLeavetypeLong> getMapByLeaveIds(String[] leaveIds) {
		return officeLeavetypeLongDao.getMapByLeaveIds(leaveIds);
	}
	@Override
	public List<OfficeLeavetypeLong> getOfficeLeavetypeLongList(){
		return officeLeavetypeLongDao.getOfficeLeavetypeLongList();
	}

	@Override
	public List<OfficeLeavetypeLong> getOfficeLeavetypeLongPage(Pagination page){
		return officeLeavetypeLongDao.getOfficeLeavetypeLongPage(page);
	}

	public void setOfficeLeavetypeLongDao(OfficeLeavetypeLongDao officeLeavetypeLongDao){
		this.officeLeavetypeLongDao = officeLeavetypeLongDao;
	}
	

	@Override
	public OfficeLeavetypeLong findByLeaveId(String leaveId) {
		OfficeHwstudentLeave stuLeave = officeHwstudentLeaveService.getOfficeHwstudentLeaveById(leaveId);
		OfficeLeavetypeLong longLeave = officeLeavetypeLongDao.findByLeaveId(leaveId);
		if(longLeave == null || stuLeave == null){
			return null;
		}
		longLeave.setClassId(stuLeave.getClassId());
		longLeave.setStudentId(stuLeave.getStudentId());
		longLeave.setUnitId(stuLeave.getUnitId());
		Student stu = studentService.getStudent(stuLeave.getStudentId());
		longLeave.setStudentName(stu.getStuname());
		BasicClass basicClass = basicClassService.getClass(stuLeave.getClassId());
		longLeave.setClassName(basicClass.getClassnamedynamic());
		longLeave.setFlowId(stuLeave.getFlowId());
		longLeave.setApplyUserId(stuLeave.getApplyUserId());
		if(StringUtils.isNotBlank(stuLeave.getApplyUserId())){
			User user = userService.getUser(stuLeave.getApplyUserId());
			longLeave.setApplyUserName(user.getRealname());
		}
		longLeave.setCreationTime(stuLeave.getCreationTime());
		if(stuLeave != null){
			if(StringUtils.isNotEmpty(stuLeave.getFlowId())){
				List<HisTask> hisTask = officeFlowService.getHisTask(stuLeave.getFlowId());
				longLeave.setHisTaskList(hisTask);
			}
		}
		return longLeave;
	}
	@Override
	public void insertLong(OfficeLeavetypeLong leaveLong) {
		OfficeHwstudentLeave stuLeave = new OfficeHwstudentLeave();
		stuLeave.setId(leaveLong.getLeaveId());
		stuLeave.setApplyDate(new Date());
		stuLeave.setApplyUserId(leaveLong.getApplyUserId());
		stuLeave.setClassId(leaveLong.getClassId());
		stuLeave.setStudentId(leaveLong.getStudentId());
		stuLeave.setState(leaveLong.getState());
		stuLeave.setFlowId(leaveLong.getFlowId());
		stuLeave.setCreationTime(leaveLong.getCreationTime());
//		stuLeave.setModifyTime(new Date());
		stuLeave.setUnitId(leaveLong.getUnitId());
		stuLeave.setType("4");
		stuLeave.setIsDeleted(false);
		stuLeave = officeHwstudentLeaveService.save(stuLeave);
		leaveLong.setCreationTime(new Date());
		leaveLong.setLeaveId(stuLeave.getId());
		officeLeavetypeLongDao.save(leaveLong);
	}
	@Override
	public void updateLong(OfficeLeavetypeLong leaveLong) {
		OfficeHwstudentLeave stuLeave = new OfficeHwstudentLeave();
		stuLeave.setId(leaveLong.getLeaveId());
		stuLeave.setApplyDate(new Date());
		stuLeave.setApplyUserId(leaveLong.getApplyUserId());
		stuLeave.setClassId(leaveLong.getClassId());
		stuLeave.setStudentId(leaveLong.getStudentId());
		stuLeave.setState(leaveLong.getState());
		stuLeave.setFlowId(leaveLong.getFlowId());
		stuLeave.setUnitId(leaveLong.getUnitId());
		stuLeave.setType("4");
		stuLeave.setIsDeleted(false);
		stuLeave.setCreationTime(leaveLong.getCreationTime());
//		stuLeave.setModifyTime(new Date());
		officeHwstudentLeaveService.update(stuLeave);
		leaveLong.setCreationTime(new Date());
		leaveLong.setLeaveId(stuLeave.getId());
		officeLeavetypeLongDao.delete(new String[]{leaveLong.getId()});
		leaveLong.setId("");
		officeLeavetypeLongDao.save(leaveLong);
	}
	@Override
	public void startFlow(OfficeLeavetypeLong leaveLong, String userId) {
		if(StringUtils.isBlank(leaveLong.getLeaveId())){
			leaveLong.setLeaveId(UUIDUtils.newId());
			leaveLong.setId(UUIDUtils.newId());
		}
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("pass",true);
		List<User> user = userService.getUsersByOwner(leaveLong.getStudentId());
		String flowId = processHandlerService.startProcessInstance(
				FlowConstant.OFFICE_SUBSYSTEM, leaveLong.getFlowId(), 
				Integer.parseInt(FlowConstant.OFFICE_SUBSYSTEM+FlowConstant.OFFICE_STUDENT_LEAVE_LONG), 
				leaveLong.getLeaveId(), user.get(0).getId(), variables);
		officeFlowStepInfoService.batchUpdateByFlowId(leaveLong.getFlowId(), flowId);
		leaveLong.setFlowId(flowId);
		OfficeHwstudentLeave office=officeHwstudentLeaveService.getOfficeHwstudentLeaveById(leaveLong.getLeaveId());
		leaveLong.setCreationTime(new Date());
		if(office!=null){
			this.updateLong(leaveLong);
		}else{
			this.insertLong(leaveLong);
		}
	}
	@Override
	public List<OfficeLeavetypeLong> getLeavelist(String unitId, String stuId,
			Pagination page) {
		List<OfficeHwstudentLeave> leaves = officeHwstudentLeaveService.getOfficeHwstudentLeaveByUnitIdPage(unitId,stuId, "4", page);
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
		List<OfficeLeavetypeLong> longLeaves = officeLeavetypeLongDao.findByLeaveIds(leaveIds.toArray(new String[0]));
		for(OfficeLeavetypeLong longLeave : longLeaves){
			leaveInfoToLong(longLeave, leaveMap.get(longLeave.getLeaveId()));
			Student stu =studentMap.get(longLeave.getStudentId());
			BasicClass basicClass = clsMap.get(longLeave.getClassId());
			longLeave.setClassName(basicClass.getClassnamedynamic());
			longLeave.setStudentName(stu.getStuname());
		}
		return longLeaves;
	}

	private static void leaveInfoToLong(OfficeLeavetypeLong longLeave,OfficeHwstudentLeave stuLeave){
		if(longLeave == null || stuLeave == null){
			return;
		}
		longLeave.setClassId(stuLeave.getClassId());
		longLeave.setStudentId(stuLeave.getStudentId());
		longLeave.setUnitId(stuLeave.getUnitId());
		longLeave.setFlowId(stuLeave.getFlowId());
		longLeave.setState(stuLeave.getState());
	}
	
	public void setTaskHandlerService(TaskHandlerService taskHandlerService) {
		this.taskHandlerService = taskHandlerService;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	@Override
	public List<OfficeLeavetypeLong> getAuditList(String searchType,
			String userId, Pagination page) {
		List<OfficeLeavetypeLong> list = new ArrayList<OfficeLeavetypeLong>();
		if(StringUtils.isBlank(searchType) || "0".equals(searchType)){//待审核
			List<TaskDescription> todoTaskList = taskHandlerService.getTodoTasks(userId, Integer.parseInt(FlowConstant.OFFICE_SUBSYSTEM+FlowConstant.OFFICE_STUDENT_LEAVE_LONG), page);
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
				Map<String,OfficeLeavetypeLong> generalMap = new HashMap<String,OfficeLeavetypeLong>();
				List<OfficeLeavetypeLong> glist = officeLeavetypeLongDao.findByLeaveIds(leaveIdSet.toArray(new String[0]));
				for(OfficeLeavetypeLong e : glist){
					generalMap.put(e.getLeaveId(), e);
				}
				Map<String,User> userMap = new HashMap<String, User>();
				userMap = userService.getUserWithDelMap(userIdSet.toArray(new String[0]));
				for (TaskDescription task : todoTaskList) {
					OfficeHwstudentLeave stuLeave = stuLeaveMap.get(task.getProcessInstanceId());
					if(stuLeave == null){
						continue;
					}
					OfficeLeavetypeLong stuLong = generalMap.get(stuLeave.getId());
					if(stuLong != null){
						stuLong.setTaskId(task.getTaskId());
						stuLong.setTaskName(task.getTaskName());
						stuLong.setClassId(stuLeave.getClassId());
						stuLong.setStudentId(stuLeave.getStudentId());
						stuLong.setUnitId(stuLeave.getUnitId());
						Student stu = studentService.getStudent(stuLeave.getStudentId());
						stuLong.setStudentName(stu.getStuname());
						BasicClass basicClass = basicClassService.getClass(stuLeave.getClassId());
						stuLong.setClassName(basicClass.getClassnamedynamic());
						stuLong.setFlowId(stuLeave.getFlowId());
						stuLong.setApplyUserId(stuLeave.getApplyUserId());
						stuLong.setState(stuLeave.getState());
						User user = userMap.get(stuLong.getApplyUserId());
						if(user!=null){
							stuLong.setApplyUserName(user.getRealname());
						}else{
							stuLong.setApplyUserName("用户已删除");
						}
						list.add(stuLong);
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
				list = new ArrayList<OfficeLeavetypeLong>();
			}
		}
		else{//已审核
			List<OfficeHwstudentLeave> stuLeaveList = officeHwstudentLeaveService.getAuditedList(
					userId, new String[]{OfficeStuLeaveConstants.OFFCIE_STULEAVE_PASS, 
					OfficeStuLeaveConstants.OFFCIE_STULEAVE_FAILED},"4", page);
			Set<String> leaveIds = new HashSet<String>(); 
			Map<String,OfficeHwstudentLeave> leaveMap = new HashMap<String, OfficeHwstudentLeave>();
			for(OfficeHwstudentLeave leave : stuLeaveList){
				leaveIds.add(leave.getId());
				leaveMap.put(leave.getId(), leave);
			}
			if(CollectionUtils.isNotEmpty(stuLeaveList)){
				list = officeLeavetypeLongDao.findByLeaveIds(leaveIds.toArray(new String[0]));
				Set<String> userIdSet = new HashSet<String>();
				for(OfficeLeavetypeLong stuLong : list){
					OfficeHwstudentLeave stuLeave = leaveMap.get(stuLong.getLeaveId());
					stuLong.setClassId(stuLeave.getClassId());
					stuLong.setStudentId(stuLeave.getStudentId());
					stuLong.setUnitId(stuLeave.getUnitId());
					Student stu = studentService.getStudent(stuLeave.getStudentId());
					stuLong.setStudentName(stu.getStuname());
					BasicClass basicClass = basicClassService.getClass(stuLeave.getClassId());
					stuLong.setClassName(basicClass.getClassnamedynamic());
					stuLong.setFlowId(stuLeave.getFlowId());
					stuLong.setApplyUserId(stuLeave.getApplyUserId());
					stuLong.setState(stuLeave.getState());
					userIdSet.add(stuLong.getApplyUserId());
				}
				Map<String,User> userMap = new HashMap<String, User>();
				userMap = userService.getUserWithDelMap(userIdSet.toArray(new String[0]));
				for(OfficeLeavetypeLong e : list){
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
	private List<OfficeLeavetypeLong> getListByUnitId(String unitId) {
		List<OfficeHwstudentLeave> stuLeaveList = officeHwstudentLeaveService.getOfficeHwstudentLeaveByUnitIdList(unitId);
		Set<String> leaveIds = new HashSet<String>();
		Map<String,OfficeHwstudentLeave> stuLeaveMap = new HashMap<String, OfficeHwstudentLeave>();
		for(OfficeHwstudentLeave stuLeave : stuLeaveList){
			leaveIds.add(stuLeave.getId());
			stuLeaveMap.put(stuLeave.getId(), stuLeave);
		}
		List<OfficeLeavetypeLong> list = officeLeavetypeLongDao.findByLeaveIds(leaveIds.toArray(new String[0]));
		for(OfficeLeavetypeLong general : list){
			OfficeHwstudentLeave stuLeave = stuLeaveMap.get(general.getLeaveId());
			if(stuLeave!=null && general != null){
				general.setClassId(stuLeave.getClassId());
				general.setStudentId(stuLeave.getStudentId());
				general.setUnitId(stuLeave.getUnitId());
				Student stu = studentService.getStudent(stuLeave.getStudentId());
				general.setStudentName(stu.getStuname());
				BasicClass basicClass = basicClassService.getClass(stuLeave.getClassId());
				general.setClassName(basicClass.getClassnamedynamic());
				general.setFlowId(stuLeave.getFlowId());
				general.setApplyUserId(stuLeave.getApplyUserId());
				general.setState(stuLeave.getState());
			}
		}
		return list;
	}
	
	@Override
	public List<OfficeLeavetypeLong> findByLeaveTimeAndLeaveIds(Date date,
			String[] leaveIds) {
		return officeLeavetypeLongDao.findByLeaveTimeAndLeaveIds(date,leaveIds);
	}
}
