package net.zdsoft.eis.base.data.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.common.entity.CurrentSemester;
import net.zdsoft.eis.base.common.entity.DateInfo;
import net.zdsoft.eis.base.common.entity.Semester;
import net.zdsoft.eis.base.common.entity.SystemIni;
import net.zdsoft.eis.base.common.service.SemesterService;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.data.dao.BaseDateInfoDao;
import net.zdsoft.eis.base.data.service.BaseDateInfoService;
import net.zdsoft.eis.base.util.BusinessUtils;
import net.zdsoft.keel.util.DateUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

public class BaseDateInfoServiceImpl implements BaseDateInfoService {

	private BaseDateInfoDao baseDateInfoDao;
	private SemesterService semesterService;
	private SystemIniService systemIniService;
	
	public void setSystemIniService(SystemIniService systemIniService) {
		this.systemIniService = systemIniService;
	}

	public void setBaseDateInfoDao(BaseDateInfoDao baseDateInfoDao) {
		this.baseDateInfoDao = baseDateInfoDao;
	}

	public void setSemesterService(SemesterService semesterService) {
		this.semesterService = semesterService;
	}

	public void deleteDateInfo(String schoolId, String acadyear, String semester) {
		baseDateInfoDao.deleteDateInfo(schoolId, acadyear, semester);
	}

	public void saveDateInfo(String schoolId, String acadyear, String semester,
			Date beginDate, Date endDate, boolean isSun) {
		// 先删除
		baseDateInfoDao.deleteDateInfo(schoolId, acadyear, semester);
		// 再插入
		baseDateInfoDao.insertDateInfo(schoolId, acadyear, semester, beginDate,
				endDate, isSun);
	}

	public List<DateInfo> getDateList(String schoolId, String acadyear,
			String semester) {
		List<DateInfo> dataList = baseDateInfoDao.getDateList(schoolId,
				acadyear, semester);
		Semester currentSemester = semesterService.getSemester(acadyear,
				semester);
		SystemIni systemIni = systemIniService.getSystemIni("SUNDAY_WEEK_DAY");
		if (CollectionUtils.isEmpty(dataList)) {
			if(currentSemester == null){
				return new ArrayList<DateInfo>();
			}
			if(systemIni != null && "1".equals(systemIni.getNowValue())){
				saveDateInfo(schoolId, acadyear, semester,
						currentSemester.getSemesterBegin(),
						currentSemester.getSemesterEnd(), true);
			}else{
				saveDateInfo(schoolId, acadyear, semester,
						currentSemester.getSemesterBegin(),
						currentSemester.getSemesterEnd(), false);
			}
			dataList = baseDateInfoDao
					.getDateList(schoolId, acadyear, semester);
		}else{
			//判断当前周次是按照周日开始还是按照周一开始
			List<DateInfo> datas = baseDateInfoDao.getDateInfoByWeek(schoolId, acadyear, semester, 1);
			DateInfo endInfo = datas.get(datas.size()-1);
			String oneWeekDay = endInfo.getWeekday();//只需要判断第一周最后一天是周几就可以 
			if(systemIni != null && "1".equals(systemIni.getNowValue())){
				if(StringUtils.equals("7", oneWeekDay)){
					//最后一天为周日  说明之前的排序方式是按照周一是第一天的方式安排，需要重新生成
					saveDateInfo(schoolId, acadyear, semester,
							currentSemester.getSemesterBegin(),
							currentSemester.getSemesterEnd(), true);
					dataList = baseDateInfoDao
							.getDateList(schoolId, acadyear, semester);
				}
			}else{
				if(StringUtils.equals("6", oneWeekDay)){
					//最后一天为周六  说明之前的排序方式是按照周日是第一天的方式安排，需要重新生成
					saveDateInfo(schoolId, acadyear, semester,
							currentSemester.getSemesterBegin(),
							currentSemester.getSemesterEnd(), false);
					dataList = baseDateInfoDao
							.getDateList(schoolId, acadyear, semester);
				}
			}
		}
		return dataList;
	}
    public List<DateInfo> getEduDateList(String unitId){
    	Date currDate = new Date();
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(DateUtils.string2Date(DateUtils.date2String(currDate), "yyyy-MM-dd"));
    	calendar.set(calendar.get(Calendar.YEAR), 0, 1);
    	Date startDate = calendar.getTime();//一年中的第一天
//    	calendar.set(Calendar.MONTH, 11);//设置时间为12月
//    	int dayNumMax = calendar.getActualMaximum(Calendar.DATE);//
//    	calendar.set(Calendar.DATE, dayNumMax);
    	calendar.set(calendar.get(Calendar.YEAR), 11, 31);
    	Date endDate = calendar.getTime();//一年的最后一天
    			
    	List<DateInfo> dataList = baseDateInfoDao.getDateList(unitId, startDate, endDate);
    	if(CollectionUtils.isEmpty(dataList)){
    		List<DateInfo> insertList = new ArrayList<DateInfo>();
    		//初始化一整年的数据  教育局节假日没有学年学期周的概念 
    		calendar.setTime(startDate);
    		DateInfo entity = null;
    		int iWeekday;
    		while(!calendar.getTime().after(endDate)){
    			entity = new DateInfo();
                entity.setSchid(unitId); // 学校
                entity.setDate(calendar.getTime()); // 日期

                // 老外以星期日为一周的第一天
                iWeekday = calendar.get(Calendar.DAY_OF_WEEK);
                if (iWeekday == 1) {
                    entity.setWeekday("7"); // 星期日
                } else {
                    entity.setWeekday(Integer.toString(iWeekday - 1)); 
                }

                if (entity.getWeekday().equals("6") || entity.getWeekday().equals("7")) {
                    entity.setIsfeast("Y"); // 是否节假日
                } else {
                    entity.setIsfeast("N"); // 是否节假日
                }
                
                insertList.add(entity);
    			calendar.add(Calendar.DATE, 1);
    		}
    		baseDateInfoDao.batchInsert(insertList);
    		dataList = baseDateInfoDao.getDateList(unitId, startDate, endDate);
    	}
    	
    	return dataList;
    }
	
	public List<DateInfo> getDateListByMon(String schoolId, String acadyear, String semester, int month){
		return baseDateInfoDao.getDateListByMon(schoolId, acadyear, semester, month);
	}
	
	public int getWeeksByMon(String schoolId, String acadyear, String semester, int month){
		return baseDateInfoDao.getWeeksByMon(schoolId, acadyear, semester, month);
	}

	public void updateDateList(List<DateInfo> dateList) {
		baseDateInfoDao.updateDateList(dateList);
	}
	public void updateDateList1(List<DateInfo> dateList) {
		baseDateInfoDao.updateDateList1(dateList);
	}
	
	public DateInfo getNextDate(String schId, String acadyear, String semester, Date date){
		return baseDateInfoDao.getNextDate(schId, acadyear, semester, date);
	}
	@Override
	public DateInfo getDate(String schId, String acadyear, String semester,
			Date date) {
		return baseDateInfoDao.getDate(schId, acadyear, semester, date);
	}
}
