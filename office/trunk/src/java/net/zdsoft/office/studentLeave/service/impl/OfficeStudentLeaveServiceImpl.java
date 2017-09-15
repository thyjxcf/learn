package net.zdsoft.office.studentLeave.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.entity.Family;
import net.zdsoft.eis.base.common.entity.Student;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.BasicClassService;
import net.zdsoft.eis.base.common.service.StudentFamilyService;
import net.zdsoft.eis.base.common.service.StudentService;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eschool.entity.base.BaseUser;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.studentLeave.dao.OfficeStudentLeaveDao;
import net.zdsoft.office.studentLeave.entity.OfficeLeaveType;
import net.zdsoft.office.studentLeave.entity.OfficeStudentLeave;
import net.zdsoft.office.studentLeave.service.OfficeLeaveTypeService;
import net.zdsoft.office.studentLeave.service.OfficeStudentLeaveService;
import net.zdsoft.office.util.Constants;
import net.zdsoft.smsplatform.client.SendResult;
import net.zdsoft.smsplatform.client.ZDResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
/**
 * office_student_leave
 * @author 
 * 
 */
public class OfficeStudentLeaveServiceImpl implements OfficeStudentLeaveService{
	
	private StudentService studentService;
	private TeacherService teacherService;
	private BasicClassService basicClassService;
	private StudentFamilyService studentFamilyService;
	private OfficeLeaveTypeService officeLeaveTypeService;
	private OfficeStudentLeaveDao officeStudentLeaveDao;
	private UserService userService;
	
	@Override
	public OfficeStudentLeave save(OfficeStudentLeave officeStudentLeave){
		return officeStudentLeaveDao.save(officeStudentLeave);
	}

	@Override
	public Integer delete(String[] ids){
		return officeStudentLeaveDao.delete(ids);
	}

	@Override
	public Integer update(OfficeStudentLeave officeStudentLeave){
		//TODO 发送短信
		if(Constants.APPLY_STATE_PASS == officeStudentLeave.getState()){
			Student student = studentService.getStudent(officeStudentLeave.getStudentId());
			String smsContent = student.getStuname()+"在"
			+DateUtils.date2String(officeStudentLeave.getStartTime(),"yyyy-MM-dd")+"到"
			+DateUtils.date2String(officeStudentLeave.getEndTime(),"yyyy-MM-dd")+"期间请假审核通过，特此告知";
			JSONObject json = new JSONObject();
	        json.put("msg", smsContent);
	        JSONArray receivers = new JSONArray();
	        BasicClass basicClass = basicClassService.getClass(student.getClassid());
			if(StringUtils.isNotBlank(basicClass.getTeacherid())){
				Teacher teacher = teacherService.getTeacher(basicClass.getTeacherid());
				if(StringUtils.isNotBlank(teacher.getPersonTel())){
					JSONObject receiverJ = new JSONObject();
					receiverJ.put("phone", teacher.getPersonTel());
					receiverJ.put("unitName", "");
					receiverJ.put("unitId", teacher.getUnitid());
					receiverJ.put("username", teacher.getName());
					receiverJ.put("userId", "");
					receivers.add(receiverJ);
				}
			}
			List<Family> familys = studentFamilyService.getFamiliesByStudentId(officeStudentLeave.getStudentId());
			if(CollectionUtils.isNotEmpty(familys)){
				for(Family family:familys){
					if(StringUtils.isNotBlank(family.getMobilePhone())){
						JSONObject receiverJ = new JSONObject();
				        receiverJ.put("phone", family.getMobilePhone());
				        receiverJ.put("unitName", "");
				        receiverJ.put("unitId", family.getSchoolId());
				        receiverJ.put("username", family.getName());
				        receiverJ.put("userId", "");
				        receivers.add(receiverJ);
					}
				}
			}
			if(receivers.size() > 0){
				json.put("receivers", receivers);
				SmsThread smsThread = new SmsThread(json);
				smsThread.start();
			}
		}
		return officeStudentLeaveDao.update(officeStudentLeave);
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
				System.out.println("--学生请假审核通过--"+sr.getDescription());
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("--学生请假审核通过发送短信失败--");
			}
 		}
    }
	
	@Override
	public void batchUpdateBackState(
			List<OfficeStudentLeave> officeStudentLeaves) {
		officeStudentLeaveDao.batchUpdateBackState(officeStudentLeaves);
	}

	@Override
	public OfficeStudentLeave getOfficeStudentLeaveById(String id){
		return officeStudentLeaveDao.getOfficeStudentLeaveById(id);
	}

	@Override
	public Map<String, OfficeStudentLeave> getOfficeStudentLeaveMapByIds(String[] ids){
		return officeStudentLeaveDao.getOfficeStudentLeaveMapByIds(ids);
	}

	@Override
	public List<OfficeStudentLeave> getOfficeStudentLeaveList(){
		return officeStudentLeaveDao.getOfficeStudentLeaveList();
	}

	@Override
	public List<OfficeStudentLeave> getOfficeStudentLeavePage(Pagination page){
		return officeStudentLeaveDao.getOfficeStudentLeavePage(page);
	}
	
	@Override
	public List<OfficeStudentLeave> getOfficeStudentLeavePageByParams(
			String createUserName,Date startTime, Date endTime, int state,Pagination page,String unitId) {
		List<OfficeStudentLeave> studentLeaves = officeStudentLeaveDao.getOfficeStudentLeavePageByParams(createUserName,startTime, endTime, state, page,unitId);
		setStudentLeavesDetail(studentLeaves);
		return studentLeaves;
	}
	
	private void setStudentLeavesDetail(List<OfficeStudentLeave> studentLeaveList){//TODO
		Set<String> studentIdSet = new HashSet<String>();
		Set<String> userIdSet=new HashSet<String>();
		Set<String> typeIdSet = new HashSet<String>();
		for(OfficeStudentLeave office:studentLeaveList){
			userIdSet.add(office.getCreateUserId());
			userIdSet.add(office.getInvalidUserId());
			studentIdSet.add(office.getStudentId());
			typeIdSet.add(office.getLeaveTypeId());
		}
		Map<String, Student> studentMap = studentService.getStudentMap(studentIdSet.toArray(new String[0]));
		Map<String,User> userMap=userService.getUserWithDelMap(userIdSet.toArray(new String[0]));
		Map<String, OfficeLeaveType> leaveTypeMap = officeLeaveTypeService.getOfficeLeaveTypeMapByIds(typeIdSet.toArray(new String[0]));
		Set<String> classIdSet=new HashSet<String>();
		for(OfficeStudentLeave office:studentLeaveList){
			office.setLeaveTypeName(leaveTypeMap.get(office.getLeaveTypeId()).getName());
			Student student = studentMap.get(office.getStudentId());
			User user=userMap.get(office.getCreateUserId());
			if(org.apache.commons.lang3.StringUtils.isNotBlank(office.getInvalidUserId())){
				User user2=userMap.get(office.getInvalidUserId());
				if(user2!=null){
					office.setInvalidUserName(user2.getRealname());
				}else{
					office.setInvalidUserName("用户已删除");
				}
			}
			if(student!=null){
				office.setStuName(student.getStuname());
				classIdSet.add(student.getClassid());
			}else{
				office.setStuName("学生已删除");
			}
			if(user!=null){
				office.setCreateUserName(user.getRealname());
			}else{
				office.setCreateUserName("用户已删除");
			}
		}
		Map<String,BasicClass> classMap=basicClassService.getClassMap(classIdSet.toArray(new String[0]));
		for (OfficeStudentLeave office : studentLeaveList) {
			Student student = studentMap.get(office.getStudentId());
			if(student!=null){
				BasicClass basicClass=classMap.get(student.getClassid());
				if(basicClass!=null){
					office.setClassName(basicClass.getClassname());
				}else{
					office.setClassName("班级已删除");
				}
			}
		}
	}

	@Override
	public List<OfficeStudentLeave> getOfficeStudentLeavesByAuditParams(
			Date startTime,Date endTime,int state,Pagination page, String unitId,String createUserName) {
		List<OfficeStudentLeave> studentLeaves = officeStudentLeaveDao.getOfficeStudentLeavesByAuditParams(startTime, endTime, state, page, unitId,createUserName);
		setStudentLeavesDetail(studentLeaves);
		return studentLeaves;
	}


	@Override
	public List<OfficeStudentLeave> getOfficeStudentLeavesByCountParams(
			Date startTime, Date endTime, int state, Pagination page,
			String unitId,String[] leaveIds,String remark,String classId,String gradeId) {
		List<OfficeStudentLeave> studentLeaveList = this.officeStudentLeaveDao.getOfficeStudentLeavesByCountParams(startTime, endTime, state, page, unitId,leaveIds,remark,classId,gradeId);
		setStudentLeavesDetail(studentLeaveList);
		return studentLeaveList;
	}

	public  List<OfficeStudentLeave> findStuIsLeaveBytime(Date date, String[] studentIds) {
		return officeStudentLeaveDao.findStuIsLeaveBytime(date,studentIds);
	}

	@Override
	public Map<String, String> getSumMap(String unitId, Date startTime,
			Date endTime, String classId) {
		return this.officeStudentLeaveDao.getSunMap(unitId, startTime, endTime, classId);
	}

	@Override
	public Map<String, String> getSumGradeMap(String unitId, Date startTime,
			Date endTime) {
		return officeStudentLeaveDao.getSumGradeMap(unitId, startTime, endTime);
	}

	@Override
	public Map<String, String> getSumClassMap(String unitId, String gradeId,
			Date startTime, Date endTime) {
		return officeStudentLeaveDao.getSumClassMap(unitId, gradeId, startTime, endTime);
	}

	@Override
	public Map<String, String> getSumGradeTimeMap(String unitId,
			Date startTime, Date endTime) {
		return officeStudentLeaveDao.getSumGradeTimeMap(unitId, startTime, endTime);
	}

	@Override
	public Map<String, String> getSumClassTimeMap(String unitId,
			String gradeId, Date startTime, Date endTime) {
		return officeStudentLeaveDao.getSumClassTimeMap(unitId, gradeId, startTime, endTime);
	}

	@Override
	public Map<String, String> getSumNameTimeMap(String unitId, String gradeId,
			String classId, Date startTime, Date endTime) {
		return officeStudentLeaveDao.getSumNameTimeMap(unitId, gradeId, classId, startTime, endTime);
	}

	@Override
	public String[] getStuIds(String unitId, Date startTime, Date endTime,
			String classId,String[] leaveTypeIds,String gradeId) {
		return this.officeStudentLeaveDao.getStuIds(unitId, startTime, endTime, classId,leaveTypeIds,gradeId);
	}

	@Override
	public List<OfficeStudentLeave> findStuIsLeaveBytime(Date startTime, Date endTime,
			String[] studentIds) {
		return officeStudentLeaveDao.findStuIsLeaveBytime(startTime,endTime,studentIds);
	}

	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

	public void setOfficeLeaveTypeService(
			OfficeLeaveTypeService officeLeaveTypeService) {
		this.officeLeaveTypeService = officeLeaveTypeService;
	}
	
	public void setOfficeStudentLeaveDao(OfficeStudentLeaveDao officeStudentLeaveDao){
		this.officeStudentLeaveDao = officeStudentLeaveDao;
	}

	@Override
	public boolean isExistConflict(String unitId, String id, String studentId,Date startTime,
			Date endTime) {
		return officeStudentLeaveDao.isExistConflict(unitId, id,studentId, startTime, endTime);
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public void setBasicClassService(BasicClassService basicClassService) {
		this.basicClassService = basicClassService;
	}

	public void setStudentFamilyService(StudentFamilyService studentFamilyService) {
		this.studentFamilyService = studentFamilyService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
