package net.zdsoft.office.dailyoffice.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.subsystemcall.entity.EduadmCourseDto;
import net.zdsoft.eis.base.subsystemcall.service.EduadmSubsystemService;
import net.zdsoft.eis.frame.action.PageSemesterAction;
import net.zdsoft.eisu.base.common.entity.TeachPlace;
import net.zdsoft.eisu.base.common.service.TeachPlaceService;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.office.dailyoffice.dto.AttendanceDto;
import net.zdsoft.office.dailyoffice.dto.AttendanceRadioDto;
import net.zdsoft.office.dailyoffice.dto.OfficeAttendanceDoorRecordDto;
import net.zdsoft.office.dailyoffice.entity.OfficeAttendanceDoorRecord;
import net.zdsoft.office.dailyoffice.service.OfficeAttendanceDoorRecordService;
import net.zdsoft.office.dailyoffice.service.OfficeAttendanceService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

public class AttendanceAction extends PageSemesterAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1925451644529924706L;

	private List<EduadmCourseDto> eduadmCourseDtoList = new ArrayList<EduadmCourseDto>();
	private List<BasicClass> classList = new ArrayList<BasicClass>();
	private List<AttendanceDto> attendanceDtoList = new ArrayList<AttendanceDto>();
	private List<TeachPlace> teachPlaceList = new ArrayList<TeachPlace>();
	private TeachPlace teachPlace = new TeachPlace();
	private List<Teacher> teacherList = new ArrayList<Teacher>();
	private Teacher teacher;
	private List<AttendanceRadioDto> attendanceRadioDtoList = new ArrayList<AttendanceRadioDto>();
	private List<OfficeAttendanceDoorRecord> officeAttendanceDoorRecordList = new ArrayList<OfficeAttendanceDoorRecord>();
	private List<OfficeAttendanceDoorRecordDto> officeAttendanceDoorRecordDtoList = new ArrayList<OfficeAttendanceDoorRecordDto>();
	private EduadmSubsystemService eduadmSubsystemService;
	private CustomRoleService customRoleService;
	private CustomRoleUserService customRoleUserService;
	private OfficeAttendanceService officeAttendanceService;
	private TeachPlaceService teachPlaceService;
	private TeacherService teacherService;
	private OfficeAttendanceDoorRecordService officeAttendanceDoorRecordService;
	private String acadyear;
	private String semester;
	private String courseId;

	private String id;
	private Date queryBeginDate;
	private Date queryEndTime;
	private String dateStr;
	private String startPeriod;
	private String endPeriod;
	private String classId;
	private String teachClassId;
	private boolean admin = false;
	public static final String ROOM_ORDER_AUDIT = "attendance_manage";// 考勤管理权限

	@Override
	public String execute() throws Exception {
		// 只获取选修课的
		if (isAdmin()) {
			eduadmCourseDtoList = eduadmSubsystemService.getEduadmCourse(
					getUnitId(), getAcadyear(), getSemester(), null, "3");
		} else {
			eduadmCourseDtoList = eduadmSubsystemService.getEduadmCourse(
					getUnitId(), getAcadyear(), getSemester(), getLoginInfo().getUser()
							.getTeacherid(), "3");
		}
		return SUCCESS;
	}

	public String list() {
		attendanceDtoList = officeAttendanceService.getAttendanceDtoList(
				getUnitId(), courseId);
		return SUCCESS;
	}

	public String detail() {
		String[] dateStrArray = dateStr.split("-");
		attendanceRadioDtoList = officeAttendanceService
				.getAttendanceRadioDtoList(getUnitId(), courseId,
						dateStrArray[0] + dateStrArray[1] + dateStrArray[2],
						Integer.parseInt(startPeriod),
						Integer.parseInt(endPeriod));
		return SUCCESS;
	}

	public String radio() {
		// 只获取选修课的
		if (isAdmin()) {
			eduadmCourseDtoList = eduadmSubsystemService.getEduadmCourse(
					getUnitId(), getAcadyear(), getSemester(), null, "3");
		} else {
			eduadmCourseDtoList = eduadmSubsystemService.getEduadmCourse(
					getUnitId(), getAcadyear(), getSemester(), getLoginInfo().getUser()
							.getTeacherid(), "3");
		}
		return SUCCESS;
	}

	public String query() {
		teachPlaceList = teachPlaceService.getTeachPlacesByFaintness(
				getUnitId(), null, null, "3");
		return SUCCESS;
	}

	public String radioList() {
		if (StringUtils.isBlank(classId)) {
			attendanceRadioDtoList = officeAttendanceService
					.getAttendanceRadioDtoList(getUnitId(), courseId);
		} else {
			attendanceRadioDtoList = officeAttendanceService
					.getAttendanceRadioDtoList(getUnitId(), getAcadyear(),
							getSemester(), classId);
		}
		return SUCCESS;
	}

	public String queryList() {
		if(StringUtils.isNotEmpty(id)){
				TeachPlace teacherPlace=teachPlaceService.getTeachPlace(id);
				String  controllerID=teacherPlace.getControllerID();
			  if(StringUtils.isNotEmpty(controllerID)){
				   officeAttendanceDoorRecordList = officeAttendanceDoorRecordService
							.getOfficeAttendanceDoorRecordByControllerID(controllerID,
									queryBeginDate, queryEndTime, getPage());
					if (officeAttendanceDoorRecordList != null
							&& !officeAttendanceDoorRecordList.isEmpty()) {
						for (OfficeAttendanceDoorRecord officeA : officeAttendanceDoorRecordList) {
							OfficeAttendanceDoorRecordDto officeAttendanceDoorRecordDto = new OfficeAttendanceDoorRecordDto();
							if (StringUtils.isNotEmpty(officeA.getTeacherCode())) {
								teacherList = teacherService.getTeachersByFaintness(
										getUnitId(), null, officeA.getTeacherCode());
								if (teacherList != null && !teacherList.isEmpty()) {
									teacher = teacherList.get(0);
									officeAttendanceDoorRecordDto.setTeacherName(teacher
											.getName());
								}
							}
							if (StringUtils.isNotEmpty(officeA.getControllerId())) {
								teachPlace = teachPlaceService.getTeachPlaceByControllerID(
										getUnitId(), officeA.getControllerId());
								officeAttendanceDoorRecordDto.setPlaceName(teachPlace
										.getPlaceName());
							}
							String dataString3 = officeA.getDataDate().concat(
									officeA.getDataTime());
							Date data = DateUtils
									.string2Date(dataString3, "yyyyMMddHHmmss");
							String dataString = DateUtils.date2String(data,
									"yyyy-MM-dd HH:mm:ss");
							officeAttendanceDoorRecordDto.setOpenTime(dataString);
							officeAttendanceDoorRecordDtoList
									.add(officeAttendanceDoorRecordDto);
						}
					}
			   }else officeAttendanceDoorRecordDtoList=null;return SUCCESS;
		}else{
			 officeAttendanceDoorRecordList = officeAttendanceDoorRecordService
						.getOfficeAttendanceDoorRecordByControllerID(null,
								queryBeginDate, queryEndTime, getPage());
				if (officeAttendanceDoorRecordList != null
						&& !officeAttendanceDoorRecordList.isEmpty()) {
					for (OfficeAttendanceDoorRecord officeA : officeAttendanceDoorRecordList) {
						OfficeAttendanceDoorRecordDto officeAttendanceDoorRecordDto = new OfficeAttendanceDoorRecordDto();
						if (StringUtils.isNotEmpty(officeA.getTeacherCode())) {
							teacherList = teacherService.getTeachersByFaintness(
									getUnitId(), null, officeA.getTeacherCode());
							if (teacherList != null && !teacherList.isEmpty()) {
								teacher = teacherList.get(0);
								officeAttendanceDoorRecordDto.setTeacherName(teacher
										.getName());
							}
						}
						if (StringUtils.isNotEmpty(officeA.getControllerId())) {
							teachPlace = teachPlaceService.getTeachPlaceByControllerID(
									getUnitId(), officeA.getControllerId());
							officeAttendanceDoorRecordDto.setPlaceName(teachPlace
									.getPlaceName());
						}
						String dataString3 = officeA.getDataDate().concat(
								officeA.getDataTime());
						Date data = DateUtils
								.string2Date(dataString3, "yyyyMMddHHmmss");
						String dataString = DateUtils.date2String(data,
								"yyyy-MM-dd HH:mm:ss");
						officeAttendanceDoorRecordDto.setOpenTime(dataString);
						officeAttendanceDoorRecordDtoList
								.add(officeAttendanceDoorRecordDto);
					}
				}
		}
		return SUCCESS;
	}

	public boolean isAdmin() {
		admin = isPracticeAdmin(ROOM_ORDER_AUDIT);
		return admin;
	}

	/**
	 * 判断其是否为各种管理员
	 */
	private boolean isPracticeAdmin(String str) {
		CustomRole role = customRoleService.getCustomRoleByRoleCode(
				getUnitId(), str);
		boolean flag;
		if (role == null) {
			flag = false;
			return flag;
		}
		List<CustomRoleUser> roleUs = customRoleUserService
				.getCustomRoleUserListByUserId(getLoginUser().getUserId());
		if (CollectionUtils.isNotEmpty(roleUs)) {
			for (CustomRoleUser ru : roleUs) {
				if (StringUtils.equals(ru.getRoleId(), role.getId())) {
					flag = true;
					return flag;
				}
			}
		}
		flag = false;
		return flag;
	}

	public String getAcadyear() {
		if (StringUtils.isBlank(acadyear)) {
			return this.getCurrentSemester().getAcadyear();
		}
		return acadyear;
	}

	public void setAcadyear(String acadyear) {
		this.acadyear = acadyear;
	}

	public String getSemester() {
		if (StringUtils.isBlank(semester)) {
			return this.getCurrentSemester().getSemester();
		}
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public void setEduadmSubsystemService(
			EduadmSubsystemService eduadmSubsystemService) {
		this.eduadmSubsystemService = eduadmSubsystemService;
	}

	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	}

	public void setCustomRoleUserService(
			CustomRoleUserService customRoleUserService) {
		this.customRoleUserService = customRoleUserService;
	}

	public List<EduadmCourseDto> getEduadmCourseDtoList() {
		return eduadmCourseDtoList;
	}

	public void setEduadmCourseDtoList(List<EduadmCourseDto> eduadmCourseDtoList) {
		this.eduadmCourseDtoList = eduadmCourseDtoList;
	}

	public void setOfficeAttendanceService(
			OfficeAttendanceService officeAttendanceService) {
		this.officeAttendanceService = officeAttendanceService;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public List<AttendanceDto> getAttendanceDtoList() {
		return attendanceDtoList;
	}

	public void setAttendanceDtoList(List<AttendanceDto> attendanceDtoList) {
		this.attendanceDtoList = attendanceDtoList;
	}

	public List<AttendanceRadioDto> getAttendanceRadioDtoList() {
		return attendanceRadioDtoList;
	}

	public void setAttendanceRadioDtoList(
			List<AttendanceRadioDto> attendanceRadioDtoList) {
		this.attendanceRadioDtoList = attendanceRadioDtoList;
	}

	public List<BasicClass> getClassList() {
		return classList;
	}

	public void setClassList(List<BasicClass> classList) {
		this.classList = classList;
	}

	public String getTeacherId() {
		return getLoginInfo().getUser().getTeacherid();
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getTeachClassId() {
		return teachClassId;
	}

	public void setTeachClassId(String teachClassId) {
		this.teachClassId = teachClassId;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public String getStartPeriod() {
		return startPeriod;
	}

	public void setStartPeriod(String startPeriod) {
		this.startPeriod = startPeriod;
	}

	public String getEndPeriod() {
		return endPeriod;
	}

	public void setEndPeriod(String endPeriod) {
		this.endPeriod = endPeriod;
	}

	public void setOfficeAttendanceDoorRecordList(
			List<OfficeAttendanceDoorRecord> officeAttendanceDoorRecordList) {
		this.officeAttendanceDoorRecordList = officeAttendanceDoorRecordList;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setQueryBeginDate(Date queryBeginDate) {
		this.queryBeginDate = queryBeginDate;
	}

	public void setQueryEndTime(Date queryEndTime) {
		this.queryEndTime = queryEndTime;
	}

	public TeachPlace getTeachPlace() {
		return teachPlace;
	}

	public void setTeachPlace(TeachPlace teachPlace) {
		this.teachPlace = teachPlace;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public List<Teacher> getTeacherList() {
		return teacherList;
	}

	public void setTeacherList(List<Teacher> teacherList) {
		this.teacherList = teacherList;
	}

	public void setOfficeAttendanceDoorRecordService(
			OfficeAttendanceDoorRecordService officeAttendanceDoorRecordService) {
		this.officeAttendanceDoorRecordService = officeAttendanceDoorRecordService;
	}

	public List<TeachPlace> getTeachPlaceList() {
		return teachPlaceList;
	}

	public void setTeachPlaceList(List<TeachPlace> teachPlaceList) {
		this.teachPlaceList = teachPlaceList;
	}

	public void setTeachPlaceService(TeachPlaceService teachPlaceService) {
		this.teachPlaceService = teachPlaceService;
	}

	public List<OfficeAttendanceDoorRecordDto> getOfficeAttendanceDoorRecordDtoList() {
		return officeAttendanceDoorRecordDtoList;
	}

	public void setOfficeAttendanceDoorRecordDtoList(
			List<OfficeAttendanceDoorRecordDto> officeAttendanceDoorRecordDtoList) {
		this.officeAttendanceDoorRecordDtoList = officeAttendanceDoorRecordDtoList;
	}

}
