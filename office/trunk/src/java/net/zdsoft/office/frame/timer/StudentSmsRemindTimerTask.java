package net.zdsoft.office.frame.timer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.entity.CurrentSemester;
import net.zdsoft.eis.base.common.entity.Family;
import net.zdsoft.eis.base.common.entity.Semester;
import net.zdsoft.eis.base.common.entity.Student;
import net.zdsoft.eis.base.common.entity.SystemIni;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.service.BasicClassService;
import net.zdsoft.eis.base.common.service.SemesterService;
import net.zdsoft.eis.base.common.service.StudentFamilyService;
import net.zdsoft.eis.base.common.service.StudentService;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keelcnet.config.ContainerManager;
import net.zdsoft.office.studentLeave.entity.OfficeStudentLeave;
import net.zdsoft.office.studentLeave.service.OfficeStudentLeaveService;
import net.zdsoft.office.studentcard.entity.StudentCardRecord;
import net.zdsoft.office.studentcard.service.StudentCardRecordService;
import net.zdsoft.smsplatform.client.SendResult;
import net.zdsoft.smsplatform.client.ZDResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class StudentSmsRemindTimerTask extends QuartzJobBean {
	Logger log = LoggerFactory.getLogger(StudentSmsRemindTimerTask.class);
	StudentService studentService = (StudentService) ContainerManager
			.getComponent("studentService");
	BasicClassService basicClassService = (BasicClassService) ContainerManager
			.getComponent("basicClassService");
	StudentFamilyService studentFamilyService = (StudentFamilyService) ContainerManager
			.getComponent("studentFamilyService");
	TeacherService teacherService = (TeacherService) ContainerManager
			.getComponent("teacherService");
	OfficeStudentLeaveService officeStudentLeaveService = (OfficeStudentLeaveService) ContainerManager
			.getComponent("officeStudentLeaveService");
	// 宁海一卡通数据
	StudentCardRecordService studentCardRecordService = (StudentCardRecordService) ContainerManager
			.getComponent("studentCardRecordService");
	SemesterService semesterService = (SemesterService) ContainerManager
			.getComponent("semesterService");
	SystemIniService systemIniService = (SystemIniService) ContainerManager
			.getComponent("systemIniService");

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		SystemIni ini = systemIniService.getSystemIni(BaseConstant.SYSTEM_DEPLOY_SCHOOL);
		if(ini != null && BaseConstant.SYS_DEPLOY_SCHOOL_NHZG.equals(ini.getNowValue())){
			CurrentSemester currentSemester = semesterService.getCurrentSemester();
			Semester semester = semesterService.getSemester(
					currentSemester.getAcadyear(), currentSemester.getSemester());
	
			// 获取当前学年学期工作时间内的进校打卡数据
			List<StudentCardRecord> studentCardRecords = studentCardRecordService
					.findStudentCardRecordbyTimes(semester.getWorkBegin(),
							semester.getWorkEnd(), StudentCardRecord.IN_SCHOOL);
			List<OfficeStudentLeave> officeStudentLeaves = officeStudentLeaveService
					.getOfficeStudentLeaveList();
			//设置back_state
			if (CollectionUtils.isNotEmpty(officeStudentLeaves)) {
				// 是否有一卡通的进校记录
				if (CollectionUtils.isNotEmpty(studentCardRecords)) {
					Set<String> unBackStudentIdSet = new HashSet<String>();
					List<OfficeStudentLeave> unBackStudentLeaveList = new ArrayList<OfficeStudentLeave>();
					for (OfficeStudentLeave leave : officeStudentLeaves) {
						boolean flag = false;
						for (StudentCardRecord record : studentCardRecords) {
							if (leave.getStudentId().equals(record.getStudentId())) {
								if (leave.getStartTime().before(
										record.getRefshCardDate())
										&& record.getRefshCardDate().before(
												leave.getEndTime())) {
									flag = true;
									break;
								}
							}
						}
						if (flag) {
							leave.setBackState(OfficeStudentLeave.HAS_BACK_SCHOOL);
						} else {
							leave.setBackState(OfficeStudentLeave.UN_BACK_SCHOOL);
							unBackStudentIdSet.add(leave.getStudentId());
							unBackStudentLeaveList.add(leave);
						}
					}
					if (unBackStudentIdSet.size() > 0) {
						sendSms(unBackStudentLeaveList,unBackStudentIdSet.toArray(new String[0]));
					}
				} else {
					Set<String> unBackStudentIdSet = new HashSet<String>();
					for (OfficeStudentLeave leave : officeStudentLeaves) {
						unBackStudentIdSet.add(leave.getStudentId());
						leave.setBackState(OfficeStudentLeave.UN_BACK_SCHOOL);
					}
					if (unBackStudentIdSet.size() > 0) {
						sendSms(officeStudentLeaves,unBackStudentIdSet.toArray(new String[0]));
					}
				}
				officeStudentLeaveService.batchUpdateBackState(officeStudentLeaves);
			}
		}
	}

	/**
	 * TODO 推送短信到家长及班主任
	 * 
	 * @param studentIds
	 */
	public void sendSms(List<OfficeStudentLeave> leaveList,String[] studentIds) {
		List<Student> studentList = studentService
				.getStudentsByIdsWithDeleted(studentIds);
		Set<String> classIdSet = new HashSet<String>();
		Map<String, Student> studentMap = new HashMap<String, Student>();
		for (Student student : studentList) {
			classIdSet.add(student.getClassid());
			studentMap.put(student.getId(), student);
		}
		Map<String, BasicClass> basicClassMap = basicClassService
				.getClassMap(classIdSet.toArray(new String[0]));
		Set<String> teacherIdSet = new HashSet<String>();
		for (BasicClass basicClass : basicClassMap.values()) {
			teacherIdSet.add(basicClass.getTeacherid());
		}
		Map<String, Teacher> teacherMap = teacherService.getTeacherMap(teacherIdSet.toArray(new String[0]));
		Map<String, List<Family>> familyListMap = studentFamilyService.getFamilyMap(studentIds);
		
		for (OfficeStudentLeave leave : leaveList) {
			Student student = studentMap.get(leave.getStudentId());
			String smsContent = student.getStuname()+"在"
			+DateUtils.date2String(leave.getStartTime(),"yyyy-MM-dd")+"到"
			+DateUtils.date2String(leave.getEndTime(),"yyyy-MM-dd")+"期间请假没有按时归校，特此告知";
			JSONObject json = new JSONObject();
	        json.put("msg", smsContent);
	        JSONArray receivers = new JSONArray();
			BasicClass basicClass = basicClassMap.get(student.getClassid());
			if(StringUtils.isNotBlank(basicClass.getTeacherid())){
				Teacher teacher = teacherMap.get(basicClass.getTeacherid());
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
			List<Family> familys = familyListMap.get(leave.getStudentId());
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
				try {
					SendResult sr = ZDResponse.post(json);
					System.out.println("--学生未归校发送短信--"+sr.getDescription());
				} catch (Exception e) {
					System.out.println("--学生未归校发送短信失败--");
				}
			}
		}
	}
}
