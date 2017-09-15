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
import net.zdsoft.office.studentLeave.dao.OfficeLeavetypeGeneralDao;
import net.zdsoft.office.studentLeave.dao.OfficeLeavetypeTemporaryDao;
import net.zdsoft.office.studentLeave.entity.OfficeHwstudentLeave;
import net.zdsoft.office.studentLeave.entity.OfficeLeavetypeGeneral;
import net.zdsoft.office.studentLeave.entity.OfficeLeavetypeTemporary;
import net.zdsoft.office.studentLeave.service.OfficeHwstudentLeaveService;
import net.zdsoft.office.studentLeave.service.OfficeLeavetypeGeneralService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
/**
 * office_leavetype_general
 * @author 
 * 
 */
public class OfficeLeavetypeGeneralServiceImpl implements OfficeLeavetypeGeneralService{
	private OfficeLeavetypeGeneralDao officeLeavetypeGeneralDao;
	private OfficeHwstudentLeaveService officeHwstudentLeaveService;
	private BasicClassService basicClassService;
	private StudentService studentService;
	private ProcessHandlerService processHandlerService;
	private OfficeFlowStepInfoService officeFlowStepInfoService;
	private OfficeFlowService officeFlowService;
	private TaskHandlerService taskHandlerService;
	private UserService userService;
	private OfficeLeavetypeTemporaryDao officeLeavetypeTemporaryDao;
	
	public void setOfficeFlowStepInfoService(
			OfficeFlowStepInfoService officeFlowStepInfoService) {
		this.officeFlowStepInfoService = officeFlowStepInfoService;
	}
	public void setProcessHandlerService(ProcessHandlerService processHandlerService) {
		this.processHandlerService = processHandlerService;
	}
	@Override
	public List<OfficeLeavetypeGeneral> getLeavelist(String unitId, String stuId, Pagination page) {
		List<OfficeHwstudentLeave> leaves = officeHwstudentLeaveService.getOfficeHwstudentLeaveByUnitIdPage(unitId,stuId, "1", page);
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
		List<OfficeLeavetypeGeneral> generals = officeLeavetypeGeneralDao.findByLeaveIds(leaveIds.toArray(new String[0]));
		for(OfficeLeavetypeGeneral general : generals){
			leaveInfoToGeneral(general, leaveMap.get(general.getLeaveId()));
			Student stu =studentMap.get(general.getStudentId());
			BasicClass basicClass = clsMap.get(general.getClassId());
			general.setClassName(basicClass.getClassnamedynamic());
			general.setStudentName(stu.getStuname());
		}
		return generals;
	}
	@Override
	public List<OfficeLeavetypeGeneral> findByLeaveTimeAndLeaveIds(Date date,
			String[] leaveIds) {
		return officeLeavetypeGeneralDao.findByLeaveTimeAndLeaveIds(date,leaveIds);
	}
	
	@Override
	public boolean isExistTime(String id, Date startTime, Date endTime, boolean hasTemporary, String temId, String stuId) {
		List<OfficeLeavetypeGeneral> generals = officeLeavetypeGeneralDao.isExistTime(id,startTime,endTime);
		boolean isExist = false;
		Set<String> leaveIds = new HashSet<String>();
		if(CollectionUtils.isNotEmpty(generals)){
			for (OfficeLeavetypeGeneral general : generals) {
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
		if(hasTemporary){
			List<OfficeLeavetypeTemporary> tenporarys = officeLeavetypeTemporaryDao.isExistTime(temId,startTime,endTime);
			leaveIds = new HashSet<String>();
			if(CollectionUtils.isNotEmpty(tenporarys)){
				for (OfficeLeavetypeTemporary t : tenporarys) {
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
	
	public void setOfficeLeavetypeTemporaryDao(
			OfficeLeavetypeTemporaryDao officeLeavetypeTemporaryDao) {
		this.officeLeavetypeTemporaryDao = officeLeavetypeTemporaryDao;
	}
	
	private static void leaveInfoToGeneral(OfficeLeavetypeGeneral general,OfficeHwstudentLeave stuLeave){
		if(general == null || stuLeave == null){
			return;
		}
		general.setClassId(stuLeave.getClassId());
		general.setStudentId(stuLeave.getStudentId());
		general.setUnitId(stuLeave.getUnitId());
		general.setFlowId(stuLeave.getFlowId());
		general.setState(stuLeave.getState());
	}
	
	@Override
	public void startFlow(OfficeLeavetypeGeneral leaveGeneral, String userId) {
		if(StringUtils.isBlank(leaveGeneral.getLeaveId())){
			leaveGeneral.setLeaveId(UUIDUtils.newId());
			leaveGeneral.setId(UUIDUtils.newId());
		}
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("pass",true);
		variables.put("days", leaveGeneral.getDays());
		List<User> user = userService.getUsersByOwner(leaveGeneral.getStudentId());
		String flowId = processHandlerService.startProcessInstance(
				FlowConstant.OFFICE_SUBSYSTEM, leaveGeneral.getFlowId(), 
				Integer.parseInt(FlowConstant.OFFICE_SUBSYSTEM+FlowConstant.OFFICE_STUDENT_LEAVE_GENERAL), 
				leaveGeneral.getLeaveId(), user.get(0).getId(), variables);
		officeFlowStepInfoService.batchUpdateByFlowId(leaveGeneral.getFlowId(), flowId);
		leaveGeneral.setFlowId(flowId);
		OfficeLeavetypeGeneral office=findByLeaveId(leaveGeneral.getLeaveId());
		leaveGeneral.setCreationTime(new Date());
		if(office!=null){
			this.updateGeneral(leaveGeneral);
		}else{
			this.insertGeneral(leaveGeneral);
		}
	}
	
	@Override
	public void insertGeneral(OfficeLeavetypeGeneral leaveGeneral) {
		OfficeHwstudentLeave stuLeave = new OfficeHwstudentLeave();
		stuLeave.setId(leaveGeneral.getLeaveId());
		stuLeave.setApplyDate(new Date());
		stuLeave.setApplyUserId(leaveGeneral.getApplyUserId());
		stuLeave.setClassId(leaveGeneral.getClassId());
		stuLeave.setStudentId(leaveGeneral.getStudentId());
		stuLeave.setState(leaveGeneral.getState());
		stuLeave.setFlowId(leaveGeneral.getFlowId());
//		stuLeave.setCreationTime(new Date());
		stuLeave.setCreationTime(leaveGeneral.getCreationTime());
		stuLeave.setUnitId(leaveGeneral.getUnitId());
		stuLeave.setType("1");
		stuLeave.setIsDeleted(false);
//		stuLeave.setModifyTime(new Date());
		stuLeave = officeHwstudentLeaveService.save(stuLeave);
		leaveGeneral.setCreationTime(new Date());
		leaveGeneral.setLeaveId(stuLeave.getId());
		officeLeavetypeGeneralDao.save(leaveGeneral);
	}
	
	@Override
	public void updateGeneral(OfficeLeavetypeGeneral leaveGeneral) {
		OfficeHwstudentLeave stuLeave = new OfficeHwstudentLeave();
		stuLeave.setId(leaveGeneral.getLeaveId());
		stuLeave.setApplyDate(new Date());
		stuLeave.setApplyUserId(leaveGeneral.getApplyUserId());
		stuLeave.setClassId(leaveGeneral.getClassId());
		stuLeave.setStudentId(leaveGeneral.getStudentId());
		stuLeave.setState(leaveGeneral.getState());
		stuLeave.setFlowId(leaveGeneral.getFlowId());
		stuLeave.setUnitId(leaveGeneral.getUnitId());
		stuLeave.setType("1");
		stuLeave.setIsDeleted(false);
//		stuLeave.setModifyTime(new Date());
		stuLeave.setCreationTime(leaveGeneral.getCreationTime());
		officeHwstudentLeaveService.update(stuLeave);
		leaveGeneral.setCreationTime(new Date());
		leaveGeneral.setLeaveId(stuLeave.getId());
		officeLeavetypeGeneralDao.delete(new String[]{leaveGeneral.getId()});
		leaveGeneral.setId("");
		officeLeavetypeGeneralDao.save(leaveGeneral);
	}
	
	public void setBasicClassService(BasicClassService basicClassService) {
		this.basicClassService = basicClassService;
	}

	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

	public void setOfficeHwstudentLeaveService(
			OfficeHwstudentLeaveService officeHwstudentLeaveService) {
		this.officeHwstudentLeaveService = officeHwstudentLeaveService;
	}

	@Override
	public OfficeLeavetypeGeneral save(OfficeLeavetypeGeneral officeLeavetypeGeneral){
		return officeLeavetypeGeneralDao.save(officeLeavetypeGeneral);
	}

	@Override
	public Integer delete(String[] ids){
		return officeLeavetypeGeneralDao.delete(ids);
	}
	@Override
	public void deleteByLeaveId(String leaveId) {
		officeLeavetypeGeneralDao.deleteByLeaveId(leaveId);
	}
	@Override
	public Integer update(OfficeLeavetypeGeneral officeLeavetypeGeneral){
		return officeLeavetypeGeneralDao.update(officeLeavetypeGeneral);
	}

	@Override
	public OfficeLeavetypeGeneral getOfficeLeavetypeGeneralById(String id){
		return officeLeavetypeGeneralDao.getOfficeLeavetypeGeneralById(id);
	}

	@Override
	public Map<String, OfficeLeavetypeGeneral> getOfficeLeavetypeGeneralMapByIds(String[] ids){
		return officeLeavetypeGeneralDao.getOfficeLeavetypeGeneralMapByIds(ids);
	}
	@Override
	public Map<String, OfficeLeavetypeGeneral> getMapByLeaveIds(
			String[] leaveIds) {
		return officeLeavetypeGeneralDao.getMapByLeaveIds(leaveIds);
	}
	@Override
	public List<OfficeLeavetypeGeneral> getOfficeLeavetypeGeneralList(){
		return officeLeavetypeGeneralDao.getOfficeLeavetypeGeneralList();
	}

	@Override
	public List<OfficeLeavetypeGeneral> getOfficeLeavetypeGeneralPage(Pagination page){
		return officeLeavetypeGeneralDao.getOfficeLeavetypeGeneralPage(page);
	}
	
	public void setOfficeLeavetypeGeneralDao(OfficeLeavetypeGeneralDao officeLeavetypeGeneralDao){
		this.officeLeavetypeGeneralDao = officeLeavetypeGeneralDao;
	}
	
	@Override
	public OfficeLeavetypeGeneral findByLeaveId(String leaveId) {
		OfficeHwstudentLeave stuLeave = officeHwstudentLeaveService.getOfficeHwstudentLeaveById(leaveId);
		OfficeLeavetypeGeneral general = officeLeavetypeGeneralDao.getOfficeLeavetypeGeneralByLeaveId(leaveId);
		if(general == null || stuLeave == null){
			return null;
		}
		general.setClassId(stuLeave.getClassId());
		general.setStudentId(stuLeave.getStudentId());
		general.setUnitId(stuLeave.getUnitId());
		Student stu = studentService.getStudent(stuLeave.getStudentId());
		general.setStudentName(stu.getStuname());
		BasicClass basicClass = basicClassService.getClass(stuLeave.getClassId());
		general.setClassName(basicClass.getClassnamedynamic());
		general.setFlowId(stuLeave.getFlowId());
		general.setApplyUserId(stuLeave.getApplyUserId());
		if(StringUtils.isNotBlank(stuLeave.getApplyUserId())){
			User user = userService.getUser(stuLeave.getApplyUserId());
			general.setApplyUserName(user.getRealname());
		}
		general.setCreationTime(stuLeave.getCreationTime());
		if(stuLeave != null){
			if(StringUtils.isNotEmpty(stuLeave.getFlowId())){
				List<HisTask> hisTask = officeFlowService.getHisTask(stuLeave.getFlowId());
				general.setHisTaskList(hisTask);
			}
		}
		return general;
	}
	public void setOfficeFlowService(OfficeFlowService officeFlowService) {
		this.officeFlowService = officeFlowService;
	}
	
	
	public void setTaskHandlerService(TaskHandlerService taskHandlerService) {
		this.taskHandlerService = taskHandlerService;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	@Override
	public List<OfficeLeavetypeGeneral> getAuditList(String searchType,
			String userId, Pagination page) {
		List<OfficeLeavetypeGeneral> list = new ArrayList<OfficeLeavetypeGeneral>();
		if(StringUtils.isBlank(searchType) || "0".equals(searchType)){//待审核
			List<TaskDescription> todoTaskList = taskHandlerService.getTodoTasks(userId, Integer.parseInt(FlowConstant.OFFICE_SUBSYSTEM+FlowConstant.OFFICE_STUDENT_LEAVE_GENERAL), page);
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
				Map<String,OfficeLeavetypeGeneral> generalMap = new HashMap<String,OfficeLeavetypeGeneral>();
				List<OfficeLeavetypeGeneral> glist = officeLeavetypeGeneralDao.findByLeaveIds(leaveIdSet.toArray(new String[0]));
				for(OfficeLeavetypeGeneral e : glist){
					generalMap.put(e.getLeaveId(), e);
				}
				Map<String,User> userMap = new HashMap<String, User>();
				userMap = userService.getUserWithDelMap(userIdSet.toArray(new String[0]));
				for (TaskDescription task : todoTaskList) {
					OfficeHwstudentLeave stuLeave = stuLeaveMap.get(task.getProcessInstanceId());
					if(stuLeave == null){
						continue;
					}
					OfficeLeavetypeGeneral stuGeneral = generalMap.get(stuLeave.getId());
					if(stuGeneral != null){
						stuGeneral.setTaskId(task.getTaskId());
						stuGeneral.setTaskName(task.getTaskName());
						stuGeneral.setClassId(stuLeave.getClassId());
						stuGeneral.setStudentId(stuLeave.getStudentId());
						stuGeneral.setUnitId(stuLeave.getUnitId());
						Student stu = studentService.getStudent(stuLeave.getStudentId());
						stuGeneral.setStudentName(stu.getStuname());
						BasicClass basicClass = basicClassService.getClass(stuLeave.getClassId());
						stuGeneral.setClassName(basicClass.getClassnamedynamic());
						stuGeneral.setFlowId(stuLeave.getFlowId());
						stuGeneral.setApplyUserId(stuLeave.getApplyUserId());
						stuGeneral.setState(stuLeave.getState());
						User user = userMap.get(stuGeneral.getApplyUserId());
						if(user!=null){
							stuGeneral.setApplyUserName(user.getRealname());
						}else{
							stuGeneral.setApplyUserName("用户已删除");
						}
						list.add(stuGeneral);
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
				list = new ArrayList<OfficeLeavetypeGeneral>();
			}
		}
		else{//已审核
			List<OfficeHwstudentLeave> stuLeaveList = officeHwstudentLeaveService.getAuditedList(
					userId, new String[]{OfficeStuLeaveConstants.OFFCIE_STULEAVE_PASS, 
					OfficeStuLeaveConstants.OFFCIE_STULEAVE_FAILED},"1", page);
			Set<String> leaveIds = new HashSet<String>(); 
			Map<String,OfficeHwstudentLeave> leaveMap = new HashMap<String, OfficeHwstudentLeave>();
			for(OfficeHwstudentLeave leave : stuLeaveList){
				leaveIds.add(leave.getId());
				leaveMap.put(leave.getId(), leave);
			}
			if(CollectionUtils.isNotEmpty(stuLeaveList)){
				list = officeLeavetypeGeneralDao.findByLeaveIds(leaveIds.toArray(new String[0]));
				Set<String> userIdSet = new HashSet<String>();
				for(OfficeLeavetypeGeneral stuGeneral : list){
					OfficeHwstudentLeave stuLeave = leaveMap.get(stuGeneral.getLeaveId());
					stuGeneral.setClassId(stuLeave.getClassId());
					stuGeneral.setStudentId(stuLeave.getStudentId());
					stuGeneral.setUnitId(stuLeave.getUnitId());
					Student stu = studentService.getStudent(stuLeave.getStudentId());
					stuGeneral.setStudentName(stu.getStuname());
					BasicClass basicClass = basicClassService.getClass(stuLeave.getClassId());
					stuGeneral.setClassName(basicClass.getClassnamedynamic());
					stuGeneral.setFlowId(stuLeave.getFlowId());
					stuGeneral.setApplyUserId(stuLeave.getApplyUserId());
					stuGeneral.setState(stuLeave.getState());
					userIdSet.add(stuGeneral.getApplyUserId());
				}
				Map<String,User> userMap = new HashMap<String, User>();
				userMap = userService.getUserWithDelMap(userIdSet.toArray(new String[0]));
				for(OfficeLeavetypeGeneral e : list){
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
	private List<OfficeLeavetypeGeneral> getListByUnitId(String unitId) {
		List<OfficeHwstudentLeave> stuLeaveList = officeHwstudentLeaveService.getOfficeHwstudentLeaveByUnitIdList(unitId);
		Set<String> leaveIds = new HashSet<String>();
		Map<String,OfficeHwstudentLeave> stuLeaveMap = new HashMap<String, OfficeHwstudentLeave>();
		for(OfficeHwstudentLeave stuLeave : stuLeaveList){
			leaveIds.add(stuLeave.getId());
			stuLeaveMap.put(stuLeave.getId(), stuLeave);
		}
		List<OfficeLeavetypeGeneral> list = officeLeavetypeGeneralDao.findByLeaveIds(leaveIds.toArray(new String[0]));
		for(OfficeLeavetypeGeneral general : list){
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
	
}
