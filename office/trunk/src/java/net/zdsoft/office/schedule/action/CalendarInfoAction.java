package net.zdsoft.office.schedule.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.Semester;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.util.BusinessUtils;
import net.zdsoft.eis.frame.action.PageSemesterAction;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.office.schedule.entity.CalendarDayInfoDto;
import net.zdsoft.office.schedule.entity.OfficeCalendarAuth;
import net.zdsoft.office.schedule.entity.OfficeCalendarDayInfo;
import net.zdsoft.office.schedule.entity.OfficeCalendarMonthInfo;
import net.zdsoft.office.schedule.entity.OfficeCalendarSemester;
import net.zdsoft.office.schedule.service.OfficeCalendarAuthService;
import net.zdsoft.office.schedule.service.OfficeCalendarDayInfoService;
import net.zdsoft.office.schedule.service.OfficeCalendarMonthInfoService;
import net.zdsoft.office.schedule.service.OfficeCalendarSemesterService;
import net.zdsoft.office.schedule.util.ScheduleConstant;
import net.zdsoft.office.util.Lunar;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;

public class CalendarInfoAction extends PageSemesterAction{
	private static final long serialVersionUID = 2655450917796703604L;
	
	private List<OfficeCalendarAuth> authDeptList = new ArrayList<OfficeCalendarAuth>();
	private List<OfficeCalendarAuth> authUnitList = new ArrayList<OfficeCalendarAuth>();
	private OfficeCalendarAuth auth;
	private OfficeCalendarAuthService officeCalendarAuthService;
	
	private String[] chineseNumber = {"一","二","三","四","五","六","七","八","九","十","十一","十二","十三","十四","十五",
			"十六","十七","十八","十九","二十","二十一","二十二","二十三","二十四","二十五","二十六","二十七","二十八",
			"二十九","三十","三十一","三十二","三十三","三十四","三十五","三十六","三十七","三十八","三十九","四十","四十一","四十二",
			"四十三","四十四","四十五","四十六","四十七","四十八","四十九","五十","五十一","五十二","五十三",
			"五十四","五十五","五十六","五十七","五十八","五十九","六十"};
	//年度
	private String calyear;
	private List<String> yearList;
	private Date beginDate;
	private Date endDate;
	private boolean canEdit;
	private boolean hasSch;
	private String unitName;
	
	private UnitService unitService;
	private OfficeCalendarSemester officeCalendarSemester;
	private OfficeCalendarDayInfo officeCalendarDayInfo;
	private OfficeCalendarMonthInfo officeCalendarMonthInfo;
	private Map<String, OfficeCalendarMonthInfo> calendarMonthInfoMap;
	private List<CalendarDayInfoDto> calendarDayInfoDtoList = new ArrayList<CalendarDayInfoDto>();
	
	
	private OfficeCalendarSemesterService officeCalendarSemesterService;
	private OfficeCalendarDayInfoService officeCalendarDayInfoService;
	private OfficeCalendarMonthInfoService officeCalendarMonthInfoService;
	
	/**行事历**/
	public List<String> getYearList() {
		yearList = new ArrayList<String>();
		Calendar calendar=Calendar.getInstance();
    	int year=calendar.get(Calendar.YEAR);
		for(int i=4;i>-1;i--){
			yearList.add(year-i+1+"");
		}
    	return yearList;
	}
	
	public String toCalendarInfo(){
		String unitId = getUnitId();
		unitName = unitService.getUnit(unitId).getName();
		Map<Integer,Integer> monthWeek = new HashMap<Integer, Integer>();
		int maxWeekForYear = 0;
		if(getLoginInfo().getUnitClass() == Unit.UNIT_CLASS_SCHOOL){
			maxWeekForYear = getSchCalendarInfo(unitId,monthWeek,maxWeekForYear);
			canEdit = getLoginInfo().validateAllModelOpera(70020,"SCHEDULE_DATEINFO_EDIT");
		}else if(getLoginInfo().getUnitClass() == Unit.UNIT_CLASS_EDU){
			maxWeekForYear = getEduCalendarInfo(unitId,monthWeek,maxWeekForYear);
			canEdit = getLoginInfo().validateAllModelOpera(70520,"SCHEDULE_DATEINFO_EDIT");
		}
		if(CollectionUtils.isNotEmpty(calendarDayInfoDtoList)){
			if(calendarDayInfoDtoList.get(0).getDay() != 1){
				if(calendarDayInfoDtoList.get(0).getMonth() < 12){
					if(monthWeek.containsKey(calendarDayInfoDtoList.get(0).getMonth() + 1)){
						calendarDayInfoDtoList.get(0).setRowspan(monthWeek.get(calendarDayInfoDtoList.get(0).getMonth()+1) - calendarDayInfoDtoList.get(0).getWeekForYear());
					}else{
						calendarDayInfoDtoList.get(0).setRowspan(calendarDayInfoDtoList.get(calendarDayInfoDtoList.size()-1).getWeekForYear() - calendarDayInfoDtoList.get(0).getWeekForYear() + 1);
					}
				}else{
					if(monthWeek.containsKey(1)){
						calendarDayInfoDtoList.get(0).setRowspan(monthWeek.get(1) - calendarDayInfoDtoList.get(0).getWeekForYear());
					}else{
						calendarDayInfoDtoList.get(0).setRowspan(calendarDayInfoDtoList.get(calendarDayInfoDtoList.size()-1).getWeekForYear() - calendarDayInfoDtoList.get(0).getWeekForYear());
					}
				}
			}
			for(CalendarDayInfoDto dto : calendarDayInfoDtoList){
				if(dto.getDay() == 1){
					if(dto.getMonth()< 12){
						if(monthWeek.containsKey(dto.getMonth() + 1)){
							if(monthWeek.get(dto.getMonth()) < monthWeek.get(dto.getMonth()+1)){
								dto.setRowspan(monthWeek.get(dto.getMonth()+1) - monthWeek.get(dto.getMonth()));
							}else{
								dto.setRowspan(maxWeekForYear -monthWeek.get(dto.getMonth()) + 1);
							}
						}else{
							dto.setRowspan(maxWeekForYear -monthWeek.get(dto.getMonth()) + 1);
						}
					}else{
						if(monthWeek.containsKey(1) && monthWeek.get(1) > monthWeek.get(dto.getMonth())){
							dto.setRowspan(monthWeek.get(1) - monthWeek.get(dto.getMonth()));
						}else{
							dto.setRowspan(maxWeekForYear -monthWeek.get(dto.getMonth()) + 1);
						}
					}
				}
			}
		}
		return SUCCESS;
	}
	
	public String saveDayInfo(){
		try{
			if(officeCalendarDayInfo != null){
				officeCalendarDayInfo.setUnitId(getUnitId());
				if(StringUtils.isNotBlank(officeCalendarDayInfo.getId())){
					officeCalendarDayInfoService.update(officeCalendarDayInfo);
				}else{
					officeCalendarDayInfoService.save(officeCalendarDayInfo);
				}
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("操作成功！");
			}else{
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setPromptMessage("没有保存数据！");
			}
		} catch (Exception e) {
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setPromptMessage("操作失败！");
				e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String saveMonthInfo(){
		try{
			if(officeCalendarMonthInfo != null){
				officeCalendarMonthInfo.setUnitId(getUnitId());
				if(StringUtils.isNotBlank(officeCalendarMonthInfo.getId())){
					officeCalendarMonthInfoService.update(officeCalendarMonthInfo);
				}else{
					officeCalendarMonthInfoService.save(officeCalendarMonthInfo);
				}
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("操作成功！");
			}else{
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setPromptMessage("没有保存数据！");
			}
		} catch (Exception e) {
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setPromptMessage("操作失败！");
				e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String saveSemesterContent(){
		try{
			if(officeCalendarSemester != null){
				if(StringUtils.isNotBlank(officeCalendarSemester.getId())){
					officeCalendarSemesterService.updateContent(officeCalendarSemester);
				}
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("操作成功！");
			}else{
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setPromptMessage("没有保存数据！");
			}
		} catch (Exception e) {
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setPromptMessage("操作失败！");
				e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String saveSemesterInfo(){
		try{
			if(officeCalendarSemester != null){
				if(getHasSch()){
					officeCalendarSemester.setAcadyear(acadyear);
					officeCalendarSemester.setSemester(NumberUtils.toInt(semester));
				}else{
					officeCalendarSemester.setCalyear(NumberUtils.toInt(calyear));
				}
				officeCalendarSemester.setUnitId(getUnitId());
				officeCalendarSemester.setBeginDate(beginDate);
				officeCalendarSemester.setEndDate(endDate);
				if(StringUtils.isNotBlank(officeCalendarSemester.getId())){
					officeCalendarSemesterService.update(officeCalendarSemester);
				}else{
					officeCalendarSemesterService.save(officeCalendarSemester);
				}
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("操作成功！");
			}else{
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setPromptMessage("没有保存数据！");
			}
		} catch (Exception e) {
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setPromptMessage("操作失败！");
				e.printStackTrace();
			}
		return SUCCESS;
	}
	private int getEduCalendarInfo(String unitId, Map<Integer,Integer> monthWeek,int maxWeekForYear){
		if(StringUtils.isBlank(calyear)){
			calyear = new SimpleDateFormat("yyyy").format(new Date());
		}
		officeCalendarSemester = officeCalendarSemesterService.getCalendarSemester(calyear, unitId);
		calendarMonthInfoMap = officeCalendarMonthInfoService.getCalendarMonthInfoMapByUnitId(unitId);
		if(officeCalendarSemester != null){
			beginDate = officeCalendarSemester.getBeginDate();
			endDate = officeCalendarSemester.getEndDate();
			
			Date[] dates = getDateArrays(beginDate, endDate, 5);
			//获取已经维护的每天信息
			Map<String, OfficeCalendarDayInfo> calendarDayInfoMap = officeCalendarDayInfoService.getCalendarDayInfoMapByUnitId(beginDate,endDate,unitId);
			SimpleDateFormat chineseDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
			boolean flag = false;
			for(Date date:dates){
				CalendarDayInfoDto calendarDayInfoDto = new CalendarDayInfoDto();
				Calendar today = Calendar.getInstance();
		    	try {
					today.setTime(chineseDateFormat.parse(DateUtils.date2String(date,"yyyy年MM月dd日")));
				} catch (ParseException e) {
					e.printStackTrace();
				}
		    	Lunar lunar = new Lunar(today);
		    	calendarDayInfoDto.setRestDate(today.getTime());
		    	calendarDayInfoDto.setCalendarStr(chineseDateFormat.format(today.getTime()));
		    	calendarDayInfoDto.setLunarStr(lunar.toString());
		    	//周几
		    	calendarDayInfoDto.setWeek(getWeek(date));
		    	calendarDayInfoDto.setDay(today.get(Calendar.DAY_OF_MONTH));
		    	calendarDayInfoDto.setMonth(today.get(Calendar.MONTH) + 1);
		    	OfficeCalendarDayInfo calendarDayInfo = calendarDayInfoMap.get(DateUtils.date2String(date, "yyyy-MM-dd"));
		    	if(calendarDayInfo!=null){
		    		calendarDayInfoDto.setId(calendarDayInfo.getId());
		    		calendarDayInfoDto.setContent(calendarDayInfo.getContent());
		    	}
		    	//第几周
		    	int wfy = 0;
		    	if(calendarDayInfoDto.getMonth() == 12){
		    		today.add(Calendar.DATE, -7);
		    		wfy = BusinessUtils.getWeek(today.getTime(), false) + 1;
		    	}else{
		    		wfy = BusinessUtils.getWeek(today.getTime(), false);
		    	}
		    	if(wfy == 0){
		    		flag = true;
		    	}
		    	if(flag){
		    		calendarDayInfoDto.setWeekForYear(wfy+1);
		    	}else{
		    		calendarDayInfoDto.setWeekForYear(wfy);
		    	}
		    	if(calendarDayInfoDto.getDay() == 1){
		    		monthWeek.put(calendarDayInfoDto.getMonth(), calendarDayInfoDto.getWeekForYear());
		    	}
		    	maxWeekForYear = calendarDayInfoDto.getWeekForYear();
		    	calendarDayInfoDtoList.add(calendarDayInfoDto);
			}
		}else{
			officeCalendarSemester = new OfficeCalendarSemester();
			beginDate = DateUtils.string2Date(calyear+"-01-01","yyyy-MM-dd");
			endDate = DateUtils.string2Date(calyear+"-12-31", "yyyy-MM-dd");
			//===============================================================================
//			Date[] dates = getDateArrays(beginDate, endDate, 5);
//			SimpleDateFormat chineseDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
//			for(Date date:dates){
//				CalendarDayInfoDto calendarDayInfoDto = new CalendarDayInfoDto();
//				Calendar today = Calendar.getInstance();
//		    	try {
//					today.setTime(chineseDateFormat.parse(DateUtils.date2String(date,"yyyy年MM月dd日")));
//				} catch (ParseException e) {
//					e.printStackTrace();
//				}
//		    	Lunar lunar = new Lunar(today);
//		    	calendarDayInfoDto.setRestDate(today.getTime());
//		    	calendarDayInfoDto.setCalendarStr(chineseDateFormat.format(today.getTime()));
//		    	calendarDayInfoDto.setLunarStr(lunar.toString());
//		    	calendarDayInfoDto.setDay(today.get(Calendar.DAY_OF_MONTH));
//		    	calendarDayInfoDto.setMonth(today.get(Calendar.MONTH) + 1);
//		    	//周几
//		    	calendarDayInfoDto.setWeek(getWeek(date));
//		    	
//		    	calendarDayInfoDtoList.add(calendarDayInfoDto);
//			}
			//========================================================================
		}
		return maxWeekForYear;
	}
	private int getSchCalendarInfo(String unitId,
			Map<Integer, Integer> monthWeek, int maxWeekForYear) {
		//TODO 学校端
		if(StringUtils.isBlank(acadyear)){
			acadyear = getAcadyear();
			semester = getSemester();
		}
		officeCalendarSemester = officeCalendarSemesterService.getCalendarSemester(acadyear,semester, unitId);
		calendarMonthInfoMap = officeCalendarMonthInfoService.getCalendarMonthInfoMapByUnitId(unitId);
		if(officeCalendarSemester != null && StringUtils.isNotBlank(officeCalendarSemester.getId())){
			beginDate = officeCalendarSemester.getBeginDate();
			endDate = officeCalendarSemester.getEndDate();
			Map<String, OfficeCalendarDayInfo> calendarDayInfoMap = officeCalendarDayInfoService.getCalendarDayInfoMapByUnitId(beginDate,endDate,unitId);
			Date[] dates = getDateArrays(beginDate, endDate, 5);
			SimpleDateFormat chineseDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
			int i = 0;
			int weekForYear =1;
			for(Date date:dates){
				CalendarDayInfoDto calendarDayInfoDto = new CalendarDayInfoDto();
				Calendar today = Calendar.getInstance();
		    	try {
					today.setTime(chineseDateFormat.parse(DateUtils.date2String(date,"yyyy年MM月dd日")));
				} catch (ParseException e) {
					e.printStackTrace();
				}
		    	Lunar lunar = new Lunar(today);
		    	calendarDayInfoDto.setRestDate(today.getTime());
		    	calendarDayInfoDto.setCalendarStr(chineseDateFormat.format(today.getTime()));
		    	calendarDayInfoDto.setLunarStr(lunar.toString());
		    	calendarDayInfoDto.setWeek(getWeek(today.getTime()));
		    	OfficeCalendarDayInfo calendarDayInfo = calendarDayInfoMap.get(DateUtils.date2String(date, "yyyy-MM-dd"));
		    	if(calendarDayInfo!=null){
		    		calendarDayInfoDto.setId(calendarDayInfo.getId());
		    		calendarDayInfoDto.setContent(calendarDayInfo.getContent());
		    	}
		    	//周几
//		    	calendarDayInfoDto.setWeek(getWeek(date));
		    	calendarDayInfoDto.setDay(today.get(Calendar.DAY_OF_MONTH));
		    	calendarDayInfoDto.setMonth(today.get(Calendar.MONTH) + 1);
		    	//第几周
		    	if(i==0){
		    		calendarDayInfoDto.setWeekForYear(weekForYear);
		    	}else{
		    		if(calendarDayInfoDto.getWeek() == 1){
		    			weekForYear++;
		    		}
		    		calendarDayInfoDto.setWeekForYear(weekForYear);
		    	}
		    	if(calendarDayInfoDto.getDay() == 1){
		    		monthWeek.put(calendarDayInfoDto.getMonth(), calendarDayInfoDto.getWeekForYear());
		    	}
		    	maxWeekForYear = calendarDayInfoDto.getWeekForYear();
		    	calendarDayInfoDtoList.add(calendarDayInfoDto);
		    	i++;
			}
		}else{
			officeCalendarSemester = new OfficeCalendarSemester();
			Semester se = semesterService.getSemester(acadyear, semester);
			if(se !=null){
				beginDate = se.getWorkBegin();
				endDate = se.getWorkEnd();
			}
		}
		return maxWeekForYear;
	}

	/**
	 * 根据开始时间、结束时间得到两个时间段内所有的日期
	 * @param start 开始日期
	 * @param end   结束日期
	 * @param calendarType  类型
	 * @return  两个日期之间的日期
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Date[] getDateArrays(Date start, Date end, int calendarType) {
		ArrayList ret = new ArrayList();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(start);
		Date tmpDate = calendar.getTime();
		long endTime = end.getTime();
		while (tmpDate.before(end) || tmpDate.getTime() == endTime) {
			ret.add(calendar.getTime());
			calendar.add(calendarType, 1);
			tmpDate = calendar.getTime();
		}
		Date[] dates = new Date[ret.size()];
		return (Date[]) ret.toArray(dates);
	}
	
	public static int getWeek(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		switch (c.get(Calendar.DAY_OF_WEEK)) {
			case 1:return 7;
			case 2:return 1;
			case 3:return 2;
			case 4:return 3;
			case 5:return 4;
			case 6:return 5;
			case 7:return 6;
			default:
				return 7;
		}
	}
	
	/**权限设置**/
	public String toAuth(){
		List<OfficeCalendarAuth> authlist = officeCalendarAuthService.getOfficeCalendarAuthByUnitIdList(getUnitId());
		for(OfficeCalendarAuth e : authlist){
			if(ScheduleConstant.CALENDAR_AUTH_TYPE_DEPT.equals(e.getAuthType())){
				authDeptList.add(e);
			}
			if(ScheduleConstant.CALENDAR_AUTH_TYPE_LEADER.equals(e.getAuthType())){
				authUnitList.add(e);
			}
		}
		return SUCCESS;
	}
	
	public String authEdit(){
		// 权限设置保存   设置部门负责人的时候判断用户是否此部门的
		if(StringUtils.isNotBlank(auth.getObjectId())){
			try {
				auth.setUnitId(getUnitId());
				OfficeCalendarAuth aa = officeCalendarAuthService.getOfficeCalendarAuthByUnitIdList(getUnitId(),auth.getObjectId(),auth.getAuthType());
				if(aa != null && StringUtils.isNotBlank(aa.getId())){
					auth.setId(aa.getId());
					officeCalendarAuthService.update(auth);
				}else{
					officeCalendarAuthService.save(auth);
				}
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("操作成功！");
			} catch (Exception e) {
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setPromptMessage("操作失败！");
				e.printStackTrace();
			}
		}else{
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("用户未选择，请选择用户！");
		}
		return SUCCESS;
	}
	
	public List<OfficeCalendarAuth> getAuthDeptList() {
		return authDeptList;
	}

	public void setAuthDeptList(List<OfficeCalendarAuth> authDeptList) {
		this.authDeptList = authDeptList;
	}

	public List<OfficeCalendarAuth> getAuthUnitList() {
		return authUnitList;
	}

	public void setAuthUnitList(List<OfficeCalendarAuth> authUnitList) {
		this.authUnitList = authUnitList;
	}
	
	public OfficeCalendarAuth getAuth() {
		return auth;
	}

	public void setAuth(OfficeCalendarAuth auth) {
		this.auth = auth;
	}
	
	public String getCalyear() {
		return calyear;
	}

	public void setCalyear(String calyear) {
		this.calyear = calyear;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Map<String, OfficeCalendarMonthInfo> getCalendarMonthInfoMap() {
		return calendarMonthInfoMap;
	}

	public void setCalendarMonthInfoMap(
			Map<String, OfficeCalendarMonthInfo> calendarMonthInfoMap) {
		this.calendarMonthInfoMap = calendarMonthInfoMap;
	}

	public List<CalendarDayInfoDto> getCalendarDayInfoDtoList() {
		return calendarDayInfoDtoList;
	}

	public void setCalendarDayInfoDtoList(
			List<CalendarDayInfoDto> calendarDayInfoDtoList) {
		this.calendarDayInfoDtoList = calendarDayInfoDtoList;
	}

	public void setOfficeCalendarSemester(
			OfficeCalendarSemester officeCalendarSemester) {
		this.officeCalendarSemester = officeCalendarSemester;
	}

	public void setOfficeCalendarSemesterService(
			OfficeCalendarSemesterService officeCalendarSemesterService) {
		this.officeCalendarSemesterService = officeCalendarSemesterService;
	}

	public void setOfficeCalendarDayInfoService(
			OfficeCalendarDayInfoService officeCalendarDayInfoService) {
		this.officeCalendarDayInfoService = officeCalendarDayInfoService;
	}
	
	public OfficeCalendarSemester getOfficeCalendarSemester() {
		return officeCalendarSemester;
	}
	
	public boolean getHasSch() {
		if(getLoginInfo().getUnitClass() == Unit.UNIT_CLASS_SCHOOL){
			hasSch = true;
		}else if(getLoginInfo().getUnitClass() == Unit.UNIT_CLASS_EDU){
			hasSch = false;
		}
		return hasSch;
	}

	public void setHasSch(boolean hasSch) {
		this.hasSch = hasSch;
	}

	public void setOfficeCalendarMonthInfoService(
			OfficeCalendarMonthInfoService officeCalendarMonthInfoService) {
		this.officeCalendarMonthInfoService = officeCalendarMonthInfoService;
	}

	public void setOfficeCalendarAuthService(
			OfficeCalendarAuthService officeCalendarAuthService) {
		this.officeCalendarAuthService = officeCalendarAuthService;
	}
	public List<String> getAcadyearList() {
//		if(getLoginInfo().getUnitClass() == Unit.UNIT_CLASS_SCHOOL){
//			return schoolSemesterService.getPreAcadyears(getUnitId());
//		}else{
			return semesterService.getAcadyears();
//		}
	}
	
	public String[] getChineseNumber() {
		return chineseNumber;
	}

	public void setChineseNumber(String[] chineseNumber) {
		this.chineseNumber = chineseNumber;
	}
	
	public OfficeCalendarDayInfo getOfficeCalendarDayInfo() {
		return officeCalendarDayInfo;
	}

	public void setOfficeCalendarDayInfo(OfficeCalendarDayInfo officeCalendarDayInfo) {
		this.officeCalendarDayInfo = officeCalendarDayInfo;
	}

	public OfficeCalendarMonthInfo getOfficeCalendarMonthInfo() {
		return officeCalendarMonthInfo;
	}

	public void setOfficeCalendarMonthInfo(
			OfficeCalendarMonthInfo officeCalendarMonthInfo) {
		this.officeCalendarMonthInfo = officeCalendarMonthInfo;
	}

	public boolean isCanEdit() {
		return canEdit;
	}

	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}
	
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public static void main(String[] args) {
		Calendar c = Calendar.getInstance();
		c.set(2015, 11, 31);
		c.add(Calendar.DATE, -7);
		System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()));
		System.out.println(c.get(Calendar.WEEK_OF_YEAR));
		
	}
}
