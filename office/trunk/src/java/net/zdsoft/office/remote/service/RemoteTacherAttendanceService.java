package net.zdsoft.office.remote.service;

/**
 * 教师考勤
 * @author Administrator
 *
 */
public interface RemoteTacherAttendanceService {
	
	/**
	 * 获取考勤信息
	 * @param remoteParam
	 * @return
	 */
	String attendanceDetail(String remoteParam);
	
	/**
	 * 打卡提交
	 * @param remoteParam
	 * @return
	 */
	String attendanceSubmit(String remoteParam);
	
	/**
	 * 补卡申请
	 * @param remoteParam
	 * @return
	 */
	String attendanceApply(String remoteParam);
	
	/**
	 * 考勤统计
	 * @param remoteParam
	 * @return
	 */
	String attendanceCount(String remoteParam);
	
	/**
	 * 考勤月历
	 * @param remoteParam
	 * @return
	 */
	String attendanceMonth(String remoteParam);
	
}
