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
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.frame.action.PageAction;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.ServletUtils;
import net.zdsoft.leadin.doc.DocumentHandler;
import net.zdsoft.leadin.util.excel.ZdCell;
import net.zdsoft.leadin.util.excel.ZdExcel;
import net.zdsoft.leadin.util.excel.ZdStyle;
import net.zdsoft.office.schedule.dto.DateDto;
import net.zdsoft.office.schedule.dto.OfficeCalendarDto;
import net.zdsoft.office.schedule.entity.OfficeWorkOutline;
import net.zdsoft.office.schedule.service.OfficeCalendarAuthService;
import net.zdsoft.office.schedule.service.OfficeWorkOutlineService;
import net.zdsoft.office.schedule.util.ScheduleConstant;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.tools.ant.filters.StringInputStream;
/**                                        
 *			  ***** ***** ***** ***** *****
 *      *******   ***   ***   ***   ***   *******
 *      |                @author                |
 *      |                zhounan                |
 *	^^------                                 ------^^  ----^^ ^^
 */
@SuppressWarnings("serial")
public class WeekWorkOutlineAction extends PageAction{
	
	private String beginDate;//周开始时间
	private String endDate;//周结束时间
	private Date startTime;//日程开始时间
	private int num;//对应的记录数
	private String officeWorkOutlineId;//日程id
	private int unitordept;//部门或单位
	private String deptId;//部门id
	private int periodParam;
	private boolean isSendSms=false;//是否发送短信
	private List<Date> weekDate=new ArrayList<Date>();//一周的时间
	private boolean isHasAuth=false;//是否局领导
	private boolean isDeptAuth=false;//是否科室领导
	private String switchWeek;//上周 本周 下周
	private boolean isWeek=false;
	private boolean isMonth=false;
	private boolean isDay=false;
	private String searchType;
	private List<DateDto> dates;
	private OfficeCalendarDto officeCalendarDto=new OfficeCalendarDto();
	private List<OfficeWorkOutline> periodList=new ArrayList<OfficeWorkOutline>();
	private List<OfficeWorkOutline> workOutlineList=new ArrayList<OfficeWorkOutline>();
	private Map<String, List<OfficeWorkOutline>> workOutlineMap=new HashMap<String, List<OfficeWorkOutline>>();
	private Map<Integer, List<OfficeWorkOutline>> workOutlineListByPeriodMap=new HashMap<Integer, List<OfficeWorkOutline>>();//上午 中午 下午 晚上的列表   period做key值
	
	private Map<String, List<OfficeWorkOutline>> monthMaptoDay=new HashMap<String, List<OfficeWorkOutline>>();//月视图用
	private Map<String, Date> dateMap=new HashMap<String, Date>();//月视图用
	private int weeksOfMonth;
	
	private String exportStr;//局办公：校办公
	
	private OfficeWorkOutline officeWorkOutline=new OfficeWorkOutline();

	private OfficeWorkOutlineService officeWorkOutlineService;
	private UserService userService;
	private DeptService deptService;
	private OfficeCalendarAuthService officeCalendarAuthService;

	
	public String execute(){
		return SUCCESS;
	}
	
	
	public String toDeptWeekWork(){
		
		dealWithCalTime();
		isHasAuth=officeCalendarAuthService.checkHasAuth(this.getUnitId(), ScheduleConstant.CALENDAR_AUTH_TYPE_LEADER, this.getLoginUser().getUserId());
		isDeptAuth=officeCalendarAuthService.checkHasAuth(this.getUnitId(), ScheduleConstant.CALENDAR_AUTH_TYPE_DEPT, this.getLoginUser().getUserId());
		return SUCCESS;
	}
	
	
	//时间处理
	public void dealWithCalTime(){
		Calendar cal=Calendar.getInstance();
		if(officeCalendarDto.getCalendarTime()==null){
			if(isWeek){
				cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			}else if(isMonth){
				cal.set(Calendar.DAY_OF_MONTH, 1);
			}
			officeCalendarDto.setCalendarTime(cal.getTime());
		}else{
			cal.setTime(officeCalendarDto.getCalendarTime());
			if("1".equals(officeCalendarDto.getOperate())){//上一个
				if(isWeek){
					cal.add(Calendar.DATE, -7);
				}else if(isMonth){
					cal.add(Calendar.MONTH,-1);
				}else{
					cal.add(Calendar.DATE, -1);
				}
			}else if("2".equals(officeCalendarDto.getOperate())){//下一个
				if(isWeek){
					cal.add(Calendar.DATE, 7);
				}else if(isMonth){
					cal.add(Calendar.MONTH, 1);
				}else{
					cal.add(Calendar.DATE, 1);
				}
			}
			officeCalendarDto.setCalendarTime(cal.getTime());
		}
		if(isWeek){
			cal.add(Calendar.DAY_OF_WEEK, 6);
			officeCalendarDto.setEndTime(cal.getTime());
		}
	}
	
	//设置数据显示的时间区间
	public void dealDates(){
		Calendar cal=Calendar.getInstance();
		cal.setTime(officeCalendarDto.getCalendarTime());
		int length=7;
		if(isMonth){
			length=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		}else if(isDay){
			length=0;
		}
		dates=new ArrayList<DateDto>();
		DateDto dto;
		do{
			dto=new DateDto();
			dto.setCalendarDate(cal.getTime());
			dto.setDateStr(DateUtils.date2String(dto.getCalendarDate(),"MM月dd日"));
			dto.setStrByDay(DateUtils.date2StringByDay(dto.getCalendarDate()));
			dto.setDayOfWeek(cal.get(Calendar.DAY_OF_WEEK));
			dto.setDayOfWeekStr(ScheduleConstant.getWeekName(dto.getDayOfWeek()));
			dates.add(dto);
			
			dto=null;
			cal.add(Calendar.DAY_OF_MONTH, 1);
			length--;
		}while(length>0);
		if(dates.size()>0){
			officeCalendarDto.setEndTime(dates.get(dates.size()-1).getCalendarDate());
		}
	}

	//科室日程 新增 编辑
	public String workOutlineAdd(){
		//新增--默认当前科室  不可维护
		String deptNam=deptService.getDept(userService.getDeptIdByUserId(getLoginUser().getUserId())).getDeptname();
		officeWorkOutline.setDeptName(deptNam); 
		if(StringUtils.isEmpty(officeWorkOutline.getId())){
			officeWorkOutline.setCalendarTime(officeCalendarDto.getCalendarTime());
			isSendSms=true;
			officeWorkOutline.setIsSmsAlarm(1);
		}else{
			officeWorkOutline=officeWorkOutlineService.getOfficeWorkOutlineById(officeWorkOutline.getId());
			if(officeWorkOutline==null){
				officeWorkOutline=new OfficeWorkOutline();
			}else{
				officeWorkOutline.setDeptName(deptNam); 
				if(officeWorkOutline.getIsSmsAlarm()==1){
					isSendSms=true;
				}else{
					isSendSms=false;
				}
			}
			String st=DateUtils.date2String(officeWorkOutline.getCalendarTime(),"HH:mm");
			String et=DateUtils.date2String(officeWorkOutline.getEndTime(),"HH:mm");
			if("00.00".equals(st)&& "23.59".equals(et)||ScheduleConstant.PERIOD_ALLDAY==officeWorkOutline.getPeriod()){
				officeWorkOutline.setIsAllDayEvent(true);
			}
		}
		return SUCCESS;
	}
	
		
	//科室日程保存
	public String workOutlineSave(){
		String deptid=userService.getDeptIdByUserId(this.getLoginUser().getUserId());
		boolean flag=officeWorkOutlineService.isExistConflict(this.getUnitId(), deptid, officeWorkOutline.getId(), officeWorkOutline.getCalendarTime(), officeWorkOutline.getEndTime());
		if(flag){
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("保存失败,当前时间段与已维护过的时间段有冲突!");
			return SUCCESS;
		}
		int day = (int) ((officeWorkOutline.getEndTime().getTime() - officeWorkOutline
				.getCalendarTime().getTime()) / (1000 * 60 * 60 * 24)) + 1;
		officeWorkOutline.setHalfDays(day);
		try {
			if(isSendSms){
				if (DateUtils.compareIgnoreSecond(officeWorkOutline.getSmsAlarmTime(), new Date()) < 0) {
					promptMessageDto.setPromptMessage("短信提醒时间必须晚于当前时间！");
					promptMessageDto.setOperateSuccess(false);
					return SUCCESS;
				}
				officeWorkOutline.setIsSmsAlarm(ScheduleConstant.WORKOUTLINE_ISSMSALARM);
			}else{
				officeWorkOutline.setIsSmsAlarm(ScheduleConstant.WORKOUTLINE_NOTSMSALARM);
				officeWorkOutline.setSmsAlarmTime(null);
			}
			
			if(StringUtils.isNotBlank(officeWorkOutline.getId())){
				officeWorkOutline.setOperator(getLoginUser().getUserId());
				officeWorkOutline.setModifyTime(new Date());
				officeWorkOutlineService.update(officeWorkOutline);
			}else{
				officeWorkOutline.setCreateDept(deptid);
				officeWorkOutline.setOperator(this.getLoginUser().getUserId());
				officeWorkOutline.setUnitId(this.getUnitId());
				officeWorkOutline.setModifyTime(new Date());
				officeWorkOutline.setIsDeleted(0);
//				officeWorkOutline.setSmsAlarmTime(null);
				officeWorkOutline.setVersion(ScheduleConstant.WORKOUTLINE_VERSION);
				officeWorkOutlineService.save(officeWorkOutline);
			}
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("保存失败！");
		}
		return SUCCESS;
	}

	
	//日程删除
	public String deleteWorkOutLineInfo(){
		try {
			if(StringUtils.isNotBlank(officeWorkOutline.getId())){
				officeWorkOutlineService.delete(new String[]{officeWorkOutline.getId()});
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("删除成功！");
			}else{
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setPromptMessage("删除对象不存在！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("操作失败！");
		}
		return SUCCESS;
	}
	//周视图列表查询   单位/科室 
	public String workOutlineListBySearchParams(){
		dealWithCalTime();
		dealDates();
		makeWeekDate(officeCalendarDto.getCalendarTime());
		
		if(unitordept==1){//dept
			deptId=userService.getDeptIdByUserId(this.getLoginInfo().getUser().getId());
			workOutlineMap=officeWorkOutlineService.getOfficeWorkOutlinesBySearchParams(weekDate,this.getUnitId(), deptId, officeCalendarDto.getCalendarTime(), officeCalendarDto.getEndTime());
			//权限控制---拥有本部门权限的才可以维护
			isDeptAuth=officeCalendarAuthService.checkHasAuth(this.getUnitId(), ScheduleConstant.CALENDAR_AUTH_TYPE_DEPT, this.getLoginUser().getUserId());
		}else{//unit
			if(StringUtils.isNotBlank(this.getUnitId())){
				workOutlineMap=officeWorkOutlineService.getOfficeWorkOutlinesBySearchParams(weekDate,this.getUnitId(), null, officeCalendarDto.getCalendarTime(), officeCalendarDto.getEndTime());
			}
			isDeptAuth=officeCalendarAuthService.checkHasAuth(this.getUnitId(), ScheduleConstant.CALENDAR_AUTH_TYPE_DEPT, this.getLoginUser().getUserId());
		}
		if(workOutlineMap==null){
			workOutlineMap=new HashMap<String, List<OfficeWorkOutline>>();
		}
		return SUCCESS;
	}
	
	//月视图列表查询  查出一个月内的记录
	public String workoutlineListOfMonthView(){
		dealWithCalTime();
		dealDates();
		String deptid=userService.getDeptIdByUserId(this.getLoginUser().getUserId());
		if(officeCalendarDto.getCalendarTime()!=null){
			//-----
			Date firstDay=new Date();
			Date lastDay=new Date();
			List<Date> dates=new ArrayList<Date>();
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(officeCalendarDto.getCalendarTime());
			int days=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			for(int i=0;i<days;i++){
				calendar.set(Calendar.DAY_OF_MONTH, i+1);
				if(i==0){
					firstDay=calendar.getTime();
				}
				if(i==(days-1)){
					lastDay=calendar.getTime();
				}
				dates.add(calendar.getTime());
			}
			//----^^^^所有记录
			if(unitordept==1){//dept
				workOutlineMap=officeWorkOutlineService.getOfficeWorkOutlinesByMonth(this.getUnitId(), deptid, firstDay, lastDay, dates);
			}else{//unit
				workOutlineMap=officeWorkOutlineService.getOfficeWorkOutlinesByMonth(this.getUnitId(), null, firstDay, lastDay, dates);
			}
			if(workOutlineMap==null){
				workOutlineMap=new HashMap<String, List<OfficeWorkOutline>>();
			}
			//权限控制
			isDeptAuth=officeCalendarAuthService.checkHasAuth(this.getUnitId(), ScheduleConstant.CALENDAR_AUTH_TYPE_DEPT, getLoginUser().getUserId());
			//-----
			calendar.setFirstDayOfWeek(Calendar.MONDAY);
			weeksOfMonth=calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
			for(int i=0;i<days;i++){
				calendar.set(Calendar.DAY_OF_MONTH, i+1);
				int weekOfMonth=calendar.get(Calendar.WEEK_OF_MONTH);
				int dayOfWeek=calendar.get(Calendar.DAY_OF_WEEK);
				if(dayOfWeek == Calendar.SUNDAY){
					dayOfWeek = 7;
				} else {
					dayOfWeek--;
				}
				monthMaptoDay.put(weekOfMonth+""+dayOfWeek, workOutlineMap.get(DateUtils.date2String(calendar.getTime())));
				dateMap.put(weekOfMonth+""+dayOfWeek, calendar.getTime());
			}
		}
		return SUCCESS;
	}
	
	
	//日视图列表查询
	public String getWorkOutlineListByDay(){
		dealDates();
		String deptid=userService.getDeptIdByUserId(this.getLoginUser().getUserId());
		workOutlineList=officeWorkOutlineService.getOfficeWorkOutlineByDay(this.getUnitId(), deptid, officeCalendarDto.getCalendarTime());//页面传daydate
		List<OfficeWorkOutline> amList=new ArrayList<OfficeWorkOutline>();
		List<OfficeWorkOutline> midList=new ArrayList<OfficeWorkOutline>();
		List<OfficeWorkOutline> pmList=new ArrayList<OfficeWorkOutline>();
		List<OfficeWorkOutline> lastList=new ArrayList<OfficeWorkOutline>();
		List<OfficeWorkOutline> alldaylist=new ArrayList<OfficeWorkOutline>();
		amList=officeWorkOutlineService.getOfficeWorkOutlineByPeriodOfDay(this.getUnitId(), deptid, officeCalendarDto.getCalendarTime(), ScheduleConstant.PERIOD_AM);
		midList=officeWorkOutlineService.getOfficeWorkOutlineByPeriodOfDay(this.getUnitId(), deptid, officeCalendarDto.getCalendarTime(), ScheduleConstant.PERIOD_NOON);
		pmList=officeWorkOutlineService.getOfficeWorkOutlineByPeriodOfDay(this.getUnitId(), deptid, officeCalendarDto.getCalendarTime(), ScheduleConstant.PERIOD_PM);
		lastList=officeWorkOutlineService.getOfficeWorkOutlineByPeriodOfDay(this.getUnitId(), deptid, officeCalendarDto.getCalendarTime(), ScheduleConstant.PERIOD_NIGHT);
		alldaylist=officeWorkOutlineService.getOfficeWorkOutlineByPeriodOfDay(this.getUnitId(), deptid, officeCalendarDto.getCalendarTime(), ScheduleConstant.PERIOD_ALLDAY);
		if(CollectionUtils.isNotEmpty(alldaylist)){
			amList.addAll(alldaylist);
			midList.addAll(alldaylist);
			pmList.addAll(alldaylist);
			lastList.addAll(alldaylist);
		}
		workOutlineListByPeriodMap.put(ScheduleConstant.PERIOD_AM, amList);
		workOutlineListByPeriodMap.put(ScheduleConstant.PERIOD_NOON, midList);
		workOutlineListByPeriodMap.put(ScheduleConstant.PERIOD_PM, pmList);
		workOutlineListByPeriodMap.put(ScheduleConstant.PERIOD_NIGHT, lastList);
		isDeptAuth=officeCalendarAuthService.checkHasAuth(this.getUnitId(), ScheduleConstant.CALENDAR_AUTH_TYPE_DEPT, getLoginUser().getUserId());
		return SUCCESS;
	}
	
	//日程(单日)列表查询(toDeatilView)
	public String getWorkOutLineListByDayPeriod(){
		String deptid=userService.getDeptIdByUserId(this.getLoginUser().getUserId());
		List<OfficeWorkOutline> alldayList=new ArrayList<OfficeWorkOutline>();
		if((Integer.valueOf(officeWorkOutline.getPeriod()))!=null&&officeCalendarDto.getCalendarTime()!=null&&(isWeek||isDay)){//周视图 日视图
			if(isWeek){
				if(unitordept==1){//dept
					alldayList=officeWorkOutlineService.getOfficeWorkOutlineByPeriodOfDay(this.getUnitId(), deptid, officeCalendarDto.getCalendarTime(), ScheduleConstant.PERIOD_ALLDAY);
					periodList=officeWorkOutlineService.getOfficeWorkOutlineByPeriodOfDay(this.getUnitId(), deptid, officeCalendarDto.getCalendarTime(), officeWorkOutline.getPeriod());
				}else{//unit--查看整个单位的
					alldayList=officeWorkOutlineService.getOfficeWorkOutlineByPeriodOfDay(this.getUnitId(), null, officeCalendarDto.getCalendarTime(), ScheduleConstant.PERIOD_ALLDAY);
					periodList=officeWorkOutlineService.getOfficeWorkOutlineByPeriodOfDay(this.getUnitId(), null, officeCalendarDto.getCalendarTime(), officeWorkOutline.getPeriod());
				}
			}else if(isDay){
				alldayList=officeWorkOutlineService.getOfficeWorkOutlineByPeriodOfDay(this.getUnitId(), deptid, officeCalendarDto.getCalendarTime(), ScheduleConstant.PERIOD_ALLDAY);
				periodList=officeWorkOutlineService.getOfficeWorkOutlineByPeriodOfDay(this.getUnitId(), deptid, officeCalendarDto.getCalendarTime(), officeWorkOutline.getPeriod());
			}
			if(CollectionUtils.isNotEmpty(alldayList)){
				periodList.addAll(alldayList);
			}
		}else if(officeCalendarDto.getCalendarTime()!=null&&isMonth){//月视图查看详情
			if(unitordept==1){
				periodList=officeWorkOutlineService.getOfficeWorkOutlineByDay(this.getUnitId(), deptid, officeCalendarDto.getCalendarTime());
			}else{
				periodList=officeWorkOutlineService.getOfficeWorkOutlineByDay(this.getUnitId(), null, officeCalendarDto.getCalendarTime());
			}
		}
		if(CollectionUtils.isEmpty(periodList)){
			periodList=new ArrayList<OfficeWorkOutline>();
		}else{
			//authority of control---本部门并且拥有权限
			isDeptAuth=officeCalendarAuthService.checkHasAuth(this.getUnitId(), ScheduleConstant.CALENDAR_AUTH_TYPE_DEPT, this.getLoginUser().getUserId());
			if(isDeptAuth){
				for(OfficeWorkOutline off:periodList){
					if(off.getCreateDept().equals(deptid)){
						off.setIsHasAuthToOperate(true);
					}
				}
			}
		}
		
		return SUCCESS;
	}
	
	
	//得出一周的时间
	public void makeWeekDate(Date beginDate){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(beginDate);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
		weekDate.add(calendar.getTime());
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
		weekDate.add(calendar.getTime());
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		weekDate.add(calendar.getTime());
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
		weekDate.add(calendar.getTime());
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		weekDate.add(calendar.getTime());
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		weekDate.add(calendar.getTime());
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		weekDate.add(calendar.getTime());
	}
	
	
	//导出
	public String exportWorkOutline(){
		//获得开始时间到结束时间
		try {
			String deptid="";
			if(unitordept==1){//dept
				deptid=userService.getDeptIdByUserId(this.getLoginUser().getUserId());
			}else{//unit
				deptid=null;
			}
			dealWithCalTime();
			dealDates();
			makeWeekDate(officeCalendarDto.getCalendarTime());
			Map<String, Object> dataMap=new HashMap<String, Object>();
			Map<String, List<OfficeWorkOutline>> outlineMap=new HashMap<String, List<OfficeWorkOutline>>();
			outlineMap=officeWorkOutlineService.getOfficeWorkOutlinesBySearchParams(weekDate, this.getUnitId(), deptid, officeCalendarDto.getCalendarTime(), officeCalendarDto.getEndTime());
			//查询列表
			exportStr=getLoginInfo().getUnitClass()==Unit.UNIT_CLASS_EDU?"局办公":"校办公";
			dataMap.put("printDate", new Date());
			dataMap.put("beginDate", officeCalendarDto.getCalendarTime());
			dataMap.put("exportStr", exportStr);
			dataMap.put("endTime", officeCalendarDto.getEndTime());
			dataMap.put("dates", dates);
			dataMap.put("weekOutlineMap", outlineMap);
			String s=DocumentHandler.createDocString(dataMap, "week1.xml");
			InputStream in=new StringInputStream(s, "utf-8");
			ServletUtils.download(in, getRequest(), getResponse(),"科室周重点工作安排表.doc");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("文件出错！");
		}
		
		return SUCCESS;
	}
	
	public String exportToExcel(){
		dealDates();
		String deptid=userService.getDeptIdByUserId(this.getLoginUser().getUserId());
		dealWithCalTime();
		dealDates();
		makeWeekDate(officeCalendarDto.getCalendarTime());
		Map<String, List<OfficeWorkOutline>> outlineMap=new HashMap<String, List<OfficeWorkOutline>>();
		outlineMap=officeWorkOutlineService.getOfficeWorkOutlinesBySearchParams(weekDate, this.getUnitId(), deptid, officeCalendarDto.getCalendarTime(), officeCalendarDto.getEndTime());
		//查询列表
		ZdExcel zdExcel=new ZdExcel();
		ZdStyle style=new ZdStyle(ZdStyle.BOLD|ZdStyle.BORDER);
		ZdStyle style2=new ZdStyle(ZdStyle.BORDER);
		style.WRAP_TEXT=10;
		style2.WRAP_TEXT=10;
		zdExcel.add(new ZdCell("科室周重点工作安排",8,2,new ZdStyle(ZdStyle.BORDER|ZdStyle.ALIGN_CENTER|ZdStyle.WRAP_TEXT,18)));
		zdExcel.add(new ZdCell("局办公编印                           时间： "+DateUtils.date2StringByDay(officeCalendarDto.getCalendarTime())+"-"+DateUtils.date2StringByDay(officeCalendarDto.getEndTime()),8,1,new ZdStyle(ZdStyle.BORDER|ZdStyle.WRAP_TEXT,12)));
		List<ZdCell> zdCellList=new ArrayList<ZdCell>();
		String[] weekNames=new String[]{"星期一","星期二","星期三","星期四","星期五","星期六","星期日"};
		int i=0;
		int n=2;
		zdCellList.add(new ZdCell("日期/时间",1,2,style));
		for(DateDto dto:dates){//----
			zdCellList.add(new ZdCell(dto.getStrByDay()+"\r\n"+weekNames[i], 1, 2,new ZdStyle(ZdStyle.BOLD|ZdStyle.BORDER|ZdStyle.ALIGN_CENTER)));
			i++;
			n++;
		}
		zdExcel.add(zdCellList.toArray(new ZdCell[0]));
		//--||
		String[] pes=new String[]{"上午","中午","下午","晚上"};
		for(int f=0;f<4;f++){
			ZdCell[] cells=new ZdCell[8];
			cells[0]=new ZdCell(pes[f],1,5,new ZdStyle(ZdStyle.ALIGN_CENTER|ZdStyle.BORDER));
			int index=0;
			for(DateDto dto:dates){
				System.out.println(dto.getStrByDay()+""+(f+1));
				System.out.println(outlineMap.get(dto.getStrByDay()+""+(f+1)));
				if(outlineMap!=null&&CollectionUtils.isNotEmpty(outlineMap.get(dto.getStrByDay()+""+(f+1)))){
					List<OfficeWorkOutline> dtlist=new ArrayList<OfficeWorkOutline>();
					dtlist=outlineMap.get(dto.getStrByDay()+""+(f+1));
					String contentStr="";//内容
					for(OfficeWorkOutline off:dtlist){
						contentStr+=off.getCalendarTime()+off.getPlace()+off.getContent()+"\r\n";
					}
					cells[index+1]=new ZdCell(contentStr,1,5,style2);
				}else{
					cells[index+1]=new ZdCell("", 1,5, style2);
				}
				index++;
			}
			zdExcel.add(cells);
		}
		Sheet sheet=zdExcel.createSheet("sheet1");
		for(int g=1;g<8;g++){
			zdExcel.setCellWidth(sheet, g, 3);
		}
		zdExcel.export("科室周重点工作");
		return NONE;
	}
	
	/*------get/set--------*/
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public OfficeWorkOutline getOfficeWorkOutline() {
		return officeWorkOutline;
	}

	public void setOfficeWorkOutline(OfficeWorkOutline officeWorkOutline) {
		this.officeWorkOutline = officeWorkOutline;
	}

	public void setOfficeWorkOutlineService(
			OfficeWorkOutlineService officeWorkOutlineService) {
		this.officeWorkOutlineService = officeWorkOutlineService;
	}

	public String getOfficeWorkOutlineId() {
		return officeWorkOutlineId;
	}

	public void setOfficeWorkOutlineId(String officeWorkOutlineId) {
		this.officeWorkOutlineId = officeWorkOutlineId;
	}

	public List<OfficeWorkOutline> getWorkOutlineList() {
		return workOutlineList;
	}

	public void setWorkOutlineList(List<OfficeWorkOutline> workOutlineList) {
		this.workOutlineList = workOutlineList;
	}

	public Map<String, List<OfficeWorkOutline>> getWorkOutlineMap() {
		return workOutlineMap;
	}

	public void setWorkOutlineMap(
			Map<String, List<OfficeWorkOutline>> workOutlineMap) {
		this.workOutlineMap = workOutlineMap;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	public void setOfficeCalendarAuthService(
			OfficeCalendarAuthService officeCalendarAuthService) {
		this.officeCalendarAuthService = officeCalendarAuthService;
	}


	public String getSwitchWeek() {
		return switchWeek;
	}


	public void setSwitchWeek(String switchWeek) {
		this.switchWeek = switchWeek;
	}


	public Date getStartTime() {
		return startTime;
	}


	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}


	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}


	public boolean getIsSendSms() {
		return isSendSms;
	}


	public void setIsSendSms(boolean isSendSms) {
		this.isSendSms = isSendSms;
	}


	public boolean getIsDeptAuth() {
		return isDeptAuth;
	}


	public void setIsDeptAuth(boolean isDeptAuth) {
		this.isDeptAuth = isDeptAuth;
	}


	public boolean getIsHasAuth() {
		return isHasAuth;
	}


	public void setIsHasAuth(boolean isHasAuth) {
		this.isHasAuth = isHasAuth;
	}


	public Map<Integer, List<OfficeWorkOutline>> getWorkOutlineListByPeriodMap() {
		return workOutlineListByPeriodMap;
	}


	public void setWorkOutlineListByPeriodMap(
			Map<Integer, List<OfficeWorkOutline>> workOutlineListByPeriodMap) {
		this.workOutlineListByPeriodMap = workOutlineListByPeriodMap;
	}


	public int getPeriodParam() {
		return periodParam;
	}


	public void setPeriodParam(int periodParam) {
		this.periodParam = periodParam;
	}


	public List<OfficeWorkOutline> getPeriodList() {
		return periodList;
	}


	public void setPeriodList(List<OfficeWorkOutline> periodList) {
		this.periodList = periodList;
	}


	public int getUnitordept() {
		return unitordept;
	}


	public void setUnitordept(int unitordept) {
		this.unitordept = unitordept;
	}


	public OfficeCalendarDto getOfficeCalendarDto() {
		return officeCalendarDto;
	}


	public void setOfficeCalendarDto(OfficeCalendarDto officeCalendarDto) {
		this.officeCalendarDto = officeCalendarDto;
	}


	public List<DateDto> getDates() {
		return dates;
	}


	public void setDates(List<DateDto> dates) {
		this.dates = dates;
	}


	public String getSearchType() {
		return searchType;
	}


	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}


	public boolean getIsWeek() {
		return isWeek;
	}


	public void setIsWeek(boolean isWeek) {
		this.isWeek = isWeek;
	}


	public boolean getIsMonth() {
		return isMonth;
	}


	public void setIsMonth(boolean isMonth) {
		this.isMonth = isMonth;
	}


	public boolean getIsDay() {
		return isDay;
	}


	public void setIsDay(boolean isDay) {
		this.isDay = isDay;
	}


	public Map<String, List<OfficeWorkOutline>> getMonthMaptoDay() {
		return monthMaptoDay;
	}


	public void setMonthMaptoDay(Map<String, List<OfficeWorkOutline>> monthMaptoDay) {
		this.monthMaptoDay = monthMaptoDay;
	}


	public Map<String, Date> getDateMap() {
		return dateMap;
	}


	public void setDateMap(Map<String, Date> dateMap) {
		this.dateMap = dateMap;
	}


	public int getWeeksOfMonth() {
		return weeksOfMonth;
	}


	public void setWeeksOfMonth(int weeksOfMonth) {
		this.weeksOfMonth = weeksOfMonth;
	}


	public String getExportStr() {
		return exportStr;
	}


	public void setExportStr(String exportStr) {
		this.exportStr = exportStr;
	}
	
	
}
