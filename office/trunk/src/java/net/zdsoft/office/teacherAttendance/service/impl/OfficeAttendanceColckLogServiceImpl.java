package net.zdsoft.office.teacherAttendance.service.impl;


import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.office.teacherAttendance.dao.OfficeAttendanceColckLogDao;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceColckLog;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceInfo;
import net.zdsoft.office.teacherAttendance.service.OfficeAttendanceColckLogService;
/**
 * 考勤打卡流水记录表
 * @author 
 * 
 */
public class OfficeAttendanceColckLogServiceImpl implements OfficeAttendanceColckLogService{
	private OfficeAttendanceColckLogDao officeAttendanceColckLogDao;

	@Override
	public OfficeAttendanceColckLog save(OfficeAttendanceColckLog officeAttendanceColckLog){
		return officeAttendanceColckLogDao.save(officeAttendanceColckLog);
	}

	@Override
	public Integer delete(String[] ids){
		return officeAttendanceColckLogDao.delete(ids);
	}

	@Override
	public Integer update(OfficeAttendanceColckLog officeAttendanceColckLog){
		return officeAttendanceColckLogDao.update(officeAttendanceColckLog);
	}

	@Override
	public OfficeAttendanceColckLog getOfficeAttendanceColckLogById(String id){
		return officeAttendanceColckLogDao.getOfficeAttendanceColckLogById(id);
	}
	@Override
	public OfficeAttendanceColckLog saveLogByInfo(OfficeAttendanceInfo officeAttendanceInfo){
		OfficeAttendanceColckLog officeAttendanceColckLog=new OfficeAttendanceColckLog();
		if(officeAttendanceInfo!=null){
			officeAttendanceColckLog.setUnitId(officeAttendanceInfo.getUnitId());
			officeAttendanceColckLog.setUserId(officeAttendanceInfo.getUserId());
			officeAttendanceColckLog.setAttenceDate(officeAttendanceInfo.getAttenceDate());
			officeAttendanceColckLog.setIsHoliday(officeAttendanceInfo.getIsHoliday());
			officeAttendanceColckLog.setType(officeAttendanceInfo.getType());
			officeAttendanceColckLog.setLogType(officeAttendanceInfo.getLogType());
			officeAttendanceColckLog.setClockState(officeAttendanceInfo.getClockState());
			officeAttendanceColckLog.setClockTime(officeAttendanceInfo.getClockTime());
			officeAttendanceColckLog.setAttendanceTime(officeAttendanceInfo.getAttendanceTime());
			officeAttendanceColckLog.setClockPlace(officeAttendanceInfo.getClockPlace());
			officeAttendanceColckLog.setLatitude(officeAttendanceInfo.getLatitude());
			officeAttendanceColckLog.setLongitude(officeAttendanceInfo.getLongitude());
			officeAttendanceColckLog.setRemark(officeAttendanceInfo.getRemark());
			officeAttendanceColckLog.setCreationTime(new Date());
		}
		return officeAttendanceColckLogDao.save(officeAttendanceColckLog);
	}
	
	
	
	public void setOfficeAttendanceColckLogDao(
			OfficeAttendanceColckLogDao officeAttendanceColckLogDao) {
		this.officeAttendanceColckLogDao = officeAttendanceColckLogDao;
	}

	@Override
	public Map<String, String> getMissingCard(Date date, String unitId,
			String attendanceState,Date startDate,Date endDate) {
		// TODO Auto-generated method stub
		return officeAttendanceColckLogDao.getMissingCard(date, unitId, attendanceState,startDate,endDate);
	}

	@Override
	public Map<String, String> getOfficeAttendanceColckLogByDateAndState(
			String state, Date date, String unitId,Date startDate,Date endDate) {
		// TODO Auto-generated method stub
		return officeAttendanceColckLogDao.getOfficeAttendanceColckLogByDateAndState(state, date, unitId,startDate,endDate);
	}

	@Override
	public List<OfficeAttendanceColckLog> listOfficeAttendanceColckLogByDateAndState(
			String unitId,Date startDate,Date endDate ,String state, Date date) {
		// TODO Auto-generated method stub
		return officeAttendanceColckLogDao.listOfficeAttendanceColckLogByDateAndState(unitId,startDate,endDate, state, date);
	}




	
}