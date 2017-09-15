package net.zdsoft.office.dailyoffice.service;

import java.util.List;

import net.zdsoft.office.dailyoffice.dto.AttendanceDto;
import net.zdsoft.office.dailyoffice.dto.AttendanceRadioDto;

public interface OfficeAttendanceService {
	
	/**
	 * 获取该课程的所有课程安排上课情况
	 * @param unitId
	 * @param courseId
	 * @return
	 */
	public List<AttendanceDto> getAttendanceDtoList(String unitId, String courseId); 
	
	/**
	 * 获取该课程学生到课情况
	 * @param unitId
	 * @param courseId
	 * @return
	 */
	public List<AttendanceRadioDto> getAttendanceRadioDtoList(String unitId, String courseId);
	
	/**
	 * 获取行政班学生到课情况
	 * @param unitId
	 * @param acadyear
	 * @param semester
	 * @param classId
	 * @return
	 */
	public List<AttendanceRadioDto> getAttendanceRadioDtoList(String unitId, String acadyear, String semester, String classId);
	
	/**
	 * 获取课程的学生刷卡情况
	 * @param unitId TODO
	 * @param courseId
	 * @param dateStr
	 * @param startPeriod
	 * @param endPeriod
	 * @return
	 */
	public List<AttendanceRadioDto> getAttendanceRadioDtoList(String unitId, String courseId, String dateStr, int startPeriod, int endPeriod);
}
