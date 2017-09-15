package net.zdsoft.office.dailyoffice.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.entity.Student;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.BasicClassService;
import net.zdsoft.eis.base.common.service.StudentService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.subsystemcall.entity.EduadmCourseDto;
import net.zdsoft.eis.base.subsystemcall.entity.EduadmTeachClassStuDto;
import net.zdsoft.eis.base.subsystemcall.service.EduadmSubsystemService;
import net.zdsoft.eisu.base.common.entity.TeachPlace;
import net.zdsoft.eisu.base.common.service.TeachPlaceService;
import net.zdsoft.office.dailyoffice.dto.AttendanceDto;
import net.zdsoft.office.dailyoffice.dto.AttendanceRadioDto;
import net.zdsoft.office.dailyoffice.entity.OfficeAttendanceStuRecord;
import net.zdsoft.office.dailyoffice.service.OfficeAttendanceService;
import net.zdsoft.office.dailyoffice.service.OfficeAttendanceStuRecordService;
import net.zdsoft.office.dailyoffice.service.OfficeUtilityApplyService;
import net.zdsoft.office.dailyoffice.sync.constant.OfficeSyncKqConstant;

public class OfficeAttendanceServiceImpl implements OfficeAttendanceService {
	private OfficeUtilityApplyService officeUtilityApplyService;
	private UserService userService;
	private BasicClassService basicClassService;
	private EduadmSubsystemService eduadmSubsystemService;
	private TeachPlaceService teachPlaceService;
	private StudentService studentService;
	private OfficeAttendanceStuRecordService officeAttendanceStuRecordService;
	
	
	@Override
	public List<AttendanceDto> getAttendanceDtoList(String unitId,
			String courseId) {
		List<AttendanceDto> dtoList = officeUtilityApplyService.getCourseArrange(unitId, courseId);
		
		Map<String, User> userMap = userService.getUserMap(unitId);
		Map<String, BasicClass> classMap = basicClassService.getClassMap(unitId);
		for(AttendanceDto dto : dtoList) {
			EduadmCourseDto courseDto = eduadmSubsystemService.getEduadmCourseById(dto.getCourseId());
			dto.setTeacherName(userMap.get(dto.getTeacherId()).getRealname());
			if(courseDto.getStudyType().equals("1")) {
				dto.setClassName(classMap.get(courseDto.getClassId()).getClassname());
			}else {
				String className = eduadmSubsystemService.getEduadmTeachClassName(courseDto.getClassId());
				dto.setClassName(className);
			}
			TeachPlace teachPlace = teachPlaceService.getTeachPlace(dto.getRoomId());
			dto.setRoomName(teachPlace.getPlaceName());
			String dateStr = dto.getDateStr().split("-")[0] + dto.getDateStr().split("-")[1] + dto.getDateStr().split("-")[2];
			int allCount = officeAttendanceStuRecordService.getCount(dto.getCourseId(), dateStr, dto.getStartPeriod(), dto.getEndPeriod(), null);
			int dkCount = officeAttendanceStuRecordService.getCount(dto.getCourseId(), dateStr, dto.getStartPeriod(), dto.getEndPeriod(), OfficeSyncKqConstant.IS_DK_YES);
		    dto.setAttendanceInfo(dkCount+"/"+allCount);
		}
		
		Collections.sort(dtoList, new Comparator<AttendanceDto>() {

			@Override
			public int compare(AttendanceDto o1, AttendanceDto o2) {
				
					if(o1.getDateStr().compareTo(o2.getDateStr()) == 0) {
						if(o1.getSortNum() == o2.getSortNum()) {
							return o1.getRoomId().compareTo(o2.getRoomId());
						}else {
							return o1.getSortNum() - o2.getSortNum();
						}
					}else {
						return o1.getDateStr().compareTo(o2.getDateStr());
					}
				}
		});
		return dtoList;
	}
	
	@Override
	public List<AttendanceRadioDto> getAttendanceRadioDtoList(String unitId,
			String courseId) {
		EduadmCourseDto courseDto = eduadmSubsystemService.getEduadmCourseById(courseId);
		List<AttendanceRadioDto> attendanceRadioDtoList = new ArrayList<AttendanceRadioDto>();
		Map<String, BasicClass> classMap = basicClassService.getClassMap(unitId);
		List<Student> stuList = null;
		if(courseDto.getStudyType().equals("1")) {
			stuList = studentService.getStudents(courseDto.getClassId());
		}else {
			stuList = eduadmSubsystemService.getStudentByTeachClassId(courseDto.getClassId());
		}
		List<String> stuCodeList = new ArrayList<String>();
		for(Student student : stuList) {
			stuCodeList.add(student.getStucode());
			AttendanceRadioDto dto = new AttendanceRadioDto();
			dto.setStuId(student.getId());
			dto.setStuCode(student.getStucode());
			dto.setStuName(student.getStuname());
			dto.setSexStr(student.getSex()==1? "男" : "女");
			dto.setClassName(classMap.get(student.getClassid()).getClassname());
			dto.setCourseName(courseDto.getSubjectName());
			attendanceRadioDtoList.add(dto);
		}
		List<AttendanceDto> attendanceDtoList = officeUtilityApplyService.getCourseArrange(unitId, courseId);
		Map<String, Integer> stuAttendanceCountMap = officeAttendanceStuRecordService.getStuAttendanceCountMap(courseId);
		for(AttendanceRadioDto dto : attendanceRadioDtoList) {
			int attendanceCount = 0;
			if(stuAttendanceCountMap.containsKey(dto.getStuCode())) {
				attendanceCount = stuAttendanceCountMap.get(dto.getStuCode());
			}
			dto.setRadioInfo(attendanceCount + "/" + attendanceDtoList.size());
		}
		return attendanceRadioDtoList;
	}
	
	@Override
	public List<AttendanceRadioDto> getAttendanceRadioDtoList(String unitId,
			String acadyear, String semester, String classId) {
		BasicClass basicClass = basicClassService.getClass(classId);
		List<AttendanceRadioDto> attendanceRadioDtoList = new ArrayList<AttendanceRadioDto>();
		List<Student> stuList = studentService.getStudents(classId);
		Map<String, Student> studentMap = new HashMap<String, Student>();
		List<String> stuIdList = new ArrayList<String>();
		List<String> stuCodeList = new ArrayList<String>();
		for(Student student : stuList) {
			studentMap.put(student.getId(), student);
			stuIdList.add(student.getId());
			stuCodeList.add(student.getStucode());
		}
		List<EduadmTeachClassStuDto> teachClassStuList = new ArrayList<EduadmTeachClassStuDto>();
		if(stuIdList.size() > 0) {
			teachClassStuList = eduadmSubsystemService.getEduadmTeachClassStu(
					unitId, acadyear, semester, 1, stuIdList.toArray(new String[] {}), null);
		}
		Map<String, Integer> sjCountMap = officeAttendanceStuRecordService.getStuAttendanceCountMap(stuCodeList.toArray(new String[] {})); //学生实际上课次数
		Map<String, Integer> ygCountMap = new HashMap<String, Integer>(); //课程应该上课次数
		Map<String, String> courseNameMap = new HashMap<String, String>();
		for(EduadmTeachClassStuDto teachClassStu : teachClassStuList) {
			if(!ygCountMap.containsKey(teachClassStu.getCourseId())) {
				List<AttendanceDto> attendanceDtoList = officeUtilityApplyService.getCourseArrange(unitId, teachClassStu.getCourseId());
				ygCountMap.put(teachClassStu.getCourseId(), attendanceDtoList.size());
			}
			if(!courseNameMap.containsKey(teachClassStu.getCourseId())) {
				EduadmCourseDto dto = eduadmSubsystemService.getEduadmCourseById(teachClassStu.getCourseId());
				courseNameMap.put(teachClassStu.getCourseId(), dto.getSubjectName());
			}
			AttendanceRadioDto dto = new AttendanceRadioDto();
			Student student = studentMap.get(teachClassStu.getStudentId());
			dto.setStuId(student.getId());
			dto.setStuCode(student.getStucode());
			dto.setStuName(student.getStuname());
			dto.setSexStr(student.getSex()==1? "男" : "女");
			dto.setClassName(basicClass.getClassname());
			dto.setCourseName(courseNameMap.get(teachClassStu.getCourseId()));
			int sjCount = 0;
			if(sjCountMap.containsKey(student.getStucode()+"-"+teachClassStu.getCourseId())) {
				sjCount = sjCountMap.get(student.getStucode()+"-"+teachClassStu.getCourseId());
			}
			int ygCount = ygCountMap.get(teachClassStu.getCourseId());
			dto.setRadioInfo(sjCount+"/"+ygCount);
			attendanceRadioDtoList.add(dto);
		}
		Collections.sort(attendanceRadioDtoList, new Comparator<AttendanceRadioDto>() {

			@Override
			public int compare(AttendanceRadioDto o1, AttendanceRadioDto o2) {
				return o1.getStuId().compareTo(o2.getStuId());
			}
		});
		return attendanceRadioDtoList;
	}
	
	@Override
	public List<AttendanceRadioDto> getAttendanceRadioDtoList(String unitId,
			String courseId, String dateStr, int startPeriod, int endPeriod) {
		List<OfficeAttendanceStuRecord> list = officeAttendanceStuRecordService.getList(courseId, dateStr, startPeriod, endPeriod, null);
		List<AttendanceRadioDto> dtoList = new ArrayList<AttendanceRadioDto>();
		Map<String, BasicClass> classMap = basicClassService.getClassMap(unitId);
		Map<String,String> stuCodeMap = new HashMap<String, String>();
		for(OfficeAttendanceStuRecord entity : list) {
			//过滤掉重复打卡的
			if(stuCodeMap.containsKey(entity.getStudentCode())) {
				continue;
			}else {
				stuCodeMap.put(entity.getStudentCode(), entity.getStudentCode());
			}
			Student student = studentService.getStudentBy2Code(unitId, entity.getStudentCode());
			AttendanceRadioDto dto = new AttendanceRadioDto();
			dto.setStuCode(entity.getStudentCode());
			dto.setStuName(student.getStuname());
			dto.setSexStr(student.getSex()==1? "男" : "女");
			dto.setClassName(classMap.get(student.getClassid()).getClassname());
			dto.setRadioInfo(entity.getIsDk().equals("1") ? "是":"否");
			dtoList.add(dto);
		}
		return dtoList;
	}

	public void setOfficeUtilityApplyService(
			OfficeUtilityApplyService officeUtilityApplyService) {
		this.officeUtilityApplyService = officeUtilityApplyService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setBasicClassService(BasicClassService basicClassService) {
		this.basicClassService = basicClassService;
	}

	public void setEduadmSubsystemService(
			EduadmSubsystemService eduadmSubsystemService) {
		this.eduadmSubsystemService = eduadmSubsystemService;
	}

	public void setTeachPlaceService(TeachPlaceService teachPlaceService) {
		this.teachPlaceService = teachPlaceService;
	}

	public void setOfficeAttendanceStuRecordService(
			OfficeAttendanceStuRecordService officeAttendanceStuRecordService) {
		this.officeAttendanceStuRecordService = officeAttendanceStuRecordService;
	}

	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}
}
