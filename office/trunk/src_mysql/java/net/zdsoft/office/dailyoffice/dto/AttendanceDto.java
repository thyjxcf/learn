package net.zdsoft.office.dailyoffice.dto;

public class AttendanceDto {
	
	private String courseId;
	private String courseName;
	private String roomId;
	private String roomName;
	private String dateStr;
	private String periodStr;
	private int startPeriod;
	private int endPeriod;
	private String attendanceInfo;
	private String teacherId;
	private String teacherName;
	private int sortNum;
	private String className;
	
	public static int toComparePeriod(int period) {
		if(period == 99) {
			return 5;
		}else if(period >= 5) {
			return period + 1;
		}else{
			return period;
		}
	}
	
	public static int toActualPeriod(int period) {
		if(period == 5) {
			return 99;
		}else if(period >= 6) {
			return period - 1;
		}else{
			return period;
		}
	}
	
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getDateStr() {
		return dateStr;
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	public String getPeriodStr() {
		return periodStr;
	}
	public void setPeriodStr(String periodStr) {
		this.periodStr = periodStr;
	}
	public String getAttendanceInfo() {
		return attendanceInfo;
	}
	public void setAttendanceInfo(String attendanceInfo) {
		this.attendanceInfo = attendanceInfo;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public int getStartPeriod() {
		return startPeriod;
	}
	public void setStartPeriod(int startPeriod) {
		this.startPeriod = startPeriod;
	}
	public int getEndPeriod() {
		return endPeriod;
	}
	public void setEndPeriod(int endPeriod) {
		this.endPeriod = endPeriod;
	}
	public int getSortNum() {
		return sortNum;
	}
	public void setSortNum(int sortNum) {
		this.sortNum = sortNum;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
}
