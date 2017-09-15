package net.zdsoft.office.dailyoffice.sync.job;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.CurrentSemester;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.SemesterService;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.subsystemcall.entity.StusysSectionTimeSetDto;
import net.zdsoft.eis.base.subsystemcall.service.BaseDataSubsystemService;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keelcnet.config.ContainerManager;
import net.zdsoft.office.dailyoffice.dto.AttendanceDto;
import net.zdsoft.office.dailyoffice.entity.OfficeTimeSet;
import net.zdsoft.office.dailyoffice.service.OfficeAttendanceDoorRecordService;
import net.zdsoft.office.dailyoffice.service.OfficeAttendanceStuRecordService;
import net.zdsoft.office.dailyoffice.service.OfficeTimeSetService;
import net.zdsoft.office.dailyoffice.sync.constant.OfficeSyncKqConstant;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class YKTSyncThread implements Runnable {
	
private static final Log log = LogFactory.getLog(YKTSyncThread.class);
	
	private Boolean isRunning = false;
	private SystemIniService systemIniService = (SystemIniService) ContainerManager.getComponent("systemIniService");
	private UnitService unitService = (UnitService) ContainerManager.getComponent("unitService");
	private BaseDataSubsystemService baseDataSubsystemService = (BaseDataSubsystemService) ContainerManager.getComponent("baseDataSubsystemService");
	private OfficeTimeSetService officeTimeSetService = (OfficeTimeSetService) ContainerManager.getComponent("officeTimeSetService");
	private SemesterService semesterService = (SemesterService) ContainerManager.getComponent("semesterService");
	private OfficeAttendanceStuRecordService officeAttendanceStuRecordService = (OfficeAttendanceStuRecordService) ContainerManager.getComponent("officeAttendanceStuRecordService");
	private OfficeAttendanceDoorRecordService officeAttendanceDoorRecordService = (OfficeAttendanceDoorRecordService) ContainerManager.getComponent("officeAttendanceDoorRecordService");
	
	@Override
	public void run() {
		try {
			boolean isRun = true;
			if(systemIniService.getBooleanValue(OfficeSyncKqConstant.OFFICE_KQ_SYNC_KG)) {
				log.error("获取机房考勤数据：同步线程开启");
				while(isRun){
					try{
						String unitId = getUnitId();
						String acadyear = getAcadyear();
						String semester = getSemester();
						Map<Integer, String> skTimeMap = getSkTimeMap(unitId, acadyear, semester);
						Map<Integer, String> xkTimeMap = getXkTimeMap(unitId, acadyear, semester);
						int currentPeriod = getCurrentPeriod(xkTimeMap);
						if(isNeedSync(currentPeriod)) {
							syncJob(currentPeriod, unitId, acadyear, semester, skTimeMap, xkTimeMap);
						}
						//同步完当天最后一节课 回收同步线程
						if(currentPeriod != 9) {
							int millisecond = getSleepTime(currentPeriod, xkTimeMap);
							Thread.sleep(millisecond);
						}else {
							isRun = false;
						}
					}catch(Exception e){
						log.error(StringUtils.defaultIfEmpty(e.getMessage(), ExceptionUtils.getFullStackTrace(e)));
						//到晚上7点关闭线程
						if(DateUtils.currentHour() == 19) {
							isRun = false;
						}else {
							Thread.sleep(10*60*1000);
						}
					}
				}
			}
		} catch (InterruptedException e) {
			System.out.print("获取机房考勤数据出错：");
			e.printStackTrace();
		}
	}
	
	//通过链接触发的方式（非线程）
	public void unThreadRun(){
		try {
			if(systemIniService.getBooleanValue(OfficeSyncKqConstant.OFFICE_KQ_SYNC_KG)) {
				log.error("----获取机房考勤数据----");
					try{
						String unitId = getUnitId();
						String acadyear = getAcadyear();
						String semester = getSemester();
						Map<Integer, String> skTimeMap = getSkTimeMap(unitId, acadyear, semester);
						Map<Integer, String> xkTimeMap = getXkTimeMap(unitId, acadyear, semester);
						int currentPeriod = getCurrentPeriod(xkTimeMap);
						if(isNeedSync(currentPeriod)) {
							syncJob(currentPeriod, unitId, acadyear, semester, skTimeMap, xkTimeMap);
						}
					}catch(Exception e){
						log.error(StringUtils.defaultIfEmpty(e.getMessage(), ExceptionUtils.getFullStackTrace(e)));
					}
				}
		} catch (Exception e) {
			System.out.print("获取机房考勤数据出错：");
			e.printStackTrace();
		}
	}
	
	public void syncJob(int currentPeriod, String unitId, String acadyear, 
			String semester, Map<Integer, String> skTimeMap, Map<Integer, String> xkTimeMap) {
			if(!isRunning) {
				isRunning = true;
				try{
					//获取机房考勤数据
					try{
						log.error("获取机房考勤数据：开始获取机房考勤数据");
						officeAttendanceStuRecordService.syncAttendanceData(currentPeriod, unitId, acadyear, semester, skTimeMap, xkTimeMap);
						log.error("获取机房考勤数据：结束获取机房考勤数据");
					}catch(Exception e){
						log.error(StringUtils.defaultIfEmpty(e.getMessage(), ExceptionUtils.getFullStackTrace(e)));
					}
					//获取机房开门数据
					try{
						log.error("获取机房考勤数据：开始获取机房开门数据");
						officeAttendanceDoorRecordService.syncDoorRecord();
						log.error("获取机房考勤数据：结束获取机房开门数据");
					}catch(Exception e){
						log.error(StringUtils.defaultIfEmpty(e.getMessage(), ExceptionUtils.getFullStackTrace(e)));
					}
					//未到学生推送消息给班主任
					try{
						log.error("获取机房考勤数据：开始推送未到信息给班主任");
						officeAttendanceStuRecordService.sendRemind();
						log.error("获取机房考勤数据：结束推送未到信息给班主任");
					}catch(Exception e){
						log.error(StringUtils.defaultIfEmpty(e.getMessage(), ExceptionUtils.getFullStackTrace(e)));
					}
				}catch(Exception e){
					log.error(StringUtils.defaultIfEmpty(e.getMessage(), ExceptionUtils.getFullStackTrace(e)));
				}finally{
					isRunning = false;
				}
			}
	}
	
	public int getCurrentPeriod(Map<Integer, String> xkTimeMap) {
		int currentPeriod = 0;
		int distance = 9999;
		int nowTime = DateUtils.currentHour() * 100 + DateUtils.currentMinute();
		for(Map.Entry<Integer, String> entry : xkTimeMap.entrySet()) {
			 String[] timeArray = entry.getValue().split(":");
			 int time = Integer.parseInt(timeArray[0]) * 100 + Integer.parseInt(timeArray[1]);
			 if(nowTime >= time) {
				 if((nowTime-time) < distance) {
					 currentPeriod = entry.getKey();
					 distance = nowTime-time;
				 }
			 }
		}
		return currentPeriod;
	}
	
	public int getSleepTime(int currentPeriod, Map<Integer, String> xkTimeMap) {
		int nowHour = DateUtils.currentHour();
		int nowMinute = DateUtils.currentMinute();
		int nextHour = 0;
		int nextMinute = 0;
		if(currentPeriod == 9) {
			String[] timeArray = xkTimeMap.get(1).split(":");
			nextHour = Integer.parseInt(timeArray[0]) + 12;
			nextMinute = Integer.parseInt(timeArray[1]);
		}else {
			String[] timeArray = xkTimeMap.get(currentPeriod + 1).split(":");
			nextHour = Integer.parseInt(timeArray[0]);
			nextMinute = Integer.parseInt(timeArray[1]);
		}
		return ((nextHour - nowHour)*60 + nextMinute - nowMinute)*60*1000;
	}
	
	public String getUnitId() {
		try{
			Unit unit = unitService.getNormalLocalUnits().get(0);
			return unit.getId();
		}catch(Exception e) {
			throw new RuntimeException("获取机房考勤数据：无法获得unitId");
		}
	}
	
	public String getAcadyear() {
		try{
			CurrentSemester  currentSemester =  semesterService.getCurrentSemester();
			return currentSemester.getAcadyear();
		}catch(Exception e) {
			throw new RuntimeException("获取机房考勤数据：无法获得学年学期");
		}
	}
	
	public String getSemester() {
		try{
			CurrentSemester  currentSemester =  semesterService.getCurrentSemester();
			return currentSemester.getSemester();
		}catch(Exception e) {
			throw new RuntimeException("获取机房考勤数据：无法获得学年学期");
		}
	}
	
	public Map<Integer, String> getSkTimeMap(String unitId, String acadyear, String semester) {
		List<StusysSectionTimeSetDto> timeSets = baseDataSubsystemService.getSectionTimeSets(unitId, acadyear, semester);

		OfficeTimeSet officeTimeSet = officeTimeSetService.getOfficeTimeSetByUnitId(unitId);
		if(officeTimeSet == null || timeSets.size() != 8) {
			throw new RuntimeException("获取机房考勤数据：无法获取节次时间安排");
		}
		Map<Integer, String> skTimeMap = new HashMap<Integer, String>();
		for(StusysSectionTimeSetDto timeSet : timeSets) {
			skTimeMap.put(AttendanceDto.toComparePeriod(timeSet.getSectionNumber()), timeSet.getBeginTime());
			if(timeSet.getSectionNumber() == 4 && timeSet.getEndTime().compareTo(officeTimeSet.getNoonStartTime()) >=0) {
				throw new RuntimeException("获取机房考勤数据：中午上课时间要在第四节课下课之后");
			}
			if(timeSet.getSectionNumber() == 5 && timeSet.getBeginTime().compareTo(officeTimeSet.getNoonEndTime()) <=0) {
				throw new RuntimeException("获取机房考勤数据：中午下课时间要在第五节课上课之前");
			}
		}
		skTimeMap.put(5, officeTimeSet.getNoonStartTime());
		return skTimeMap;
	}
	
	public Map<Integer, String> getXkTimeMap(String unitId, String acadyear, String semester) {
		List<StusysSectionTimeSetDto> timeSets = baseDataSubsystemService.getSectionTimeSets(unitId, acadyear, semester);
		OfficeTimeSet officeTimeSet = officeTimeSetService.getOfficeTimeSetByUnitId(unitId);
		if(officeTimeSet == null || timeSets.size() != 8) {
			throw new RuntimeException("获取机房考勤数据：无法获取节次时间安排");
		}
		Map<Integer, String> xkTimeMap = new HashMap<Integer, String>();
		for(StusysSectionTimeSetDto timeSet : timeSets) {
			xkTimeMap.put(AttendanceDto.toComparePeriod(timeSet.getSectionNumber()), timeSet.getEndTime());
		}
		xkTimeMap.put(5, officeTimeSet.getNoonEndTime());
		return xkTimeMap;
	}
	
	public boolean isNeedSync(int currentPeriod) {
		try{
			String timeString = systemIniService.getValue(OfficeSyncKqConstant.OFFICE_KQ_SYNC_LAST_TIME);
			String[] timeArray = timeString.split("-");
			String currentDateStr = DateUtils.date2String(new Date(), "yyyyMMdd");
			if(currentDateStr.compareTo(timeArray[0]) == 0) {
				int period = getComparePeriod(Integer.parseInt(timeArray[1]));
				if(currentPeriod > period) {
					return true;
				}
			}else if(currentDateStr.compareTo(timeString) > 0) {
				return true;
			}
		}catch(Exception e){
			throw new RuntimeException("获取机房考勤数据：获取机房考勤数据同步最后一次的时间出错");
		}
		return false;
	}

	public int getComparePeriod (int period) {
		if(period == 99) {
			return 5;
		}else if(period >= 5) {
			return period + 1;
		}else{
			return period;
		}
	}
	
	public Boolean getIsRunning() {
		return isRunning;
	}

	public void setIsRunning(Boolean isRunning) {
		this.isRunning = isRunning;
	}
}
