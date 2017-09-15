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
import net.zdsoft.office.studentLeave.dao.OfficeLeavetypeTemporaryDao;
import net.zdsoft.office.studentLeave.entity.OfficeHwstudentLeave;
import net.zdsoft.office.studentLeave.entity.OfficeLeavetypeTemporary;
import net.zdsoft.office.studentLeave.service.OfficeHwstudentLeaveService;
import net.zdsoft.office.studentLeave.service.OfficeLeavetypeTemporaryService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
/**
 * office_leavetype_temporary
 * @author 
 * 
 */
public class OfficeLeavetypeTemporaryServiceImpl implements OfficeLeavetypeTemporaryService{
	private OfficeLeavetypeTemporaryDao officeLeavetypeTemporaryDao;
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
	public OfficeLeavetypeTemporary save(OfficeLeavetypeTemporary officeLeavetypeTemporary){
		return officeLeavetypeTemporaryDao.save(officeLeavetypeTemporary);
	}
	@Override
	public void deleteByLeaveId(String leaveId) {
		officeLeavetypeTemporaryDao.deleteByLeaveId(leaveId);
	}
	@Override
	public Integer delete(String[] ids){
		return officeLeavetypeTemporaryDao.delete(ids);
	}

	@Override
	public Integer update(OfficeLeavetypeTemporary officeLeavetypeTemporary){
		return officeLeavetypeTemporaryDao.update(officeLeavetypeTemporary);
	}

	@Override
	public OfficeLeavetypeTemporary getOfficeLeavetypeTemporaryById(String id){
		return officeLeavetypeTemporaryDao.getOfficeLeavetypeTemporaryById(id);
	}

	@Override
	public Map<String, OfficeLeavetypeTemporary> getOfficeLeavetypeTemporaryMapByIds(String[] ids){
		return officeLeavetypeTemporaryDao.getOfficeLeavetypeTemporaryMapByIds(ids);
	}
	@Override
	public Map<String, OfficeLeavetypeTemporary> getMapByLeaveIds(
			String[] leaveIds) {
		return officeLeavetypeTemporaryDao.getMapByLeaveIds(leaveIds);
	}
	@Override
	public List<OfficeLeavetypeTemporary> getOfficeLeavetypeTemporaryList(){
		return officeLeavetypeTemporaryDao.getOfficeLeavetypeTemporaryList();
	}

	@Override
	public List<OfficeLeavetypeTemporary> getOfficeLeavetypeTemporaryPage(Pagination page){
		return officeLeavetypeTemporaryDao.getOfficeLeavetypeTemporaryPage(page);
	}

	public void setOfficeLeavetypeTemporaryDao(OfficeLeavetypeTemporaryDao officeLeavetypeTemporaryDao){
		this.officeLeavetypeTemporaryDao = officeLeavetypeTemporaryDao;
	}
	@Override
	public List<OfficeLeavetypeTemporary> findByLeaveTimeAndLeaveIds(Date date,
			String[] leaveIds) {
		return officeLeavetypeTemporaryDao.findByLeaveTimeAndLeaveIds(date,leaveIds);
	}
	@Override
	public OfficeLeavetypeTemporary findByLeaveId(String leaveId) {
		OfficeHwstudentLeave stuLeave = officeHwstudentLeaveService.getOfficeHwstudentLeaveById(leaveId);
		OfficeLeavetypeTemporary temporary = officeLeavetypeTemporaryDao.findByLeaveId(leaveId);
		if(temporary == null || stuLeave == null){
			return null;
		}
		temporary.setClassId(stuLeave.getClassId());
		temporary.setStudentId(stuLeave.getStudentId());
		temporary.setUnitId(stuLeave.getUnitId());
		Student stu = studentService.getStudent(stuLeave.getStudentId());
		temporary.setStudentName(stu.getStuname());
		BasicClass basicClass = basicClassService.getClass(stuLeave.getClassId());
		temporary.setClassName(basicClass.getClassnamedynamic());
		temporary.setFlowId(stuLeave.getFlowId());
		temporary.setApplyUserId(stuLeave.getApplyUserId());
		if(StringUtils.isNotBlank(stuLeave.getApplyUserId())){
			User user = userService.getUser(stuLeave.getApplyUserId());
			temporary.setApplyUserName(user.getRealname());
		}
		temporary.setCreationTime(stuLeave.getCreationTime());
		if(stuLeave != null){
			if(StringUtils.isNotEmpty(stuLeave.getFlowId())){
				List<HisTask> hisTask = officeFlowService.getHisTask(stuLeave.getFlowId());
				temporary.setHisTaskList(hisTask);
			}
		}
		return temporary;
	}
	@Override
	public void insertTemporary(OfficeLeavetypeTemporary leaveTemporary) {
		OfficeHwstudentLeave stuLeave = new OfficeHwstudentLeave();
		stuLeave.setId(leaveTemporary.getLeaveId());
		stuLeave.setApplyDate(new Date());
		stuLeave.setApplyUserId(leaveTemporary.getApplyUserId());
		stuLeave.setClassId(leaveTemporary.getClassId());
		stuLeave.setStudentId(leaveTemporary.getStudentId());
		stuLeave.setState(leaveTemporary.getState());
		stuLeave.setFlowId(leaveTemporary.getFlowId());
//		stuLeave.setCreationTime(new Date());
		stuLeave.setCreationTime(leaveTemporary.getCreationTime());
		stuLeave.setUnitId(leaveTemporary.getUnitId());
		stuLeave.setType("2");
		stuLeave.setIsDeleted(false);
//		stuLeave.setModifyTime(new Date());
		stuLeave = officeHwstudentLeaveService.save(stuLeave);
		leaveTemporary.setCreationTime(new Date());
		leaveTemporary.setLeaveId(stuLeave.getId());
		officeLeavetypeTemporaryDao.save(leaveTemporary);
	}
	@Override
	public void updateTemporary(OfficeLeavetypeTemporary leaveTemporary) {
		OfficeHwstudentLeave stuLeave = new OfficeHwstudentLeave();
		stuLeave.setId(leaveTemporary.getLeaveId());
		stuLeave.setApplyDate(new Date());
		stuLeave.setApplyUserId(leaveTemporary.getApplyUserId());
		stuLeave.setClassId(leaveTemporary.getClassId());
		stuLeave.setStudentId(leaveTemporary.getStudentId());
		stuLeave.setState(leaveTemporary.getState());
		stuLeave.setFlowId(leaveTemporary.getFlowId());
		stuLeave.setUnitId(leaveTemporary.getUnitId());
		stuLeave.setType("2");
		stuLeave.setIsDeleted(false);
//		stuLeave.setModifyTime(new Date());
		stuLeave.setCreationTime(leaveTemporary.getCreationTime());
		officeHwstudentLeaveService.update(stuLeave);
		leaveTemporary.setCreationTime(new Date());
		leaveTemporary.setLeaveId(stuLeave.getId());
		officeLeavetypeTemporaryDao.delete(new String[]{leaveTemporary.getId()});
		leaveTemporary.setId("");
		officeLeavetypeTemporaryDao.save(leaveTemporary);
	}
	@Override
	public void startFlow(OfficeLeavetypeTemporary leaveTemporary, String userId) {
		if(StringUtils.isBlank(leaveTemporary.getLeaveId())){
			leaveTemporary.setLeaveId(UUIDUtils.newId());
			leaveTemporary.setId(UUIDUtils.newId());
		}
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("pass",true);
		List<User> user = userService.getUsersByOwner(leaveTemporary.getStudentId());
		String flowId = processHandlerService.startProcessInstance(
				FlowConstant.OFFICE_SUBSYSTEM, leaveTemporary.getFlowId(), 
				Integer.parseInt(FlowConstant.OFFICE_SUBSYSTEM+FlowConstant.OFFICE_STUDENT_LEAVE_TEMPORARY), 
				leaveTemporary.getLeaveId(), user.get(0).getId(), variables);
		officeFlowStepInfoService.batchUpdateByFlowId(leaveTemporary.getFlowId(), flowId);
		leaveTemporary.setFlowId(flowId);
		OfficeHwstudentLeave office=officeHwstudentLeaveService.getOfficeHwstudentLeaveById(leaveTemporary.getLeaveId());
		leaveTemporary.setCreationTime(new Date());
		if(office!=null){
			this.updateTemporary(leaveTemporary);
		}else{
			this.insertTemporary(leaveTemporary);
		}
	}
	@Override
	public List<OfficeLeavetypeTemporary> getLeavelist(String unitId,
			String stuId, Pagination page) {
		List<OfficeHwstudentLeave> leaves = officeHwstudentLeaveService.getOfficeHwstudentLeaveByUnitIdPage(unitId,stuId, "2", page);
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
		List<OfficeLeavetypeTemporary> temporarys = officeLeavetypeTemporaryDao.findByLeaveIds(leaveIds.toArray(new String[0]));
		for(OfficeLeavetypeTemporary temporary : temporarys){
			leaveInfoToTemporary(temporary, leaveMap.get(temporary.getLeaveId()));
			Student stu =studentMap.get(temporary.getStudentId());
			BasicClass basicClass = clsMap.get(temporary.getClassId());
			temporary.setClassName(basicClass.getClassnamedynamic());
			temporary.setStudentName(stu.getStuname());
		}
		return temporarys;
	}
	

	private static void leaveInfoToTemporary(OfficeLeavetypeTemporary temporary,OfficeHwstudentLeave stuLeave){
		if(temporary == null || stuLeave == null){
			return;
		}
		temporary.setClassId(stuLeave.getClassId());
		temporary.setStudentId(stuLeave.getStudentId());
		temporary.setUnitId(stuLeave.getUnitId());
		temporary.setFlowId(stuLeave.getFlowId());
		temporary.setState(stuLeave.getState());
	}
	
	
	public void setTaskHandlerService(TaskHandlerService taskHandlerService) {
		this.taskHandlerService = taskHandlerService;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	@Override
	public List<OfficeLeavetypeTemporary> getAuditList(String searchType,
			String userId, Pagination page) {
		List<OfficeLeavetypeTemporary> list = new ArrayList<OfficeLeavetypeTemporary>();
		if(StringUtils.isBlank(searchType) || "0".equals(searchType)){//待审核
			List<TaskDescription> todoTaskList = taskHandlerService.getTodoTasks(userId, Integer.parseInt(FlowConstant.OFFICE_SUBSYSTEM+FlowConstant.OFFICE_STUDENT_LEAVE_TEMPORARY), page);
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
				Map<String,OfficeLeavetypeTemporary> temporaryMap = new HashMap<String,OfficeLeavetypeTemporary>();
				List<OfficeLeavetypeTemporary> glist = officeLeavetypeTemporaryDao.findByLeaveIds(leaveIdSet.toArray(new String[0]));
				for(OfficeLeavetypeTemporary e : glist){
					temporaryMap.put(e.getLeaveId(), e);
				}
				Map<String,User> userMap = new HashMap<String, User>();
				userMap = userService.getUserWithDelMap(userIdSet.toArray(new String[0]));
				for (TaskDescription task : todoTaskList) {
					OfficeHwstudentLeave stuLeave = stuLeaveMap.get(task.getProcessInstanceId());
					if(stuLeave == null){
						continue;
					}
					OfficeLeavetypeTemporary stuTemporary = temporaryMap.get(stuLeave.getId());
					if(stuTemporary != null){
						stuTemporary.setTaskId(task.getTaskId());
						stuTemporary.setTaskName(task.getTaskName());
						stuTemporary.setClassId(stuLeave.getClassId());
						stuTemporary.setStudentId(stuLeave.getStudentId());
						stuTemporary.setUnitId(stuLeave.getUnitId());
						Student stu = studentService.getStudent(stuLeave.getStudentId());
						stuTemporary.setStudentName(stu.getStuname());
						BasicClass basicClass = basicClassService.getClass(stuLeave.getClassId());
						stuTemporary.setClassName(basicClass.getClassnamedynamic());
						stuTemporary.setFlowId(stuLeave.getFlowId());
						stuTemporary.setApplyUserId(stuLeave.getApplyUserId());
						stuTemporary.setState(stuLeave.getState());
						User user = userMap.get(stuTemporary.getApplyUserId());
						if(user!=null){
							stuTemporary.setApplyUserName(user.getRealname());
						}else{
							stuTemporary.setApplyUserName("用户已删除");
						}
						list.add(stuTemporary);
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
				list = new ArrayList<OfficeLeavetypeTemporary>();
			}
		}
		else{//已审核
			List<OfficeHwstudentLeave> stuLeaveList = officeHwstudentLeaveService.getAuditedList(
					userId, new String[]{OfficeStuLeaveConstants.OFFCIE_STULEAVE_PASS, 
					OfficeStuLeaveConstants.OFFCIE_STULEAVE_FAILED},"2", page);
			Set<String> leaveIds = new HashSet<String>(); 
			Map<String,OfficeHwstudentLeave> leaveMap = new HashMap<String, OfficeHwstudentLeave>();
			for(OfficeHwstudentLeave leave : stuLeaveList){
				leaveIds.add(leave.getId());
				leaveMap.put(leave.getId(), leave);
			}
			if(CollectionUtils.isNotEmpty(stuLeaveList)){
				list = officeLeavetypeTemporaryDao.findByLeaveIds(leaveIds.toArray(new String[0]));
				Set<String> userIdSet = new HashSet<String>();
				for(OfficeLeavetypeTemporary stuTemporary : list){
					OfficeHwstudentLeave stuLeave = leaveMap.get(stuTemporary.getLeaveId());
					stuTemporary.setClassId(stuLeave.getClassId());
					stuTemporary.setStudentId(stuLeave.getStudentId());
					stuTemporary.setUnitId(stuLeave.getUnitId());
					Student stu = studentService.getStudent(stuLeave.getStudentId());
					stuTemporary.setStudentName(stu.getStuname());
					BasicClass basicClass = basicClassService.getClass(stuLeave.getClassId());
					stuTemporary.setClassName(basicClass.getClassnamedynamic());
					stuTemporary.setFlowId(stuLeave.getFlowId());
					stuTemporary.setApplyUserId(stuLeave.getApplyUserId());
					stuTemporary.setState(stuLeave.getState());
					userIdSet.add(stuTemporary.getApplyUserId());
				}
				Map<String,User> userMap = new HashMap<String, User>();
				userMap = userService.getUserWithDelMap(userIdSet.toArray(new String[0]));
				for(OfficeLeavetypeTemporary e : list){
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
	private List<OfficeLeavetypeTemporary> getListByUnitId(String unitId) {
		List<OfficeHwstudentLeave> stuLeaveList = officeHwstudentLeaveService.getOfficeHwstudentLeaveByUnitIdList(unitId);
		Set<String> leaveIds = new HashSet<String>();
		Map<String,OfficeHwstudentLeave> stuLeaveMap = new HashMap<String, OfficeHwstudentLeave>();
		for(OfficeHwstudentLeave stuLeave : stuLeaveList){
			leaveIds.add(stuLeave.getId());
			stuLeaveMap.put(stuLeave.getId(), stuLeave);
		}
		List<OfficeLeavetypeTemporary> list = officeLeavetypeTemporaryDao.findByLeaveIds(leaveIds.toArray(new String[0]));
		for(OfficeLeavetypeTemporary temporary : list){
			OfficeHwstudentLeave stuLeave = stuLeaveMap.get(temporary.getLeaveId());
			if(stuLeave!=null && temporary != null){
				temporary.setClassId(stuLeave.getClassId());
				temporary.setStudentId(stuLeave.getStudentId());
				temporary.setUnitId(stuLeave.getUnitId());
				Student stu = studentService.getStudent(stuLeave.getStudentId());
				temporary.setStudentName(stu.getStuname());
				BasicClass basicClass = basicClassService.getClass(stuLeave.getClassId());
				temporary.setClassName(basicClass.getClassnamedynamic());
				temporary.setFlowId(stuLeave.getFlowId());
				temporary.setApplyUserId(stuLeave.getApplyUserId());
				temporary.setState(stuLeave.getState());
			}
		}
		return list;
	}
	
}
