package net.zdsoft.office.schedule.action;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.frame.action.PageAction;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.ServletUtils;
import net.zdsoft.leadin.doc.DocumentHandler;
import net.zdsoft.office.schedule.dto.DateDto;
import net.zdsoft.office.schedule.dto.OfficeCalendarDto;
import net.zdsoft.office.schedule.entity.OfficeCalendar;
import net.zdsoft.office.schedule.service.OfficeCalendarAuthService;
import net.zdsoft.office.schedule.service.OfficeCalendarService;
import net.zdsoft.office.schedule.util.ScheduleConstant;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.tools.ant.filters.StringInputStream;


@SuppressWarnings("serial")
public class ScheduleAction extends PageAction{
	private OfficeCalendarService officeCalendarService;
	private OfficeCalendarAuthService officeCalendarAuthService;
	private DeptService deptService;
	
	private boolean hasLeader;
	private boolean hasAuthority;
	private List<DateDto> dates;
	private OfficeCalendarDto calDto = new OfficeCalendarDto();
	private OfficeCalendar cal = new OfficeCalendar();
	private Map<String, OfficeCalendarDto> calMap;
	private List<OfficeCalendar> cals;
	boolean ismonth=false;// 是否月视图
	boolean isday=false;// 是否日程视图
	boolean isweek=false;// 是否周视图
	private boolean hasDeptAuth=false;
	private boolean hasUnitAuth=false;
	private boolean isSendS=false;
	private boolean isLeader = false;//是否部门分管领导
	private boolean isGroupHead = false;//是否是部门负责人
	
	private String startTime;
	private String endTime;
	private String searchName;
	
	public String scheduleAdmin(){
		if(getLoginInfo().getUnitClass() == Unit.UNIT_CLASS_SCHOOL){
			hasLeader = getLoginInfo().validateAllModelOpera(70020,"SCHEDULE_LEADER");
			hasAuthority = getLoginInfo().validateAllModelOpera(70020,"SCHEDULE_AUTHORITY");
		}else if(getLoginInfo().getUnitClass() == Unit.UNIT_CLASS_EDU){
			hasLeader = getLoginInfo().validateAllModelOpera(70520,"SCHEDULE_LEADER");
			hasAuthority = getLoginInfo().validateAllModelOpera(70520,"SCHEDULE_AUTHORITY");
		}
		isLeader = deptService.isLeader(getUnitId(), getLoginUser().getUserId());
		return SUCCESS;
	}
	
	// 个人、局领导日程
	public String toPersonal(){
		if (StringUtils.isBlank(calDto.getSearchType())){
			calDto.setSearchType(ScheduleConstant.SEARCH_TYPE_WEEK);
			isweek = true;
		}
		if(StringUtils.isBlank(calDto.getRangeType())){
			calDto.setRangeType(ScheduleConstant.CALENDAR_RANGE_TYPE_SELF);
		}
		dealWithSearchType();
		dealWithCalTime();
		hasDeptAuth = officeCalendarAuthService.checkHasAuth(getUnitId(), ScheduleConstant.CALENDAR_AUTH_TYPE_DEPT, getLoginInfo().getUser().getId());
//		hasUnitAuth = officeCalendarAuthService.checkHasAuth(getUnitId(), ScheduleConstant.CALENDAR_AUTH_TYPE_LEADER, getLoginInfo().getUser().getId());
		return SUCCESS;
	}
	
	/**
	 * 时间处理
	 */
	private void dealWithCalTime(){
		Calendar cal = Calendar.getInstance();
		if(calDto.getCalendarTime() == null){
			if (isweek) {// 周视图，改成周一
				cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			} else if (ismonth){// 月视图，改成1号
				cal.set(Calendar.DAY_OF_MONTH, 1);
			}
			calDto.setCalendarTime(cal.getTime());
		} else {
			cal.setTime(calDto.getCalendarTime());
			if("1".equals(calDto.getOperate())){// 上一个
				if (isweek) {
					cal.add(Calendar.DATE, -7);
				} else if(ismonth){
					cal.add(Calendar.MONTH, - 1);
				} else {
					cal.add(Calendar.DATE, -1);
				}
				calDto.setCalendarTime(cal.getTime());
			} else if("2".equals(calDto.getOperate())){// 下一个
				if (isweek) {
					cal.add(Calendar.DATE, 7);
				} else if(ismonth){
					cal.add(Calendar.MONTH, 1);
				} else {
					cal.add(Calendar.DATE, 1);
				}
				calDto.setCalendarTime(cal.getTime());
			}
		}
		if(isweek){
			cal.add(Calendar.DAY_OF_MONTH, 6);
			calDto.setEndTime(cal.getTime());
		}
	}
	
	/**
	 * 处理查看视图类型
	 */
	private void dealWithSearchType(){
		if (!isweek){
			isweek = ScheduleConstant.SEARCH_TYPE_WEEK.equals(calDto.getSearchType())?true:false;
		}
		if (!isweek){
			ismonth = ScheduleConstant.SEARCH_TYPE_MONTH.equals(calDto.getSearchType())?true:false;
		}
		if (!ismonth) {
			isday = ScheduleConstant.SEARCH_TYPE_DATE.equals(calDto
					.getSearchType()) ? true : false;
		}
	}
	
	// 各视图数据显示
	public String toData(){
		dealWithSearchType();
		dealDates();
		if(!ismonth){
			calDto.setNeedContent(true);
		}
		calDto.setCreator(getLoginInfo().getUser().getId());
		calDto.setUnitId(getLoginInfo().getUnitID());
		calMap = officeCalendarService.getCalendarMapBy(calDto, dates);
		if(ismonth){
			return "month";
		} else if(isday){
			return "day";
		}
		return "week";
	}
	
	/**
	 * 处理数据显示的时间区间
	 */
	private void dealDates(){
		Calendar cal = Calendar.getInstance();
		cal.setTime(calDto.getCalendarTime());
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		int lenth = 7;
		if(ismonth){
			lenth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			calDto.setWeekNum(cal.getActualMaximum(Calendar.WEEK_OF_MONTH));
			calDto.setDayNum(lenth);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(calDto.getCalendarTime());
			cal2.set(Calendar.DATE, 1);
			calDto.setFirstWeek(cal2.get(Calendar.DAY_OF_WEEK));
		} else if(isday){
			lenth = 0;
		}
		dates = new ArrayList<DateDto>();
		DateDto dto;
		do {
			dto = new DateDto();
			dto.setCalendarDate(cal.getTime());
			dto.setDateStr(DateUtils.date2String(dto.getCalendarDate(), "MM月dd日"));
			dto.setStrByDay(DateUtils.date2StringByDay(dto.getCalendarDate()));
			dto.setDayOfWeek(cal.get(Calendar.DAY_OF_WEEK));
			dto.setDayOfWeekStr(ScheduleConstant.getWeekName(dto.getDayOfWeek()));
			dto.setWeek(cal.get(Calendar.WEEK_OF_MONTH));
			dates.add(dto);
			dto = null;
			cal.add(Calendar.DAY_OF_MONTH, 1);
			lenth --;
		} while(lenth > 0);
		if (dates.size() > 0) {
			calDto.setEndTime(dates.get(dates.size() - 1).getCalendarDate());
		}
	}
	
	// 新增修改
	public String toEdit(){
		if(StringUtils.isNotEmpty(cal.getId())){
			cal = officeCalendarService.getOfficeCalendarById(cal.getId());
			if(cal == null){
				cal = new OfficeCalendar();
			}
		}
		if(StringUtils.isEmpty(cal.getId())){
			cal.setCalendarTime(calDto.getCalendarTime());
			cal.setPeriod(NumberUtils.toInt(calDto.getPeriod()));
			cal.setCreator(getLoginInfo().getUser().getId());
			cal.setCreatorType(NumberUtils.toInt(calDto.getCreatorType()));
			cal.setUnitId(getUnitId());
			cal.setOperator(cal.getCreator());
			cal.setIsSmsAlarm(true);
		} else {
			String cts = DateUtils.date2String(cal.getCalendarTime(), "HH:mm");
			String ets = DateUtils.date2String(cal.getEndTime(), "HH:mm");
			if(("00:00".equals(cts) 
					&& "23:59".equals(ets)) || cal.getPeriod() == ScheduleConstant.PERIOD_ALLDAY) {
				cal.setAllDayEvent(true);
			}
		}
		
		return SUCCESS;
	}
	
	public String saveCal(){
		try {
			int day = (int) ((cal.getEndTime().getTime() - cal
					.getCalendarTime().getTime()) / (1000 * 60 * 60 * 24)) + 1;
			cal.setHalfDays(day);
			if(isSendS){
				if (DateUtils.compareIgnoreSecond(cal.getSmsAlarmTime(), new Date()) < 0) {
					promptMessageDto.setErrorMessage("短信提醒时间必须晚于当前时间！");
					promptMessageDto.setOperateSuccess(false);
					return SUCCESS;
				}
				cal.setIsSmsAlarm(true);
			} else {
				cal.setSmsAlarmTime(null);
				cal.setIsSmsAlarm(false);
			}
			promptMessageDto = officeCalendarService.saveOrUpdate(cal);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			promptMessageDto.setErrorMessage("保存失败！");
			promptMessageDto.setOperateSuccess(false);
		}
		return SUCCESS;
	}
	
	// 删除
	public String delCal(){
		try {
			officeCalendarService.delete(new String[]{cal.getId()});
			promptMessageDto.setPromptMessage("删除成功！");
			promptMessageDto.setOperateSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			promptMessageDto.setErrorMessage("删除失败！");
			promptMessageDto.setOperateSuccess(false);
		}
		return SUCCESS;
	}
	
	// 弹出的详情
	public String toDetails(){
		calDto.setCreator(getLoginInfo().getUser().getId());
		calDto.setUnitId(getLoginInfo().getUnitID());
		cals = officeCalendarService.getOfficeCalendarDetails(calDto);
		return SUCCESS;
	}
	
	// 导出
	public String toDown(){
		//生成数据对象dataMap，其中key是模板文件中引用的标签         
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try {             
			dealWithSearchType();
			dealDates();
			calDto.setNeedContent(true);
			calDto.setFullContent(true);
			calDto.setCreator(getLoginInfo().getUser().getId());
			calDto.setUnitId(getLoginInfo().getUnitID());
			calMap = officeCalendarService.getCalendarMapBy(calDto, dates);
			
			boolean edu = getLoginInfo().getUnitClass()==Unit.UNIT_CLASS_EDU ? true : false;
			String calName = StringUtils.equals(ScheduleConstant.CALENDAR_CREATOR_TYPE_LEADER+"", calDto.getCreatorType())?"局领导":"个人";
			if(!edu && StringUtils.equals(ScheduleConstant.CALENDAR_CREATOR_TYPE_LEADER+"", calDto.getCreatorType())){
				calName = "校领导";
			}
			String jbgby = "局办公编印";
			if(!edu){
				jbgby = "校办公编印";
			}
			dataMap.put("title", calName + "日志安排表");
			List<List<OfficeCalendarDto>> ddtos = new ArrayList<List<OfficeCalendarDto>>();
			for(int i=1;i<5;i++){
				List<OfficeCalendarDto> dtos = new ArrayList<OfficeCalendarDto>();
				for(DateDto dt : dates){
					OfficeCalendarDto dto = calMap.get(dt.getStrByDay()+"-"+i);
					if(dto == null){
						dto = new OfficeCalendarDto();
					} else if(CollectionUtils.isNotEmpty(dto.getCals())) {
						StringBuffer sb = new StringBuffer();
						int j=1;
						for(OfficeCalendar cal : dto.getCals()){
							sb.append(j+".("+cal.getCreatorName()+"):("+DateUtils.date2StringByMinute(cal.getCalendarTime())+"至"+DateUtils.date2StringByMinute(cal.getEndTime())+")(");
							if(StringUtils.isNotBlank(cal.getPlace())){
								sb.append(StringUtils.trimToEmpty(cal.getPlace())+")(");
							}
							sb.append(StringUtils.trimToEmpty(cal.getContent()).replaceAll("<p>", "").replaceAll("</p>", "").replaceAll("<br/>", "")+")");
							j++;
						}
						dto.setContent(sb.toString());
					}
					dtos.add(dto);
				}
				ddtos.add(dtos);
			}
//			dataMap.put("calMap", calMap);
			dataMap.put("calDto", calDto);
			dataMap.put("ddtos", ddtos);
			dataMap.put("dates", dates);
			dataMap.put("jbgby", jbgby);
			//生成word内容
			String s = DocumentHandler.createDocString(dataMap, "officeCalendarTemp.xml");
			//因为office和wps认utf-8格式必须是不带bom的，所以这里需要对输入流进行转码
			InputStream in = new StringInputStream(s, "utf-8");
			//或者也可以直接调用DocumentHandler.createDocInputStream方法返回in
			//下载内容  
			String fileName = calName + "日志安排表.doc";
			ServletUtils.download(in, getRequest(), getResponse(), fileName);         
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return NONE;
	}
	
	//TODO
	public String deptLeaderSchedule() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		startTime = DateUtils.date2String(cal.getTime(), "yyyy-MM-dd");
		cal.add(Calendar.DAY_OF_MONTH, 6);
		endTime = DateUtils.date2String(cal.getTime(), "yyyy-MM-dd");
		return SUCCESS;
	}
	public String deptLeaderScheduleList() {
		//获取科室负责人日志
		cals = officeCalendarService.getOfficeCalendarListPage(getLoginUser().getUserId(), getUnitId(), startTime, endTime, getPage());
		return SUCCESS;
	}
	
	/**
	 * 科室人员日志
	 * @return
	 */
	public String toDeptLog(){
		return SUCCESS;
	}
	
	public String toDeptLogList(){
		cals = officeCalendarService.getCalendarListByCondition(getUnitId(), getLoginInfo().getUser().getDeptid(), searchName, startTime, endTime, getPage());
		return SUCCESS;
	}
	
	public String queryLogDetail(){
		cal = officeCalendarService.getOfficeCalendarById(cal.getId());
		return SUCCESS;
	}
	
	public boolean isHasLeader() {
		return hasLeader;
	}

	public void setHasLeader(boolean hasLeader) {
		this.hasLeader = hasLeader;
	}

	public boolean isHasAuthority() {
		return hasAuthority;
	}

	public void setHasAuthority(boolean hasAuthority) {
		this.hasAuthority = hasAuthority;
	}

	public List<DateDto> getDates() {
		return dates;
	}

	public OfficeCalendarDto getCalDto() {
		return calDto;
	}

	public void setCalDto(OfficeCalendarDto calDto) {
		this.calDto = calDto;
	}

	public Map<String, OfficeCalendarDto> getCalMap() {
		return calMap;
	}

	public void setOfficeCalendarService(OfficeCalendarService officeCalendarService) {
		this.officeCalendarService = officeCalendarService;
	}

	public OfficeCalendar getCal() {
		return cal;
	}

	public void setCal(OfficeCalendar cal) {
		this.cal = cal;
	}

	public void setOfficeCalendarAuthService(
			OfficeCalendarAuthService officeCalendarAuthService) {
		this.officeCalendarAuthService = officeCalendarAuthService;
	}

	public boolean isHasDeptAuth() {
		hasDeptAuth = officeCalendarAuthService.checkHasAuth(getUnitId(), ScheduleConstant.CALENDAR_AUTH_TYPE_DEPT, getLoginInfo().getUser().getId());
		return hasDeptAuth;
	}

	public boolean isHasUnitAuth() {
		if(!hasUnitAuth){
			hasUnitAuth = officeCalendarAuthService.checkHasAuth(getUnitId(), ScheduleConstant.CALENDAR_AUTH_TYPE_LEADER, getLoginInfo().getUser().getId());
		}
		return hasUnitAuth;
	}

	public List<OfficeCalendar> getCals() {
		return cals;
	}

	public boolean getIsSendS() {
		return isSendS;
	}

	public void setIsSendS(boolean isSendS) {
		this.isSendS = isSendS;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public boolean isLeader() {
		return isLeader;
	}

	public void setLeader(boolean isLeader) {
		this.isLeader = isLeader;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public boolean isGroupHead() {
		String deptId = deptService.isPrincipanGroupHead(getLoginInfo().getUser().getId());
		if(StringUtils.isNotBlank(deptId))
			return true;
		return false;
	}

	public void setGroupHead(boolean isGroupHead) {
		this.isGroupHead = isGroupHead;
	}

	
}
